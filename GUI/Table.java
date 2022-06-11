package GUI;

import Board.Board;
import Pieces.Piece;
import Board.BoardUtils;
import Board.Move;
import Board.Tile;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import Board.AddPiece;
import Pieces.PieceType;
import Player.Player;

public class Table {
    private static final Dimension dimension = new Dimension(700, 600);
    private final TakenPiecesPanel takenPiecesPanel;
    private Board chessBoard;
    private static final String imagePath = "piece icons/";
    private final Color lightTileColor = new Color(255, 255, 204);
    private final Color darkTileColor = new Color(153, 102, 0);
    private final Dimension tilePanelDimension = new Dimension(10, 10);
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece movedPiece;
    private final static Dimension boardPanelDimension = new Dimension(400, 350);

    public Table() {
        JFrame gameFrame = new JFrame("chess");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setSize(dimension);
        this.chessBoard = Board.createStandardBoard();
        this.takenPiecesPanel = new TakenPiecesPanel();
        BoardPanel boardPanel = new BoardPanel();
        gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }

    public class TilePanel extends JPanel {
        private final int tileID;
        TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(tilePanelDimension);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    if (isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;
                        takenPiecesPanel.setSourcePiece(null);
                    } else if (isLeftMouseButton(event)) {
                        if (takenPiecesPanel.getSourcePiece() == null) {
                            if (sourceTile == null) {
                                sourceTile = chessBoard.getTile(tileID);
                                movedPiece = sourceTile.getPiece();
                                if (movedPiece == null) {
                                    sourceTile = null;
                                }
                            } else {
                                destinationTile = chessBoard.getTile(tileID);
                                final Move move = Move.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile
                                        .getTileCoordinate());
                                if (move != null) {
                                    if (destinationTile.isTileOccupied()) {
                                        if (destinationTile.getPiece().getPieceType() == PieceType.King) {
                                            Player winner = chessBoard.getCurrentPlayer();
                                            JOptionPane.showMessageDialog(null, winner.getPieceColor().toString() + " wins!");
                                            System.exit(0);
                                        }
                                    }
                                    chessBoard = move.execute();
                                }
                                sourceTile = null;
                                destinationTile = null;
                                movedPiece = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileID);
                            if (!destinationTile.isTileOccupied() && takenPiecesPanel.getSourcePiece().getPieceColor()
                                    == chessBoard.getCurrentPlayer().getPieceColor() ) {
                                AddPiece addPiece = new AddPiece(chessBoard, takenPiecesPanel.getSourcePiece(), tileID);
                                chessBoard = addPiece.execute();
                            }
                            takenPiecesPanel.setSourcePiece(null);
                            sourceTile = null;
                            destinationTile = null;
                        }
                        SwingUtilities.invokeLater(() -> {
                            boardPanel.drawBoard(chessBoard);
                            takenPiecesPanel.redo(chessBoard);
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent event) {
                }

                @Override
                public void mouseReleased(MouseEvent event) {
                }
                @Override
                public void mouseEntered(MouseEvent event) {
                }
                @Override
                public void mouseExited(MouseEvent event) {
                }
            });
            validate();
        }
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        private void assignTileColor() {
            if (BoardUtils.firstRow[this.tileID] || BoardUtils.thirdRow[this.tileID]
                    || BoardUtils.fifthRow[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.secondRow[this.tileID] || BoardUtils.forthRow[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
            }
        }
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileID).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(imagePath +
                            board.getTile(this.tileID).getPiece().getPieceColor().toString().charAt(0) +
                            board.getTile(this.tileID).getPiece().toString().toLowerCase() + ".gif"));
                    add (new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(5, 5));
            this.boardTiles = new ArrayList<>();
            int index = 0;
            for (int i = 1; i <= 25; i++) {
                if (i < 6) {
                    index = 20 + i;
                }
                if (i < 11 && i > 5) {
                    index = i + 10;
                }
                if (i < 16 && i > 10) {
                    index = i;
                }
                if (i < 21 && i > 15) {
                    index = i - 10;
                }
                if (i > 20) {
                    index = i - 20;
                }
                final TilePanel tilePanel = new TilePanel(this, index);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(boardPanelDimension);
            validate();
        }
        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

}

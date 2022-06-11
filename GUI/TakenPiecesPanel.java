package GUI;
import Board.Board;
import Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TakenPiecesPanel extends JPanel {
    private final JPanel blackPanel;
    private final JPanel whitePanel;
    private static final EtchedBorder panelBorder = new EtchedBorder(EtchedBorder.RAISED);
    private Piece sourcePiece;

    public TakenPiecesPanel() {
        super(new BorderLayout());
        this.setBackground(Color.GRAY);
        this.setBorder(panelBorder);
        this.blackPanel = new JPanel(new GridLayout(3, 2));
        this.whitePanel = new JPanel(new GridLayout(3, 2));
        this.blackPanel.setBackground(Color.GRAY);
        this.whitePanel.setBackground(Color.GRAY);
        add(this.blackPanel, BorderLayout.NORTH);
        add(this.whitePanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(100, 80));
        setVisible(true);
        validate();
    }

    public void redo(Board board) {
        this.whitePanel.removeAll();
        this.blackPanel.removeAll();
        for (final Piece piece : board.getBlackPlayer().getDeadPieces()) {
            TakenPieceTile takenPieceTile = new TakenPieceTile(piece);
            blackPanel.add(takenPieceTile);
        }
        for (final Piece piece : board.getWhitePlayer().getDeadPieces()) {
            TakenPieceTile takenPieceTile = new TakenPieceTile(piece);
            whitePanel.add(takenPieceTile);
        }
        validate();
    }
    private class TakenPieceTile extends JPanel {
        TakenPieceTile(final Piece piece) {
            super(new GridBagLayout());
            setBackground(Color.GRAY);
            setPreferredSize(new Dimension(50, 50));
            try {
                final BufferedImage image = ImageIO.read(new File("piece icons/" +
                        piece.getPieceColor().toString().charAt(0) + "" + piece.toString().toLowerCase() + ".gif"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(icon);
                this.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        sourcePiece = null;
                    }
                    if (isLeftMouseButton(e)) {
                        sourcePiece = piece;
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }
    }
    public Piece getSourcePiece() {
        return sourcePiece;
    }

    public void setSourcePiece(Piece sourcePiece) {
        this.sourcePiece = sourcePiece;
    }
}

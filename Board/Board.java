package Board;
import Pieces.*;
import Player.BlackPlayer;
import Player.Player;
import Player.WhitePlayer;
import java.util.*;

public class Board {

    private final List<Tile> gameBoard;
    private final List<Piece> activeBlackPieces;
    private final List<Piece> activeWhitePieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.activeBlackPieces = calculateActivePieces(this.gameBoard, Color.black);
        this.activeWhitePieces = calculateActivePieces(this.gameBoard, Color.white);
        this.blackPlayer = new BlackPlayer(this);
        this.whitePlayer = new WhitePlayer(this);
        this.currentPlayer = builder.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    public List<Piece> getActiveBlackPieces() {
        return activeBlackPieces;
    }

    public List<Piece> getActiveWhitePieces() {
        return activeWhitePieces;
    }

    private List<Piece> calculateActivePieces(final List<Tile> gameBoard, final Color color) {
        List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            final Piece piece = tile.getPiece();
            if (piece != null && piece.getPieceColor() == color) {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            output.append(gameBoard.get(i).toString());
        }
        return output.toString();
    }

    private List<Tile> createGameBoard(Builder builder) {
        final Tile[] tiles = new Tile[25];
        for (int i = 0; i < 25; i++) {
            tiles[i] = Tile.createTile(i + 1, builder.boardConfig.get(i + 1));
        }
        return Arrays.asList(tiles);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // black side
        builder.setPiece(new King(1, Color.white, PieceStat.notUpdated));
        builder.setPiece(new GoldGeneral(2, Color.white, PieceStat.notUpdated));
        builder.setPiece(new SilverGeneral(3, Color.white, PieceStat.notUpdated));
        builder.setPiece(new Bishop(4, Color.white, PieceStat.notUpdated));
        builder.setPiece(new Rook(5, Color.white, PieceStat.notUpdated));
        builder.setPiece(new Pawn(6, Color.white, PieceStat.notUpdated));
        // black side
        builder.setPiece(new King(25, Color.black, PieceStat.notUpdated));
        builder.setPiece(new GoldGeneral(24, Color.black, PieceStat.notUpdated));
        builder.setPiece(new SilverGeneral(23, Color.black, PieceStat.notUpdated));
        builder.setPiece(new Bishop(22, Color.black, PieceStat.notUpdated));
        builder.setPiece(new Rook(21, Color.black, PieceStat.notUpdated));
        builder.setPiece(new Pawn(20, Color.black, PieceStat.notUpdated));
        builder.setMoveMaker(Color.black);
        return builder.build();
    }

    public List<Piece> getAllActivePieces() {
        List<Piece> activePieces = new ArrayList<>(this.getActiveWhitePieces());
        activePieces.addAll(this.getActiveBlackPieces());
        return activePieces;
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate - 1);
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }
}

package Player;
import Board.Board;
import Pieces.*;
import Board.Move;
import java.util.ArrayList;
import java.util.List;

public class WhitePlayer extends Player {
    protected static List<Piece> deadPieces = new ArrayList<>();

    public WhitePlayer (final Board board) {
        super(board);
    }

    @Override
    public Color getPieceColor() {
        return Color.white;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }
    @Override
    public List<Move> calculatePlayerLegalMoves() {
        List<Move> playerLegalMoves = new ArrayList<>();
        for (Piece piece : this.board.getActiveWhitePieces()) {
            playerLegalMoves.addAll(piece.calculateLegalMoves(board));
        }
        return playerLegalMoves;
    }

    @Override
    public void addDeadPieces(Player player, Piece piece) {
        piece.setPieceColor(player.getPieceColor());
        piece.setPieceStat(PieceStat.notUpdated);
        deadPieces.add(piece);
    }
    @Override
    public String deadPiecesToString() {
        StringBuilder toString = new StringBuilder();
        for (Piece piece : deadPieces) {
            toString.append(piece.toString());
        }
        return toString.toString();
    }

    @Override
    public List<Piece> getDeadPieces() {
        return deadPieces;
    }
}

package Player;
import Board.Board;
import Pieces.Color;
import Board.Move;
import Pieces.Piece;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final List<Move> legalMoves;

    public Player(final Board board) {
        this.board = board;

        this.legalMoves = calculatePlayerLegalMoves();
    }

    public abstract Color getPieceColor();
    public abstract Player getOpponent();
    public abstract List<Move> calculatePlayerLegalMoves();
    public abstract void addDeadPieces(Player player, Piece piece);
    public abstract String deadPiecesToString();
    public abstract List<Piece> getDeadPieces();
}

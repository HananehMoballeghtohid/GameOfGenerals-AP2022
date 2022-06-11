package Pieces;
import Board.Move;
import java.util.List;
import Board.Board;

public abstract class Piece {
    protected final int piecePosition;
    protected Color pieceColor;
    protected final boolean isFirstMove;
    protected final PieceType pieceType;
    protected PieceStat pieceStat;

    public void setPieceStat(PieceStat pieceStat) {
       this.pieceStat = pieceStat;
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    public Piece(final int piecePosition, final Color pieceColor, final PieceType pieceType, PieceStat pieceStat) {
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
        this.pieceType = pieceType;
        this.pieceStat = pieceStat;
    }

    public PieceStat getPieceStat() {
        return pieceStat;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public abstract Piece movePiece(Move move);

    public abstract List<Move> calculateLegalMoves(final Board board);

    public abstract String toString();

    public int getPiecePosition() {
        return piecePosition;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public abstract Piece addPiece(Piece piece, int destinationCoordinate);
}


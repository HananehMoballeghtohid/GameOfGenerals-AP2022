package Board;
import Pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public Move(Board board, Piece movedPiece, int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public abstract Board execute();

    public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
        for (Piece piece : board.getAllActivePieces()) {
            for (Move move : piece.calculateLegalMoves(board)) {
                if (move.getMovedPiece().getPiecePosition() == currentCoordinate) {
                    if (move.getDestinationCoordinate() == destinationCoordinate) {
                        if (move.getMovedPiece().getPieceColor() == board.getCurrentPlayer().getPieceColor()) {
                            return move;
                        }
                    }
                }
            }
        }
        return null;
    }

}

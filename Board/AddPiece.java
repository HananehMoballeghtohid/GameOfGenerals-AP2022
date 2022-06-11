package Board;

import Pieces.Color;
import Pieces.Piece;

public final class AddPiece extends Move {

    public AddPiece(Board board, Piece movedPiece, int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public Board execute() {
        final Builder builder = new Builder();
        for (Piece piece : board.getActiveBlackPieces()) {
            if (!piece.equals(getMovedPiece())) {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : board.getActiveWhitePieces()) {
            if (!piece.equals(getMovedPiece())) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.getMovedPiece().addPiece(this.getMovedPiece(), destinationCoordinate));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getPieceColor());
        if (this.getMovedPiece().getPieceColor() == Color.black) {
            board.getBlackPlayer().getDeadPieces().remove(this.getMovedPiece());
        } else {
            board.getWhitePlayer().getDeadPieces().remove(this.getMovedPiece());
        }
        return builder.build();
    }
}

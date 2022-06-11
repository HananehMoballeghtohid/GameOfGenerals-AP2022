package Board;

import Pieces.Piece;

public final class MajorMove extends Move {
    public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
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
        builder.setPiece(this.getMovedPiece().movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getPieceColor());
        return builder.build();
    }
}

package Board;

import Pieces.Color;
import Pieces.Piece;

public final class AttackMove extends Move {
    final Piece attackedPiece;
    public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate);
        this.attackedPiece = attackedPiece;
    }

    @Override
    public Board execute() {
        final Builder builder = new Builder();
        for (Piece piece : board.getActiveBlackPieces()) {
            if (!piece.equals(getMovedPiece()) && !piece.equals(attackedPiece)) {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : board.getActiveWhitePieces()) {
            if (!piece.equals(getMovedPiece()) && !piece.equals(attackedPiece)) {
                builder.setPiece(piece);
            }
        }
        if (attackedPiece.getPieceColor() == Color.black) {
            board.getWhitePlayer().addDeadPieces(board.getCurrentPlayer(), attackedPiece);
            board.getAllActivePieces().remove(attackedPiece);
            board.getActiveBlackPieces().remove(attackedPiece);
        } else {
            board.getBlackPlayer().addDeadPieces(board.getCurrentPlayer(), attackedPiece);
            board.getAllActivePieces().remove(attackedPiece);
            board.getActiveWhitePieces().remove(attackedPiece);
        }
        builder.setPiece(this.getMovedPiece().movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getPieceColor());
        return builder.build();
    }
}

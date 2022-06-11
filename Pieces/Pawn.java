package Pieces;
import Board.Board;
import Board.Move;
import Board.MajorMove;
import Board.AttackMove;
import Board.Tile;
import java.util.ArrayList;
import java.util.List;
import Board.BoardUtils;

public class Pawn extends Piece {

    private final static int candidateMoveCoordinate = 5;
    private static final PieceType pieceType = PieceType.Pawn;

    public Pawn(int piecePosition, Color pieceColor, PieceStat pieceStat) {
        super(piecePosition, pieceColor, pieceType, pieceStat);
    }
    @Override
    public Pawn addPiece(Piece piece, int destinationCoordinate) {
        return new Pawn(destinationCoordinate, piece.getPieceColor(), PieceStat.notUpdated);
    }
    @Override
    public String toString() {
        if (this.getPieceColor() == Color.black) {
            return "p";
        } else {
            return "P";
        }
    }

    @Override
    public Piece movePiece(Move move) {
        if (this.pieceStat == PieceStat.Updated) {
            return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
        } else {
            if (this.getPieceColor().isInZone(move.getDestinationCoordinate())) {
                return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
            } else {
                return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.notUpdated);
            }
        }
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMove = new ArrayList<>();
        if (this.pieceStat == PieceStat.notUpdated) {
            int destinationCoordinate = this.piecePosition + candidateMoveCoordinate*this.getPieceColor().getDirection();
            if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                final Tile destinationTile = board.getTile(destinationCoordinate);
                if (!destinationTile.isTileOccupied()) {
                    legalMove.add(new MajorMove(board, this, destinationCoordinate));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                    if (!this.pieceColor.equals(pieceAtDestinationColor)) {
                        legalMove.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                    }
                }
            }
        } else {
            int[] candidateMoveCoordinate1 = {6, 4, 1, -1, 5, -5};
            for (final int destinationVector : candidateMoveCoordinate1) {
                int destinationCoordinate = this.piecePosition + destinationVector*this.getPieceColor().getDirection();
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    if (isFifthColumnException(this.piecePosition, destinationVector, this.getPieceColor()) ||
                            isFirstColumnException(this.piecePosition, destinationVector, this.getPieceColor())) {
                        continue;
                    }
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMove.add(new MajorMove(board, this, destinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.getPieceColor() != pieceAtDestinationColor) {
                            legalMove.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                    }
                }
            }
        }
        return legalMove;
    }
    private static boolean isFirstColumnException (final int currentPosition, final int destinationVector, final Color pieceColor){
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == -1 || destinationVector == 4);
        } else {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == 1 || destinationVector == 6);
        }
    }
    private static boolean isFifthColumnException (final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 6 || destinationVector == 1);
        } else {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 4 || destinationVector == -1);
        }
    }
}

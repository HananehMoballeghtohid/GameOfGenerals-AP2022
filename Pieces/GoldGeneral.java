package Pieces;
import Board.Board;
import Board.Move;
import Board.Tile;
import java.util.ArrayList;
import java.util.List;
import Board.BoardUtils;
import Board.MajorMove;
import Board.AttackMove;

public class GoldGeneral extends Piece {

    private static final int[] candidateMoveCoordinate = {6, 4, 1, -1, 5, -5};
    private static final PieceType pieceType = PieceType.GoldGeneral;

    public GoldGeneral(int piecePosition, Color pieceColor, PieceStat pieceStat) {
        super(piecePosition, pieceColor, pieceType, pieceStat);
    }

    @Override
    public String toString() {
        if (this.getPieceColor() == Color.black) {
            return "g";
        } else {
            return "G";
        }
    }

    @Override
    public GoldGeneral addPiece(Piece piece, int destinationCoordinate) {
        return new GoldGeneral(destinationCoordinate, piece.getPieceColor(), PieceStat.notUpdated);
    }

    @Override
    public GoldGeneral movePiece(Move move) {
        return new GoldGeneral(move.getDestinationCoordinate() , move.getMovedPiece().getPieceColor(), PieceStat.notUpdated);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        for (final int destinationVector : candidateMoveCoordinate) {
            int destinationCoordinate = this.piecePosition + destinationVector*this.getPieceColor().getDirection();
            if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                if (isFifthColumnException(this.piecePosition, destinationVector, this.getPieceColor()) ||
                        isFirstColumnException(this.piecePosition, destinationVector, this.getPieceColor())) {
                    continue;
                }
                final Tile destinationTile = board.getTile(destinationCoordinate);
                if (!destinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                    if (this.getPieceColor() != pieceAtDestinationColor) {
                        legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
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

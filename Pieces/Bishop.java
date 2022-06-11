package Pieces;
import Board.Board;
import Board.Move;
import Board.Tile;
import Board.BoardUtils;
import java.util.ArrayList;
import java.util.List;
import Board.AttackMove;
import Board.MajorMove;

public class Bishop extends Piece {

    private static final int[] candidateMoveVector = {6, 4};
    private static final PieceType pieceType = PieceType.Bishop;
    private static PieceStat pieceStat;

    public Bishop(int piecePosition, Color pieceColor, PieceStat pieceStat) {
        super(piecePosition, pieceColor, pieceType, pieceStat);
    }

    @Override
    public String toString() {
        if (this.pieceColor == Color.black) {
            return "b";
        } else {
            return "B";
        }
    }

    public PieceStat getPieceStat() {
        return pieceStat;
    }

    public void setPieceStat(PieceStat pieceStat) {
        Bishop.pieceStat = pieceStat;
    }

    @Override
    public Bishop addPiece(Piece piece, int destinationCoordinate) {
        return new Bishop(destinationCoordinate, piece.getPieceColor(), PieceStat.notUpdated);
    }

    @Override
    public Piece movePiece(Move move) {
        if (pieceStat == PieceStat.Updated) {
            return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
        } else {
            if (this.getPieceColor().isInZone(move.getDestinationCoordinate())) {
                return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
            } else {
                return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.notUpdated);
            }
        }
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        List<Move> legalMoves = new ArrayList<>();
        if (pieceStat == PieceStat.notUpdated) {
            for (final int destinationVector : candidateMoveVector) {
                int position = this.piecePosition;
                int destinationCoordinate;
                while (BoardUtils.isValidTileCoordinate(position + destinationVector*this.getPieceColor().getDirection())) {
                    destinationCoordinate = position + destinationVector*this.getPieceColor().getDirection();
                    if (isFirstColumnException(position, destinationVector, this.pieceColor) ||
                            isFifthColumnException(position, destinationVector, this.pieceColor)) {
                        break;
                    }
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                        position = destinationCoordinate;
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.getPieceColor() != pieceAtDestinationColor) {
                            legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        } else {
            int[] candidateMoveVector1 = {6, 4, -4, -6};
            int[] candidateMoveVector2 = {1, -1, +5, -5};
            for (final int destinationVector : candidateMoveVector1) {
                int position = this.piecePosition;
                int destinationCoordinate;
                while (BoardUtils.isValidTileCoordinate(position + destinationVector*this.getPieceColor().getDirection())) {
                    destinationCoordinate = position + destinationVector*this.getPieceColor().getDirection();
                    if (isFirstColumnException(position, destinationVector, this.pieceColor) ||
                            isFifthColumnException(position, destinationVector, this.pieceColor)) {
                        break;
                    }
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                        position = destinationCoordinate;
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceAtDestinationColor) {
                            legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }

                }
            }
            for (final int destinationVector : candidateMoveVector2) {
                int destinationCoordinate = this.piecePosition + destinationVector;
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    if (isFirthColumnException2(destinationCoordinate, destinationVector, this.pieceColor) ||
                            isFifthColumnException2(destinationCoordinate, destinationVector, this.pieceColor)) {
                        continue;
                    }
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceAtDestinationColor) {
                            legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                    }

                }
            }
        }
        return legalMoves;
    }


    private static boolean isFirstColumnException(final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == 4 || destinationVector == -6);
        } else {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == 6 || destinationVector == -4);
        }
    }
    private static boolean isFifthColumnException(final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 6 || destinationVector == -4);
        } else {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 4 || destinationVector == -6);
        }
    }
    private static boolean isFirthColumnException2(final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == -1);
        } else {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == 1);
        }
    }
    private static boolean isFifthColumnException2(final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == 1);
        } else {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == -1);
        }
    }
}

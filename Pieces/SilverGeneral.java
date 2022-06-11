package Pieces;
import Board.BoardUtils;
import Board.Tile;
import Board.Board;
import Board.Move;
import Board.MajorMove;
import java.util.ArrayList;
import java.util.List;
import Board.AttackMove;

public class SilverGeneral extends Piece {
    private static final int[] candidateMoveCoordinate = {6, 4, -6, -4, 5};
    private static final PieceType pieceType = PieceType.SilverGeneral;

    public SilverGeneral(int piecePosition, Color pieceColor, PieceStat pieceStat) {
        super(piecePosition, pieceColor, pieceType, pieceStat);
    }

    @Override
    public SilverGeneral addPiece(Piece piece, int destinationCoordinate) {
        return new SilverGeneral(destinationCoordinate, piece.getPieceColor(), PieceStat.notUpdated);
    }

    @Override
    public String toString() {
        if (this.pieceColor == Color.black) {
            return "s";
        } else {
            return "S";
        }
    }

    @Override
    public Piece movePiece(Move move) {
        if (this.pieceStat == PieceStat.Updated) {
            return new SilverGeneral(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
        } else {
            if (this.getPieceColor().isInZone(move.getDestinationCoordinate())) {
                return new SilverGeneral(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
            } else {
                return new SilverGeneral(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.notUpdated);
            }
        }
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        if (this.pieceStat == PieceStat.notUpdated) {
            for (final int destinationVector : candidateMoveCoordinate) {
                int destinationCoordinate = this.piecePosition + destinationVector*this.getPieceColor().getDirection();
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    if (isFifthColumnException1(this.piecePosition, destinationVector, this.getPieceColor()) ||
                            isFirstColumnException1(this.piecePosition, destinationVector, this.getPieceColor())) {
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
        } else {
            int[] candidateMoveCoordinate = {1, 2, -1, -2, 4, 5, 6, 8, 10, 12, -4, -5, -6, -8, -10, -12 };
            for (final int destinationVector : candidateMoveCoordinate) {
                int destinationCoordinate = this.piecePosition + destinationVector*this.getPieceColor().getDirection();
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    if (isFifthColumnException(this.piecePosition, destinationVector) ||
                            isFirstColumnException(this.piecePosition, destinationVector) ||
                            isForthColumnException(this.piecePosition, destinationVector) ||
                            isSecondColumnException(this.piecePosition, destinationVector)) {
                        continue;
                    }
                    if (isPieceInTheWay(destinationVector, this.piecePosition, board)) {
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
        }
        return legalMoves;
    }
    private static boolean isFirstColumnException1 (final int currentPosition, final int destinationVector, final Color pieceColor){
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == -6 || destinationVector == 4);
        } else {
            return BoardUtils.firstColumn[currentPosition] && (destinationVector == -4 || destinationVector == 6);
        }
    }
    private static boolean isFifthColumnException1 (final int currentPosition, final int destinationVector, final Color pieceColor) {
        if (pieceColor.equals(Color.white)) {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 6 || destinationVector == -4);
        } else {
            return BoardUtils.fifthColumn[currentPosition] && (destinationVector == 4 || destinationVector == -6);
        }
    }
    private boolean isPieceInTheWay(int destinationVector, int piecePosition, final Board board) {
        if (this.getPieceColor() == Color.white) {
            if (destinationVector == 2) {
                if (board.getTile(1 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -2) {
                if (board.getTile(-1 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 10) {
                if (board.getTile(5 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -10) {
                if (board.getTile(-5 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 12) {
                if (board.getTile(6 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -12) {
                if (board.getTile(-6 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 8) {
                if (board.getTile(4 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -8) {
                return board.getTile(-4 + piecePosition).isTileOccupied();
            }
        } else {
            if (destinationVector == -2) {
                if (board.getTile(1 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 2) {
                if (board.getTile(-1 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -10) {
                if (board.getTile(5 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 10) {
                if (board.getTile(-5 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -12) {
                if (board.getTile(6 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 12) {
                if (board.getTile(-6 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == -8) {
                if (board.getTile(4 + piecePosition).isTileOccupied()) {
                    return true;
                }
            }
            if (destinationVector == 8) {
                return board.getTile(-4 + piecePosition).isTileOccupied();
            }
        }
        return false;
    }

    private boolean isSecondColumnException(int piecePosition, int destinationVector) {
        if (this.getPieceColor() == Color.white) {
            return BoardUtils.secondColumn[piecePosition] && (destinationVector == -2 || destinationVector == 8 || destinationVector == -12);
        } else {
            return BoardUtils.secondColumn[piecePosition] && (destinationVector == 2 || destinationVector == -8 || destinationVector == 12);
        }
    }

    private boolean isForthColumnException(int piecePosition, int destinationVector) {
        if (this.getPieceColor() == Color.white) {
            return BoardUtils.forthColumn[piecePosition] && (destinationVector == 2 || destinationVector == -8 || destinationVector == 12);
        } else {
            return BoardUtils.forthColumn[piecePosition] && (destinationVector == -2 || destinationVector == 8 || destinationVector == -12);
        }
    }

    private boolean isFirstColumnException(int piecePosition, int destinationVector) {
        if (this.getPieceColor() == Color.white) {
            return BoardUtils.firstColumn[piecePosition] && (destinationVector == -1
                    || destinationVector == -2 || destinationVector == 4|| destinationVector == 8
                    || destinationVector == -6 || destinationVector == -12);
        } else {
            return BoardUtils.firstColumn[piecePosition] && (destinationVector == 1
                    || destinationVector == 2 || destinationVector == -4|| destinationVector == -8
                    || destinationVector == 6 || destinationVector == 12);
        }
    }

    private boolean isFifthColumnException(int piecePosition, int destinationVector) {
        if (this.getPieceColor() == Color.white) {
            return BoardUtils.fifthColumn[piecePosition] && (destinationVector == 1
                    || destinationVector == 2 || destinationVector == -4|| destinationVector == -8
                    || destinationVector == 6 || destinationVector == 12);
        } else {
            return BoardUtils.fifthColumn[piecePosition] && (destinationVector == -1
                    || destinationVector == -2 || destinationVector == 4|| destinationVector == 8
                    || destinationVector == -6 || destinationVector == -12);
        }
    }
}

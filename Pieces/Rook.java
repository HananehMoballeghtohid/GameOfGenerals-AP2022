package Pieces;
import Board.Board;
import Board.Move;
import Board.BoardUtils;
import java.util.ArrayList;
import java.util.List;
import Board.Tile;
import Board.MajorMove;
import Board.AttackMove;

public class Rook extends Piece {
    private static final PieceType pieceType = PieceType.Rook;
    public Rook(int piecePosition, Color pieceColor, PieceStat pieceStat) {
        super(piecePosition, pieceColor, pieceType, pieceStat);
    }

    @Override
    public String toString() {
        if (this.pieceColor == Color.black) {
            return "l";
        } else {
            return "L";
        }
    }

    @Override
    public Piece movePiece(Move move) {
        if (this.pieceStat == PieceStat.Updated) {
            return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
        } else {
            if (this.getPieceColor().isInZone(move.getDestinationCoordinate())) {
                return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.Updated);
            } else {
                return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), PieceStat.notUpdated);
            }
        }
    }

    @Override
    public Rook addPiece(Piece piece, int destinationCoordinate) {
        return new Rook(destinationCoordinate, piece.getPieceColor(), PieceStat.notUpdated);
    }
    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        if (this.pieceStat == PieceStat.notUpdated) {
            int availableDestination = 5;
            for (int i = 1; i < 5; i++) {
                int destinationCoordinate = this.piecePosition + this.pieceColor.getDirection() * availableDestination * i;
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceAtDestinationColor) {
                            legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            int[] availableDestination = {1, -1, 5, -5};
            for (int destination : availableDestination ) {
                for (int i = 1; i < 5; i++) {
                    int destinationCoordinate = this.piecePosition + this.pieceColor.getDirection() * destination * i;
                    if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                        final Tile destinationTile = board.getTile(destinationCoordinate);
                        if (!destinationTile.isTileOccupied()) {
                            legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                        } else {
                            final Piece pieceAtDestination = destinationTile.getPiece();
                            final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                            if (this.pieceColor != pieceAtDestinationColor) {
                                legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtDestination));
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }
}

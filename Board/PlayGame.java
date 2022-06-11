package Board;
import Pieces.Piece;
import Pieces.PieceType;
import java.util.Scanner;

public class PlayGame {
    public PlayGame(Board board) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String pieceLetter;
            String gridFrom;
            String gridTo;
            int currentPosition;
            int nextPosition;
            pieceLetter = scanner.next();
            if (pieceLetter.equals("0")) {
                System.exit(0);
            }
            gridFrom = scanner.next();
            gridTo = scanner.next();
            currentPosition = Integer.parseInt(gridFrom) / 10 + (Integer.parseInt(gridFrom) % 10 - 1) * (5);
            nextPosition = Integer.parseInt(gridTo) / 10 + (Integer.parseInt(gridTo) % 10 - 1) * (5);
            if (gridFrom.equals("00")) {
                for (Piece piece : board.getCurrentPlayer().getDeadPieces()) {
                    if (pieceLetter.equals(piece.toString())) {
                        if (nextPosition >= 1 && nextPosition <= 25) {
                            if (!board.getTile(nextPosition).isTileOccupied()) {
                                AddPiece addPiece = new AddPiece(board, piece, nextPosition);
                                board = addPiece.execute();
                                break;
                            }
                        }
                    }
                }
            } else {
                for (Piece piece : board.getAllActivePieces()) {
                    if (piece.toString().equals(pieceLetter)) {
                        if (board.getCurrentPlayer().getPieceColor() == piece.getPieceColor()) {
                            if (currentPosition == piece.getPiecePosition()) {
                                for (Move move : piece.calculateLegalMoves(board)) {
                                    if (move.getDestinationCoordinate() == nextPosition) {
                                        board = move.execute();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(board.toString());
            System.out.println(board.getBlackPlayer().deadPiecesToString());
            System.out.println(board.getWhitePlayer().deadPiecesToString());
            for (Piece piece : board.getCurrentPlayer().getOpponent().getDeadPieces()) {
                if (piece.getPieceType() == PieceType.King) {
                    System.out.println(board.getCurrentPlayer().getOpponent().getPieceColor() + " wins!");
                    System.exit(0);
                }
            }
            for (Piece piece : board.getCurrentPlayer().getOpponent().getDeadPieces()) {
                if (piece.getPieceType() == PieceType.King) {
                    System.out.println(board.getCurrentPlayer().getOpponent().getPieceColor() + " wins!");
                    System.exit(0);
                }
            }
        }
    }
}

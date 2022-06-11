import Board.Board;
import Board.PlayGame;

public class Main {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        new PlayGame(board);
    }
}
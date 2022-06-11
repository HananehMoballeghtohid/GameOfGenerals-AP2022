package Board;

public class BoardUtils {
    public static final boolean[] firstColumn = inItColumn(1);
    public static final boolean[] secondColumn = inItColumn(2);
    public static final boolean[] thirdColumn = inItColumn(3);
    public static final boolean[] forthColumn = inItColumn(4);
    public static final boolean[] fifthColumn = inItColumn(5);

    public static final boolean[] firstRow = inItRow(1);
    public static final boolean[] secondRow = inItRow(6);
    public static final boolean[] thirdRow = inItRow(11);
    public static final boolean[] forthRow = inItRow(16);
    public static final boolean[] fifthRow = inItRow(21);

    private static boolean[] inItRow(int rowNumber) {
        final boolean[] row = new boolean[26];
        for (int i = 0; i < 5; i++) {
            row[rowNumber + i] = true;
        }
        return row;
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 1 && coordinate <= 25;
    }
    private static boolean[] inItColumn(int columnNumber) {
        final boolean[] column = new boolean[26];
        do {
            column[columnNumber] = true;
            columnNumber += 5;
        } while (columnNumber < 26);
        return column;
    }
}

package Board;

import Pieces.Piece;

public final class EmptyTile extends Tile {

    public EmptyTile(int tileCoordinate) {
        super(tileCoordinate);
    }

    @Override
    public boolean isTileOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
    @Override
    public String toString() {
        return "-";
    }
}

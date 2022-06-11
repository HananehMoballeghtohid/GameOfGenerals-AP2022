package Pieces;

public enum Color {
    black {
        @Override
        int getDirection() {
            return -1;
        }

        @Override
        boolean isInZone(int nextPosition) {
            return nextPosition <= 10;
        }
    },

    white {
        @Override
        int getDirection() {
            return 1;
        }

        @Override
        boolean isInZone(int nextPosition) {
            return nextPosition >= 16;
        }
    };

    abstract int getDirection();
    abstract boolean isInZone(int nextPosition);
}

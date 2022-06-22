package game.board;

import java.util.Arrays;

public enum Cell {
    X("X"),
    O("0"),
    E(".");

    private final String str;

    private static final Cell[] arr = Arrays.copyOf(Cell.values(), Cell.values().length - 1);

    Cell(String str) {
        this.str = str;
    }

    public static Cell getCell(int index) {
        return arr[index];
    }

    public static int getSize() {
        return  arr.length;
    }

    public static int indexOf(Cell cell) {
        return Arrays.binarySearch(arr, cell);
    }

    @Override
    public String toString() {
        return str;
    }
}

package game.board;

public interface Position {
    Cell getTurn();

    boolean isValid(Move move);

    Cell getCell(int row, int column);

    int getK();

    int getRowCount();

    int getColCount();
}

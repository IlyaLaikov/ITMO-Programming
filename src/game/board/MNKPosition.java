package game.board;

public class MNKPosition implements Position {
    private final Position position;

    public MNKPosition(Position position) {
        this.position = position;
    }

    @Override
    public Cell getTurn() {
        return position.getTurn();
    }

    @Override
    public boolean isValid(Move move) {
        return position.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return position.getCell(r, c);
    }

    @Override
    public int getK() { return position.getK(); }

    @Override
    public int getRowCount() {
        return position.getRowCount();
    }

    @Override
    public int getColCount() {
        return position.getColCount();
    }

    @Override
    public String toString() {
        return position.toString();
    }
}

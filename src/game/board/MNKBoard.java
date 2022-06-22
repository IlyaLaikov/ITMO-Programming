package game.board;

public class MNKBoard extends AbstractMNKBoard {
    private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

    public MNKBoard(int m, int n, int k) {
        super(DIRECTIONS, m, n, k);
    }

    @Override
    public MNKBoard getBoard() {
        return new MNKBoard(getRowCount(), getColCount(), getK());
    }
}

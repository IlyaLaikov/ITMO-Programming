package game.board;

public class HexMNKBoard extends AbstractMNKBoard {
    private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {1, -1}};
    
    public HexMNKBoard(int m, int k) {
        super(DIRECTIONS, m, m, k);
    }

    @Override
    public HexMNKBoard getBoard() {
        return new HexMNKBoard(getRowCount(), getK());
    }
}

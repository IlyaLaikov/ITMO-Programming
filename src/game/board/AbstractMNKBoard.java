package game.board;

import game.game.GameResult;

import java.util.Arrays;

public abstract class AbstractMNKBoard implements Board, Position {
    private final Cell[][] field;
    private Cell turn;
    private final int k;
    private int cellCounter = 0;
    private final int[][] directions;

    protected AbstractMNKBoard(int[][] directions, int m, int n, int k) {
        this.directions = directions;
        field = new Cell[m][n];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        this.k = k;
    }

    @Override
    public Position getPosition() {
        return new MNKPosition(this);
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    private int oneDirectionCount(Move move, int xDelta, int yDelta) {
        int counter = 0;
        for (int x = move.getRow(), y = move.getCol();; x += xDelta, y += yDelta) {
            if (0 <= x && x < field.length && 0 <= y && y < field[0].length
                    && field[x][y] == field[move.getRow()][move.getCol()]) {
                ++counter;
            } else {
                break;
            }
        }
        return counter;
    }

    private boolean hasWinChainInDirection(Move move, int xDirection, int yDirection) {
        return oneDirectionCount(move, xDirection, yDirection) + oneDirectionCount(move, -xDirection, -yDirection)
                - 1 >= k;
    }

    private boolean checkWin(Move move) {
        for (int[] direction : directions) {
            if (hasWinChainInDirection(move, direction[0], direction[1])) {
                return true;
            }
        }
        return false;
    }

    private GameResult getResult(Move move) {
        for (int i = 0; i < 4; i++) {
            if (checkWin(move)) {
                return GameResult.WIN;
            }
        }
        return cellCounter == field.length * field[0].length ? GameResult.DRAW : GameResult.UNKNOWN;
    }

    @Override
    public GameResult makeMove(final Move move) {
        if (!isValid(move)) {
            return GameResult.LOSE;
        }

        field[move.getRow()][move.getCol()] = turn;
        cellCounter++;
        GameResult gameResult = getResult(move);
        turn = (turn == Cell.X ? Cell.O : Cell.X);
        return gameResult;
    }

    @Override
    public boolean isValid(final Move move) {
        return move != null
                && 0 <= move.getRow() && move.getRow() < field.length
                && 0 <= move.getCol() && move.getCol() < field[0].length
                && field[move.getRow()][move.getCol()] == Cell.E
                && move.getValue() == turn;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return field[r][c];
    }

    @Override
    public int getK() {
        return k;
    }

    @Override
    public int getRowCount() {
        return field.length;
    }

    @Override
    public int getColCount() {
        return field[0].length;
    }

    @Override
    public String toString() {
        int lenN = Integer.toString(field.length).length(), lenM = Integer.toString(field[0].length).length();
        final StringBuilder sb = new StringBuilder(" ".repeat(lenN + 1));
        for (int c = 0; c < field[0].length; c++) {
            sb.append(String.format("%" + lenM + "d ", c + 1));
        }
        for (int r = 0; r < field.length; r++) {
            sb.append("\n");
            sb.append(String.format("%" + lenN + "d ", (r + 1)));
            for (int c = 0; c < field[0].length; c++) {
                sb.append(String.format("%" + lenM + "s ", field[r][c].toString()));
            }
        }
        return sb.toString();
    }
}

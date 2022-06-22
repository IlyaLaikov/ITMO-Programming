package game.game;

import game.board.Board;
import game.players.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tournier {
    private final Board board;
    private final List<Player> players;
    private final int[][] stand;
    private final int[] results;

    private PrintStream out = System.out;

    public Tournier(final Board board, final Player... players) {
        this(board, Arrays.asList(players));
    }

    public Tournier(final Board board, final List<Player> playerList) {
        if (playerList.size() < 2) {
            throw new IllegalArgumentException("Too little players in list");
        }

        players = new ArrayList<>(playerList);
        this.stand = new int[playerList.size()][playerList.size()];
        this.results = new int[playerList.size()];
        this.board = board;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public int[] play() {
        return play(false);
    }

    public int[] play(boolean log) {
        for (int i = 0; i < players.size(); ++i) {
            for (int j = 0; j < players.size(); ++j) {
                if (i == j) {
                    continue;
                }
                TwoPlayerGame game = new TwoPlayerGame(
                        board.getBoard(),
                        players.get(i),
                        players.get(j));
                game.setOut(out);
                if (log) {
                    out.println("Play " + (i + 1) + " and " + (j + 1));
                }
                int result = game.play(log);
                stand[i][j] = result;
                switch (result) {
                    case 0:
                        results[i] += 1;
                        results[j] += 1;
                        break;
                    case 1:
                        results[i] += 3;
                        break;
                    case 2:
                        results[j] += 3;
                        break;
                    default:
                        throw new AssertionError("Unknown game result " + result);
                }
                if (log) {
                    out.println();
                }
            }
        }
        return results;
    }

    public int[][] getStand() {
        return stand;
    }

    public String getStringStand() {
        int len = Integer.toString(players.size()).length();
        StringBuilder sb = new StringBuilder(" ".repeat(len + 1));
        for (int c = 0; c < players.size(); c++) {
            sb.append(String.format("%" + len + "d ", c + 1));
        }
        for (int r = 0; r < players.size(); r++) {
            sb.append("\n");
            sb.append(String.format("%" + len + "d ", (r + 1)));
            for (int c = 0; c < players.size(); c++) {
                if (c == r) {
                    sb.append(String.format("%" + len + "s ", "X"));
                } else {
                    sb.append(String.format("%" + len + "s ", stand[r][c]));
                }
            }
        }
        return sb.toString();
    }

    public int[] getResults() {
        return results;
    }

    public String getStringResult() {
        int len = Integer.toString(players.size()).length();
        StringBuilder sb = new StringBuilder();
        for (int c = 0; c < players.size(); c++) {
            sb.append(String.format("%" + len + "d ", c + 1));
        }
        sb.append('\n');
        for (int c = 0; c < players.size(); c++) {
            sb.append(String.format("%" + len + "d ", results[c]));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getStringStand() + "\n\n" + getStringResult();
    }
}

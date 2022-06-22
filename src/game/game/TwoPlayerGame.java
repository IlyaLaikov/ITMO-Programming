package game.game;

import game.board.Board;
import game.board.Move;
import game.players.Player;

import java.io.PrintStream;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    private PrintStream out = System.out;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public int play() {
        return play(false);
    }

    public int play(boolean log) {
        while (true) {
            final int result1 = makeMove(player1, 1, log);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = makeMove(player2, 2, log);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            out.println();
            out.println("Player: " + no);
            out.println(move);
            out.println(board);
            out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return no;
            case LOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}

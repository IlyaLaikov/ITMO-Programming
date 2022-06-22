package game.players;

import game.board.Move;
import game.board.Position;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final PrintStream out;

    public HumanPlayer() {
        this(new Scanner(System.in), System.out);
    }

    public HumanPlayer(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public Move makeMove(Position position) {
        out.println();
        out.println("Current position");
        out.println(position);
        out.format("Enter you move for %s: ", position.getTurn());
        String arg1 = in.next(), arg2 = in.next();
        Move move;
        try {
            move = new Move(Integer.parseInt(arg1) - 1, Integer.parseInt(arg2) - 1, position.getTurn());
        } catch (NumberFormatException exc) {
            move = null;
        }
        while (!position.isValid(move)) {
            out.println();
            out.println("This move is invalid");
            out.format("Re-enter you move for %s: ", position.getTurn());
            arg1 = in.next();
            arg2 = in.next();
            try {
                move = new Move(Integer.parseInt(arg1) - 1, Integer.parseInt(arg2) - 1, position.getTurn());
            } catch (NumberFormatException exc) {
                move = null;
            }
        }
        return move;
    }
}

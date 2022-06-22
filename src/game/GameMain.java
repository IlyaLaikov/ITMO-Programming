package game;

import game.board.HexMNKBoard;
import game.game.Tournier;
import game.players.RandomPlayer;
import game.players.SequentialPlayer;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class GameMain {
    private static final String LOG_DEST = "log.out";
    private static final String RES_DEST = "results.out";

    public static void main(String[] args) {
        Tournier tour = new Tournier(
                new HexMNKBoard(3, 3),
                new SequentialPlayer(),
                new SequentialPlayer(),
                new RandomPlayer());

        try (PrintStream ps = new PrintStream(LOG_DEST)) {
            tour.setOut(ps);
            tour.play(true);
        } catch (FileNotFoundException exc) {
            tour.play(false);
        }

        try (PrintStream ps = new PrintStream(RES_DEST)) {
            ps.println(tour);
        } catch (FileNotFoundException exc) {
            System.out.println(tour);
        }
    }
}

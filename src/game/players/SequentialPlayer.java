package game.players;

import game.board.Move;
import game.board.Position;

public class SequentialPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        for (int r = 0; r < position.getRowCount(); r++) {
            for (int c = 0; c < position.getColCount(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}

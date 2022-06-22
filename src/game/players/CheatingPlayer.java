package game.players;

import game.board.AbstractMNKBoard;
import game.board.Move;
import game.board.Position;

public class CheatingPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        final AbstractMNKBoard board = (AbstractMNKBoard) position;
        Move first = null;
        for (int r = 0; r < position.getColCount(); r++) {
            for (int c = 0; c < position.getRowCount(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    if (first == null) {
                        first = move;
                    } else {
                        board.makeMove(move);
                    }
                }
            }
        }
        return first;
    }
}

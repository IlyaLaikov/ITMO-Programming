package game.players;

import game.board.Position;
import game.board.Move;

public interface Player {
    Move makeMove(Position position);
}

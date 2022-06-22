package game.board;

import game.game.GameResult;

public interface Board {
    Position getPosition();

    GameResult makeMove(Move move);

    Board getBoard();
}
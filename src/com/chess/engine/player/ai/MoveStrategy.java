package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;

public interface MoveStrategy {
	Move execute(Board board, Integer depth, BuildHandler handler);

}

package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class BluePlayerHandler<T extends BluePlayer> {
	private Class<T> playerClass;
	public BluePlayerHandler(Class<T> playerClass) {
		this.playerClass = playerClass;
	}
	
	public T createPlayer(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		try {
			return playerClass.getDeclaredConstructor(Board.class, Collection.class, Collection.class).newInstance(board, legalMoves, opponentMoves);
			} catch (Exception e) {
			throw new RuntimeException("Failed to create builder instance", e);
		}
	}
}

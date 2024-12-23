package com.chess.engine.player;

import java.lang.reflect.Constructor;
import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class WhitePlayerHandler<T extends WhitePlayer> {
	private Class<T> playerClass;
	public WhitePlayerHandler(Class<T> playerClass) {
		this.playerClass = playerClass;
	}
		
	public T createPlayer(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		try {
			Constructor<T> constructor = playerClass.getDeclaredConstructor(Board.class, Collection.class, Collection.class);
			constructor.setAccessible(true);
			return constructor.newInstance(board, legalMoves, opponentMoves);
			} catch (Exception e) {
				System.out.println(e.getCause());
				throw new RuntimeException("Failed to create builder instance", e);
		}
	}
}


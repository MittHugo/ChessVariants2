package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;

public class BuildHandler<T extends Builder> {
	private Class<T> builderClass;
	public BuildHandler(Class<T> builderClass) {
		this.builderClass = builderClass;
	}
		
	public T createBuilder() {
		try {
			return builderClass.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
			throw new RuntimeException("Failed to create builder instance", e);
		}
	}
}
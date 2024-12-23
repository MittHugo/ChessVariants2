package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;

public class StandardBoard extends Board {
	final static int boardDimension = 8;
	final static int numberOfRowsUsed = 2;
	public StandardBoard(Builder builder) {
		super(builder, boardDimension, boardDimension, true, true, true, true, numberOfRowsUsed);
	}

	public static Board createBoard() {
		final StandardBuilder builder = new StandardBuilder();
		Class<? extends Piece>[] classes = new Class[] { Rook.class, Knight.class, Bishop.class, Queen.class,
				King.class, Bishop.class, Knight.class, Rook.class };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 2, Pawn.class, numberOfRowsUsed);

		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class StandardBuilder extends Builder {
		@Override
		public Board build() {
			return new StandardBoard(this);
		}
	}

}

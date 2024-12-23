package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.variants.AlmostChess.AlmostChessBuilder;
import com.chess.engine.board.variants.Berolina.BerolinaBuilder;
import com.chess.engine.board.variants.Berolina.BerolinaPawn;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.gui.Table;
import com.chess.gui.Table.PlayerCount;

public class GrandChess extends Board {
	final static int boardDimension = 10;
	final static int numberOfRowsUsed = 3;
	public GrandChess(Builder builder) {
		super(builder, 10, 10, true, true, true, true,3);
	}

	public static Board createBoard() {
		final GrandBuilder builder = new GrandBuilder();
		// Black Layout
		Class<? extends Piece>[] classes = new Class[] { Rook.class, EmptyPiece.class, EmptyPiece.class,
				EmptyPiece.class, EmptyPiece.class, EmptyPiece.class, EmptyPiece.class, EmptyPiece.class,
				EmptyPiece.class, Rook.class, EmptyPiece.class, Knight.class, Bishop.class, Queen.class, King.class,
				Chancellor.class, Archbishop.class, Bishop.class, Knight.class, EmptyPiece.class};
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 3, Pawn.class, numberOfRowsUsed);

//		BoardUtils.buildBoard(classes, builder, 10, 10, 3, Pawn.class);
		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class GrandBuilder extends Builder {
		@Override
		public Board build() {
			return new GrandChess(this);
		}
	}

}

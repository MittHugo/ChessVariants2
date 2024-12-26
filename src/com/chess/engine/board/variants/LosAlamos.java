package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class LosAlamos extends Board {
	public LosAlamos(Builder builder) {
		super(builder, 6, 6, false, false, false, true);
	}
	
	public static Board createBoard() {
		final LosAlamosBuilder builder = new LosAlamosBuilder();
		Class<? extends Piece>[] blackLayout = new Class[]{
			    Rook.class, Queen.class, Knight.class, King.class, Archbishop.class, Knight.class, Chancellor.class,
			     Rook.class
			};
			
			// Building the board using the new layout
			BoardUtils.buildBoard(blackLayout, builder, 6, 6, 2, Pawn.class);
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	public static class LosAlamosBuilder extends Builder {
		@Override
		public Board build() {
			return new LosAlamos(this);
		}
	}
}


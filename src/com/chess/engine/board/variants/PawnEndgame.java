package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.King;

public class PawnEndgame extends Board {

	public PawnEndgame(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard() {
		final PawnEndgameBuilder builder = new PawnEndgameBuilder();
		// Black Layout
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		createPawnLine(builder, Alliance.BLACK, 2,8);
		//White Layout
		createPawnLine(builder, Alliance.WHITE, 7,8);
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}

	
	public static class PawnEndgameBuilder extends Builder {
		@Override
		public Board build() {
			return new PawnEndgame(this);
		}
	}
}

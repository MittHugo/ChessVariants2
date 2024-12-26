package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class PeasantRevolt extends Board{
	
	public PeasantRevolt(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard() {
		final PeasantRevoltBuilder builder = new PeasantRevoltBuilder();
		// Black Layout
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Knight(Alliance.BLACK, 2, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class PeasantRevoltBuilder extends Builder {
		@Override
		public Board build() {
			return new PeasantRevolt(this);
		}
	}
	
}

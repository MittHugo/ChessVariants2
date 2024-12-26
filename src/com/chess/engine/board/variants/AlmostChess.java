package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;

public class AlmostChess extends Board{
	
	public AlmostChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final AlmostChessBuilder builder = new AlmostChessBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        Knight.class, 
		        Bishop.class,
		        Chancellor.class,
		        King.class,
		        Bishop.class,
		        Knight.class,
		        Rook.class,
		    };
		BoardUtils.buildBoard(classes, builder, 8,8,2, Pawn.class);
//		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class AlmostChessBuilder extends Builder {
		@Override
		public Board build() {
			return new AlmostChess(this);
		}
	}
	
}

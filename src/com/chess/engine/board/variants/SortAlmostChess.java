package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class SortAlmostChess extends Board{
	
	public SortAlmostChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final SortAlmostBuilder builder = new SortAlmostBuilder();
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Chancellor(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
		builder.setPiece(new Rook(Alliance.WHITE, 63, true));
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class SortAlmostBuilder extends Builder {
		@Override
		public SortAlmostChess build() {
			return new SortAlmostChess(this);
		}
	}
	
}

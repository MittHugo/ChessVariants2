package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class Weak extends Board{
	
	public Weak(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final WeakBuilder builder = new WeakBuilder();
		// Black Layout
		builder.setPiece(new Knight(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Knight(Alliance.BLACK, 2, true));
		builder.setPiece(new Knight(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Knight(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Knight(Alliance.BLACK, 7, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 18, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 21, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 25, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 26, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 27, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 28, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 29, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 30, true));
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
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class WeakBuilder extends Builder {
		@Override
		public Board build() {
			return new Weak(this);
		}
	}
	
}

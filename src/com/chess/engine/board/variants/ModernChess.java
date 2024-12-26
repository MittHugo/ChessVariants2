package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class ModernChess extends Board{
	
	public ModernChess(Builder builder) {
		super(builder, 9, 9, true, true, true, true);
	}
	
	public static Board createBoard() {
		final ModernBuilder builder = new ModernBuilder();
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Archbishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 6, true));
		builder.setPiece(new Knight(Alliance.BLACK, 7, true));
		builder.setPiece(new Rook(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 16, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 17, true));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 63, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 64, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 65, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 66, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 67, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 68, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 69, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 70, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 71, true));
		builder.setPiece(new Rook(Alliance.WHITE, 72, true));
		builder.setPiece(new Knight(Alliance.WHITE, 73, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 74, true));
		builder.setPiece(new Queen(Alliance.WHITE, 75, true));
		builder.setPiece(new King(Alliance.WHITE, 76, true));
		builder.setPiece(new Archbishop(Alliance.WHITE, 77, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 78, true));
		builder.setPiece(new Knight(Alliance.WHITE, 79, true));
		builder.setPiece(new Rook(Alliance.WHITE, 80, true));
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class ModernBuilder extends Builder {
		@Override
		public Board build() {
			return new ModernChess(this);
		}
	}
	
}

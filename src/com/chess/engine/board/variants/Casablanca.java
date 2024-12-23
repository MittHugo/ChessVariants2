package com.chess.engine.board.variants;

import java.util.Random;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class Casablanca extends Board{
	
	public Casablanca(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard() {
		final CasablancaBuilder builder = new CasablancaBuilder();
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
		// Black Layout
        if(randomNumber == 1) {
        	builder.setPiece(new Rook(Alliance.BLACK, 0, true));
    		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
    		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
    		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
    		builder.setPiece(new King(Alliance.BLACK, 4, true));
    		builder.setPiece(new Rook(Alliance.BLACK, 6, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 20, true));
    		builder.setPiece(new Knight(Alliance.BLACK, 21, true));
    		builder.setPiece(new Queen(Alliance.WHITE, 23, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 26, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 35, true));
    		builder.setPiece(new Pawn(Alliance.BLACK, 36, true));
    		//White Layout
    		builder.setPiece(new Pawn(Alliance.WHITE, 40, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 42, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
    		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
    		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
    		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
    		builder.setPiece(new King(Alliance.WHITE, 60, true));
    		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
    		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
    		builder.setPiece(new Rook(Alliance.WHITE, 63, true));
        } else if(randomNumber == 2) {
			builder.setPiece(new Rook(Alliance.BLACK, 0, true));
			builder.setPiece(new Queen(Alliance.BLACK, 3, true));
			builder.setPiece(new Rook(Alliance.BLACK, 5, true));
			builder.setPiece(new King(Alliance.BLACK, 6, true));
			builder.setPiece(new Rook(Alliance.BLACK, 7, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
			builder.setPiece(new Bishop(Alliance.BLACK, 9, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
			builder.setPiece(new Knight(Alliance.BLACK, 11, true));
			builder.setPiece(new Bishop(Alliance.BLACK, 12, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 17, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 20, true));
			builder.setPiece(new Knight(Alliance.BLACK, 21, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 27, true));
			//White Layout
			builder.setPiece(new Pawn(Alliance.WHITE, 34, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 41, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 44, true));
			builder.setPiece(new Knight(Alliance.WHITE, 45, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 46, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
			builder.setPiece(new Bishop(Alliance.WHITE, 49, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
			builder.setPiece(new Bishop(Alliance.WHITE, 54, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
			builder.setPiece(new Rook(Alliance.WHITE, 56, true));
			builder.setPiece(new Knight(Alliance.WHITE, 57, true));
			builder.setPiece(new Queen(Alliance.WHITE, 59, true));
			builder.setPiece(new Rook(Alliance.WHITE, 61, true));
			builder.setPiece(new King(Alliance.WHITE, 62, true));
		} else {
			builder.setPiece(new Rook(Alliance.BLACK, 0, true));
			builder.setPiece(new Queen(Alliance.BLACK, 3, true));
			builder.setPiece(new King(Alliance.BLACK, 4, true));
			builder.setPiece(new Rook(Alliance.BLACK, 7, true));
			builder.setPiece(new Bishop(Alliance.BLACK, 11, true));
			builder.setPiece(new Bishop(Alliance.BLACK, 12, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 16, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 20, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 25, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 27, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 28, true));
			builder.setPiece(new Pawn(Alliance.BLACK, 29, true));
			//White Layout
			builder.setPiece(new Pawn(Alliance.WHITE, 37, true));
			builder.setPiece(new Knight(Alliance.WHITE, 42, true));
			builder.setPiece(new Queen(Alliance.WHITE, 44, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
			builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
			builder.setPiece(new King(Alliance.WHITE, 57, true));
			builder.setPiece(new Rook(Alliance.WHITE, 59, true));
			builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
			builder.setPiece(new Rook(Alliance.WHITE, 63, true));
		}
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class CasablancaBuilder extends Builder {
		@Override
		public Board build() {
			return new StandardBoard(this);
		}
	}
	
}

package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;

public class Displacement extends Board{
	
	public Displacement(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard() {
		final DisplacementChessBuilder builder = new DisplacementChessBuilder();
		// Define the piece classes for the major pieces
		Class<? extends Piece>[] majorPieceClasses = new Class[]{
		    Rook.class, 
		    Bishop.class, 
		    Bishop.class, 
		    Queen.class, 
		    King.class, 
		    Knight.class, 
		    Knight.class, 
		    Rook.class
		};

		// Use BoardUtils to set up the board
		BoardUtils.buildBoard(
		    majorPieceClasses, // Classes for major pieces
		    builder,           // The builder instance
		    8,                 // Number of tiles per row
		    8,                 // Number of rows
		    2,                 // Black pawn rank (row index 1)
		    Pawn.class         // Class for pawns
		);

		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class DisplacementChessBuilder extends Builder {
		@Override
		public Board build() {
			return new StandardBoard(this);
		}
	}
	
}

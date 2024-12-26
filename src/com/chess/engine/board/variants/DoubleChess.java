package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.engine.player.variants.DoubleBlackPlayer;
import com.chess.engine.player.variants.DoubleWhitePlayer;

public class DoubleChess extends Board{
	
	public DoubleChess(Builder builder) {
		super(builder, 12, 16, false, true, true, true, 
				new WhitePlayerHandler<>(DoubleWhitePlayer.class), new BlackPlayerHandler<>(DoubleBlackPlayer.class));
	}
	
	public static Board createBoard() {
		final DoubleBuilder builder = new DoubleBuilder();
	    // Define major piece classes for 16 columns
	    Class<? extends Piece>[] majorPieceClasses = new Class[] {
	        Rook.class, Knight.class, Bishop.class, Queen.class, 
	        King.class, Bishop.class, Knight.class, Rook.class,
	        Rook.class, Knight.class, Bishop.class, Queen.class,
	        King.class, Bishop.class, Knight.class, Rook.class
	    };

	    // Use buildBoard to set up the layout
	    BoardUtils.buildBoard(
	        majorPieceClasses,   // Major piece classes
	        builder,             // Builder instance
	        16,                  // Number of tiles per row (columns)
	        12,                  // Number of rows
	        2,                   // Black pawn row (row index 2)
	        Pawn.class           // Pawn class
	    );
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class DoubleBuilder extends Builder {
		@Override
		public Board build() {
			return new DoubleChess(this);
		}
	}
	
}

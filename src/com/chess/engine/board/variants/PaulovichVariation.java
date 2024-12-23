package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class PaulovichVariation extends Board{

	public PaulovichVariation(Builder builder) {
		super(builder, 8, 10, true, true, true, true);
	}

	public static Board createBoard() {
		final PaulovichVariationBuilder builder = new PaulovichVariationBuilder();
		// Black Layout
		builder.setPiece(new Chancellor(Alliance.BLACK, 0, true));
		builder.setPiece(new Rook(Alliance.BLACK, 1, true));
		builder.setPiece(new Knight(Alliance.BLACK, 2, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 3, true));
		builder.setPiece(new Archbishop(Alliance.BLACK, 4, true));
		builder.setPiece(new King(Alliance.BLACK, 5, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 6, true));
		builder.setPiece(new Knight(Alliance.BLACK, 7, true));
		builder.setPiece(new Rook(Alliance.BLACK, 8, true));
		builder.setPiece(new Queen(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 16, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 17, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 18, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 19, true));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 60, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 61, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 62, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 63, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 64, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 65, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 66, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 67, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 68, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 69, true));
		builder.setPiece(new Chancellor(Alliance.WHITE, 70, true));
		builder.setPiece(new Rook(Alliance.WHITE, 71, true));
		builder.setPiece(new Knight(Alliance.WHITE, 72, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 73, true));
		builder.setPiece(new Archbishop(Alliance.WHITE, 74, true));
		builder.setPiece(new King(Alliance.WHITE, 75, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 76, true));
		builder.setPiece(new Knight(Alliance.WHITE, 77, true));
		builder.setPiece(new Rook(Alliance.WHITE, 78, true));
		builder.setPiece(new Queen(Alliance.WHITE, 79, true));
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class PaulovichVariationBuilder extends Builder {
		@Override
		public Board build() {
			return new PaulovichVariation(this);
		}
	}
	
}

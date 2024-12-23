package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Random;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class ReallyBadChess extends Board{
	
	public ReallyBadChess(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	private static Piece toPiece(String name, Alliance alliance, int pos) {
		if(name == "N") {
			return new Knight(alliance, pos, true);
		} else if(name == "Q") {
			return new Queen(alliance, pos, true);
		} else if(name == "R") {
			return new Rook(alliance, pos, true);
		} else if(name == "B") {
			return new Bishop(alliance, pos, true);
		} else if(name == "P") {
			return new Pawn(alliance, pos, true);
		}else {
			throw new RuntimeException("Should not be here");
		}
	}
	
	public static Board createBoard() {
		final ReallyBadBuilder builder = new ReallyBadBuilder();
		
		
		ArrayList<String> names = new ArrayList<>();
		names.add("N");
		names.add("Q");
		names.add("R");
		names.add("B");
		names.add("P");
		Random random = new Random();
		String letter;
		
		// Black Layout
		for(int i = 0; i <=15; i++) {
			if(i != 4) {
				letter = names.get(random.nextInt(names.size()));
				builder.setPiece(toPiece(letter, Alliance.BLACK, i));
			} else {
				builder.setPiece(new King(Alliance.BLACK, 4, true));
			}
		}
		for(int i = 48; i <=63; i++) {
			if(i != 60) {
				letter = names.get(random.nextInt(names.size()));
				builder.setPiece(toPiece(letter, Alliance.WHITE, i));
			} else {
				builder.setPiece(new King(Alliance.WHITE, 60, true));
			}
		}
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class ReallyBadBuilder extends Builder {
		@Override
		public Board build() {
			return new ReallyBadChess(this);
		}
	}
	
}

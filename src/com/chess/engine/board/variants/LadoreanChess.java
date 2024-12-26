package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class LadoreanChess extends Board{

	public LadoreanChess(Builder builder) {
		super(builder, 8, 10, true, true, true, true);
	}

	public static Board createBoard() {
		final LadoreanChessBuilder builder = new LadoreanChessBuilder();
		// Black Layout
		// Black pieces layout
		Class<? extends Piece>[] blackLayout = new Class[]{
		    Rook.class, Bishop.class, Queen.class, Knight.class, King.class, Archbishop.class, Knight.class, Chancellor.class,
		    Bishop.class, Rook.class
		};
		
		// Building the board using the new layout
		BoardUtils.buildBoard(blackLayout, builder, 10, 8, 2, Pawn.class);
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class LadoreanChessBuilder extends Builder {
		@Override
		public Board build() {
			return new LadoreanChess(this);
		}
	}
	
}

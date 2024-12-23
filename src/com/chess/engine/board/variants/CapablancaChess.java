package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.variants.Berolina.BerolinaPawn;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class CapablancaChess extends Board{

	public CapablancaChess(Builder builder) {
		super(builder, 8, 10, true, true, true, true);
	}

	public static Board createBoard() {
		final CapablancaChessBuilder builder = new CapablancaChessBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        Knight.class,
		        Archbishop.class,
		        Bishop.class,
		        Queen.class,
		        King.class,
		        Bishop.class,
		        Chancellor.class,
		        Knight.class,
		        Rook.class
		    };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.buildBoard(classes, builder, 10,8,2, Pawn.class);//,(Class<? extends Piece>)pawnClass
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class CapablancaChessBuilder extends Builder {
		@Override
		public Board build() {
			return new CapablancaChess(this);
		}
	}
	
}

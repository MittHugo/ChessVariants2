package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.variants.FalconHunterBoard.FalconHunterPawn;
import com.chess.engine.board.variants.GrossChess.Champion;
import com.chess.engine.board.variants.GrossChess.Vao;
import com.chess.engine.board.variants.GrossChess.Wizard;
import com.chess.engine.board.variants.Metamachy.Cannon;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class ActiveBoard extends Board {
	public ActiveBoard(Builder builder) {
		super(builder, 8, 9, true, true, true, true,2);
	}
	
	public static Board createBoard() {
		final ActiveBoardBuilder builder = new ActiveBoardBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        Knight.class, 
		        Bishop.class,
		        Queen.class,
		        King.class,
		        Bishop.class,
		        Knight.class,
		        Rook.class,
		        Queen.class,
		    };
			BoardUtils.betterBuildBoard(classes, builder, 9,8,2, Pawn.class,2);
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}

	public static class ActiveBoardBuilder extends Builder {
		@Override
		public Board build() {
			return new ActiveBoard(this);
		}
	}
}

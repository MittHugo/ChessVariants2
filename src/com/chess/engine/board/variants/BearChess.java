package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.board.variants.FalconHunterBoard.*;

public class BearChess extends Board{
	
	public BearChess(Builder builder) {
		super(builder, 10, 10, true, true, true, true);
	}
	
	public static Board createBoard() {
		final BearBuilder builder = new BearBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        Knight.class,
		        Bishop.class,
		        Bear.class,
		        Queen.class,
		        King.class,
		        Bear.class,
		        Bishop.class,
		        Knight.class,
		        Rook.class
		    };
		    Class<? extends Piece> pawnClass =FalconHunterPawn.class;
			BoardUtils.buildBoard(classes, builder, 10,10,2,(Class<? extends Piece>)pawnClass);
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class BearBuilder extends Builder {
		@Override
		public Board build() {
			return new BearChess(this);
		}
	}
	public static class Bear extends Piece {
		public Bear(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(PieceType.BEAR,pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			final Vectors[] CANIDATE_MOVE_COORDINATES = {
				new Vectors(board.getNumberColumns()*2,  MotionType.Jump),
				new Vectors(-board.getNumberColumns()*2,  MotionType.Jump),
				new Vectors(-2,  MotionType.Jump),
				new Vectors(2, MotionType.Jump),
				new Vectors(-board.getNumberColumns()*2-2, MotionType.Jump), 
				new Vectors(-board.getNumberColumns()*2+2,  MotionType.Jump),
				new Vectors(board.getNumberColumns()*2-2,  MotionType.Jump),
				new Vectors(board.getNumberColumns()*2+2, MotionType.Jump),};
			final List<Move> legalMoves = new ArrayList<>();
			legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
			legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board, PieceUtils.pieceFromPiece(this, Knight.class)));
			return Collections.unmodifiableList(legalMoves);
		}
		
		@Override 
		public String toString() {
			return PieceType.BEAR.toString();
		}
		
		@Override
		public Bear movePiece(final Move move) {
			return new Bear(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
	}

	
}

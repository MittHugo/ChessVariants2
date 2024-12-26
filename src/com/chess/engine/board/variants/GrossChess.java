package com.chess.engine.board.variants;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.board.variants.FalconHunterBoard.*;
import com.chess.engine.board.variants.Metamachy.Cannon;

public class GrossChess extends Board{
	// TODO Fix Promotion
	public GrossChess(Builder builder) {
		super(builder, 12, 12, true, true, true, true);
	}
	
	public static Board createBoard() {
		final GrossBuilder builder = new GrossBuilder();
		// Black Layout
	    Class<? extends Piece>[] classes = new Class[] {
	        Chancellor.class, 
	        Archbishop.class, 
	        Vao.class,
	        Wizard.class,
	        Cannon.class,
	        EmptyPiece.class,
	        EmptyPiece.class,
	        Cannon.class,
	        Wizard.class,
	        Vao.class,
	        Archbishop.class,
	        Chancellor.class,
	        EmptyPiece.class,
	        Rook.class,
	        Champion.class,
	        Knight.class,
	        Bishop.class,
	        Queen.class,
	        King.class,
	        Bishop.class,
	        Knight.class,
	        Champion.class,
	        Rook.class,
	        EmptyPiece.class,
	    };
	    Class<? extends Piece> pawnClass =FalconHunterPawn.class;
		BoardUtils.buildBoard(classes, builder, 12,12,3,(Class<? extends Piece>)pawnClass);
//		builder.setPiece(new Chancellor(Alliance.BLACK, 0, true));
//		builder.setPiece(new Archbishop(Alliance.BLACK, 1, true));
//		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
//		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
//		builder.setPiece(new King(Alliance.BLACK, 4, true));
//		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
//		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
//		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
//		builder.setPiece(new FalconHunterPawn(Alliance.BLACK, 8, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
//		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
//		//White Layout
//		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
//		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
//		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
//		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
//		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
//		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
//		builder.setPiece(new King(Alliance.WHITE, 60, true));
//		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
//		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
//		builder.setPiece(new Rook(Alliance.WHITE, 63, true));
//		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class Wizard extends Piece {

		public Wizard(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.WIZARD, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Wizard movePiece(Move move) {
			return new Wizard(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(-board.getNumberColumns()*3-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()*3+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(board.getNumberColumns()*3+1, MotionType.Jump),
					new Vectors(board.getNumberColumns()*3-1, MotionType.Jump),
					new Vectors(board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
					
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	
	public static class Champion extends Piece {

		public Champion(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.CHAMPION, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Champion movePiece(Move move) {
			return new Champion(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(-board.getNumberColumns()*2, MotionType.Jump),
					new Vectors(board.getNumberColumns()*2, MotionType.Jump),
					new Vectors(-2, MotionType.Jump),
					new Vectors(2, MotionType.Jump),
					new Vectors(board.getNumberColumns(), MotionType.Jump),
					new Vectors(-board.getNumberColumns(), MotionType.Jump),
					new Vectors(-1, MotionType.Jump),
					new Vectors(1, MotionType.Jump),
						
				};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Vao extends Piece {

		public Vao(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.VAO, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Vao movePiece(Move move) {
			return new Vao(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()+1, MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(board.getNumberColumns()-1, MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(-board.getNumberColumns()-1, MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(-board.getNumberColumns()+1, MotionType.Itterative, AttackType.MotionOnly),
					new Vectors(board.getNumberColumns()+1, AttackType.AttackOnly	, Integer.MAX_VALUE,12,false,1),
					new Vectors(board.getNumberColumns()-1,AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					new Vectors(-board.getNumberColumns()+1,AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					new Vectors(-board.getNumberColumns()-1, AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	
	public static class GrossBuilder extends Builder {
		@Override
		public Board build() {
			return new GrossChess(this);
		}
	}
	
}

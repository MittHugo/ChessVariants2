package com.chess.engine.board.variants;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.MotionType;

public class GrantAcedrex extends Board{
	
	public GrantAcedrex(Builder builder) {
		super(builder, 12, 12, false, true, true, true);
	}
	
	public static Board createBoard() {
		final GrantAcedrexBuilder builder = new GrantAcedrexBuilder();
		// Black Layout
//		Class[] classes = {Rook.class, Lion.class, Unicorn.class,Giraffe.class,
//				Bishop.class, Roc.class, King.class,Bishop.class,Giraffe.class,Unicorn.class, Lion.class,Rook.class};
//		BoardBuilderUtils.buildBoard(builder, classes, 3, 12,12);
		Class<? extends Piece>[] classes = new Class[] {
			    Rook.class, Lion.class, Unicorn.class, Giraffe.class, Bishop.class, Roc.class, King.class, 
			    Bishop.class, Giraffe.class, Unicorn.class, Lion.class, Rook.class
			};

			// Build the board for both sides with pawns and special piece arrangements
			BoardUtils.buildBoard(classes, builder, 12, 12, 3, Pawn.class);
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class GrantAcedrexBuilder extends Builder {
		@Override
		public Board build() {
			return new GrantAcedrex(this);
		}
	}
	
	public static class Lion extends Piece {

		public Lion(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.LION, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Lion movePiece(Move move) {
			return new Lion(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()*3, MotionType.Jump),
					new Vectors(-board.getNumberColumns()*3, MotionType.Jump),
					new Vectors(board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(-3, MotionType.Jump),
					new Vectors(3, MotionType.Jump),
					new Vectors(3*board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(3*board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()+1, MotionType.Jump),

			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Giraffe extends Piece {

		public Giraffe(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.GIRAFFE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Giraffe movePiece(Move move) {
			return new Giraffe(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(3*board.getNumberColumns()-2,MotionType.Jump),
					new Vectors(3*board.getNumberColumns()+2,MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()-2,MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()+2,MotionType.Jump),
					new Vectors(2*board.getNumberColumns()-3,MotionType.Jump),
					new Vectors(2*board.getNumberColumns()+3,MotionType.Jump),
					new Vectors(-2*board.getNumberColumns()-3,MotionType.Jump),
					new Vectors(-2*board.getNumberColumns()+3,MotionType.Jump),
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Roc extends Piece {

		public Roc(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ROC, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Roc movePiece(Move move) {
			return new Roc(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			int[] vectorCollection1 = {-1, board.getNumberColumns()};
			int[] vectorCollection2 = {1, board.getNumberColumns()};
			int[] vectorCollection3 = {-1, -board.getNumberColumns()};
			int[] vectorCollection4 = {1, -board.getNumberColumns()};
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()-1,MotionType.JumpToItterative, vectorCollection1),
					new Vectors(board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection2),
					new Vectors(-board.getNumberColumns()-1,MotionType.JumpToItterative, vectorCollection3),
					new Vectors(-board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection4),
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Unicorn extends Piece {

		public Unicorn(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.UNICORN, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Unicorn movePiece(Move move) {
			return new Unicorn(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			int[] vectorCollection1 = {-board.getNumberColumns()-1};
			int[] vectorCollection2 = {-board.getNumberColumns()+1};
			int[] vectorCollection3 = {board.getNumberColumns() -1};
			int[] vectorCollection4 = {board.getNumberColumns()+1};
			Vectors[] vectors = {
					new Vectors(-2*board.getNumberColumns()-1, MotionType.JumpToItterative,vectorCollection1),
					new Vectors(-board.getNumberColumns()-2, MotionType.JumpToItterative, vectorCollection1),
					new Vectors(-2*board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection2),
					new Vectors(-board.getNumberColumns()+2,MotionType.JumpToItterative, vectorCollection2),
					new Vectors(2*board.getNumberColumns()-1,MotionType.JumpToItterative, vectorCollection3),
					new Vectors(board.getNumberColumns()-2,MotionType.JumpToItterative, vectorCollection3),
					new Vectors(2*board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection4),
					new Vectors(board.getNumberColumns()+2,MotionType.JumpToItterative, vectorCollection4),
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
}

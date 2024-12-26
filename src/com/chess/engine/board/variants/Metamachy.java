package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.board.variants.WildebeastChess.Camel;
import com.chess.engine.board.variants.GrantAcedrex.Roc;

public class Metamachy extends Board {

	public Metamachy(Builder builder) {
		super(builder, 12, 12, false, true, true, true);
	}

	public static Board createBoard() {
		final MetamachyBuilder builder = new MetamachyBuilder();
		// Black Layout
		builder.setPiece(new Cannon(Alliance.BLACK, 0, true));
		builder.setPiece(new Camel(Alliance.BLACK, 1, true));
		builder.setPiece(new Queen(Alliance.BLACK, 5, true));
		builder.setPiece(new MetamachyKing(Alliance.BLACK, 6, true));
		builder.setPiece(new Camel(Alliance.BLACK, 10, true));
		builder.setPiece(new Cannon(Alliance.BLACK, 11, true));
		
		builder.setPiece(new Elephant(Alliance.BLACK, 12, true));
		builder.setPiece(new Rook(Alliance.BLACK, 13, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 14, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 15, true));
		builder.setPiece(new Prince(Alliance.BLACK, 16, true));
		builder.setPiece(new Lion(Alliance.BLACK, 17, true));
		builder.setPiece(new Roc(Alliance.BLACK, 18, true));
		builder.setPiece(new Prince(Alliance.BLACK, 19, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 20, true));
		builder.setPiece(new Knight(Alliance.BLACK, 21, true));
		builder.setPiece(new Rook(Alliance.BLACK, 22, true));
		builder.setPiece(new Elephant(Alliance.BLACK, 23, true));
		createPawnLine(builder, Alliance.BLACK,3, 12);
		createPawnLine(builder, Alliance.WHITE,10, 12);
		// White Layout
		builder.setPiece(new Cannon(Alliance.WHITE, 143, true));
		builder.setPiece(new Camel(Alliance.WHITE, 142, true));
		builder.setPiece(new Queen(Alliance.WHITE, 137, true));
		builder.setPiece(new MetamachyKing(Alliance.WHITE, 138, true));
		builder.setPiece(new Camel(Alliance.WHITE, 133, true));
		builder.setPiece(new Cannon(Alliance.WHITE, 132, true));
		
		builder.setPiece(new Elephant(Alliance.WHITE, 120, true));
		builder.setPiece(new Rook(Alliance.WHITE, 121, true));
		builder.setPiece(new Knight(Alliance.WHITE, 122, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 123, true));
		builder.setPiece(new Prince(Alliance.WHITE, 124, true));
		builder.setPiece(new Lion(Alliance.WHITE, 125, true));
		builder.setPiece(new Roc(Alliance.WHITE, 126, true));
		builder.setPiece(new Prince(Alliance.WHITE, 127, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 128, true));
		builder.setPiece(new Knight(Alliance.WHITE, 129, true));
		builder.setPiece(new Rook(Alliance.WHITE, 130, true));
		builder.setPiece(new Elephant(Alliance.WHITE, 131, true));

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	
	public static class Prince extends Piece {

		public Prince(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.PRINCE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Prince movePiece(Move move) {
			return new Prince(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			int[] behindVector = {
				pieceAlliance.getDirection()*board.getNumberColumns(),
			};
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns(), MotionType.Jump),
					new Vectors(-board.getNumberColumns(), MotionType.Jump),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(1, MotionType.Jump),
					new Vectors(-1, MotionType.Jump),
					new Vectors(2*pieceAlliance.getDirection()*board.getNumberColumns(), MotionType.JumpToItterative,behindVector, AttackType.MotionOnly),

			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
		
	}
	public static class Elephant extends Piece {

		public Elephant(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ELEPHANT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Elephant movePiece(Move move) {
			return new Elephant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()-2, MotionType.Jump),
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Lion extends Piece {

		public Lion(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.LION, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Lion movePiece(Move move) {
			return new Lion(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*-board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(board.getNumberColumns()*2, MotionType.Jump),
					new Vectors(board.getNumberColumns(), MotionType.Jump),
					new Vectors(-board.getNumberColumns()*2, MotionType.Jump),
					new Vectors(-board.getNumberColumns(), MotionType.Jump),
					
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	
	public static class MetamachyKing extends Piece {

		public MetamachyKing(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.KING, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public MetamachyKing movePiece(Move move) {
			return new MetamachyKing(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(-board.getNumberColumns()*2-1, MotionType.Jump, true),
					new Vectors(-board.getNumberColumns()*2+1, MotionType.Jump, true),
					new Vectors(-board.getNumberColumns()-2, MotionType.Jump, true),
					new Vectors(-board.getNumberColumns()+2, MotionType.Jump, true),
					new Vectors(board.getNumberColumns()-2, MotionType.Jump, true),
					new Vectors(board.getNumberColumns()*2+1, MotionType.Jump, true),
					new Vectors(board.getNumberColumns()*2-1, MotionType.Jump, true),
					new Vectors(board.getNumberColumns()+2, MotionType.Jump, true),
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump, true),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump, true),
					new Vectors(2*-board.getNumberColumns()+2, MotionType.Jump, true),
					new Vectors(2*-board.getNumberColumns()-2, MotionType.Jump, true),
					new Vectors(-board.getNumberColumns()*2, MotionType.Jump, true),
					new Vectors(board.getNumberColumns()*2, MotionType.Jump, true),
					new Vectors(-2, MotionType.Jump, true),
					new Vectors(2, MotionType.Jump,true),
					new Vectors(board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
					new Vectors(board.getNumberColumns(), MotionType.Jump),
					new Vectors(-board.getNumberColumns(), MotionType.Jump),
					new Vectors(-1, MotionType.Jump),
					new Vectors(1, MotionType.Jump),
						
				};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Cannon extends Piece {

		public Cannon(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.CANNON, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Cannon movePiece(Move move) {
			return new Cannon(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(-1, MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(1, MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(board.getNumberColumns(), MotionType.Itterative,AttackType.MotionOnly),
					new Vectors(-board.getNumberColumns(), MotionType.Itterative, AttackType.MotionOnly),
					new Vectors(-1, AttackType.AttackOnly	, Integer.MAX_VALUE,12,false,1),
					new Vectors(1,AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					new Vectors(board.getNumberColumns(),AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					new Vectors(-board.getNumberColumns(), AttackType.AttackOnly, Integer.MAX_VALUE,12,false,1),
					
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}

	public static class MetamachyBuilder extends Builder {
		@Override
		public Board build() {
			return new Metamachy(this);
		}
	}

}

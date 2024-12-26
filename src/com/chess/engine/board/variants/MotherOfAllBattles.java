package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.board.variants.FalconHunterBoard.*;
import com.chess.engine.board.variants.GrantAcedrex.Roc;
import com.chess.engine.board.variants.GrantAcedrex.Unicorn;
import com.chess.engine.board.variants.GrossChess.Vao;
import com.chess.engine.board.variants.Metamachy.Cannon;
import com.chess.engine.board.variants.Metamachy.Elephant;
import com.chess.engine.board.variants.WildebeastChess.Camel;

public class MotherOfAllBattles extends Board{
	public MotherOfAllBattles(Builder builder) {
		super(builder, 16, 16, true, true, true, true);
	}
	
	public static Board createBoard() {
		final MotherOfAllBattlesBuilder builder = new MotherOfAllBattlesBuilder();
		// Black Layout
	    Class<? extends Piece>[] classes = new Class[] {
	    	Antelope.class,
	    	Camel.class,
	    	Vao.class,
	    	Bull.class,
	    	Cannon.class,
	    	Buffalo.class,
	    	Unicorn.class,
	    	WolfChess.Elephant.class,
	    	Chancellor.class,
	    	Archbishop.class,
	    	Cannon.class,
	    	Bull.class,
	    	Vao.class,
	    	Camel.class,
	    	Antelope.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Lion.class,
	    	Roc.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Sergeant.class,
	    	Metamachy.Elephant.class,
	    	Machine.class,
	    	Rook.class,
	    	Knight.class,
	    	Bishop.class,
	    	Ship.class,
	    	Buffoon.class,
	    	Queen.class,
	    	King.class,
	    	Buffoon.class,
	    	Ship.class,
	    	Bishop.class,
	    	Rook.class,
	    	Machine.class,
	    	Metamachy.Elephant.class,
	    };
	    Class<? extends Piece> pawnClass =FalconHunterPawn.class;
		BoardUtils.buildBoard(classes, builder, 16,16,3, FalconHunterPawn.class);
//		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	public static class Star extends Piece {

		public Star(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.STAR, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Star movePiece(Move move) {
			return new Star(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
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
	public static class Sergeant extends Piece {

		public Sergeant(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SERGEANT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Sergeant movePiece(Move move) {
			return new Sergeant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(2*this.pieceAlliance.getDirection()*board.getNumberColumns(), MotionType.Jump),
					new Vectors(this.pieceAlliance.getDirection()*board.getNumberColumns(), MotionType.Jump),
					new Vectors(this.pieceAlliance.getDirection()*board.getNumberColumns()+1, MotionType.Jump),
					new Vectors(this.pieceAlliance.getDirection()*board.getNumberColumns()-1, MotionType.Jump),
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	
	public static class Antelope extends Piece {

		public Antelope(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ANTELOPE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Antelope movePiece(Move move) {
			return new Antelope(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(-2*board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(-2*board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump),
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(-3*board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(3*board.getNumberColumns()-3, MotionType.Jump),
					new Vectors(3*board.getNumberColumns()+3, MotionType.Jump),
					new Vectors(-2*board.getNumberColumns(), MotionType.Jump),
					new Vectors(-3*board.getNumberColumns(), MotionType.Jump),
					new Vectors(3*board.getNumberColumns(), MotionType.Jump),
					new Vectors(2*board.getNumberColumns(), MotionType.Jump),
					
					
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	public static class Buffalo extends Piece {

		public Buffalo(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.BUFFALO, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Antelope movePiece(Move move) {
			return new Antelope(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			ArrayList<Vectors> vectors = new ArrayList<Vectors>();
			vectors.addAll(PieceUtils.customKnightJump(3, 1, board));
			vectors.addAll(PieceUtils.customKnightJump(4, 1, board));
			return PieceUtils.moveMaker((Vectors[])vectors.toArray(), board, this);
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
			ArrayList<Vectors> vectors = new ArrayList<Vectors>();
			vectors.addAll(PieceUtils.diagonalJump(1, board));
			vectors.addAll(PieceUtils.diagonalJump(2, board));
			vectors.addAll(PieceUtils.orthongonalJump(1, board));
			vectors.addAll(PieceUtils.orthongonalJump(2, board));
			return PieceUtils.moveMaker((Vectors[])vectors.toArray(), board, this);
		}
	}
	public static class Machine extends Piece {

		public Machine(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.MACHINE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Machine movePiece(Move move) {
			return new Machine(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			ArrayList<Vectors> vectors = new ArrayList<Vectors>();
			vectors.addAll(PieceUtils.diagonalJump(1, board));
			vectors.addAll(PieceUtils.diagonalJump(2, board));
			vectors.addAll(PieceUtils.orthongonalJump(1, board));
			vectors.addAll(PieceUtils.orthongonalJump(2, board));
			return PieceUtils.moveMaker((Vectors[])vectors.toArray(), board, this);
		}
	}
	public static class Buffoon extends Piece {

		public Buffoon(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.PRINCE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Buffoon movePiece(Move move) {
			return new Buffoon(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			ArrayList<Vectors> vectors = new ArrayList<Vectors>();
			vectors.addAll(PieceUtils.diagonalJump(1, board));
			vectors.addAll(PieceUtils.orthongonalJump(1, board));
			return PieceUtils.moveMaker((Vectors[])vectors.toArray(), board, this);
		}
	}
	public static class Bull extends Piece {

		public Bull(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.BULL, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Antelope movePiece(Move move) {
			return new Antelope(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			ArrayList<Vectors> vectors = new ArrayList<Vectors>();
			vectors.addAll(PieceUtils.customKnightJump(3, 2, board));
			return PieceUtils.moveMaker((Vectors[])vectors.toArray(), board, this);
		}
	}
//	
	public static class Ship extends Piece {

		protected Ship(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SHIP, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Roc movePiece(Move move) {
			return new Roc(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			int[] vectorCollection1 = {board.getNumberColumns()};
			int[] vectorCollection2 = {board.getNumberColumns()};
			int[] vectorCollection3 = {-board.getNumberColumns()};
			int[] vectorCollection4 = {-board.getNumberColumns()};
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()-1,MotionType.JumpToItterative, vectorCollection1),
					new Vectors(board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection2),
					new Vectors(-board.getNumberColumns()-1,MotionType.JumpToItterative, vectorCollection3),
					new Vectors(-board.getNumberColumns()+1,MotionType.JumpToItterative, vectorCollection4),
			};
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}
	
	public static class MotherOfAllBattlesBuilder extends Builder {
		@Override
		public Board build() {
			return new MotherOfAllBattles(this);
		}
	}
	
}

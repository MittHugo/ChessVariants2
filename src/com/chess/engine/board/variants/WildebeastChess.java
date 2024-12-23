package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;

public class WildebeastChess extends Board{
	
	public WildebeastChess(Builder builder) {
		super(builder, 10, 11, true, true, true, true);
	}
	
	public static Board createBoard() {
		final WildebeastBuilder builder = new WildebeastBuilder();
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Wildebeast(Alliance.BLACK, 5, true));
		builder.setPiece(new Camel(Alliance.BLACK, 6, true));
		builder.setPiece(new Camel(Alliance.BLACK, 7, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 8, true));
		builder.setPiece(new Knight(Alliance.BLACK, 9, true));
		builder.setPiece(new Rook(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 16, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 17, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 18, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 19, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 20, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 21, true));
		//White Layout
		createPawnLine(builder, Alliance.WHITE, 9,11);
		builder.setPiece(new Rook(Alliance.WHITE, 99, true));
		builder.setPiece(new Knight(Alliance.WHITE, 100, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 101, true));
		builder.setPiece(new Queen(Alliance.WHITE, 102, true));
		builder.setPiece(new King(Alliance.WHITE, 103, true));
		builder.setPiece(new Wildebeast(Alliance.WHITE, 104, true));
		builder.setPiece(new Camel(Alliance.WHITE, 105, true));
		builder.setPiece(new Camel(Alliance.WHITE, 106, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 107, true));
		builder.setPiece(new Knight(Alliance.WHITE, 108, true));
		builder.setPiece(new Rook(Alliance.WHITE, 109, true));
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class WildebeastBuilder extends Builder {
		@Override
		public Board build() {
			return new WildebeastChess(this);
		}
	}

	public static class Camel extends Piece {

		public Camel(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.CAMEL, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Camel movePiece(Move move) {
			return new Camel(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> legalMoves = new ArrayList<Move>();
		    legalMoves.addAll(PieceUtils.calculate3To1PatternLeaper(board, this));
			return Collections.unmodifiableCollection(legalMoves);
		}
		
		@Override 
		public String toString() {
			return PieceType.CAMEL.toString();
		}
	}
	public static class Wildebeast extends Piece {

		public Wildebeast(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.WILDEBEAST, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Wildebeast movePiece(Move move) {
			return new Wildebeast(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> legalMoves = new ArrayList<Move>();
		    legalMoves.addAll(PieceUtils.calculate3To1PatternLeaper(board, this));
		    legalMoves.addAll(PieceUtils.calculate2To1PatternLeaper(board, this));
			return Collections.unmodifiableCollection(legalMoves);
		}
		
		@Override 
		public String toString() {
			return PieceType.WILDEBEAST.toString();
		}
	}

	
}

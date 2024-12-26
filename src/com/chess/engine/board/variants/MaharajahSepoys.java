package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;

public class MaharajahSepoys extends Board{
	
	public MaharajahSepoys(Builder builder) {
		super(builder, 8, 8, true, true, true, false);
	}
	
	public static Board createBoard() {
		final MaharajahSepoysBuilder builder = new MaharajahSepoysBuilder();
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		//White Layout
		builder.setPiece(new Maharajah(Alliance.WHITE, 60, true));
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class MaharajahSepoysBuilder extends Builder {
		@Override
		public Board build() {
			return new MaharajahSepoys(this);
		}
	}
	
	public static class Maharajah extends Piece {

		public Maharajah(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.MAHARAJAH, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Maharajah movePiece(Move move) {
			return new Maharajah(this.getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public String toString() {
			return this.getPieceType().toString();
		}
		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> moves = new ArrayList<Move>();
			moves.addAll(PieceUtils.compulatePieceMoves(this, board, PieceUtils.pieceFromPiece(this, Bishop.class),
				 PieceUtils.pieceFromPiece(this, Rook.class), PieceUtils.pieceFromPiece(this, Knight.class)));
			return moves;
		}
		
	}
	
}

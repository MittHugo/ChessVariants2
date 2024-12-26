package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;

public class StratomicChess extends Board{
	
	public StratomicChess(Builder builder) {
		super(builder, 10, 10, true, true, true, true);
		SECOND_ROW = initRow(20);
		SECOND_LAST_ROW = initRow(70);
	}
	
	public static Board createBoard() {
		final StratomicBuilder builder = new StratomicBuilder();
		// Black Layout
		builder.setPiece(new MissilePiece(Alliance.BLACK, 10, true));
		builder.setPiece(new Rook(Alliance.BLACK, 11, true));
		builder.setPiece(new Knight(Alliance.BLACK, 12, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 13, true));
		builder.setPiece(new Queen(Alliance.BLACK, 14, true));
		builder.setPiece(new King(Alliance.BLACK, 15, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 16, true));
		builder.setPiece(new Knight(Alliance.BLACK, 17, true));
		builder.setPiece(new Rook(Alliance.BLACK, 18, true));
		builder.setPiece(new MissilePiece(Alliance.BLACK, 19, true));
		createPawnLine(builder, Alliance.BLACK, 3, 10);
		//White Layout
		createPawnLine(builder, Alliance.WHITE, 8, 10);
		builder.setPiece(new MissilePiece(Alliance.WHITE, 80, true));
		builder.setPiece(new Rook(Alliance.WHITE, 81, true));
		builder.setPiece(new Knight(Alliance.WHITE, 82, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 83, true));
		builder.setPiece(new Queen(Alliance.WHITE, 84, true));
		builder.setPiece(new King(Alliance.WHITE, 85, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 86, true));
		builder.setPiece(new Knight(Alliance.WHITE, 87, true));
		builder.setPiece(new Rook(Alliance.WHITE, 88, true));
		builder.setPiece(new MissilePiece(Alliance.WHITE, 89, true));
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class StratomicBuilder extends Builder {
		@Override
		public Board build() {
			return new StratomicChess(this);
		}
	}
	
	
	public static class MissilePiece extends Piece {

		protected MissilePiece(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.STRATOMIC, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Piece movePiece(Move move) {
			return null;
		}
		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> legalMoves = new ArrayList<>();
			Collection<Piece> pieces;
			if(this.getAlliance().isWhite()) {
				pieces = board.getWhitePieces();
			} else {
				pieces = board.getBlackPieces();
			}
			int numberOfRooks = 0;
			boolean hasQueen = false;
			int numberOfKnights = 0;
			int numberOfBishops = 0;
			int numberOfStratomics = 0;
			for(Piece piece: pieces) {
				if(piece.getPieceType().isRook()) {
					numberOfRooks ++;
				} else if(piece.getPieceType() == PieceType.QUEEN) {
					hasQueen = true;
				} else if(piece.getPieceType() == PieceType.KNIGHT) {
					numberOfKnights ++;
				} else if(piece.getPieceType() == PieceType.BISHOP) {
					numberOfBishops ++;
				} else if(piece.getPieceType() == PieceType.STRATOMIC) {
					numberOfStratomics ++;
				}
			}
			if(!(numberOfRooks == 2 && hasQueen && numberOfKnights == 2 && numberOfBishops == 2 && numberOfStratomics ==2)) {
				for(int i = 0; i < board.getNumberTiles(); i++) {
					if(board.getTile(i).isTileOccupied()) {
						if(board.getTile(i).getPiece().getPieceType() != PieceType.KING) {
							legalMoves.add(new Move.AtomicMove(board, this, i));
						}
					} else {
						legalMoves.add(new Move.AtomicMove(board, this, i));
					}
				}
			}
			return legalMoves;
		}

		@Override 
		public String toString() {
			return PieceType.STRATOMIC.toString();
		}
		
	}
}

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
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.*;
import com.chess.engine.board.variants.FalconHunterBoard.*;
import com.chess.engine.board.variants.GrossChess.Champion;
import com.chess.engine.board.variants.GrossChess.Vao;
import com.chess.engine.board.variants.GrossChess.Wizard;
import com.chess.engine.board.variants.Metamachy.Cannon;

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
			final int[] CANIDATE_MOVE_COORDINATES = {board.getNumberColumns()*2, -board.getNumberColumns()*2, -2, 2,
					-board.getNumberColumns()*2-2, -board.getNumberColumns()*2+2, board.getNumberColumns()*2-2, board.getNumberColumns()*2+2};
			final List<Move> legalMoves = new ArrayList<>();
			legalMoves.addAll(PieceUtils.calculate2To1PatternLeaper(board, this));
			
			for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition+currentCanidateOffset;
				if(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					
					if(isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board) ||
							isSecondColumnExclusion(this.piecePosition, currentCanidateOffset, board) ||
							isSeventhColumnExclusion(this.piecePosition, currentCanidateOffset, board) ||
							isEightColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
						continue;
					}
					
					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
					
					if(!canidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}
		
		@Override 
		public String toString() {
			return PieceType.BEAR.toString();
		}
		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -2 || canidateOffset == board.getNumberColumns()*2-2 ||
					canidateOffset == -board.getNumberColumns()*2-2);
		}
		
		private static boolean isSecondColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.SECOND_COLUMN[currentPosition] && (canidateOffset == -2 || canidateOffset == board.getNumberColumns()*2-2 ||
					canidateOffset == -board.getNumberColumns()*2-2);
		}
		
		private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.SECOND_TO_LAST_COLUMN[currentPosition] && (canidateOffset == 2 || canidateOffset == board.getNumberColumns()*2+2 ||
					canidateOffset == -board.getNumberColumns()*2+2);
		}
		private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == 2 || canidateOffset == board.getNumberColumns()*2+2 ||
					canidateOffset == -board.getNumberColumns()*2+2);
		}
		
		@Override
		public Bear movePiece(final Move move) {
			return new Bear(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
	}

	
}

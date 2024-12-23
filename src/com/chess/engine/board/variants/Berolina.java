package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.board.variants.BearChess.Bear;
import com.chess.engine.board.variants.FalconHunterBoard.FalconHunterPawn;
import com.chess.engine.pieces.*;

public class Berolina extends Board{
	
	public Berolina(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final BerolinaBuilder builder = new BerolinaBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        Knight.class,
		        Bishop.class,
		        Queen.class,
		        King.class,
		        Bishop.class,
		        Knight.class,
		        Rook.class
		    };
		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.buildBoard(classes, builder, 8,8,2,(Class<? extends Piece>)pawnClass);
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class BerolinaPawn extends Pawn {

		public BerolinaPawn(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}
		
		@Override 
		public String toString() {
			return PieceType.BEROLINA_PAWN.toString();
		}
		@Override
		public BerolinaPawn movePiece(final Move move) {
			return new BerolinaPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
					((board.getNumberColumns()*2)-2), ((board.getNumberColumns()*2)+2)};
			final List<Move> legalMoves = new ArrayList<>();
			for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
				if(!board.isValidTileCoordinate(canidateDestinationCoordinate) || 
						PieceUtils.isException(board, currentCanidateOffset*pieceAlliance.getDirection(), this.piecePosition)) {
					continue;
				} 
				if(currentCanidateOffset == board.getNumberColumns()) {
					if(board.getTile(canidateDestinationCoordinate).isTileOccupied() 
							&& board.getTile(canidateDestinationCoordinate).getPiece().getAlliance() != this.pieceAlliance) {
						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) &&
								board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					
						}
						if(board.getEnPassantPawn() !=null) {
							if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getOppositeDirection()))) {
								final Piece pieceOnCanidate = board.getEnPassantPawn();
								if(this.pieceAlliance != pieceOnCanidate.getAlliance()&& board.canEnPassant()) {
									legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
								}
							}
						}
					}
				} else if(currentCanidateOffset == ((board.getNumberColumns()*2)+2) && this.isFirstMove()&& 
						((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite())) && board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection()*(board.getNumberColumns()+1));
//					final int behindCanidateDestinationCoordinate2 = this.piecePosition +(this.pieceAlliance.getDirection()*board.getNumberColumns()-2);
					if((!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
							!board.getTile(canidateDestinationCoordinate).isTileOccupied())) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == ((board.getNumberColumns()*2)-2) && this.isFirstMove()&& 
						((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite())) && board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection()*(board.getNumberColumns()-1));
					if(!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
							!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()-1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) && 
								board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					} 
				} else if(currentCanidateOffset == (board.getNumberColumns()+1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
								&& board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board, final Alliance pieceAlliance) {
			return board.FIRST_COLUMN[currentPosition] && 
					((canidateOffset == -pieceAlliance.getDirection()*(board.getNumberColumns()*2+2) || canidateOffset == -pieceAlliance.getDirection()*(board.getNumberColumns()+1)));
		}
		
		private static boolean isSecondColumnExclusion(final int currentPosition, final int canidateOffset, final Board board,  final Alliance pieceAlliance) {
			return board.SECOND_COLUMN[currentPosition] && (canidateOffset == -pieceAlliance.getDirection()*(board.getNumberColumns()*2+2));
		}
		
//		private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset, final Board board,  final Alliance pieceAlliance) {
//			return board.SECOND_TO_LAST_COLUMN[currentPosition] && ((canidateOffset == (board.getNumberColumns()*2+2) && pieceAlliance.isBlack()) || 
//					(canidateOffset == board.getNumberColumns()*2-2 && pieceAlliance.isWhite()));
//		}
		private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset, final Board board,  final Alliance pieceAlliance) {
			return board.SECOND_TO_LAST_COLUMN[currentPosition] && (canidateOffset == pieceAlliance.getDirection()*(board.getNumberColumns()*2+2));
		}
		private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset, final Board board,  final Alliance pieceAlliance) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == (board.getNumberColumns()*2+2) || canidateOffset == (board.getNumberColumns()+1));
		}
		
		
		
	}
	
	public static class BerolinaBuilder extends Builder {
		@Override
		public Board build() {
			return new Berolina(this);
		}
	}
	
}

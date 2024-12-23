package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.*;

public class WolfChess extends Board{
	
	public WolfChess(Builder builder) {
		super(builder, 10, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final WolfBuilder builder = new WolfBuilder();
		// Black Layout
		builder.setPiece(new Queen(Alliance.BLACK, 0, true));
		builder.setPiece(new Chancellor(Alliance.BLACK, 1, true));
		builder.setPiece(new Archbishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Rook(Alliance.BLACK, 3, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Nightrider(Alliance.BLACK, 6, true));
		builder.setPiece(new King(Alliance.BLACK, 7, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Sergeant(Alliance.BLACK, 9, true));
		builder.setPiece(new Sergeant(Alliance.BLACK, 10, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 11, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Sergeant(Alliance.BLACK, 13, true));
		builder.setPiece(new Sergeant(Alliance.BLACK, 14, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 15, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 17, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 18, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 21, true));
		builder.setPiece(new WolfPawn(Alliance.BLACK, 22, true));
		
//		builder.setPiece(new MissilePiece(Alliance.BLACK, 19));
		//White Layout
		builder.setPiece(new WolfPawn(Alliance.WHITE, 57, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 58, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 61, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 62, true));
//		builder.setPiece(new MissilePiece(Alliance.WHITE, 80, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 64, true));
		builder.setPiece(new Sergeant(Alliance.WHITE, 65, true));
		builder.setPiece(new Sergeant(Alliance.WHITE, 66, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 67, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 68, true));
		builder.setPiece(new Sergeant(Alliance.WHITE, 69, true));
		builder.setPiece(new Sergeant(Alliance.WHITE, 70, true));
		builder.setPiece(new WolfPawn(Alliance.WHITE, 71, true));
		
		builder.setPiece(new King(Alliance.WHITE, 72, true));
		builder.setPiece(new Nightrider(Alliance.WHITE, 73, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 74, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 75, true));
		builder.setPiece(new Rook(Alliance.WHITE, 76, true));
		builder.setPiece(new Archbishop(Alliance.WHITE, 77, true));
		builder.setPiece(new Chancellor(Alliance.WHITE, 78, true));
		builder.setPiece(new Queen(Alliance.WHITE, 79, true));
//		builder.setPiece(new MissilePiece(Alliance.WHITE, 89, true));
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class WolfBuilder extends Builder {
		@Override
		public Board build() {
			return new WolfChess(this);
		}
	}
	public static class WolfPawn extends Pawn {

		public WolfPawn(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}
		@Override
		public WolfPawn movePiece(final Move move) {
			return new WolfPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
		
		@Override
		public Piece getPromotionPiece() {
			return new Elephant(this.pieceAlliance, this.piecePosition, false);
		}
		@Override 
		public String toString() {
			return PieceType.PAWN.toString();
		}
	}
	
	// Seargent 
	public static class Elephant extends Piece {

		protected Elephant(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ELEPHANT_FROM_WOLF_CHESS, pieceAlliance, piecePosition,isFirstMove);
		}

		@Override
		public Elephant movePiece(Move move) {
			return new Elephant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> legalMoves = new ArrayList<>();
			Nightrider nightrider = new Nightrider(this.pieceAlliance, this.piecePosition, isFirstMove);
			for(Move move: nightrider.calculateLegalMoves(board)) {
				if(move.isAttack()) {
					legalMoves.add(new AttackMove(board, this, move.getDestinationCoordinate(), move.getAttackedPiece()));
				}else {
					legalMoves.add(new MajorMove(board, this, move.getDestinationCoordinate()));
				}
			}
			legalMoves.addAll(PieceUtils.calculateDiagonalMoves(board, this));
			legalMoves.addAll(PieceUtils.calculateOrthogonalMoves(board, this));
			return legalMoves;
		}
		
		@Override 
		public String toString() {
			return PieceType.ELEPHANT_FROM_WOLF_CHESS.toString();
		}
	}
	
	public static class Sergeant extends Pawn{

		public Sergeant(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(PieceType.SERGEANT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
					board.getNumberColumns()*2};
			final List<Move> legalMoves = new ArrayList<>();
			for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
				if(!board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					continue;
				} 	
				if(currentCanidateOffset == board.getNumberColumns()) {	
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) &&
								board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()*2) && this.isFirstMove()&& 
						((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite())) && board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection()*board.getNumberColumns());
					if(!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
							!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()-1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
					if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) && 
									board.canPromote()) {
								legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					}  else {
						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()+1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
					if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
									&& board.canPromote()) {
								legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					} else {
						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					}
				}
			}
//			System.out.println((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
//						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite()));
			return Collections.unmodifiableList(legalMoves);
		}
		
		
		@Override 
		public String toString() {
			return PieceType.SERGEANT.toString();
		}
		
		@Override
		public Sergeant movePiece(final Move move) {
			return new Sergeant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		public Piece getPromotionPiece() {
			return new Queen(this.pieceAlliance, this.piecePosition, false);
		}

	}
	
	
}

package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Piece.PieceType;

public class TorpedoChess extends Board{
	
	public TorpedoChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}
	
	public static Board createBoard() {
		final TorpedoBuilder builder = new TorpedoBuilder();
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 8, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 9, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 10, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 11, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 12, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 13, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 14, true));
		builder.setPiece(new TorpedoPawn(Alliance.BLACK, 15, true));
		//White Layout
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 48, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 49, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 50, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 51, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 52, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 53, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 54, true));
		builder.setPiece(new TorpedoPawn(Alliance.WHITE, 55, true));
		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
		builder.setPiece(new Rook(Alliance.WHITE, 63, true));
		
		// white to move
		 builder.setMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class TorpedoBuilder extends Builder {
		@Override
		public Board build() {
			return new TorpedoChess(this);
		}
	}
	public static class TorpedoPawn extends Pawn{

		public TorpedoPawn(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(PieceType.TORPEDO_PAWN, pieceAlliance, piecePosition, isFirstMove);
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
				} else if(currentCanidateOffset == (board.getNumberColumns()*2)  && board.canPawnJump()) {
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
					} 
					if(board.getEnPassantPawn() !=null) {
						if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getOppositeDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if(this.pieceAlliance != pieceOnCanidate.getAlliance()&& board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
							}
						}
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
					}
					if(board.getEnPassantPawn() !=null) {
						if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if(this.pieceAlliance != pieceOnCanidate.getAlliance() && board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
							}
						}
					}
				}
			}
//			System.out.println((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
//						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite()));
			return Collections.unmodifiableList(legalMoves);
		}
		
		
		@Override 
		public String toString() {
			return PieceType.TORPEDO_PAWN.toString();
		}
		
		@Override
		public TorpedoPawn movePiece(final Move move) {
			return new TorpedoPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		public Piece getPromotionPiece() {
			return new Queen(this.pieceAlliance, this.piecePosition, false);
		}

	}
}

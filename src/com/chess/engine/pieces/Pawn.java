package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;

public class Pawn extends Piece{

	public Pawn(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
		super(PieceType.PAWN, pieceAlliance, piecePosition, isFirstMove);
	}
	public Pawn(final PieceType type,final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
		super(type, pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		// int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
		// 		board.getNumberColumns()*2};
		final List<Move> legalMoves = new ArrayList<>();
		// if(this.getAlliance() == Alliance.WHITE || this.getAlliance() == Alliance.BLACK) {
		// 	legalMoves.addAll(whiteBlackLegalMoves(board));
		// } else {
		// 	legalMoves.addAll(redBlueLegalMoves(board));
		// }
		if(this.getAlliance().isBlack()) {
			Vectors[] CANIDATE_MOVE_COORDINATE = {
				new Vectors(board.getNumberColumns()-1, MotionType.Jump, AttackType.AttackOnly),
				new Vectors(board.getNumberColumns(),  MotionType.Jump, AttackType.MotionOnly),
				new Vectors(board.getNumberColumns()+1, MotionType.Jump, AttackType.AttackOnly),
				new Vectors(board.getNumberColumns()*2,  MotionType.Jump, AttackType.MotionOnly, true)
			};
			legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATE, board, this));
		} else if(this.getAlliance().isWhite()) {
			Vectors[] CANIDATE_MOVE_COORDINATE = {
				new Vectors(-board.getNumberColumns()-1, MotionType.Jump, AttackType.AttackOnly),
				new Vectors(-board.getNumberColumns(),  MotionType.Jump, AttackType.MotionOnly),
				new Vectors(-board.getNumberColumns()+1, MotionType.Jump, AttackType.AttackOnly),
				new Vectors(-board.getNumberColumns()*2,  MotionType.Jump, AttackType.MotionOnly, true)
			};
			legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATE, board, this));
		} else if(this.getAlliance().isBlue()) {
			Vectors[] CANIDATE_MOVE_COORDINATE = {
				new Vectors(-1,MotionType.Jump, AttackType.MotionOnly),
				new Vectors(-2,MotionType.Jump, AttackType.MotionOnly, true),
				new Vectors(-board.getNumberColumns()-1,MotionType.Jump, AttackType.AttackOnly),
				new Vectors(board.getNumberColumns()-1, MotionType.Jump, AttackType.AttackOnly)
			};
			legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATE, board, this));
		} else {
			Vectors[] CANIDATE_MOVE_COORDINATE = {
				new Vectors(1,MotionType.Jump, AttackType.MotionOnly),
				new Vectors(2,MotionType.Jump, AttackType.MotionOnly, true),
				new Vectors(-board.getNumberColumns()+1,MotionType.Jump, AttackType.AttackOnly),
				new Vectors(board.getNumberColumns()+1, MotionType.Jump, AttackType.AttackOnly)
			};
			legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATE, board, this));
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
// 	private Collection<Move> whiteBlackLegalMoves(final Board board) {
// 		int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
// 				board.getNumberColumns()*2};
// 		final List<Move> legalMoves = new ArrayList<>();
// 		for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
// 			int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
// 			if(!board.isValidTileCoordinate(canidateDestinationCoordinate)) {
// 				continue;
// 			} 	
// 			if(currentCanidateOffset == board.getNumberColumns()) {	
// 				if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) &&
// 							board.canPromote()) {
// 						legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
// 					} else {
// 						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
// 					}
// 				}
// 			} else if(currentCanidateOffset == (board.getNumberColumns()*2) && this.isFirstMove()&& 
// 					 board.canPawnJump()) {
// 				final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection()*board.getNumberColumns());
// 				if(!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
// 						!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
// 				}
// 			} else if(currentCanidateOffset == (board.getNumberColumns()-1) &&
// 					!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite() || 
// 					 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
// 				if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
// 					if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
// 						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) && 
// 								board.canPromote()) {
// 							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate)));
// 						} else {
// 							legalMoves.add(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				} 
// 				if(board.getEnPassantPawn() !=null) {
// 					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getOppositeDirection()))) {
// 						final Piece pieceOnCanidate = board.getEnPassantPawn();
// //						System.out.println(pieceOnCanidate.getPieceType());
// 						if(this.pieceAlliance != pieceOnCanidate.getAlliance()&& board.canEnPassant()) {
// 							legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 			} else if(currentCanidateOffset == (board.getNumberColumns()+1) &&
// 					!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack() || 
// 					 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
// 				if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
// 					if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
// 						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
// 								&& board.canPromote()) {
// 							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate)));
// 						} else {
// 							legalMoves.add(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 				if(board.getEnPassantPawn() !=null) {
// 					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getDirection()))) {
// 						final Piece pieceOnCanidate = board.getEnPassantPawn();
// //						System.out.println(pieceOnCanidate.getPieceType());
// 						if(this.pieceAlliance != pieceOnCanidate.getAlliance() && board.canEnPassant()) {
// 							legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 			}
// 		}
// 		return Collections.unmodifiableList(legalMoves);
// 	}
	
// 	private Collection<Move> redBlueLegalMoves(final Board board) {
// 		int[] CANIDATE_MOVE_COORDINATE = {
// 				1, 2, 
// 				-board.getNumberColumns()+1,
// 				board.getNumberColumns()+1};
// 		final List<Move> legalMoves = new ArrayList<>();
// 		for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
// 			int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
// 			if(!board.isValidTileCoordinate(canidateDestinationCoordinate)) {
// 				continue;
// 			} 	
// 			if(currentCanidateOffset ==1) {	
// 				if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) &&
// 							board.canPromote()) {
// 						legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
// 					} else {
// 						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
// 					}
// 				}
// 			} else if(currentCanidateOffset == 2 && this.isFirstMove()&& 
// 					 board.canPawnJump()) {
// 				final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection());
// 				if(!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
// 						!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
// 				}
// 			} else if(currentCanidateOffset == (-board.getNumberColumns()+1) &&
// 					!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite() || 
// 					 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
// 				if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
// 					if(this.pieceAlliance.canAttack(pieceOnCanidate.getAlliance())) {
// 						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) && 
// 								board.canPromote()) {
// 							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate)));
// 						} else {
// 							legalMoves.add(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				} 
// 				if(board.getEnPassantPawn() !=null) {
// 					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getOppositeDirection()))) {
// 						final Piece pieceOnCanidate = board.getEnPassantPawn();
// //						System.out.println(pieceOnCanidate.getPieceType());
// 						if(this.pieceAlliance.canAttack(pieceOnCanidate.getAlliance())&& board.canEnPassant()) {
// 							legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 			} else if(currentCanidateOffset == (board.getNumberColumns()+1) &&
// 					!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack() || 
// 					 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
// 				if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
// 					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
// 					if(this.pieceAlliance.canAttack(pieceOnCanidate.getAlliance())) {
// 						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
// 								&& board.canPromote()) {
// 							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate)));
// 						} else {
// 							legalMoves.add(new PawnAttackMove(board, this, canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 				if(board.getEnPassantPawn() !=null) {
// 					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getDirection()))) {
// 						final Piece pieceOnCanidate = board.getEnPassantPawn();
// //						System.out.println(pieceOnCanidate.getPieceType());
// 						if(this.pieceAlliance.canAttack(pieceOnCanidate.getAlliance()) && board.canEnPassant()) {
// 							legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
// 						}
// 					}
// 				}
// 			}
// 		}
// 		return Collections.unmodifiableList(legalMoves);
// 	}
	
	@Override 
	public String toString() {
		return PieceType.PAWN.toString();
	}
	
	@Override
	public Pawn movePiece(final Move move) {
		return new Pawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
	
	@Override
	public Piece getPromotionPiece() {
		return new Queen(this.pieceAlliance, this.piecePosition, false);
	}

}

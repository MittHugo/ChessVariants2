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
import com.chess.engine.board.Move.*;
import com.chess.engine.pieces.*;

public class FalconHunterBoard extends Board {

	public FalconHunterBoard(Builder builder) {
		super(builder, 10, 10, true, false, true, true);
	}

	public static Board createBoard() {
		final FalconHunterBuilder builder = new FalconHunterBuilder();
	    Class<? extends Piece>[] classes = new Class[] {
		        Rook.class, 
		        FalconHunterKnight.class,
		        FalconHunterBoard.Hunter.class,
		        Bishop.class,
		        Queen.class,
		        King.class,
		        Bishop.class,
		        FalconHunterBoard.Falcon.class,
		        FalconHunterKnight.class,
		        Rook.class
		    };
		Class<? extends Piece> pawnClass =FalconHunterPawn.class;
		BoardUtils.buildBoard(classes, builder, 10,10,2, pawnClass);//,(Class<? extends Piece>)pawnClass

		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class Hunter extends Piece {

		public Hunter(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
			super(PieceType.HUNTER, pieceAlliance, piecePosition,isFirstMove);
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -(board.getNumberColumns() + 1)
					|| canidateOffset == (board.getNumberColumns() - 1));
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -(board.getNumberColumns() - 1)
					|| canidateOffset == (board.getNumberColumns() + 1));
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_VECTOR_COORDINATES = {this.getAlliance().getOppositeDirection()*(board.getNumberColumns() + 1),
					this.getAlliance().getOppositeDirection()*(board.getNumberColumns() - 1),
					this.getAlliance().getDirection()*board.getNumberColumns()};
			final List<Move> legalMoves = new ArrayList<>();

			for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition;
//				System.out.println(canidateCoordinateOffset);
				while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					if (isFirstColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)
							|| isLastColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)) {
						break;
					}

					canidateDestinationCoordinate += canidateCoordinateOffset;
					if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
						final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

						if (!canidateDestinationTile.isTileOccupied()) {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						} else {
							final Piece pieceAtDestination = canidateDestinationTile.getPiece();
							final Alliance pieceAlliance = pieceAtDestination.getAlliance();
							if (this.pieceAlliance != pieceAlliance) {
								legalMoves.add(
										new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
							}
							break;
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

		@Override
		public Hunter movePiece(final Move move) {
			return new Hunter(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public String toString() {
			return PieceType.HUNTER.toString();
		}

	}

	public static class Falcon extends Piece {

		public Falcon(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.FALCON, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_VECTOR_COORDINATES = {this.getAlliance().getDirection()*(board.getNumberColumns() + 1),
					this.getAlliance().getDirection()*(board.getNumberColumns() - 1),
					this.getAlliance().getOppositeDirection()*board.getNumberColumns()};
			final List<Move> legalMoves = new ArrayList<>();

			for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition;
//				System.out.println(canidateCoordinateOffset);
				while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					if (isFirstColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)
							|| isLastColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)) {
						break;
					}

					canidateDestinationCoordinate += canidateCoordinateOffset;
					if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
						final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

						if (!canidateDestinationTile.isTileOccupied()) {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						} else {
							final Piece pieceAtDestination = canidateDestinationTile.getPiece();
							final Alliance pieceAlliance = pieceAtDestination.getAlliance();
							if (this.pieceAlliance != pieceAlliance) {
								legalMoves.add(
										new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
							}
							break;
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}
		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -(board.getNumberColumns() + 1)
					|| canidateOffset == (board.getNumberColumns() - 1));
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -(board.getNumberColumns() - 1)
					|| canidateOffset == (board.getNumberColumns() + 1));
		}

		@Override
		public Falcon movePiece(final Move move) {
			return new Falcon(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public String toString() {
			return PieceType.FALCON.toString();
		}

	}

	public static class FalconHunterPawn extends Pawn {

		public FalconHunterPawn(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = { board.getNumberColumns() - 1, board.getNumberColumns(),
					board.getNumberColumns() + 1, board.getNumberColumns() * 2, board.getNumberColumns() * 3 };
			final List<Move> legalMoves = new ArrayList<>();
			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATE) {
				int canidateDestinationCoordinate = this.piecePosition
						+ currentCanidateOffset * pieceAlliance.getDirection();
				if (!board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					continue;
				}
				if (currentCanidateOffset == board.getNumberColumns()) {
					if (!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
								&& board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() * 2) && this.isFirstMove()
						&& board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition
							+ (this.pieceAlliance.getDirection() * board.getNumberColumns());
					if (!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied()
							&& !board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() * 3) && this.isFirstMove()
						&& board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition
							+ (this.pieceAlliance.getDirection() * board.getNumberColumns());
					if (!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied()
							&& !board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() - 1)
						&& !((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite()
								|| (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
					if (board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if (this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
									&& board.canPromote()) {
								legalMoves.add(
										new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					}
					if (board.getEnPassantPawn() != null) {
						if (board.getEnPassantPawn().getPiecePosition() == (this.piecePosition
								+ (this.getAlliance().getOppositeDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if (this.pieceAlliance != pieceOnCanidate.getAlliance() && board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this, canidateDestinationCoordinate,
										pieceOnCanidate));
							}
						}
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() + 1)
						&& !((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack()
								|| (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
					if (board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if (this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
									&& board.canPromote()) {
								legalMoves.add(
										new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					}
					if (board.getEnPassantPawn() != null) {
						if (board.getEnPassantPawn()
								.getPiecePosition() == (this.piecePosition + (this.getAlliance().getDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if (this.pieceAlliance != pieceOnCanidate.getAlliance() && board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this, canidateDestinationCoordinate,
										pieceOnCanidate));
							}
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

	}

	public static class FalconHunterKnight extends Knight {

		public FalconHunterKnight(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}
		public Collection<Move> calculateLegalMoves(final Board board) {
			final int[] CANIDATE_MOVE_COORDINATES = {-board.getNumberColumns()*2-1, -board.getNumberColumns()*2+1, -board.getNumberColumns()-2,
					-board.getNumberColumns()+2, board.getNumberColumns()-2, board.getNumberColumns()+2, board.getNumberColumns()*2-1,
					board.getNumberColumns()*2+1, board.getNumberColumns()-3, board.getNumberColumns()+3, 4*board.getNumberColumns()+1, 4*board.getNumberColumns()-1, 
					-board.getNumberColumns()+3, -board.getNumberColumns()-3, -4*board.getNumberColumns()+1, -4*board.getNumberColumns()-1};
			final List<Move> legalMoves = new ArrayList<>();
			
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
					
					if((currentCanidateOffset == board.getNumberColumns()-3 || 
							currentCanidateOffset ==  board.getNumberColumns()+3 ||
							currentCanidateOffset == 4*board.getNumberColumns()+1||
							currentCanidateOffset == 4*board.getNumberColumns()-1 || 
							currentCanidateOffset == -board.getNumberColumns()+3 || 
							currentCanidateOffset == -board.getNumberColumns()-3 ||
							currentCanidateOffset == -4*board.getNumberColumns()+1||
							currentCanidateOffset == -4*board.getNumberColumns()-1)) {	
						if (this.isFirstMove()) {
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
					} else {
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
			}
			return Collections.unmodifiableList(legalMoves);
		}
		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()*2-1 || canidateOffset == -board.getNumberColumns()-2 || canidateOffset == board.getNumberColumns()-2 || 
					canidateOffset == board.getNumberColumns()*2-1);
		}
		
		private static boolean isSecondColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.SECOND_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()-2 || 
					canidateOffset == board.getNumberColumns()-2 || canidateOffset == -board.getNumberColumns()-3 
					||canidateOffset == board.getNumberColumns()-3);
		}
		
		private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.SECOND_TO_LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()+2 || 
					canidateOffset == board.getNumberColumns()+2|| canidateOffset == -board.getNumberColumns()-3 
					||canidateOffset == board.getNumberColumns()+3);
		}
		private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()*2+1 || canidateOffset == -board.getNumberColumns()+2 || canidateOffset == board.getNumberColumns()+2 || 
					canidateOffset == board.getNumberColumns()*2+1);
		}
	}
	public static class FalconHunterBuilder extends Builder {
		@Override
		public Board build() {
			return new FalconHunterBoard(this);
		}
	}
}

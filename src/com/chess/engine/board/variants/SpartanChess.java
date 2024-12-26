package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.engine.player.variants.SpartanPlayer;

public class SpartanChess extends Board {

	public SpartanChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true, new WhitePlayerHandler<>(WhitePlayer.class),  
				new BlackPlayerHandler<>(SpartanPlayer.class));
	}

	public static Board createBoard() {
		final SpartanBuilder builder = new SpartanBuilder();
		// Black Layout
		builder.setPiece(new Liutenant(Alliance.BLACK, 0, true));
		builder.setPiece(new General(Alliance.BLACK, 1, true));
		builder.setPiece(new SpartanKing(Alliance.BLACK, 2, true));
		builder.setPiece(new Captian(Alliance.BLACK, 3, true));
		builder.setPiece(new Captian(Alliance.BLACK, 4, true));
		builder.setPiece(new SpartanKing(Alliance.BLACK, 5, true));
		builder.setPiece(new Warlord(Alliance.BLACK, 6, true));
		builder.setPiece(new Liutenant(Alliance.BLACK, 7, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 8, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 9, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 10, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 11, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 12, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 13, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 14, true));
		builder.setPiece(new SpartanPawn(Alliance.BLACK, 15, true));
		// White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
		builder.setPiece(new Rook(Alliance.WHITE, 63, true));

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class SpartanBuilder extends Builder {
		@Override
		public Board build() {
			return new SpartanChess(this);
		}
	}

	public static class General extends Piece {

		protected General(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.GENERAL, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public General movePiece(Move move) {
			return new General(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			final int[] CANIDATE_MOVE_COORDINATE = { -board.getNumberColumns() - 1, -board.getNumberColumns() + 1,
					board.getNumberColumns() - 1, board.getNumberColumns() + 1 };
			final List<Move> legalMoves = new ArrayList<>();

			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATE) {
				final int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					if (isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board)
							|| isLastColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
						continue;
					}
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
					}
				}

			}
			legalMoves.addAll(PieceUtils.calculateOrthogonalMoves(board, this));
			return Collections.unmodifiableList(legalMoves);
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() - 1
					|| canidateOffset == -1 || canidateOffset == board.getNumberColumns() - 1);
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() + 1
					|| canidateOffset == 1 || canidateOffset == board.getNumberColumns() + 1);
		}

		@Override
		public String toString() {
			return PieceType.GENERAL.toString();
		}

	}

	public static class SpartanKing extends Piece {

		public SpartanKing(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SPARTAN_KING, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public SpartanKing movePiece(Move move) {
			return new SpartanKing(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			final int[] CANIDATE_MOVE_COORDINATE = { -board.getNumberColumns() - 1, -board.getNumberColumns(),
					-board.getNumberColumns() + 1, -1, 1, board.getNumberColumns() - 1, board.getNumberColumns(),
					board.getNumberColumns() + 1 };
			final List<Move> legalMoves = new ArrayList<>();

			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATE) {
				final int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					if (isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board)
							|| isLastColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
						continue;
					}
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
					}
				}

			}

			return Collections.unmodifiableList(legalMoves);
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() - 1
					|| canidateOffset == -1 || canidateOffset == board.getNumberColumns() - 1);
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() + 1
					|| canidateOffset == 1 || canidateOffset == board.getNumberColumns() + 1);
		}

		@Override
		public String toString() {
			return PieceType.SPARTAN_KING.toString();
		}

	}

	public static class Warlord extends Piece {

		protected Warlord(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.WARLORD, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Warlord movePiece(Move move) {
			return new Warlord(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			final List<Move> legalMoves = new ArrayList<>();
			legalMoves.addAll(PieceUtils.calculate2To1PatternLeaper(board, this));
			legalMoves.addAll(PieceUtils.calculateDiagonalMoves(board, this));

			return Collections.unmodifiableList(legalMoves);
		}

		@Override
		public String toString() {
			return PieceType.WARLORD.toString();
		}

	}

	public static class Captian extends Piece {

		protected Captian(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.CAPTIAN, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Captian movePiece(Move move) {
			return new Captian(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			final List<Move> legalMoves = new ArrayList<>();
			final int[] CANIDATE_MOVE_COORDINATES = { -2, -1, 1, 2, -board.getNumberColumns(),
					-2 * board.getNumberColumns(), board.getNumberColumns(), 2 * board.getNumberColumns()};

			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {

					if (isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board)
							|| isEightColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
						continue;
					}

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
					}
				}
			}

			return Collections.unmodifiableList(legalMoves);
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -2 || canidateOffset == -1);
		}

		private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == 2 || canidateOffset == 1);
		}

		@Override
		public String toString() {
			return PieceType.CAPTIAN.toString();
		}

	}

	public static class Liutenant extends Piece {

		protected Liutenant(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.LIEUTENANT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Liutenant movePiece(Move move) {
			return new Liutenant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()+1, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(board.getNumberColumns()-1, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(-board.getNumberColumns()+1, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(-board.getNumberColumns()-1, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(2*board.getNumberColumns()-2, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(2*board.getNumberColumns()+2, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(-2*board.getNumberColumns()-2, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(-2*board.getNumberColumns()+2, MotionType.Jump, AttackType.MotionOnly),
					new Vectors(-1, MotionType.Jump, AttackType.AttackOnly),
					new Vectors(1, MotionType.Jump, AttackType.AttackOnly),

			};
			return PieceUtils.moveMaker(vectors, board, this);
//			final List<Move> legalMoves = new ArrayList<>();
//			final int[] CANIDATE_MOVE_COORDINATES = { board.getNumberColumns() + 1, board.getNumberColumns() - 1,
//					2 * board.getNumberColumns() - 2, 2 * board.getNumberColumns() + 2, -board.getNumberColumns() + 1,
//					-board.getNumberColumns() - 1, -2 * board.getNumberColumns() - 2, -2 * board.getNumberColumns() + 2,
//					1, -1 };
//
//			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATES) {
//				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
//				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
//
//					if (isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board)
//							|| isSecondColumnExclusion(this.piecePosition, currentCanidateOffset, board)
//							|| isSeventhColumnExclusion(this.piecePosition, currentCanidateOffset, board)
//							|| isEightColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
//						continue;
//					}
//
//					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
//
//					if (!canidateDestinationTile.isTileOccupied()) {
//						if(!(currentCanidateOffset == 1 || currentCanidateOffset == -1)) {
//							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));	
//						} 
//					} else {
//						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
//						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
//						if (this.pieceAlliance != pieceAlliance) {
//							legalMoves.add(
//									new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
//						}
//					}
//				}
//			}

//			return Collections.unmodifiableList(legalMoves);
		}

//		private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset, final Board board) {
//		    // First column: Can't capture left, restrict leftward diagonal jumps
//		    return board.FIRST_COLUMN[currentPosition] && (
//		        candidateOffset == -1 ||                               // Can't capture left in the first column
//		        candidateOffset == -board.getNumberColumns() - 1 ||  // Jump 1 space diagonally to the left
//		        candidateOffset == 2 * board.getNumberColumns() - 2 || // Jump 2 spaces diagonally to the left
//		        candidateOffset == board.getNumberColumns() - 1 ||
//		        candidateOffset == -2*board.getNumberColumns()-2
//		    );
//		}
//
//		private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset, final Board board) {
//		    // Second column: Restrict certain leftward jumps, including diagonal jumps that exceed bounds
//		    return board.SECOND_COLUMN[currentPosition] && (
//		    		candidateOffset == 2 * board.getNumberColumns() - 2 || // Jump 2 spaces diagonally to the left
//			        candidateOffset == -2*board.getNumberColumns()-2// || // Jump 2 spaces diagonally to the left
////		        candidateOffset == -(board.getNumberColumns() + 1)       // Jump 1 space diagonally to the left
//		    );
//		}

//		private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset, final Board board) {
//		    // Seventh column: Can't capture right, restrict certain rightward jumps
//		    return board.SECOND_TO_LAST_COLUMN[currentPosition] && (
//		        candidateOffset == -2*board.getNumberColumns()+2 ||                                // Can't capture right in the seventh column
////		        candidateOffset == (board.getNumberColumns() + 1) ||   // Jump 1 space diagonally to the right
//		        candidateOffset == 2 * board.getNumberColumns() + 2  // Jump 2 spaces diagonally to the right
//		    );
//		}

//		private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset, final Board board) {
//		    // Eighth column: Can't capture right, restrict diagonal rightward jumps
//		    return board.LAST_COLUMN[currentPosition] && (
//			        candidateOffset == 1 ||                               // Can't capture left in the first column
//			        candidateOffset == -board.getNumberColumns() + 1 ||  // Jump 1 space diagonally to the left
//			        candidateOffset == 2 * board.getNumberColumns() + 2 || // Jump 2 spaces diagonally to the left
//			        candidateOffset == board.getNumberColumns() + 1 ||
//			        candidateOffset == -2*board.getNumberColumns()+2
//		    );
//		}


		@Override
		public String toString() {
			return PieceType.LIEUTENANT.toString();
		}

	}
	public static class SpartanPawn extends Pawn {

		public SpartanPawn(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}
		
		@Override 
		public String toString() {
			return PieceType.BEROLINA_PAWN.toString();
		}
		@Override
		public SpartanPawn movePiece(final Move move) {
			return new SpartanPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
					((board.getNumberColumns()*2)-2), ((board.getNumberColumns()*2)+2)};
			final List<Move> legalMoves = new ArrayList<>();
			for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
				if(!board.isValidTileCoordinate(canidateDestinationCoordinate) || 
						isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board, this.pieceAlliance) ||
						isSecondColumnExclusion(this.piecePosition, currentCanidateOffset, board, this.pieceAlliance)|| 
						isSeventhColumnExclusion(this.piecePosition, currentCanidateOffset, board, this.pieceAlliance)||
						isEightColumnExclusion(this.piecePosition, currentCanidateOffset, board, this.pieceAlliance)) {
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
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == ((board.getNumberColumns()*2)-2) && this.isFirstMove()&& 
						((board.SECOND_ROW[this.piecePosition]&&this.getAlliance().isBlack()) ||
						(board.SECOND_LAST_ROW[this.piecePosition]&& this.getAlliance().isWhite())) && board.canPawnJump()) {
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
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
}

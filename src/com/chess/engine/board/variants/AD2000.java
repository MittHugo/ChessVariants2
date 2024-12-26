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
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.pieces.*;

public class AD2000 extends Board {

	public AD2000(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}

	public static Board createBoard() {
		final AD2000Builder builder = new AD2000Builder();
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

	public static class AD2000Builder extends Builder {
		@Override
		public Board build() {
			return new AD2000(this);
		}
	}

	public class Emperess extends Piece {

		public Emperess(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.EMPERESS, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			final int[] CANIDATE_MOVE_VECTOR_COORDINATES = { -board.getNumberColumns() - 1, -board.getNumberColumns(),
					-board.getNumberColumns() + 1, -1, 1, board.getNumberColumns() - 1, board.getNumberColumns(),
					board.getNumberColumns() + 1 };
			final List<Move> legalMoves = new ArrayList<>();

			for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition;

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
							if (this.pieceAlliance != pieceAlliance
									&& pieceAtDestination.getPieceType() != PieceType.GORGON) {
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
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() - 1
					|| canidateOffset == board.getNumberColumns() - 1 || canidateOffset == -1);
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() + 1
					|| canidateOffset == board.getNumberColumns() + 1 || canidateOffset == 1);
		}

		@Override
		public String toString() {
			return PieceType.EMPERESS.toString();
		}

		@Override
		public Emperess movePiece(final Move move) {
			return new Emperess(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

	}

	public class AD2000Pawn extends Pawn {

		public AD2000Pawn(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public AD2000Pawn movePiece(Move move) {
			return new AD2000Pawn(this.getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = { board.getNumberColumns() - 1, board.getNumberColumns(),
					board.getNumberColumns() + 1, };
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
					} else if (board.getTile(canidateDestinationCoordinate).isTileOccupied() && 
							board.getTile(canidateDestinationCoordinate).getPiece().getPieceType() != PieceType.GORGON) {
						if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
								&& board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new AttackMove(board, this, canidateDestinationCoordinate, 
									board.getTile(canidateDestinationCoordinate).getPiece()));
						}
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() - 1)
						&& !((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite()
								|| (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCanidate.getAlliance() && pieceOnCanidate.getPieceType() != PieceType.GORGON) {
						if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
								&& board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					}
				} else if (currentCanidateOffset == (board.getNumberColumns() + 1)
						&& !((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack()
								|| (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
					final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCanidate.getAlliance() && pieceOnCanidate.getPieceType() !=PieceType.GORGON) {
						if (this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
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

	}

	public class Attendant extends Piece {

		public Attendant(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ATTENDANT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
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
						if (this.pieceAlliance != pieceAlliance
								&& pieceAtDestination.getPieceType() != PieceType.GORGON) {
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
			return PieceType.ATTENDANT.toString();
		}

		@Override
		public Attendant movePiece(final Move move) {
			return new Attendant(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

	}

}

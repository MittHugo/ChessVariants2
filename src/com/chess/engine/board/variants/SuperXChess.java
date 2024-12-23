package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Piece.PieceType;

public class SuperXChess extends Board {

	public SuperXChess(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}

	public static Board createBoard() {
		final SuperXBuilder builder = new SuperXBuilder();
		// Black Layout
		builder.setPiece(new SuperXRook(Alliance.BLACK, 0, true));
		builder.setPiece(new SuperXKnight(Alliance.BLACK, 1, true));
		builder.setPiece(new SuperXBishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new SuperXBishop(Alliance.BLACK, 5, true));
		builder.setPiece(new SuperXKnight(Alliance.BLACK, 6, true));
		builder.setPiece(new SuperXRook(Alliance.BLACK, 7, true));
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
		builder.setPiece(new SuperXRook(Alliance.WHITE, 56, true));
		builder.setPiece(new SuperXKnight(Alliance.WHITE, 57, true));
		builder.setPiece(new SuperXBishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new SuperXBishop(Alliance.WHITE, 61, true));
		builder.setPiece(new SuperXKnight(Alliance.WHITE, 62, true));
		builder.setPiece(new SuperXRook(Alliance.WHITE, 63, true));

		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class SuperXBuilder extends Builder {
		@Override
		public SuperXChess build() {
			return new SuperXChess(this);
		}
	}

	public static class SuperXKnight extends Piece {
		public SuperXKnight(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(PieceType.SUPERX_KNIGHT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			final int[] CANIDATE_MOVE_COORDINATES = { -board.getNumberColumns() * 2 - 1,
					-board.getNumberColumns() * 2 + 1, -board.getNumberColumns() - 2, -board.getNumberColumns() + 2,
					board.getNumberColumns() - 2, board.getNumberColumns() + 2, board.getNumberColumns() * 2 - 1,
					board.getNumberColumns() * 2 + 1 };
			final List<Move> legalMoves = new ArrayList<>();

			for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {

					if (isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board)
							|| isSecondColumnExclusion(this.piecePosition, currentCanidateOffset, board)
							|| isSeventhColumnExclusion(this.piecePosition, currentCanidateOffset, board)
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
						} else if (pieceAtDestination.getPieceType() == PieceType.SUPERX_BISHOP
								|| pieceAtDestination.getPieceType() == PieceType.SUPERX_ROOK) {
							legalMoves.add(
									new SuperXMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

		@Override
		public String toString() {
			return PieceType.SUPERX_KNIGHT.toString();
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() * 2 - 1
					|| canidateOffset == -board.getNumberColumns() - 2 || canidateOffset == board.getNumberColumns() - 2
					|| canidateOffset == board.getNumberColumns() * 2 - 1);
		}

		private static boolean isSecondColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.SECOND_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() - 2
					|| canidateOffset == board.getNumberColumns() - 2);
		}

		private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.SECOND_TO_LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() + 2
					|| canidateOffset == board.getNumberColumns() + 2);
		}

		private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns() * 2 + 1
					|| canidateOffset == -board.getNumberColumns() + 2 || canidateOffset == board.getNumberColumns() + 2
					|| canidateOffset == board.getNumberColumns() * 2 + 1);
		}

		@Override
		public SuperXKnight movePiece(final Move move) {
			return new SuperXKnight(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
	}

	public static class SuperXRook extends Piece {

		public SuperXRook(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SUPERX_ROOK, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_VECTOR_COORDINATES = { -board.getNumberColumns(), -1, 1, board.getNumberColumns() };
			final List<Move> legalMoves = new ArrayList<>();

			for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
				int canidateDestinationCoordinate = this.piecePosition;

				while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					if (isFirstColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)
							|| isLastColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)) {
//						System.out.println("here");
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
							} else if (pieceAtDestination.getPieceType() == PieceType.SUPERX_BISHOP
									|| pieceAtDestination.getPieceType() == PieceType.SUPERX_KNIGHT) {
								legalMoves.add(
										new SuperXMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
							}
							break;
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

		@Override
		public String toString() {
			return PieceType.SUPERX_ROOK.toString();
		}

		private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.FIRST_COLUMN[currentPosition] && canidateOffset == -1;
		}

		private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset,
				final Board board) {
			return board.LAST_COLUMN[currentPosition] && canidateOffset == 1;
		}

		@Override
		public SuperXRook movePiece(final Move move) {
			return new SuperXRook(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
	}

	public static class SuperXBishop extends Piece {

		public SuperXBishop(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SUPERX_BISHOP, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_VECTOR_COORDINATES = { -(board.getNumberColumns() + 1), -(board.getNumberColumns() - 1),
					board.getNumberColumns() - 1, board.getNumberColumns() + 1 };
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
							if (this.pieceAlliance != pieceAlliance) {
								legalMoves.add(
										new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
							} else if (pieceAtDestination.getPieceType() == PieceType.SUPERX_KNIGHT
									|| pieceAtDestination.getPieceType() == PieceType.SUPERX_ROOK) {
								legalMoves.add(
										new SuperXMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
							}
							break;
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}

		@Override
		public String toString() {
			return PieceType.SUPERX_BISHOP.toString();
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
		public SuperXBishop movePiece(final Move move) {
			return new SuperXBishop(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}
	}
	public static class SuperXMove extends Move {
		final Piece combinedPiece;
		final Board thisBoard;
		final Piece thisPiece;
		final int thisDestinationCoordinate;

		public SuperXMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Piece combinedPiece) {
			super(board, piece, destinationCoordinate);
			this.thisBoard = board;
			this.combinedPiece = combinedPiece;
			this.thisPiece = piece;
			this.thisDestinationCoordinate = destinationCoordinate;
		}

		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
//			System.out.println(board.currentPlayer().getAlliance());
			for (final Piece piece : thisBoard.currentPlayer().getActivePieces()) {
				System.out.println(combinedPiece.equals(piece));
				if (!this.thisPiece.equals(piece) && !combinedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}

			for (final Piece piece : thisBoard.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}

			builder.setPiece(getCombinedPiece(thisPiece, combinedPiece.getPieceType(), thisDestinationCoordinate));
			builder.setMoveMaker(thisBoard.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}

		private Piece getCombinedPiece(Piece piece, PieceType movedPiece, int destinationCoordinate) {
		    // Combine Rook and Bishop to make Queen
		    if (piece.getPieceType() == PieceType.SUPERX_ROOK && movedPiece == PieceType.SUPERX_BISHOP) {
		        return new Queen(piece.getAlliance(), destinationCoordinate, false);
		    }
		    // Combine Rook and Knight to make Chancellor
		    else if (piece.getPieceType() == PieceType.SUPERX_ROOK && movedPiece == PieceType.SUPERX_KNIGHT) {
		        return new Chancellor(piece.getAlliance(), destinationCoordinate, false);
		    } else if (movedPiece == PieceType.SUPERX_ROOK && piece.getPieceType() == PieceType.SUPERX_KNIGHT) {
		        return new Chancellor(piece.getAlliance(), destinationCoordinate, false);
		    }
		    // Combine Bishop and Knight to make Archbishop
		    else if (piece.getPieceType() == PieceType.SUPERX_BISHOP && movedPiece == PieceType.SUPERX_KNIGHT) {
		        return new Archbishop(piece.getAlliance(), destinationCoordinate, false);
		    } else if (movedPiece == PieceType.SUPERX_BISHOP && piece.getPieceType() == PieceType.SUPERX_KNIGHT) {
		        return new Archbishop(piece.getAlliance(), destinationCoordinate, false);
		    }
		    // Combine Bishop and Rook to make Queen (reverse of the first case)
		    else if (piece.getPieceType() == PieceType.SUPERX_BISHOP && movedPiece == PieceType.SUPERX_ROOK) {
		        return new Queen(piece.getAlliance(), destinationCoordinate, false);
		    }
		    throw new RuntimeException("Not valid combination");
		}

	}

}

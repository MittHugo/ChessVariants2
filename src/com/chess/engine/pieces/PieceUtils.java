package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AtomicMove;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Vectors.AttackType;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.gui.Table;

public class PieceUtils {

	public static boolean isException(Board board, int vector, int piecePosition) {
		if(!board.isValidTileCoordinate(piecePosition)) {
			return true;
		}
		if(board.isValidTileCoordinate(piecePosition+vector)) {
			if(board.getTile(piecePosition+vector).isNullTile()) {
				return true;
			}
		}
		int remanider = vector;
		while (Math.abs(remanider) > board.getNumberColumns()) {
			if (remanider < 0) {
				remanider += board.getNumberColumns();
			} else {
				remanider -= board.getNumberColumns();
			}
		}

		int columnNumber = 0;
		for (int i = 0; i < board.isInDesiredColumn.length; i++) {
			if (board.isInDesiredColumn[i][piecePosition]) {
				columnNumber = i;
			}
		}
		if (Math.abs(remanider) >= (board.getNumberColumns() / 2)) {
			if (remanider > 0) {
				remanider -= board.getNumberColumns();
			} else {
				remanider += board.getNumberColumns();
			}
		}
		boolean value = false;
		if (remanider < 0 && Math.abs(remanider) > columnNumber) {
			value = true;
		} else if (remanider > 0 && remanider >= (board.getNumberColumns() - columnNumber)) {
			value = true;
		}
		return value;
	}

	public static Collection<Move> calculateOrthogonalMoves(final Board board, final Piece piece) {
		int[] CANIDATE_MOVE_VECTOR_COORDINATES = { -board.getNumberColumns(), -1, 1, board.getNumberColumns() };
		final List<Move> legalMoves = new ArrayList<>();

		for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
			int canidateDestinationCoordinate = piece.piecePosition;

			while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				if (PieceUtils.isException(board,canidateCoordinateOffset,canidateDestinationCoordinate)) {
					break;
				}

				canidateDestinationCoordinate += canidateCoordinateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

					if (!canidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
						if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
							legalMoves.add(
									new AttackMove(board, piece, canidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}

	public static Collection<Move> calculateDiagonalMoves(final Board board, final Piece piece) {
		int[] CANIDATE_MOVE_VECTOR_COORDINATES = { -(board.getNumberColumns() + 1), -(board.getNumberColumns() - 1),
				board.getNumberColumns() - 1, board.getNumberColumns() + 1 };
		final List<Move> legalMoves = new ArrayList<>();

		for (final int canidateCoordinateOffset : CANIDATE_MOVE_VECTOR_COORDINATES) {
			int canidateDestinationCoordinate = piece.piecePosition;

			while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
//				boolean isFirstColumnExclusion = board.FIRST_COLUMN[canidateDestinationCoordinate]
//						&& (canidateCoordinateOffset == -(board.getNumberColumns() + 1)
//								|| canidateCoordinateOffset == (board.getNumberColumns() - 1));
//				boolean isLastColumnExclusion = board.LAST_COLUMN[canidateDestinationCoordinate]
//						&& (canidateCoordinateOffset == -(board.getNumberColumns() - 1)
//								|| canidateCoordinateOffset == (board.getNumberColumns() + 1));
				if (PieceUtils.isException(board,canidateCoordinateOffset,canidateDestinationCoordinate)) {
					break;
				}

				canidateDestinationCoordinate += canidateCoordinateOffset;
				if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

					if (!canidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
						if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
							legalMoves.add(
									new AttackMove(board, piece, canidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}

	public static Collection<Move> calculate2To1PatternLeaper(final Board board, final Piece piece) {
		final int[] CANIDATE_MOVE_COORDINATES = { -board.getNumberColumns() * 2 - 1, -board.getNumberColumns() * 2 + 1,
				-board.getNumberColumns() - 2, -board.getNumberColumns() + 2, board.getNumberColumns() - 2,
				board.getNumberColumns() + 2, board.getNumberColumns() * 2 - 1, board.getNumberColumns() * 2 + 1 };
		final List<Move> legalMoves = new ArrayList<>();

		for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATES) {
			int canidateDestinationCoordinate = piece.piecePosition + currentCanidateOffset;

			if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				if (PieceUtils.isException(board,currentCanidateOffset,piece.piecePosition)) {
					continue;
				}

				final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

				if (!canidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = canidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getAlliance();
					if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
						legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}

	public static Collection<Move> calculate3To1PatternLeaper(final Board board, final Piece piece) {
		final int[] CANIDATE_MOVE_COORDINATES = { 3 * board.getNumberColumns() - 1, 3 * board.getNumberColumns() + 1,
				board.getNumberColumns() + 3, board.getNumberColumns() - 3, -3 * board.getNumberColumns() - 1,
				-3 * board.getNumberColumns() + 1, -board.getNumberColumns() + 3, -board.getNumberColumns() - 3 };
		final List<Move> legalMoves = new ArrayList<>();

		for (final int currentCanidateOffset : CANIDATE_MOVE_COORDINATES) {
			int canidateDestinationCoordinate = piece.piecePosition + currentCanidateOffset;

			if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
//				boolean isFirstColumnExclusion = board.FIRST_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == -3 * board.getNumberColumns() - 1
//								|| currentCanidateOffset == 3 * board.getNumberColumns() - 1
//								|| currentCanidateOffset == board.getNumberColumns() - 3
//								|| currentCanidateOffset == -board.getNumberColumns() - 3);
//				boolean isSecondColumnExclusion = board.SECOND_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == board.getNumberColumns() - 3
//								|| currentCanidateOffset == -board.getNumberColumns() - 3);
//				boolean isThirdColumnExclusion = board.THIRD_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == board.getNumberColumns() - 3
//								|| currentCanidateOffset == -board.getNumberColumns() - 3);
//				boolean isSixthColumnExclusion = board.THIRD_TO_LAST_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == board.getNumberColumns() + 3
//								|| currentCanidateOffset == -board.getNumberColumns() + 3);
//				boolean isSeventhColumnExclusion = board.SECOND_TO_LAST_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == board.getNumberColumns() + 3
//								|| currentCanidateOffset == -board.getNumberColumns() + 3);
//				boolean isEigthColumnExclusion = board.LAST_COLUMN[piece.piecePosition]
//						&& (currentCanidateOffset == -3 * board.getNumberColumns() + 1
//								|| currentCanidateOffset == 3 * board.getNumberColumns() + 1
//								|| currentCanidateOffset == board.getNumberColumns() + 3
//								|| currentCanidateOffset == -board.getNumberColumns() + 3);

				if (PieceUtils.isException(board, currentCanidateOffset, piece.piecePosition)) {
					continue;
				}

				final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

				if (!canidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = canidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getAlliance();
					if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
						legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}

	public static Collection<Move> moveMaker(final Vectors[] vectors, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		for (Vectors vector : vectors) {
			if (vector.motionType == MotionType.Itterative) {
				if(vector.firstMoveOnlyVector && piece.isFirstMove()) {
					legalMoves.addAll(itterativeMoveMaker(vector, board, piece));
				} else if(!vector.firstMoveOnlyVector) {
					legalMoves.addAll(itterativeMoveMaker(vector, board, piece));
				}
			} else if (vector.motionType == MotionType.Jump) {
				if(vector.firstMoveOnlyVector && piece.isFirstMove()) {
					legalMoves.addAll(jumpMoveMaker(vector, board, piece));
				} else if(!vector.firstMoveOnlyVector) {
					legalMoves.addAll(jumpMoveMaker(vector, board, piece));
				}
			} else if (vector.motionType == MotionType.MotionIfClear) {
				if(vector.firstMoveOnlyVector && piece.isFirstMove()) {
					legalMoves.addAll(motionIfClearMoveMaker(vector, board, piece));
				} else if(!vector.firstMoveOnlyVector) {
					legalMoves.addAll(motionIfClearMoveMaker(vector, board, piece));
				}
			} else if (vector.motionType == MotionType.JumpToItterative) {
				if(vector.firstMoveOnlyVector && piece.isFirstMove()) {
					legalMoves.addAll(jumpToItterativeMoveMaker(vector, board, piece));
				} else if(!vector.firstMoveOnlyVector) {
					legalMoves.addAll(jumpToItterativeMoveMaker(vector, board, piece));
				}
			} else if (vector.motionType == MotionType.CheckerLikeJump) {
				if(vector.firstMoveOnlyVector && piece.isFirstMove()) {
					legalMoves.addAll(checkerLikeJumpMoveMaker(vector, board, piece));
				} else if(!vector.firstMoveOnlyVector) {
					legalMoves.addAll(checkerLikeJumpMoveMaker(vector, board, piece));
				}
			}
		}
		return legalMoves;
	}

	private static Collection<Move> itterativeMoveMaker(final Vectors vector, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		int canidateDestinationCoordinate = piece.piecePosition;
		while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
			if (PieceUtils.isException(board, vector.vector, canidateDestinationCoordinate)) {
				break;
			}
			canidateDestinationCoordinate += vector.vector;
			if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

				if (!canidateDestinationTile.isTileOccupied() && (vector.attackType == AttackType.MotionOnly
						|| vector.attackType == AttackType.Both)) {
					legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
				} else if (canidateDestinationTile.isTileOccupied()) {
					if(vector.attackType != AttackType.MotionOnly) {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
						if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
							if(Table.get().isAtomic()) {
								legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
							} else {
								legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
									pieceAtDestination));
							}
						}
					}
					break;
				}
			}
		}
		return legalMoves;
	}
	
	private static Collection<Move> jumpMoveMaker(final Vectors vector, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		int canidateDestinationCoordinate = piece.piecePosition + vector.vector;
		if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {

			if (PieceUtils.isException(board, vector.vector, piece.piecePosition)) {
				return new ArrayList<>();
			}

			final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);

			if (!canidateDestinationTile.isTileOccupied()
					&& (vector.attackType == AttackType.MotionOnly || vector.attackType == AttackType.Both)) {
				legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
			} else if (canidateDestinationTile.isTileOccupied()
					&& (vector.attackType == AttackType.AttackOnly || vector.attackType == AttackType.Both)) {
				final Piece pieceAtDestination = canidateDestinationTile.getPiece();
				final Alliance pieceAlliance = pieceAtDestination.getAlliance();
				if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
					if(Table.get().isAtomic()) {
						legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
					} else {
						legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
							pieceAtDestination));
					}
				}
			}
		}
		return legalMoves;
	}
	
	private static Collection<Move> motionIfClearMoveMaker(final Vectors vector, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		int canidateDestinationCoordinate = piece.piecePosition + vector.vector;
		boolean isBehindVectorsOccupied = false;
		for(int tilePosition: vector.behindVector) {
			if(board.getTile(piece.piecePosition + tilePosition).isTileOccupied()) {
				isBehindVectorsOccupied = true;
			}
		}
		if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
			if (PieceUtils.isException(board, vector.vector, piece.piecePosition)) {
				return new ArrayList<>();
			}
			
			final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
			if (!isBehindVectorsOccupied) {
				if (!canidateDestinationTile.isTileOccupied() && (vector.attackType == AttackType.MotionOnly
						|| vector.attackType == AttackType.Both)) {
					legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
				} else if (canidateDestinationTile.isTileOccupied()
						&& (vector.attackType == AttackType.AttackOnly
								|| vector.attackType == AttackType.Both)) {
					final Piece pieceAtDestination = canidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getAlliance();
					if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
						if(Table.get().isAtomic()) {
							legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
						} else {
							legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
								pieceAtDestination));
						}
					}
				}
			}
		}
		return legalMoves;
	}
	
	private static Collection<Move> checkerLikeJumpMoveMaker(final Vectors vector, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		int canidateDestinationCoordinate = piece.piecePosition;
		int numPreTravel = 0;
		while (board.isValidTileCoordinate(canidateDestinationCoordinate + vector.vector)
				&& board.isValidTileCoordinate(canidateDestinationCoordinate + 2 * vector.vector)
				&& numPreTravel < vector.preTravel) {
			if (PieceUtils.isException(board, vector.vector, canidateDestinationCoordinate) || PieceUtils
					.isException(board, vector.vector, canidateDestinationCoordinate + vector.vector)) {
				break;
			}
			numPreTravel++;
			canidateDestinationCoordinate += vector.vector;
			if (board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
				if (vector.hopAgain) {
					if (vector.postTravel-1 > 0 && vector.numberOfJumps-1 > 0) {
						// TODO Any double jump is considered an illegal Move
						int nextCanidateDestinationCoordinate = canidateDestinationCoordinate+ vector.vector;
						if (!board.isValidTileCoordinate(nextCanidateDestinationCoordinate) || PieceUtils
								.isException(board, vector.vector, canidateDestinationCoordinate)) {
							break;
						}
						final Tile tile = board.getTile(nextCanidateDestinationCoordinate);
						if (!tile.isTileOccupied() && vector.attackType != AttackType.AttackOnly) {
							legalMoves.add(new MajorMove(board, piece, nextCanidateDestinationCoordinate));
						} else {
							final Piece pieceAtDestination = tile.getPiece();
							final Alliance pieceAlliance = pieceAtDestination.getAlliance();
							if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())&& vector.attackType != AttackType.MotionOnly) {
								if(Table.get().isAtomic()) {
									legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
								} else {
									legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
										pieceAtDestination));
								}
							}
						}
						Vectors[] instancVector = { 
								new Vectors(vector.vector, vector.postTravel , vector.postTravel - 1, vector.hopAgain, vector.numberOfJumps-1)
						};
						legalMoves.addAll(PieceUtils.moveMaker(instancVector, board, 
								piece.movePiece(new MajorMove(board, piece, nextCanidateDestinationCoordinate))));
					} 
				} else {
					int jumpCount =0;
					for (int postHopCount = 1; postHopCount < vector.postTravel + 1 && jumpCount < vector.numberOfJumps; postHopCount++) {
						int nextCanidateDestinationCoordinate = canidateDestinationCoordinate
								+ postHopCount * vector.vector;
						if (!board.isValidTileCoordinate(nextCanidateDestinationCoordinate) ||
								PieceUtils.isException(board, vector.vector*postHopCount, canidateDestinationCoordinate)) {
							break;
						}
						final Tile tile = board.getTile(nextCanidateDestinationCoordinate);
						if (!tile.isTileOccupied() && vector.attackType != AttackType.AttackOnly) {
							legalMoves.add(new MajorMove(board, piece, nextCanidateDestinationCoordinate));
							jumpCount++;
						} else if(tile.isTileOccupied() && vector.attackType != AttackType.MotionOnly) {
							final Piece pieceAtDestination = tile.getPiece();
							final Alliance pieceAlliance = pieceAtDestination.getAlliance();
							if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())&& vector.attackType != AttackType.MotionOnly) {
								if(Table.get().isAtomic()) {
									legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
								} else {
									legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
										pieceAtDestination));
								}
							}
							jumpCount++;
						}
					}
				}
				numPreTravel = Integer.MAX_VALUE;
			}
		}
		return legalMoves;
	}
	
	private static Collection<Move> jumpToItterativeMoveMaker(final Vectors vector, final Board board, final Piece piece) {
		List<Move> legalMoves = new ArrayList<>();
		int jumpCoordinate = piece.piecePosition + vector.vector;
		if (board.isValidTileCoordinate(jumpCoordinate)) {
			if (PieceUtils.isException(board, vector.vector, piece.piecePosition)) {
				return new ArrayList<>();
			}
			final Tile canidateDestinationTile = board.getTile(jumpCoordinate);

			if (!canidateDestinationTile.isTileOccupied() && !(vector.attackType == AttackType.AttackOnly)) {
				legalMoves.add(new MajorMove(board, piece, jumpCoordinate));
			} else if (canidateDestinationTile.isTileOccupied()
					&& !(vector.attackType == AttackType.MotionOnly)) {
				final Piece pieceAtDestination = canidateDestinationTile.getPiece();
				final Alliance pieceAlliance = pieceAtDestination.getAlliance();
				if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
					if(Table.get().isAtomic()) {
						legalMoves.add(new AtomicMove(board, piece, jumpCoordinate));
					} else {
						legalMoves.add(new AttackMove(board, piece, jumpCoordinate,
							pieceAtDestination));
					}
				}
			}
			if (!board.getTile(jumpCoordinate).isTileOccupied()) {
				for (int expansionVector : vector.expansionVectors) {
					int canidateDestinationCoordinate = jumpCoordinate;
					while (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
						if (PieceUtils.isException(board, expansionVector, canidateDestinationCoordinate)) {
							break;
						}
						canidateDestinationCoordinate += expansionVector;
						if (board.isValidTileCoordinate(canidateDestinationCoordinate)) {
							final Tile canidateDestinationTileExpanded = board
									.getTile(canidateDestinationCoordinate);

							if (!canidateDestinationTileExpanded.isTileOccupied()
									&& (vector.attackType == AttackType.MotionOnly
											|| vector.attackType == AttackType.Both)) {
								legalMoves.add(new MajorMove(board, piece, canidateDestinationCoordinate));
							} else if (canidateDestinationTileExpanded.isTileOccupied()
									&& (vector.attackType == AttackType.AttackOnly
											|| vector.attackType == AttackType.Both)) {
								final Piece pieceAtDestination = canidateDestinationTileExpanded.getPiece();
								final Alliance pieceAlliance = pieceAtDestination.getAlliance();
								if (piece.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
									if(Table.get().isAtomic()) {
										legalMoves.add(new AtomicMove(board, piece, canidateDestinationCoordinate));
									} else {
										legalMoves.add(new AttackMove(board, piece, canidateDestinationCoordinate,
											pieceAtDestination));
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		return legalMoves;
	}
	
	public static ArrayList<Vectors> diagonalJump(int diagonalNumber, Board board) {
		ArrayList<Vectors> legalMoves = new ArrayList<Vectors>();
		legalMoves.add(new Vectors(-diagonalNumber*board.getNumberColumns()-diagonalNumber, MotionType.Jump));
		legalMoves.add(new Vectors(-diagonalNumber*board.getNumberColumns()+diagonalNumber, MotionType.Jump));
		legalMoves.add(new Vectors(diagonalNumber*board.getNumberColumns()-diagonalNumber, MotionType.Jump));
		legalMoves.add(new Vectors(diagonalNumber*board.getNumberColumns()+diagonalNumber, MotionType.Jump));
		return legalMoves;
	}
	
	public static ArrayList<Vectors> orthongonalJump(int orthongonalNumber, Board board) {
		ArrayList<Vectors> legalMoves = new ArrayList<Vectors>();
		legalMoves.add(new Vectors(-orthongonalNumber, MotionType.Jump));
		legalMoves.add(new Vectors(orthongonalNumber, MotionType.Jump));
		legalMoves.add(new Vectors(orthongonalNumber*board.getNumberColumns(), MotionType.Jump));
		legalMoves.add(new Vectors(orthongonalNumber*board.getNumberColumns(), MotionType.Jump));
		return legalMoves;
	}
	
	public static ArrayList<Vectors> diagonalItterative(Board board) {
		ArrayList<Vectors> legalMoves = new ArrayList<Vectors>();
		legalMoves.add(new Vectors(-board.getNumberColumns()-1, MotionType.Itterative));
		legalMoves.add(new Vectors(-board.getNumberColumns()+1, MotionType.Itterative));
		legalMoves.add(new Vectors(board.getNumberColumns()-1, MotionType.Itterative));
		legalMoves.add(new Vectors(board.getNumberColumns()+1, MotionType.Itterative));
		return legalMoves;
	}
	
	public static ArrayList<Vectors> orthongonalItterative(Board board) {
		ArrayList<Vectors> legalMoves = new ArrayList<Vectors>();
		legalMoves.add(new Vectors(1, MotionType.Itterative));
		legalMoves.add(new Vectors(-1, MotionType.Itterative));
		legalMoves.add(new Vectors(board.getNumberColumns(), MotionType.Itterative));
		legalMoves.add(new Vectors(board.getNumberColumns(), MotionType.Itterative));
		return legalMoves;
	}
	public static ArrayList<Vectors> customKnightJump(int horizontalJump, int verticalJump, Board board) {
	    ArrayList<Vectors> legalMoves = new ArrayList<Vectors>();
	    int boardWidth = board.getNumberColumns();
	    
	    // Horizontal then vertical jumps
	    legalMoves.add(new Vectors(horizontalJump + verticalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(horizontalJump - verticalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(-horizontalJump + verticalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(-horizontalJump - verticalJump * boardWidth, MotionType.Jump));
	    
	    // Vertical then horizontal jumps
	    legalMoves.add(new Vectors(verticalJump + horizontalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(verticalJump - horizontalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(-verticalJump + horizontalJump * boardWidth, MotionType.Jump));
	    legalMoves.add(new Vectors(-verticalJump - horizontalJump * boardWidth, MotionType.Jump));

	    return legalMoves;
	}

	
	public static class NullPiece extends Piece {

		public NullPiece(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.BISHOP, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Piece movePiece(Move move) {
			return new NullPiece(this.pieceAlliance, 1, true);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			List<Move> legalMoves = new ArrayList<>();
			return legalMoves;
		}

	}
}

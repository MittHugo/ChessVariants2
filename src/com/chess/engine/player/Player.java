package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.gui.Table;
import com.chess.gui.Table.ChessType;
import com.chess.engine.board.Move;

public abstract class Player {
	protected Board board;
	protected Piece playerKing;
	protected Collection<Move> legalMoves;
	protected boolean isInCheck;

	protected Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.playerKing = establishKing();
//		this.builder = board.getBuilder();
		// ???
		Collection<Move> moves = new ArrayList<>();
		moves.addAll(legalMoves);
		if (board.canCastle()) {
			moves.addAll(calculateKingCalstles(legalMoves, opponentMoves));
		}
//		moves.addAll(calculateKingCalstles(legalMoves, opponentMoves));
		this.legalMoves = moves;
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}

	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move : moves) {
			if (piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableList(attackMoves);
	}

	protected Piece establishKing() {
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isKing()) {
				return piece;
			}
		}
		if(Table.get().getChessType() == ChessType.ConquerAll) {
			return new King(Alliance.BLACK, -1, false);
		} else {
			throw new RuntimeException("Shout not reach here! Not a valid board!");
		}
	}

	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}

	public boolean isInCheck() {
		return this.isInCheck;
	}

	public boolean isInCheckmate(BuildHandler handler) {
		return this.isInCheck && !hasEscapeMoves(handler);
	}

//	protected boolean hasEscapeMoves(BuildHandler handler) {
//		for (final Move move : this.legalMoves) {
//			final Board transitionBoard = move.execute(handler);
//			final Collection<Move> kingAttacks = getKingAttacks(transitionBoard);
//			if (kingAttacks.isEmpty()) {
//				return true;
//			}
//		}
//		return false;
//	}
    protected boolean hasEscapeMoves(BuildHandler handler) {
        return this.legalMoves.stream()
                              .anyMatch(move -> makeMove(move,handler)
                              .getMoveStatus().isDone());
    }

	public boolean isInStalemate(BuildHandler handler) {
		return !this.isInCheck && !hasEscapeMoves(handler);
	}

	public boolean isCastled() {
		return false;
	}

	public boolean hasAttackMove() {
		for (Move move : legalMoves) {
			if (move.isAttack()) {
				return true;
			}
		}
		return false;
	}

	public static Collection<Move> getKingAttacks(Board transitionBoard) {
		Collection<Move> kingAttacks = new ArrayList<>();
		Collection<Move> legalAttackMoves = new ArrayList<>();
		if (Table.get().getPlayerCount().isRegular()) {
			legalAttackMoves.addAll(transitionBoard.currentPlayer().getLegalMoves());
		} else if (Table.get().getPlayerCount().isFourPlayerOpponents()) {
			if (transitionBoard.currentPlayer().getPreviousPlayer().getAlliance().isWhite()
					|| transitionBoard.currentPlayer().getPreviousPlayer().getAlliance().isBlack()) {
				legalAttackMoves.addAll(transitionBoard.getBluePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getRedPlayer().getLegalMoves());
			} else {
				legalAttackMoves.addAll(transitionBoard.getBlackPlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getWhitePlayer().getLegalMoves());
			}
		} else {
			if (transitionBoard.currentPlayer().getPreviousPlayer().getAlliance().isWhite()) {
				legalAttackMoves.addAll(transitionBoard.getBluePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getRedPlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getBlackPlayer().getLegalMoves());
			} else if (transitionBoard.currentPlayer().getPreviousPlayer().getAlliance().isBlack()) {
				legalAttackMoves.addAll(transitionBoard.getBluePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getWhitePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getRedPlayer().getLegalMoves());
			} else if (transitionBoard.currentPlayer().getPreviousPlayer().getAlliance().isRed()) {
				legalAttackMoves.addAll(transitionBoard.getBluePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getWhitePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getBlackPlayer().getLegalMoves());
			} else {
				legalAttackMoves.addAll(transitionBoard.getBlackPlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getWhitePlayer().getLegalMoves());
				legalAttackMoves.addAll(transitionBoard.getRedPlayer().getLegalMoves());
			}
		}

		kingAttacks.addAll(Player.calculateAttacksOnTile(
				transitionBoard.currentPlayer().getPreviousPlayer().getPlayerKing().getPiecePosition(),
				legalAttackMoves));
		return kingAttacks;
	}

	public MoveTransition makeMove(final Move move, BuildHandler handler) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.IllegalMove);
		}
		final Board transitionBoard = move.execute(handler);
		if(Table.get().getChessType() != ChessType.ConquerAll) {
			if(!Player.getKingAttacks(transitionBoard).isEmpty()) {
				return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
			}
		}
		if(Table.get().getChessType() == ChessType.AnitChess) {
			if(!move.isAttack() && hasAttackMove()) {
				return new MoveTransition(this.board, move, MoveStatus.MUST_ATTACK);
			}
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}

	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}

	public Piece getPlayerKing() {
		return this.playerKing;
	}

	protected Rook[] getRooks() {
		Rook[] rooks = new Rook[2];
		int i = 0;
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isRook()) {
				rooks[i] = (Rook) piece;
				i++;
			}
		}
		return rooks;
	}

	protected boolean spacesBettweenEmptyOfAttacks(final Board board, final int rookLocation, final int kingLocation,
			final Collection<Move> opponentLegalMoves) {
		int delta;
		int startLocation;
		int startIndex;
		boolean returnValue = true;
		if (kingLocation > rookLocation) {
			delta = kingLocation - rookLocation;
			startLocation = rookLocation;
			startIndex = 1;
		} else {
			delta = rookLocation - kingLocation;
			startLocation = kingLocation;
			startIndex = 0;
		}
		for (int i = startIndex; i < delta + startIndex; i++) {
			if (board.getTile(i + startLocation).isTileOccupied() && i + startLocation != kingLocation) {
				returnValue = false;
			} else if (!Player.calculateAttacksOnTile(i + startLocation, opponentLegalMoves).isEmpty()) {
//				System.out.println("here");
				returnValue = false;
			}
		}
		return returnValue;
	}

	protected static int castleLocationOfKing(int kingLocation, int rookLocation) {
		int delta;
		if (kingLocation > rookLocation) {
			delta = kingLocation - rookLocation;
			System.out.println("Median Value" + findMedianValue(delta - 1));
			if (delta - 1 != 0) {
				return -findMedianValue(delta - 1) + kingLocation;
			}
		} else {
			delta = rookLocation - kingLocation;
			if (delta - 1 != 0) {
				return findMedianValue(delta - 1) + kingLocation;
			}
		}
		return 1;
	}

	protected static int castleLocationOfRook(int kingLocation, int rookLocation) {
		if (kingLocation > rookLocation) {
			return castleLocationOfKing(kingLocation, rookLocation) + 1;
		} else {
			return castleLocationOfKing(kingLocation, rookLocation) - 1;
		}
	}

	private static int findMedianValue(int value) {
		int[] values = new int[value];
		for (int i = 0; i < value; i++) {
			values[i] = i + 1;
		}
		int n = values.length;
//        System.out.println(values.length);

		if (n % 2 == 1) {
			// If the array has an odd number of elements, the median is the middle element
			return values[n / 2];
		} else {
			// If the array has an even number of elements, the median is the average of the
			// two middle elements
			System.out.println((int) Math.round((values[(n / 2) - 1] + values[n / 2]) / 2) + 1);
			return (int) Math.round((values[(n / 2) - 1] + values[n / 2]) / 2) + 1;
		}
	}

	public abstract Collection<Piece> getActivePieces();

	public abstract Alliance getAlliance();

	public abstract Player getOpponent();

	public abstract Player getNextPlayer();

	public abstract Player getPreviousPlayer();

	protected abstract Collection<Move> calculateKingCalstles(Collection<Move> playerLegals,
			Collection<Move> opponentsLegals);

}

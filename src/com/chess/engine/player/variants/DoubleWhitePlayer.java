package com.chess.engine.player.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.MoveStatus;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public class DoubleWhitePlayer extends WhitePlayer {
	Piece otherKing;
	boolean otherKingInCheck;
	
	public DoubleWhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
		otherKing = establishOtherKing();
		otherKingInCheck = !Player.calculateAttacksOnTile(this.otherKing.getPiecePosition(), whiteStandardLegalMoves).isEmpty();
		
	}

	@Override
	protected Piece establishKing() {
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isKing()) {
				return piece;
			}
		}
		return new King(Alliance.BLACK, -1, false);
	}

	protected Piece establishOtherKing() {
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isKing() && !piece.equals(playerKing)) {
				return piece;
			}
		}
		return new King(Alliance.BLACK, -1, false);
	}

	@Override
	public boolean isInCheck() {
		return this.isInCheck || otherKingInCheck;
	}

	@Override
	public boolean isInCheckmate(BuildHandler handler) {
		return (this.isInCheck && !hasEscapeMoves(handler) && otherKing.getPiecePosition() != -1) || 
				(this.isInCheck && !hasEscapeMoves(handler) && otherKingInCheck);
	}

	@Override
	public boolean isInStalemate(BuildHandler handler) {
		return (!this.isInCheck && !hasEscapeMoves(handler) && otherKingInCheck);
	}
	
	@Override 
	public MoveTransition makeMove(final Move move, BuildHandler handler) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.IllegalMove);
		}
		final Board transitionBoard = move.execute(handler);
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
				transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());
		if(!kingAttacks.isEmpty() && otherKing.getPiecePosition() == -1) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	@Override
	protected Collection<Move> calculateKingCalstles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		List<Move> moves = new ArrayList<Move>();
		return moves;
	}

}
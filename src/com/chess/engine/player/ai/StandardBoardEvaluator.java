package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;
import com.chess.gui.Table;
import com.chess.gui.Table.ChessType;

public class StandardBoardEvaluator implements BoardEvaluator {

	private static final int CHECK_BONUS = 10;
	private static final int CHECK_MATE_BONUS = 10000;
	private static final int CASTlE_BONUS = 60;

	@Override
	public int evaluate(Board board, int depth, BuildHandler handler) {
		return scorePlayer(board, board.whitePlayer(), depth, handler) -scorePlayer(board, board.blackPlayer(), depth, handler);
	}

	private int scorePlayer(Board board, Player player, int depth, BuildHandler handler) {
		return peiceValue(player)
				+ mobility(player) + check(player) + checkMate(player, depth, handler) + castled(player);
	}

	private int castled(Player player) {
		return player.isCastled() ? CASTlE_BONUS:0;
	}

	private int checkMate(Player player, int depth, BuildHandler handler) {
		if(Table.get().getChessType() == ChessType.ConquerAll) {
			return 0;
		} else {
			return player.getOpponent().isInCheckmate(handler) ? CHECK_MATE_BONUS*depthBonus(depth):0;
		}
	}

	private int depthBonus(int depth) {
		return depth == 1?1:100*depth;
	}

	private int check(Player player) {
		if(Table.get().getChessType() == ChessType.ConquerAll) {
			return 0;
		} else {
			return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
		}
	}

	private int mobility(Player player) {
		return player.getLegalMoves().size();
	}

	private static int peiceValue(Player player) {
		int pieceValueScore = 0;
		for (Piece piece: player.getActivePieces()) {
			pieceValueScore += piece.getPieceType().getPieceValue();
		}
		return pieceValueScore;
	}

}

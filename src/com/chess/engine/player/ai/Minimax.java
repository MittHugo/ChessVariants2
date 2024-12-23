//package com.chess.engine.player.ai;
//
//import com.chess.engine.board.Board;
//import com.chess.engine.board.BuildHandler;
//import com.chess.engine.board.Move;
//import com.chess.engine.player.MoveTransition;
//
//public class Minimax implements MoveStrategy {
//
//	private final BoardEvaluator boardEvaluator;
//
//	public Minimax() {
//		boardEvaluator = new StandardBoardEvaluator();
//	}
//
//	@Override
//	public String toString() {
//		return "Minimax";
//	}
//
//	@Override
//	public Move execute(Board board, int depth, BuildHandler handler) {
////		System.out.println("here");
//		final long startTime = System.currentTimeMillis();
//		
//		Move bestMove = null;	
//		int highestSeenValue = Integer.MIN_VALUE;
//		int lowestSeenValue = Integer.MAX_VALUE;
//		int currentValue;
//		
//		int numMoves = board.currentPlayer().getLegalMoves().size();
//		for(final Move move: board.currentPlayer().getLegalMoves()) {
//			MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
//			if(moveTransition.getMoveStatus().isDone()) {
//				currentValue = board.currentPlayer().getAlliance().isWhite() ?
//						min(moveTransition.getTransitionBoard(),depth -1, handler):
//						max(moveTransition.getTransitionBoard(),depth -1, handler);
//				if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
//					highestSeenValue = currentValue;
//					bestMove = move;
//				}else if(board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue) {
//					lowestSeenValue = currentValue;
//					bestMove = move;
//				}
//			}
//		}
//		final long executionTime  = System.currentTimeMillis() - startTime;
//		return bestMove;
//	}
//
//	public int min(final Board board, final int depth, final BuildHandler handler) {
//		if (depth == 0 || isEndGameSenario(board, depth)) {
//			return this.boardEvaluator.evaluate(board, depth);
//		}
//		int lowestSeenValue = Integer.MAX_VALUE;
//		for (Move move : board.currentPlayer().getLegalMoves()) {
//			final MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
//			if (moveTransition.getMoveStatus().isDone()) {
//				int currentValue = max(moveTransition.getTransitionBoard(), depth - 1, handler);
//				if (currentValue <= lowestSeenValue) {
//					lowestSeenValue = currentValue;
//				}
//			}
//		}
//		return lowestSeenValue;
//	}
//
//	private boolean isEndGameSenario(Board board, int depth) {
//		return board.currentPlayer().isInCheckmate() || board.currentPlayer().isInStalemate();
//	}
//
//	public int max(final Board board, final int depth, final BuildHandler handler) {
//		if (depth == 0 ||isEndGameSenario(board, depth)) {
//			return this.boardEvaluator.evaluate(board, depth);
//		}
//		int highestSeenValue = Integer.MIN_VALUE;
//		for (Move move : board.currentPlayer().getLegalMoves()) {
//			final MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
//			if (moveTransition.getMoveStatus().isDone()) {
//				int currentValue = min(moveTransition.getTransitionBoard(), depth - 1, handler);
//				if (currentValue >= highestSeenValue) {
//					highestSeenValue = currentValue;
//				}
//			}
//		}
//		return highestSeenValue;
//	}
//}
package com.chess.engine.player.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class Minimax implements MoveStrategy {
	private final int boardIndex = 0;
	private final int depthIndex = 1;
	private final int allianceIndex = 2;
	private final int moveIndex = 3;
	private final BoardEvaluator boardEvaluator;
	private final static String fileName = "ChessboardShortcut.csv";
	
	// Fix Alpha-beta pruning
	// Add starting things
	

	public Minimax() {
		boardEvaluator = new StandardBoardEvaluator();
	}

	@Override
	public String toString() {
		return "Minimax with Alpha-Beta Pruning";
	}

	@Override
	public Move execute(Board board, Integer depth, BuildHandler handler) {
		final long startTime = System.currentTimeMillis();

		Move bestMove = null;
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE;
		int currentValue;
		for (final Move move : board.currentPlayer().getLegalMoves()) {
			MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
			if (moveTransition.getMoveStatus().isDone()) {
				currentValue = board.currentPlayer().getAlliance().isWhite()
						? min(moveTransition.getTransitionBoard(), depth - 1, handler, Integer.MIN_VALUE,
								Integer.MAX_VALUE)
						: max(moveTransition.getTransitionBoard(), depth - 1, handler, Integer.MIN_VALUE,
								Integer.MAX_VALUE);

				if (board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
					highestSeenValue = currentValue;
					bestMove = move;
				} else if (board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue) {
					lowestSeenValue = currentValue;
					bestMove = move;
				}
			}
		}
//		String[] data = { board.toString(), depth.toString(), board.currentPlayer().toString(), bestMove.toString(), };
		final long executionTime = System.currentTimeMillis() - startTime;
		return bestMove;
	}

	public int min(final Board board, final int depth, final BuildHandler handler, int alpha, int beta) {
		if (depth == 0 || isEndGameScenario(board, handler)) {
			return this.boardEvaluator.evaluate(board, depth, handler);
		}
		int lowestSeenValue = Integer.MAX_VALUE;

		for (Move move : board.currentPlayer().getLegalMoves()) {
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
			if (moveTransition.getMoveStatus().isDone()) {
				int currentValue = max(moveTransition.getTransitionBoard(), depth - 1, handler, alpha, beta);
				if (currentValue < lowestSeenValue) {
					lowestSeenValue = currentValue;
				}
				beta = Math.min(beta, currentValue);
				if (beta <= alpha) {
					break; // Alpha-beta pruning
				}
			}
		}
		return lowestSeenValue;
	}

	public int max(final Board board, final int depth, final BuildHandler handler, int alpha, int beta) {
		if (depth == 0 || isEndGameScenario(board, handler)) {
			return this.boardEvaluator.evaluate(board, depth, handler);
		}
		int highestSeenValue = Integer.MIN_VALUE;

		for (Move move : board.currentPlayer().getLegalMoves()) {
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move, handler);
			if (moveTransition.getMoveStatus().isDone()) {
				int currentValue = min(moveTransition.getTransitionBoard(), depth - 1, handler, alpha, beta);
				if (currentValue > highestSeenValue) {
					highestSeenValue = currentValue;
				}
				alpha = Math.max(alpha, currentValue);
				if (beta <= alpha) {
					break; // Alpha-beta pruning
				}
			}
		}
		return highestSeenValue;
	}

	private boolean isEndGameScenario(Board board, BuildHandler handler) {
		return board.currentPlayer().isInCheckmate(handler) || board.currentPlayer().isInStalemate(handler);
	}
}
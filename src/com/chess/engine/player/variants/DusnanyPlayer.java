package com.chess.engine.player.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.variants.DusnanyChess;
import com.chess.engine.pieces.King;
import com.chess.engine.player.WhitePlayer;

public class DusnanyPlayer extends WhitePlayer {

	public DusnanyPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}
		@Override
		protected King establishKing() {
			return new King(Alliance.WHITE, -1, false);
		}
		@Override
		public boolean isInCheck() {
			return false;
		}
		@Override
		public boolean isInCheckmate(BuildHandler handler) {
			return false;
		}
		@Override
		public boolean isInStalemate(BuildHandler handler) {
			return false;
		}

		@Override
		protected Collection<Move> calculateKingCalstles(Collection<Move> playerLegals,
				Collection<Move> opponentsLegals) {
			List<Move> moves = new ArrayList<Move>();
			return moves;
		}

}
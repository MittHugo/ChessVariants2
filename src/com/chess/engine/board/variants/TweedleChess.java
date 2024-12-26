package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.variants.StandardBoard.StandardBuilder;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.BluePlayerHandler;
import com.chess.engine.player.RedPlayerHandler;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.engine.player.variants.DoubleBlackPlayer;
import com.chess.engine.player.variants.DoubleBluePlayer;
import com.chess.engine.player.variants.DoubleRedPlayer;
import com.chess.engine.player.variants.DoubleWhitePlayer;
import com.chess.gui.Table;
import com.chess.gui.Table.PlayerCount;

public class TweedleChess extends Board {
	final static int boardDimension = 10;
	final static int numberOfRowsUsed = 2;
	public TweedleChess(Builder builder) {
		super(builder, boardDimension, boardDimension, true, true, 
                true, true, numberOfRowsUsed,
                new WhitePlayerHandler<>(DoubleWhitePlayer.class), 
				new BlackPlayerHandler<>(DoubleBlackPlayer.class),
                new RedPlayerHandler<>(DoubleRedPlayer.class), 
				new BluePlayerHandler<>(DoubleBluePlayer.class));
	}

	public static Board createBoard() {
		final TweedleChessBuilder builder = new TweedleChessBuilder();
		Class<? extends Piece>[] classes = new Class[] {
            Rook.class,
            Knight.class,
            Bishop.class,
            King.class,
            Queen.class,
            Queen.class,
            King.class,
            Bishop.class,
            Knight.class,
            Rook.class,
        };

        BoardUtils.betterBuildBoard(classes,builder,boardDimension,boardDimension,2,Pawn.class,numberOfRowsUsed);
		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class TweedleChessBuilder extends Builder {
		@Override
		public Board build() {
			return new TweedleChess(this);
		}
	}

}


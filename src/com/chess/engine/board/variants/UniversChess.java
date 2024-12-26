package com.chess.engine.board.variants;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;

public class UniversChess extends Board {

    public UniversChess(Builder builder) {
        super(builder, 8, 10, true, true, true, true);
    }

    public static Board createBoard() {
        final UniversChessBuilder builder = new UniversChessBuilder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 1, true));
        builder.setPiece(new Knight(Alliance.BLACK, 2, true));
        builder.setPiece(new Chancellor(Alliance.BLACK, 3, true));
        builder.setPiece(new Queen(Alliance.BLACK, 4, true));
        builder.setPiece(new King(Alliance.BLACK, 5, true));
        builder.setPiece(new Archbishop(Alliance.BLACK, 6, true));
        builder.setPiece(new Knight(Alliance.BLACK, 7, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 8, true));
        builder.setPiece(new Rook(Alliance.BLACK, 9, true));

        // Black Pawns
        for (int i = 10; i < 20; i++) {
            builder.setPiece(new Pawn(Alliance.BLACK, i, true));
        }

        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 70, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 71, true));
        builder.setPiece(new Knight(Alliance.WHITE, 72, true));
        builder.setPiece(new Chancellor(Alliance.WHITE, 73, true));
        builder.setPiece(new Queen(Alliance.WHITE, 74, true));
        builder.setPiece(new King(Alliance.WHITE, 75, true));
        builder.setPiece(new Archbishop(Alliance.WHITE, 76, true));
        builder.setPiece(new Knight(Alliance.WHITE, 77, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 78, true));
        builder.setPiece(new Rook(Alliance.WHITE, 79, true));

        // White Pawns
        for (int i = 60; i < 70; i++) {
            builder.setPiece(new Pawn(Alliance.WHITE, i, true));
        }

        // White to move
        builder.setFirstMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static class UniversChessBuilder extends Builder {
        @Override
        public Board build() {
            return new UniversChess(this);
        }
    }
}

package com.chess.engine.board.variants;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;

public class GrasshoperChess extends Board {

	public GrasshoperChess(Builder builder) {
		super(builder, 8, 8, true, true, false, false);
	}

	public static Board createBoard() {
		final GrasshoperBuilder builder = new GrasshoperBuilder();
		// Black Layout
		// Define the back rank with pieces for both Black and White
		Class<? extends Piece>[] backRankClasses = new Class[] {
		    Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class, 
		    Grasshoper.class, Grasshoper.class, Grasshoper.class, Grasshoper.class,
		    Grasshoper.class, Grasshoper.class, Grasshoper.class, Grasshoper.class,
		};

		// Define the Grasshoper line for both Black and White
		Class<? extends Piece>[] grasshoperLineClasses = new Class[] {
		    Grasshoper.class, Grasshoper.class, Grasshoper.class, Grasshoper.class,
		    Grasshoper.class, Grasshoper.class, Grasshoper.class, Grasshoper.class
		};

		// Build the board
		BoardUtils.buildBoard(backRankClasses, builder, 8, 8, 3, Pawn.class); // Add pawns at rows 3 (Black) and 6 (White)

		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class Grasshoper extends Piece {

		public Grasshoper(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.GRASSHOPPER, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Grasshoper movePiece(Move move) {
			return new Grasshoper(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = {
					new Vectors(board.getNumberColumns()+1, Integer.MAX_VALUE, 1, false,1),
					new Vectors(board.getNumberColumns()-1,Integer.MAX_VALUE, 1, false,1),
					new Vectors(-board.getNumberColumns()+1, Integer.MAX_VALUE, 1, false,1),
					new Vectors(-board.getNumberColumns()-1, Integer.MAX_VALUE, 1, false,1),
					new Vectors(board.getNumberColumns(), Integer.MAX_VALUE, 1, false,1),
					new Vectors(-board.getNumberColumns(), Integer.MAX_VALUE, 1, false,1),

			};
			return PieceUtils.moveMaker(vectors, board, this);
		}

	}

	public static class GrasshoperBuilder extends Builder {
		@Override
		public Board build() {
			return new GrasshoperChess(this);
		}
	}

}

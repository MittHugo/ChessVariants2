package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.MotionType;

public class AugmentedKnight extends Board {
	final static int boardDimension = 8;
	final static int numberOfRowsUsed = 2;
	public AugmentedKnight(Builder builder) {
		super(builder, boardDimension, boardDimension, true, true, true, true, numberOfRowsUsed);
	}

	public static Board createBoard1() {
		final AugmentedKnightBuilder builder = new AugmentedKnightBuilder();
		Class<? extends Piece>[] classes = new Class[] { Rook.class, 
                AugmentedKnight1.class, Bishop.class, Queen.class, King.class, Bishop.class, 
                AugmentedKnight1.class, Rook.class };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 2, Pawn.class, numberOfRowsUsed);

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}
    public static Board createBoard2() {
		final AugmentedKnightBuilder builder = new AugmentedKnightBuilder();
		Class<? extends Piece>[] classes = new Class[] { Rook.class, 
                AugmentedKnight2.class, Bishop.class, Queen.class, King.class, Bishop.class, 
                AugmentedKnight2.class, Rook.class };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 2, Pawn.class, numberOfRowsUsed);

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}
    public static Board createBoard3() {
		final AugmentedKnightBuilder builder = new AugmentedKnightBuilder();
		Class<? extends Piece>[] classes = new Class[] { Rook.class, 
                AugmentedKnight3.class, Bishop.class, Queen.class, King.class, Bishop.class, 
                AugmentedKnight3.class, Rook.class };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 2, Pawn.class, numberOfRowsUsed);

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}
    public static Board createBoard4() {
		final AugmentedKnightBuilder builder = new AugmentedKnightBuilder();
		Class<? extends Piece>[] classes = new Class[] { Rook.class, 
                AugmentedKnight4.class, Bishop.class, Queen.class, King.class, Bishop.class, 
                AugmentedKnight4.class, Rook.class };
//		Class<? extends Piece> pawnClass =BerolinaPawn.class;
		BoardUtils.betterBuildBoard(classes, builder, boardDimension, boardDimension, 2, Pawn.class, numberOfRowsUsed);

		// white to move
		builder.setFirstMoveMaker(Alliance.WHITE);

		return builder.build();
	}
    public static class AugmentedKnight1 extends Piece {
    	public AugmentedKnight1(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
    		super(PieceType.AUGMENTED_KNIGHT,pieceAlliance, piecePosition, isFirstMove);
    	}

	    @Override
	    public Collection<Move> calculateLegalMoves(final Board board) {
	    	final Vectors[] CANIDATE_MOVE_COORDINATES = {
		    	new Vectors(-board.getNumberColumns()*2-2, MotionType.Jump),
                new Vectors(-board.getNumberColumns()*2+2, MotionType.Jump),
                new Vectors(board.getNumberColumns()*2+2, MotionType.Jump),
                new Vectors(board.getNumberColumns()*2-2, MotionType.Jump),
            };
		    final List<Move> legalMoves = new ArrayList<>();
    		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
            legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board,
                     PieceUtils.pieceFromPiece(this, Knight.class)));
                
    		return Collections.unmodifiableList(legalMoves);
    	}
	
	    @Override
    	public AugmentedKnight1 movePiece(final Move move) {
    		return new AugmentedKnight1(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	    }
    }
    public static class AugmentedKnight2 extends Piece {
    	public AugmentedKnight2(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
    		super(PieceType.AUGMENTED_KNIGHT,pieceAlliance, piecePosition, isFirstMove);
    	}

	    @Override
	    public Collection<Move> calculateLegalMoves(final Board board) {
	    	final Vectors[] CANIDATE_MOVE_COORDINATES = {
		    	new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
                new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
                new Vectors(board.getNumberColumns()+1, MotionType.Jump),
                new Vectors(board.getNumberColumns()-1, MotionType.Jump),
            };
		    final List<Move> legalMoves = new ArrayList<>();
    		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
            legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board,
                     PieceUtils.pieceFromPiece(this, Knight.class)));
                
    		return Collections.unmodifiableList(legalMoves);
    	}
	
	    @Override
    	public AugmentedKnight2 movePiece(final Move move) {
    		return new AugmentedKnight2(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	    }
    }
    public static class AugmentedKnight3 extends Piece {
    	public AugmentedKnight3(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
    		super(PieceType.AUGMENTED_KNIGHT,pieceAlliance, piecePosition, isFirstMove);
    	}

	    @Override
	    public Collection<Move> calculateLegalMoves(final Board board) {
	    	final Vectors[] CANIDATE_MOVE_COORDINATES = {
		    	new Vectors(-board.getNumberColumns()*2, MotionType.Jump),
                new Vectors(-2, MotionType.Jump),
                new Vectors(board.getNumberColumns()*2, MotionType.Jump),
                new Vectors(2, MotionType.Jump),
            };
		    final List<Move> legalMoves = new ArrayList<>();
    		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
            legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board,
                     PieceUtils.pieceFromPiece(this, Knight.class)));
                
    		return Collections.unmodifiableList(legalMoves);
    	}
	
	    @Override
    	public AugmentedKnight3 movePiece(final Move move) {
    		return new AugmentedKnight3(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	    }
    }
    public static class AugmentedKnight4 extends Piece {
    	public AugmentedKnight4(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
    		super(PieceType.AUGMENTED_KNIGHT,pieceAlliance, piecePosition, isFirstMove);
    	}

	    @Override
	    public Collection<Move> calculateLegalMoves(final Board board) {
	    	final Vectors[] CANIDATE_MOVE_COORDINATES = {
		    	new Vectors(-board.getNumberColumns(), MotionType.Jump),
                new Vectors(-1, MotionType.Jump),
                new Vectors(board.getNumberColumns(), MotionType.Jump),
                new Vectors(1, MotionType.Jump),
            };
		    final List<Move> legalMoves = new ArrayList<>();
    		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
            legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board,
                     PieceUtils.pieceFromPiece(this, Knight.class)));
                
    		return Collections.unmodifiableList(legalMoves);
    	}
	
	    @Override
    	public AugmentedKnight4 movePiece(final Move move) {
    		return new AugmentedKnight4(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	    }
    }

	public static class AugmentedKnightBuilder extends Builder {
		@Override
		public Board build() {
			return new AugmentedKnight(this);
		}
	}

}

package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Vectors.MotionType;

public class King extends Piece {
	
	public King(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.KING,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final  Vectors[] CANIDATE_MOVE_COORDINATE = {
			new Vectors(-board.getNumberColumns()-1, MotionType.Jump),
			new Vectors(-board.getNumberColumns(), MotionType.Jump),
			new Vectors(-board.getNumberColumns()+1, MotionType.Jump),
			new Vectors(-1,  MotionType.Jump),
			new Vectors(1,  MotionType.Jump),
			new Vectors(board.getNumberColumns()-1, MotionType.Jump),
			new Vectors(board.getNumberColumns(),  MotionType.Jump),
			new Vectors(board.getNumberColumns()+1, MotionType.Jump),};
		final List<Move> legalMoves= new ArrayList<>();
		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATE, board, this));
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.KING.toString();
	}

	
	@Override
	public King movePiece(final Move move) {
		return new King(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

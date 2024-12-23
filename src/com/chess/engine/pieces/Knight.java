package com.chess.engine.pieces;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Vectors.MotionType;

public class Knight extends Piece {
	public Knight(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
		super(PieceType.KNIGHT,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final Vectors[] CANIDATE_MOVE_COORDINATES = {
			new Vectors(-board.getNumberColumns()*2-1, MotionType.Jump),
			new Vectors(-board.getNumberColumns()*2+1,  MotionType.Jump),
			new Vectors(-board.getNumberColumns()-2,  MotionType.Jump),
			new Vectors(-board.getNumberColumns()+2,  MotionType.Jump),
			new Vectors(board.getNumberColumns()-2,  MotionType.Jump),
			new Vectors(board.getNumberColumns()+2,  MotionType.Jump),
			new Vectors(board.getNumberColumns()*2-1,  MotionType.Jump),
			new Vectors(board.getNumberColumns()*2+1, MotionType.Jump),};
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	@Override
	public Knight movePiece(final Move move) {
		return new Knight(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

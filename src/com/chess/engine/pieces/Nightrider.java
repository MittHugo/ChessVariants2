package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Vectors.MotionType;

public class Nightrider extends Piece {
	public Nightrider(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
		super(PieceType.NIGHTRIDER,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final Vectors[] CANIDATE_MOVE_COORDINATES = {
			new Vectors(-board.getNumberColumns()*2-1, MotionType.Itterative),
			new Vectors(-board.getNumberColumns()*2+1,  MotionType.Itterative),
			new Vectors(-board.getNumberColumns()-2,  MotionType.Itterative),
			new Vectors(-board.getNumberColumns()+2,  MotionType.Itterative),
			new Vectors(board.getNumberColumns()-2,  MotionType.Itterative),
			new Vectors(board.getNumberColumns()+2,  MotionType.Itterative),
			new Vectors(board.getNumberColumns()*2-1,  MotionType.Itterative),
			new Vectors(board.getNumberColumns()*2+1, MotionType.Itterative),};
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_COORDINATES, board, this));
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.NIGHTRIDER.toString();
	}
	
	@Override
	public Nightrider movePiece(final Move move) {
		return new Nightrider(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), isFirstMove);
	}
}

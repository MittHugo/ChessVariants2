package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Vectors.MotionType;

public class Bishop extends Piece {

	public Bishop(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.BISHOP, pieceAlliance, piecePosition,isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		Vectors[] CANIDATE_MOVE_VECTOR_COORDINATES = {
			new Vectors(-(board.getNumberColumns()+1),MotionType.Itterative),
			new Vectors(-(board.getNumberColumns()-1),MotionType.Itterative), 
			new Vectors(board.getNumberColumns()-1,MotionType.Itterative),
			new Vectors(board.getNumberColumns()+1, MotionType.Itterative)};
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_VECTOR_COORDINATES, board, this));
		return legalMoves;
	}
	
	@Override 
	public String toString() {
		return PieceType.BISHOP.toString();
	}

	@Override
	public Bishop movePiece(final Move move) {
		return new Bishop(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

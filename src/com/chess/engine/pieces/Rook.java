package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Vectors.MotionType;

public class Rook extends Piece {

	public Rook(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.ROOK,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		Vectors[] CANIDATE_MOVE_VECTOR_COORDINATES = {
			new Vectors(-board.getNumberColumns(), MotionType.Itterative),
			new Vectors(-1, MotionType.Itterative),
			new Vectors(1, MotionType.Itterative),
			new Vectors(board.getNumberColumns(), MotionType.Itterative)};
		List<Move> legalMoves = new ArrayList<>();
		legalMoves.addAll(PieceUtils.moveMaker(CANIDATE_MOVE_VECTOR_COORDINATES, board, this));
		return Collections.unmodifiableList(legalMoves);
	}
	@Override 
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	@Override
	public Rook movePiece(final Move move) {
		return new Rook(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece.PieceType;

public class Archbishop extends Piece {

	public Archbishop(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.ARCHBISHOP, pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Piece movePiece(Move move) {
		return new Archbishop(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		legalMoves.addAll(PieceUtils.calculate2To1PatternLeaper(board, this));
		legalMoves.addAll(PieceUtils.calculateDiagonalMoves(board, this));
		return Collections.unmodifiableCollection(legalMoves);
	}
	@Override 
	public String toString() {
		return PieceType.ARCHBISHOP.toString();
	}

}

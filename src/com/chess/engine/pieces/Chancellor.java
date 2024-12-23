package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class Chancellor extends Piece {

	public Chancellor(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.CHANCELLOR, pieceAlliance, piecePosition,isFirstMove);
	}

	@Override
	public Chancellor movePiece(Move move) {
		return new Chancellor(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(),isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
	    // legalMoves.addAll(PieceUtils.calculateOrthogonalMoves(board, this));
	    // legalMoves.addAll(PieceUtils.calculate2To1PatternLeaper(board, this));
		legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board, PieceUtils.pieceFromPiece(this, Rook.class), PieceUtils.pieceFromPiece(this, Knight.class)));
		return Collections.unmodifiableCollection(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.CHANCELLOR.toString();
	}
}

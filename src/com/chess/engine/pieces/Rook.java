package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;

public class Rook extends Piece {

	public Rook(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.ROOK,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		int[] CANIDATE_MOVE_VECTOR_COORDINATES = {-board.getNumberColumns(), -1, 1, board.getNumberColumns()};
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int canidateCoordinateOffset: CANIDATE_MOVE_VECTOR_COORDINATES) {
			int canidateDestinationCoordinate = this.piecePosition;
			
			while(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				if(PieceUtils.isException(board,canidateCoordinateOffset,canidateDestinationCoordinate)) {
					break;
				}
				
				canidateDestinationCoordinate += canidateCoordinateOffset;
				if(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
					
					if(!canidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
						if(this.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
							legalMoves.add(new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	@Override 
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.FIRST_COLUMN[currentPosition] && canidateOffset == -1;
	}
	
	private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.LAST_COLUMN[currentPosition] && canidateOffset == 1;
	}
	
	@Override
	public Rook movePiece(final Move move) {
		return new Rook(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

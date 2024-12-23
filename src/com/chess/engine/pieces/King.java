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

public class King extends Piece {
	
	public King(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.KING,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final  int[] CANIDATE_MOVE_COORDINATE = {-board.getNumberColumns()-1, -board.getNumberColumns(), -board.getNumberColumns()+1, -1, 1, board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1};
		final List<Move> legalMoves= new ArrayList<>();

		for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
			final int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset;
			if(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(this.piecePosition, currentCanidateOffset, board) || 
						isLastColumnExclusion(this.piecePosition, currentCanidateOffset, board)) {
					continue;
				}
				final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
				if(!canidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = canidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getAlliance();
					if(this.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
						legalMoves.add(new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
			
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.KING.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()-1 || canidateOffset == -1 || canidateOffset == board.getNumberColumns()-1);
	}
	
	private static boolean isLastColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()+1 || canidateOffset == 1 || canidateOffset == board.getNumberColumns()+1);
	}
	
	@Override
	public King movePiece(final Move move) {
		return new King(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

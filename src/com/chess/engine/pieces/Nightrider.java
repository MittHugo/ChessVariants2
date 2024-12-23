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
import com.chess.engine.pieces.Piece.PieceType;

public class Nightrider extends Piece {
	public Nightrider(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
		super(PieceType.NIGHTRIDER,pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final int[] CANIDATE_MOVE_COORDINATES = {-board.getNumberColumns()*2-1, -board.getNumberColumns()*2+1, -board.getNumberColumns()-2, -board.getNumberColumns()+2, board.getNumberColumns()-2, board.getNumberColumns()+2, board.getNumberColumns()*2-1, board.getNumberColumns()*2+1};
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATES) {
			int canidateDestinationCoordinate = this.piecePosition;
			while(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(canidateDestinationCoordinate, currentCanidateOffset, board) ||
						isSecondColumnExclusion(canidateDestinationCoordinate, currentCanidateOffset, board) ||
						isSeventhColumnExclusion(canidateDestinationCoordinate, currentCanidateOffset, board) ||
						isEightColumnExclusion(canidateDestinationCoordinate, currentCanidateOffset, board)) {
					break;
				}
				canidateDestinationCoordinate +=currentCanidateOffset;
				if(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
					
					if(!canidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
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
		return PieceType.NIGHTRIDER.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.FIRST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()*2-1 || canidateOffset == -board.getNumberColumns()-2 || canidateOffset == board.getNumberColumns()-2 || 
				canidateOffset == board.getNumberColumns()*2-1);
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.SECOND_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()-2 || canidateOffset == board.getNumberColumns()-2);
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.SECOND_TO_LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()+2 || canidateOffset == board.getNumberColumns()+2);
	}
	private static boolean isEightColumnExclusion(final int currentPosition, final int canidateOffset, final Board board) {
		return board.LAST_COLUMN[currentPosition] && (canidateOffset == -board.getNumberColumns()*2+1 || canidateOffset == -board.getNumberColumns()+2 || canidateOffset == board.getNumberColumns()+2 || 
				canidateOffset == board.getNumberColumns()*2+1);
	}
	
	@Override
	public Nightrider movePiece(final Move move) {
		return new Nightrider(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), isFirstMove);
	}
}

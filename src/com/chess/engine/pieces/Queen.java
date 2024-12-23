package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class Queen extends Piece {

	public Queen(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
		super(PieceType.QUEEN, pieceAlliance, piecePosition, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		// final int[] CANIDATE_MOVE_VECTOR_COORDINATES = {-board.getNumberColumns()-1, -board.getNumberColumns(), -board.getNumberColumns()+1, -1, 1, board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1};
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.addAll(PieceUtils.compulatePieceMoves(this, board, new Rook(this.pieceAlliance, this.piecePosition, this.isFirstMove)
				, new Bishop(this.pieceAlliance, this.piecePosition, this.isFirstMove)));
// 		for(final int canidateCoordinateOffset: CANIDATE_MOVE_VECTOR_COORDINATES) {
// 			int canidateDestinationCoordinate = this.piecePosition;
// 			int vectorCount = 0;
// 			while(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
// 				if(PieceUtils.isException(board, canidateCoordinateOffset, canidateDestinationCoordinate)
// //						isFirstColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)||
// //						isLastColumnExclusion(canidateDestinationCoordinate, canidateCoordinateOffset, board)
// 						) {
// 					break;
// 				}
// 				vectorCount++;
// 				canidateDestinationCoordinate += canidateCoordinateOffset;
// 				if(board.isValidTileCoordinate(canidateDestinationCoordinate)) {
// 					final Tile canidateDestinationTile = board.getTile(canidateDestinationCoordinate);
					
// 					if(!canidateDestinationTile.isTileOccupied()) {
// 						legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
// 					} else {
// 						final Piece pieceAtDestination = canidateDestinationTile.getPiece();
// 						final Alliance pieceAlliance = pieceAtDestination.getAlliance();
// 						if(this.pieceAlliance.canAttack(pieceAtDestination.getAlliance())) {
// 							legalMoves.add(new AttackMove(board, this, canidateDestinationCoordinate, pieceAtDestination));
// 						}
// 						break;
// 					}
// 				}
// 			}
// 		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.QUEEN.toString();
	}
	
	@Override
	public Queen movePiece(final Move move) {
		return new Queen(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
	}
}

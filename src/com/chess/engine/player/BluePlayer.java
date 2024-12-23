package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.gui.Table;
import com.chess.gui.Table.PlayerCount;

public class BluePlayer extends Player {

	public BluePlayer(final Board board, final Collection<Move> legalMoves,
			final Collection<Move> opponentMoves) {
		super(board,legalMoves, opponentMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return  this.board.getBluePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLUE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCalstles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
//		Rook leftRook;
//		Rook rightRook;
//		if(this.getRooks()[0].getPiecePosition() > this.getRooks()[1].getPiecePosition()) {
//			rightRook=this.getRooks()[0];
//			leftRook = this.getRooks()[1];
//		} else {
//			rightRook=this.getRooks()[1];
//			leftRook = this.getRooks()[0];
//		}
//		// white king side 
//		if(this.playerKing.isFirstMove() && !this.isInCheck) {
//			if(spacesBettweenEmptyOfAttacks(board,leftRook.getPiecePosition(), playerKing.getPiecePosition(), opponentsLegals)) {
//				kingCastles.add(new KingSideCastleMove(board, playerKing, 
//						Player.castleLocationOfKing(playerKing.getPiecePosition(), leftRook.getPiecePosition()), 
//						(Rook)leftRook, leftRook.getPiecePosition(), 
//						Player.castleLocationOfRook(playerKing.getPiecePosition(), leftRook.getPiecePosition())));
//				System.out.println(Player.castleLocationOfKing(playerKing.getPiecePosition(), leftRook.getPiecePosition()));
//			}
//		}
//		if(this.playerKing.isFirstMove() && !this.isInCheck) {
//			if(spacesBettweenEmptyOfAttacks(board,rightRook.getPiecePosition(), playerKing.getPiecePosition(), opponentsLegals)) {
//				kingCastles.add(new KingSideCastleMove(board, playerKing, 
//						Player.castleLocationOfKing(playerKing.getPiecePosition(), rightRook.getPiecePosition()), 
//						(Rook)rightRook, rightRook.getPiecePosition(), 
//						Player.castleLocationOfRook(playerKing.getPiecePosition(), rightRook.getPiecePosition())));
//				System.out.println(Player.castleLocationOfKing(playerKing.getPiecePosition(), rightRook.getPiecePosition()));
//			}
//		}
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(61).isTileOccupied()&&!this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(board, playerKing, 62, 
								(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
					}
				}
			}
			// Queen side
			if(!this.board.getTile(59).isTileOccupied()&&!this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(57, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(56, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new QueenSideCastleMove(board, playerKing, 58, 
								(Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
					
				}
				}
			}
		}
		return kingCastles;
	}
	@Override
	public Player getNextPlayer() {
		return this.board.whitePlayer();
	}
	
	@Override
	public Player getPreviousPlayer() {
		return this.board.blackPlayer();
	}
	
	
}

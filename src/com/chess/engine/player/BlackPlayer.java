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
import com.chess.gui.Table.MoveType;
import com.chess.gui.Table.PlayerCount;

public class BlackPlayer extends Player {

	public BlackPlayer(final Board board, final Collection<Move> legalMoves,
			final Collection<Move> opponentMoves) {
		super(board, legalMoves, opponentMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}
	
	@Override
	protected Collection<Move> calculateKingCalstles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		// black king side 
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(5).isTileOccupied()&&!this.board.getTile(6).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(board, playerKing, 6, 
								(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
					}
				}
			}
			// Queen side
			if(!this.board.getTile(1).isTileOccupied()&&!this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(1, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty() &&
							Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new QueenSideCastleMove(board, playerKing, 2, 
								(Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
					}
				}
			}
		}
		return kingCastles;
	}

	@Override
	public Player getNextPlayer() {
		if(Table.get().getMoveType() == MoveType.DoubleMove) {
			if(this.board.getBuilder().countCurrentMoveMaker < 2) {
				return this.board.blackPlayer();
			}
		}
		if(Table.get().getMoveType() == MoveType.Progressive) {
			if(this.board.getBuilder().countCurrentMoveMaker < this.board.getBuilder().countPreviousMoveMaker+1) {
				return this.board.blackPlayer();
			}
		}
		if(Table.get() != null) {
			if(Table.get().getPlayerCount() != null) {
				if(Table.get().getPlayerCount() != PlayerCount.Regular) {
					return this.board.bluePlayer();
				}
			} 
		}
		return this.board.whitePlayer();
	}
	@Override
	public Player getPreviousPlayer() {
		if(this.board.getBuilder().countCurrentMoveMaker > 1) {
			return this.board.blackPlayer();
		}
		if(Table.get() != null) {
			if(Table.get().getPlayerCount() != null) {
				if(Table.get().getPlayerCount() != PlayerCount.Regular) {
					return this.board.getRedPlayer();
				}
			} 
		}
		return this.board.whitePlayer();
	}

}

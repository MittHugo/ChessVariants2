package com.chess.engine.board;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;



public abstract class Tile {
	protected final int tileCoordinate; 
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createALLPossibleEmptyTiles();

	private Tile(final int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	public static Tile createTile(final int tileCoordinate, Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece): EMPTY_TILES.get(tileCoordinate);
	}
	
	private static Map<Integer, EmptyTile> createALLPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		// TODO May need to be increased
		for(int i = 0; i < 1000; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return Collections.unmodifiableMap(emptyTileMap);
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile {
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		@Override 
		public String toString() {
			return"-";
		}
		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
		@Override
		public boolean isNullTile() {
			return false;
		}
	}
	
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile;
		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getAlliance().isBlack() ? getPiece().toString().toLowerCase(): getPiece().toString();
		}

		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return pieceOnTile;
		}

		@Override
		public boolean isNullTile() {
			return false;
		}
	}
	public static final class NullTile extends Tile {
		
		NullTile(int tileCoordinate) {
			super(tileCoordinate);
		}

		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}

		@Override
		public boolean isNullTile() {
			return true;
		}
	}

	public abstract boolean isNullTile();
	
}

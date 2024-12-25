package com.chess.engine.board;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;
import com.chess.engine.pieces.Piece;



public abstract class Tile {
	protected final int tileCoordinate; 
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createALLPossibleEmptyTiles();
	public static final Map<Integer, DisappearedTile> DISAPPEARED_TILES = createAllDisappearedTiles();

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

	private static Map<Integer, DisappearedTile> createAllDisappearedTiles() {
    	final Map<Integer, DisappearedTile> disappearedTileMap = new HashMap<>();
    	for (int i = 0; i < 1000; i++) {
        	disappearedTileMap.put(i, new DisappearedTile(i));
		}
    	return Collections.unmodifiableMap(disappearedTileMap);
	}


	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile {
		EmptyTile(final int coordinate) {
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
		@Override
		public boolean isDisappeared() {
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
		@Override
		public boolean isDisappeared() {
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
		@Override
		public boolean isDisappeared() {
			return false;
		}		
	}

	public static final class DisappearedTile extends Tile {
		
		DisappearedTile(int tileCoordinate) {
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
			return false;
		}
		@Override
		public boolean isDisappeared() {
			return true;
		}
	}

	public abstract boolean isNullTile();
	public abstract boolean isDisappeared();
	
}

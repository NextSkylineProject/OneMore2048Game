package com.nextskylineproject.onemore2048game;

import android.util.Log;

import java.util.ArrayList;

abstract class TileGrid {
	private static final String TAG = "Debug";
	private Tile[][] tilesGrid;
	private ArrayList<Tile> tiles;
	protected int columns;
	protected int rows;
	
	protected abstract void tileSpawnAction(Tile tile, int x, int y);
	
	protected abstract void tileMoveAction(MoveEvent moveEvent);
	
	protected void setSize(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		tiles = new ArrayList<>(columns * rows);
		tilesGrid = new Tile[columns][rows];
	}
	
	protected ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	private Tile getTileOrNull(int x, int y) {
		return tilesGrid[x][y];
	}
	
	protected boolean isNotEmpty(int x, int y) {
		return getTileOrNull(x, y) != null;
	}
	
	/**
	 * Base functions
	 */
	
	protected void createTile(int x, int y, int value) {
		if (isNotEmpty(x, y)) {
			return;
		}
		
		Tile freshTile = new Tile(x, y, value);
		tilesGrid[x][y] = freshTile;
		tiles.add(freshTile);
		
		tileSpawnAction(freshTile, x, y);
		
		return;
	}
	
	protected void removeTile(Tile tile) {
		if (tile == null) return;
		Log.d(TAG, "removeTile: " + tile);
		
		tilesGrid[tile.x][tile.y] = null;
		tiles.remove(tile);
	}
	
	protected void mergeTile(Tile main, Tile second) {
		if (main == null || second == null) return;
		Log.d(TAG, "mergeTile: " + main + " + " + second);
		
		if (main.equalsVal(second)) {
			removeTile(second);
			main.value = main.value * 2;
			replaceTile(main, second.x, second.y);
		}
	}
	
	protected void replaceTile(Tile tile, int x, int y) {
		tilesGrid[tile.x][tile.y] = null;
		tilesGrid[x][y] = tile;
		tile.x = x;
		tile.y = y;
	}
	
	/**
	 * Tile Move functions
	 */
	
	private void moveTileRight(Tile tile) {
		if (tile == null) return;
		int x = tile.x;
		int y = tile.y;
		MoveEvent moveEvent = new MoveEvent();
		
		if (x != columns - 1) {
			while (x < columns - 1) {
				x++;
				
				if (isNotEmpty(x, y)) {
					Tile tile2 = getTileOrNull(x, y);
					
					if (!tile.equalsVal(tile2)) {
						x--;
					}
					if (x == tile.x) {
						return;
					}
					
					moveEvent.hit = true;
					moveEvent.tile2 = tile2;
					moveEvent.isEqual = tile.equalsVal(tile2);
					break;
				}
			}
			
			moveEvent.tile = tile;
			moveEvent.newX = x;
			moveEvent.newY = y;
			tileMoveAction(moveEvent);
		}
	}
	
	private void moveTileLeft(Tile tile) {
		if (tile == null) return;
		int x = tile.x;
		int y = tile.y;
		MoveEvent moveEvent = new MoveEvent();
		
		if (x != 0) {
			while (x > 0) {
				x--;
				
				if (isNotEmpty(x, y)) {
					Tile tile2 = getTileOrNull(x, y);
					
					if (!tile.equalsVal(tile2)) {
						x++;
					}
					if (x == tile.x) {
						return;
					}
					
					moveEvent.hit = true;
					moveEvent.tile2 = tile2;
					moveEvent.isEqual = tile.equalsVal(tile2);
					break;
				}
			}
			
			moveEvent.tile = tile;
			moveEvent.newX = x;
			moveEvent.newY = y;
			tileMoveAction(moveEvent);
		}
	}
	
	private void moveTileDown(Tile tile) {
		if (tile == null) return;
		int x = tile.x;
		int y = tile.y;
		MoveEvent moveEvent = new MoveEvent();
		
		if (y != rows - 1) {
			while (y < rows - 1) {
				y++;
				
				if (isNotEmpty(x, y)) {
					Tile tile2 = getTileOrNull(x, y);
					
					if (!tile.equalsVal(tile2)) {
						y--;
					}
					if (y == tile.y) {
						return;
					}
					
					moveEvent.hit = true;
					moveEvent.tile2 = tile2;
					moveEvent.isEqual = tile.equalsVal(tile2);
					break;
				}
			}
			
			moveEvent.tile = tile;
			moveEvent.newX = x;
			moveEvent.newY = y;
			tileMoveAction(moveEvent);
		}
	}
	
	private void moveTileUp(Tile tile) {
		if (tile == null) return;
		int x = tile.x;
		int y = tile.y;
		MoveEvent moveEvent = new MoveEvent();
		
		if (y != 0) {
			while (y > 0) {
				y--;
				
				if (isNotEmpty(x, y)) {
					Tile tile2 = getTileOrNull(x, y);
					
					if (!tile.equalsVal(tile2)) {
						y++;
					}
					if (y == tile.y) {
						return;
					}
					
					moveEvent.hit = true;
					moveEvent.tile2 = tile2;
					moveEvent.isEqual = tile.equalsVal(tile2);
					break;
				}
			}
			
			moveEvent.tile = tile;
			moveEvent.newX = x;
			moveEvent.newY = y;
			tileMoveAction(moveEvent);
		}
	}
	
	// Moves field
	
	protected void shiftGridRight() {
		for (int x = columns - 1; x >= 0; x--) {
			for (int y = 0; y < rows; y++) {
				moveTileRight(getTileOrNull(x, y));
			}
		}
	}
	
	protected void shiftGridLeft() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				moveTileLeft(getTileOrNull(x, y));
			}
		}
	}
	
	protected void shiftGridDown() {
		for (int x = 0; x < columns; x++) {
			for (int y = rows - 1; y >= 0; y--) {
				moveTileDown(getTileOrNull(x, y));
			}
		}
	}
	
	protected void shiftGridUp() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				moveTileUp(getTileOrNull(x, y));
			}
		}
	}
	
	//
	
	static class MoveEvent {
		Tile tile;
		Tile tile2;
		int newX;
		int newY;
		boolean hit = false;
		boolean isEqual = false;
		
		@Override
		public String toString() {
			return "MoveEvent{" +
				   "tile=" + tile +
				   ", tile2=" + tile2 +
				   ", newX=" + newX +
				   ", newY=" + newY +
				   ", isEqual=" + isEqual +
				   ", hit=" + hit +
				   '}';
		}
	}
}

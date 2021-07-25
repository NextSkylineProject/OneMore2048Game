package com.nextskylineproject.onemore2048game;

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
		if (isNotEmpty(x, y)) return;
		Tile freshTile = new Tile(x, y, value);
		freshTile.setInGrid(true);
		freshTile.unLock();
		tilesGrid[x][y] = freshTile;
		tiles.add(freshTile);
		tileSpawnAction(freshTile, x, y);
	}
	
	protected void removeTile(Tile tile) {
		if (tile.isInGrid() && (tilesGrid[tile.gridX][tile.gridY] == tile)) {
			tilesGrid[tile.gridX][tile.gridY] = null;
		}
		tiles.remove(tile);
	}
	
	protected void removeTileFromGridOnly(Tile tile) {
		tilesGrid[tile.gridX][tile.gridY] = null;
//		tile.screenX = -1; // temp
//		tile.screenY = -1; // temp
		tile.setInGrid(false);
	}
	
	protected void replaceTile(Tile tile, int x, int y) {
		if (isNotEmpty(x, y)) return;
		if (tile.isInGrid()) {
			tilesGrid[tile.gridX][tile.gridY] = null;
		}
		tile.gridX = x;
		tile.gridY = y;
//		tile.screenX = x; // temp
//		tile.screenY = y; // temp
		tilesGrid[x][y] = tile;
	}
	
	/**
	 * Tile Move functions
	 */
	private void moveTileRight(Tile tile) {
		if (tile == null) return;
		if (!tile.isInGrid()) return;
		tile.unLock();
		if (tile.gridX == columns - 1) return;
		int x = tile.gridX;
		int y = tile.gridY;
		MoveEvent moveEvent = new MoveEvent();
		moveEvent.movingTile = tile;
		moveEvent.moveAxisIsX = true;
		while (x < columns - 1) {
			x++;
			if (isNotEmpty(x, y)) {
				moveEvent.hitTile = getTileOrNull(x, y);
				if (!tile.isSameValue(moveEvent.hitTile) || moveEvent.getHitTile().isLock()) x--;
				if (x == tile.gridX) return;
				break;
			}
		}
		moveEvent.newX = x;
		moveEvent.newY = y;
		tileMoveAction(moveEvent);
	}
	
	private void moveTileLeft(Tile tile) {
		if (tile == null) return;
		if (!tile.isInGrid()) return;
		tile.unLock();
		if (tile.gridX == 0) return;
		int x = tile.gridX;
		int y = tile.gridY;
		MoveEvent moveEvent = new MoveEvent();
		moveEvent.movingTile = tile;
		moveEvent.moveAxisIsX = true;
		while (x > 0) {
			x--;
			if (isNotEmpty(x, y)) {
				moveEvent.hitTile = getTileOrNull(x, y);
				if (!tile.isSameValue(moveEvent.hitTile) || moveEvent.getHitTile().isLock()) x++;
				if (x == tile.gridX) return;
				break;
			}
		}
		moveEvent.newX = x;
		moveEvent.newY = y;
		tileMoveAction(moveEvent);
	}
	
	private void moveTileDown(Tile tile) {
		if (tile == null) return;
		if (!tile.isInGrid()) return;
		tile.unLock();
		if (tile.gridY == rows - 1) return;
		int x = tile.gridX;
		int y = tile.gridY;
		MoveEvent moveEvent = new MoveEvent();
		moveEvent.movingTile = tile;
		moveEvent.moveAxisIsX = false;
		while (y < rows - 1) {
			y++;
			if (isNotEmpty(x, y)) {
				moveEvent.hitTile = getTileOrNull(x, y);
				if (!tile.isSameValue(moveEvent.hitTile) || moveEvent.getHitTile().isLock()) y--;
				if (y == tile.gridY) return;
				break;
			}
		}
		moveEvent.newX = x;
		moveEvent.newY = y;
		tileMoveAction(moveEvent);
	}
	
	private void moveTileUp(Tile tile) {
		if (tile == null) return;
		if (!tile.isInGrid()) return;
		tile.unLock();
		if (tile.gridY == 0) return;
		int x = tile.gridX;
		int y = tile.gridY;
		MoveEvent moveEvent = new MoveEvent();
		moveEvent.movingTile = tile;
		moveEvent.moveAxisIsX = false;
		while (y > 0) {
			y--;
			if (isNotEmpty(x, y)) {
				moveEvent.hitTile = getTileOrNull(x, y);
				if (!tile.isSameValue(moveEvent.hitTile) || moveEvent.getHitTile().isLock()) y++;
				if (y == tile.gridY) return;
				break;
			}
		}
		moveEvent.newX = x;
		moveEvent.newY = y;
		tileMoveAction(moveEvent);
	}
	
	// Moves field
	protected void shiftGridRight() {
		for (int x = columns - 1; x >= 0; x--)
			for (int y = 0; y < rows; y++)
				moveTileRight(getTileOrNull(x, y));
	}
	
	protected void shiftGridLeft() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++)
				moveTileLeft(getTileOrNull(x, y));
	}
	
	protected void shiftGridDown() {
		for (int x = 0; x < columns; x++)
			for (int y = rows - 1; y >= 0; y--)
				moveTileDown(getTileOrNull(x, y));
	}
	
	protected void shiftGridUp() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++)
				moveTileUp(getTileOrNull(x, y));
	}
	
	/**
	 * MoveEvent
	 */
	static class MoveEvent {
		private Tile movingTile;
		private Tile hitTile;
		private int newX;
		private int newY;
		private boolean moveAxisIsX;
		
		public Tile getMovingTile() {
			return movingTile;
		}
		
		public Tile getHitTile() {
			return hitTile;
		}
		
		public int getNewX() {
			return newX;
		}
		
		public int getNewY() {
			return newY;
		}
		
		public boolean isMoveAxisIsX() {
			return moveAxisIsX;
		}
		
		public boolean hit() {
			return hitTile != null;
		}
		
		public boolean isEqual() {
			return hit() && movingTile.isSameValue(hitTile);
		}
		
		@Override
		public String toString() {
			return "MoveEvent{" +
				   "tile=" + movingTile +
				   ", tile2=" + hitTile +
				   ", newX=" + newX +
				   ", newY=" + newY +
				   ", moveAxisIsX=" + moveAxisIsX +
				   ", isEqual=" + isEqual() +
				   '}';
		}
	}
}

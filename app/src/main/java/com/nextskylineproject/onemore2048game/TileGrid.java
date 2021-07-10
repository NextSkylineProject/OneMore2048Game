package com.nextskylineproject.onemore2048game;

import android.util.Log;

import java.util.ArrayList;

// TODO добавить интерфейс для каллбеков под анимацию и метрику
class TileGrid {
	private static final String TAG = "Debug";
	private ArrayList<Tile> tiles;
	private Tile[][] tilesGrid;
	private int columns;
	private int rows;
	
	
	public void setSize(int c, int r) {
		this.columns = c;
		this.rows = r;
		tiles = new ArrayList<>(c * r);
		tilesGrid = new Tile[c][r];
	}
	
	// Base
	
	public boolean createTile(int x, int y, int value) {
		//TODO check here x and y on a grid
		if (!checkCord(x, y)) {
			Tile freshTile = new Tile(x, y, value);
			tilesGrid[x][y] = freshTile;
			tiles.add(freshTile);
			
			Log.d(TAG, "createTile: " + x + " " + y + " - Success");
			return true;
		} else {
			Log.d(TAG, "createTile: " + x + " " + y + " - Fail, tile already exist!");
			return false;
		}
	}
	
	public void removeTile(Tile tile) {
		if (tile == null) return;
		tilesGrid[tile.x][tile.y] = null;
		tiles.remove(tile);
	}
	
	public void cleanup() {
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				removeTile(getTileOrNull(i, j));
			}
		}
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public Tile getTileOrNull(int x, int y) {
		return tilesGrid[x][y];
	}
	
	public boolean checkCord(int x, int y) {
		return getTileOrNull(x, y) != null;
	}
	
	private void lvlUpTile(Tile tile) {
		tile.value = tile.value * 2;
	}
	
	private void mergeTiles(Tile main, Tile second) {
		if (main == null || second == null) return;
		
		if (main.equalsVal(second)) {
			removeTile(second);
			lvlUpTile(main);
		}
	}
	
	// Moves
	
	private void moveTile(Tile tile, int x, int y) {
		if (tile == null) return;
		if (checkCord(x, y)) return;
		if (tile == getTileOrNull(x, y)) return;
		
		tilesGrid[x][y] = tile;
		tilesGrid[tile.x][tile.y] = null;
		tile.x = x;
		tile.y = y;
	}
	
	private void moveTileRight(Tile tile) {
		if (tile == null) return;
		int tx = tile.x;
		int ty = tile.y;
		
		if (tx < columns - 1) {
			if (!checkCord(tx + 1, ty)) {
				moveTile(tile, tx + 1, ty);
				moveTileRight(tile);
			} else {
				Tile tile2 = getTileOrNull(tx + 1, ty);
				mergeTiles(tile2, tile);
			}
		}
	}
	
	private void moveTileLeft(Tile tile) {
		if (tile == null) return;
		int tx = tile.x;
		int ty = tile.y;
		
		if (tx > 0) {
			if (!checkCord(tx - 1, ty)) {
				moveTile(tile, tx - 1, ty);
				moveTileLeft(tile);
			} else {
				Tile tile2 = getTileOrNull(tx - 1, ty);
				mergeTiles(tile2, tile);
			}
		}
	}
	
	private void moveTileDown(Tile tile) {
		if (tile == null) return;
		int tx = tile.x;
		int ty = tile.y;
		
		if (ty < rows - 1) {
			if (!checkCord(tx, ty + 1)) {
				moveTile(tile, tx, ty + 1);
				moveTileDown(tile);
			} else {
				Tile tile2 = getTileOrNull(tx, ty + 1);
				mergeTiles(tile2, tile);
			}
		}
	}
	
	private void moveTileUp(Tile tile) {
		if (tile == null) return;
		int tx = tile.x;
		int ty = tile.y;
		
		if (ty > 0) {
			if (!checkCord(tx, ty - 1)) {
				moveTile(tile, tx, ty - 1);
				moveTileUp(tile);
			} else {
				Tile tile2 = getTileOrNull(tx, ty - 1);
				mergeTiles(tile2, tile);
			}
		}
	}
	
	// Moves field
	
	public void moveFieldRight() {
		for (int x = columns - 1; x >= 0; x--) {
			for (int y = 0; y < rows; y++) {
				moveTileRight(getTileOrNull(x, y));
			}
		}
	}
	
	public void moveFieldLeft() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				moveTileLeft(getTileOrNull(x, y));
			}
		}
	}
	
	public void moveFieldDown() {
		for (int x = 0; x < columns; x++) {
			for (int y = rows - 1; y >= 0; y--) {
				moveTileDown(getTileOrNull(x, y));
			}
		}
	}
	
	public void moveFieldUp() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				moveTileUp(getTileOrNull(x, y));
			}
		}
	}
}

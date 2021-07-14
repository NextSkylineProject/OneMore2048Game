package com.nextskylineproject.onemore2048game;

import java.util.ArrayList;
import java.util.Random;

class TileGridManager {
	private TileGrid tileGrid;
	private int columns;
	private int rows;
	private final Random random;
	
	public TileGridManager() {
		random = new Random();
		random.setSeed(12345L);
	}
	
	public ArrayList<Tile> getTiles() {
		return tileGrid.getTiles();
	}
	
	public void createGrid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		tileGrid = new TileGrid();
		tileGrid.setSize(columns, rows);
	}
	
	public void placeRandomTile() {
		int rx = random.nextInt(columns);
		int ry = random.nextInt(rows);
		if(!tileGrid.createTile(rx, ry, 2)) {
			placeRandomTile();
		}
	}
	
	public void clearGrid() {
		tileGrid.cleanup();
	}
	
	public void swipeRight() {
		tileGrid.moveFieldRight();
	}
	public void swipeLeft() {
		tileGrid.moveFieldLeft();
	}
	public void swipeUp() {
		tileGrid.moveFieldUp();
	}
	public void swipeDown() {
		tileGrid.moveFieldDown();
	}
}

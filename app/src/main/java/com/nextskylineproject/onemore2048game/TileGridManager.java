package com.nextskylineproject.onemore2048game;

import android.util.Log;

import java.util.Random;

class TileGridManager extends TileGrid {
	private static final String TAG = "Debug";
	private final Random random;
	
	public TileGridManager() {
		random = new Random();
		random.setSeed(12345L);
	}
	
	public void createGrid(int columns, int rows) {
		setSize(columns, rows);
	}
	
	public void placeRandomTile() {
		int rx = random.nextInt(columns);
		int ry = random.nextInt(rows);
		
		if (isNotEmpty(rx, ry)) {
			placeRandomTile();
			return;
		}
		createTile(rx, ry, 2);
	}
	
	protected void cleanup() {
		setSize(columns, rows);
	}
	
	@Override
	protected void tileSpawnAction(Tile tile, int x, int y) {
	}
	
	@Override
	protected void tileMoveAction(MoveEvent moveEvent) {
		if (moveEvent.hit) {
			if (moveEvent.isEqual) {
				mergeTile(moveEvent.tile, moveEvent.tile2);
				return;
			}
		}
		replaceTile(moveEvent.tile, moveEvent.newX, moveEvent.newY);
	}
}

package com.nextskylineproject.onemore2048game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

class TileGridManager extends TileGrid {
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
		if(!createTile(rx, ry, 2)) {
			placeRandomTile();
		}
	}
	
	public void swipeRight() {
		moveFieldRight();
	}
	public void swipeLeft() {
		moveFieldLeft();
	}
	public void swipeUp() {
		moveFieldUp();
	}
	public void swipeDown() {
		moveFieldDown();
	}
}

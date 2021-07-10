package com.nextskylineproject.onemore2048game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

class TileGridManager {
	private TileGrid tileGrid;
	private int tileSize;
	private int columns;
	private int rows;
	private int pr;
	private int pt;
	private final Random random;
	
	public TileGridManager() {
		random = new Random();
		random.setSeed(12345L);
	}
	
	public void createGrid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		tileGrid = new TileGrid();
		tileGrid.setSize(columns, rows);
	}
	
	public void updateViewParams(View view) {
		pr = view.getPaddingRight();
		pt = view.getPaddingTop();
		
		tileSize = (view.getMeasuredWidth() - pr - view.getPaddingLeft()) / columns;
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
		placeRandomTile();
	}
	public void swipeLeft() {
		tileGrid.moveFieldLeft();
		placeRandomTile();
	}
	public void swipeUp() {
		tileGrid.moveFieldUp();
		placeRandomTile();
	}
	public void swipeDown() {
		tileGrid.moveFieldDown();
		placeRandomTile();
	}
	
	
	
	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(Color.GRAY);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int rx = pr + x * tileSize;
				int ry = pt + y * tileSize;
				
				canvas.drawRect(rx, ry, rx + tileSize - 1, ry + tileSize - 1, paint);
			}
		}
		
		if (!tileGrid.getTiles().isEmpty()) {
			for (Tile tile : tileGrid.getTiles()) {
				int rx = pr + tile.x * tileSize;
				int ry = pt + tile.y * tileSize;
				
				paint.setColor(Color.RED);
				canvas.drawRect(rx, ry, rx + tileSize, ry + tileSize, paint);
				paint.setColor(Color.BLACK);
				int fs = tileSize / 4;
				paint.setTextSize(fs);
				canvas.drawText("Val:" + tile.value, rx, ry + fs, paint);
			}
		}
	}
}

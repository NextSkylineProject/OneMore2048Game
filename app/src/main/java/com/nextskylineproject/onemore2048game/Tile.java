package com.nextskylineproject.onemore2048game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
	public int gridX;
	public int gridY;
	public float screenX;
	public float screenY;
	private int value;
	private boolean inGrid;
	private boolean lock;
	private static int size;
	private static Bitmap bitmap;
	private static Rect bitmapRect;
	
	public static void setBitmap(Bitmap bitmap) {
		Tile.bitmap = bitmap;
		Tile.bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	}
	
	public static void setSize(int size) {
		Tile.size = size;
	}
	
	public Tile(int x, int y, int value) {
		this.gridX = x;
		this.gridY = y;
		this.screenX = x;
		this.screenY = y;
		this.value = value;
	}
	
	public void upValue() {
		this.value = this.value * 2;
		lock();
	}
	
	public boolean isSameValue(Tile tile2) {
		if (tile2 == null) return false;
		return this.value == tile2.value;
	}
	
	public void draw(Canvas canvas, Paint paint, Rect rect) {
		paint.setColor(Color.WHITE);
		canvas.drawBitmap(bitmap, bitmapRect, rect, paint);
		
		int fs = size / 4;
		paint.setColor(Color.BLACK);
		paint.setTextSize(fs);
		canvas.drawText("Val:" + value, rect.left + 20, rect.top + fs + 10, paint);
		canvas.drawText("L: " + lock, rect.left + 20, rect.top + fs * 2 + 10, paint);
	}
	
	@Override
	public String toString() {
		return "Tile{" +
			   gridX +
			   "/" + gridY +
			   ", value=" + value +
			   '}';
	}
	
	public boolean isInGrid() {
		return inGrid;
	}
	
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}
	
	public boolean isLock() {
		return lock;
	}
	
	public void lock() {
		this.lock = true;
	}
	
	public void unLock() {
		this.lock = false;
	}
}

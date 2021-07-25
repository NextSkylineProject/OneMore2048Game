package com.nextskylineproject.onemore2048game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Tile {
	private static final String TAG = "Debug";
	private static int staticSize;
	private static Bitmap bitmap;
	private int size;
	private Bitmap scaledBitmap;
	public int gridX;
	public int gridY;
	public float screenX;
	public float screenY;
	public float scaleShift;
	public float scale;
	private int value;
	private boolean inGrid;
	private boolean lock;
	
	public static void setBitmap(Bitmap bitmap) {
		Tile.bitmap = bitmap;
	}
	
	public static void setSize(int tileSize) {
		Tile.staticSize = tileSize;
	}
	
	public Tile(int x, int y, int value) {
		this.gridX = x;
		this.gridY = y;
		this.screenX = x;
		this.screenY = y;
		this.value = value;
		this.scale = 1;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		int scaledSize = (int) (Tile.staticSize * scale);
		scaleShift = (Tile.staticSize - scaledSize) / 2f;
		scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, scaledSize, scaledSize, false);
	}
	
	public void draw(Canvas canvas, Paint paint, int x, int y) {
		if (size != staticSize) {
			size = staticSize;
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
		}
		
		paint.setColor(Color.WHITE);
		canvas.drawBitmap(scaledBitmap, x + scaleShift, y + scaleShift, paint);
		
		int fs = staticSize / 4;
		paint.setColor(Color.BLACK);
		paint.setTextSize(fs);
		paint.setTextAlign(Paint.Align.CENTER);
		float h = paint.getTextSize();
		canvas.drawText("Val:" + value, x + staticSize / 2f, y + staticSize / 2f + h / 2, paint);
//		canvas.drawText("Val:" + value, rect.left + 20, rect.top + fs + 10, paint);
//		canvas.drawText("L: " + lock, rect.left + 20, rect.top + fs * 2 + 10, paint);
//		canvas.drawText("S: " + scale, rect.left + 20, rect.top + fs * 3 + 10, paint);
	}
	
	@Override
	public String toString() {
		return "Tile{" +
			   gridX +
			   "/" + gridY +
			   ", value=" + value +
			   '}';
	}
	
	public void upValue() {
		this.value = this.value * 2;
		lock();
	}
	
	public boolean isSameValue(Tile tile2) {
		if (tile2 == null) return false;
		return this.value == tile2.value;
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

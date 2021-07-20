package com.nextskylineproject.onemore2048game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private static final String TAG = "Debug";
	private Paint paint;
	private int columns = 4;
	private int rows = 4;
	private final TileGridManager tileGridManager;
	private final SwipeDirectionDetector directionDetector;
	private int tileSize;
	private int paddingLeft;
	private int paddingTop;
	private int offsetToCenterX;
	private int offsetToCenterY;
	private Bitmap tileBitmap;
	private final Rect tempTileRect;
	
	public GameView(Context context, AttributeSet attr) {
		super(context);
		
		tileGridManager = new TileGridManager();
		directionDetector = new SwipeDirectionDetector(context) {
			public void onDirectionDetected(Direction direction) {
//				if (tileGridManager.isAnimated()) return;
				
				switch (direction) {
					case UP:
						tileGridManager.shiftGridUp();
//						tileGridManager.placeRandomTile();
						break;
					case DOWN:
						tileGridManager.shiftGridDown();
//						tileGridManager.placeRandomTile();
						break;
					case LEFT:
						tileGridManager.shiftGridLeft();
//						tileGridManager.placeRandomTile();
						break;
					
					case RIGHT:
						tileGridManager.shiftGridRight();
//						tileGridManager.placeRandomTile();
						break;
				}
//				tileGridManager.placeRandomTile();
//				invalidate();
			}
		};
		tempTileRect = new Rect(0, 0, 32, 32);
		
		loadResource();
	}
	
	public void loadResource() {
		tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tile);
		Tile.setBitmap(tileBitmap);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		paddingLeft = getPaddingLeft();
		paddingTop = getPaddingTop();
		
		int inputWidth = MeasureSpec.getSize(widthMeasureSpec);
		int inputHeight = MeasureSpec.getSize(heightMeasureSpec);
		int areaWidth = inputWidth - getPaddingLeft() - getPaddingRight();
		int areaHeight = inputHeight - getPaddingTop() - getPaddingBottom();
		int tileWidth = areaWidth / columns;
		int tileHeight = areaHeight / rows;
		tileSize = Math.min(tileWidth, tileHeight);
		Tile.setSize(tileSize);
		
		int tileAreaWidth = tileSize * columns;
		int tileAreaHeight = tileSize * rows;
		int desireWidth = tileAreaWidth + getPaddingLeft() + getPaddingRight();
		int desireHeight = tileAreaHeight + getPaddingTop() + getPaddingBottom();
		int outputWidth = resolveSize(desireWidth, widthMeasureSpec);
		int outputHeight = resolveSize(desireHeight, heightMeasureSpec);
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		switch (heightMode) {
			case MeasureSpec.UNSPECIFIED: // 0
			case MeasureSpec.AT_MOST: // -2147483648
				offsetToCenterY = 0;
				break;
			case MeasureSpec.EXACTLY: // 1073741824
				offsetToCenterY = (areaHeight - tileAreaHeight) / 2;
				break;
		}
		
		switch (widthMode) {
			case MeasureSpec.UNSPECIFIED: // 0
			case MeasureSpec.AT_MOST: // -2147483648
				offsetToCenterX = 0;
				break;
			case MeasureSpec.EXACTLY: // 1073741824
				offsetToCenterX = (areaWidth - tileAreaWidth) / 2;
				break;
		}
		
		setMeasuredDimension(outputWidth, outputHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (paint == null) {
			paint = new Paint();
		}
		
		// background
		{
			paint.setColor(Color.LTGRAY);
			canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 50, 50, paint);
			paint.setColor(Color.GRAY);
			canvas.drawRoundRect(paddingLeft, paddingTop, getWidth() - paddingLeft,
								 getHeight() - paddingTop, 25,
								 25, paint);
		}
		
		if (!tileGridManager.getTiles().isEmpty()) {
			for (Tile tile : tileGridManager.getTiles()) {
				int rx = (int) (tile.screenX * tileSize) + paddingLeft + offsetToCenterX;
				int ry = (int) (tile.screenY * tileSize) + paddingTop + offsetToCenterY;
				
				tempTileRect.set(rx, ry, rx + tileSize, ry + tileSize);
				
				tile.draw(canvas, paint, tempTileRect);
			}
		}
		
		invalidate();
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		directionDetector.onTouchEvent(event);
		return true;
	}
	
	public void restart() {
		tileGridManager.cleanup();
		invalidate();
	}
	
	public void stepBack() {
		tileGridManager.placeRandomTile();
		invalidate();
	}
	
	public void createGrid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		
		tileGridManager.createGrid(columns, rows);
		
		tileGridManager.createTile(0, 0, 2);
		tileGridManager.createTile(2, 0, 2);
		tileGridManager.createTile(3, 0, 4);
		tileGridManager.createTile(4, 0, 8);
		
		tileGridManager.createTile(0, 4, 2);
		tileGridManager.createTile(2, 4, 2);
		tileGridManager.createTile(3, 4, 2);
		tileGridManager.createTile(4, 4, 2);
		
		requestLayout();
	}
}
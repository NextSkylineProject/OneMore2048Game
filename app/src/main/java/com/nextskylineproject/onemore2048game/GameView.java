package com.nextskylineproject.onemore2048game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private static final String TAG = "Debug";
	private Paint paint;
	private int columns = 2;
	private int rows = 2;
	private final TileGridManager tileGridManager;
	private final SwipeDirectionDetector directionDetector;
	private int tileSize;
	private int paddingLeft;
	private int paddingTop;
	private int offsetToCenterX;
	private int offsetToCenterY;
	
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
		
		loadResource();
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
			case MeasureSpec.UNSPECIFIED: // 0 wrap_content
			case MeasureSpec.AT_MOST: // -2147483648 match_parent
				offsetToCenterY = 0;
				break;
			case MeasureSpec.EXACTLY: // 1073741824 hard
				offsetToCenterY = (areaHeight - tileAreaHeight) / 2;
				break;
		}
		
		switch (widthMode) {
			case MeasureSpec.UNSPECIFIED: // 0 wrap_content
			case MeasureSpec.AT_MOST: // -2147483648 match_parent
				offsetToCenterX = 0;
				break;
			case MeasureSpec.EXACTLY: // 1073741824 hard
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
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 0, 0, paint);
			paint.setColor(Color.LTGRAY);
			canvas.drawRoundRect(paddingLeft, paddingTop, getWidth() - paddingLeft,
								 getHeight() - paddingTop, 25,
								 25, paint);
			paint.setColor(Color.GRAY);
			canvas.drawRoundRect(paddingLeft + offsetToCenterX, paddingTop + offsetToCenterY,
								 paddingLeft + offsetToCenterX + tileSize * columns,
								 paddingTop + offsetToCenterY + tileSize * rows, 0,
								 0, paint);
		}
		
		if (!tileGridManager.getTiles().isEmpty()) {
			for (Tile tile : tileGridManager.getTiles()) {
				int posX = (int) (tile.screenX * tileSize) + paddingLeft + offsetToCenterX;
				int posY = (int) (tile.screenY * tileSize) + paddingTop + offsetToCenterY;
				tile.draw(canvas, paint, posX, posY);
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
	
	public void loadResource() {
		Bitmap tileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tile);
		Tile.setBitmap(tileBitmap);
	}
	
	public void restart() {
		tileGridManager.cleanup();
	}
	
	public void stepBack() {
		tileGridManager.placeRandomTile();
	}
	
	public void setGridSize(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		tileGridManager.createGrid(columns, rows);
	}
	
	public void createTile(int x, int y, int val) {
		tileGridManager.createTile(x, y, val);
	}
}
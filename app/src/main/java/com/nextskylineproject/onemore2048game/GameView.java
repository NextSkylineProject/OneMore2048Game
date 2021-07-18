package com.nextskylineproject.onemore2048game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	
	public GameView(Context context) {
		super(context);
		
		tileGridManager = new TileGridManager();
		directionDetector = new SwipeDirectionDetector(context) {
			public void onDirectionDetected(Direction direction) {
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
				tileGridManager.placeRandomTile();
				invalidate();
			}
		};
		
		/*
		animator = ValueAnimator.ofFloat(0f, 1f);
		animator.setDuration(5000);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				Log.d(TAG, "onAnimationStart: start anim");
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				Log.d(TAG, "onAnimationEnd: end anim");
				
				
				
			}
		});
		animator.addUpdateListener(valueAnimator -> {
			animVal = (float) valueAnimator.getAnimatedValue();
			
			Log.d(TAG, "GameView: " + animVal);
			
			invalidate();
		});
//		animator.start();
*/
	}
	
	public GameView(Context context, AttributeSet attr) {
		this(context);
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
		
		paint.setColor(Color.LTGRAY);
		canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 50, 50, paint);
		paint.setColor(Color.GRAY);
		canvas.drawRoundRect(paddingLeft, paddingTop, getWidth() - paddingLeft,
				getHeight() - paddingTop, 25,
				25, paint);
//		paint.setColor(Color.GRAY);
//		for (int y = 0; y < rows; y++) {
//			for (int x = 0; x < columns; x++) {
//				int rx = x * tileSize + paddingLeft + offsetToCenterX;
//				int ry = y * tileSize + paddingTop + offsetToCenterY;
//
//				float[] hsv = {(1 + x) * (1 + y) * (rows + columns), 1, 1};
//
//				paint.setColor(Color.HSVToColor(hsv));
//				canvas.drawRect(rx, ry, rx + tileSize, ry + tileSize, paint);
//			}
//		}
		if (!tileGridManager.getTiles().isEmpty()) {
			for (Tile tile : tileGridManager.getTiles()) {
				int rx = tile.x * tileSize + paddingLeft + offsetToCenterX;
				int ry = tile.y * tileSize + paddingTop + offsetToCenterY;
				
				paint.setColor(Color.RED);
				canvas.drawRoundRect(rx, ry, rx + tileSize, ry + tileSize, 25, 25, paint);
				paint.setColor(Color.BLACK);
				int fs = tileSize / 4;
				paint.setTextSize(fs);
				canvas.drawText("Val:" + tile.value, rx + 10, ry + fs, paint);
			}
		}
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
		requestLayout();
	}
	
	public void createGrid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		
		tileGridManager.createGrid(columns, rows);
		tileGridManager.createTile(0, 0, 2);
		tileGridManager.createTile(3, 0, 2);
		
		requestLayout();
	}
}
package com.nextskylineproject.onemore2048game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
	private static final String TAG = "Debug";
	private Paint paint;
	private int columns = 4;
	private int rows = 5;
	private final TileGridManager tileGridManager;
	private int defTileSize = 64;
	private SwipeDirectionDetector directionDetector;
	
	
	public GameView(Context context, AttributeSet attr) {
		super(context);
		
		tileGridManager = new TileGridManager();
		tileGridManager.createGrid(columns, rows);
		
		defTileSize = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, defTileSize, getResources().getDisplayMetrics());
		
		directionDetector = new SwipeDirectionDetector(context) {
			public void onDirectionDetected(Direction direction) {
				Log.d(TAG, "onDirectionDetected: " + direction.name());
				switch (direction) {
					case UP:
						tileGridManager.swipeUp();
						break;
					case DOWN:
						tileGridManager.swipeDown();
						break;
					case LEFT:
						tileGridManager.swipeLeft();
						break;
					
					case RIGHT:
						tileGridManager.swipeRight();
						break;
				}
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
	
	public GameView(Context context) {
		this(context, null);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// Момент прикрепления вьюшки к окну
		// тут удобнее всего искать остальные вьюшки лайаута если нужно (???)
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int desiredWidth = defTileSize * columns;
		int desiredHeight = defTileSize * rows;
		desiredWidth = desiredWidth + getPaddingLeft() + getPaddingRight();
		desiredHeight = desiredHeight + getPaddingTop() + getPaddingBottom();
		
		int width = resolveSize(desiredWidth, widthMeasureSpec);
		int height = resolveSize(desiredHeight, heightMeasureSpec);
		
		tileGridManager.updateViewParams(this);
		
		/*
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		*/
		
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (paint == null) {
			paint = new Paint();
		}
		
		paint.setColor(Color.WHITE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		
		tileGridManager.draw(canvas, paint);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		directionDetector.onTouchEvent(event);
		return true; //super.onTouchEvent(event);
	}
	
	public void testAct1() {
		tileGridManager.clearGrid();
		invalidate();
	}
	
	public void testAct2() {
		tileGridManager.placeRandomTile();
		invalidate();
	}
	
	public void testAct3() {
		tileGridManager.swipeRight();
		invalidate();
	}
	
	public void testAct4() {
		tileGridManager.swipeLeft();
		invalidate();
	}
	
	public void testAct5() {
		tileGridManager.swipeUp();
		invalidate();
	}
	
	public void testAct6() {
		tileGridManager.swipeDown();
		invalidate();
	}
	
}
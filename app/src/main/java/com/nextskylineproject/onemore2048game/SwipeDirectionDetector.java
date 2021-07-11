package com.nextskylineproject.onemore2048game;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

abstract class SwipeDirectionDetector {
	private static final String TAG = "Debug";
	private float startX;
	private float startY;
	private final int touchSlop;
	private boolean isDetected;
	
	public abstract void onDirectionDetected(Direction direction);
	
	
	public SwipeDirectionDetector(Context context) {
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	
	public void onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				if (!isDetected) {
					onDirectionDetected(Direction.NOT_DETECTED);
				}
				
				startX = startY = 0.0F;
				isDetected = false;
				break;
			case MotionEvent.ACTION_MOVE:
				if (!isDetected && getDistance(event) > touchSlop) {
					isDetected = true;
					float x = event.getX();
					float y = event.getY();
					
					Direction direction = Direction.get(getAngle(startX, startY, x, y));
					onDirectionDetected(direction);
				}
				break;
		}
	}
	
	private float getDistance(MotionEvent ev) {
		float distanceSum = 0;
		float dx = ev.getX(0) - startX;
		float dy = ev.getY(0) - startY;
		distanceSum += Math.sqrt(dx * dx + dy * dy);
		
		return distanceSum;
	}
	
	private double getAngle(float x1, float y1, float x2, float y2) {
		double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
		return (rad * 180 / Math.PI + 180) % 360;
	}
	
	
	public enum Direction {
		NOT_DETECTED,
		UP,
		DOWN,
		LEFT,
		RIGHT;
		
		public static Direction get(double angle) {
			if (inRange(angle, 45, 135)) {
				return Direction.UP;
			} else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
				return Direction.RIGHT;
			} else if (inRange(angle, 225, 315)) {
				return Direction.DOWN;
			} else {
				return Direction.LEFT;
			}
		}
		
		private static boolean inRange(double angle, float init, float end) {
			return (angle >= init) && (angle < end);
		}
	}
}


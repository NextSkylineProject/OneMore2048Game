package com.nextskylineproject.onemore2048game;

import android.animation.ValueAnimator;

abstract class AnimationList {
	public static int animDuration = 1000;
	
	
	public static void moveAnim(TileGridManager tileGridManager, TileGrid.MoveEvent moveEvent) {
	}
	
	public static void spawnAnim(TileGridManager tileGridManager, Tile tile) {
	}
	
	public static void moveAndHitAnim(TileGridManager tileGridManager, TileGrid.MoveEvent moveEvent) {
	}
	
	public static void moveAndMergeAnim(TileGridManager tileGridManager, TileGrid.MoveEvent moveEvent) {
	}
	
	public static class Anim extends ValueAnimator {
	
	}
	
	
}
//		ValueAnimator animator = ValueAnimator.ofFloat();
//		animator.setDuration(animSpeed);
//
//		Log.d(TAG, "tileMoveAction: " + moveEvent);
//
//		tile2 = moveEvent.hitTile;
//		if (moveAxisIsX) {
//			animator.setFloatValues(tile.gridX, moveEvent.newX);
//			animator.addUpdateListener(valueAnimator -> {
//				tile.screenX = (float) valueAnimator.getAnimatedValue();
//			});
//		} else {
//			animator.setFloatValues(tile.gridY, moveEvent.newY);
//			animator.addUpdateListener(valueAnimator -> {
//				tile.screenY = (float) valueAnimator.getAnimatedValue();
//			});
//		}
//		animator.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				super.onAnimationEnd(animation);
//
//				if (moveEvent.isEqual) {
//					tile2.value = tile2.value * 2;
//					removeTile(tile);
//				}
//
//
//				isAnimated = false;
//			}
//		});
//
//
//		if (moveEvent.isEqual) {
//			removeTileFromGridOnly(tile);
//		} else {
//			replaceTile(tile, moveEvent.newX, moveEvent.newY);
//		}
//
//		isAnimated = true;
//		animator.start();
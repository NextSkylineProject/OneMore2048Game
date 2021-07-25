package com.nextskylineproject.onemore2048game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;

abstract class AnimationList {
	public static int animMoveDuration = 1000;
	public static int animLvlUpDuration = (int) (animMoveDuration * 0.5);
	public static int animHitDuration = (int) (animMoveDuration * 0.5);
	
	public static void spawnAnim(TileGridManager tileGridManager, Tile tile) {
	
	}
	
	public static void moveAnim(TileGridManager tileGridManager, TileGrid.MoveEvent moveEvent) {
		ValueAnimator anim = ValueAnimator.ofFloat();
		anim.setDuration(animMoveDuration);
		Tile tile = moveEvent.getMovingTile();
		
		if (moveEvent.isMoveAxisIsX()) {
			anim.setFloatValues(tile.gridX, moveEvent.getNewX());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenX = (float) valueAnimator.getAnimatedValue();
			});
		} else {
			anim.setFloatValues(tile.gridY, moveEvent.getNewY());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenY = (float) valueAnimator.getAnimatedValue();
			});
		}
		anim.start();
	}
	
	public static void moveAndHitAnim(TileGridManager tileGridManager,
									  TileGrid.MoveEvent moveEvent) {
		AnimatorSet animatorSet = new AnimatorSet();
		
		ValueAnimator anim = ValueAnimator.ofFloat();
		anim.setDuration(animMoveDuration);
		Tile tile = moveEvent.getMovingTile();
		Tile tile2 = moveEvent.getHitTile();
		
		if (moveEvent.isMoveAxisIsX()) {
			anim.setFloatValues(tile.gridX, moveEvent.getNewX());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenX = (float) valueAnimator.getAnimatedValue();
			});
		} else {
			anim.setFloatValues(tile.gridY, moveEvent.getNewY());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenY = (float) valueAnimator.getAnimatedValue();
			});
		}
		ValueAnimator anim2 = ValueAnimator.ofFloat(0, 1);
		anim2.setDuration(animHitDuration);
		anim2.addUpdateListener(valueAnimator -> {
		
		});
		
		animatorSet.play(anim).before(anim2);
		animatorSet.start();
	}
	
	public static void moveAndMergeAnim(TileGridManager tileGridManager,
										TileGrid.MoveEvent moveEvent) {
		AnimatorSet animatorSet = new AnimatorSet();
		
		ValueAnimator anim = ValueAnimator.ofFloat();
		anim.setDuration(animMoveDuration);
		Tile tile = moveEvent.getMovingTile();
		Tile tile2 = moveEvent.getHitTile();
		
		if (moveEvent.isMoveAxisIsX()) {
			anim.setFloatValues(tile.gridX, moveEvent.getNewX());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenX = (float) valueAnimator.getAnimatedValue();
			});
		} else {
			anim.setFloatValues(tile.gridY, moveEvent.getNewY());
			anim.addUpdateListener(valueAnimator -> {
				tile.screenY = (float) valueAnimator.getAnimatedValue();
			});
		}
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				
				tileGridManager.removeTile(tile);
			}
		});
		
		ValueAnimator anim2 = ValueAnimator.ofFloat(1, 2, 1);
		anim2.setDuration(animLvlUpDuration);
		anim2.addUpdateListener(valueAnimator -> {
//			tile2.scale = (float) valueAnimator.getAnimatedValue();
			tile2.setScale((float) valueAnimator.getAnimatedValue());
			
		});
		
		animatorSet.play(anim).before(anim2);
		animatorSet.start();
//		anim.start();
	}
}
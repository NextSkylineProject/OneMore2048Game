package com.nextskylineproject.onemore2048game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
	private static final String TAG = "Debug";
	private int columns;
	private int rows;
	private GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Intent intent = getIntent();
		columns = intent.getIntExtra("game_columns", 4);
		rows = intent.getIntExtra("game_rows", 4);
		
		int padding = 50;
		ConstraintLayout cl = findViewById(R.id.gameLayout);
		gameView = (GameView) cl.getChildAt(0); // Fuck R.id.* !!! (Don't work)
		gameView.setPadding(padding, padding, padding, padding);
		gameView.setGridSize(columns, rows);
		
		gameStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@SuppressLint("NonConstantResourceId")
	public void buttonAction(View view) {
		Button btn = (Button) view;
		
		switch (btn.getId()) {
			case R.id.btnRestart:
				gameView.restart();
				break;
			case R.id.btnStepBack:
				gameView.stepBack();
				break;
		}
	}
	
	private void gameStart() {
		// test temp code

		gameView.createTile(0, 0, 2);
		gameView.createTile(2, 0, 2);
		gameView.createTile(3, 0, 4);
		gameView.createTile(4, 0, 8);

		gameView.createTile(0, 4, 2);
		gameView.createTile(2, 4, 2);
		gameView.createTile(3, 4, 2);
		gameView.createTile(4, 4, 2);
	}
}
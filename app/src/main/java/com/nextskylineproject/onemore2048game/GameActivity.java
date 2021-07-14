package com.nextskylineproject.onemore2048game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
	private GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		ConstraintLayout cl = findViewById(R.id.gameLayout);
		gameView = (GameView) cl.getChildAt(0); // Fuck R.id.* !!! (Don't work)
		
		int padding = 50;
		gameView.setPadding(padding, padding, padding, padding);
		
		// test temp code
		int columns = 5;
		int rows = 5;
		
		gameView.createGrid(columns, rows);
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
}
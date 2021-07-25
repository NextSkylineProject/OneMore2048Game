package com.nextskylineproject.onemore2048game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
	private TextView tvColumnsCount;
	private TextView tvRowsCount;
	private SeekBar sbColumns;
	private SeekBar sbRows;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvColumnsCount = findViewById(R.id.textViewColumnsCount);
		tvRowsCount = findViewById(R.id.textViewRowsCount);
		sbColumns = findViewById(R.id.seekBarColumns);
		sbRows = findViewById(R.id.seekBarRows);
		sbColumns.setOnSeekBarChangeListener(this);
		sbRows.setOnSeekBarChangeListener(this);
	}
	
	@SuppressLint("NonConstantResourceId")
	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		switch (seekBar.getId()) {
			case R.id.seekBarColumns:
				tvColumnsCount.setText(String.valueOf(i));
				break;
			case R.id.seekBarRows:
				tvRowsCount.setText(String.valueOf(i));
				break;
		}
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
	
	public void createBtnAction(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("game_columns", sbColumns.getProgress());
		intent.putExtra("game_rows", sbRows.getProgress());
		startActivity(intent);
	}
}
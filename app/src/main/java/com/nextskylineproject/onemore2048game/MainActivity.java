package com.nextskylineproject.onemore2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "Debug";
	
	private GameView gm;
	private Button btn1;
	private Button btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FrameLayout fl = findViewById(R.id.gameFieldFrame);
		btn1 = findViewById(R.id.actButton2);
		btn2 = findViewById(R.id.actButton1);
		
		gm = new GameView(this);
//		gm.setLayoutParams(new FrameLayout.LayoutParams(100,300));
		gm.setPadding(20, 20, 20, 20);

		fl.addView(gm);
	}
	
	
	@SuppressLint("NonConstantResourceId")
	public void btnTestAct(View view) {
		Button btn = (Button) view;
		
		switch (btn.getId()) {
			case R.id.actButton1:
				gm.testAct1();
				break;
			case R.id.actButton2:
				gm.testAct2();
				break;
			case R.id.btnRight:
				gm.testAct3();
				break;
			case R.id.btnLeft:
				gm.testAct4();
				break;
			case R.id.btnUp:
				gm.testAct5();
				break;
			case R.id.btnDown:
				gm.testAct6();
				break;
		}
	}
}
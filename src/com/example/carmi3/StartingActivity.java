package com.example.carmi3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.starting);
	}
	
	
	public void welcomeClick(View view) {
		Intent intent = new Intent("com.example.carmi3.SelectionActivity");
		startActivity(intent);
	}	
	
}

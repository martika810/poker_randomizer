package com.example.poker_randomizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NumberPlayerActivity extends Activity {
	private PreferencesHelper prefs;
	private static String NUM_PLAYERS = "num_players";
	
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_number_players);
		prefs=new PreferencesHelper(getApplicationContext());
		addButtonListener();
	}

	private void addButtonListener() {
		TextView btn_two_players = (TextView) findViewById(R.id.btn_two_players);
		btn_two_players.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{	//two players
			
				prefs.savePreferences(NUM_PLAYERS, Integer.toString(2));
				
				startActivity(new Intent(NumberPlayerActivity.this,MainActivity.class));
			}
		});
		TextView btn_three_players = (TextView) findViewById(R.id.btn_three_players);
		btn_three_players.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				prefs.savePreferences(NUM_PLAYERS, Integer.toString(3));
				startActivity(new Intent(NumberPlayerActivity.this,ThreePlayersActivity.class));
			}
		});
		TextView btn_four_players = (TextView) findViewById(R.id.btn_four_players);
		btn_four_players.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prefs.savePreferences(NUM_PLAYERS, Integer.toString(4));
				startActivity(new Intent(NumberPlayerActivity.this,FourPlayersActivity.class));
			}
		});
	}
	
	
}

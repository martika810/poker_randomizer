package com.example.poker_randomizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FourPlayersActivity extends PokerActivity {

	static String ACTION_NEW_HAND = "android.intent.action.MAIN3";
	
	protected void onCreate(Bundle savedInstanceState) {
		String card_picked;
		data_file = "four_hands_results.txt";
		super.onCreate(savedInstanceState);
		
		hand_resulter = new HandResulter(getApplicationContext());
		// check if the file exist, if so load the json data in HandRecorder
		// if create and initialize the HandRecorder
		String strDataHandRecorder = gson.toJson(new HandRecorder());
		try {
			if (!getFileStreamPath(data_file).exists()) {

				hand_recorder = new HandRecorder();
				writeHandRecorderToFile(hand_recorder);

			} else {
				hand_recorder = readHandRecorderFromFile();

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		setContentView(R.layout.four_players);
		TextView number_handTxt = (TextView) findViewById(R.id.txt_number_hands);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());

		

		int position_card_pick = r.nextInt(cards.size());
		// String strImage="R.drawable"+cards.get(number_card_pick);
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player 1

		position_card_pick = r.nextInt(cards.size());
		String card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked2);
		cards.remove(position_card_pick);

		CardPairView cardpairPlayer1 = (CardPairView) findViewById(R.id.pair_player1);
		cardpairPlayer1.init(card_picked, card_picked2);
		cardpairPlayer1.invalidate();

		// pick card 1 for player 2

		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player 2

		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked2);
		cards.remove(position_card_pick);

		CardPairView cardpairPlayer2 = (CardPairView) findViewById(R.id.pair_player2);
		cardpairPlayer2.init(card_picked, card_picked2);
		cardpairPlayer2.invalidate();

		// pick card 1 for player3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(3, card_picked);
		cards.remove(position_card_pick);

		// pick card 1 for player 3
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(3, card_picked2);
		cards.remove(position_card_pick);

		CardPairView cardpairPlayer3 = (CardPairView) findViewById(R.id.pair_player3);
		cardpairPlayer3.init(card_picked, card_picked2);
		cardpairPlayer3.invalidate();

		// pick card 1 for player 4
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(4, card_picked);
		cards.remove(position_card_pick);

		// pick card 1 for player 4
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(4, card_picked2);
		cards.remove(position_card_pick);

		CardPairView cardpairPlayer4 = (CardPairView) findViewById(R.id.pair_player4);
		cardpairPlayer4.init(card_picked, card_picked2);
		cardpairPlayer4.invalidate();

		addButtonListener();

	}

	

	
	public void addButtonListener() {
		super.addButtonListener();

		TextView btnNextHand = (TextView) findViewById(R.id.txt_next_hand);
		btnNextHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(FourPlayersActivity.this,
						FourPlayersActivity.class));

			}
		});
		RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.board3_radio_player1);
		radioPlayer1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				buttonView.setChecked(isChecked);
			}
		});
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.board3_radio_player2);
		radioPlayer2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				buttonView.setChecked(isChecked);
			}
		});

		RadioButton radioPlayer3 = (RadioButton) findViewById(R.id.board3_radio_player3);
		radioPlayer3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				buttonView.setChecked(isChecked);
			}
		});

		RadioButton radioPlayer4 = (RadioButton) findViewById(R.id.board3_radio_player4);
		radioPlayer4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				buttonView.setChecked(isChecked);

			}
		});
		
		ImageView btn_statistics=(ImageView)findViewById(R.id.btn_statistics);
		btn_statistics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start statistics activity
				Intent statisticsIntent = new Intent();
				statisticsIntent.putExtra("ParentClassName", "FourPlayersActivity");
				startActivity(statisticsIntent.setClass(FourPlayersActivity.this,
						StatisticResultActivity.class));
				
				
			}
		});
		
		ImageView btn_players=(ImageView)findViewById(R.id.btn_players);
		btn_players.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start statistics activity
				startActivity(new Intent(FourPlayersActivity.this,
						NumberPlayerActivity.class));
				
				
			}
		});

	}
	public void resulting() throws Exception {
		
		// ((Button) v).setCompoundDrawablesWithIntrinsicBounds(
		// R.drawable.winner, 0, 0, 0);
		// ((Button) v).setPadding(60, 0, 20, 0);
		// // RESOLVE THE HAND
		ResultHand resultplayer1 = hand_resulter.getResult("1");
		ResultHand resultplayer2 = hand_resulter.getResult("2");
		ResultHand resultplayer3 = hand_resulter.getResult("3");
		ResultHand resultplayer4 = hand_resulter.getResult("4");
		// int valueResultPlayer1 = resultplayer1.getSumValues();
		// int valueResultPlayer2 = resultplayer2.getSumValues();
		// int valueResultPlayer3 = resultplayer3.getSumValues();
		int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
		int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
		int totalScore3 = Integer.valueOf(resultplayer3.getTypeHand());
		int totalScore4 = Integer.valueOf(resultplayer4.getTypeHand());

		List<Integer> playerLayouts = new ArrayList<Integer>();
		playerLayouts.add(R.id.inner_player_panel1);
		playerLayouts.add(R.id.inner_player_panel2);
		playerLayouts.add(R.id.inner_player_panel3);
		playerLayouts.add(R.id.inner_player_panel4);

		List<Integer> lTotalScore;
		List<ResultHand> lResultHand;
		List<Integer> winner_player_num;

		boolean player1_won;
		String strWhoWon = "";
		// Check if player1 has won, to store the result in the
		// hand_recorder
		//
		// // save the totalScore in a vector to check
		lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2,
				totalScore3,totalScore4);
		lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2,
				resultplayer3,resultplayer4);
		// Once u know the highest hand, check who it belongs to
		int winner=getAndHighLightWinner(playerLayouts, lTotalScore, lResultHand,
				HandResulter.FIRST_STAGE);
		//
		//
		//
		// // Save save player1's hand for the statistic
		switch(winner){
		case 0:
			hand_recorder.saveResult(resultplayer1.getTypeHand());
			break;
		case 1:
			hand_recorder.saveResult(resultplayer2.getTypeHand());
			break;
		case 2:
			hand_recorder.saveResult(resultplayer3.getTypeHand());
			break;
		case 3:
			hand_recorder.saveResult(resultplayer4.getTypeHand());
			break;
		default:
			throw new Exception("Error Unknown player");
			
			
	}
		// Store the type of hand(player1)
		List<Card> handPlayer1 = hand_resulter.getCards_player1();
		hand_recorder.addHand(handPlayer1);
		try {
			writeHandRecorderToFile(hand_recorder);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		//

	}
	private void cleanRadioSelection() {
		RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.board3_radio_player1);
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.board3_radio_player2);
		RadioButton radioPlayer3 = (RadioButton) findViewById(R.id.board3_radio_player3);
		RadioButton radioPlayer4 = (RadioButton) findViewById(R.id.board3_radio_player4);
		radioPlayer1.setChecked(false);
		radioPlayer2.setChecked(false);
		radioPlayer3.setChecked(false);
		radioPlayer4.setChecked(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.statistics:

			Intent statisticsIntent = new Intent();
			statisticsIntent.putExtra("ParentClassName", "FourPlayersActivity");
			startActivity(statisticsIntent.setClass(FourPlayersActivity.this,
					StatisticResultActivity.class));
			return true;
		case R.id.change_number_users:
			startActivity(new Intent(FourPlayersActivity.this,
					NumberPlayerActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}

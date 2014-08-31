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
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

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

public class ThreePlayersActivity extends ActionBarActivity {
	String[] typeCard = { "s", "h", "d", "c" };
	private static final String[] typeNumbers = { "2", "3", "4", "5", "6", "7",
			"8", "9", "x", "j", "q", "k", "a" };
	List<String> cards = new ArrayList<String>();
	Random r = new Random();
	private static final String ACTION_NEW_HAND = "android.intent.action.MAIN3";
	private final static String data_file = "three_hands_results.txt";
	private HandRecorder hand_recorder;
	private HandResulter hand_resulter;
	Gson gson = new GsonBuilder().create();

	protected void onCreate(Bundle savedInstanceState) {
		String card_picked;
		super.onCreate(savedInstanceState);
		populateCards();
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

		setContentView(R.layout.three_players);
		TextView number_handTxt = (TextView) findViewById(R.id.board3_number_hands_txt);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());

		// pick card 1

		int position_card_pick = r.nextInt(cards.size());
		// String strImage="R.drawable"+cards.get(number_card_pick);
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked);
		cards.remove(position_card_pick);

		// pick card 2
		position_card_pick = r.nextInt(cards.size());
		String card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked2);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer1 = (CardPairView) findViewById(R.id.pair_player1);
		cardPairPlayer1.init(card_picked, card_picked2);
		cardPairPlayer1.invalidate();

		// pick card 3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player2
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked2);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer2 = (CardPairView) findViewById(R.id.pair_player2);
		cardPairPlayer2.init(card_picked, card_picked2);
		cardPairPlayer2.invalidate();

		// pick card 1 for player3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(3, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player 3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(3, card_picked);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer3 = (CardPairView) findViewById(R.id.pair_player3);
		cardPairPlayer3.init(card_picked, card_picked2);
		cardPairPlayer3.invalidate();

		addButtonListener();

	}

	private void populateCards() {
		for (String type : typeCard) {
			for (String num : typeNumbers) {
				cards.add(type + num);

			}
		}
	}

	// those two methods needs to be refactor
	private HandRecorder readHandRecorderFromFile() throws IOException {

		FileInputStream fis = openFileInput(data_file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String strJson = br.readLine();

		HandRecorder handRecorder = loadJsonToHandRecorder(strJson);

		br.close();
		return handRecorder;
	}

	private void writeHandRecorderToFile(HandRecorder handRecorder)
			throws FileNotFoundException {
		File file = new File(data_file);
		FileOutputStream fos = openFileOutput(data_file, MODE_PRIVATE);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(fos)));
		String strDataHandRecorder = gson.toJson(handRecorder);
		pw.print(strDataHandRecorder);
		pw.close();

	}

	private HandRecorder loadJsonToHandRecorder(String strJson) {

		HandRecorder handRecorder = gson.fromJson(strJson, HandRecorder.class);
		return handRecorder;

	}

	private void addButtonListener() {
		Button btnFlop = (Button) findViewById(R.id.board3_btn_flop);
		btnFlop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Set image for the flop cards
				ImageView image_card1_flop = (ImageView) findViewById(R.id.board3_image_card1);
				int position_card_pick = r.nextInt(cards.size());
				String card_picked = cards.get(position_card_pick);
				hand_resulter.addCardBoard(card_picked);
				int identifier = getResources().getIdentifier(card_picked,
						"drawable", "com.example.poker_randomizer");
				cards.remove(position_card_pick);
				image_card1_flop.setImageResource(identifier);

				// second car flop
				ImageView image_card2_flop = (ImageView) findViewById(R.id.board3_image_card2);
				position_card_pick = r.nextInt(cards.size());
				card_picked = cards.get(position_card_pick);
				hand_resulter.addCardBoard(card_picked);
				identifier = getResources().getIdentifier(card_picked,
						"drawable", "com.example.poker_randomizer");
				cards.remove(position_card_pick);
				image_card2_flop.setImageResource(identifier);

				// third card flop
				ImageView image_card3_flop = (ImageView) findViewById(R.id.board3_image_card3);
				position_card_pick = r.nextInt(cards.size());
				card_picked = cards.get(position_card_pick);
				hand_resulter.addCardBoard(card_picked);
				identifier = getResources().getIdentifier(card_picked,
						"drawable", "com.example.poker_randomizer");
				cards.remove(position_card_pick);
				image_card3_flop.setImageResource(identifier);

				v.setVisibility(View.GONE);

			}
		});
		Button btnTurn = (Button) findViewById(R.id.board3_btn_turn);
		btnTurn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// card flop
				ImageView image_card4_turn = (ImageView) findViewById(R.id.board3_image_card4);
				int position_card_pick = r.nextInt(cards.size());
				String card_picked = cards.get(position_card_pick);
				hand_resulter.addCardBoard(card_picked);
				int identifier = getResources().getIdentifier(card_picked,
						"drawable", "com.example.poker_randomizer");
				cards.remove(position_card_pick);
				image_card4_turn.setImageResource(identifier);

				v.setVisibility(View.GONE);

			}
		});
		Button btnRiver = (Button) findViewById(R.id.board3_btn_river);
		btnRiver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// card flop
				ImageView image_card5_river = (ImageView) findViewById(R.id.board3_image_card5);
				int position_card_pick = r.nextInt(cards.size());
				String card_picked = cards.get(position_card_pick);
				hand_resulter.addCardBoard(card_picked);
				int identifier = getResources().getIdentifier(card_picked,
						"drawable", "com.example.poker_randomizer");
				cards.remove(position_card_pick);
				image_card5_river.setImageResource(identifier);

				// v.setVisibility(View.GONE);
				// LinearLayout layout_winner = (LinearLayout)
				// findViewById(R.id.panel_winner);
				// layout_winner.setVisibility(View.VISIBLE);
				v.setBackgroundColor(Color.GREEN);
				((Button) v).setTextColor(Color.BLACK);
				((Button) v).setEnabled(false);

				int drwWinnerIcon = R.drawable.winner;

				((Button) v).setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.winner, 0, 0, 0);
				((Button) v).setPadding(60, 0, 20, 0);
				// RESOLVE THE HAND
				ResultHand resultplayer1 = hand_resulter.getResult("1");
				ResultHand resultplayer2 = hand_resulter.getResult("2");
				ResultHand resultplayer3 = hand_resulter.getResult("3");
				// int valueResultPlayer1 = resultplayer1.getSumValues();
				// int valueResultPlayer2 = resultplayer2.getSumValues();
				// int valueResultPlayer3 = resultplayer3.getSumValues();
				int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
				int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
				int totalScore3 = Integer.valueOf(resultplayer3.getTypeHand());

				List<Integer> playerLayouts = new ArrayList<Integer>();
				playerLayouts.add(R.id.inner_player_panel1);
				playerLayouts.add(R.id.inner_player_panel2);
				playerLayouts.add(R.id.inner_player_panel3);

				List<Integer> lTotalScore;
				List<ResultHand> lResultHand;
				List<Integer> winner_player_num;

				boolean player1_won;
				String strWhoWon = "";
				// Check if player1 has won, to store the result in the
				// hand_recorder

				// save the totalScore in a vector to check
				lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2,
						totalScore3);
				lResultHand = Util.buildIntegerArrays(resultplayer1,
						resultplayer2, resultplayer3);
				// Once u know the highest hand, check who it belongs to
				getAndHighLightWinner(playerLayouts,lTotalScore, lResultHand, v, HandResulter.FIRST_STAGE);
		


				// Save save player1's hand for the statistic
				hand_recorder.saveResult(resultplayer1.getTypeHand());

			}
		});

		Button btnNextHand = (Button) findViewById(R.id.board3_btn_next_hand);
		btnNextHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Store the type of hand(player1)
				List<Card> handPlayer1 = hand_resulter.getCards_player1();
				hand_recorder.addHand(handPlayer1);
				try {
					writeHandRecorderToFile(hand_recorder);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				startActivity(new Intent(ThreePlayersActivity.this,
						ThreePlayersActivity.class));

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

	}

	// when just one player has winner hand
	// Return 0 if two or more players have the same hand
	private void getAndHighLightWinner(List<Integer> playerLayoutsIds,
			List<Integer> totalScores, List<ResultHand> resultHands, View v,
			int resulting_stage) {
		// Return the player number of the player who won the hand
		int maxTotalScore = Util.getMax(totalScores);
		int currentPos = 0;
		String strWhoWon = "";
		List<Integer> lwinners = new ArrayList<Integer>();
		//int times = Collections.frequency(totalScores, maxTotalScore);
		// if (times>1) return 0;
		// Once u know the highest hand, check who it belongs to
		for (int score : totalScores) {
			if (maxTotalScore == score) {
				lwinners.add(currentPos);
			}
			currentPos++;
		}
		if (lwinners.size() == 1) {
			int winner = lwinners.get(0);
			highlighWinner(playerLayoutsIds.get(winner), R.string.player1,
					resultHands.get(winner), v);
			if (winner == 0) {
				hand_recorder.setPlayer1_won(true);
			} else
				hand_recorder.setPlayer1_won(false);
		} else {
			switch (resulting_stage) {
			case HandResulter.FIRST_STAGE:
				//Recreate the totalScore for the winners, as there is several player with the same hand,
				//the value of the hand has to count as well
				resultHands=Util.getSubVectorResultFromPositions(lwinners,resultHands);
				totalScores=new ArrayList<Integer>();
				for (ResultHand result:resultHands){
					totalScores.add(Integer.valueOf(result.getTypeHand())+result.getSumValues());
				}
				 playerLayoutsIds=Util.getSubVectorFromPositions(lwinners,playerLayoutsIds);
				getAndHighLightWinner(playerLayoutsIds,totalScores,resultHands,v,HandResulter.SECOND_STAGE);
				break;
			case HandResulter.SECOND_STAGE:
				resultHands=Util.getSubVectorResultFromPositions(lwinners,resultHands);
				totalScores=new ArrayList<Integer>();
				for (ResultHand result:resultHands){
					totalScores.add(Card.valuesCards.get(hand_resulter.maxCard_from_playerHand("1").getCardNumber()));
				}
				playerLayoutsIds=Util.getSubVectorFromPositions(lwinners,playerLayoutsIds);
				getAndHighLightWinner(playerLayoutsIds,totalScores,resultHands,v,HandResulter.THIRD_STAGE);
				break;
			case HandResulter.THIRD_STAGE:
				
				break;
			case HandResulter.FOURTH_STAGE:
				break;
			}
		}

		
	}

	// winner_player_num=getAndHighLightWinner(playerLayouts,
	// lTotalScore,lResultHand,v);
	// if(winner_player_num==0){
	// String
	// highCardPlayer1=hand_resulter.maxCard_from_playerHand("1").getCardNumber();
	// String
	// highCardPlayer2=hand_resulter.maxCard_from_playerHand("2").getCardNumber();
	// String
	// highCardPlayer3=hand_resulter.maxCard_from_playerHand("3").getCardNumber();
	// totalScore1=Card.valuesCards.get(highCardPlayer1);
	// totalScore2=Card.valuesCards.get(highCardPlayer2);
	// totalScore3=Card.valuesCards.get(highCardPlayer3);
	// winner_player_num=getAndHighLightWinner(playerLayouts,
	// lTotalScore,lResultHand,v);
	private void highlighWinner(int playerLayoutId, int strPlayerId,
			ResultHand result, View v) {
		String strWhoWon;
		LinearLayout panelPlayer = (LinearLayout) findViewById(playerLayoutId);
		panelPlayer.setBackgroundResource(R.drawable.button_winner);
		strWhoWon = getString(strPlayerId) + " won with "
				+ result.getNameResult();
		((Button) v).setText(strWhoWon);
	}

	private void cleanRadioSelection() {
		RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.board3_radio_player1);
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.board3_radio_player2);
		RadioButton radioPlayer3 = (RadioButton) findViewById(R.id.board3_radio_player3);
		radioPlayer1.setChecked(false);
		radioPlayer2.setChecked(false);
		radioPlayer3.setChecked(false);

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
			statisticsIntent
					.putExtra("ParentClassName", "ThreePlayersActivity");
			startActivity(statisticsIntent.setClass(ThreePlayersActivity.this,
					StatisticResultActivity.class));
			return true;
		case R.id.change_number_users:
			startActivity(new Intent(ThreePlayersActivity.this,
					NumberPlayerActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle presses on the action bar items
	// switch (item.getItemId()) {
	// case R.id.action_search:
	// openSearch();
	// return true;
	// case R.id.action_compose:
	// composeMessage();
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_main, container,
	// false);
	// return rootView;
	// }
	// }

}

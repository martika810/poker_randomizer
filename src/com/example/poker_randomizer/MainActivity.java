package com.example.poker_randomizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends PokerActivity implements
		FragmentManager.OnBackStackChangedListener {
	AlertDialog alertDialog;
	


	protected void onCreate(Bundle savedInstanceState) {
		String card_picked;
		data_file = "hands_results.txt";
		ACTION_NEW_HAND = "android.intent.action.MAIN2";

		super.onCreate(savedInstanceState);
		
		//setTextViewFonts();
		// Populate the board cards
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

		setContentView(R.layout.activity_main2);
		TextView number_handTxt = (TextView) findViewById(R.id.number_hands_txt);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());

		// pick card 1
		int position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked);
		cards.remove(position_card_pick);

		// pick card 2
		position_card_pick = r.nextInt(cards.size());
		String card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(1, card_picked2);
		cards.remove(position_card_pick);

		// Load the cards in the view
		CardPairView cardpair = (CardPairView) findViewById(R.id.pair_player1);
		cardpair.init(card_picked, card_picked2);
		cardpair.invalidate();
		int identifier;

		// pick card 3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked);
		cards.remove(position_card_pick);

		// pick card 4
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
		hand_resulter.addCardPlayer(2, card_picked2);
		cards.remove(position_card_pick);

		// Load the cards in the view
		CardPairView cardpairPlayer2 = (CardPairView) findViewById(R.id.pair_player2);
		cardpairPlayer2.init(card_picked, card_picked2);
		cardpairPlayer2.invalidate();

		addButtonListener();

	}

	
	public void setTextViewFonts(){
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"Chunkfive.otf");
		TextView txt_numberHand=(TextView)findViewById(R.id.number_hands_txt);
		TextView radio_player1=(TextView)findViewById(R.id.radio_player1);
		TextView radio_player2=(TextView)findViewById(R.id.radio_player2);
		TextView txt_nextHand=(TextView)findViewById(R.id.txt_next_hand);
		txt_numberHand.setTypeface(typeFace);
		radio_player1.setTypeface(typeFace);
		radio_player2.setTypeface(typeFace);
		txt_nextHand.setTypeface(typeFace);
	}

	
	
	public void addButtonListener() {
		
		super.addButtonListener();
		
		TextView txtNextHand = (TextView) findViewById(R.id.txt_next_hand);
		txtNextHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Store the type of hand(player1)
//				List<Card> handPlayer1 = hand_resulter.getCards_player1();
//				hand_recorder.addHand(handPlayer1);
//				try {
//					writeHandRecorderToFile(hand_recorder);
//				} catch (FileNotFoundException e) {
//
//					e.printStackTrace();
//				}
				startActivity(new Intent(MainActivity.this, MainActivity.class));

			}
		});
		RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.radio_player1);
		radioPlayer1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.radio_player2);
				if (isChecked) {
					radioPlayer2.setChecked(false);
				} else {
					radioPlayer2.setChecked(true);
					setCurrent_player_predictions("2");
					
				}

			}
		});
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.radio_player2);
		radioPlayer2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.radio_player1);
				if (isChecked) {

					radioPlayer1.setChecked(false);

				} else {
					radioPlayer1.setChecked(true);
					setCurrent_player_predictions("1");
				}

			}
		});
		
		ImageView btn_statistics=(ImageView)findViewById(R.id.btn_statistics);
		btn_statistics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start statistics activity
				Intent statisticsIntent = new Intent();
				statisticsIntent.putExtra("ParentClassName", "MainActivity");
				startActivity(statisticsIntent.setClass(MainActivity.this,
						StatisticResultActivity.class));
				
				
			}
		});
		
		ImageView btn_players=(ImageView)findViewById(R.id.btn_players);
		btn_players.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start statistics activity
				startActivity(new Intent(MainActivity.this,
						NumberPlayerActivity.class));
				
				
			}
		});

	}
	
	public void resulting(){
		
		//get total for player1 and player2
		ResultHand resultplayer1 = hand_resulter.getResult("1");
		ResultHand resultplayer2 = hand_resulter.getResult("2");
		int valueResultPlayer1 = resultplayer1.getSumValues();
		int valueResultPlayer2 = resultplayer2.getSumValues();
		int totalScore1;
		int totalScore2;
		if (resultplayer1.getTypeHand() != resultplayer2.getTypeHand()) {
			totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
			totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
		} else {
			totalScore1 = Integer.valueOf(resultplayer1.getTypeHand())
					+ valueResultPlayer1;
			totalScore2 = Integer.valueOf(resultplayer2.getTypeHand())
					+ valueResultPlayer2;
		}
		
		String strWhoWon = "";
		boolean player1_won;
		// Check if player1 has won, to store the result in the
		// hand_recorder
		if (totalScore1 > totalScore2) {
			hand_recorder.setPlayer1_won(true);
			LinearLayout panelPlayer1 = (LinearLayout) findViewById(R.id.inner_player_panel1);
			panelPlayer1
					.setBackgroundResource(R.drawable.button_winner);
			strWhoWon = getString(R.string.player1) + " won with "
					+ resultplayer1.getNameResult();

		} else if (totalScore1 < totalScore2) {
			hand_recorder.setPlayer1_won(false);
			LinearLayout panelPlayer2 = (LinearLayout) findViewById(R.id.inner_player_panel2);
			panelPlayer2
					.setBackgroundResource(R.drawable.button_winner);
			strWhoWon = getString(R.string.player2) + " won with "
					+ resultplayer2.getNameResult();
		} else {// in case equals
			// if the type of hand is the same for both player, check
			// which is the highest
			String highCardPlayer1 = hand_resulter.maxCard("1")
					.getCardNumber();
			String highCardPlayer2 = hand_resulter.maxCard("2")
					.getCardNumber();
			int highestPlayer1 = Card.valuesCards.get(highCardPlayer1);
			int highestPlayer2 = Card.valuesCards.get(highCardPlayer2);
			if (highestPlayer1 > highestPlayer2) {
				hand_recorder.setPlayer1_won(true);
				LinearLayout panelPlayer1 = (LinearLayout) findViewById(R.id.inner_player_panel1);
				panelPlayer1
						.setBackgroundResource(R.drawable.button_winner);
				strWhoWon = getString(R.string.player1) + " won with "
						+ resultplayer1.getNameResult();
			} else if (highestPlayer2 > highestPlayer1) {
				hand_recorder.setPlayer1_won(false);

				LinearLayout panelPlayer2 = (LinearLayout) findViewById(R.id.inner_player_panel2);
				panelPlayer2
						.setBackgroundResource(R.drawable.button_winner);
				strWhoWon = getString(R.string.player1) + " won with "
						+ resultplayer1.getNameResult();
			} else {
				System.out.println("Forever!!!!!!");
			}

		}
		//Hide the tap favorite textview and display the winner textview
		
		TextView winnerBtn=(TextView)findViewById(R.id.txt_winner);
		winnerBtn.setText(strWhoWon);
	   	winnerBtn.setVisibility(View.VISIBLE);
		
	   	//Save the player 1's hand type, whether (s)he won and the winning hand
		hand_recorder.saveResult(resultplayer1.getTypeHand());
		List<Card> handPlayer1 = hand_resulter.getCards_player1();
		hand_recorder.addHand(handPlayer1);
		try {
			writeHandRecorderToFile(hand_recorder);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		
	}



//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		switch (id) {
//		case R.id.statistics:
//			return true;
//		case R.id.change_number_users:
//			
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//
//	}







	// private void flipCards(int[] idCardLayouts) {
	// Random r = new Random();
	// int position_card_pick;
	// String card_picked;
	// int identifier;
	// for (int idLayout : idCardLayouts) {
	// position_card_pick = r.nextInt(cards.size());
	// card_picked = cards.get(position_card_pick);
	// hand_resulter.addCardBoard(card_picked);
	// identifier = getResources().getIdentifier(card_picked, "drawable",
	// "com.example.poker_randomizer");
	// cards.remove(position_card_pick);
	// // image_card1_flop.setImageResource(identifier);
	// flipCard(identifier, idLayout);
	// mShowingBack = false;
	// }
	//
	// }
	// pick a card from cards llist, returning the drawable id and removes the
	// card from the "card stack"
	

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

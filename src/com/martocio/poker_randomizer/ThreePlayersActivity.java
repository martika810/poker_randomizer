package com.martocio.poker_randomizer;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ThreePlayersActivity extends PokerActivity {
	
	// handle the animation and allow swiping horizontally
		private ViewPager mPager;

		// Provide pages for to the viewPager
		private PagerAdapter mPagerAdapter;

	//static String ACTION_NEW_HAND = "android.intent.action.MAIN3";

	protected void onCreate(Bundle savedInstanceState) {
		
		ACTION_NEW_HAND = "android.intent.action.MAIN3";
		
		super.onCreate(savedInstanceState);

		hand_recorder=HandRecorder.createInstance(this, HandRecorder.DATA_FILE_THREE_PLAYERS);
//		hand_resulter = new HandResulter(getApplicationContext());
//		// check if the file exist, if so load the json data in HandRecorder
//		// if create and initialize the HandRecorder
//		String strDataHandRecorder = gson.toJson(new HandRecorder());
//		try {
//			if (!getFileStreamPath(data_file).exists()) {
//
//				hand_recorder = new HandRecorder();
//				writeHandRecorderToFile(hand_recorder);
//
//			} else {
//				hand_recorder = readHandRecorderFromFile();
//
//			}
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
//
		setContentView(R.layout.threeplayers_activity_screen_slide);
		
		mPager=(ViewPager)findViewById(R.id.threeplayer_pager);
		mPagerAdapter=new ThreePlayerScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
/*		TextView number_handTxt = (TextView) findViewById(R.id.
				txt_number_hands);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());

		TextView number_guessTxt = (TextView) findViewById(R.id.number_guess_txt);
		number_guessTxt.setText("Guesses: "
				+ hand_recorder.getNumber_rigth_guesses() + "/"
				+ hand_recorder.getNumber_guesses());

		// pick card 1

		int position_card_pick = r.nextInt(cards.size());
		// String strImage="R.drawable"+cards.get(number_card_pick);
		card_picked = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(1, card_picked);
		cards.remove(position_card_pick);

		// pick card 2
		position_card_pick = r.nextInt(cards.size());
		String card_picked2 = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(1, card_picked2);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer1 = (CardPairView) findViewById(R.id.pair_player1);
		cardPairPlayer1.init(card_picked, card_picked2);
		cardPairPlayer1.invalidate();

		// pick card 3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(2, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player2
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(2, card_picked2);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer2 = (CardPairView) findViewById(R.id.pair_player2);
		cardPairPlayer2.init(card_picked, card_picked2);
		cardPairPlayer2.invalidate();

		// pick card 1 for player3
		position_card_pick = r.nextInt(cards.size());
		card_picked = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(3, card_picked);
		cards.remove(position_card_pick);

		// pick card 2 for player 3
		position_card_pick = r.nextInt(cards.size());
		card_picked2 = cards.get(position_card_pick);
//		hand_resulter.addCardPlayer(3, card_picked2);
		cards.remove(position_card_pick);

		// Load player1's cards
		CardPairView cardPairPlayer3 = (CardPairView) findViewById(R.id.pair_player3);
		cardPairPlayer3.init(card_picked, card_picked2);
		cardPairPlayer3.invalidate();

		addButtonListener();
*/
	}

	public ViewPager getmPager(){
		if(mPager==null){
			mPager=(ViewPager)findViewById(R.id.threeplayer_pager);
		}
		return mPager;
		
	}
	

/*	public void addButtonListener() {
		//super.addButtonListener();

		TextView btnNextHand = (TextView) findViewById(R.id.txt_next_hand);
		btnNextHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Store the type of hand(player1)

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
				if (!flop_displayed) {
					
					buttonView.setChecked(isChecked);
					if (isChecked){
	//					setCurrent_player_predictions("1");
					}
				}

			}
		});
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.board3_radio_player2);
		radioPlayer2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				if (!flop_displayed) {
					
					buttonView.setChecked(isChecked);
					if (isChecked){
					//	setCurrent_player_predictions("2");
					}
				}
			}
		});

		RadioButton radioPlayer3 = (RadioButton) findViewById(R.id.board3_radio_player3);
		radioPlayer3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cleanRadioSelection();
				if (!flop_displayed) {
					
					buttonView.setChecked(isChecked);
					if (isChecked){}
						//setCurrent_player_predictions("3");

				}
			}
		});

		ImageView btn_statistics = (ImageView) findViewById(R.id.btn_statistics);
		btn_statistics.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// start statistics activity
				Intent statisticsIntent = new Intent();
				statisticsIntent.putExtra("ParentClassName",
						"ThreePlayersActivity");
				startActivity(statisticsIntent.setClass(
						ThreePlayersActivity.this,
						StatisticResultActivity.class));

			}
		});

		ImageView btn_players = (ImageView) findViewById(R.id.btn_players);
		btn_players.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// start statistics activity
				startActivity(new Intent(ThreePlayersActivity.this,
						NumberPlayerActivity.class));

			}
		});

	}*/

	// when just one player has winner hand
	// Return 0 if two or more players have the same hand

	/*private void cleanRadioSelection() {
		RadioButton radioPlayer1 = (RadioButton) findViewById(R.id.board3_radio_player1);
		RadioButton radioPlayer2 = (RadioButton) findViewById(R.id.board3_radio_player2);
		RadioButton radioPlayer3 = (RadioButton) findViewById(R.id.board3_radio_player3);
		radioPlayer1.setChecked(false);
		radioPlayer2.setChecked(false);
		radioPlayer3.setChecked(false);

	}*/

	/*@Override
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

	}*/

/*	public void resulting() throws Exception {

//		// ((Button) v).setCompoundDrawablesWithIntrinsicBounds(
//		// R.drawable.winner, 0, 0, 0);
//		// ((Button) v).setPadding(60, 0, 20, 0);
//		int[] vPlayerStrings={R.string.player1,R.string.player2,R.string.player3,R.string.player4};
//		// // RESOLVE THE HAND
//		ResultHand resultplayer1 = hand_resulter.getResult("1");
//		ResultHand resultplayer2 = hand_resulter.getResult("2");
//		ResultHand resultplayer3 = hand_resulter.getResult("3");
//
//		int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
//		int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
//		int totalScore3 = Integer.valueOf(resultplayer3.getTypeHand());
//
//		List<Integer> playerLayouts = new ArrayList<Integer>();
//		playerLayouts.add(R.id.inner_player_panel1);
//		playerLayouts.add(R.id.inner_player_panel2);
//		playerLayouts.add(R.id.inner_player_panel3);
//
//		List<Integer> lTotalScore;
//		List<ResultHand> lResultHand;
//		List<Integer> winner_player_num;
//
//		boolean player1_won;
//		String strWhoWon = "";
//		// Check if player1 has won, to store the result in the
//		// hand_recorder
//		//
//		// // save the totalScore in a vector to check
//		lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2,
//				totalScore3);
//		lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2,
//				resultplayer3);
//		// Once u know the highest hand, check who it belongs to
//		int winner = getAndHighLightWinner(playerLayouts, lTotalScore,
//				lResultHand, HandResulter.FIRST_STAGE);
//		highlighWinner(playerLayouts.get(winner), vPlayerStrings[winner],
//				lResultHand.get(winner));
//		resultGuess(String.valueOf(winner));
//		if (winner == -1) {
//			throw new Exception("Error calculating the winner:winner is -1");
//		}
//
//		// // Save save player1's hand for the statistic
//		switch (winner) {
//		case 0:
//			hand_recorder.saveResult(resultplayer1.getTypeHand());
//			break;
//		case 1:
//			hand_recorder.saveResult(resultplayer2.getTypeHand());
//			break;
//		case 2:
//			hand_recorder.saveResult(resultplayer3.getTypeHand());
//			break;
//		default:
//			throw new Exception("Error Unknown player");
//
//		}
//		List<Card> handPlayer1 = hand_resulter.getCards_player1();
//		hand_recorder.addHand(handPlayer1);
//		try {
//			writeHandRecorderToFile(hand_recorder);
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		}
//		//

	}*/

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		
	}

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

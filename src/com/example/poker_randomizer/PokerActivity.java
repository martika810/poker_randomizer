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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class PokerActivity extends FragmentActivity implements
		FragmentManager.OnBackStackChangedListener {

	
	boolean mShowingBack1 = false;
	boolean mShowingBack2 = false;
	boolean mShowingBack3 = false;
	boolean mShowingBack4 = false;
	boolean mShowingBack5 = false;
	boolean flop_displayed = false;
	boolean turn_displayed = false;
	boolean river_displayed = false;
	
	
	List<String> cards = new ArrayList<String>();
	Random r = new Random();
	static String ACTION_NEW_HAND = "android.intent.action.MAIN2";
	private static final String STATISTICACTIVITYNAME = "StatisticResultActivity";
	static String data_file;
	HandRecorder hand_recorder;
	HandResulter hand_resulter;
	Gson gson = new GsonBuilder().create();

	private String current_player_predictions;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		


	}

	

//	public void initTopFiveCards(Bundle savedInstanceState){
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.image_card1, new CardBackFragment()).commit();
//			getFragmentManager().beginTransaction()
//					.add(R.id.image_card2, new CardBackFragment()).commit();
//			getFragmentManager().beginTransaction()
//					.add(R.id.image_card3, new CardBackFragment()).commit();
//			getFragmentManager().beginTransaction()
//					.add(R.id.image_card4, new CardBackFragment()).commit();
//			getFragmentManager().beginTransaction()
//					.add(R.id.image_card5, new CardBackFragment()).commit();
//		} else {
//			mShowingBack1 = (getFragmentManager().getBackStackEntryCount() > 0);
//			mShowingBack2 = (getFragmentManager().getBackStackEntryCount() > 0);
//			mShowingBack3 = (getFragmentManager().getBackStackEntryCount() > 0);
//			mShowingBack4 = (getFragmentManager().getBackStackEntryCount() > 0);
//			mShowingBack5 = (getFragmentManager().getBackStackEntryCount() > 0);
//		}
//		getFragmentManager().addOnBackStackChangedListener(this);
//		
//		
//	}
//	public void addButtonListener() {
//		FrameLayout first_card = (FrameLayout) findViewById(R.id.image_card1);
//		first_card.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					if (!flop_displayed) {
//						// int[] idCardLayouts = { R.id.image_card1,
//						// R.id.image_card2, R.id.image_card3 };
//						flipCard1(pickCard());
//						flipCard2(pickCard());
//						flipCard3(pickCard());
//						flop_displayed = true;
//					}
//					// remove the Tap favorite instructions
//					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
//					tap_favorite.setVisibility(View.GONE);
//				}
//				return true;
//
//			}
//
//		});
//		FrameLayout second_card = (FrameLayout) findViewById(R.id.image_card2);
//		second_card.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					if (!flop_displayed) {
//						int[] idCardLayouts = { R.id.image_card1,
//								R.id.image_card2, R.id.image_card3 };
//						flipCard1(pickCard());
//						flipCard2(pickCard());
//						flipCard3(pickCard());
//						flop_displayed = true;
//					}
//					// remove the Tap favorite instructions
//					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
//					tap_favorite.setVisibility(View.GONE);
//				}
//				return true;
//
//			}
//
//		});
//		FrameLayout third_card = (FrameLayout) findViewById(R.id.image_card3);
//		third_card.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					if (!flop_displayed) {
//						// int[] idCardLayouts = { R.id.image_card1,
//						// R.id.image_card2, R.id.image_card3 };
//
//						flipCard1(pickCard());
//						flipCard2(pickCard());
//						flipCard3(pickCard());
//						flop_displayed = true;
//						mShowingBack4 = false;
//						mShowingBack5 = false;
//					}
//					// remove the Tap favorite instructions
//					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
//					tap_favorite.setVisibility(View.GONE);
//				}
//				return true;
//
//			}
//
//		});
//
//		FrameLayout fourth_card = (FrameLayout) findViewById(R.id.image_card4);
//		fourth_card.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					if (!turn_displayed && flop_displayed) {
//						// int[] idCardLayouts = { R.id.image_card4 };
//						flipCard4(pickCard());
//						turn_displayed = true;
//					}
//				}
//				return true;
//
//			}
//
//		});
//
//		FrameLayout fifth_card = (FrameLayout) findViewById(R.id.image_card5);
//		fifth_card.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					if (!river_displayed && flop_displayed && turn_displayed) {
//						// int[] idCardLayouts = { R.id.image_card5 };
//						// Turn last card and result
//						flipCard5(pickCard());
//						river_displayed = true;
//
//						highlighWinnerPanel("");
//						try {
//							resulting();
//						} catch (Exception e) {
//							// TODO Add something to handle the exception
//							// properly
//							Toast.makeText(getApplicationContext(),
//									e.getMessage(), Toast.LENGTH_SHORT).show();
//
//						}
//					}
//				}
//				return true;
//
//			}
//
//		});
//
//		
//		
//	}

	protected void highlighWinnerPanel(String whoWon) {
		TextView winnerBtn = (TextView) findViewById(R.id.txt_winner);
		winnerBtn.setEnabled(false);
		

	}

//	public int pickCard() {
//		Random r = new Random();
//
//		int position_card_pick = r.nextInt(cards.size());
//		String card_picked = cards.get(position_card_pick);
//		hand_resulter.addCardBoard(card_picked);
//		int identifier = getResources().getIdentifier(card_picked, "drawable",
//				"com.example.poker_randomizer");
//		cards.remove(position_card_pick);
//
//		return identifier;
//
//		// }
//
//	}

	// parameter: the drawable id of the card that will be displayed
//	public void flipCard1(int idCardDrawable) {
//		if (mShowingBack1) {
//			getFragmentManager().popBackStack();
//			return;
//
//		}
//		mShowingBack1 = true;
//
//		getFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.animator.card_flip_right_in,
//						R.animator.card_flip_right_out,
//						R.animator.card_flip_left_in,
//						R.animator.card_flip_left_out)
//
//				.replace(R.id.image_card1,
//						new CardFrontFragment(idCardDrawable))
//				.addToBackStack(null).commit();
//
//	}
//
//	public void flipCard2(int idCardDrawable) {
//		if (mShowingBack2) {
//			getFragmentManager().popBackStack();
//			return;
//
//		}
//		mShowingBack2 = true;
//
//		getFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.animator.card_flip_right_in,
//						R.animator.card_flip_right_out,
//						R.animator.card_flip_left_in,
//						R.animator.card_flip_left_out)
//
//				.replace(R.id.image_card2,
//						new CardFrontFragment(idCardDrawable))
//				.addToBackStack(null).commit();
//
//	}
//
//	public void flipCard3(int idCardDrawable) {
//		if (mShowingBack3) {
//			getFragmentManager().popBackStack();
//			return;
//
//		}
//		mShowingBack3 = true;
//
//		getFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.animator.card_flip_right_in,
//						R.animator.card_flip_right_out,
//						R.animator.card_flip_left_in,
//						R.animator.card_flip_left_out)
//
//				.replace(R.id.image_card3,
//						new CardFrontFragment(idCardDrawable))
//				.addToBackStack(null).commit();
//
//	}
//
//	public void flipCard4(int idCardDrawable) {
//		if (mShowingBack4) {
//			getFragmentManager().popBackStack();
//			return;
//
//		}
//		mShowingBack4 = true;
//
//		getFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.animator.card_flip_right_in,
//						R.animator.card_flip_right_out,
//						R.animator.card_flip_left_in,
//						R.animator.card_flip_left_out)
//
//				.replace(R.id.image_card4,
//						new CardFrontFragment(idCardDrawable))
//				.addToBackStack(null).commit();
//
//	}
//
//	public void flipCard5(int idCardDrawable) {
//		if (mShowingBack5) {
//			getFragmentManager().popBackStack();
//			return;
//
//		}
//		mShowingBack5 = true;
//
//		getFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.animator.card_flip_right_in,
//						R.animator.card_flip_right_out,
//						R.animator.card_flip_left_in,
//						R.animator.card_flip_left_out)
//
//				.replace(R.id.image_card5,
//						new CardFrontFragment(idCardDrawable))
//				.addToBackStack(null).commit();
//
//	}

//	@Override
//	public void onBackStackChanged() {
//		invalidateOptionsMenu();
//	}





	// Returns the number of the hand winner
	public int getAndHighLightWinner(List<Integer> playerLayoutsIds,
			List<Integer> totalScores, List<ResultHand> resultHands,
			int resulting_stage) {
		
		//
		int[] vPlayerStrings={R.string.player1,R.string.player2,R.string.player3,R.string.player4};
		// Return the player number of the player who won the hand
		int maxTotalScore = Util.getMax(totalScores);
		int currentPos = 0;
		String strWhoWon = "";
		int winner = -1;
		int pos;
		List<Integer> lwinners = new ArrayList<Integer>();
		// int times = Collections.frequency(totalScores, maxTotalScore);
		// if (times>1) return 0;
		// Once u know the highest hand, check who it belongs to
		for (int score : totalScores) {
			if (maxTotalScore == score) {
				lwinners.add(currentPos);
			}
			currentPos++;
		}
		if (lwinners.size() == 1) {
			winner = lwinners.get(0);
			
			
			if (winner == 0) {
				hand_recorder.setPlayer1_won(true);
			} else
				hand_recorder.setPlayer1_won(false);
			
			return winner;
		} else {
			switch (resulting_stage) {
			case HandResulter.FIRST_STAGE:
				// Recreate the totalScore for the winners, as there is several
				// player with the same hand,
				// the value of the hand has to count as well
				resultHands = Util.getSubVectorResultFromPositions(lwinners,
						resultHands);
				totalScores = new ArrayList<Integer>();
				for (ResultHand result : resultHands) {
					totalScores.add(Integer.valueOf(result.getTypeHand())
							+ result.getSumValues());
				}
				playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
						playerLayoutsIds);
				winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
						resultHands, HandResulter.SECOND_STAGE);
				
				break;
			case HandResulter.SECOND_STAGE:
				resultHands = Util.getSubVectorResultFromPositions(lwinners,
						resultHands);
				totalScores = new ArrayList<Integer>();
				pos = 1;
				for (ResultHand result : resultHands) {
					totalScores.add(Card.valuesCards.get(hand_resulter
							.maxCard_from_playerHand(String.valueOf(pos))
							.getCardNumber()));
					pos++;
				}
				playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
						playerLayoutsIds);
				winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
						resultHands, HandResulter.THIRD_STAGE);

				break;
			case HandResulter.THIRD_STAGE:
				resultHands = Util.getSubVectorResultFromPositions(lwinners,
						resultHands);
				totalScores = new ArrayList<Integer>();
				pos = 1;
				for (ResultHand result : resultHands) {
					totalScores.add(result.getSumAllValues());
					pos++;
				}
				playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
						playerLayoutsIds);
				winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
						resultHands, HandResulter.FOURTH_STAGE);
				
				break;
			case HandResulter.FOURTH_STAGE:
				break;
			}
		}
		
		return winner;

	}

	protected void highlighWinner(int playerLayoutId, int strPlayerId,
			ResultHand result) {
		String strWhoWon;
		LinearLayout panelPlayer = (LinearLayout) findViewById(playerLayoutId);
		panelPlayer.setBackgroundResource(R.drawable.button_winner);

		TextView txt_winner = (TextView) findViewById(R.id.txt_winner);
		strWhoWon = getString(strPlayerId) + " won with "
				+ result.getNameResult();
		txt_winner.setText(strWhoWon);
		txt_winner.setVisibility(View.VISIBLE);
	}

	public HandRecorder readHandRecorderFromFile() throws IOException {

		FileInputStream fis = openFileInput(data_file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String strJson = br.readLine();

		HandRecorder handRecorder = loadJsonToHandRecorder(strJson);

		br.close();
		return handRecorder;
	}

	private HandRecorder loadJsonToHandRecorder(String strJson) {

		HandRecorder handRecorder = gson.fromJson(strJson, HandRecorder.class);
		return handRecorder;

	}

	public abstract void resulting() throws Exception;

	public void writeHandRecorderToFile(HandRecorder handRecorder)
			throws FileNotFoundException {
		File file = new File(data_file);
		FileOutputStream fos = openFileOutput(data_file, MODE_PRIVATE);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(fos)));
		String strDataHandRecorder = gson.toJson(handRecorder);
		pw.print(strDataHandRecorder);
		pw.close();

	}

	public void resultGuess(String winner_player) {
		//subtract one to the winner so it s based on 0 instead of 1
		String winner=String.valueOf((Integer.parseInt(winner_player))+1);
		if (isValidGuess(winner)) {
			int current_number_guesses = hand_recorder.getNumber_guesses();
			int current_number_right_guesses = hand_recorder
					.getNumber_rigth_guesses();
			if (winner.equals(current_player_predictions)) {
				hand_recorder
						.setNumber_rigth_guesses(++current_number_right_guesses);
			}
			hand_recorder.setNumber_guesses(++current_number_guesses);
		}
	}

	private boolean isValidGuess(String winner_player_guess) {
		try {
			if (!current_player_predictions.equals("")
					&& (Integer.parseInt(current_player_predictions) > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "The winner player guess string was invalid", Toast.LENGTH_LONG);
			return false;
		}
	}

	public String getCurrent_player_predictions() {
		return current_player_predictions;
	}

	public void setCurrent_player_predictions(String current_player_predictions) {
		this.current_player_predictions = current_player_predictions;
	}
	
	public boolean isThereAGuess(){
		if (Integer.valueOf(current_player_predictions)>0){
			return true;
		}
		return false;
	}

	public HandResulter getHand_resulter() {
		return hand_resulter;
	}

	public void setHand_resulter(HandResulter hand_resulter) {
		this.hand_resulter = hand_resulter;
	}

	public HandRecorder getHand_recorder() {
		return hand_recorder;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}

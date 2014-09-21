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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class PokerActivity extends ActionBarActivity implements
		FragmentManager.OnBackStackChangedListener {

	String[] typeCard = { "s", "h", "d", "c" };
	private static final String[] typeNumbers = { "2", "3", "4", "5", "6", "7",
			"8", "9", "x", "j", "q", "k", "a" };
	List<String> cards = new ArrayList<String>();
	Random r = new Random();
	static String ACTION_NEW_HAND = "android.intent.action.MAIN2";
	private static final String STATISTICACTIVITYNAME = "StatisticResultActivity";
	static String data_file;
	HandRecorder hand_recorder;
	HandResulter hand_resulter;
	Gson gson = new GsonBuilder().create();
	boolean mShowingBack1 = false;
	boolean mShowingBack2 = false;
	boolean mShowingBack3 = false;
	boolean mShowingBack4 = false;
	boolean mShowingBack5 = false;
	boolean flop_displayed = false;
	boolean turn_displayed = false;
	boolean river_displayed = false;
	private String current_player_predictions;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.image_card1, new CardBackFragment()).commit();
			getFragmentManager().beginTransaction()
					.add(R.id.image_card2, new CardBackFragment()).commit();
			getFragmentManager().beginTransaction()
					.add(R.id.image_card3, new CardBackFragment()).commit();
			getFragmentManager().beginTransaction()
					.add(R.id.image_card4, new CardBackFragment()).commit();
			getFragmentManager().beginTransaction()
					.add(R.id.image_card5, new CardBackFragment()).commit();
		} else {
			mShowingBack1 = (getFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack2 = (getFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack3 = (getFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack4 = (getFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack5 = (getFragmentManager().getBackStackEntryCount() > 0);
		}
		getFragmentManager().addOnBackStackChangedListener(this);

		populateCards();
	}

	protected void populateCards() {
		for (String type : typeCard) {
			for (String num : typeNumbers) {
				cards.add(type + num);

			}
		}
	}

	public void addButtonListener() {

		FrameLayout first_card = (FrameLayout) findViewById(R.id.image_card1);
		first_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card1,
						// R.id.image_card2, R.id.image_card3 };
						flipCard1(pickCard());
						flipCard2(pickCard());
						flipCard3(pickCard());
						flop_displayed = true;
					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
				}
				return true;

			}

		});
		FrameLayout second_card = (FrameLayout) findViewById(R.id.image_card2);
		second_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						int[] idCardLayouts = { R.id.image_card1,
								R.id.image_card2, R.id.image_card3 };
						flipCard1(pickCard());
						flipCard2(pickCard());
						flipCard3(pickCard());
						flop_displayed = true;
					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
				}
				return true;

			}

		});
		FrameLayout third_card = (FrameLayout) findViewById(R.id.image_card3);
		third_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card1,
						// R.id.image_card2, R.id.image_card3 };

						flipCard1(pickCard());
						flipCard2(pickCard());
						flipCard3(pickCard());
						flop_displayed = true;
						mShowingBack4 = false;
						mShowingBack5 = false;
					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) findViewById(R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
				}
				return true;

			}

		});

		FrameLayout fourth_card = (FrameLayout) findViewById(R.id.image_card4);
		fourth_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!turn_displayed && flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card4 };
						flipCard4(pickCard());
						turn_displayed = true;
					}
				}
				return true;

			}

		});

		FrameLayout fifth_card = (FrameLayout) findViewById(R.id.image_card5);
		fifth_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!river_displayed && flop_displayed && turn_displayed) {
						// int[] idCardLayouts = { R.id.image_card5 };
						// Turn last card and result
						flipCard5(pickCard());
						river_displayed = true;

						highlighWinnerPanel("");
						try {
							resulting();
						} catch (Exception e) {
							// TODO Add something to handle the exception
							// properly
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();

						}
					}
				}
				return true;

			}

		});

	}

	protected void highlighWinnerPanel(String whoWon) {
		TextView winnerBtn = (TextView) findViewById(R.id.txt_winner);
		winnerBtn.setEnabled(false);
		//
		//
		// winnerBtn.setCompoundDrawablesWithIntrinsicBounds(
		// R.drawable.winner, 0, 0, 0);
		// winnerBtn.setPadding(60, 0, 20, 0);

	}

	public int pickCard() {
		Random r = new Random();

		int position_card_pick = r.nextInt(cards.size());
		String card_picked = cards.get(position_card_pick);
		hand_resulter.addCardBoard(card_picked);
		int identifier = getResources().getIdentifier(card_picked, "drawable",
				"com.example.poker_randomizer");
		cards.remove(position_card_pick);

		return identifier;

		// }

	}

	// parameter: the drawable id of the card that will be displayed
	public void flipCard1(int idCardDrawable) {
		if (mShowingBack1) {
			getFragmentManager().popBackStack();
			return;

		}
		mShowingBack1 = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				.replace(R.id.image_card1,
						new CardFrontFragment(idCardDrawable))
				.addToBackStack(null).commit();

	}

	public void flipCard2(int idCardDrawable) {
		if (mShowingBack2) {
			getFragmentManager().popBackStack();
			return;

		}
		mShowingBack2 = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				.replace(R.id.image_card2,
						new CardFrontFragment(idCardDrawable))
				.addToBackStack(null).commit();

	}

	public void flipCard3(int idCardDrawable) {
		if (mShowingBack3) {
			getFragmentManager().popBackStack();
			return;

		}
		mShowingBack3 = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				.replace(R.id.image_card3,
						new CardFrontFragment(idCardDrawable))
				.addToBackStack(null).commit();

	}

	public void flipCard4(int idCardDrawable) {
		if (mShowingBack4) {
			getFragmentManager().popBackStack();
			return;

		}
		mShowingBack4 = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				.replace(R.id.image_card4,
						new CardFrontFragment(idCardDrawable))
				.addToBackStack(null).commit();

	}

	public void flipCard5(int idCardDrawable) {
		if (mShowingBack5) {
			getFragmentManager().popBackStack();
			return;

		}
		mShowingBack5 = true;

		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				.replace(R.id.image_card5,
						new CardFrontFragment(idCardDrawable))
				.addToBackStack(null).commit();

	}

	@Override
	public void onBackStackChanged() {
		invalidateOptionsMenu();
	}

	public static class CardFrontFragment extends Fragment {
		private int idDrawable;

		public CardFrontFragment(int idCardDrawable) {
			this.idDrawable = idCardDrawable;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View img = inflater.inflate(R.layout.fragment_card_front,
					container, false);
			((ImageView) img).setImageResource(idDrawable);
			return img;
		}
	}

	public class CardBackFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_card_back, container,
					false);
		}
	}

	// Returns the number of the hand winner
	public int getAndHighLightWinner(List<Integer> playerLayoutsIds,
			List<Integer> totalScores, List<ResultHand> resultHands,
			int resulting_stage) {
		// Return the player number of the player who won the hand
		int maxTotalScore = Util.getMax(totalScores);
		int currentPos = 0;
		String strWhoWon = "";
		int winner = -1;
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
			highlighWinner(playerLayoutsIds.get(winner), R.string.player1,
					resultHands.get(winner));
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
				int pos = 1;
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

				break;
			case HandResulter.FOURTH_STAGE:
				break;
			}
		}
		return winner;

	}

	private void highlighWinner(int playerLayoutId, int strPlayerId,
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
		if (isValidGuess(winner_player)) {
			int current_number_guesses = hand_recorder.getNumber_guesses();
			int current_number_right_guesses = hand_recorder
					.getNumber_rigth_guesses();
			if (winner_player.equals(current_player_predictions)) {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}

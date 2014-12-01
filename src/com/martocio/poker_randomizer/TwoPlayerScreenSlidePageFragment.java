package com.martocio.poker_randomizer;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayerScreenSlidePageFragment extends Fragment implements
		OnBackStackChangedListener, AnimationListener, ResulterFragment {

	int mPageNumber;



	boolean mShowingBack1 = true;
	boolean mShowingBack2 = true;
	boolean mShowingBack3 = true;
	boolean mShowingBack4 = true;
	boolean mShowingBack5 = true;
	boolean flop_displayed = false;
	boolean turn_displayed = false;
	boolean river_displayed = false;
	private int pos;
	private Dealer dealer;
	HandResulter hand_resulter;

	private Animation animation1;
	private Animation animation2;
	private String current_player_predictions;

	public static final TwoPlayerScreenSlidePageFragment newInstance(int pos) {
		TwoPlayerScreenSlidePageFragment fragment = new TwoPlayerScreenSlidePageFragment(
				pos);

		return fragment;

	}

	public TwoPlayerScreenSlidePageFragment(int pos) {
		this.pos = pos;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHand_resulter(new HandResulter(getActivity().getBaseContext()));
		mPageNumber = 10;
	}

	@Override
	public void onStart() {

		super.onStart();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.activity_main2, container, false);

		return rootView;
	}

	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		try {
			final ViewPager pager = ((MainActivity) this.getActivity())
					.getmPager();

			dealer = Dealer.getInstance();
			dealer.dealAllCards(this, 2);

			initFlipOverAnimations();

			initLayout();

			addInitialListener();
			addButtonListener();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void initLayout() throws Exception {

		// ((MainActivity) getActivity()).getHand_resulter().refresh();

		HandRecorder hand_recorder = ((MainActivity) getActivity())
				.getHand_recorder();

		TextView number_handTxt = (TextView) getView().findViewById(
				R.id.txt_number_hands);

		TextView number_guessTxt = (TextView) getView().findViewById(
				R.id.number_guess_txt);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());
		number_guessTxt.setText("Guesses: "
				+ hand_recorder.getNumber_rigth_guesses() + "/"
				+ hand_recorder.getNumber_guesses());

		CardPairView cardPairPlayerOne = (CardPairView) getView().findViewById(
				R.id.pair_player1);

		dealer.placeCardsToPlayer(getResources(), hand_resulter,
				cardPairPlayerOne, Dealer.PLAYER_ONE);

		CardPairView cardPairPlayerTwo = (CardPairView) getView().findViewById(
				R.id.pair_player2);
		dealer.placeCardsToPlayer(getResources(), hand_resulter,
				cardPairPlayerTwo, Dealer.PLAYER_TWO);

	}

	// add Listener to the board cards to flip over
	public void addInitialListener() {
		ImageView first_card = (ImageView) getView().findViewById(
				R.id.image_card1);
		first_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card1,
						// R.id.image_card2, R.id.image_card3 };
						flipCard1();
						flipCard2();
						flipCard3();

					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) getView().findViewById(
							R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
					disablePlayerRadioButtons();
				}
				return true;

			}

		});
		ImageView second_card = (ImageView) getView().findViewById(
				R.id.image_card2);
		second_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						int[] idCardLayouts = { R.id.image_card1,
								R.id.image_card2, R.id.image_card3 };
						flipCard1();
						flipCard2();
						flipCard3();

					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) getView().findViewById(
							R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
				}
				return true;

			}

		});
		ImageView third_card = (ImageView) getView().findViewById(
				R.id.image_card3);
		third_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card1,
						// R.id.image_card2, R.id.image_card3 };

						flipCard1();
						flipCard2();

						flipCard3();

					}
					// remove the Tap favorite instructions
					TextView tap_favorite = (TextView) getView().findViewById(
							R.id.txt_tap_favorite);
					tap_favorite.setVisibility(View.GONE);
				}
				return true;

			}

		});

		ImageView fourth_card = (ImageView) getView().findViewById(
				R.id.image_card4);
		fourth_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!turn_displayed && flop_displayed) {
						// int[] idCardLayouts = { R.id.image_card4 };

						flipCard4();

					}
				}
				return true;

			}

		});

		ImageView fifth_card = (ImageView) getView().findViewById(
				R.id.image_card5);
		fifth_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					if (!river_displayed && flop_displayed && turn_displayed) {
						// int[] idCardLayouts = { R.id.image_card5 };
						// Turn last card and result
						List<Integer> playerLayouts = new ArrayList<Integer>();

						playerLayouts.add(R.id.inner_player_panel1);
						playerLayouts.add(R.id.inner_player_panel2);

						int[] vPlayerStrings = { R.string.player1,
								R.string.player2, R.string.player3,
								R.string.player4 };

						flipCard5();

						((MainActivity) getActivity()).highlighWinnerPanel("");

						try {
							ResultHand winnerResult = hand_resulter
									.resultingTwoPlayers();

							highlighWinner(
									playerLayouts.get(winnerResult
											.getNum_player() - 1),
									vPlayerStrings[winnerResult.getNum_player() - 1],
									winnerResult);

							resultGuess(String.valueOf(winnerResult
									.getNum_player()));
							
							((MainActivity) getActivity()).getHand_recorder()
									.saveWinnerHand(winnerResult);
							List<Card> handPlayer1 = hand_resulter
									.getCards_player1();
							((MainActivity) getActivity()).getHand_recorder()
									.addHand(handPlayer1);
							((MainActivity) getActivity()).getHand_recorder()
									.saveToFile(getActivity(),
											HandRecorder.DATA_FILE_TWO_PLAYERS);

						} catch (Exception e) {

							e.printStackTrace();

						}
					}
				}
				return true;

			}

		});

	}

	// add listener to next hand button and radio buttons, and the statistic and
	// player button
	public void addButtonListener() {

		TextView txtNextHand = (TextView) getView().findViewById(
				R.id.txt_next_hand);
		txtNextHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewPager parentPager = ((MainActivity) getActivity())
						.getmPager();

				resetTopFiveCards();
				parentPager.setCurrentItem(parentPager.getCurrentItem() + 1);

			}
		});
		RadioButton radioPlayer1 = (RadioButton) getView().findViewById(
				R.id.radio_player1);
		radioPlayer1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!flop_displayed) {
					RadioButton radioPlayer2 = (RadioButton) getView()
							.findViewById(R.id.radio_player2);
					RadioButton radioPlayer1 = (RadioButton) getView()
							.findViewById(R.id.radio_player1);
					if (isChecked) {
						radioPlayer2.setChecked(false);
					} else {
						radioPlayer2.setChecked(true);

					}
					if (radioPlayer1.isChecked()) {
						setCurrent_player_predictions("1");
					}
				}

			}
		});
		RadioButton radioPlayer2 = (RadioButton) getView().findViewById(
				R.id.radio_player2);
		radioPlayer2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!flop_displayed) {
					RadioButton radioPlayer1 = (RadioButton) getView()
							.findViewById(R.id.radio_player1);
					RadioButton radioPlayer2 = (RadioButton) getView()
							.findViewById(R.id.radio_player2);
					if (isChecked) {

						radioPlayer1.setChecked(false);

					} else {
						radioPlayer1.setChecked(true);

					}
					if (radioPlayer2.isChecked()) {
						setCurrent_player_predictions("2");
					}

				}
			}
		});

		ImageView btn_statistics = (ImageView) getView().findViewById(
				R.id.btn_statistics);
		btn_statistics.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// start statistics activity
				launchStatisticActivity();

			}
		});

		ImageView btn_players = (ImageView) getView().findViewById(
				R.id.btn_players);
		btn_players.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// start statistics activity
				startActivity(new Intent(getActivity(),
						NumberPlayerActivity.class));

			}
		});

	}

	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void onBackStackChanged() {
		hasOptionsMenu();
	}
	
	

	private void resetTopFiveCards() {

		mShowingBack1 = false;
		mShowingBack2 = false;
		mShowingBack3 = false;
		mShowingBack4 = false;
		mShowingBack5 = false;

	}

	public void flipCard1() {
		if (mShowingBack1) {
			((ImageView) getView().findViewById(R.id.image_card1))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card1))
					.setAnimation(animation1);
			((ImageView) getView().findViewById(R.id.image_card1))
					.startAnimation(animation1);

		}
		mShowingBack1 = false;

	}

	public void flipCard2() {
		if (mShowingBack2) {
			((ImageView) getView().findViewById(R.id.image_card2))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card2))
					.setAnimation(animation1);
			((ImageView) getView().findViewById(R.id.image_card2))
					.startAnimation(animation1);

		}
		mShowingBack2 = false;
	}

	public void flipCard3() {
		if (mShowingBack3) {
			((ImageView) getView().findViewById(R.id.image_card3))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card3))
					.setAnimation(animation1);
			((ImageView) getView().findViewById(R.id.image_card3))
					.startAnimation(animation1);

		}
		mShowingBack3 = false;

	}

	public void flipCard4() {
		if (mShowingBack4) {
			((ImageView) getView().findViewById(R.id.image_card4))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card4))
					.setAnimation(animation1);
			((ImageView) getView().findViewById(R.id.image_card4))
					.startAnimation(animation1);

		}
		mShowingBack4 = false;

	}

	public void flipCard5() {
		if (mShowingBack5) {
			((ImageView) getView().findViewById(R.id.image_card5))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card5))
					.setAnimation(animation1);
			((ImageView) getView().findViewById(R.id.image_card5))
					.startAnimation(animation1);

		}
		mShowingBack5 = false;

	}

	private void launchStatisticActivity() {
		Intent statisticsIntent = new Intent();
		statisticsIntent.putExtra("ParentClassName", "MainActivity");
		startActivity(statisticsIntent.setClass(getActivity()
				.getApplicationContext(), StatisticResultActivity.class));

	}

	private void initFlipOverAnimations() {
		animation1 = AnimationUtils.loadAnimation(getActivity()
				.getBaseContext(), R.anim.to_middle);
		animation1.setAnimationListener(this);

		animation2 = AnimationUtils.loadAnimation(getActivity()
				.getBaseContext(), R.anim.from_middle);
		animation2.setAnimationListener(this);
	}

	@Override
	public void onResume() {

		super.onResume();
		
	
	

	}
	


	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	private void updateCounters(){
		HandRecorder hand_recorder = ((PokerActivity)getActivity())
				.getHand_recorder();
		TextView number_handTxt = (TextView)getView().findViewById(
				R.id.txt_number_hands);

		TextView number_guessTxt = (TextView)getView().findViewById(
				R.id.number_guess_txt);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());
		number_guessTxt.setText("Guesses: "
				+ hand_recorder.getNumber_rigth_guesses() + "/"
				+ hand_recorder.getNumber_guesses());
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser && getView()!=null){
			updateCounters();
					}
	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (!flop_displayed) {
			ImageView boardCardOne = (ImageView) getView().findViewById(
					R.id.image_card1);
			dealer.placeBoardCard(getResources(), hand_resulter, boardCardOne,
					1);
			ImageView boardCardTwo = (ImageView) getView().findViewById(
					R.id.image_card2);
			dealer.placeBoardCard(getResources(), hand_resulter, boardCardTwo,
					2);
			ImageView boardCardThree = (ImageView) getView().findViewById(
					R.id.image_card3);
			dealer.placeBoardCard(getResources(), hand_resulter,
					boardCardThree, 3);

			flop_displayed = true;
			((ImageView) getView().findViewById(R.id.image_card1))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card2))
					.clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card3))
					.clearAnimation();
		} else if (!turn_displayed) {
			ImageView boardCardFour = (ImageView) getView().findViewById(
					R.id.image_card4);
			dealer.placeBoardCard(getResources(), hand_resulter, boardCardFour,
					4);
			turn_displayed = true;
			((ImageView) getView().findViewById(R.id.image_card4))
					.clearAnimation();
		} else if (!river_displayed) {
			ImageView boardCardFive = (ImageView) getView().findViewById(
					R.id.image_card5);
			dealer.placeBoardCard(getResources(), hand_resulter, boardCardFive,
					5);
			river_displayed = true;
			((ImageView) getView().findViewById(R.id.image_card5))
					.clearAnimation();
		}

	}
	
	

	protected void highlighWinner(int playerLayoutId, int strPlayerId,
			ResultHand result) {
		String strWhoWon;
		LinearLayout panelPlayer = (LinearLayout) getView().findViewById(
				playerLayoutId);
		panelPlayer.setBackgroundResource(R.drawable.button_winner);

		TextView txt_winner = (TextView) getView()
				.findViewById(R.id.txt_winner);
		strWhoWon = getString(strPlayerId) + " won with "
				+ result.getNameResult() ;//+ result.handValueToString();
		;
		txt_winner.setText(strWhoWon);
		txt_winner.setVisibility(View.VISIBLE);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	public HandResulter getHand_resulter() {
		return hand_resulter;
	}

	public void setHand_resulter(HandResulter hand_resulter) {
		this.hand_resulter = hand_resulter;
	}

	public void disablePlayerRadioButtons() {
		((RadioButton) getView().findViewById(R.id.radio_player1))
				.setEnabled(false);
		((RadioButton) getView().findViewById(R.id.radio_player2))
				.setEnabled(false);

	}

	public void resultGuess(String winner_player) {
		// subtract one to the winner so it s based on 0 instead of 1
		HandRecorder handRecorder = ((PokerActivity) getActivity())
				.getHand_recorder();
		String winner = String.valueOf((Integer.parseInt(winner_player)));
		if (isValidGuess(winner)) {
			int current_number_guesses = handRecorder.getNumber_guesses();
			int current_number_right_guesses = handRecorder
					.getNumber_rigth_guesses();
			if (winner.equals(current_player_predictions)) {
				current_number_right_guesses++;
				handRecorder
						.setNumber_rigth_guesses(current_number_right_guesses);
			}
			current_number_guesses++;
			handRecorder.setNumber_guesses(current_number_guesses);
		}
	}

	private boolean isValidGuess(String winner_player_guess) {
		try {
			if (current_player_predictions != null
					&& !current_player_predictions.equals("")
					&& (Integer.parseInt(current_player_predictions) > 0)) {
				return true;
			}

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			Toast.makeText(getActivity().getApplicationContext(),
					"The winner player guess string was invalid",
					Toast.LENGTH_LONG);
			// return false;
		}
		return false;
	}

	public String getCurrent_player_predictions() {
		return current_player_predictions;
	}

	public void setCurrent_player_predictions(String current_player_predictions) {
		this.current_player_predictions = current_player_predictions;
	}

	public boolean isThereAGuess() {
		if (Integer.valueOf(current_player_predictions) > 0) {
			return true;
		}
		return false;
	}
	
	

}

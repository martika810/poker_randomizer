package com.example.poker_randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TwoPlayerScreenSlidePageFragment extends Fragment implements
		OnBackStackChangedListener, AnimationListener {

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

	List<String> cards = new ArrayList<String>();
	private Animation animation1;
	private Animation animation2;

	String[] typeCard = { "s", "h", "d", "c" };
	private static final String[] typeNumbers = { "2", "3", "4", "5", "6", "7",
			"8", "9", "x", "j", "q", "k", "a" };

	public static final TwoPlayerScreenSlidePageFragment newInstance(int pos) {
		TwoPlayerScreenSlidePageFragment f = new TwoPlayerScreenSlidePageFragment(
				pos);
		
		return f;

	}

	public TwoPlayerScreenSlidePageFragment(int pos) {
		this.pos = pos;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		final ViewPager pager = ((MainActivity) this.getActivity()).getmPager();
		//initTopFiveCards(savedInstanceState);
		((MainActivity) this.getActivity()).getHand_resulter().refresh();
		initFlipOverAnimations();
		initLayout();
		addInitialListener();
		addButtonListener();

		

	}

	public void initLayout() {

		HandResulter hand_resulter = ((MainActivity) getActivity())
				.getHand_resulter();
		HandRecorder hand_recorder = ((MainActivity) getActivity())
				.getHand_recorder();
		Random r = new Random();
		String card_picked;
		populateCards();
		TextView number_handTxt = (TextView) getView().findViewById(
				R.id.number_hands_txt);

		TextView number_guessTxt = (TextView) getView().findViewById(
				R.id.number_guess_txt);
		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());
		number_guessTxt.setText("Guesses: "
				+ hand_recorder.getNumber_rigth_guesses() + "/"
				+ hand_recorder.getNumber_guesses());

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
		CardPairView cardpair = (CardPairView) getView().findViewById(
				R.id.pair_player1);
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
		CardPairView cardpairPlayer2 = (CardPairView) getView().findViewById(
				R.id.pair_player2);
		cardpairPlayer2.init(card_picked, card_picked2);
		cardpairPlayer2.invalidate();

	}

	public void addInitialListener() {
		ImageView first_card = (ImageView) getView().findViewById(
				R.id.image_card1);
		first_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HandResulter hand_resulter = ((MainActivity) getActivity())
						.getHand_resulter();
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
		ImageView second_card = (ImageView) getView().findViewById(
				R.id.image_card2);
		second_card.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HandResulter hand_resulter = ((MainActivity) getActivity())
						.getHand_resulter();
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
				
						flipCard5();
						

						((MainActivity) getActivity()).highlighWinnerPanel("");
						try {
							((MainActivity) getActivity()).resulting();
						} catch (Exception e) {
							
							Toast.makeText(
									getActivity().getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();

						}
					}
				}
				return true;

			}

		});

	}

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
					if (isChecked) {
						radioPlayer2.setChecked(false);
					} else {
						radioPlayer2.setChecked(true);
						((MainActivity) getActivity())
								.setCurrent_player_predictions("2");
				
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
					if (isChecked) {

						radioPlayer1.setChecked(false);

					} else {
						radioPlayer1.setChecked(true);
						((MainActivity) getActivity())
								.setCurrent_player_predictions("1");
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

	public void initTopFiveCards(Bundle savedInstanceState) {

		if (savedInstanceState == null) {

			CardBackFragment firstTopCard = new CardBackFragment();
			CardBackFragment secondTopCard = new CardBackFragment();
			CardBackFragment thirdTopCard = new CardBackFragment();
			CardBackFragment fourthTopCard = new CardBackFragment();
			CardBackFragment fifthTopCard = new CardBackFragment();

			// Grab the ids
			// int
			// getActivity().getFragmentManager().beginTransaction()
			getChildFragmentManager().beginTransaction()
					.add(R.id.image_card1, firstTopCard).addToBackStack(null)
					.commit();
			getChildFragmentManager().beginTransaction()
					.add(R.id.image_card2, secondTopCard).addToBackStack(null)
					.commit();
			getChildFragmentManager().beginTransaction()
					.add(R.id.image_card3, thirdTopCard).addToBackStack(null)
					.commit();
			getChildFragmentManager().beginTransaction()
					.add(R.id.image_card4, fourthTopCard).addToBackStack(null)
					.commit();
			getChildFragmentManager().beginTransaction()
					.add(R.id.image_card5, fifthTopCard).addToBackStack(null)
					.commit();
		} else {
			mShowingBack1 = (getChildFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack2 = (getChildFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack3 = (getChildFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack4 = (getChildFragmentManager().getBackStackEntryCount() > 0);
			mShowingBack5 = (getChildFragmentManager().getBackStackEntryCount() > 0);

		}
		getChildFragmentManager().addOnBackStackChangedListener(this);

	}

	private void resetTopFiveCards() {

		mShowingBack1 = false;
		mShowingBack2 = false;
		mShowingBack3 = false;
		mShowingBack4 = false;
		mShowingBack5 = false;
		// Fragment
		// firstTopCard=getFragmentManager().findFragmentById(R.id.image_card1);
		// Fragment
		// secondTopCard=getFragmentManager().findFragmentById(R.id.image_card2);
		// Fragment
		// thirdTopCard=getFragmentManager().findFragmentById(R.id.image_card3);
		// Fragment
		// fourthTopCard=getFragmentManager().findFragmentById(R.id.image_card4);
		// Fragment
		// fifthTopCard=getFragmentManager().findFragmentById(R.id.image_card5);
		// getFragmentManager().beginTransaction()
		// .remove(firstTopCard).commit();
		// getFragmentManager().beginTransaction()
		// .remove(secondTopCard).commit();
		// getFragmentManager().beginTransaction()
		// .remove(thirdTopCard).commit();
		// getFragmentManager().beginTransaction()
		// .remove(fourthTopCard).commit();
		// getFragmentManager().beginTransaction()
		// .remove(fifthTopCard).commit();
	}

	protected void populateCards() {
		for (String type : typeCard) {
			for (String num : typeNumbers) {
				cards.add(type + num);

			}
		}
	}

	public int pickCard(HandResulter hand_resulter) {
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
	
	private void launchStatisticActivity(){
		Intent statisticsIntent = new Intent();
		statisticsIntent.putExtra("ParentClassName", "MainActivity");
		startActivity(statisticsIntent
				.setClass(getActivity().getApplicationContext(),
						StatisticResultActivity.class));

		
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

	@Override
	public void onAnimationEnd(Animation animation) {
		HandResulter hand_resulter = ((MainActivity) getActivity()).getHand_resulter();
		if(!flop_displayed){
			((ImageView) getView().findViewById(R.id.image_card1)).setImageResource(pickCard(hand_resulter));
			((ImageView) getView().findViewById(R.id.image_card2)).setImageResource(pickCard(hand_resulter));
			((ImageView) getView().findViewById(R.id.image_card3)).setImageResource(pickCard(hand_resulter));
			flop_displayed=true;
			((ImageView) getView().findViewById(R.id.image_card1)).clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card2)).clearAnimation();
			((ImageView) getView().findViewById(R.id.image_card3)).clearAnimation();
		}else if(!turn_displayed){
			((ImageView) getView().findViewById(R.id.image_card4)).setImageResource(pickCard(hand_resulter));
			turn_displayed=true;
			((ImageView) getView().findViewById(R.id.image_card4)).clearAnimation();
		}
		else if(!river_displayed){
			((ImageView) getView().findViewById(R.id.image_card5)).setImageResource(pickCard(hand_resulter));
			river_displayed=true;
			((ImageView) getView().findViewById(R.id.image_card5)).clearAnimation();
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}

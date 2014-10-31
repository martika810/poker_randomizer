package com.example.poker_randomizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends PokerActivity 
		{
	AlertDialog alertDialog;

	// handle the animation and allow swiping horizontally
	private ViewPager mPager;

	// Provide pages for to the viewPager
	private PagerAdapter mPagerAdapter;

	protected void onCreate(Bundle savedInstanceState) {

		data_file = "hands_results.txt";
		ACTION_NEW_HAND = "android.intent.action.MAIN2";

		super.onCreate(savedInstanceState);

		// setTextViewFonts();
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
		}  catch (IOException e) {
			e.printStackTrace();
		}

		// setContentView(R.layout.activity_main2);
		setContentView(R.layout.twoplayers_activity_screen_slide);

		// instance the viewPager and pager adapter
		mPager = (ViewPager) findViewById(R.id.twoplayer_pager);
		mPagerAdapter = new TwoPlayerScreenSlidePagerAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		

	}



	public ViewPager getmPager() {
		if(mPager==null){
			mPager=(ViewPager)findViewById(R.id.twoplayer_pager);
		}
			
		return mPager;
	}

	public void resulting() throws Exception {

		// get total for player1 and player2

		ResultHand resultplayer1 = hand_resulter.getResult("1");
		ResultHand resultplayer2 = hand_resulter.getResult("2");

		int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
		int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());

		List<Integer> playerLayouts = new ArrayList<Integer>();
		playerLayouts.add(R.id.inner_player_panel1);
		playerLayouts.add(R.id.inner_player_panel2);

		int[] vPlayerStrings = { R.string.player1, R.string.player2,
				R.string.player3, R.string.player4 };

		List<Integer> lTotalScore;
		List<ResultHand> lResultHand;
		List<Integer> winner_player_num;

		boolean player1_won;
		String strWhoWon = "";
		// Check if player1 has won, to store the result in the
		// hand_recorder
		//
		// // save the totalScore in a vector to check
		lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2);
		lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2);
		// Once u know the highest hand, check who it belongs to
		int winner = getAndHighLightWinner(playerLayouts, lTotalScore,
				lResultHand, HandResulter.FIRST_STAGE);
		highlighWinner(playerLayouts.get(winner), vPlayerStrings[winner],
				lResultHand.get(winner));
		resultGuess(String.valueOf(winner));
		if (winner == -1) {
			throw new Exception("Error calculating the winner:winner is -1");
		}

		// // Save save player1's hand for the statistic
		switch (winner) {
		case 0:
			hand_recorder.saveResult(resultplayer1.getTypeHand());
			break;
		case 1:
			hand_recorder.saveResult(resultplayer2.getTypeHand());
			break;
		default:
			throw new Exception("Error Unknown player");

		}
		List<Card> handPlayer1 = hand_resulter.getCards_player1();
		hand_recorder.addHand(handPlayer1);
		try {
			writeHandRecorderToFile(hand_recorder);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		//

	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class TwoPlayerScreenSlidePagerAdapter extends
			FragmentStatePagerAdapter {
		public TwoPlayerScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		

		@Override
		public Fragment getItem(int position) {
			return TwoPlayerScreenSlidePageFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return 10;
		}
	}

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		
	}
	

	
}

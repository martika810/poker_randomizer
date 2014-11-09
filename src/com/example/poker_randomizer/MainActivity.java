package com.example.poker_randomizer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends PokerActivity {

	// handle the animation and allow swiping horizontally
	private ViewPager mPager;

	// Provide pages for to the viewPager
	private PagerAdapter mPagerAdapter;

	protected void onCreate(Bundle savedInstanceState) {

		
		ACTION_NEW_HAND = "android.intent.action.MAIN2";

		super.onCreate(savedInstanceState);

		hand_recorder = HandRecorder.createInstance(this,
				HandRecorder.DATA_FILE_TWO_PLAYERS);

		setContentView(R.layout.twoplayers_activity_screen_slide);

		// instance the viewPager and pager adapter
		mPager = (ViewPager) findViewById(R.id.twoplayer_pager);

		mPagerAdapter = new TwoPlayerScreenSlidePagerAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);

	}

	public ViewPager getmPager() {
		if (mPager == null) {
			mPager = (ViewPager) findViewById(R.id.twoplayer_pager);
		}

		return mPager;
	}

	

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub

	}

}

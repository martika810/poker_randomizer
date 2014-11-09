package com.example.poker_randomizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class FourPlayersActivity extends PokerActivity {

	// handle the animation and allow swiping horizontally
	private ViewPager mPager;

	// Provide pages for to the viewPager
	private PagerAdapter mPagerAdapter;

	// static String ACTION_NEW_HAND = "android.intent.action.MAIN3";

	protected void onCreate(Bundle savedInstanceState) {

		ACTION_NEW_HAND = "android.intent.action.MAIN3";

		super.onCreate(savedInstanceState);

		hand_recorder = HandRecorder.createInstance(this,
				HandRecorder.DATA_FILE_FOUR_PLAYERS);
		setContentView(R.layout.fourplayers_activity_screen_slide);

		mPager = (ViewPager) findViewById(R.id.fourplayer_pager);
		mPagerAdapter = new FourPlayerScreenSlidePagerAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);

	}
	
	public ViewPager getmPager(){
		if(mPager==null){
			mPager=(ViewPager)findViewById(R.id.fourplayer_pager);
		}
		return mPager;
		
	}
	
	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		
	}
	


}

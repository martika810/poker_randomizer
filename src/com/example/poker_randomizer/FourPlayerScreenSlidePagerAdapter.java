package com.example.poker_randomizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class FourPlayerScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

	

	public FourPlayerScreenSlidePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		return FourPlayerScreenSlidePageFragment.newInstance(position);

	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	
}

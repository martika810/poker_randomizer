package com.example.poker_randomizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ThreePlayerScreenSlidePagerAdapter extends
		FragmentStatePagerAdapter {

	public ThreePlayerScreenSlidePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		return ThreePlayerScreenSlidePageFragment.newInstance(position);

	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}

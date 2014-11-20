package com.example.poker_randomizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class TwoPlayerScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements OnPageChangeListener{

	

	public TwoPlayerScreenSlidePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		
		return TwoPlayerScreenSlidePageFragment.newInstance(position);
		

	}
	


	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		
		super.notifyDataSetChanged();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		System.out.println("test");
		
	}

	@Override
	public void onPageSelected(int arg0) {
		System.out.println("test");
		
	}


	
	
}

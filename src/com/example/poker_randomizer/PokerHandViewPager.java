package com.example.poker_randomizer;

import android.content.Context;
import android.graphics.Canvas;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PokerHandViewPager extends ViewPager implements OnPageChangeListener{
	

	int selectedPage;
	public PokerHandViewPager(Context context) {
		super(context);
	}

	public PokerHandViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		super.onPageScrolled(arg0, arg1, arg2);
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}



	
	

}

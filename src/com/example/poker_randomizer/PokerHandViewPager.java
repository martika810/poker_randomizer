package com.example.poker_randomizer;

import android.content.Context;
import android.graphics.Canvas;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class PokerHandViewPager extends ViewPager implements OnPageChangeListener{
	

	public PokerHandViewPager(Context context) {
		super(context);
	}

	public PokerHandViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
		
		

	}
	
	@Override
	public void onPageScrollStateChanged(int position) {


		
	}

	@Override
	public void setCurrentItem(int item) {
		
		super.setCurrentItem(item);

	}

	@Override
	public void onPageSelected(int position) {
		
//		HandRecorder hand_recorder = ((PokerActivity) getContext())
//				.getHand_recorder();
//		
//		
//		TextView number_handTxt = (TextView) getChildAt(position).findViewById(R.id.txt_number_hands);
//
//		TextView number_guessTxt = (TextView) getChildAt(position).findViewById(R.id.number_guess_txt);
//		number_handTxt.setText("Hands: " + hand_recorder.getNumber_hands());
//		number_guessTxt.setText("Guesses: "
//				+ hand_recorder.getNumber_rigth_guesses() + "/"
//				+ hand_recorder.getNumber_guesses());

	}

	



	
	

}

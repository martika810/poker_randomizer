package com.martocio.poker_randomizer;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardBackFragment extends Fragment {
	
	public CardBackFragment() {
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View cardBackView=inflater.inflate(R.layout.fragment_card_back, container,
				false);
		cardBackView.setVisibility(View.VISIBLE);
		return cardBackView;
	}
}

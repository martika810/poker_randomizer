package com.example.poker_randomizer;



import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CardFrontFragment extends Fragment {
	private int idDrawable;

	public CardFrontFragment(int idCardDrawable) {
		this.idDrawable = idCardDrawable;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View img = inflater.inflate(R.layout.fragment_card_front,
				container, false);
		((ImageView) img).setImageResource(idDrawable);
		return img;
	}
}

package com.example.poker_randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

public class Dealer {

	List<String> cards = new ArrayList<String>();
	
	public static final int PLAYER_ONE=1;
	public static final int PLAYER_TWO=2;
	public static final int PLAYER_THREE=3;
	public static final int PLAYER_FOUR=4;
	

	String[] typeCard = { "s", "h", "d", "c" };
	private static final String[] typeNumbers = { "2", "3", "4", "5", "6", "7",
			"8", "9", "x", "j", "q", "k", "a" };

	private static Dealer INSTANCE = null;

	protected Dealer() {
	}

	public static Dealer getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new Dealer();
		}
		return INSTANCE;
	}

	public void flushCards() {
		for (String type : typeCard) {
			for (String num : typeNumbers) {
				cards.add(type + num);

			}
		}
	}

	public String pickCard() {
		Random r = new Random();

		int position_card_pick = r.nextInt(cards.size());
		String card_picked = cards.get(position_card_pick);
		cards.remove(position_card_pick);
		return card_picked;

	}
	public void dealAllCards(Fragment parentFragment,int num_players){
		
		flushCards();
		HandResulter handResulter=((ResulterFragment)parentFragment).getHand_resulter();
		handResulter.refresh();
		//pick five board cards
		for(int it1=1;it1<6;it1++){
			handResulter.addCardBoard(pickCard());
		}
		for(int it2=1;it2<num_players+1;it2++){
			handResulter.addCardPlayer(it2, pickCard());
			handResulter.addCardPlayer(it2, pickCard());
		}
		
	}

	public void placeBoardCard(Resources spotToPlaceCard,
			HandResulter handResulter,ImageView viewToPlaceCard, int cardToDeal) {

		int placeToPutPickedCard = -1;
		Card cardToPlace= handResulter.getCards_boards().get(cardToDeal-1);

		
		//hand_resulter.addCardBoard(pickedCard);
		int identifierCardPicked = spotToPlaceCard.getIdentifier(cardToPlace.toString(),
				"drawable", "com.example.poker_randomizer");
		
		viewToPlaceCard.setImageResource(identifierCardPicked);

	}

	public void placeCardsToPlayer(Resources spotToPlaceCard,
			HandResulter handResulter, CardPairView spotToPlaceCardPair,int player) throws Exception {
		
		if (player < 1 || player > 4) {
			throw new Exception("the player number is wrong");
		}

		//String pickedFirstCard = pickCard();

		List<Card> playerCards=handResulter.getPlayerHand(player);

//		// pick card 2
//		String pickedSecondCard = pickCard();
//
//		((MainActivity) board).getHand_resulter().addCardPlayer(player, pickedSecondCard);
//
//		// Load the cards in the view
		
	
		spotToPlaceCardPair.init(playerCards.get(0).toString(),playerCards.get(1).toString());
		spotToPlaceCardPair.invalidate();

	}

}

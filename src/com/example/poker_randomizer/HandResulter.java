package com.example.poker_randomizer;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

public class HandResulter {
	List<Card> cards_boards;
	List<Card> cards_player1;
	List<Card> cards_player2;
	List<Card> cards_player3;
	List<Card> cards_player4;

	public static final int FIRST_STAGE=0;
	public static final int SECOND_STAGE=1;
	public static final int THIRD_STAGE=2;
	public static final int FOURTH_STAGE=3;
	private static final String HIGH_CARD = "0";
	private static final String PAIR = "1";
	private static final String TRIPLE = "3";
	private static final String DOUBLE_PAIR = "2";
	private static final String FULL = "6";
	private static final String POKER = "7";
	private static final String STRAIGHT = "4";
	private static final String FLUSH = "5";
	private static final String CLUB = "c";
	private static final String HEART = "h";
	private static final String DIAMOND = "d";
	private static final String SHAPE = "s";
	private static final String[] typeNumbers = { "2", "3", "4", "5", "6", "7",
			"8", "9", "x", "j", "q", "k", "a" };

	private static final String[] typeColors = { CLUB, HEART, DIAMOND, SHAPE };
	private Context context;

	public HandResulter(Context context) {
		cards_boards = new ArrayList<Card>();
		cards_player1 = new ArrayList<Card>();
		cards_player2 = new ArrayList<Card>();
		cards_player3 = new ArrayList<Card>();
		cards_player4 = new ArrayList<Card>();
		this.context = context;
	}

	public void refresh() {
		cards_boards = new ArrayList<Card>();
		cards_player1 = new ArrayList<Card>();
		cards_player2 = new ArrayList<Card>();
		cards_player3 = new ArrayList<Card>();
		cards_player4 = new ArrayList<Card>();
	}

	public void addCardBoard(String strCard) {
		cards_boards.add(new Card(strCard));

	}

	// public void addCardPlayer1(String strCard){
	// cards_player1.add(new Card(strCard));
	//
	// }
	// public void addCardplayer2(String strCard){
	// cards_player2.add(new Card(strCard));
	//
	// }
	// public void addCardplayer3(String strCard){
	// cards_player3.add(new Card(strCard));
	//
	// }
	// public void addCardplayer4(String strCard){
	// cards_player4.add(new Card(strCard));
	//
	// }
	public void addCardPlayer(int player, String strCard) {
		switch (player) {
		case 1:
			cards_player1.add(new Card(strCard));
			break;
		case 2:
			cards_player2.add(new Card(strCard));
			break;
		case 3:
			cards_player3.add(new Card(strCard));
			break;
		case 4:
			cards_player4.add(new Card(strCard));
			break;
		}
	}

	public List<Card> getCards_boards() {
		return cards_boards;
	}

	public void setCards_boards(List<Card> cards_boards) {
		this.cards_boards = cards_boards;
	}

	public List<Card> getCards_player1() {
		return cards_player1;
	}

	public void setCards_player1(List<Card> cards_player1) {
		this.cards_player1 = cards_player1;
	}

	public List<Card> getCards_player2() {
		return cards_player2;
	}

	public void setCards_player2(List<Card> cards_player2) {
		this.cards_player2 = cards_player2;
	}

	public ResultHand getResult(String player) {
		ResultHand result;
		String allCards = "";
		List<Card> listallCards = new ArrayList<Card>();
		Map<Integer, List<Integer>> mapCounting = new HashMap<Integer, List<Integer>>();
		Map<Integer, String> mapFlush = new HashMap<Integer, String>();
		for (Card c : cards_boards) {
			allCards = allCards + c.toString();
			listallCards.add(c);
		}
		if (player == "1") {
			for (Card c : cards_player1) {
				allCards = allCards + c.toString();
				listallCards.add(c);
			}
		} else if ((player == "2")) {
			for (Card c : cards_player2) {
				allCards = allCards + c.toString();
				listallCards.add(c);
			}

		} else if ((player == "3")) {
			for (Card c : cards_player3) {
				allCards = allCards + c.toString();
				listallCards.add(c);
			}

		} else {
			for (Card c : cards_player4) {
				allCards = allCards + c.toString();
				listallCards.add(c);
			}

		}
		// counting the ocurrencies for each card number type
		for (String typeCard : typeNumbers) {
			int numOccurrencies = StringUtils.countMatches(allCards, typeCard);
			if (numOccurrencies > 0) {
				if (mapCounting.containsKey(numOccurrencies)) {
					List<Integer> value = mapCounting.get(numOccurrencies);
					value.add(Card.valuesCards.get(typeCard));
					mapCounting.put(numOccurrencies, value);
				} else {
					List<Integer> list = new ArrayList<Integer>();
					list.add(Card.valuesCards.get(typeCard));
					mapCounting.put(numOccurrencies, list);
					
				}
			}

		}
		// Counting suits to check is there is flush
		for (String suit : typeColors) {
			int numOccurrencies = StringUtils.countMatches(allCards, suit);
			if (numOccurrencies == 5) {
				mapFlush.put(5, suit);
			}
		}

		// count the colors in some way to after work out if it s a flush ir not
		result = new ResultHand(context);
		if ((result = isPoker(mapCounting)) == null) {
			if ((result = isFull(mapCounting)) == null) {
				if ((result = isFlush(mapFlush)) == null) {
					if ((result = isStraight(listallCards)) == null) {
						if ((result = isTriple(mapCounting)) == null) {
							if ((result = isDoublePair(mapCounting)) == null) {
								if ((result = isPair(mapCounting)) == null) {

									result = new ResultHand(context);
									result.setTypeHand(HIGH_CARD);
									int valueHand = mapCounting.get(1).get(0);
									result.setValue(valueHand);

								}
							}
						}
					}
				}
			}
		}

		return result;

	}

	private ResultHand isPoker(Map<Integer, List<Integer>> mapCounting) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(4)) {
			List<Integer> valuesCards = mapCounting.get(4);
			Collections.sort(valuesCards);
			result.setTypeHand(POKER);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isFlush(Map<Integer, String> mapFlush) {
		ResultHand result = new ResultHand(context);
		if (mapFlush.containsKey(5)) {
			result.setTypeHand(FLUSH);
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isDoublePair(Map<Integer, List<Integer>> mapCounting) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(2) && (mapCounting.get(2).size() >= 2)) {

			List<Integer> valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setTypeHand(DOUBLE_PAIR);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			result.setValue(valuesCards.get(valuesCards.size() - 2));
			return result;

		} else {
			return null;
		}
	}
	
	public Card maxCard_from_playerHand(String player){
		List<Card> listallCards = new ArrayList<Card>();
		//place the cards of player and the board cards in a list
		if (player.equals("1")) {
			for (Card c : cards_player1) {
				listallCards.add(c);
			}
		} else if (player.equals("2")) {
			for (Card c : cards_player2) {
				
				listallCards.add(c);
			}
		} else if (player.equals("3")){
			for (Card c : cards_player3) {
				
				listallCards.add(c);
			}
		} else {
			for (Card c : cards_player4) {
				
				listallCards.add(c);
			}

		}
		//loop throught them and find the max
		Collections.sort(listallCards);
		Card maxCard=listallCards.get(listallCards.size()-1);
		return maxCard;
		
		
	}
	
	public Card maxCard(String player){
		List<Card> listallCards = new ArrayList<Card>();
		//place the cards of player and the board cards in a list
		for (Card c : cards_boards) {
			
			listallCards.add(c);
		}
		if (player == "1") {
			for (Card c : cards_player1) {
				listallCards.add(c);
			}
		} else if ((player == "2")) {
			for (Card c : cards_player2) {
				
				listallCards.add(c);
			}
		} else if ((player == "3")) {
			for (Card c : cards_player3) {
				
				listallCards.add(c);
			}
		} else {
			for (Card c : cards_player4) {
				
				listallCards.add(c);
			}

		}
		//loop throught them and find the max
		Collections.sort(listallCards);
		Card maxCard=listallCards.get(listallCards.size()-1);
		return maxCard;
		
		
	}


	private ResultHand isPair(Map<Integer, List<Integer>> mapCounting) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(2)) {

			List<Integer> valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setTypeHand(PAIR);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			return result;

		} else {
			return null;
		}
	}

	private ResultHand isTriple(Map<Integer, List<Integer>> mapCounting) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(3)) {
			List<Integer> valuesCards = mapCounting.get(3);
			Collections.sort(valuesCards);
			result.setTypeHand(TRIPLE);
			int valueHand = valuesCards.get(valuesCards.size() - 1);
			result.setValue(valueHand);
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isStraight(List<Card> cards) {
		ResultHand result = new ResultHand(context);
		Collections.sort(cards);
		int count = 0, i = 0;
		while (i < cards.size() - 1) {
			if (cards.get(i).diff(cards.get(i + 1)) == 1) {
				count++;
				if (count == 4) {
					result.setTypeHand(STRAIGHT);
					result.setValue(Card.valuesCards.get(cards.get(i + 1)
							.getCardNumber()));
				}
			} else {
				count = 0;
			}
			i++;
		}
		if (count != 4) {
			result = null;
		}
		return result;
	}

	private ResultHand isFull(Map<Integer, List<Integer>> mapCounting) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(3) && mapCounting.containsKey(2)) {
			List<Integer> valuesCards = mapCounting.get(3);
			Collections.sort(valuesCards);
			result.setTypeHand(FULL);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			return result;
		} else {
			return null;
		}
	}

}

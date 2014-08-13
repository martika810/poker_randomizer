package com.example.poker_randomizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card>{
	private String suit;
	private String cardNumber;
	protected static Map<String,Integer> valuesCards;
	
	private static final String CLUB="c";
	private static final String HEART="h";
	private static final String DIAMOND="d";
	private static final String SHAPE="s";
	
	final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;
	static{
		Map<String,Integer> values=new HashMap<String,Integer>();
		values.put("2",2);
		values.put("3",3);
		values.put("4",4);
		values.put("5",5);
		values.put("6",6);
		values.put("7",7);
		values.put("8",8);
		values.put("9",9);
		values.put("x",10);
		values.put("j",11);
		values.put("q",12);
		values.put("k",13);
		values.put("a",14);
		valuesCards=Collections.unmodifiableMap(values);		
	}
	
	
	public Card(String suit, String cardNumber) {
		super();
		this.suit = suit;
		this.cardNumber = cardNumber;
	}
	
	public Card(String strCard) {
		this.suit = String.valueOf(strCard.charAt(0));
		this.cardNumber = String.valueOf(strCard.charAt(1));
	}
	@Override
	
	public String toString() {
		return suit+cardNumber;
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public int compareTo(Card another) {
		int valueCurrentCard=valuesCards.get(this.cardNumber);
		int valueAnotherCard=valuesCards.get(another.cardNumber);
		return valueCurrentCard>valueAnotherCard?AFTER:valueCurrentCard<valueAnotherCard?BEFORE:EQUAL;
	}

	public int diff(Card another) {
		int valueCurrentCard=valuesCards.get(this.cardNumber);
		int valueAnotherCard=valuesCards.get(another.cardNumber);
		return valueCurrentCard>valueAnotherCard?valueCurrentCard-valueAnotherCard:valueAnotherCard-valueCurrentCard;
	}
}

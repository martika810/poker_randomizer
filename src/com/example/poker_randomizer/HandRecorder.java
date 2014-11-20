package com.example.poker_randomizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HandRecorder {
	private int number_hands;
	private Map<String, HandTypes> hands;// to save the hand type(ll,lh...) and
	// how many times this type has won or lost
	private Map<String, Integer> mapCardValue;
	private int number_flush_straight;
	private int number_poker;
	private int number_full;
	private int number_straight;
	private int number_flush;
	private int number_triple;
	private int number_double_pair;
	private int number_pair;
	private int number_high_card;
	private boolean player1_won = false;
	private int number_guesses;
	private int number_rigth_guesses;

	
	public static String DATA_FILE_TWO_PLAYERS = "hands_results.txt";
	public static String DATA_FILE_FOUR_PLAYERS = "four_hands_results.txt";
	public static String DATA_FILE_THREE_PLAYERS = "three_hands_results.txt";

	public HandRecorder() {
		number_hands = 0;
		hands = new HashMap<String, HandTypes>();
		mapCardValue = new HashMap<String, Integer>();
		String[] typeNumbers = { "2", "3", "4", "5", "6", "7", "8", "9", "x",
				"j", "q", "k", "a" };
		int i = 2;
		for (String card : typeNumbers) {
			mapCardValue.put(card, i);
			i++;
		}
		// initialize the hand type(ll,lh,hh,lp,hp) map
		hands.put("ll", new HandTypes("ll", 0, 0));
		hands.put("lh", new HandTypes("lh", 0, 0));
		hands.put("hh", new HandTypes("hh", 0, 0));
		hands.put("lp", new HandTypes("lp", 0, 0));
		hands.put("hp", new HandTypes("hp", 0, 0));

	}

	public HandRecorder(int number_hands, Map<String, HandTypes> hands) {
		super();
		this.number_hands = number_hands;
		this.hands = hands;
		String[] typeNumbers = { "2", "3", "4", "5", "6", "7", "8", "9", "x",
				"j", "q", "k", "a" };
		int i = 2;
		// to set the value of each card
		for (String card : typeNumbers) {
			mapCardValue.put(card, i);
			i++;
		}

	}

	public int getNumber_hands() {
		return number_hands;
	}

	public void setNumber_hands(int number_hands) {
		this.number_hands = number_hands;
	}

	public Map<String, HandTypes> getHands() {
		return hands;
	}

	public void setHands(Map<String, HandTypes> hands) {
		this.hands = hands;
	}

	public String getTypeHand(Card card1, Card card2) {
		String typecard = "";

		int valuecard1 = mapCardValue
				.get(String.valueOf(card1.getCardNumber()));
		int valuecard2 = mapCardValue
				.get(String.valueOf(card2.getCardNumber()));
		// if the hand is pair, checks if it s a low of high pair
		if (valuecard1 == valuecard2) {
			if (valuecard1 >= 2 && valuecard1 <= 8) {
				typecard = "lp";
			} else {
				typecard = "hp";

			}
		} else {
			if (valuecard1 >= 2 && valuecard1 <= 8) {
				typecard = typecard + "l";
			} else {
				typecard = typecard + "h";

			}
			if (valuecard2 >= 2 && valuecard2 <= 8) {
				typecard = typecard + "l";
			} else {
				typecard = typecard + "h";

			}
		}
		if (typecard.equals("hl")){typecard="lh";}
		return typecard;
	}

	public int getWinner(List<String> cardsOnTable,
			List<List<String>> cardsPlayers) {

		return 0;
	}

	public void addHand(List<Card> hand) {
		String resultTypeHand = getTypeHand(hand.get(0), hand.get(1));
		if (hands.containsKey(resultTypeHand)) {
			if (player1_won) {
				hands.get(resultTypeHand).addWon();
			} else {
				hands.get(resultTypeHand).addLose();
			}

		}
		number_hands++;
	}

	public boolean isPlayer1_won() {
		return player1_won;
	}

	public void setPlayer1_won(boolean player1_won) {
		this.player1_won = player1_won;
	}

	public void saveResult(String result) {

		int num_result = Integer.valueOf(result);
		switch (num_result) {
		case 0:
			number_high_card++;
			break;
		case 1:
			number_pair++;
			break;
		case 2:
			number_double_pair++;
			break;
		case 3:
			number_triple++;
			break;
		case 4:
			number_straight++;
			break;
		case 5:
			number_flush++;
			break;
		case 6:
			number_full++;
			break;
		case 7:
			number_poker++;
			break;
		default:
			break;
		}

	}

	public int getNumber_flush_straight() {
		return number_flush_straight;
	}

	public void setNumber_flush_straight(int number_flush_straight) {
		this.number_flush_straight = number_flush_straight;
	}

	public int getNumber_poker() {
		return number_poker;
	}

	public void setNumber_poker(int number_poker) {
		this.number_poker = number_poker;
	}

	public int getNumber_full() {
		return number_full;
	}

	public void setNumber_full(int number_full) {
		this.number_full = number_full;
	}

	public int getNumber_straight() {
		return number_straight;
	}

	public void setNumber_straight(int number_straight) {
		this.number_straight = number_straight;
	}

	public int getNumber_flush() {
		return number_flush;
	}

	public void setNumber_flush(int number_flush) {
		this.number_flush = number_flush;
	}

	public int getNumber_triple() {
		return number_triple;
	}

	public void setNumber_triple(int number_triple) {
		this.number_triple = number_triple;
	}

	public int getNumber_double_pair() {
		return number_double_pair;
	}

	public void setNumber_double_pair(int number_double_pair) {
		this.number_double_pair = number_double_pair;
	}

	public int getNumber_pair() {
		return number_pair;
	}

	public void setNumber_pair(int number_pair) {
		this.number_pair = number_pair;
	}

	public int getNumber_high_card() {
		return number_high_card;
	}

	public void setNumber_high_card(int number_high_card) {
		this.number_high_card = number_high_card;
	}

	public Map<String, Integer> getMapCardValue() {
		return mapCardValue;
	}

	public void setMapCardValue(Map<String, Integer> mapCardValue) {
		this.mapCardValue = mapCardValue;
	}

	public int getNumber_guesses() {
		return number_guesses;
	}

	public void setNumber_guesses(int number_guesses) {
		this.number_guesses = number_guesses;
	}

	public int getNumber_rigth_guesses() {
		return number_rigth_guesses;
	}

	public void setNumber_rigth_guesses(int number_rigth_guesses) {
		this.number_rigth_guesses = number_rigth_guesses;
	}

	public void saveWinnerHand(ResultHand winnerResult) {
		
			saveResult(winnerResult.getTypeHand());
		
	}

	public void saveToFile(FragmentActivity context,String dataFile) throws FileNotFoundException {
		Gson gson = new GsonBuilder().create();
		File file = new File(dataFile);
		FileOutputStream fos = context.openFileOutput(dataFile,
				context.MODE_PRIVATE);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(fos)));
		String strDataHandRecorder = gson.toJson(this);
		pw.print(strDataHandRecorder);
		pw.close();

	}

	private HandRecorder loadJsonToHandRecorder(Gson gson,String strJson) {

		HandRecorder handRecorder = gson.fromJson(strJson, HandRecorder.class);
		return handRecorder;

	}

	public HandRecorder readHandRecorderFromFile(FragmentActivity context,Gson gson,
			String dataFile) throws IOException {

		FileInputStream fis = context.openFileInput(dataFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String strJson = br.readLine();

		HandRecorder handRecorder = loadJsonToHandRecorder(gson,strJson);

		br.close();
		return handRecorder;
	}

	public static HandRecorder createInstance(FragmentActivity context,String dataFile) {
		HandRecorder instance=null;
		Gson gson = new GsonBuilder().create();
		
		String strDataHandRecorder = gson.toJson(new HandRecorder());
		try {
			if(instance==null){
				instance=new HandRecorder();
			}
			if (!context.getFileStreamPath(dataFile).exists()) {

				
				instance.saveToFile(context,dataFile);

			} else {
				instance =  instance.readHandRecorderFromFile(context,gson,dataFile);

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}

}

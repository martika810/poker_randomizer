package com.martocio.poker_randomizer;

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
		result = new ResultHand(context);
		// count the colors in some way to after work out if it s a flush ir not
		int player_num=Integer.parseInt(player);
		if ((result = isPoker(mapCounting,player_num)) == null) {
			if ((result = isFull(mapCounting,player_num)) == null) {
				if ((result = isFlush(mapFlush,player_num)) == null) {
					if ((result = isStraight(listallCards,mapCounting,player_num)) == null) {
						if ((result = isTriple(mapCounting,player_num)) == null) {
							if ((result = isDoublePair(mapCounting,player_num)) == null) {
								if ((result = isPair(mapCounting,player_num)) == null) {
									result = new ResultHand(context);
									result.setTypeHand(HIGH_CARD);
									int valueHand = mapCounting.get(1).get(0);
									result.setValue(valueHand);
									result.setCardValuesFromCards(getPlayerHand(player_num));

								}
							}
						}
					}
				}
			}
		}
		result.setNum_player(Integer.parseInt(player));//that can be deleted
		return result;

	}

	private ResultHand isPoker(Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(4)) {
			List<Integer> valuesCards = mapCounting.get(4);
			Collections.sort(valuesCards);
			result.setTypeHand(POKER);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			result.setCardValuesFromCards(getPlayerHand(player_num));
			result.setNum_player(player_num);
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isFlush(Map<Integer, String> mapFlush,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapFlush.containsKey(5)) {
			result.setTypeHand(FLUSH);
			result.setCardValuesFromCards(getPlayerHand(player_num));
			result.setNum_player(player_num);
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isDoublePair(Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(2) && (mapCounting.get(2).size() >= 2)) {

			List<Integer> valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setTypeHand(DOUBLE_PAIR);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			result.setValue(valuesCards.get(valuesCards.size() - 2));
			result.setCardValuesFromCards(getPlayerHand(player_num));
			result.setNum_player(player_num);
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


	private ResultHand isPair(Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(2)) {

			List<Integer> valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setTypeHand(PAIR);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			result.setCardValuesFromCards(getPlayerHand(player_num));
			result.setNum_player(player_num);
			return result;

		} else {
			return null;
		}
	}

	private ResultHand isTriple(Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(3)) {
			List<Integer> valuesCards = mapCounting.get(3);
			Collections.sort(valuesCards);
			result.setTypeHand(TRIPLE);
			int valueHand = valuesCards.get(valuesCards.size() - 1);
			result.setValue(valueHand);
			result.setCardValuesFromCards(getPlayerHand(player_num));
			return result;
		} else {
			return null;
		}
	}

	private ResultHand isStraight(List<Card> cards,Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		Collections.sort(cards);
		int count = 0, i = cards.size() - 1;
		while (i > 0) {
			//chek if the first card is and ACE, in that case check if its a low straight[A,2,3,4,5]
			if(cards.get(i).getCardNumber().equals("a")){
				if(mapCounting.get(1).contains(2) && mapCounting.get(1).contains(3) && mapCounting.get(1).contains(4)&& mapCounting.get(1).contains(5)){
					result.setTypeHand(STRAIGHT);
					result.setValue(Card.valuesCards.get(cards.get(i)
							.getCardNumber()));
					result.setCardValuesFromCards(getPlayerHand(player_num));
					return result;
				}
			}
			if (cards.get(i).diff(cards.get(i - 1)) == 1) {
				count++;
				if (count == 4) {
					result.setTypeHand(STRAIGHT);
					result.setValue(Card.valuesCards.get(cards.get(i + 1)
							.getCardNumber()));
					result.setCardValuesFromCards(getPlayerHand(player_num));
					
				}
			} else {
				count = 0;
			}
			i--;
		}
		if (count != 4) {
			result = null;
		}
		return result;
	}

	private ResultHand isFull(Map<Integer, List<Integer>> mapCounting,int player_num) {
		ResultHand result = new ResultHand(context);
		if (mapCounting.containsKey(3) && mapCounting.containsKey(2)) {
			List<Integer> valuesCards = mapCounting.get(3);
			Collections.sort(valuesCards);
			result.setTypeHand(FULL);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			valuesCards = mapCounting.get(2);
			Collections.sort(valuesCards);
			result.setValue(valuesCards.get(valuesCards.size() - 1));
			result.setCardValuesFromCards(getPlayerHand(player_num));
			result.setNum_player(player_num);
			return result;
		} else {
			return null;
		}
	}
	
	public List<Card> getPlayerHand(int player_number){
		List<Card> returnList=null;
		switch(player_number){
		case 1:
			returnList=cards_player1;
			break;
			
		case 2:
			returnList=cards_player2;
			break;
		case 3:
			returnList=cards_player3;
			break;
		case 4:
			returnList=cards_player4;
			break;
		
		}
		return returnList;	
	}
	
	public ResultHand resultingTwoPlayers() throws Exception {

		// get total for player1 and player2

		ResultHand resultplayer1 = getResult("1");
		ResultHand resultplayer2 =getResult("2");

		int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
		int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());

		List<Integer> playerLayouts = new ArrayList<Integer>();
		playerLayouts.add(R.id.inner_player_panel1);
		playerLayouts.add(R.id.inner_player_panel2);

		int[] vPlayerStrings = { R.string.player1, R.string.player2,
				R.string.player3, R.string.player4 };

		List<Integer> lTotalScore;
		List<ResultHand> lResultHand;
		List<Integer> winner_player_num;

		boolean player1_won;
		String strWhoWon = "";
		// Check if player1 has won, to store the result in the
		// hand_recorder
		//
		// // save the totalScore in a vector to check
		lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2);
		lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2);
		// Once u know the highest hand, check who it belongs to
		ResultHand winner = getAndHighLightWinner(playerLayouts, lTotalScore,
				lResultHand, HandResulter.FIRST_STAGE);

		if (winner.getNum_player() == -1) {
			throw new Exception("Error calculating the winner:winner is -1");
		}


		return winner;

	}
	
	// Returns the number of the hand winner
		public ResultHand getAndHighLightWinner(List<Integer> playerLayoutsIds,
				List<Integer> totalScores, List<ResultHand> resultHands,
				int resulting_stage) {
			
			//
			int[] vPlayerStrings={R.string.player1,R.string.player2,R.string.player3,R.string.player4};
			// Return the player number of the player who won the hand
			int maxTotalScore = Util.getMax(totalScores);
			int currentPos = 0;
			String strWhoWon = "";
			ResultHand winner = null;
			int pos;
			List<Integer> lwinners = new ArrayList<Integer>();
			// int times = Collections.frequency(totalScores, maxTotalScore);
			// if (times>1) return 0;
			// Once u know the highest hand, check who it belongs to
			for (int score : totalScores) {
				if (maxTotalScore == score) {
					lwinners.add(currentPos);
				}
				currentPos++;
			}
			if (lwinners.size() == 1) {
				winner = resultHands.get(lwinners.get(0));
				
//				
//				if (winner == 0) {
//					hand_recorder.setPlayer1_won(true);
//				} else
//					hand_recorder.setPlayer1_won(false);
				
				return winner;
			} else {
				switch (resulting_stage) {
				case HandResulter.FIRST_STAGE:
					// Recreate the totalScore for the winners, as there is several
					// player with the same hand,
					// the value of the hand has to count as well
					resultHands = Util.getSubVectorResultFromPositions(lwinners,
							resultHands);
					totalScores = new ArrayList<Integer>();
					for (ResultHand result : resultHands) {
						totalScores.add(Integer.valueOf(result.getTypeHand())
								+ result.getSumValues());
					}
					playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
							playerLayoutsIds);
					winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
							resultHands, HandResulter.SECOND_STAGE);
					
					break;
				case HandResulter.SECOND_STAGE:
					resultHands = Util.getSubVectorResultFromPositions(lwinners,
							resultHands);
					totalScores = new ArrayList<Integer>();
					pos = 1;
					for (ResultHand result : resultHands) {
						totalScores.add(Card.valuesCards.get(maxCard_from_playerHand(String.valueOf(pos))
								.getCardNumber()));
						pos++;
					}
					playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
							playerLayoutsIds);
					winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
							resultHands, HandResulter.THIRD_STAGE);

					break;
				case HandResulter.THIRD_STAGE:
					resultHands = Util.getSubVectorResultFromPositions(lwinners,
							resultHands);
					totalScores = new ArrayList<Integer>();
					pos = 1;
					for (ResultHand result : resultHands) {
						totalScores.add(result.getSumAllValues());
						pos++;
					}
					playerLayoutsIds = Util.getSubVectorFromPositions(lwinners,
							playerLayoutsIds);
					winner = getAndHighLightWinner(playerLayoutsIds, totalScores,
							resultHands, HandResulter.FOURTH_STAGE);
					
					break;
				case HandResulter.FOURTH_STAGE:
					break;
				}
			}
			
			return winner;

		}
		
		public ResultHand resultingFourPlayers() throws Exception {
			
			
			int[] vPlayerStrings={R.string.player1,R.string.player2,R.string.player3,R.string.player4};
			// // RESOLVE THE HAND
			ResultHand resultplayer1 = getResult("1");
			ResultHand resultplayer2 = getResult("2");
			ResultHand resultplayer3 = getResult("3");
			ResultHand resultplayer4 = getResult("4");
	
			int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
			int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
			int totalScore3 = Integer.valueOf(resultplayer3.getTypeHand());
			int totalScore4 = Integer.valueOf(resultplayer4.getTypeHand());
	
			List<Integer> playerLayouts = new ArrayList<Integer>();
			playerLayouts.add(R.id.inner_player_panel1);
			playerLayouts.add(R.id.inner_player_panel2);
			playerLayouts.add(R.id.inner_player_panel3);
			playerLayouts.add(R.id.inner_player_panel4);
	
			List<Integer> lTotalScore;
			List<ResultHand> lResultHand;
			List<Integer> winner_player_num;
	
			boolean player1_won;
			String strWhoWon = "";
			// Check if player1 has won, to store the result in the
			// hand_recorder
			//
			// // save the totalScore in a vector to check
			lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2,
					totalScore3);
			lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2,
					resultplayer3);
			// Once u know the highest hand, check who it belongs to
			ResultHand winner = getAndHighLightWinner(playerLayouts, lTotalScore,
					lResultHand, HandResulter.FIRST_STAGE);
//			highlighWinner(playerLayouts.get(winner), vPlayerStrings[winner],
//					lResultHand.get(winner));
			//resultGuess(String.valueOf(winner));
			if (winner.getNum_player() == -1) {
				throw new Exception("Error calculating the winner:winner is -1");
			}
	
			// // Save save player1's hand for the statistic
			return winner;

		}
		
		public ResultHand resultingThreePlayers() throws Exception {
			
			// ((Button) v).setCompoundDrawablesWithIntrinsicBounds(
			// R.drawable.winner, 0, 0, 0);
			// ((Button) v).setPadding(60, 0, 20, 0);
			int[] vPlayerStrings={R.string.player1,R.string.player2,R.string.player3,R.string.player4};
			// // RESOLVE THE HAND
			ResultHand resultplayer1 = getResult("1");
			ResultHand resultplayer2 = getResult("2");
			ResultHand resultplayer3 = getResult("3");
	
			int totalScore1 = Integer.valueOf(resultplayer1.getTypeHand());
			int totalScore2 = Integer.valueOf(resultplayer2.getTypeHand());
			int totalScore3 = Integer.valueOf(resultplayer3.getTypeHand());
	
			List<Integer> playerLayouts = new ArrayList<Integer>();
			playerLayouts.add(R.id.inner_player_panel1);
			playerLayouts.add(R.id.inner_player_panel2);
			playerLayouts.add(R.id.inner_player_panel3);
	
			List<Integer> lTotalScore;
			List<ResultHand> lResultHand;
			List<Integer> winner_player_num;
	
			boolean player1_won;
			String strWhoWon = "";
			// Check if player1 has won, to store the result in the
			// hand_recorder
			//
			// // save the totalScore in a vector to check
			lTotalScore = Util.buildIntegerArrays(totalScore1, totalScore2,
					totalScore3);
			lResultHand = Util.buildIntegerArrays(resultplayer1, resultplayer2,
					resultplayer3);
			// Once u know the highest hand, check who it belongs to
			ResultHand winner = getAndHighLightWinner(playerLayouts, lTotalScore,
					lResultHand, HandResulter.FIRST_STAGE);
//			highlighWinner(playerLayouts.get(winner), vPlayerStrings[winner],
//					lResultHand.get(winner));
			//resultGuess(String.valueOf(winner));
			if (winner.getNum_player() == -1) {
				throw new Exception("Error calculating the winner:winner is -1");
			}
	
			// // Save save player1's hand for the statistic
			return winner;

		}
	

}

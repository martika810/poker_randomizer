package com.example.poker_randomizer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ResultHand {
	
	private static final String HIGH_CARD="0";
	private static final String PAIR="1";
	private static final String DOUBLE_PAIR="2";
	private static final String TRIPLE="3";
	private static final String STRAIGHT="4";
	private static final String FLUSH="5";
	private static final String FULL="6";
	private static final String POKER="7";
	
	
	private String typeHand;
	private List<Integer> value;
	private Context context;
	
	
	
	public ResultHand(String typeHand, Integer value,Context context) {
		this.typeHand = typeHand;
		this.value=new ArrayList<Integer>();
		this.context=context;
	}
	
	public ResultHand(Context context) {
		this.value=new ArrayList<Integer>();
		this.context=context;
		
	}

	public String getTypeHand() {
		return typeHand;
	}
	public void setTypeHand(String typeHand) {
		this.typeHand = typeHand;
	}
	public List<Integer> getValue() {
		return value;
	}
	
	public int getValue(int pos) {
		return value.get(pos);
	}
	
	public void setValue(int value) {
		this.value.add(value);
	}
	
//	private static final String HIGH_CARD="0";
//	private static final String PAIR="1";
//	private static final String DOUBLE_PAIR="2";
//	private static final String TRIPLE="3";
//	private static final String STRAIGHT="4";
//	private static final String FLUSH="5";
//	private static final String FULL="6";
//	private static final String POKER="7";
//	
	public int getSumValues(){
//		int totalSumValues=0;
//		for(int v:value){
//			totalSumValues+=v;
//		}
//		return totalSumValues;
		return Util.getMax(value);
		
	}
	//return the full name of the hand passing in the code in String
	//example numHand="1" returns--> Pair
	public String getNameResult(){
		int codeHand=Integer.parseInt(typeHand);
		String strHand="";
		switch(codeHand){
		case 0:
			strHand=context.getString(R.string.high_card);
			break;
		case 1:
			strHand=context.getString(R.string.pair);
			break;
		case 2:
			strHand=context.getString(R.string.double_pair);
			break;
		case 3:
			strHand=context.getString(R.string.triple);
			break;
		case 4:
			strHand=context.getString(R.string.straight);
			break;
		case 5:
			strHand=context.getString(R.string.flush);
			break;
		case 6:
			strHand=context.getString(R.string.full);
			break;
		case 7:
			strHand=context.getString(R.string.poker);
			break;
		
		}
		return strHand;
		
		
	}
	
	
	

}

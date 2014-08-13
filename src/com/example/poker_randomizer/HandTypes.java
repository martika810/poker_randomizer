package com.example.poker_randomizer;

public class HandTypes {
	private String name;
	private int won;
	private int lose;
	
	@Override
	public String toString() {
		return super.toString();
	}
	public HandTypes(String name, int won, int lose) {
		super();
		this.name = name;
		this.won = won;
		this.lose = lose;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWon() {
		return won;
	}
	public void setWon(int won) {
		this.won = won;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public void addWon(){
		this.won++;
	}
	public void addLose(){
		this.lose++;
	}

}

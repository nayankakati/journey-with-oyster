package com.oyster.domain;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class Card {
	private Integer number;
	private double balance;

	public Card(Integer number, double balance) {
		this.number = number;
		this.balance = balance;
	}

	public Integer getNumber() {
		return number;
	}

	public Double getBalance() {
		return balance;
	}

	public Double addToCard(double amount) {
		return balance += amount;
	}

	public Double deductFromCard(double amount) {
		return balance -= amount;
	}
}

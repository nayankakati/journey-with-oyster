package com.oyster.exception;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String errorMessage) {
		super(errorMessage);
	}
}

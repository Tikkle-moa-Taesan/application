package com.ssafy.TmT.exception;

public class BudgetNotFoundException extends RuntimeException {

	public BudgetNotFoundException(String text) {
		super(text);
	}
}

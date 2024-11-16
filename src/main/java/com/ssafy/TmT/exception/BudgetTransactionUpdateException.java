package com.ssafy.TmT.exception;

public class BudgetTransactionUpdateException extends RuntimeException{

	public BudgetTransactionUpdateException(String string, Exception e) {
		super(string,e);
	}
	

}

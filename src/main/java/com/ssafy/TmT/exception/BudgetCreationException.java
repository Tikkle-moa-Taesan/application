package com.ssafy.TmT.exception;

import java.sql.SQLException;

public class BudgetCreationException extends RuntimeException{

	public BudgetCreationException(String string,  Exception e) {
		super(string, e);
	}

}

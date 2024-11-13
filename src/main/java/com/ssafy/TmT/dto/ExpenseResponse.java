package com.ssafy.TmT.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseResponse {

	private ExpenseDTO expense;
	
	private BudgetRateDTO budgetRate;
}

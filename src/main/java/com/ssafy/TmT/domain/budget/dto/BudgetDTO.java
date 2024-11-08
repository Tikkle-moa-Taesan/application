package com.ssafy.TmT.domain.budget.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetDTO {

	private Long monthBudget;
	private Long monthExpense;
	private Long expensePercent;
}

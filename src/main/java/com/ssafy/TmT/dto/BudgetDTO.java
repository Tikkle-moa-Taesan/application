package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetDTO {

	// 이번 달 예산
	private Long monthBudget;
	
	// 이번 달 지출
	private Long monthExpense;
	
	// 예산 대비 지출
	private Long expensePercent;
}

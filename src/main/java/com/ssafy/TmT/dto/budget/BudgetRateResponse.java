package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BudgetRateResponse {

//	// 이번 달 예산
//	// 이번 달 지출
//	private BudgetRateDTO budgetRate;
	
	private Long thisMonthExpense;
	
	private Long thisMonthBudget;
	
	// 예산 대비 지출
	private Float rate;
}

package com.ssafy.TmT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BudgetRateDTO {

	// 이번 달 예산
	private Long monthBudget;
	
	// 이번 달 지출
	private Long thisMonthExpense;
}

package com.ssafy.TmT.dto.budget;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetRateDTO {

	// 이번 달 예산
	private Long monthBudget;
	
	// 이번 달 지출
	private Long monthExpense;
}

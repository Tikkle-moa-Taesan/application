package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BudgetProfileResponse {


	// 버젯아이디
	private Long budgetId;
	
	// 지출
	private Long monthExpense;
	
	// 수입
	private Long monthIncome;
}

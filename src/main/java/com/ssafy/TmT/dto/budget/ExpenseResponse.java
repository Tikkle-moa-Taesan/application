package com.ssafy.TmT.dto.budget;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseResponse {

	// 지난 달 지출
	private Long lastMonthExpense;
	
	// 지난 주 지출
	private Long lastWeekExpense;
	
	// 이번 주 지출
	private Long thisWeekExpense;

	// 이번 달 지출
	private Long thisMonthExpense;
	
	// 카테고리별 지출
	private CategoryExpenseDTO categoryExpense;
	
}

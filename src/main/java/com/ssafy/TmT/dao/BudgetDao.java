package com.ssafy.TmT.dao;

import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;

public interface BudgetDao {


	// 예산 : 지출 비율
//	BudgetRateDTO findBudgetRate(Long memberId);

	CategoryExpenseDTO findCategoryExpense(Long memberId);

	Long calculateLastMonthExpense(Long memberId);

	Long calculateLastWeekExpense(Long memberId);
	
	Long calculateThisWeekExpense(Long memberId);

	Long calculateThisMonthExpense(Long memberId);

	Long findBudget(Long memberId);

}

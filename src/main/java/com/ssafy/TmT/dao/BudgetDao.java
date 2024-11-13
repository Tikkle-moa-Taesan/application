package com.ssafy.TmT.dao;

import com.ssafy.TmT.dto.BudgetRateDTO;
import com.ssafy.TmT.dto.Category;
import com.ssafy.TmT.dto.ExpenseDTO;
import com.ssafy.TmT.dto.StatisticsDTO;

public interface BudgetDao {

	// 지출 통계
	StatisticsDTO getExpenseStatistics(Long memberId);

	// 예산 : 지출 비율
	BudgetRateDTO findBudgetRate(Long memberId);

	Category getCategoryExpense(Long memberId);

}

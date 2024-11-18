package com.ssafy.TmT.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetDTO;
import com.ssafy.TmT.dto.budget.UpdateBudgetTransactionsDTO;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;

public interface BudgetDao {
	// 예산 : 지출 비율
//	BudgetRateDTO findBudgetRate(Long memberId);


	// 작동함
	void createBudget(CreateBudgetDTO createBudgetDTO);

	// 작동모름
	CategoryExpenseDTO findCategoryExpense(Long budgetId);
	
	// 작동함
	void updateBudgetTransaction(UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO);

	Long getCurrentBudgetId(Long memberId);	// 이번달 버젯 찾기
	
	Long getPreviousBudgetId(Long memberId);	// 지난달 버젯 찾기

	Long calculateMonthExpense(Long currentBudgetId);	// 이번 달 가계부 지출

	Long calculateWeekExpense(WeekExpenseDTO weekExpenseDTO);
	
	CategoryExpenseDTO findCategoryBudget(Long budgetId);

	Long findBudget(Long budgetId);

	List<BudgetTransactionDTO> findBudgetTransactions(@Param("budgetId") Long budgetId, @Param("offset") int offset);

	Long findBudgetByDate(@Param("year") String year, @Param("month") String month);

	Long calculateMonthIncome(Long budgetId);

}

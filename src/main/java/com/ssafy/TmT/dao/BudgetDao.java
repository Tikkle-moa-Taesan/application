package com.ssafy.TmT.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.ssafy.TmT.dto.budget.BudgetCategoryRequest;
import com.ssafy.TmT.dto.budget.BudgetCategoryResponse;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetDTO;
import com.ssafy.TmT.dto.budget.UpdateBudgetRequest;
import com.ssafy.TmT.dto.budget.UpdateBudgetTransactionsDTO;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;

public interface BudgetDao {
	// 예산 : 지출 비율
//	BudgetRateDTO findBudgetRate(Long memberId);


	// 작동함
	int createBudget(CreateBudgetDTO createBudgetDTO);

	// 작동모름
	CategoryExpenseDTO findCategoryExpense(Long budgetId);
	
	// 작동함
	void updateBudgetTransaction(UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO);

	Optional<Long> getCurrentBudgetId(Long memberId);	// 이번달 버젯 찾기
	
	Optional<Long> getPreviousBudgetId(@Param("memberId") Long memberId, @Param("monthsAgo") int monthsAgo);	// 지난달 버젯 찾기

	Long calculateMonthExpense(Long currentBudgetId);	// 이번 달 가계부 지출

	Long calculateWeekExpense(WeekExpenseDTO weekExpenseDTO);
	
	BudgetCategoryResponse findCategoryBudget(Long budgetId);

	Optional<Long> findBudget(Long budgetId);

	List<BudgetTransactionDTO> findBudgetTransactions(@Param("budgetId") Long budgetId, @Param("offset") int offset);

	List<BudgetTransactionDTO> findAllBudgetTransactions(Long budgetId);

	Optional<Long> findBudgetByDate(@Param("memberId") Long memberId, @Param("year") String year, @Param("month") String month);

	Long calculateMonthIncome(Long budgetId);

	int modifyCategoryBudget(@Param("memberId") Long memberId, BudgetCategoryRequest request);

	void createBudgetForMonth(CreateBudgetDTO createBudgetDTO);

	int modifyBudget(@Param("budgetId") Long budgetId, @Param("budgetAmount") Long budgetAmount);

//	boolean existsBudgetForMonth(Long memberId, YearMonth yearMonth);

//	Optional<Long> findBudgetIdByDate(Long memberId, LocalDateTime transactionDatetime);

//	void insertBudgetTransaction(UpdateBudgetTransactionsDTO dto);

//	List<BudgetTransactionDTO> findTransactionsWithinPeriod(Long memberId, LocalDateTime startDate,
//			LocalDateTime endDate);

}

package com.ssafy.TmT.dao;


import com.ssafy.TmT.dto.Category;
import com.ssafy.TmT.dto.Profile;

public interface MemberDao {

	void regist(String subject);

	Profile login(String subject);

	
	// getTotalBalance
//	Long getFreeAccountBalanceSum(Long memberId);

//	Long getSavingsAccountBalanceSum(Long memberId);
	
	
	// getExpenseStatistics
//	Long getTotalBalance(Long memberId);

	Long getLastMonthExpense(Long memberId);

	Long getThisMonthExpense(Long memberId);

	Long getThisWeekExpense(Long memberId);

	Long getLastWeekExpense(Long memberId);

	Category getExpenseWithCategory(Long memberId);

	Long getThisMonthBudget(Long memberId);


}

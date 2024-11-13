package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dto.BudgetRateDTO;
import com.ssafy.TmT.dto.Category;
import com.ssafy.TmT.dto.ExpenseDTO;
import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.dto.StatisticsDTO;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

	private final BudgetDao budgetDao;
	private final JwtUtil jwtUtil;

	public ExpenseResponse calculateExpenseAndBudget(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		Category categoryExpense = budgetDao.getCategoryExpense(memberId);
		StatisticsDTO expenseStatistics = budgetDao.getExpenseStatistics(memberId);
		ExpenseDTO expenseDTO = new ExpenseDTO(categoryExpense,expenseStatistics);
		BudgetRateDTO budget = findBudgetRate(memberId);
		ExpenseResponse response = new ExpenseResponse(expenseDTO, budget);
		return response;
	}

	// 여기서 알아내고 싶은 것 :  1. 이번달 예산. 2. 내가 이번달 쓴 돈
	private BudgetRateDTO findBudgetRate(Long memberId) {
		BudgetRateDTO response = budgetDao.findBudgetRate(memberId);
		return response;
	}
	
	// 여기서 알아내고 싶은 것 :  1. 이번달 예산. 2. 내가 이번달 쓴 돈
	public BudgetRateDTO findBudgetRate(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		BudgetRateDTO response = budgetDao.findBudgetRate(memberId);
		return response;
	}
	
	
}

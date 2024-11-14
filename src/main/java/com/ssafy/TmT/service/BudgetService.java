package com.ssafy.TmT.service;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dto.budget.BudgetRateDTO;
import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

	private final BudgetDao budgetDao;
//	private final JwtUtil jwtUtil;

	// 지출 통계 조회
	public ExpenseResponse calculateExpenseAndBudget() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 이 메서드를 쪼개자.

		// 1. 지난 달에 얼마 썼는지 알아오기
		Long lastMonthExpense = calculateLastMonthExpense(memberId);

		// 2. 이번 달에 얼마 썼는지 알아오기
		Long thisMonthExpense = calculateThisMonthExpense(memberId);

		// 3. 이번 달 카테고리별 예산 조회하기
		CategoryExpenseDTO categoryExpense = budgetDao.findCategoryExpense(memberId);

		// 4. 지난 주에 얼마 썼는지 알아오기
		Long lastWeekExpense = calculateLastWeekExpense(memberId);

		// 5. 이번 주에 얼마 썼는지 알아오기
		Long thisWeekExpense = calculateThisWeekExpense(memberId);

		ExpenseResponse response = new ExpenseResponse(lastMonthExpense, lastWeekExpense, thisWeekExpense,
				thisMonthExpense, categoryExpense);
		return response;
	}

	// 여기서 알아내고 싶은 것 : 1. 이번달 예산. 2. 내가 이번달 쓴 돈
	public BudgetRateResponse findBudgetRate() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long thisMonthExpense = calculateThisMonthExpense(memberId);
		
		Long thisMonthBudget = budgetDao.findBudget(memberId);
		Float rate = ((float) 100 * Math.abs(thisMonthExpense) / thisMonthBudget);

		BudgetRateResponse response = new BudgetRateResponse(thisMonthExpense, thisMonthBudget, rate);
		return response;
	}

	public Long calculateThisWeekExpense(Long memberId) {
		return budgetDao.calculateThisWeekExpense(memberId);
	}

	private Long calculateThisMonthExpense(Long memberId) {
		return budgetDao.calculateThisMonthExpense(memberId);
	}

	public Long calculateLastMonthExpense(Long memberId) {
		return budgetDao.calculateLastMonthExpense(memberId);
	}

	public Long calculateLastWeekExpense(Long memberId) {
		return budgetDao.calculateLastWeekExpense(memberId);
	}

}

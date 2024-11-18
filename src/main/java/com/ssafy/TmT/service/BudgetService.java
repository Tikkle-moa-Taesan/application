package com.ssafy.TmT.service;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetRequest;
import com.ssafy.TmT.dto.budget.CreateBudgetResponse;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.UpdateBudgetTransactionsDTO;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.exception.BudgetCreationException;
import com.ssafy.TmT.exception.BudgetNotFoundException;
import com.ssafy.TmT.exception.BudgetTransactionUpdateException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

	private final BudgetDao budgetDao;

	// 메서드 분리 끝. 가계부 생성은 성공적으로 되나, updateBudgetTransaction 이 안됨.
	// 역할 : 가계부 생성
	// 역할: 가계부 생성
	public CreateBudgetResponse createBudget(CreateBudgetRequest request) {
		Long memberId = getAuthenticatedMemberId();

		try {
			// CreateBudgetDTO 생성 및 DAO 호출
			CreateBudgetDTO createBudgetDTO = new CreateBudgetDTO(memberId, request.getMonthBudget());
			budgetDao.createBudget(createBudgetDTO);

			// 생성된 가계부 ID 조회
			Long budgetId = budgetDao.getCurrentBudgetId(memberId).orElseThrow(() -> new BudgetNotFoundException("이번 달 가계부가 없습니다."));

			// 응답 생성 및 반환
			return new CreateBudgetResponse(budgetId);
		} catch (Exception e) {
			throw new BudgetCreationException("Budget creation failed for memberId: " + memberId, e);
		}
	}

	// 지출 통계 조회
	public ExpenseResponse calculateExpenseAndBudget() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentBudgetId = getCurrentBudgetId(memberId);
		Long lastBudgetId = getPreviousBudgetId(memberId);
		System.out.println("currentBudgetId : " + currentBudgetId);
		System.out.println("lastBudgetId: " + lastBudgetId);
		// 이 메서드를 쪼개자.

		// 1. 지난 달에 얼마 썼는지 알아오기
		Long lastMonthExpense = calculateLastMonthExpense(lastBudgetId);
		System.out.println("지난달 통계 : " + lastMonthExpense);

		// 2. 이번 달에 얼마 썼는지 알아오기
		Long thisMonthExpense = calculateThisMonthExpense(currentBudgetId);
		System.out.println("이번 달 합 : " + thisMonthExpense);

		// 3. 이번 달 카테고리별 예산 조회하기
		CategoryExpenseDTO categoryExpense = budgetDao.findCategoryExpense(currentBudgetId);

		System.out.println("여기까지 완료. 카테고리 이상 무.");
		// 4. 지난 주에 얼마 썼는지 알아오기
		Long lastWeekExpense = calculateLastWeekExpense(currentBudgetId);

		// 5. 이번 주에 얼마 썼는지 알아오기
		Long thisWeekExpense = calculateThisWeekExpense(currentBudgetId);
		System.out.println("이번 주 합 : " + thisWeekExpense);

		ExpenseResponse response = new ExpenseResponse(lastMonthExpense, lastWeekExpense, thisWeekExpense,
				thisMonthExpense, categoryExpense);
		return response;
	}

	private Long getPreviousBudgetId(Long memberId) {
		return budgetDao.getPreviousBudgetId(memberId);
	}

	private Long getCurrentBudgetId(Long memberId) {
		return budgetDao.getCurrentBudgetId(memberId).orElseThrow(() -> new BudgetNotFoundException("이번 달 가계부가 없습니다."));
	}

	// 여기서 알아내고 싶은 것 : 1. 이번달 예산. 2. 내가 이번달 쓴 돈
	public BudgetRateResponse findBudgetRate() {
		Long currentBudgetId = getCurrentBudgetId(getAuthenticatedMemberId());
		Long thisMonthExpense = calculateThisMonthExpense(currentBudgetId);

		Long thisMonthBudget = budgetDao.findBudget(currentBudgetId);
		Float rate = ((float) 100 * Math.abs(thisMonthExpense) / thisMonthBudget);

		BudgetRateResponse response = new BudgetRateResponse(thisMonthExpense, thisMonthBudget, rate);
		return response;
	}

	private Long calculateThisWeekExpense(Long currentBudgetId) {
		return budgetDao.calculateWeekExpense(new WeekExpenseDTO(currentBudgetId, 0)); // 이번 주 데이터
	}

	private Long calculateThisMonthExpense(Long currentBudgetId) {
		return budgetDao.calculateMonthExpense(currentBudgetId);
	}

	// 지난달 지출 조회 ( 값이 없으면 0 )
	private Long calculateLastMonthExpense(Long lastBudgetId) {
		if (lastBudgetId == null) {
			return 0L; // 지난 Budget이 없으면 0 반환
		}
		return budgetDao.calculateMonthExpense(lastBudgetId);
	}

	private Long calculateLastWeekExpense(Long currentBudgetId) {
		return budgetDao.calculateWeekExpense(new WeekExpenseDTO(currentBudgetId, -1)); // 지난 주 데이터
	}

	private Long getAuthenticatedMemberId() {
		return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	// 컨트롤러 요청 처리
	public void updateBudgetTransactions() {
		Long memberId = getAuthenticatedMemberId();
		Long budgetId = budgetDao.getCurrentBudgetId(memberId).orElseThrow(() -> new BudgetNotFoundException("이번 달 가계부가 없습니다.")); // 음... 이 부분을 예외 처리 해달라고 함.
		updateBudgetTransactions(memberId, budgetId);
	}

	// 이번 달 예산 업데이트
	private void updateBudgetTransactions(Long memberId, Long budgetId) {
		System.out.println("이번 달 예산 업데이트하기");

		UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO = new UpdateBudgetTransactionsDTO(memberId, budgetId);
		try {
			System.out.println("업데이트 실행");
			System.out.println("memberId : " + updateBudgetTransactionsDTO.getMemberId());
			System.out.println("budgetId : " + updateBudgetTransactionsDTO.getBudgetId());
			budgetDao.updateBudgetTransaction(updateBudgetTransactionsDTO);
			System.out.println("업데이트 성공");
		} catch (Exception e) {
			throw new BudgetTransactionUpdateException("Failed to update transactions for budgetId: " + budgetId, e);
		}
	}

}

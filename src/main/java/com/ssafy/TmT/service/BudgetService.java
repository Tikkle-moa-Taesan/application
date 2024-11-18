package com.ssafy.TmT.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.controller.AccountController;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.budget.BudgetDetailResponse;
import com.ssafy.TmT.dto.budget.BudgetProfileResponse;
import com.ssafy.TmT.dto.budget.BudgetRateDTO;
import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetRequest;
import com.ssafy.TmT.dto.budget.CreateBudgetResponse;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.UpdateBudgetTransactionsDTO;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;
import com.ssafy.TmT.entity.Budget;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BudgetService {

	private final BudgetDao budgetDao;

	// 메서드 분리 끝. 가계부 생성은 성공적으로 되나, updateBudgetTransaction 이 안됨.
	// 역할 : 가계부 생성
	// 역할: 가계부 생성
	public CreateBudgetResponse createBudget(CreateBudgetRequest request) {
		Long memberId = getAuthenticatedMemberId();

		// CreateBudgetDTO 생성 및 DAO 호출
		CreateBudgetDTO createBudgetDTO = new CreateBudgetDTO(memberId, request.getMonthBudget());
		budgetDao.createBudget(createBudgetDTO);

		// 생성된 가계부 ID 조회
		Long budgetId = budgetDao.getCurrentBudgetId(memberId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

		// 응답 생성 및 반환
		return new CreateBudgetResponse(budgetId);
	}

	// 지출 통계 조회
	public ExpenseResponse calculateExpenseAndBudget() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentBudgetId = budgetDao.getCurrentBudgetId(memberId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
		Long lastBudgetId = budgetDao.getPreviousBudgetId(memberId)
				.orElse(0L);

		System.out.println("currentBudgetId : " + currentBudgetId);
		System.out.println("lastBudgetId: " + lastBudgetId);
		// 이 메서드를 쪼개자.

		// 1. 지난 달에 얼마 썼는지 알아오기
		Long lastMonthExpense = budgetDao.calculateMonthExpense(lastBudgetId);

		System.out.println("지난달 통계 : " + lastMonthExpense);

		// 2. 이번 달에 얼마 썼는지 알아오기
		Long thisMonthExpense = budgetDao.calculateMonthExpense(currentBudgetId);

//				calculateThisMonthExpense(currentBudgetId);
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

	private Long getCurrentBudgetId(Long memberId) {
		return budgetDao.getCurrentBudgetId(memberId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
	}

	// 여기서 알아내고 싶은 것 : 1. 이번달 예산. 2. 내가 이번달 쓴 돈
	public BudgetRateResponse findBudgetRate() {
		Long currentBudgetId = getCurrentBudgetId(getAuthenticatedMemberId());
		Long thisMonthExpense = budgetDao.calculateMonthExpense(currentBudgetId);
//				calculateThisMonthExpense(currentBudgetId);

		Long thisMonthBudget = budgetDao.findBudget(currentBudgetId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
		Float rate = ((float) 100 * Math.abs(thisMonthExpense) / thisMonthBudget);

		BudgetRateResponse response = new BudgetRateResponse(thisMonthExpense, thisMonthBudget, rate);
		return response;
	}

	private Long calculateThisWeekExpense(Long currentBudgetId) {
		return budgetDao.calculateWeekExpense(new WeekExpenseDTO(currentBudgetId, 0)); // 이번 주 데이터
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
		Long budgetId = budgetDao.getCurrentBudgetId(memberId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND)); // 음... 이 부분을 예외 처리 해달라고 함.
		updateBudgetTransactions(memberId, budgetId);
	}

	// 이번 달 예산 업데이트
	private void updateBudgetTransactions(Long memberId, Long budgetId) {
		log.info("서비스 : updateBudgetTransactions() memberId : " + memberId + " , budgetId : " + budgetId);
		UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO = new UpdateBudgetTransactionsDTO(memberId, budgetId);
		System.out.println("업데이트 실행");
		System.out.println("memberId : " + updateBudgetTransactionsDTO.getMemberId());
		System.out.println("budgetId : " + updateBudgetTransactionsDTO.getBudgetId());
		int result = budgetDao.updateBudgetTransaction(updateBudgetTransactionsDTO);
		if (result == 0) throw new CustomException(ErrorCode.BUDGET_TRANSACTION_UPDATE_FAILED);
		System.out.println("업데이트 성공");
	}

	public BudgetDetailResponse findBudgetTransactions(Long budgetId, int page) {
		int offset = page * 20;
		List<BudgetTransactionDTO> transactions = budgetDao.findBudgetTransactions(budgetId, offset);
		BudgetDetailResponse response = new BudgetDetailResponse(transactions);
		return response;
	}

	public BudgetProfileResponse findBudgetByDate(Integer date) {
		String year = String.valueOf(date).substring(0, 4);
		String month = String.valueOf(date).substring(4, 6);

		Long budgetId = budgetDao.findBudgetByDate(year, month).orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
		Long monthExpense = budgetDao.calculateMonthExpense(budgetId);
		Long monthIncome = budgetDao.calculateMonthIncome(budgetId);

		BudgetProfileResponse response = new BudgetProfileResponse(budgetId, monthExpense, monthIncome);

		return response;
	}

}

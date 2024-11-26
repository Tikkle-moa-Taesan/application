package com.ssafy.TmT.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.controller.impl.AccountControllerImpl;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.budget.BudgetCategoryRequest;
import com.ssafy.TmT.dto.budget.BudgetCategoryResponse;
import com.ssafy.TmT.dto.budget.BudgetDetailResponse;
import com.ssafy.TmT.dto.budget.BudgetProfileResponse;
import com.ssafy.TmT.dto.budget.BudgetRateDTO;
import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetDTO;
import com.ssafy.TmT.dto.budget.CreateBudgetRequest;
import com.ssafy.TmT.dto.budget.CreateBudgetResponse;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.GraphResponse;
import com.ssafy.TmT.dto.budget.UpdateBudgetTransactionsDTO;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.SecurityUtil;

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
		Long lastBudgetId = budgetDao.getPreviousBudgetId(memberId, 1).orElse(0L);

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

	private BudgetRateResponse findBudgetRate(Long currentBudgetId) {
		Long thisMonthExpense = budgetDao.calculateMonthExpense(currentBudgetId);

		Long thisMonthBudget = budgetDao.findBudget(currentBudgetId).orElse(0L);
		BudgetRateResponse response = new BudgetRateResponse(thisMonthExpense, thisMonthBudget, 0f);
		return response;
	}

	private Long calculateThisWeekExpense(Long currentBudgetId) {
		return budgetDao.calculateWeekExpense(new WeekExpenseDTO(currentBudgetId, 0)); // 이번 주 데이터
	}

	private Long calculateLastWeekExpense(Long currentBudgetId) {
		return budgetDao.calculateWeekExpense(new WeekExpenseDTO(currentBudgetId, -1)); // 지난 주 데이터
	}

	private Long getAuthenticatedMemberId() {
		return SecurityUtil.getAuthenticatedMemberId();
	}

	// 컨트롤러 요청 처리
//	public void updateBudgetTransactions() {
//		Long memberId = getAuthenticatedMemberId();
//		Long budgetId = budgetDao.getCurrentBudgetId(memberId)
//				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
//
//		UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO = new UpdateBudgetTransactionsDTO(memberId, budgetId);
//		budgetDao.updateBudgetTransaction(updateBudgetTransactionsDTO);
//	}

	@Transactional
	public void updateBudgetTransactions() {
	    Long memberId = getAuthenticatedMemberId();

	    // 최근 6개월 동안의 Budget 데이터 생성 및 BudgetTransaction 업데이트
	    for (int i = 0; i < 6; i++) {
	        final int monthOffset = i;

	        // 해당 월의 Budget ID 조회 또는 생성
	        Long budgetId = budgetDao.getPreviousBudgetId(memberId, monthOffset)
	                .orElseGet(() -> createBudgetForMonth(memberId, monthOffset));

	        // 해당 월의 시작 날짜 계산
	        LocalDate createdMonth = LocalDate.now().minusMonths(monthOffset).withDayOfMonth(1);

	        // BudgetTransaction 업데이트
	        UpdateBudgetTransactionsDTO updateBudgetTransactionsDTO = new UpdateBudgetTransactionsDTO(
	            memberId, budgetId, createdMonth
	        );
	        budgetDao.updateBudgetTransaction(updateBudgetTransactionsDTO);
	    }
	}

	private Long createBudgetForMonth(Long memberId, int monthsAgo) {
		// 해당 월의 첫째 날 계산
		LocalDate firstDayOfMonth = LocalDate.now().minusMonths(monthsAgo).withDayOfMonth(1);
		CreateBudgetDTO createBudgetDTO = new CreateBudgetDTO(memberId, 0L); // 초기 예산은 0으로 설정
		createBudgetDTO.setCreatedAt(firstDayOfMonth.atStartOfDay()); // 생성 날짜 설정
		budgetDao.createBudgetForMonth(createBudgetDTO);

		// 새로 생성된 Budget ID 반환
		return budgetDao.getPreviousBudgetId(memberId, monthsAgo)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_CREATION_FAILED));
	}

	public BudgetDetailResponse findBudgetTransactions(Long budgetId, int page) {
		int offset = page * 20;
		List<BudgetTransactionDTO> transactions = budgetDao.findBudgetTransactions(budgetId, offset);
		BudgetDetailResponse response = new BudgetDetailResponse(transactions);
		return response;
	}

	public BudgetDetailResponse findBudgetTransactions(Long budgetId) {
		List<BudgetTransactionDTO> transactions = budgetDao.findAllBudgetTransactions(budgetId);
		BudgetDetailResponse response = new BudgetDetailResponse(transactions);
		return response;
	}

	public BudgetProfileResponse findBudgetByDate(Integer date) {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		String year = String.valueOf(date).substring(0, 4);
		String month = String.valueOf(date).substring(4, 6);

		Long budgetId = budgetDao.findBudgetByDate(memberId, year, month)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));
		Long monthExpense = budgetDao.calculateMonthExpense(budgetId);
		Long monthIncome = budgetDao.calculateMonthIncome(budgetId);

		BudgetProfileResponse response = new BudgetProfileResponse(budgetId, monthExpense, monthIncome);

		return response;
	}

	@Transactional
	public BudgetCategoryResponse modifyCategoryBudget(BudgetCategoryRequest request) {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		// 예산 수정
		int rowsUpdated = budgetDao.modifyCategoryBudget(memberId, request);
		if (rowsUpdated == 0) {
			throw new CustomException(ErrorCode.BUDGET_UPDATE_FAILURE);
		}

		// 수정된 budgetId 가져오기
		Long budgetId = budgetDao.getCurrentBudgetId(memberId)
				.orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

		// 응답 생성
		return budgetDao.findCategoryBudget(budgetId);

	}

	public BudgetCategoryResponse findCategoryBudget() {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		Long budgetId = getCurrentBudgetId(memberId);
		return budgetDao.findCategoryBudget(budgetId);
	}

	public GraphResponse findRecentStatistics() {
		log.info("서비스 : 최근 6개월간 정보 조회");
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		// 지금 가계부

		Long budgetId = budgetDao.getPreviousBudgetId(memberId, 0).orElse(0L);
		BudgetRateResponse thisMonthRate = findBudgetRate(budgetId);
		Long oneMonthBeforeBudgetId = budgetDao.getPreviousBudgetId(memberId, 1).orElse(0L);
		BudgetRateResponse oneMonthBeforeRate = findBudgetRate(oneMonthBeforeBudgetId);
		Long twoMonthBeforeBudgetId = budgetDao.getPreviousBudgetId(memberId, 2).orElse(0L);
		BudgetRateResponse twoMonthBeforeRate = findBudgetRate(twoMonthBeforeBudgetId);
		Long threeMonthBeforeBudgetId = budgetDao.getPreviousBudgetId(memberId, 3).orElse(0L);
		BudgetRateResponse threeMonthBeforeRate = findBudgetRate(threeMonthBeforeBudgetId);
		Long fourMonthBeforeBudgetId = budgetDao.getPreviousBudgetId(memberId, 4).orElse(0L);
		BudgetRateResponse fourMonthBeforeRate = findBudgetRate(fourMonthBeforeBudgetId);
		Long fiveMonthBeforeBudgetId = budgetDao.getPreviousBudgetId(memberId, 5).orElse(0L);
		BudgetRateResponse fiveMonthBeforeRate = findBudgetRate(fiveMonthBeforeBudgetId);

		GraphResponse response = new GraphResponse(thisMonthRate, oneMonthBeforeRate, twoMonthBeforeRate,
				threeMonthBeforeRate, fourMonthBeforeRate, fiveMonthBeforeRate);

		return response;
	}
}

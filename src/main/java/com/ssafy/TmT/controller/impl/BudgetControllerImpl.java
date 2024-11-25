package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.BudgetController;
import com.ssafy.TmT.dto.budget.BudgetCategoryRequest;
import com.ssafy.TmT.dto.budget.BudgetCategoryResponse;
import com.ssafy.TmT.dto.budget.BudgetDetailResponse;
import com.ssafy.TmT.dto.budget.BudgetProfileResponse;
import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CreateBudgetRequest;
import com.ssafy.TmT.dto.budget.CreateBudgetResponse;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.GraphResponse;
import com.ssafy.TmT.service.BudgetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BudgetControllerImpl implements BudgetController{
	
	private final BudgetService budgetService;
	
	@Override
	public ResponseEntity<CreateBudgetResponse> createBudget(@RequestBody CreateBudgetRequest request) {
		log.info("컨트롤러 : 가계부 생성하기");
		CreateBudgetResponse response = budgetService.createBudget(request);
		log.info("성공 끝 : 가계부 아이디 : " + response.getBudgetId());
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<String> downloadTransactions() {
		budgetService.updateBudgetTransactions();
        return ResponseEntity.ok("업데이트 완료");
	}

	@Override
	public ResponseEntity<ExpenseResponse> calculateExpenseSummary() {
		log.info("컨트롤러 : 지출 통계 조회");
		ExpenseResponse response = budgetService.calculateExpenseAndBudget();
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BudgetRateResponse> totalBudget() {
		log.info("예산 통계 가져오기");
		BudgetRateResponse response = budgetService.findBudgetRate();
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BudgetDetailResponse> findBudgetTransactions(@PathVariable Long budgetId, @RequestParam(defaultValue = "0") int page) {
		log.info("컨트롤러 : 해당 가계부 거래내역 페이징해서 불러오기");
		BudgetDetailResponse response = budgetService.findBudgetTransactions(budgetId, page);	
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BudgetProfileResponse> getBudgetProfile(@PathVariable Integer date) {
		log.info("컨트롤러 : 해당 가계부 중요 내용 불러오기");
		BudgetProfileResponse response = budgetService.findBudgetByDate(date);	
		return ResponseEntity.ok(response);
	}

 
	@Override
	public ResponseEntity<BudgetCategoryResponse> modifyCategoryBudget(@RequestBody BudgetCategoryRequest request) {
		log.info("컨트롤러 : 이번달 가계부 예산 카테고리별로 설정하기");
		BudgetCategoryResponse response = budgetService.modifyCategoryBudget(request);
		return ResponseEntity.ok(response);
	}


	@Override
	public ResponseEntity<BudgetDetailResponse> findAllBudgetTransactions(Long budgetId) {
		log.info("컨트롤러 : 해당 가계부 거래내역 전부 불러오기");
		BudgetDetailResponse response = budgetService.findBudgetTransactions(budgetId);	
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<BudgetCategoryResponse> findCategoryBudget() {
		log.info("컨트롤러 : 카테고리별 예산 조회");
		BudgetCategoryResponse response = budgetService.findCategoryBudget();
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<GraphResponse> findGraphExpense() {
		log.info("컨트롤러 : 최근 6개월 정보 조회");
		GraphResponse response = budgetService.findRecentStatistics();
		return ResponseEntity.ok(response);
	}
	
	
}

package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.AccountResponse;
import com.ssafy.TmT.dto.BudgetDTO;
import com.ssafy.TmT.dto.BudgetRateDTO;
import com.ssafy.TmT.dto.ExpenseDTO;
import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.service.AccountService;
import com.ssafy.TmT.service.BudgetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/budget")
public class BudgetController {
	
	private final BudgetService budgetService;
	
	
	// 지출 통계
	// 4번 api. 지출 통계 조회
	@GetMapping("/expense")
	@Operation(summary = "4. 지출 통계 조회", description = "JWT를 이용해 지출 통계를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<ExpenseResponse> calculateExpenseSummary() {
		log.info("컨트롤러 : 지출 통계 조회");
		// 이 안에 내부적으로 예산통계를 집어넣는게 좋을것같음.
		ExpenseResponse response = budgetService.calculateExpenseAndBudget();
		return ResponseEntity.ok(response);
	}
	
	// 5번 api. 예산 통계 : 이번달, 저번달, 이번주, 저번주 예산 가져옴
	@GetMapping("/rate")
	@Operation(summary = "5. 예산 통계 조회", description = "예산과 예산대비 몇프로 썼는지 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<BudgetRateDTO> totalBudget() {
		System.out.println("예산 통계 가져오기");
		BudgetRateDTO response = budgetService.findBudgetRate();
		return ResponseEntity.ok(response);
	}
	
}

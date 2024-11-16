package com.ssafy.TmT.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.budget.BudgetRateResponse;
import com.ssafy.TmT.dto.budget.CreateBudgetRequest;
import com.ssafy.TmT.dto.budget.CreateBudgetResponse;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
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
	
	
	// 가계부 만드는 api
	// 11.16 생성 확인.
	@PostMapping("/create")	
	@Operation(summary = "8. 가계부 생성하기", description = "입력받은 예산을 통해 가계부 생성")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<CreateBudgetResponse> createBudget(@RequestBody CreateBudgetRequest request) {
		System.out.println("컨트롤러 : 가계부 생성하기");
		CreateBudgetResponse response = budgetService.createBudget(request);
		System.out.println("성공 끝 : 가계부 아이디 : " + response.getBudgetId());
		return ResponseEntity.ok(response);
	}
	
	//
	
	// 11. 16 다운로드 되는거 확인. 이번달것만 됨.
	@PostMapping("/download")
	public ResponseEntity<String> downloadTransactions() {
	    System.out.println("최신 거래내역 다운로드하기");
	    try {
	        budgetService.updateBudgetTransactions();
	        return ResponseEntity.ok("Transactions updated successfully");
	    } catch (Exception e) {
	        System.err.println("거래 내역 업데이트 중 에러 발생: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update transactions");
	    }
	}

	
	// 지출 통계
	// 4번 api. 지출 통계 조회 완성. 24.11.14	11.16 정상화는 시켰음. 이번달 데이터 넣었을 때 작동하는지 확인필요.
	// 11.15 : 이거 TransactionBudget 에서 가져와야 함.
	// 11.16 정상화 해 줬자나~ (작동함)
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
	// 작동함.
	// 완성. 24.11.14. 점검 : 11.16
	@GetMapping("/rate")
	@Operation(summary = "5. 예산 통계 조회", description = "예산과 예산대비 몇프로 썼는지 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<BudgetRateResponse> totalBudget() {
		System.out.println("예산 통계 가져오기");
		BudgetRateResponse response = budgetService.findBudgetRate();
		return ResponseEntity.ok(response);
	}
	
}

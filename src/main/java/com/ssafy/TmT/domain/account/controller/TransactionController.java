package com.ssafy.TmT.domain.account.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.domain.account.dto.ExpenseResponse;
import com.ssafy.TmT.domain.account.dto.TransactionResponse;
import com.ssafy.TmT.domain.account.service.AccountService;
import com.ssafy.TmT.domain.account.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

	// searchTransactionsByCategory
	private final TransactionService transactionService;
	
	// 4번 api. 지출 통계
	@GetMapping("/expense")
	public ResponseEntity<ExpenseResponse> getMonthlyExpenses(HttpHeaders headers) {
		log.info("컨트롤러 : 지출 통계");
		return ResponseEntity.ok(accountService.getMonthlyExpenses(headers));
	}	
	
	// 자산 페이지 - 특정 계좌 클릭 시
	@GetMapping("/{accountId}")
	public ResponseEntity<List<TransactionResponse>> findTransactions(@PathVariable Long accountId) {
		List<TransactionResponse> response = transactionService.findTransactionsByAccountId(accountId);	
		return ResponseEntity.ok(response);
	}
	
	//
	@GetMapping("/{transactionId}")
	public ResponseEntity<AccountResponse> 상세계좌조회(HttpHeaders headers) {
		log.info("컨트롤러 : 상세 계좌 조회");
		return ResponseEntity.ok(accountService.findAccountById(headers));
	}
	
	// 자유 입출금 계좌 조회
	// 적금 계좌 조회
	@GetMapping("/accounts/{type}")
	public ResponseEntity<List<AccountResponse>> findSavingsAccounts(@PathVariable String type, HttpHeaders headers) {
		List<AccountResponse> response = accountService.findAccountsByType(type, headers);
		return ResponseEntity.ok(response);
	}
	
}

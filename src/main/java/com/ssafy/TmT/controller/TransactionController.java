package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.AccountResponse;
import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.dto.TransactionDTO;
import com.ssafy.TmT.service.AccountService;
import com.ssafy.TmT.service.TransactionService;

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
		return ResponseEntity.ok(transactionService.getMonthlyExpenses(headers));
	}	
	
	//
	@GetMapping("/{transactionId}")
	public ResponseEntity<AccountResponse> 상세계좌조회(HttpHeaders headers) {
		log.info("컨트롤러 : 상세 계좌 조회");
		return ResponseEntity.ok(transactionService.findAccountById(headers));
	}
	
}

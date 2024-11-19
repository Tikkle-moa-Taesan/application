package com.ssafy.TmT.controller.impl;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.AccountController;
import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailResponse;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.dto.search.SearchCondition;
import com.ssafy.TmT.dto.search.SearchRequest;
import com.ssafy.TmT.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountControllerImpl implements AccountController {
	
	private final AccountService accountService;
	
	public ResponseEntity<List<FreeAccountResponse>> findFreeAccounts() {
		log.info("컨트롤러 : 자유 계좌 조회");
		List<FreeAccountResponse> response = accountService.findFreeAccounts();	
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<List<SavingsAccountResponse>> findSavingsAccounts() {
		log.info("컨트롤러 : 적금 계좌 조회");
		List<SavingsAccountResponse> response = accountService.findSavingsAccounts();	
		return ResponseEntity.ok(response);
	}
	
	// 3번 api. 총 자산 조회 완성 : 24.11.14	11.16 점검 완료
	public ResponseEntity<BalanceResponse> getTotalBalance() {
		log.info("컨트롤러 : 총 자산 조회");
		BalanceResponse response = accountService.getTotalBalance();
		return ResponseEntity.ok(response);
	}
	
	
	// 6번 api
	// 자산 페이지 - 자유 입출금 계좌 클릭 시 : 완성 24.11.14. 16 점검 완료
	public ResponseEntity<FreeAccountDetailResponse> getFreeAccountDetail(@PathVariable Long accountId, @RequestParam(defaultValue = "0") int page, @RequestBody(required = false) SearchRequest request) {
		log.info("컨트롤러 : 자유 계좌 단일 조회");
		FreeAccountDetailResponse response = accountService.findFreeAccountDetail(accountId, page, request);	
		return ResponseEntity.ok(response);
	}
	
	// 7번 api
	// 자산 페이지 - 적금 계좌 클릭 시 : 완성 24.11.14 16 점검 완료
	public ResponseEntity<SavingsAccountDetailResponse> getSavingAccountDetail(@PathVariable Long accountId,  @RequestParam(defaultValue = "0") int page, @RequestBody(required = false) SearchRequest request) {
		System.out.println("적금 계좌 단일 조회");
		SavingsAccountDetailResponse response = accountService.getSavingAccountDetail(accountId, page, request);	
		return ResponseEntity.ok(response);
	}
}

// 11.08 메인페이지 컨트롤러 끝
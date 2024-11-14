package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailResponse;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final AccountService accountService;
	
	// 1번 api. 자유 계좌 조회 완성 : 24.11.14
	@GetMapping("/free")
	@Operation(summary = "1. 자유 계좌 전체 조회", description = "JWT를 이용해 자유 입출금 계좌를 전부 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<List<FreeAccountResponse>> findFreeAccounts() {
		log.info("컨트롤러 : 자유 계좌 조회");
		List<FreeAccountResponse> response = accountService.findFreeAccounts();	
		return ResponseEntity.ok(response);
	}
	
	// 2번 api. 적금 계좌 조회 완성 : 24.11.14
	@GetMapping("/savings")
	@Operation(summary = "2. 적금 계좌 전체 조회", description = "JWT를 이용해 적금 계좌를 전부 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<List<SavingsAccountResponse>> findSavingsAccounts() {
		log.info("컨트롤러 : 적금 계좌 조회");
		List<SavingsAccountResponse> response = accountService.findSavingsAccounts();	
		return ResponseEntity.ok(response);
	}
	
	// 3번 api. 총 자산 조회 완성 : 24.11.14
	@GetMapping("/balance")
	@Operation(summary = "3. 총 자산 조회", description = "JWT를 이용해 총 자산을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<BalanceResponse> getTotalBalance() {
		log.info("컨트롤러 : 총 자산 조회");
		BalanceResponse response = accountService.getTotalBalance();
		return ResponseEntity.ok(response);
	}
	
	
	// 6번 api
	// 자산 페이지 - 자유 입출금 계좌 클릭 시 : 완성 24.11.14
	@GetMapping("/free/{accountId}")
	@Operation(summary = "6. 자유 계좌 단일 조회", description = "accountId를 이용해 자유 입출금 계좌를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<FreeAccountDetailResponse> getFreeAccountDetail(@PathVariable Long accountId) {
		log.info("컨트롤러 : 자유 계좌 단일 조회");
		FreeAccountDetailResponse response = accountService.findFreeAccountDetail(accountId);	
		return ResponseEntity.ok(response);
	}
	
	// 7번 api
	// 자산 페이지 - 적금 계좌 클릭 시 : 완성 24.11.14
	@GetMapping("/savings/{accountId}")
	@Operation(summary = "7. 적금 계좌 단일 조회", description = "accountId를 이용해 적금 계좌를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<SavingsAccountDetailResponse> getSavingAccountDetail(@PathVariable Long accountId) {
		System.out.println("적금 계좌 단일 조회");
		SavingsAccountDetailResponse response = accountService.getSavingAccountDetail(accountId);	
		return ResponseEntity.ok(response);
	}
}

// 11.08 메인페이지 컨트롤러 끝
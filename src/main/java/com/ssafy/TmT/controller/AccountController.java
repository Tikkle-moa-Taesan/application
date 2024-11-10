package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.AccountResponse;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.dto.FreeAccountDTO;
import com.ssafy.TmT.dto.SavingsAccountDTO;
import com.ssafy.TmT.dto.TransactionDTO;
import com.ssafy.TmT.service.AccountService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final AccountService accountService;
	
	// 1번 api. 자유 계좌 조회
	@GetMapping("/free")
	public ResponseEntity<List<FreeAccountDTO>> findFreeAccounts(HttpHeaders headers) {
		log.info("컨트롤러 : 자유 계좌 조회");
		List<FreeAccountDTO> response = accountService.findFreeAccounts(headers);	
		return ResponseEntity.ok(response);
	}
	
	// 2번 api. 적금 계좌 조회
	@GetMapping("/savings")
	public ResponseEntity<List<SavingsAccountDTO>> findSavingsAccounts(HttpHeaders headers) {
		log.info("컨트롤러 : 적금 계좌 조회");
		List<SavingsAccountDTO> response = accountService.findSavingsAccounts(headers);	
		return ResponseEntity.ok(response);
	}
	

	
	// 6번 api
	// 자산 페이지 - 자유 입출금 계좌 클릭 시
	@GetMapping("/free/{accountId}")
	public ResponseEntity<FreeAccountDTO> getFreeAccountDetail(@PathVariable Long accountId, HttpHeaders headers) {
		FreeAccountDTO response = accountService.getFreeAccountDetail(accountId,headers);	
		return ResponseEntity.ok(response);
		// 이건 내부에서 자유입출금인지 / 적금인지 나누면 될것같음.
	}
	
	// 7번 api
	// 자산 페이지 - 적금 계좌 클릭 시
	@GetMapping("/savings/{accountId}")
	public ResponseEntity<SavingsAccountDTO> getSavingAccountDetail(@PathVariable Long accountId, HttpHeaders headers) {
		SavingsAccountDTO response = accountService.getSavingAccountDetail(accountId,headers);	
		return ResponseEntity.ok(response);
		// 이건 내부에서 자유입출금인지 / 적금인지 나누면 될것같음.
	}
	
}

// 11.08 메인페이지 컨트롤러 끝
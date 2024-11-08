package com.ssafy.TmT.domain.account.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.domain.account.dto.BalanceDTO;
import com.ssafy.TmT.domain.account.dto.ExpenseResponse;
import com.ssafy.TmT.domain.account.dto.FreeAccountDTO;
import com.ssafy.TmT.domain.account.dto.SavingsAccountDTO;
import com.ssafy.TmT.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
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
	
	// 3번 api. 총 자산 조회
	@GetMapping("")
	public ResponseEntity<BalanceDTO> getTotalBalance(HttpHeaders headers) {
		log.info("컨트롤러 : 총 자산 조회");
		return ResponseEntity.ok(accountService.getTotalBalance(headers));
	}
}

// 11.08 메인페이지 컨트롤러 끝
package com.ssafy.TmT.domain.account.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/finance")
public class AccountController {
	
	private final AccountService accountService;
	
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountResponse>> findAllAccounts(HttpHeaders headers) {
		log.info("컨트롤러 : 계좌 통합 조회 실행");
		List<AccountResponse> response = accountService.findAllAccountsByMember(headers);	
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/account")
	public ResponseEntity<AccountResponse> 상세계좌조회(HttpHeaders headers) {
		log.info("컨트롤러 : 상세 계좌 조회");
		return ResponseEntity.ok(accountService.findAccountById(headers));
	}
	
}
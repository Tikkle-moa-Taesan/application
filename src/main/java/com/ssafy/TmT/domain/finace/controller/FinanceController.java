package com.ssafy.TmT.domain.finace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class FinanceController {
	
	private final FinanceServiceImpl financeService;
	
	@PostMapping("/InquireDepositorAccountNumber")
	public ResponseEntity<FinanceResponse> 계좌통합조회(@RequestBody FinanceRequest request) {
		System.out.println("계좌통합조회 실행");
		System.out.println("request in Controller : " + request);
		FundResponse response = bankService.예금주조회(request);	
		return ResponseEntity.ok(response);
	}
	
}
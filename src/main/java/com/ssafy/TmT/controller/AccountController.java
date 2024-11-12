package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.FreeAccountDTO;
import com.ssafy.TmT.dto.SavingsAccountDTO;
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
	
	// 1번 api. 자유 계좌 조회
	@GetMapping("/free")
	@Operation(summary = "1. 자유 계좌 전체 조회", description = "JWT를 이용해 자유 입출금 계좌를 전부 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<List<FreeAccountDTO>> findFreeAccounts(@RequestHeader("Authorization") String jwt) {
		System.out.println("자유 계좌 조회");
		List<FreeAccountDTO> response = accountService.findFreeAccounts(jwt);	
		return ResponseEntity.ok(response);
	}
	
	// 2번 api. 적금 계좌 조회
	@GetMapping("/savings")
	@Operation(summary = "2. 적금 계좌 전체 조회", description = "JWT를 이용해 적금 계좌를 전부 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<List<SavingsAccountDTO>> findSavingsAccounts(@RequestHeader("Authorization") String jwt) {
		log.info("컨트롤러 : 적금 계좌 조회");
		List<SavingsAccountDTO> response = accountService.findSavingsAccounts(jwt);	
		return ResponseEntity.ok(response);
	}
	

	
	// 6번 api
	// 자산 페이지 - 자유 입출금 계좌 클릭 시
	@GetMapping("/free/{accountId}")
	@Operation(summary = "6. 자유 계좌 단일 조회", description = "accountId를 이용해 자유 입출금 계좌를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<FreeAccountDTO> getFreeAccountDetail(@PathVariable Long accountId, @RequestHeader("Authorization") String jwt) {
		FreeAccountDTO response = accountService.getFreeAccountDetail(accountId,jwt);	
		return ResponseEntity.ok(response);
		// 이건 내부에서 자유입출금인지 / 적금인지 나누면 될것같음.
	}
	
	// 7번 api
	// 자산 페이지 - 적금 계좌 클릭 시
	@GetMapping("/savings/{accountId}")
	@Operation(summary = "7. 적금 계좌 단일 조회", description = "accountId를 이용해 적금 계좌를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<SavingsAccountDTO> getSavingAccountDetail(@PathVariable Long accountId, @RequestHeader("Authorization") String jwt) {
		System.out.println("컨트롤러 호출");
		SavingsAccountDTO response = accountService.getSavingAccountDetail(accountId,jwt);	
		return ResponseEntity.ok(response);
	}
	
}

// 11.08 메인페이지 컨트롤러 끝
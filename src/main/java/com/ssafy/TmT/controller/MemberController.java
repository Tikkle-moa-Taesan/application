package com.ssafy.TmT.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	// 3번 api. 총 자산 조회
	@GetMapping("/balance")
	@Operation(summary = "3. 총 자산 조회", description = "JWT를 이용해 총 자산을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<BalanceDTO> getTotalBalance(@RequestHeader("Authorization") String jwt) {
		log.info("컨트롤러 : 총 자산 조회");
		BalanceDTO response = memberService.getTotalBalance(jwt);
		return ResponseEntity.ok(response);
	}
	
	
	// 4번 api. 총 자산 조회
	@GetMapping("/expense")
	@Operation(summary = "4. 총 지출 통계 조회", description = "JWT를 이용해 총 자산을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<BalanceDTO> calculateExpenseSummary(@RequestHeader("Authorization") String jwt) {
		log.info("컨트롤러 : 지출 통계 조회");
		BalanceDTO response = memberService.getTotalBalance(jwt);
		return ResponseEntity.ok(response);
	}
	
	
	
}

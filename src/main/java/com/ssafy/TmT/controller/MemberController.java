package com.ssafy.TmT.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class MemberController {

	private final MemberService memberService;

	// 3번 api. 총 자산 조회
	@GetMapping("/balance")
	public ResponseEntity<BalanceDTO> getTotalBalance(HttpHeaders headers) {
		log.info("컨트롤러 : 총 자산 조회");
		BalanceDTO response = memberService.getTotalBalance(headers);
		return ResponseEntity.ok(response);
	}
	
}

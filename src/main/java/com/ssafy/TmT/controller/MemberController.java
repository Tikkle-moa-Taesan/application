package com.ssafy.TmT.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.oauth.Profile;
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
	
	// 9번 api. profile get. 11.16 개발/점검 완료
	@GetMapping("/profile")
	@Operation(summary = "9. 사용자 프로필 조회", description = "사용자의 프로필을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	public ResponseEntity<Profile> findFreeAccounts() {
		log.info("컨트롤러 : 자유 계좌 조회");
		Profile response = memberService.getProfile();	
		return ResponseEntity.ok(response);
	}
	
}

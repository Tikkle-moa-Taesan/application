package com.ssafy.TmT.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.LoginResponse;
import com.ssafy.TmT.service.OAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor	
@RequestMapping("/api/oauth")
public class OAuthController {
	
	private final OAuthService oAuthService;
	
	// 카카오 로그인
	@Operation(summary = "0. 카카오 로그인", description = "카카오 회원 정보를 가져옵니다")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	@GetMapping("/kakao/login/{code}")
	public ResponseEntity<LoginResponse> kakaoLogin(@PathVariable String code) throws Exception {
		System.out.println("카카오로그인 호출");
		HttpHeaders headers = new HttpHeaders();
		LoginResponse loginResponse = oAuthService.getMemberInfo(code, headers);
		oAuthService.setCookie(headers);
		return ResponseEntity.ok().headers(headers).body(loginResponse);
	}
}
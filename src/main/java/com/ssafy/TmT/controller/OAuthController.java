package com.ssafy.TmT.controller;


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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor	
@RequestMapping("/api/oauth")
public class OAuthController {
	
	private final OAuthService oAuthService;
	
//	@GetMapping("/{provider}/url") 
//	@Operation(summary = "로그인 url 요청", description = "카카오 url을 요청합니다")
//	@ApiResponse(responseCode = "200", description = "요청 성공")
//	@ApiResponse(responseCode = "400", description = "요청 실패")
//	public ResponseEntity<UrlResponse> getLoginUrl(@PathVariable("provider") String provider) {
//	    if (!List.of("kakao", "naver", "google").contains(provider.toLowerCase())) {
//	        return ResponseEntity.badRequest().body(new UrlResponse("Invalid provider"));
//	    }
//		UrlResponse response = new UrlResponse(oAuthService.buildAuthUrl(provider));
//		return ResponseEntity.ok(response);
//	}
	
	// 카카오 로그인
	@Operation(summary = "카카오 로그인", description = "카카오 회원 정보를 가져옵니다")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "요청 실패")
	@GetMapping("/kakao/login/{code}")
	public ResponseEntity<LoginResponse> kakaoLogin(@PathVariable String code) throws Exception {
		System.out.println("카카오로그인 호출");
		LoginResponse response = oAuthService.getMemberInfo(code);
		return ResponseEntity.ok(response);
	}
}
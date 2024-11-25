package com.ssafy.TmT.controller.impl;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.OAuthController;
import com.ssafy.TmT.dto.oauth.LoginResponse;
import com.ssafy.TmT.service.OAuthService;
import com.ssafy.TmT.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor	
public class OAuthControllerImpl implements OAuthController {
	
	private final OAuthService oAuthService;
	private final JwtUtil jwtUtil;
	
//	@Override
//    public ResponseEntity<LoginResponse> kakaoLogin(@PathVariable String code) {
//        log.info("카카오 로그인 호출 with code: {}", code);
//        HttpHeaders headers = new HttpHeaders();
//        LoginResponse loginResponse = oAuthService.processKakaoLogin(code, headers);
//
//        return ResponseEntity.ok().headers(headers).body(loginResponse);
//    }

	@Override
	public ResponseEntity<String> logout() {
		HttpHeaders responseHeader = jwtUtil.expireCookies();
		return ResponseEntity.ok().headers(responseHeader).body("로그아웃 성공");
	}

    @Override
    public ResponseEntity<String> getGoogleLoginUrl() {
        log.info("Google 로그인 URL 생성 요청");

        // Google 로그인 URL 생성
        String googleLoginUrl = oAuthService.generateGoogleLoginUrl();

        return ResponseEntity.status(302).body(googleLoginUrl); // Redirect URL 반환
    }

    @Override
    public ResponseEntity<LoginResponse> handleGoogleCallback(@RequestParam String code, @RequestParam String state) {
        log.info("Google 로그인 콜백 수신 with code: {} and state: {}", code, state);

        // State 검증
        oAuthService.validateStateToken(state);

        HttpHeaders headers = new HttpHeaders();
        // Google 인증 코드로 사용자 정보 처리
        LoginResponse loginResponse = oAuthService.processGoogleLogin(code, headers);

        return ResponseEntity.ok().headers(headers).body(loginResponse);
    }

    @Override
    public ResponseEntity<String> getKakaoLoginUrl() {
        String kakaoLoginUrl = oAuthService.generateKakaoLoginUrl();
        return ResponseEntity.status(302).body(kakaoLoginUrl); // Redirect URL 반환
    }
    
    
    @Override
    public ResponseEntity<LoginResponse> handleKakaoCallback(String code) {
    	HttpHeaders headers = new HttpHeaders();
        LoginResponse loginResponse = oAuthService.processKakaoLogin(code, headers);
        return ResponseEntity.ok(loginResponse);
    }
	
}
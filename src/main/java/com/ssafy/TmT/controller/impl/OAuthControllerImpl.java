package com.ssafy.TmT.controller.impl;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.OAuthController;
import com.ssafy.TmT.dto.oauth.LoginRequest;
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
	
	@Override
	public ResponseEntity<String> logout() {
		HttpHeaders responseHeader = jwtUtil.expireCookies();
		return ResponseEntity.ok().headers(responseHeader).body("로그아웃 성공");
	}

    @Override
    public ResponseEntity<LoginResponse> googleLogin(LoginRequest loginRequest) {
        log.info("구글 로그인 호출");
        HttpHeaders headers = new HttpHeaders();
        LoginResponse loginResponse = oAuthService.processGoogleLogin(loginRequest.getAuthorizationCode(), headers);
        return ResponseEntity.ok().headers(headers).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> kakaoLogin(LoginRequest loginRequest) {
        log.info("카카오 로그인 호출");
        HttpHeaders headers = new HttpHeaders();
        LoginResponse loginResponse = oAuthService.processKakaoLogin(loginRequest.getAuthorizationCode(), headers);
        return ResponseEntity.ok().headers(headers).body(loginResponse);
    }
	
}
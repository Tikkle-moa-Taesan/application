package com.ssafy.TmT.controller.impl;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.OAuthController;
import com.ssafy.TmT.dto.oauth.LoginResponse;
import com.ssafy.TmT.service.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor	
@RequestMapping("/api/oauth")
public class OAuthControllerImpl implements OAuthController {
	
	private final OAuthService oAuthService;
	
	public ResponseEntity<LoginResponse> kakaoLogin(@PathVariable String code) throws Exception {
		System.out.println("카카오로그인 호출");
		HttpHeaders headers = new HttpHeaders();
		LoginResponse loginResponse = oAuthService.getMemberInfo(code, headers);
		return ResponseEntity.ok().headers(headers).body(loginResponse);
	}
}
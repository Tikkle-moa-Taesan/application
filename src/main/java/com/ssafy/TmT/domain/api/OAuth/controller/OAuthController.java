package com.ssafy.TmT.domain.api.OAuth.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.domain.api.OAuth.dto.OAuthResponse;
import com.ssafy.TmT.domain.api.OAuth.service.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {
	
	private final OAuthService oAuthService;
	
    // 사용자에게 인증 페이지로 리다이렉트
    @GetMapping("/authorize")
    public ResponseEntity<String> authorize() {
        String redirectUrl = oAuthService.buildAuthorizeUrl();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }
	
    // 리다이렉트된 후 code 값을 받아오는 엔드포인트
    @GetMapping("/callback")
    public ResponseEntity<OAuthResponse> callback(@RequestParam("code") String code) {
    	OAuthResponse response = oAuthService.exchangeCodeForAccessToken(code);
        return ResponseEntity.ok(response);
    }
}
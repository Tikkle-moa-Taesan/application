package com.ssafy.TmT.domain.api.OAuth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.ssafy.TmT.domain.api.ApiUtil;
import com.ssafy.TmT.domain.api.OAuth.dto.KakaoTokenResponse;
import com.ssafy.TmT.domain.api.OAuth.dto.IdTokenPayload;
import com.ssafy.TmT.domain.api.OAuth.dto.KakaoOAuthResponse;
import com.ssafy.TmT.domain.api.OAuth.dto.LoginRequest;
import com.ssafy.TmT.domain.api.OAuth.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthProvider {

	private final ApiUtil apiUtil;

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectUri;

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String kakaoTokenUri;

	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String kakaoUserInfoUri;

	// provider에 따라 clientId와 redirectUri 설정
	public String buildAuthUrl(String provider) {
		if (provider.equals("kakao")) {
			return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId
					+ "&redirect_uri=" + kakaoRedirectUri;
		} else
			return "ERROR URL";
	}

	// 토큰 받기
	public KakaoOAuthResponse getKakaoAccessToken(LoginRequest request) {
		String authorizationCode = request.getAuthorizationCode();
		// 요청 보내기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoClientId);
		body.add("redirect_uri", kakaoRedirectUri);
		body.add("code", authorizationCode);
		body.add("client_secret", kakaoClientSecret);

		System.out.println("카카오 토큰 받아오기 성공");
		return apiUtil.sendPostRequest(kakaoTokenUri, body, headers, KakaoOAuthResponse.class).getBody();
	}

	public IdTokenPayload getIdTokenPayload(String accessToken) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return apiUtil.sendPostRequest(kakaoUserInfoUri, null, httpHeaders, IdTokenPayload.class).getBody();
	}
}

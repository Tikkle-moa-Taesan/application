package com.ssafy.TmT.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.dto.oauth.IdTokenPayload;
import com.ssafy.TmT.dto.oauth.KakaoOAuthResponse;
import com.ssafy.TmT.util.ApiUtil;

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


	// 토큰 받기
	public KakaoOAuthResponse getKakaoAccessToken(String code) {
		// 요청 보내기
		HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoClientId);
		body.add("redirect_uri", kakaoRedirectUri);
		body.add("code", code);
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

	public IdTokenPayload decodeIdToken(String idToken) throws Exception {
	    String[] tokenParts = idToken.split("\\.");
	    if (tokenParts.length != 3) {
	        throw new IllegalArgumentException("Invalid ID Token format.");
	    }

	    // payload 부분을 디코딩
	    String payloadJson = new String(Base64.getUrlDecoder().decode(tokenParts[1]));

	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(payloadJson, IdTokenPayload.class);
	}
}


//// provider에 따라 clientId와 redirectUri 설정
//public String buildAuthUrl(String provider) {
//	if (provider.equals("kakao")) {
//		return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId
//				+ "&redirect_uri=" + kakaoRedirectUri;
//	} else
//		return "ERROR URL";
//}

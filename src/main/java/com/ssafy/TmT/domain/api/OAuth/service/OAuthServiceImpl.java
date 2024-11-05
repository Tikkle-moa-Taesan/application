package com.ssafy.TmT.domain.api.OAuth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthServiceImpl implements OAuthService {

	// 데이터 일단은 보관하고 있자 나중에 옮겨
	private static final String AUTHORIZATION_URL = "https://testapi.openbanking.or.kr/oauth/2.0/authorize";
	private static final String CLIENT_ID = "53798d71-78d0-401f-adf7-a388f979dea6";
	private static final String REDIRECT_URI = "http://localhost:3000/callbackURL";
	private static final String SCOPE = "login inquiry transfer";
	private static final String STATE = "12345678901234567890123456789012";
	private static final String CLIENT_SECRET = "84f09c3c-d3f1-4b3e-9655-55e4b9f5183f";

	// 인증 페이지 URL 생성
	public String buildAuthorizeUrl() {
		return AUTHORIZATION_URL + "?response_type=code" + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
				+ "&scope=" + SCOPE + "&state=" + STATE + "&auth_type=0" + "&client_secret=" + CLIENT_SECRET;
	}

	// code로 Access Token 교환
	public String exchangeCodeForAccessToken(String code) {
		 RestTemplate restTemplate = new RestTemplate();
		 String accessTokenUrl = "https://testapi.openbanking.or.kr/oauth/2.0/token";
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		 Map<String, Object> params = new HashMap<>();
		 params.put("grant_type", "authorization_code");
		 params.put("code", code);
		 params.put("client_id", CLIENT_ID);
		 params.put("client_secret", CLIENT_SECRET);
		 params.put("redirect_uri", REDIRECT_URI);

		 HttpEntity<Map<String,Object>> request = new HttpEntity<>(params, headers);
		// HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,
		// headers);
		ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUrl, request, String.class);

		// 실제 응답에서 access token 추출 예시
		String accessToken = extractAccessToken(response.getBody());

		return accessToken;
	}

	// 실제로는 JSON 응답에서 access token을 추출해야 합니다.
	private String extractAccessToken(String responseBody) {
		return "parsed_access_token";
	}
}

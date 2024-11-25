package com.ssafy.TmT.service;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.controller.impl.AccountControllerImpl;
import com.ssafy.TmT.dto.oauth.GoogleIdTokenPayload;
import com.ssafy.TmT.dto.oauth.KakaoIdTokenPayload;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.ApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
    
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String googleTokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;

    // 공통 메서드: OAuth Access Token 요청
    private Map<String, String> getAccessToken(String tokenUri, String clientId, String clientSecret, String redirectUri, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        return apiUtil.sendPostRequest(tokenUri, body, headers, Map.class).getBody();
    }
    
    // Kakao ID Token 가져오기
    public String getKakaoIdToken(String code) {
        Map<String, String> response = getAccessToken(kakaoTokenUri, kakaoClientId, kakaoClientSecret, kakaoRedirectUri, code);
        return response.get("id_token");
    }

    // Google Access Token 가져오기
    public String getGoogleIdToken(String code) {
        Map<String, String> response = getAccessToken(googleTokenUri, googleClientId, googleClientSecret, googleRedirectUri, code);
        return response.get("id_token");
    }

    // Google 로그인 URL 생성
    public String generateGoogleLoginUrl(String state) {
        return UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
            .queryParam("client_id", googleClientId)
            .queryParam("redirect_uri", googleRedirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", "openid email profile")
            .queryParam("state", state)
            .toUriString();
    }
    
    // **ID Token 디코딩: Kakao**
    public KakaoIdTokenPayload decodeKakaoIdToken(String idToken) {
        return decodeIdToken(idToken, KakaoIdTokenPayload.class);
    }

    // **ID Token 디코딩: Google**
    public GoogleIdTokenPayload decodeGoogleIdToken(String accessToken) {
        return decodeIdToken(accessToken, GoogleIdTokenPayload.class);
    }

    // 공통 메서드: ID Token 디코딩
    private <T> T decodeIdToken(String idToken, Class<T> clazz) {
    	log.debug("Received ID Token: {}", idToken);
    	
        try {
            String[] tokenParts = idToken.split("\\.");
            if (tokenParts.length != 3) {
            	throw new CustomException(ErrorCode.INVALID_ID_TOKEN_FORMAT);
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(tokenParts[1]));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payloadJson, clazz);
        } catch (IllegalArgumentException e) {
            // Base64 디코딩 실패
            log.error("Base64 decoding failed for ID Token: {}", idToken, e);
            throw new CustomException(ErrorCode.DECODING_FAILED);
        } catch (Exception e) {
            // JSON 파싱 실패
            log.error("Failed to parse ID Token payload: {}", e.getMessage(), e);
            throw new CustomException(ErrorCode.PARSING_FAILED);
        }
    }

    public String generateKakaoLoginUrl() {
        return UriComponentsBuilder.fromHttpUrl(kakaoAuthorizationUri)
            .queryParam("client_id", kakaoClientId)
            .queryParam("redirect_uri", kakaoRedirectUri)
            .queryParam("response_type", "code")
            .toUriString();
    }
}

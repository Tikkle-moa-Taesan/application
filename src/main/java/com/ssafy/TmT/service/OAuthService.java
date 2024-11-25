package com.ssafy.TmT.service;

import java.util.Map;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssafy.TmT.controller.impl.OAuthControllerImpl;
import com.ssafy.TmT.dto.oauth.GoogleIdTokenPayload;
import com.ssafy.TmT.dto.oauth.KakaoIdTokenPayload;
import com.ssafy.TmT.dto.oauth.LoginDTO;
import com.ssafy.TmT.dto.oauth.LoginResponse;
import com.ssafy.TmT.dto.oauth.Profile;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.ApiUtil;
import com.ssafy.TmT.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

	private final JwtUtil jwtUtil;
	private final OAuthProvider oAuthProvider;
	private final MemberService memberService;
//    private final Map<String, String> stateStore = new ConcurrentHashMap<>(); // 간단한 상태 저장소


    // Kakao 로그인 처리
    public LoginResponse processKakaoLogin(String code, HttpHeaders headers) {
    	
        String idToken = oAuthProvider.getKakaoIdToken(code);
        
        // 2. ID Token 디코딩
        KakaoIdTokenPayload kakaoPayload = oAuthProvider.decodeKakaoIdToken(idToken);

        // 3. 공통 로그인 처리
        return processLogin(kakaoPayload.getSub(), kakaoPayload.getNickname(), kakaoPayload.getPicture(), headers);
    }
    
    // **구글 로그인 처리**
    public LoginResponse processGoogleLogin(String code, HttpHeaders headers) {
    	// 1. Google Access Token 가져오기
        String idToken = oAuthProvider.getGoogleIdToken(code);

        // 2. ID Token 디코딩
        GoogleIdTokenPayload googlePayload = oAuthProvider.decodeGoogleIdToken(idToken);

        // 3. 공통 로그인 처리
        return processLogin(googlePayload.getSub(), googlePayload.getName(), googlePayload.getPicture(), headers);
    }
    
    // Google 로그인 URL 생성
//    public String generateGoogleLoginUrl() {
//        String state = generateStateToken();
//        return oAuthProvider.generateGoogleLoginUrl(state);
//    }
    
    // 상태 토큰 검증
//    public void validateStateToken(String state) {
//        if (!stateStore.containsKey(state)) {
//            throw new CustomException(ErrorCode.INVALID_STATE_TOKEN);
//        }
//        stateStore.remove(state); // 검증 후 삭제
//    }
    // 상태 토큰 생성
//    public String generateStateToken() {
//        String state = UUID.randomUUID().toString();
//        stateStore.put(state, state);
//        return state;
//    }
    
    // 공통 로그인 처리
    private LoginResponse processLogin(String sub, String name, String picture, HttpHeaders headers) {
        LoginDTO loginDTO = new LoginDTO(sub,name,picture);
    	Profile profile = memberService.login(loginDTO);

        // JWT 생성
        String customAccessToken = jwtUtil.generateAccessToken(profile.getMemberId(), profile.getRole());
        String customRefreshToken = jwtUtil.generateRefreshToken(profile.getMemberId(), profile.getRole());

        // 쿠키 설정
        jwtUtil.setAccessToken(headers, customAccessToken);
        jwtUtil.setRefreshToken(headers, customRefreshToken);

        return new LoginResponse(profile, jwtUtil.getAccessTokenExpiry(), jwtUtil.getRefreshTokenExpiry());
    }

    public String generateKakaoLoginUrl() {
        return oAuthProvider.generateKakaoLoginUrl();
    }
}
//
//public LoginResponse getMemberInfo(String code, HttpHeaders headers)  {
//    
//	// 인증코드로 카카오 엑세스 토큰 발급받기
//	KakaoOAuthResponse kakaoToken = oAuthProvider.getKakaoAccessToken(code);
////	System.out.println("kakaoToken : " + kakaoToken);
//	
//	// IdToken을 디코딩해보자
//	String idToken = kakaoToken.getId_token();
//	IdTokenPayload idTokenPayLoad;
//	try {
//		idTokenPayLoad = oAuthProvider.decodeIdToken(idToken);
//	} catch (Exception e) {
//		throw new CustomException(ErrorCode.OAUTH_INVALID);
//	}
//	System.out.println("idTokenPayload : " + idTokenPayLoad);
//	
////	String subject = idTokenPayLoad.getSub();
//	
//	// idTokenPayload 로 회원 가입 하기
//	Profile profile = memberService.login(idTokenPayLoad);
//	System.out.println("프로필 : " + profile);
//	
//	// member 값을 통해 커스텀 JWT를 생성
//	String customAccessToken = jwtUtil.generateAccessToken(profile.getMemberId(), profile.getRole());
//	String customRefreshToken = jwtUtil.generateRefreshToken(profile.getMemberId(), profile.getRole());
//    // 응답에 만료 시간 정보 추가
//    Long accessTokenExpiry = jwtUtil.getAccessTokenExpiry();
//    Long refreshTokenExpiry = jwtUtil.getRefreshTokenExpiry();
//    
//    System.out.println("쿠키 추가");
//    
//    // 쿠키 추가
//    jwtUtil.setAccessToken(headers,customAccessToken);
//    jwtUtil.setRefreshToken(headers,customRefreshToken);	
//    
//	LoginResponse loginResponse = new LoginResponse(profile,accessTokenExpiry,refreshTokenExpiry);
//	return loginResponse;
//}
//

//// Google 인증 코드로 사용자 로그인 처리
//public LoginResponse processGoogleLogin(String code, HttpHeaders headers) {
//    // Google Access Token 가져오기
//    String accessToken = oAuthProvider.getGoogleAccessToken(code);
//
//    // Google ID Token 가져오기 및 디코딩
//    IdTokenPayload idTokenPayload;
//	try {
//		idTokenPayload = oAuthProvider.decodeGoogleIdToken(accessToken);
//	} catch (Exception e) {
//		throw new CustomException(ErrorCode.OAUTH_INVALID);
//	}
//
//    // 사용자 정보로 로그인/회원가입 처리
//    Profile profile = memberService.login(idTokenPayload);
//
//    // JWT 생성
//    String customAccessToken = jwtUtil.generateAccessToken(profile.getMemberId(), profile.getRole());
//    String customRefreshToken = jwtUtil.generateRefreshToken(profile.getMemberId(), profile.getRole());
//    
//    // 쿠키 추가
//    jwtUtil.setAccessToken(headers,customAccessToken);
//    jwtUtil.setRefreshToken(headers,customRefreshToken);
//
//    // 반환 데이터
//    return new LoginResponse(profile, jwtUtil.getAccessTokenExpiry(), jwtUtil.getRefreshTokenExpiry());
//}
//
//// 상태 토큰 생성
//public String generateStateToken() {
//    String state = UUID.randomUUID().toString();
//    stateStore.put(state, state); // 간단히 메모리에 저장 (추후 Redis 등 사용 권장)
//    return state;
//}
//
//// 상태 토큰 검증
//public void validateStateToken(String state) {
//    if (!stateStore.containsKey(state)) {
//        throw new CustomException(ErrorCode.OAUTH_INVALID);
//    }
//    stateStore.remove(state); // 검증 후 삭제
//}
//
//
//public String generateGoogleLoginUrl(String state) {
//	return oAuthProvider.generateGoogleLoginUrl(state);
//}
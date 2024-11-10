//package com.ssafy.TmT.service;
//
//import org.springframework.stereotype.Service;
//
//import com.ssafy.TmT.dto.IdTokenPayload;
//import com.ssafy.TmT.dto.KakaoOAuthResponse;
//import com.ssafy.TmT.dto.LoginRequest;
//import com.ssafy.TmT.dto.LoginResponse;
//import com.ssafy.TmT.util.ApiUtil;
//import com.ssafy.TmT.util.JwtUtil;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class OAuthService {
//
//	private final ApiUtil apiUtil;
//	private final JwtUtil jwtUtil;
//	private final OAuthProvider oAuthProvider;
//	private final MemberService memberService;
//	
//	public String buildAuthUrl(String provider) {
//		return oAuthProvider.buildAuthUrl(provider);
//	}
//
//	
////    public LoginResponse getMemberInfo(LoginRequest request) throws Exception {
////    
////    	// 인증코드로 카카오 엑세스 토큰 발급받기
////    	KakaoOAuthResponse kakaoToken = oAuthProvider.getKakaoAccessToken(request);
////    	// 엑세스 토큰으로 idTokenPayload 받기
////    	IdTokenPayload idToken = oAuthProvider.getIdTokenPayload(kakaoToken.getAccessToken());
////    	// idTokenPayload 로 회원 가입 하기
////    	Member member = memberService.login(idToken);
////    	// 처음 온 사람이면 회원가입 진행
////    	if (member == null) {
////    	member = memberService.register(idToken);
////    	}
////    	
////    	// member 값을 통해 커스텀 JWT를 생성
////    	String customAccessToken = jwtUtil.generateAccessToken(member.getMemberId());
////    	String customRefreshToken = jwtUtil.generateRefreshToken(member.getMemberId());
////        // 응답에 만료 시간 정보 추가
////        Long accessTokenExpiry = jwtUtil.getAccessTokenExpiry();
////        Long refreshTokenExpiry = jwtUtil.getRefreshTokenExpiry();
////
////    	LoginResponse response = new LoginResponse(customAccessToken,customRefreshToken,accessTokenExpiry,refreshTokenExpiry);
////    	return response;
////    }
//}

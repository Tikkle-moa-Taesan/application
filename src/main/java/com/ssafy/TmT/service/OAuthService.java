package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.dto.IdTokenPayload;
import com.ssafy.TmT.dto.KakaoOAuthResponse;
import com.ssafy.TmT.dto.LoginResponse;
import com.ssafy.TmT.dto.Profile;
import com.ssafy.TmT.util.ApiUtil;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

	private final ApiUtil apiUtil;
	private final JwtUtil jwtUtil;
	private final OAuthProvider oAuthProvider;
	private final MemberService memberService;
	
//	@Transactional
    public LoginResponse getMemberInfo(String code) throws Exception {
    
    	// 인증코드로 카카오 엑세스 토큰 발급받기
    	KakaoOAuthResponse kakaoToken = oAuthProvider.getKakaoAccessToken(code);
    	System.out.println("kakaoToken : " + kakaoToken);
    	
    	// IdToken을 디코딩해보자
    	String idToken = kakaoToken.getId_token();
    	IdTokenPayload idTokenPayLoad = oAuthProvider.decodeIdToken(idToken);
    	System.out.println("idTokenPayload : " + idTokenPayLoad);
    	
    	String subject = idTokenPayLoad.getSub();
    	
    	// idTokenPayload 로 회원 가입 하기
    	Profile profile = memberService.login(subject);
    	System.out.println("프로필 : " + profile);
    	
    	// member 값을 통해 커스텀 JWT를 생성
    	String customAccessToken = jwtUtil.generateAccessToken(profile.getMemberId());
    	String customRefreshToken = jwtUtil.generateRefreshToken(profile.getMemberId());
        // 응답에 만료 시간 정보 추가
        Long accessTokenExpiry = jwtUtil.getAccessTokenExpiry();
        Long refreshTokenExpiry = jwtUtil.getRefreshTokenExpiry();

    	LoginResponse response = new LoginResponse(profile,customAccessToken,customRefreshToken,accessTokenExpiry,refreshTokenExpiry);
    	return response;
    }

}





//	private Profile memberToProfile(Member member) {
//		Profile profile = new Profile();
//		profile.setMemberId(member.getMemberId());
//		profile.setMemberName(member.getMemberName());
//		profile.setTotalBalance(member.getTotalBalance());
//		return profile;
//	}


//public String buildAuthUrl(String provider) {
//return oAuthProvider.buildAuthUrl(provider);
//}
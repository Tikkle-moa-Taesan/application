package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class KakaoOAuthResponse {

    private String tokenType;           // 토큰 타입 (bearer)
    private String accessToken;         // 사용자 액세스 토큰 값
    private String idToken;             // ID 토큰 값, OIDC 활성화 시 제공
    private Integer expiresIn;          // 액세스 토큰 만료 시간 (초)
    private String refreshToken;        // 사용자 리프레시 토큰 값
    private Integer refreshTokenExpiresIn; // 리프레시 토큰 만료 시간 (초)
    private String scope;               // 인증된 정보 조회 권한 범위
}

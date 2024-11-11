package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class KakaoOAuthResponse {

    // 액세스 토큰
    private String access_token;

    // 토큰 타입 (bearer로 고정)
    private String token_type;

    // 액세스 토큰 만료 시간 (초 단위)
    private Integer expires_in;

    // ID 토큰 (OpenID Connect 활성화 시 발급)
    private String id_token;

    // 리프레시 토큰
    private String refresh_token;

    // 리프레시 토큰 만료 시간 (초 단위)
    private Integer refresh_token_expires_in;

    // 인증된 사용자의 정보 조회 권한 범위
    private String scope;
}

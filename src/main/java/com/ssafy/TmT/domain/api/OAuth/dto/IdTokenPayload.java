package com.ssafy.TmT.domain.api.OAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdTokenPayload {

    private String iss;          // ID 토큰을 발급한 인증 기관 정보
    private String aud;          // ID 토큰이 발급된 앱의 앱 키
    private String sub;          // ID 토큰에 해당하는 사용자의 회원번호
    private Integer iat;         // ID 토큰 발급 또는 갱신 시각 (UNIX 타임스탬프)
    private Integer exp;         // ID 토큰 만료 시간 (UNIX 타임스탬프)
    private Integer authTime;    // 사용자 인증 완료 시각 (UNIX 타임스탬프)

    // 선택 항목
    private String nonce;        // 인가 코드 요청 시 전달된 nonce 값, ID 토큰 유효성 검증 시 사용
    private String nickname;     // 닉네임 (동의 필요)
    private String picture;      // 프로필 미리보기 이미지 URL (동의 필요)
    private String email;        // 카카오 계정 대표 이메일 (동의 필요)
}

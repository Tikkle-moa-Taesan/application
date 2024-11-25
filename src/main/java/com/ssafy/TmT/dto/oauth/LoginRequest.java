package com.ssafy.TmT.dto.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String authorizationCode; // 프론트엔드에서 전달받는 인가 코드
}
package com.ssafy.TmT.dto.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoTokenResponse {	
	// 카카오 엑세스 토큰 반환값
	// OpenId Connect 활성화
	private String token_type;
	private String access_token;
	private String id_token;
	private int expires_in;
	private String refresh_token;
	private int refresh_token_expires_in;
	private String scope;
}
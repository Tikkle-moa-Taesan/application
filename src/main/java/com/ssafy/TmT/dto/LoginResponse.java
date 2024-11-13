package com.ssafy.TmT.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	
	private Profile profile;	// 처음 로그인했을 때 어떤 정보를 줘야할까?
	
//	private String customAccessToken;
//	private String customRefreshToken;
	private Long accessTokenExpiry;
	private Long refreshTokenExpiry;
} 
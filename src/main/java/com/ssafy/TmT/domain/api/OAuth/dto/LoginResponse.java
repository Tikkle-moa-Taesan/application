package com.ssafy.TmT.domain.api.OAuth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	
	private String customAccessToken;
	private String customRefreshToken;
	private Long accessTokenExpiry;
	private Long refreshTokenExpiry;
} 
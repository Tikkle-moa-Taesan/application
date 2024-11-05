package com.ssafy.TmT.domain.api.OAuth.service;

import com.ssafy.TmT.domain.api.OAuth.dto.OAuthResponse;

public interface OAuthService {

	public String buildAuthorizeUrl();
	OAuthResponse exchangeCodeForAccessToken(String code);
}

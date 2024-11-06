package com.ssafy.TmT.domain.api.OAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthResponse {

	private String accessToken;
	private String tokenType;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int user_seq_no;
	
}

//    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDU5Njk5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE3Mzg1NzM4MDAsImp0aSI6ImRiMjAxN2UyLWYzYzEtNGRmNS1iMGFiLTc1MWIxOTQ0ZDYwNSJ9.VpnC4HzPCtXCoHycch8Je28T2S_DrI5DMnhy4P2AFjA",
//    "token_type": "Bearer",
//    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDU5Njk5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE3Mzk0Mzc4MDAsImp0aSI6ImM0Y2I4ODY5LTZkZDgtNDI2OS1iMjJiLWM5OTY3MjI2Nzc4YSJ9._yNJDrzAItZuTgCE3KlB8-7FzRfSRbxYhqSdQ6VnLoM",
//    "expires_in": 7775999,
//    "scope": "inquiry login transfer",
//    "user_seq_no": "1101059699"
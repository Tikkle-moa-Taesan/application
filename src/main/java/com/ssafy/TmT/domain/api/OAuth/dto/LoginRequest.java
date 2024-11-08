package com.ssafy.TmT.domain.api.OAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class LoginRequest {

	private String authorizationCode;

}
package com.ssafy.TmT.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIdTokenPayload {
	private String name;
	private String picture;
	private String sub;
}

package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Profile {
	
	private Long memberId;
	private String memberName;
	private Long totalBalance;
}

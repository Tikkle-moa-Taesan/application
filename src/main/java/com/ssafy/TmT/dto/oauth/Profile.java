package com.ssafy.TmT.dto.oauth;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Profile {
	
	// 멤버 아이디
	private Long memberId;
	
	// 멤버 이름
	private String memberName;
	
	// 멤버 프사
	private String picture;
	
	private Timestamp createdAt;
	
	private String role;
}

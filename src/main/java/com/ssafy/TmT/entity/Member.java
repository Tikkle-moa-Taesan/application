package com.ssafy.TmT.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Member {

	// 회원 id
	private Long memberId;
	
	// openId 값
	private String subject;
	
	// 회원 이름
	private String memberName;
	
	// 총 자산
	private Long totalBalance;
	
	// 자유입출금 계좌 목록
	private List<FreeAccount> freeAccountList;
	
	// 적금 계좌 목록
	private List<SavingsAccount> savingsAccountList;
	
}

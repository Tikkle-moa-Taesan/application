package com.ssafy.TmT.entity;

import java.time.LocalDate;

import com.ssafy.TmT.dto.account.AccountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	// 계좌 id
	private Long accountId;

	// free / savings
	private AccountType accountType;
	
	// 계좌 번호
	private String accountNumber;
	
	private String accountName;
	// 은행 이름
	private String bankName;

	// 계좌 소유자 아이디
	private Long memberId; // 계좌 소유자의 ID (외래키 역할)

	// 계좌 잔액
	private Long balance;

	// 만기일
	private LocalDate maturityDate;

	// 이율
	private Double interestRate;

}

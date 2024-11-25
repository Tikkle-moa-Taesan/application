package com.ssafy.TmT.dto.admin;

import java.sql.Timestamp;

import com.ssafy.TmT.dto.account.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InsertAccountDTO{
	
	private AccountType accountType;
	
	private String accountNumber;
	
	private String accountName;
	
	private String bankName;
	
	private Float interestRate;
	
	private Long memberId;
	
	private Timestamp maturityDate;

}

package com.ssafy.TmT.dto.admin;

import com.ssafy.TmT.dto.account.AccountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertAccountRequest {

	private AccountType accountType;
	
	private String accountNumber;
	
	private String accountName;
	
	private String bankName;
	
	private Float interestRate;
}

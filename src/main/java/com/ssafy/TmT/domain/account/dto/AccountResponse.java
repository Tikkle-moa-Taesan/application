package com.ssafy.TmT.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

	private Long accountId;
	private String accountNumber;
	private String bankName;
	private Float balance;
	
}

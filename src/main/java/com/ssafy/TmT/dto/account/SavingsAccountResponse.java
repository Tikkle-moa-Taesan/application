package com.ssafy.TmT.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAccountResponse {

	private Long accountId;
	private String accountName;
	private Long balance;
}

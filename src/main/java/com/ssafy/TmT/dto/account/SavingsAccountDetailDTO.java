package com.ssafy.TmT.dto.account;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAccountDetailDTO {
	
	private Long accountId;
	private String accountNumber;
	private String accountName;
	private String bankName;
	private Long balance;
	private Date maturityDate;
	private Float interestRate;

}

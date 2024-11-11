package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAccountDTO {

	// 대략적으로 보이는 자유 입출금 계좌
	private Long accountId;
	
	// 잔액 필요
	private Long balance;
	
	// 은행 이름
	private String bankName;
	
	// 차이를 표시하자.
	private Long difference;	// 전날 대비 금액. 로직 처리 필요
	
}

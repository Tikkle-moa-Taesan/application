package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeAccountDTO {

	// 자유 입출금 계좌
	private Long accountId;
	
	// 통장 번호
	private Long accountNumber;
	
	// 통장 이름
	private String accountName;
	
	// 은행 이름
	private String bankName;
	
	// 잔액
	private Long balance;
	
	// 차이를 표시하자.
	private Long difference;	// 전날 대비 금액. 로직 처리 필요

}

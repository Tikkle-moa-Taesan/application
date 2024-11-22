package com.ssafy.TmT.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeAccountResponse {

	// 자유 입출금 계좌
	private Long accountId;	// 1
	
	// 통장 이름
	private String accountName;	// 뱅크 월렛 카카오 통장
	
	// 잔액
	private Long balance;	// 1234567
	
	private Long balanceDifference;	// 전날 대비 금액. 로직 처리 필요
	
	private String bankName;	// 은행 이름

}

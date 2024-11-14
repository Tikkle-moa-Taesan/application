package com.ssafy.TmT.dto.account;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeAccountDetailDTO {	
	
	// 메인페이지에서 계좌 목록 조회할때 나오는 DTO
	
	// 계좌 아이디
	private Long accountId;	// 1	
	
	// 계좌 번호
	private Long accountNumber;	//
	
	// 통장 이름
	private String accountName;	// 뱅크월렛 카카오 통장
	
	// 은행 이름
	private String bankName;
	
	// 잔액 필요
	private Long balance;
	
	// 차이를 표시하자.
//	private Long difference;	// 전날 대비 금액. 로직 처리 필요
	
	// 적금 만료일
//	private Date maturityDate;
	
	// 이율
//	private Float interestRate;
}

package com.ssafy.TmT.dto;

import java.util.List;

import com.ssafy.TmT.dto.account.FreeAccountDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreeAccountDetailResponse {

//	// 자유 입출금 계좌
//	private Long accountId;		// 1
//	
//	// 통장 번호
//	private Long accountNumber;	// 11 123 123456 11
//	
//	// 통장 이름
//	private String accountName;	// 뱅크월렛 카카오 통장
//	
//	// 은행 이름
//	private String bankName;	// 하나은행
//	
//	// 잔액
//	private Long balance;	// 123456789원
	
	private FreeAccountDetailDTO accountDetail;	// 주석친 내용들이 들어가 있음.
	
	private List<TransactionDTO> transactions;	// 트랜잭션 리스트
	
}
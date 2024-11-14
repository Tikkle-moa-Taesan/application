package com.ssafy.TmT.dto.account;

import java.util.List;

import com.ssafy.TmT.dto.transaction.TransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreeAccountDetailResponse {

	// 계좌 id값
	// 계좌 번호
	// 통장 이름
	// 은행 이름
	// 잔액
	private FreeAccountDetailDTO accountDetail;	// 주석친 내용들이 들어가 있음.
	
	private List<TransactionDTO> transactions;	// 트랜잭션 리스트
	
}
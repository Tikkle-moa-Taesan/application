package com.ssafy.TmT.dto.account;

import java.util.List;

import com.ssafy.TmT.dto.transaction.TransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SavingsAccountDetailResponse {


	private SavingsAccountDetailDTO accountDetail;	// 적금 계좌 상세 내역
	
	private List<TransactionDTO> transactions;	// 트랜잭션 리스트
	
}

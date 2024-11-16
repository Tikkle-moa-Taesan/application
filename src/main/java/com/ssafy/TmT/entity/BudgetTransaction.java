package com.ssafy.TmT.entity;

import java.sql.Date;

// 왜냐하면 이건 통합으로 가져오는거니깐.
public class BudgetTransaction {

	// PK 값 : 
	private Long budgetTransactionId;
	
	// 반정규화 : 멤버가 누구인지 바로 아는게 편할꺼야.
	private Long memberId;
	
	// budgetId로 조회하는게 편한가???
	private Long budgetId;	// 한 멤버가 하나의 budget이 있는게 아니니깐, 둘 다 있는게 좋을것같네.
	
	// 아래의 내용들은 transaction 에서 가져오면 돼
	private Long transactionId;	

	private Long accountId;	// 계좌 아이디
	
	private Long categoryCode;	// 카테고리 코드를 알려줘.
	
	private Date transactionDatetime;
	
	private Long amount;
	
	private String merchantName;
	
	private Long transactionType;
	
	// 이렇게 하면 DB 작업이 끝날것같음.
}

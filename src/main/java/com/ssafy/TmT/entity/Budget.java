package com.ssafy.TmT.entity;

public class Budget {

	// PK
	private Long budgetId;
	
	// 가계부 회원 아이디(FK)
	private Long memberId;
	
	// 이번 달 예산
	private Long monthBudget;
	
	// 이번 달 지출
	private Long monthExpense;
	
	// 예산 대비 지출
	private Long expensePercent;
	
    private Long food;            // 식비
    private Long living;          // 생활
    private Long housingCommunication; // 주거/통신
    private Long finance;         // 금융
    private Long transportation;  // 교통
    private Long childcare;       // 육아
    private Long leisureCulture;  // 문화/여가
    private Long pet;             // 반려동물
    private Long eventGift;       // 경조/선물
}

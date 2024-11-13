package com.ssafy.TmT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsDTO {
	// 지난 달 지출
	private Long lastMonthExpense;
	
	// 이번 달 지출
	private Long thisMonthExpense;
	
	// 지난 주 지출
	private Long lastWeekExpense;
	
	// 이번 주 지출
	private Long thisWeekExpense;
	
}

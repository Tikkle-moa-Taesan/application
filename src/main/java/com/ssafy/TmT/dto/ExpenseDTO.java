package com.ssafy.TmT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseDTO {
	
	// 이번 달 카테고리별 지출
	private Category category;
	
	private StatisticsDTO statistics;


	
}

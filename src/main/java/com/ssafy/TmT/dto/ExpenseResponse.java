package com.ssafy.TmT.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseResponse {

	private Long lastMonthTotal;
	private Long thisMonthTotal;
	private Long lastWeekTotal;
	private Long thisWeekTotal;
	private Category category;
}

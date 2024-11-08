package com.ssafy.TmT.domain.account.dto;

import com.ssafy.TmT.trashbin.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseResponse {

	private Float lastMonthTotal;
	private Float thisMonthTotal;
	private Category category;
}

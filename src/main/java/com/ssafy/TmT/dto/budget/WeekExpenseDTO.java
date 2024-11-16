package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeekExpenseDTO {

	private Long budgetId;
	private int weekOffset;
}

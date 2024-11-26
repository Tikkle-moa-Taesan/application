package com.ssafy.TmT.dto.budget;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateBudgetTransactionsDTO {

	private Long memberId;
	private Long budgetId;
	private LocalDate createdMonth;
}

package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphResponse {
	
	private BudgetRateResponse thisMonthRate;
	private BudgetRateResponse oneMonthBeforeRate;
	private BudgetRateResponse twoMonthBeforeRate;
	private BudgetRateResponse threeMonthBeforeRate;
	private BudgetRateResponse fourMonthBeforeRate;
	private BudgetRateResponse fiveMonthBeforeRate;

}

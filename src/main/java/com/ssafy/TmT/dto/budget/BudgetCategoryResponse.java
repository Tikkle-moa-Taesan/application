package com.ssafy.TmT.dto.budget;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetCategoryResponse {
	private Long foodBudget;            // 식비
    private Long livingBudget;          // 생활
    private Long housingCommunicationBudget; // 주거/통신
    private Long financeBudget;         // 금융
    private Long transportationBudget;  // 교통
    private Long childcareBudget;       // 육아
    private Long leisureCultureBudget;  // 문화/여가
    private Long petBudget;             // 반려동물
    private Long eventGiftBudget;       // 경조/선물
}

package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private Long budgetId;
    private Long monthBudget;
    private Long foodBudget;
    private Long livingBudget;
    private Long housingCommunicationBudget;
    private Long financeBudget;
    private Long transportationBudget;
    private Long childcareBudget;
    private Long leisureCultureBudget;
    private Long petBudget;
    private Long eventGiftBudget;
    private LocalDateTime createdAt;
}
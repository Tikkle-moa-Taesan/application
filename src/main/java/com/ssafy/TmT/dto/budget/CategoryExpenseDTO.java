package com.ssafy.TmT.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExpenseDTO {

    private Long foodExpense;            // 식비
    private Long livingExpense;          // 생활
    private Long housingCommunicationExpense; // 주거/통신
    private Long financeExpense;         // 금융
    private Long transportationExpense;  // 교통
    private Long childcareExpense;       // 육아
    private Long leisureCultureExpense;  // 문화/여가
    private Long petExpense;             // 반려동물
    private Long eventGiftExpense;       // 경조/선물
}

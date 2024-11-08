package com.ssafy.TmT.domain.budget.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private int food;            // 식비
    private int living;          // 생활
    private int housingCommunication; // 주거/통신
    private int finance;         // 금융
    private int transportation;  // 교통
    private int childcare;       // 육아
    private int leisureCulture;  // 문화/여가
    private int pet;             // 반려동물
    private int eventGift;       // 경조/선물
}

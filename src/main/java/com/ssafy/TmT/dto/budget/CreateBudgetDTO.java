package com.ssafy.TmT.dto.budget;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBudgetDTO {

	private Long memberId;
	private Long monthBudget;
	private LocalDateTime createdAt;
	
    public CreateBudgetDTO(Long memberId, Long monthBudget) {
        this.memberId = memberId;
        this.monthBudget = monthBudget;
    }
}

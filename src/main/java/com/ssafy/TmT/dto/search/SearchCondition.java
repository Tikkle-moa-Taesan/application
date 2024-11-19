package com.ssafy.TmT.dto.search;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCondition {

	private Long accountId;
	private int offset = 0;
	private int period = 0;	// 이번달, 최근3달, 최근6달
	private String transactionType;	// expense, income
	private LocalDateTime startDate;
	private int size = 20;
}

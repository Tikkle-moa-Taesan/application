package com.ssafy.TmT.dto.notUsed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {

	private Long memberId;
	private String period;
	private String transactionType;
	private String accountType;
	private String accountId;
}

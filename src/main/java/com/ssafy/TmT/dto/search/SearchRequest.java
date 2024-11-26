package com.ssafy.TmT.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchRequest {

	private int period;
	private String transactionType;
}

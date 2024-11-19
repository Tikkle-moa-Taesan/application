package com.ssafy.TmT.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

	private int period;
	private String transactionType;
}

package com.ssafy.TmT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {

	// 총 자산(금액만 나오면 됨)
	private Long total;
}

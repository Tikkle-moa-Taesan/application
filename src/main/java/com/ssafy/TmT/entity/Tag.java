package com.ssafy.TmT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
	
	private Long tagId;
	private String tagName;
	private Long transactionId;

}

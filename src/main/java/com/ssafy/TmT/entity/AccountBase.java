package com.ssafy.TmT.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBase {
	
    private Long id;
    private String accountNumber;
    private String accountHolder;
    private Long memberId; // 계좌 소유자의 ID (외래키 역할)
    
}

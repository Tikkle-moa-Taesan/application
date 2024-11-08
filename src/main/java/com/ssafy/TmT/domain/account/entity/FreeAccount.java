package com.ssafy.TmT.domain.account.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeAccount extends AccountBase {
    private boolean overdraftEnabled; // 마이너스 통장 여부
}
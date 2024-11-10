package com.ssafy.TmT.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAccount extends AccountBase {
    private LocalDate maturityDate; // 만기일
    private double interestRate;    // 이율

}

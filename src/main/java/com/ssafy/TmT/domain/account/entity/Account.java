package com.ssafy.TmT.domain.account.entity;

import java.math.BigDecimal;

import com.ssafy.TmT.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account") // 테이블 이름 명시
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id") // 컬럼 이름 명시
    private Long accountId;

    @Column(name = "account_number", length = 20, nullable = false) // 컬럼 길이 및 null 허용 여부 지정
    private String accountNumber;

    @Column(name = "bank_name", length = 50, nullable = false)
    private String bankName;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)	// 조회할때만 갱신
    @JoinColumn(name = "member_id") // 외래 키 이름 명시
    private Member member;
}

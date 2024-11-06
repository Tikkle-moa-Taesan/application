package com.ssafy.TmT.domain.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.TmT.domain.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
//    Optional<List<Account>> findAllByMember_MemberId(Long memberId); // memberId로 계좌 조회
	List<Account> findAllByMember_MemberId(Long memberId); // Optional 제거
}
package com.ssafy.TmT.domain.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.TmT.domain.account.entity.Account;

// j p a 
// java persistence api
public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findAllByMember_MemberId(Long memberId); // Optional 제거
}
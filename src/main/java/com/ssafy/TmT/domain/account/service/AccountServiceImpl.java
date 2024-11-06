package com.ssafy.TmT.domain.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.domain.account.entity.Account;
import com.ssafy.TmT.domain.account.exception.AccountNotFoundException;
import com.ssafy.TmT.domain.account.repository.AccountRepository;
import com.ssafy.TmT.domain.api.ApiUtil;
import com.ssafy.TmT.global.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final JwtUtil jwtUtil;
	private final AccountRepository accountRepository;
	
	
	@Override
	public List<AccountResponse> findAllAccountsByMember(HttpHeaders headers) {
		Long memberId  = jwtUtil.getMemberIdFromHeaders(headers);
		List<Account> accounts = accountRepository.findAllByMember_MemberId(memberId);

		// 계좌가 없으면 예외 발생
	    if (accounts.isEmpty()) {
	        throw new AccountNotFoundException("Account not found for memberId: " + memberId);
	    }
	    
	    // Account 엔티티 목록을 AccountResponse로 매핑하여 반환
	    return accounts.stream()
	                   .map(AccountMapper.INSTANCE::toAccountResponse)
	                   .collect(Collectors.toList());
	}

	@Override
	public AccountResponse findAccountById(HttpHeaders headers) {
		Long userId = jwtUtil.getMemberIdFromHeaders(headers);
		Account account = accountRepository.findById(userId).orElseThrow(() -> new AccountNotFoundException("Account not found for userId: " + userId));
		return AccountMapper.INSTANCE.toAccountResponse(account);
	}

}

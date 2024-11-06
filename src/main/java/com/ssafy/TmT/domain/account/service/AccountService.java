package com.ssafy.TmT.domain.account.service;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.ssafy.TmT.domain.account.dto.AccountResponse;

public interface AccountService {

	List<AccountResponse> findAllAccountsByMember(HttpHeaders headers);
	
	AccountResponse findAccountById(HttpHeaders headers);
	
}

package com.ssafy.TmT.domain.account.service;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.domain.account.dto.BalanceDTO;
import com.ssafy.TmT.domain.account.dto.FreeAccountDTO;
import com.ssafy.TmT.domain.account.dto.SavingsAccountDTO;

public interface AccountService {

	List<AccountResponse> findAccountsByType(HttpHeaders headers);
	
	AccountResponse findAccountById(HttpHeaders headers);

	List<AccountResponse> findAccountsByType(String type, HttpHeaders headers);

	List<FreeAccountDTO> findFreeAccounts(HttpHeaders headers);

	List<SavingsAccountDTO> findSavingsAccounts(HttpHeaders headers);

	BalanceDTO getTotalBalance(HttpHeaders headers);
	
}

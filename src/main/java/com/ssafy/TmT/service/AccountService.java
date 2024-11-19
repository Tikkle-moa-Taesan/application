package com.ssafy.TmT.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailDTO;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailDTO;
import com.ssafy.TmT.dto.account.SavingsAccountDetailResponse;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.dto.notUsed.SearchCondition;
import com.ssafy.TmT.dto.transaction.TransactionDTO;
import com.ssafy.TmT.util.JwtUtil;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService {

	private final AccountDao accountDao;
	
	private final TransactionDao transactionDao;

	public List<FreeAccountResponse> findFreeAccounts() {
		Long memberId = getAuthenticatedMemberId();
		List<FreeAccountResponse> accounts = accountDao.findFreeAccounts(memberId);
		return accounts;
	}
	
	public BalanceResponse getTotalBalance() {
		Long memberId = getAuthenticatedMemberId();
		BalanceResponse balance = accountDao.getTotalBalance(memberId);
		
		return balance;
	}



	public List<SavingsAccountResponse> findSavingsAccounts() {
		Long memberId = getAuthenticatedMemberId();
		List<SavingsAccountResponse> accounts = accountDao.findSavingsAccounts(memberId);
		return accounts;
	}
	
	public SavingsAccountDetailResponse getSavingAccountDetail(Long accountId, int page) {
		int offset = page * 20;
		SavingsAccountDetailDTO savingsAccountDto = accountDao.findSavingsAccountByAccountId(accountId);
		List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(accountId, offset);
		SavingsAccountDetailResponse response = new SavingsAccountDetailResponse(savingsAccountDto, transactions);
		return response;
	}

	// 작성 끝. 테스트 시작.
	public FreeAccountDetailResponse findFreeAccountDetail(Long accountId, int page) {
		int offset = page * 20;	// 사이즈를 5로 설정
		FreeAccountDetailDTO freeAccountDto = accountDao.findFreeAccountByAccountId(accountId);
		List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(accountId, offset);
		FreeAccountDetailResponse response = new FreeAccountDetailResponse(freeAccountDto, transactions);
		return response;
	}
	
	private Long getAuthenticatedMemberId() {
		return SecurityUtil.getAuthenticatedMemberId();
	}
	
}

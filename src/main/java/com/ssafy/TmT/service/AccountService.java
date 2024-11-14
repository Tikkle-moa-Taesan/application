package com.ssafy.TmT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dto.AccountDTO;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.FreeAccountDTO;
import com.ssafy.TmT.dto.SavingsAccountDTO;
import com.ssafy.TmT.dto.SearchCondition;
import com.ssafy.TmT.util.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService {

	private final AccountDao accountDao;

//	private final JwtUtil jwtUtil;

	public BalanceDTO getTotalBalance() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 총 자산 찾기 위해서는 내 아이디를 가진 모든 계좌를 찾고, 그 금액을 갱신해야 함.
		BalanceDTO balance = accountDao.getTotalBalance(memberId);
		
		return balance;
	}

	public List<FreeAccountDTO> findFreeAccounts() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		List<FreeAccountDTO> accounts = accountDao.findFreeAccounts(memberId);
		return accounts;
	}

	public List<SavingsAccountDTO> findSavingsAccounts() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		List<SavingsAccountDTO> accounts = accountDao.findSavingsAccounts(memberId);
		return accounts;
	}

	public FreeAccountDTO findFreeAccountDetail(Long accountId) {
//		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FreeAccountDTO account = accountDao.findFreeAccountByAccountId(accountId);
		return account;
	}
	
	public SavingsAccountDTO getSavingAccountDetail(Long accountId) {
		SavingsAccountDTO account = accountDao.findSavingsAccountByAccountId(accountId);
		return account;
	}

	public List<AccountDTO> findAccountsBySearchCondition(SearchCondition searchCondition) {
//		searchCondition.setMemberId(jwtUtil.getMemberIdFromJwt(jwt));
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		searchCondition.setMemberId(memberId);
		List<AccountDTO> accounts = accountDao.findAccountsBySearchCondition(searchCondition);
		return accounts;
	}

}

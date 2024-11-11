package com.ssafy.TmT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.FreeAccountDTO;
import com.ssafy.TmT.dto.SavingsAccountDTO;
import com.ssafy.TmT.util.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService {

	private final AccountDao accountDao;
	
	private final JwtUtil jwtUtil;
	

	public List<FreeAccountDTO> findFreeAccounts(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		List<FreeAccountDTO> accounts = accountDao.findFreeAccounts(memberId);
		return accounts;
	}

	public List<SavingsAccountDTO> findSavingsAccounts(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		List<SavingsAccountDTO> accounts = accountDao.findSavingsAccounts(memberId);
		return accounts;
	}

	public BalanceDTO getTotalBalance(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		
		// 총 자산 찾기 위해서는 내 아이디를 가진 모든 계좌를 찾고, 그 금액을 갱신해야 함.
		BalanceDTO balance = accountDao.getTotalBalance(memberId);
		
		return balance;
	}

	public FreeAccountDTO getFreeAccountDetail(Long accountId, String jwt) {
		// 계좌 주인 알아보기
		Long memberId = accountDao.findMemberIdFromFreeAccount(accountId);
		if (!jwtUtil.validateAccountId(jwt,memberId)) {
			System.out.println("memberId값 : " + memberId);
			System.out.println("여기서 예외 던저야함");
			return null;
		}
		
		FreeAccountDTO account = accountDao.findFreeAccountByAccountId(accountId);
		return account;
	}

	public SavingsAccountDTO getSavingAccountDetail(Long accountId, String jwt) {
		Long memberId = accountDao.findMemberIdFromSavingsAccount(accountId);
		if (!jwtUtil.validateAccountId(jwt,memberId)) {
			System.out.println("memberId값 : " + memberId);
			System.out.println("여기서 예외 던저야함");
			return null;
		}
		System.out.println("서비스 메서드 정상 실행");
		SavingsAccountDTO account = accountDao.findSavingsAccountByAccountId(accountId);
		System.out.println(account);
		return account;
	}

}

package com.ssafy.TmT.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.TransactionDao;
//import com.ssafy.TmT.dto.AccountDTO;
import com.ssafy.TmT.dto.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.SearchCondition;
import com.ssafy.TmT.dto.TransactionDTO;
import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailDTO;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailDTO;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.util.JwtUtil;

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
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<FreeAccountResponse> accounts = accountDao.findFreeAccounts(memberId);
		
		return accounts;
	}
	
	public BalanceResponse getTotalBalance() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 총 자산 찾기 위해서는 내 아이디를 가진 모든 계좌를 찾고, 그 금액을 갱신해야 함.
		BalanceResponse balance = accountDao.getTotalBalance(memberId);
		
		return balance;
	}



	public List<SavingsAccountResponse> findSavingsAccounts() {
		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		List<SavingsAccountResponse> accounts = accountDao.findSavingsAccounts(memberId);
		return accounts;
	}
	
	public SavingsAccountResponse getSavingAccountDetail(Long accountId) {
		SavingsAccountDetailDTO savingsAccountDto = accountDao.findSavingsAccountByAccountId(accountId);
		List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(accountId);
		SavingsAccountResponse response = new SavingsAccountResponse(savingsAccountDto, transactions);
		return response;
	}

	// 작성 끝. 테스트 시작.
	public FreeAccountDetailResponse findFreeAccountDetail(Long accountId) {
		FreeAccountDetailDTO freeAccountDto = accountDao.findFreeAccountByAccountId(accountId);
		List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(accountId);
		FreeAccountDetailResponse response = new FreeAccountDetailResponse(freeAccountDto, transactions);
		return response;
	}
	

	


//	public List<AccountDTO> findAccountsBySearchCondition(SearchCondition searchCondition) {
////		searchCondition.setMemberId(jwtUtil.getMemberIdFromJwt(jwt));
//		Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		searchCondition.setMemberId(memberId);
//		List<AccountDTO> accounts = accountDao.findAccountsBySearchCondition(searchCondition);
//		return accounts;
//	}
	
//	<!-- 계좌 조회 (동적 쿼리로 계좌 유형별 조회) -->
//<select id="findAccountsBySearchCondition" parameterType="SearchCondition"
//	resultType="AccountDTO">
//			SELECT
//			account_id,
//			account_number,
//			account_name,
//			bank_name,
//			balance
//			FROM Account
//			WHERE member_id = #{memberId} 
//</select>

}

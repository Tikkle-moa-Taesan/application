package com.ssafy.TmT.service;

import java.time.LocalDateTime;
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
import com.ssafy.TmT.dto.search.SearchCondition;
import com.ssafy.TmT.dto.search.SearchRequest;
import com.ssafy.TmT.dto.transaction.TransactionDTO;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
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
	
	public SavingsAccountDetailResponse getSavingAccountDetail(Long accountId, int page, SearchRequest request) {
	    if (request == null) {
	    	request = new SearchRequest(); // 기본값 설정
	    }

	    SearchCondition searchCondition = new SearchCondition();
	    // SearchCondition에 기본 필드 설정
	    searchCondition.setAccountId(accountId);
	    searchCondition.setOffset(page * searchCondition.getSize());
	    searchCondition.setTransactionType(request.getTransactionType());
	    searchCondition.setPeriod(request.getPeriod());
	    
	    // 시작 날짜 계산
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime startDate;

	    if (request.getPeriod() == 3) {
	        startDate = now.minusMonths(2).withDayOfMonth(1); // 최근 3개월
	    } else if (request.getPeriod() == 6) {
	        startDate = now.minusMonths(5).withDayOfMonth(1); // 최근 6개월
	    } else {
	        startDate = now.withDayOfMonth(1); // 이번 달
	    }

	    searchCondition.setStartDate(startDate);

	    // 적금 계좌 정보 조회
	    SavingsAccountDetailDTO savingsAccountDto = accountDao.findSavingsAccountByAccountId(accountId);

	    if (savingsAccountDto == null) throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);

	    
	    // 조건에 맞는 거래내역 조회
	    List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(searchCondition);

	    // 응답 생성 및 반환
	    return new SavingsAccountDetailResponse(savingsAccountDto, transactions);
	}

	public FreeAccountDetailResponse findFreeAccountDetail(Long accountId, int page, SearchRequest request) {

		System.out.println(request);
		if (request == null) {
	    	request = new SearchRequest(); // 기본값 설정
	    }

		System.out.println(request.getPeriod());
	    SearchCondition searchCondition = new SearchCondition();
	    // SearchCondition에 기본 필드 설정
	    searchCondition.setAccountId(accountId);
	    searchCondition.setOffset(page * searchCondition.getSize());
	    searchCondition.setTransactionType(request.getTransactionType());
	    searchCondition.setPeriod(request.getPeriod());
	    
	    // 시작 날짜 계산
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime startDate;

	    if (request.getPeriod() == 3) {
	    	System.out.println("3개월입니다");
	    	
	        startDate = now.minusMonths(2).withDayOfMonth(1); // 최근 3개월
	    } else if (request.getPeriod() == 6) {
	    	System.out.println("6개월입니다");
	        startDate = now.minusMonths(5).withDayOfMonth(1); // 최근 6개월
	    } else {
	        startDate = now.withDayOfMonth(1); // 이번 달
	    }

	    searchCondition.setStartDate(startDate);

	    // 자유 입출금 계좌 정보 조회
	    FreeAccountDetailDTO freeAccountDto = accountDao.findFreeAccountByAccountId(accountId);

	    if (freeAccountDto == null) throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
	    
	    // 조건에 맞는 거래내역 조회
	    List<TransactionDTO> transactions = transactionDao.findTransactionsByAccountId(searchCondition);

	    System.out.println(searchCondition);
	    // 응답 생성 및 반환
	    return new FreeAccountDetailResponse(freeAccountDto, transactions);
	}
	
	private Long getAuthenticatedMemberId() {
		return SecurityUtil.getAuthenticatedMemberId();
	}
	
}

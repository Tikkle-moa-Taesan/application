package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.AdminDao;
import com.ssafy.TmT.dto.admin.InsertTransactionRequest;
import com.ssafy.TmT.dto.transaction.TransactionType;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminDao adminDao;
	private final AccountDao accountDao;
	
	@Transactional
	public void insertTransaction(InsertTransactionRequest request) {
		// 어떤 정보가 필요한가?
		Long balance = accountDao.findBalance(request.getAccountId()).orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
		Long amount = request.getAmount();
		if (request.getTransactionType() == TransactionType.expense) {
			balance -= amount;
		} else if (request.getTransactionType() == TransactionType.income) {
			balance += amount;
		}
		request.setBalanceAfter(balance);
		// transaction
		int result = adminDao.insertTransaction(request);
		
		if (result == 0) throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);

		return;
	}
}

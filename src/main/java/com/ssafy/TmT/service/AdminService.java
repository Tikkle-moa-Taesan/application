package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.protocol.Security;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.AdminDao;
import com.ssafy.TmT.dto.admin.InsertAccountDTO;
import com.ssafy.TmT.dto.admin.InsertAccountRequest;
import com.ssafy.TmT.dto.admin.InsertTransactionDTO;
import com.ssafy.TmT.dto.admin.InsertTransactionRequest;
import com.ssafy.TmT.dto.transaction.TransactionType;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.SecurityUtil;

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
		InsertTransactionDTO dto = new InsertTransactionDTO(request.getAccountId(), request.getCategoryCode(), request.getTransactionDatetime(), amount, balance, request.getMerchantName(), request.getTransactionType());
		// transaction
		int result = adminDao.insertTransaction(dto);
		
		if (result == 0) throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);

		return;
	}

	public void insertAccount(InsertAccountRequest request) {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		
		InsertAccountDTO dto = new InsertAccountDTO(request.getAccountType(), request.getAccountNumber(), request.getAccountName(), request.getBankName(), request.getInterestRate(), memberId);

		int result = adminDao.insertAccount(dto);
		if (result == 0) throw new CustomException(ErrorCode.ACCOUNT_CREATION_FAILED);
		
	}
}

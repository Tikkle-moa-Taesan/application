package com.ssafy.TmT.service;

import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.dto.TransactionDTO;

@Service
public class TransactionService {
	

	public ExpenseResponse getMonthlyExpenses(HttpHeaders headers) {
		return null;
	}

	public List<TransactionDTO> findTransactionsByAccountId(Long accountId) {
		return null;
	}


}

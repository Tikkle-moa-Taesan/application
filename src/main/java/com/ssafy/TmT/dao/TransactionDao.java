package com.ssafy.TmT.dao;

import java.util.List;

import com.ssafy.TmT.dto.transaction.TransactionDTO;

public interface TransactionDao {

	List<TransactionDTO> findTransactionsByAccountId(Long accountId);

}

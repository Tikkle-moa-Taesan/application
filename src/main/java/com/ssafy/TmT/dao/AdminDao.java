package com.ssafy.TmT.dao;

import com.ssafy.TmT.dto.admin.InsertAccountDTO;
import com.ssafy.TmT.dto.admin.InsertTransactionDTO;
import com.ssafy.TmT.dto.admin.InsertTransactionRequest;

public interface AdminDao {

	int insertTransaction(InsertTransactionDTO request);

	int insertAccount(InsertAccountDTO dto);
}

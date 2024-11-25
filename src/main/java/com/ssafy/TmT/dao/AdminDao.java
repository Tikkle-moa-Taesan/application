package com.ssafy.TmT.dao;

import com.ssafy.TmT.dto.admin.InsertTransactionRequest;

public interface AdminDao {

	int insertTransaction(InsertTransactionRequest request);
}

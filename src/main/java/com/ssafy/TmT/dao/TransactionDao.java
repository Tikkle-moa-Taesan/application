package com.ssafy.TmT.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.TmT.dto.search.SearchCondition;
import com.ssafy.TmT.dto.transaction.TransactionDTO;

public interface TransactionDao {

	List<TransactionDTO> findTransactionsByAccountId(SearchCondition searchCondition);
}

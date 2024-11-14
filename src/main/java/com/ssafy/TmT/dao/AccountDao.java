package com.ssafy.TmT.dao;

import java.util.List;

import com.ssafy.TmT.dto.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.SearchCondition;
import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailDTO;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailDTO;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;

public interface AccountDao {

	// 1번 api. 자유 계좌 조회
	List<FreeAccountResponse> findFreeAccounts(Long MemberId);

	// 2번 api. 적금 계좌 조회
	List<SavingsAccountResponse> findSavingsAccounts(Long MemberId);

	// 3번 api. 총 자산 조회
	BalanceResponse getTotalBalance(Long MemberId);

	FreeAccountDetailDTO findFreeAccountByAccountId(Long accountId);

	SavingsAccountDetailDTO findSavingsAccountByAccountId(Long accountId);
	
//	List<AccountDTO> findAccountsBySearchCondition(SearchCondition searchCondition);

//	Long findMemberIdByAccountId(Long accountId);
	
}

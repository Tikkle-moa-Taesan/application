package com.ssafy.TmT.dao;

import java.util.List;

import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.FreeAccountDTO;
import com.ssafy.TmT.dto.SavingsAccountDTO;

public interface AccountDao {

	// 1번 api. 자유 계좌 조회
	List<FreeAccountDTO> findFreeAccounts(Long MemberId);

	// 2번 api. 적금 계좌 조회
	List<SavingsAccountDTO> findSavingsAccounts(Long MemberId);

	// 3번 api. 총 자산 조회
	BalanceDTO getTotalBalance(Long MemberId);

	Long findMemberIdFromFreeAccount(Long accountId);

	Long findMemberIdFromSavingsAccount(Long accountId);

	SavingsAccountDTO findSavingsAccountByAccountId(Long accountId);

	FreeAccountDTO findFreeAccountByAccountId(Long accountId);
}

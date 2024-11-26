package com.ssafy.TmT.dao;

import java.util.List;
import java.util.Optional;

import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailDTO;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailDTO;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.entity.Account;

public interface AccountDao {

	List<FreeAccountResponse> findFreeAccounts(Long MemberId);

	List<SavingsAccountResponse> findSavingsAccounts(Long MemberId);

	BalanceResponse getTotalBalance(Long MemberId);

	FreeAccountDetailDTO findFreeAccountByAccountId(Long accountId);

	SavingsAccountDetailDTO findSavingsAccountByAccountId(Long accountId);

	Optional<Long> findBalance(Long accountId);

	int insertAccounts(List<Account> accounts);
	
	int updateBalance(Account account);
	
}

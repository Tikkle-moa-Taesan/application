package com.ssafy.TmT.dao;


import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.Profile;
import com.ssafy.TmT.entity.Member;

public interface MemberDao {

	void regist(String subject);

	Profile login(String subject);

	Long getFreeAccountBalanceSum(Long memberId);

	Long getSavingsAccountBalanceSum(Long memberId);

}

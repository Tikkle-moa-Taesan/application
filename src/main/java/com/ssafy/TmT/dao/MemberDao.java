package com.ssafy.TmT.dao;


import org.apache.ibatis.annotations.Param;

import com.ssafy.TmT.dto.member.MemberPreviewDataDTO;
import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
import com.ssafy.TmT.dto.member.ModifyBudgetTransactionRequest;
import com.ssafy.TmT.dto.oauth.LoginDTO;
import com.ssafy.TmT.dto.oauth.Profile;

public interface MemberDao {

	void regist(LoginDTO loginDto);

	Profile login(String name);

	Profile getProfile(Long memberId);

	MemberTotalDataDTO getAllData(Long memberId);

	MemberPreviewDataDTO getPreviewData(Long memberId);

	int modifyBudgetTransaction(@Param("budgetTransactionId") Long transactionId, @Param("request") ModifyBudgetTransactionRequest request);

	int removeBudgetTransaction(Long budgetTransactionId);

}

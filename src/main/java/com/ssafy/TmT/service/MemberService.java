package com.ssafy.TmT.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dto.member.ModifyBudgetTransactionRequest;
import com.ssafy.TmT.dto.oauth.LoginDTO;
import com.ssafy.TmT.dto.oauth.Profile;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDao memberDao;

	@Transactional
	public void register(LoginDTO loginDTO) {
		System.out.println("회원가입 메서드 실행");
		memberDao.regist(loginDTO);
		System.out.println("회원가입 완료");
	}

	@Transactional
	public Profile login(LoginDTO loginDTO) {
		String name = loginDTO.getName();
		// 처음 로그인하는 경우 : 회원가입
		if ((memberDao.login(name) == null)) {
			System.out.println("회원가입으로 연결");
			register(loginDTO);
		}
		
		Profile profile = memberDao.login(name);
		System.out.println("로그인 완료 : " + profile);
		return profile;
	}

	public Profile getProfile() {
		Long memberId = getAuthenticatedMemberId();
		Profile profile = memberDao.getProfile(memberId);
		return profile;
	}
	

	private Long getAuthenticatedMemberId() {
		return SecurityUtil.getAuthenticatedMemberId();
	}

	@Transactional
	public void modifyBudgetTransaction(Long budgetTransactionId, ModifyBudgetTransactionRequest request) {
		int result = memberDao.modifyBudgetTransaction(budgetTransactionId, request);
		if (result == 0) throw new CustomException(ErrorCode.BUDGET_TRANSACTION_MODIFY_FAILED);
		return;
	}

	@Transactional
	public void removeBudgetTransaction(Long budgetTransactionId) {
	    int result = memberDao.removeBudgetTransaction(budgetTransactionId);
	    if (result == 0) {
	        throw new CustomException(ErrorCode.BUDGET_TRANSACTION_DELETE_FAILED);
	    }
	}
	
}
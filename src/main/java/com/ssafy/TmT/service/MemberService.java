package com.ssafy.TmT.service;

import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.IdTokenPayload;
import com.ssafy.TmT.dto.Profile;
import com.ssafy.TmT.entity.Member;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final JwtUtil jwtUtil;
	private final MemberDao memberDao;
	


	public BalanceDTO getTotalBalance(String jwt) {
		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
		Long total = getTotalBalance(memberId);
		BalanceDTO response = new BalanceDTO(total);
		return response;
	}
	
	



	@Transactional
	public void register(String subject) {
		System.out.println("회원가입 메서드 실행");
		memberDao.regist(subject);
		System.out.println("회원가입 완료");
	}

	@Transactional
	public Profile login(String subject) {
		// 처음 로그인하는 경우 : 회원가입
		if ((memberDao.login(subject) == null)) {
			System.out.println("회원가입으로 연결");
			register(subject);
		}
		
		Profile profile = memberDao.login(subject);
		System.out.println("로그인 완료 : " + profile);
		return profile;
	}
	
	private Long getTotalBalance(Long memberId) {
		Long freeAccountBalance = memberDao.getFreeAccountBalanceSum(memberId);
		Long savingsAccountBalance = memberDao.getSavingsAccountBalanceSum(memberId);
		return freeAccountBalance+savingsAccountBalance;
	}
	
}

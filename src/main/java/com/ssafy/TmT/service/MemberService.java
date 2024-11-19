package com.ssafy.TmT.service;


import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dto.oauth.IdTokenPayload;
import com.ssafy.TmT.dto.oauth.Profile;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDao memberDao;

//	@Transactional
	public void register(IdTokenPayload idTokenPayload) {
		System.out.println("회원가입 메서드 실행");
		memberDao.regist(idTokenPayload);
		System.out.println("회원가입 완료");
	}

//	@Transactional
	public Profile login(IdTokenPayload idTokenPayload) {
		String subject = idTokenPayload.getSub();
		// 처음 로그인하는 경우 : 회원가입
		if ((memberDao.login(subject) == null)) {
			System.out.println("회원가입으로 연결");
			register(idTokenPayload);
		}
		
		Profile profile = memberDao.login(subject);
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
	
}
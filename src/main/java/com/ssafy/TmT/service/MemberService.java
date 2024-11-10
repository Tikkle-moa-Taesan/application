package com.ssafy.TmT.service;

import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.IdTokenPayload;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final JwtUtil jwtUtil;
	private final MemberDao memberDao;
	


	public BalanceDTO getTotalBalance(HttpHeaders headers) {
		// 어떻게 total Balance 를 모을꺼냐
		Long memberId = jwtUtil.getMemberIdFromHeaders(headers);
		BalanceDTO response = memberDao.getTotalBalance(memberId);
		return response;
	}

	
}

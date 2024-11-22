package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dao.OpenAIDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
import com.ssafy.TmT.dto.openAI.OpenAIResponse;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.OpenAIUtil;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {

	private final MemberDao memberDao;
	private final OpenAIUtil openAIUtil;
	private final ObjectMapper objectMapper;

	public OpenAIResponse getAdvice() {
		// 회원 정보 가져오기
		Long memberId = SecurityUtil.getAuthenticatedMemberId();

		// 회원 데이터 가져오기
		MemberTotalDataDTO data = memberDao.getAllData(memberId);
		if (data == null) throw new CustomException(ErrorCode.MEMBER_DATA_NOT_FOUND);

		// JSON 데이터로 파싱하기
		try {
			// 2. 데이터를 JSON으로 변환
			String jsonData = objectMapper.writeValueAsString(data);

			// 3. OpenAI API 호출
			return openAIUtil.generateInsights(jsonData);
		} catch (JsonProcessingException e) {
			throw new CustomException(ErrorCode.DATA_SERIALIZATION_FAILED);
		}
	}
}

//package com.ssafy.TmT.service;
//
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.TmT.dao.AccountDao;
//import com.ssafy.TmT.dao.BudgetDao;
//import com.ssafy.TmT.dao.MemberDao;
//import com.ssafy.TmT.dao.OpenAIDao;
//import com.ssafy.TmT.dao.TransactionDao;
//import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
//import com.ssafy.TmT.dto.openAI.OpenAIResponse;
//import com.ssafy.TmT.exception.CustomException;
//import com.ssafy.TmT.exception.ErrorCode;
//import com.ssafy.TmT.util.OpenAIUtil;
//import com.ssafy.TmT.util.SecurityUtil;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class OpenAIService {
//	
//	private final MemberDao memberDao;
//	private final OpenAIUtil openAIUtil;
//	private final ObjectMapper objectMapper;
//	
//	public OpenAIResponse getAdvice() {
//		Long memberId = SecurityUtil.getAuthenticatedMemberId();
//		MemberTotalDataDTO data = memberDao.getAllData(memberId).orElseThrow(() -> new CustomException());
//		String jsonData = objectMapper.writeValueAsString(data);
//		String response = openAIUtil.generateInsights(jsonData);
//		return response;
//	}
//}

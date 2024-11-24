package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dao.OpenAIDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.member.MemberPreviewDataDTO;
import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
import com.ssafy.TmT.dto.openAI.AIPreviewResponse;
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
	private final BudgetService budgetService;
	private final OpenAIUtil openAIUtil;
	private final ObjectMapper objectMapper;

	public OpenAIResponse getAdvice() {
		// 회원 정보 가져오기
		Long memberId = SecurityUtil.getAuthenticatedMemberId();

		// 회원 데이터 가져오기
		MemberTotalDataDTO data = memberDao.getAllData(memberId);
		if (data == null)
			throw new CustomException(ErrorCode.MEMBER_DATA_NOT_FOUND);

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

	// preview
	public AIPreviewResponse showPreview() {
		// 회원 데이터 가져오기
//		MemberPreviewDataDTO data = memberDao.getPreviewData(memberId);
		ExpenseResponse data = budgetService.calculateExpenseAndBudget();

		if (data == null)
			throw new CustomException(ErrorCode.MEMBER_DATA_NOT_FOUND);

		try {
//	        String jsonData = objectMapper.writeValueAsString(data);

			// JSON 데이터 생성
			String prompt = buildPrompt(data);
			OpenAIResponse response = openAIUtil.generateInsights(prompt);

			// content만 반환
			if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
				return new AIPreviewResponse(response.getChoices().get(0).getMessage().getContent());
			}
			throw new CustomException(ErrorCode.OPENAI_RESPONSE_INVALID);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.DATA_SERIALIZATION_FAILED);
		}
	}

	// 프롬프트 작성 메서드
	private String buildPrompt(ExpenseResponse data) {
		return String.format("다음은 사용자의 지출 데이터입니다:\n\n" + "1. 지난 달 지출: %d원\n" + "2. 지난 주 지출: %d원\n" + "3. 이번 주 지출: %d원\n"
				+ "4. 이번 달 지출: %d원\n" + "이 데이터를 기반으로 다음을 작성하세요:\n" + "1. 사용자의 지난 달 대비 이번 달 지출 변화율을 계산하여 간략히 요약.\n"
				+ "2. 지난 주 대비 이번 주 지출 변화율을 계산하여 간략히 요약.\n" + "3. 가장 지출이 큰 카테고리를 언급하고, 절약 방법을 2~3개 제안.\n"
				+ "4. 효율적인 예산 관리를 위한 행동 계획 1~2개를 추천.\n" + "5. 모든 내용을 간결하고 구체적으로 작성하세요.", data.getLastMonthExpense(),
				data.getLastWeekExpense(), data.getThisWeekExpense(), data.getThisMonthExpense());
	}
}

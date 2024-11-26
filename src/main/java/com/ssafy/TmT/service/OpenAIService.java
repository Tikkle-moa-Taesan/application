package com.ssafy.TmT.service;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.controller.impl.AccountControllerImpl;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.account.AccountDTO;
import com.ssafy.TmT.dto.budget.BudgetDTO;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.member.MemberPreviewDataDTO;
import com.ssafy.TmT.dto.openAI.AITextResponse;
import com.ssafy.TmT.dto.openAI.OpenAIResponse;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;
import com.ssafy.TmT.entity.Tag;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.OpenAIUtil;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAIService {

	private final MemberDao memberDao;
	private final BudgetService budgetService;
	private final OpenAIUtil openAIUtil;
//	private final int maxTokens = 10000; // OpenAI 모델 최대 토큰 수

	
	public AITextResponse getAdvice() {
	    log.info("서비스 : 전체 조언 받기");
	    Long memberId = SecurityUtil.getAuthenticatedMemberId();
	    String jsonData = memberDao.getAllData(memberId);

	    if (jsonData == null) {
	        throw new CustomException(ErrorCode.MEMBER_DATA_FETCH_FAILED);
	    }

	    // 데이터를 분할하여 처리
//	    List<String> jsonChunks = splitJsonIntoChunks(jsonData);
//	    StringBuilder combinedResponse = new StringBuilder();

	    String promt = buildAdvicePrompt(jsonData);
//	    for (int i = 0; i < jsonChunks.size(); i++) {
//	        log.info("Sending chunk {}/{} to OpenAI", i + 1, jsonChunks.size());
//	        String prompt = buildAdvicePrompt(jsonChunks.get(i));
//	        AITextResponse partialResponse = generateResponse(prompt);
//
//	        combinedResponse.append(partialResponse.getText());
//	        combinedResponse.append("\n\n"); // 응답 구분을 위한 추가
//	    }

	    // 최종 응답 생성
//	    return new AITextResponse(combinedResponse.toString().trim());
	    return generateResponse(promt);
	}
	
//	private List<String> splitJsonIntoChunks(String jsonData) {
//	    int maxTokensPerChunk = maxTokens - 1000; // 안전 범위를 두고 설정
//	    Map<String, Object> dataMap = convertJsonToMap(jsonData);
//	    List<Map<String, Object>> transactions = (List<Map<String, Object>>) dataMap.get("transactions");
//
//	    if (transactions == null || transactions.isEmpty()) {
//	        return List.of(jsonData); // 분할할 필요가 없는 경우
//	    }
//
//	    List<String> chunks = new ArrayList<>();
//	    int startIndex = 0;
//
//	    while (startIndex < transactions.size()) {
//	        // 일정 개수의 트랜잭션을 포함한 JSON 조각 생성
//	        int endIndex = Math.min(startIndex + calculateChunkSize(transactions, maxTokensPerChunk), transactions.size());
//	        List<Map<String, Object>> subList = transactions.subList(startIndex, endIndex);
//
//	        // JSON 조각 생성
//	        dataMap.put("transactions", subList);
//	        String chunkJson = convertToJson(dataMap);
//	        chunks.add(chunkJson);
//
//	        startIndex = endIndex;
//	    }
//
//	    return chunks;
//	}
	
//	private int calculateChunkSize(List<Map<String, Object>> transactions, int maxTokensPerChunk) {
//	    int estimatedTokensPerTransaction = 50; // 트랜잭션당 평균 토큰 수 추정
//	    return maxTokensPerChunk / estimatedTokensPerTransaction;
//	}
//	private String convertToJson(Object object) {
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    try {
//	        return objectMapper.writeValueAsString(object);
//	    } catch (JsonProcessingException e) {
//	        log.error("Failed to convert object to JSON", e);
//	        throw new CustomException(ErrorCode.JSON_PARSING_FAILED);
//	    }
//	}
	
	
	// preview
	public AITextResponse showPreview() {
		ExpenseResponse data = budgetService.calculateExpenseAndBudget();

		if (data == null)
			throw new CustomException(ErrorCode.MEMBER_DATA_FETCH_FAILED);
		String prompt = buildPreviewPrompt(data);
		return generateResponse(prompt);
	}


	private AITextResponse generateResponse(String prompt) {
		log.info("Sending Prompt to OpenAI: {}", prompt); // 전송 전 데이터 확인

		OpenAIResponse response = openAIUtil.generateInsights(prompt);
		if (response == null) {
			throw new CustomException(ErrorCode.OPENAI_NO_RESPONSE);
		}
		if (response.getChoices() == null || response.getChoices().isEmpty()) {
			throw new CustomException(ErrorCode.OPENAI_EMPTY_CHOICES);
		}
		if (response.getChoices().get(0).getMessage() == null) {
			throw new CustomException(ErrorCode.OPENAI_NO_MESSAGE_CONTENT);
		}

		// OpenAI 응답에서 메시지 가져오기
		String content = response.getChoices().get(0).getMessage().getContent();

		// 응답 데이터 로깅
		log.info("Response from OpenAI: {}", content);

		// 응답을 정형화된 JSON으로 가공
		String formattedResponse = formatResponse(content);

		return new AITextResponse(formattedResponse);
	}

	private String buildAdvicePrompt(String jsonData) {
	    StringBuilder prompt = new StringBuilder();

	    // OpenAI에게 JSON 데이터를 분석하도록 요청
	    prompt.append("다음은 사용자의 이번 달 데이터를 JSON 형식으로 제공합니다.\n");
	    prompt.append("이 데이터를 분석하여 아래와 같은 형식으로 결과를 작성하세요.\n\n");
	    prompt.append("1. 이번 달 가장 지출이 큰 카테고리: [카테고리 이름]\n");
	    prompt.append("2. 가장 지출이 큰 거래내역: [거래 이름] - [금액] ([거래 일자 및 시간])\n");
	    prompt.append("3. 가장 돈을 많이 쓴 달: [달 이름]\n");
	    prompt.append("4. 거래 내역 분석 및 조언:\n");
	    prompt.append("   - [조언 1]\n");
	    prompt.append("   - [조언 2]\n");
	    prompt.append("   - [조언 3]\n\n");

	    prompt.append("사용자 데이터(JSON):\n");
	    prompt.append(jsonData);

	    log.info("Generated Prompt for OpenAI: {}", prompt.toString());
	    return prompt.toString();
	}

	private String buildPreviewPrompt(ExpenseResponse data) {
		StringBuilder prompt = new StringBuilder();

		// 지출 데이터
		prompt.append("다음은 사용자의 지출 데이터입니다:\n\n");
		prompt.append(String.format("1. 지난 달 지출: %d원\n", data.getLastMonthExpense()));
		prompt.append(String.format("2. 지난 주 지출: %d원\n", data.getLastWeekExpense()));
		prompt.append(String.format("3. 이번 주 지출: %d원\n", data.getThisWeekExpense()));
		prompt.append(String.format("4. 이번 달 지출: %d원\n\n", data.getThisMonthExpense()));

		// 카테고리별 지출 데이터
		prompt.append("5. 카테고리별 지출:\n");
		CategoryExpenseDTO categoryExpense = data.getCategoryExpense();
		if (categoryExpense != null) {
			prompt.append(String.format("   * 식비: %d원\n", categoryExpense.getFoodExpense()));
			prompt.append(String.format("   * 생활비: %d원\n", categoryExpense.getLivingExpense()));
			prompt.append(String.format("   * 주거/통신비: %d원\n", categoryExpense.getHousingCommunicationExpense()));
			prompt.append(String.format("   * 금융비: %d원\n", categoryExpense.getFinanceExpense()));
			prompt.append(String.format("   * 교통비: %d원\n", categoryExpense.getTransportationExpense()));
			prompt.append(String.format("   * 육아비: %d원\n", categoryExpense.getChildcareExpense()));
			prompt.append(String.format("   * 여가/문화비: %d원\n", categoryExpense.getLeisureCultureExpense()));
			prompt.append(String.format("   * 반려동물비: %d원\n", categoryExpense.getPetExpense()));
			prompt.append(String.format("   * 행사/선물비: %d원\n", categoryExpense.getEventGiftExpense()));
		} else {
			prompt.append("   - 카테고리별 지출 데이터가 없습니다.\n");
		}

		// 요청 사항
		prompt.append("\n이 데이터를 기반으로 다음을 작성하세요:\n");
		prompt.append("1. 사용자의 지난 달 대비 이번 달 지출 변화율을 계산하여 간략히 요약.\n");
		prompt.append("2. 지난 주 대비 이번 주 지출 변화율을 계산하여 간략히 요약.\n");
		prompt.append("3. 가장 지출이 큰 카테고리를 언급하고, 절약 방법을 2~3개 제안.\n");
		prompt.append("4. 효율적인 예산 관리를 위한 행동 계획 1~2개를 추천.\n");
		prompt.append("5. 모든 내용을 간결하고 구체적으로 작성하세요.\n");

		return prompt.toString();
	}


	private String formatResponse(String content) {
	    // OpenAI 응답 메시지를 정형화된 형식으로 변환
	    StringBuilder formatted = new StringBuilder();
	    boolean isListStarted = false;

	    // 줄바꿈 제거 및 각 문장 정리
	    String[] lines = content.split("\\n");
	    for (String line : lines) {
	        line = line.trim(); // 앞뒤 공백 제거
	        if (!line.isEmpty()) {
	            if (line.startsWith("-") || line.startsWith("*")) {
	                // 리스트 항목 시작
	                if (!isListStarted) {
	                    formatted.append("<ul>\n"); // <ul> 시작
	                    isListStarted = true;
	                }
	                formatted.append("<li>").append(line.substring(1).trim()).append("</li>\n");
	            } else {
	                // 리스트 종료
	                if (isListStarted) {
	                    formatted.append("</ul>\n"); // <ul> 종료
	                    isListStarted = false;
	                }

	                if (line.matches("\\d+\\..*")) {
	                    // 번호로 시작하는 경우 (1. 항목 형태)
	                    formatted.append("<h3>").append(line).append("</h3>\n");
	                } else {
	                    // 일반 텍스트
	                    formatted.append("<p>").append(line).append("</p>\n");
	                }
	            }
	        }
	    }

	    // 남아있는 <ul> 태그 닫기
	    if (isListStarted) {
	        formatted.append("</ul>\n");
	    }

	    // HTML 스타일로 반환
	    return "<div class='ai-response'>\n" + formatted.toString() + "</div>";
	}

	public Map<String, Object> convertJsonToMap(String jsonData) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        // JSON 데이터를 Map으로 변환
	        return objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {});
	    } catch (Exception e) {
	        log.error("Failed to convert JSON to Map", e);
	        throw new CustomException(ErrorCode.JSON_PARSING_FAILED);
	    }
	}

}
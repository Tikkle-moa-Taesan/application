package com.ssafy.TmT.service;

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

	public AITextResponse getAdvice() {
		log.info("서비스 : 전체 조언 받기");
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		System.out.println("멤버아이디 : " + memberId);
//		MemberTotalDataDTO data = memberDao.getAllData(memberId);

		String jsonData = memberDao.getAllData(memberId);
		
		if (jsonData == null) {
			throw new CustomException(ErrorCode.MEMBER_DATA_FETCH_FAILED);
		}
//		
		Map<String, Object> dataMap = convertJsonToMap(jsonData);
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
//			log.info("Fetched MemberTotalDataDTO: {}", jsonData);
//		} catch (JsonProcessingException e) {
//			log.error("Failed to serialize MemberTotalDataDTO", e);
//		}
		
		String prompt = buildAdvicePrompt(dataMap);
		System.out.println("getAdvice 서비스 프롬프트 : " + prompt);
		return generateResponse(prompt);
	}

	// preview
	public AITextResponse showPreview() {
		ExpenseResponse data = budgetService.calculateExpenseAndBudget();

		if (data == null)
			throw new CustomException(ErrorCode.MEMBER_DATA_FETCH_FAILED);
		String prompt = buildPreviewPrompt(data);
		return generateResponse(prompt);
	}

//	// openAI 에 요청하고 결과를 처리하는 공통 메서드.. 만들어보자
//	private AITextResponse generateResponse(String prompt) {
//		OpenAIResponse response = openAIUtil.generateInsights(prompt);
//	    if (response == null)
//	        throw new CustomException(ErrorCode.OPENAI_NO_RESPONSE);
//	    if (response.getChoices() == null)
//	        throw new CustomException(ErrorCode.OPENAI_NO_CHOICES_FIELD);
//	    if (response.getChoices().isEmpty())
//	        throw new CustomException(ErrorCode.OPENAI_EMPTY_CHOICES);
//	    if (response.getChoices().get(0).getMessage() == null)
//	        throw new CustomException(ErrorCode.OPENAI_NO_MESSAGE_CONTENT);
//		return new AITextResponse(response.getChoices().get(0).getMessage().getContent());
//	}

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

//	private String buildAdvicePrompt(MemberTotalDataDTO data) {
//	    StringBuilder prompt = new StringBuilder();
//
//	    // 회원 정보
//	    prompt.append("사용자의 최근 6개월 데이터를 분석한 결과입니다.\n\n");
//
//	    // 1. 최근 6개월간 가장 지출이 큰 카테고리
//	    Map<String, Integer> categoryExpenses = new HashMap<>();
//	    if (data.getTransactions() != null) {
//	        for (BudgetTransactionDTO transaction : data.getTransactions()) {
//	            categoryExpenses.merge(
//	                transaction.getCategoryName(),
//	                transaction.getAmount().intValue(),
//	                Integer::sum
//	            );
//	        }
//	    }
//	    String mostSpentCategory = categoryExpenses.entrySet().stream()
//	        .max(Map.Entry.comparingByValue())
//	        .map(Map.Entry::getKey)
//	        .orElse("데이터 없음");
//	    prompt.append(String.format("1. 최근 6개월간 가장 지출이 큰 카테고리: %s\n", mostSpentCategory));
//
//	    // 2. 최근 6개월간 가장 지출이 큰 거래내역
//	    BudgetTransactionDTO highestTransaction = data.getTransactions().stream()
//	        .max(Comparator.comparing(BudgetTransactionDTO::getAmount))
//	        .orElse(null);
//	    if (highestTransaction != null) {
//	        prompt.append(String.format("2. 가장 지출이 큰 거래내역: %s - %d원 (%s)\n",
//	            highestTransaction.getMerchantName(),
//	            highestTransaction.getAmount(),
//	            highestTransaction.getTransactionDatetime()));
//	    } else {
//	        prompt.append("2. 가장 지출이 큰 거래내역: 데이터 없음\n");
//	    }
//
//	    // 3. 최근 6개월간 가장 돈을 많이 쓴 달
//	    Map<String, Long> monthlyExpenses = new HashMap<>();
//	    if (data.getTransactions() != null) {
//	        for (BudgetTransactionDTO transaction : data.getTransactions()) {
//	            String month = transaction.getTransactionDatetime().toLocalDateTime().toLocalDate().withDayOfMonth(1).toString();
//	            monthlyExpenses.merge(month, transaction.getAmount(), Long::sum);
//	        }
//	    }
//	    String mostSpentMonth = monthlyExpenses.entrySet().stream()
//	        .max(Map.Entry.comparingByValue())
//	        .map(Map.Entry::getKey)
//	        .orElse("데이터 없음");
//	    prompt.append(String.format("3. 가장 돈을 많이 쓴 달: %s\n", mostSpentMonth));
//
//	    // 4. 거래 내역 분석을 통한 조언
//	    prompt.append("4. 거래 내역 분석 및 조언:\n");
//	    prompt.append("   - 예산 설정 조정: 가장 지출이 큰 카테고리와 거래를 기반으로 월별 예산을 재조정하세요.\n");
//	    prompt.append("   - 지난달 대비 지출 변화율을 점검하고, 필요 시 지출 상한선을 설정하세요.\n");
//	    prompt.append("   - 가장 지출이 큰 달의 패턴을 확인하고, 불필요한 지출을 줄일 방법을 고민하세요.\n");
//
//	    return prompt.toString();
//	}

//	private String buildAdvicePrompt(MemberTotalDataDTO data) {
//	private String buildAdvicePrompt(String jsonData) {
	private String buildAdvicePrompt(Map<String, Object> dataMap) {
	    StringBuilder prompt = new StringBuilder();
	    
	    // 데이터를 추출
	    List<Map<String, Object>> transactions = (List<Map<String, Object>>) dataMap.get("transactions");

	    // 회원 정보
	    prompt.append("사용자의 최근 6개월 데이터를 분석한 결과입니다.\n\n");

	    // 1. 최근 6개월간 가장 지출이 큰 카테고리
	    Map<String, Integer> categoryExpenses = new HashMap<>();
	    if (transactions != null) {
	        for (Map<String, Object> transaction : transactions) {
	            String categoryName = (String) transaction.get("categoryName");
	            Integer amount = (Integer) transaction.get("amount");
	            if (categoryName != null && amount != null) {
	                categoryExpenses.merge(categoryName, amount, Integer::sum);
	            }
	        }
	    }
	    String mostSpentCategory = categoryExpenses.entrySet().stream()
	            .max(Map.Entry.comparingByValue())
	            .map(Map.Entry::getKey)
	            .orElse("데이터 없음");
	    prompt.append(String.format("1. 최근 6개월간 가장 지출이 큰 카테고리: %s\n", mostSpentCategory));

	    // 2. 최근 6개월간 가장 지출이 큰 거래내역
	    Map<String, Object> highestTransaction = transactions != null ? transactions.stream()
	            .max(Comparator.comparing(t -> (Integer) t.get("amount")))
	            .orElse(null) : null;
	    if (highestTransaction != null) {
	        prompt.append(String.format("2. 가장 지출이 큰 거래내역: %s - %d원 (%s)\n",
	                highestTransaction.get("merchantName"),
	                highestTransaction.get("amount"),
	                highestTransaction.get("transactionDateTime")));
	    } else {
	        prompt.append("2. 가장 지출이 큰 거래내역: 데이터 없음\n");
	    }

	    // 3. 최근 6개월간 가장 돈을 많이 쓴 달
	    Map<String, Long> monthlyExpenses = new HashMap<>();
	    if (transactions != null) {
	        for (Map<String, Object> transaction : transactions) {
	            String transactionDateTime = (String) transaction.get("transactionDateTime");
	            Integer amount = (Integer) transaction.get("amount");
	            if (transactionDateTime != null && amount != null) {
	                String month = transactionDateTime.substring(0, 7); // YYYY-MM 형태 추출
	                monthlyExpenses.merge(month, amount.longValue(), Long::sum);
	            }
	        }
	    }
	    String mostSpentMonth = monthlyExpenses.entrySet().stream()
	            .max(Map.Entry.comparingByValue())
	            .map(Map.Entry::getKey)
	            .orElse("데이터 없음");
	    prompt.append(String.format("3. 가장 돈을 많이 쓴 달: %s\n", mostSpentMonth));

	    // 4. 거래 내역 분석을 통한 조언
	    prompt.append("4. 거래 내역 분석 및 조언:\n");
	    prompt.append("   - 예산 설정 조정: 가장 지출이 큰 카테고리와 거래를 기반으로 월별 예산을 재조정하세요.\n");
	    prompt.append("   - 지난달 대비 지출 변화율을 점검하고, 필요 시 지출 상한선을 설정하세요.\n");
	    prompt.append("   - 가장 지출이 큰 달의 패턴을 확인하고, 불필요한 지출을 줄일 방법을 고민하세요.\n");

	    // 로그로 출력
	    log.info("Generated Prompt: {}", prompt.toString());
	    return prompt.toString();
	}


//	private String buildAdvicePrompt(MemberTotalDataDTO data) {
//	    StringBuilder prompt = new StringBuilder();
//
//	    // 회원 정보
//	    prompt.append("다음은 사용자의 종합 데이터입니다:\n");
//	    prompt.append("1. 사용자 정보:\n");
//	    prompt.append(String.format("   - 가입일: %s\n\n", data.getMemberCreatedAt()));
//
//	    // 계좌 정보
//	    prompt.append("2. 계좌 정보:\n");
//	    if (data.getAccounts() != null && !data.getAccounts().isEmpty()) {
//	        for (AccountDTO account : data.getAccounts()) {
//	            prompt.append(String.format("   - 계좌 이름: %s, 은행명: %s, 잔액: %d원, 계좌 타입: %s\n",
//	                account.getAccountName(),
//	                account.getBankName(),
//	                account.getBalance(),
//	                account.getAccountType()
//	            ));
//	            if (account.getAccountType().equals("savings") && account.getMaturityDate() != null) {
//	                prompt.append(String.format("     * 만기일: %s, 이자율: %.2f%%\n",
//	                    account.getMaturityDate(),
//	                    account.getInterestRate()
//	                ));
//	            }
//	        }
//	    } else {
//	        prompt.append("   - 계좌 정보가 없습니다.\n");
//	    }
//	    prompt.append("\n");
//
//	 // 예산 정보
//	    prompt.append("3. 예산 정보:\n");
//	    if (data.getBudget() != null) {
//	        BudgetDTO budget = data.getBudget();
//	        prompt.append(String.format("   - 월 전체 예산: %d원\n", budget.getMonthBudget()));
//	        prompt.append("   - 카테고리별 예산:\n");
//	        prompt.append(String.format("     * 식비: %d원\n", budget.getFoodBudget()));
//	        prompt.append(String.format("     * 생활비: %d원\n", budget.getLivingBudget()));
//	        prompt.append(String.format("     * 주거/통신비: %d원\n", budget.getHousingCommunicationBudget()));
//	        prompt.append(String.format("     * 금융비: %d원\n", budget.getFinanceBudget()));
//	        prompt.append(String.format("     * 교통비: %d원\n", budget.getTransportationBudget()));
//	        prompt.append(String.format("     * 육아비: %d원\n", budget.getChildcareBudget()));
//	        prompt.append(String.format("     * 여가/문화비: %d원\n", budget.getLeisureCultureBudget()));
//	        prompt.append(String.format("     * 반려동물비: %d원\n", budget.getPetBudget()));
//	        prompt.append(String.format("     * 행사/선물비: %d원\n", budget.getEventGiftBudget()));
//	    } else {
//	        prompt.append("   - 예산 정보가 없습니다.\n");
//	    }
//	    prompt.append("\n");
//
//
//	    // 거래 내역
//	    prompt.append("4. 최근 거래 내역:\n");
//	    if (data.getTransactions() != null && !data.getTransactions().isEmpty()) {
//	        for (BudgetTransactionDTO transaction : data.getTransactions()) {
//	            prompt.append(String.format("   - 날짜: %s, 금액: %d원, 잔액: (제공되지 않음), 거래명: %s\n",
//	                transaction.getTransactionDatetime(),
//	                transaction.getAmount(),
//	                transaction.getMerchantName()
//	            ));
//	            prompt.append(String.format("     * 카테고리: %s, 거래 타입: %s, 계좌 이름: %s\n",
//	                transaction.getCategoryName(),
//	                transaction.getTransactionType(),
//	                transaction.getAccountName()
//	            ));
//	            if (transaction.getTags() != null && !transaction.getTags().isEmpty()) {
//	                prompt.append("     * 태그: ");
//	                for (Tag tag : transaction.getTags()) {
//	                    prompt.append(String.format("#%s ", tag.getTagName()));
//	                }
//	                prompt.append("\n");
//	            }
//	        }
//	    } else {
//	        prompt.append("   - 거래 내역이 없습니다.\n");
//	    }
//	    prompt.append("\n");
//
//	    // 요청 사항
//	    prompt.append("이 데이터를 기반으로 다음 질문에 대한 답을 작성하세요:\n");
//	    prompt.append("1. 사용자의 예산, 거래 내역, 계좌 정보를 분석하여 주요 문제점 및 패턴을 제시하세요.\n");
//	    prompt.append("2. 문제점 개선을 위한 구체적인 행동 계획을 2~3가지 추천하세요.\n");
//	    prompt.append("3. 계좌, 예산, 거래 데이터에서 발견한 긍정적인 점을 간략히 요약하세요.\n");
//	    prompt.append("4. 모든 내용을 간결하고 구체적으로 작성하세요.\n");
//
//	    return prompt.toString();
//	}

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

	
	//	private String formatResponse(String content) {
//		// OpenAI 응답 메시지를 정형화된 형식으로 변환
//		StringBuilder formatted = new StringBuilder();
//
//		// 줄바꿈 제거 및 각 문장 정리
//		String[] lines = content.split("\\n");
//		for (String line : lines) {
//			line = line.trim(); // 앞뒤 공백 제거
//			if (!line.isEmpty()) {
//				if (line.startsWith("-") || line.startsWith("*")) {
//					// 항목 형태일 경우
//					formatted.append("<li>").append(line.substring(1).trim()).append("</li>\n");
//				} else if (line.matches("\\d+\\..*")) {
//					// 번호로 시작하는 경우 (1. 항목 형태)
//					formatted.append("<h3>").append(line).append("</h3>\n");
//				} else {
//					// 일반 텍스트
//					formatted.append("<p>").append(line).append("</p>\n");
//				}
//			}
//		}
//
//		// HTML 스타일로 반환
//		return "<div class='ai-response'>\n" + formatted.toString() + "</div>";
//	}
	
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
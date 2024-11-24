package com.ssafy.TmT.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dao.OpenAIDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.account.AccountDTO;
import com.ssafy.TmT.dto.budget.BudgetDTO;
import com.ssafy.TmT.dto.budget.CategoryExpenseDTO;
import com.ssafy.TmT.dto.budget.ExpenseResponse;
import com.ssafy.TmT.dto.budget.WeekExpenseDTO;
import com.ssafy.TmT.dto.member.MemberPreviewDataDTO;
import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
import com.ssafy.TmT.dto.openAI.AITextResponse;
import com.ssafy.TmT.dto.openAI.OpenAIResponse;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;
import com.ssafy.TmT.entity.Tag;
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
//	private final ObjectMapper objectMapper;

	public AITextResponse getAdvice() {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();
		MemberTotalDataDTO data = memberDao.getAllData(memberId);

		if (data == null) {
			throw new CustomException(ErrorCode.MEMBER_DATA_NOT_FOUND);
		}

//			String jsonData = objectMapper.writeValueAsString(data);
			String prompt = buildAdvicePrompt(data);
			return generateResponse(prompt);
//		} catch (JsonProcessingException e) {
//			throw new CustomException(ErrorCode.DATA_SERIALIZATION_FAILED);
	}

	// preview
	public AITextResponse showPreview() {
		ExpenseResponse data = budgetService.calculateExpenseAndBudget();

		if (data == null)
			throw new CustomException(ErrorCode.MEMBER_DATA_NOT_FOUND);
		String prompt = buildPreviewPrompt(data);
		return generateResponse(prompt);
	}


	// openAI 에 요청하고 결과를 처리하는 공통 메서드.. 만들어보자
	private AITextResponse generateResponse(String prompt) {
		OpenAIResponse response = openAIUtil.generateInsights(prompt);
		if (response == null)
			throw new CustomException(ErrorCode.OPENAI_RESPONSE_NULL);
		if (response.getChoices() == null)
			throw new CustomException(ErrorCode.OPENAI_CHOICES_NULL);
		if (response.getChoices().isEmpty())
			throw new CustomException(ErrorCode.OPENAI_NO_CHOICES);
		if (response.getChoices().get(0).getMessage() == null)
			throw new CustomException(ErrorCode.OPENAI_MESSAGE_NULL);

		return new AITextResponse(response.getChoices().get(0).getMessage().getContent());
	}
	
	private String buildAdvicePrompt(MemberTotalDataDTO data) {
	    StringBuilder prompt = new StringBuilder();

	    // 회원 정보
	    prompt.append("다음은 사용자의 종합 데이터입니다:\n");
	    prompt.append("1. 사용자 정보:\n");
	    prompt.append(String.format("   - 가입일: %s\n\n", data.getMemberCreatedAt()));

	    // 계좌 정보
	    prompt.append("2. 계좌 정보:\n");
	    if (data.getAccounts() != null && !data.getAccounts().isEmpty()) {
	        for (AccountDTO account : data.getAccounts()) {
	            prompt.append(String.format("   - 계좌 이름: %s, 은행명: %s, 잔액: %d원, 계좌 타입: %s\n",
	                account.getAccountName(),
	                account.getBankName(),
	                account.getBalance(),
	                account.getAccountType()
	            ));
	            if (account.getAccountType().equals("savings") && account.getMaturityDate() != null) {
	                prompt.append(String.format("     * 만기일: %s, 이자율: %.2f%%\n",
	                    account.getMaturityDate(),
	                    account.getInterestRate()
	                ));
	            }
	        }
	    } else {
	        prompt.append("   - 계좌 정보가 없습니다.\n");
	    }
	    prompt.append("\n");

	 // 예산 정보
	    prompt.append("3. 예산 정보:\n");
	    if (data.getBudget() != null) {
	        BudgetDTO budget = data.getBudget();
	        prompt.append(String.format("   - 월 전체 예산: %d원\n", budget.getMonthBudget()));
	        prompt.append("   - 카테고리별 예산:\n");
	        prompt.append(String.format("     * 식비: %d원\n", budget.getFoodBudget()));
	        prompt.append(String.format("     * 생활비: %d원\n", budget.getLivingBudget()));
	        prompt.append(String.format("     * 주거/통신비: %d원\n", budget.getHousingCommunicationBudget()));
	        prompt.append(String.format("     * 금융비: %d원\n", budget.getFinanceBudget()));
	        prompt.append(String.format("     * 교통비: %d원\n", budget.getTransportationBudget()));
	        prompt.append(String.format("     * 육아비: %d원\n", budget.getChildcareBudget()));
	        prompt.append(String.format("     * 여가/문화비: %d원\n", budget.getLeisureCultureBudget()));
	        prompt.append(String.format("     * 반려동물비: %d원\n", budget.getPetBudget()));
	        prompt.append(String.format("     * 행사/선물비: %d원\n", budget.getEventGiftBudget()));
	    } else {
	        prompt.append("   - 예산 정보가 없습니다.\n");
	    }
	    prompt.append("\n");


	    // 거래 내역
	    prompt.append("4. 최근 거래 내역:\n");
	    if (data.getTransactions() != null && !data.getTransactions().isEmpty()) {
	        for (BudgetTransactionDTO transaction : data.getTransactions()) {
	            prompt.append(String.format("   - 날짜: %s, 금액: %d원, 잔액: (제공되지 않음), 거래명: %s\n",
	                transaction.getTransactionDatetime(),
	                transaction.getAmount(),
	                transaction.getMerchantName()
	            ));
	            prompt.append(String.format("     * 카테고리: %s, 거래 타입: %s, 계좌 이름: %s\n",
	                transaction.getCategoryName(),
	                transaction.getTransactionType(),
	                transaction.getAccountName()
	            ));
	            if (transaction.getTags() != null && !transaction.getTags().isEmpty()) {
	                prompt.append("     * 태그: ");
	                for (Tag tag : transaction.getTags()) {
	                    prompt.append(String.format("#%s ", tag.getTagName()));
	                }
	                prompt.append("\n");
	            }
	        }
	    } else {
	        prompt.append("   - 거래 내역이 없습니다.\n");
	    }
	    prompt.append("\n");

	    // 요청 사항
	    prompt.append("이 데이터를 기반으로 다음 질문에 대한 답을 작성하세요:\n");
	    prompt.append("1. 사용자의 예산, 거래 내역, 계좌 정보를 분석하여 주요 문제점 및 패턴을 제시하세요.\n");
	    prompt.append("2. 문제점 개선을 위한 구체적인 행동 계획을 2~3가지 추천하세요.\n");
	    prompt.append("3. 계좌, 예산, 거래 데이터에서 발견한 긍정적인 점을 간략히 요약하세요.\n");
	    prompt.append("4. 모든 내용을 간결하고 구체적으로 작성하세요.\n");

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

}
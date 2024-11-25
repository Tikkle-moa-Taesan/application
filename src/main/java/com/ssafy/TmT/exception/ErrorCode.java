package com.ssafy.TmT.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    
    // 공통 에러
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),
    DATA_SERIALIZATION_FAILED(404, "C002", "데이터 직렬화 실패"),
    
    // transaction 관련 에러
    TRANSACTION_CREATE_FAIL(404, "T001", "거래내역 생성 실패"),

    // Budget 관련 에러
    BUDGET_NOT_FOUND(404, "B001", "해당 가계부가 없습니다."),
    BUDGET_TRANSACTION_UPDATE_FAILED(500, "B002", "가계부 거래 내역 업데이트 중 오류가 발생했습니다."),
    BUDGET_AMOUNT_NOT_FOUND(404, "B003", "가계부의 예산을 찾을 수 없습니다."),
    BUDGET_UPDATE_FAILURE(404,"B005","카테고리 예산 업데이트에 실패했습니다."),

    BUDGET_TRANSACTION_MODIFY_FAILED(500, "B006", "거래내역 수정에 실패했습니다"),
    BudgetTransaction_DELETE_FAILED(500, "B007", "거래내역 삭제에 실패했습니다."),
    
    
    // Account 관련 에러
    ACCOUNT_NOT_FOUND(404, "A001", "해당 계좌를 찾을 수 없습니다."),

    // 인증 및 토큰 관련 에러
    INVALID_TOKEN(401, "T001", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(401, "T002", "토큰이 만료되었습니다."),
    INVALID_AUTHENTICATION(401, "A003", "인증되지 않은 사용자입니다."),
    OAUTH_INVALID(401,"T004", "OAuth 인증에 실패했습니다"),
    
    MEMBER_DATA_NOT_FOUND(404, "M002","사용자 데이터를 불러오는 데 실패했습니다"),
    MEMBER_NOT_FOUND(404, "M001", "사용자를 확인할 수 없습니다."), 
    
    // openAI 에러
    OPENAI_RESPONSE_INVALID(404, "O001", "OpenAI 데이터 구조가 예상과 다릅니다."),
    OPENAI_API_CALL_FAILED(500, "O002", "OpenAI 통신이 실패했습니다."),
    OPENAI_RESPONSE_NULL(500, "O003", "OpenAI 응답이 null입니다."),
    OPENAI_CHOICES_NULL(500, "O004", "OpenAI 응답에서 choices가 null입니다."),
    OPENAI_NO_CHOICES(500, "O005", "OpenAI 응답에서 choices가 비어 있습니다."),
    OPENAI_MESSAGE_NULL(500, "O006", "OpenAI 응답에서 메시지가 null입니다."), 
    
    INVALID_ID_TOKEN_FORMAT(404,"T005", "id토큰 형식이 올바르지 않습니다."), 
    DECODING_FAILED(404,"T006", "디코딩에 실패했습니다"), 
    INVALID_STATE_TOKEN(404,"T007","state 값이 이상합니다"), 
    PARSING_FAILED(404,"T008","parsing 실패");
	
	
	
	private final int status;	// HTTP 상태 코드
	private final String code;	// 커스텀 상태 코드
	private final String message;	// 프론트에게 전하고싶은 메시지


}

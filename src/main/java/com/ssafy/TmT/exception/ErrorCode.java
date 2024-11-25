package com.ssafy.TmT.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    
    // 공통 에러
    METHOD_NOT_ALLOWED(405, "C001", "지원하지 않는 요청 방식입니다."),
    DATA_SERIALIZATION_FAILED(500, "C002", "데이터 직렬화에 실패했습니다."),
    INTERNAL_SERVER_ERROR(500, "C003", "서버 내부 오류가 발생했습니다."),
    INVALID_REQUEST(400, "C004", "요청이 올바르지 않습니다."),
    
    // Transaction 관련 에러
    TRANSACTION_CREATE_FAIL(500, "T001", "거래 내역 생성에 실패했습니다."),
    TRANSACTION_NOT_FOUND(404, "T002", "거래 내역을 찾을 수 없습니다."),
    TRANSACTION_UPDATE_FAILED(500, "T003", "거래 내역 업데이트 중 오류가 발생했습니다."),
    TRANSACTION_DELETE_FAILED(500, "T004", "거래 내역 삭제에 실패했습니다."),
    
    // Budget 관련 에러
    BUDGET_NOT_FOUND(404, "B001", "가계부를 찾을 수 없습니다."),
    BUDGET_TRANSACTION_UPDATE_FAILED(500, "B002", "가계부 거래 내역 업데이트 중 오류가 발생했습니다."),
    BUDGET_AMOUNT_NOT_FOUND(404, "B003", "가계부의 예산 정보를 찾을 수 없습니다."),
    BUDGET_UPDATE_FAILURE(500, "B004", "카테고리 예산 업데이트에 실패했습니다."),
    BUDGET_TRANSACTION_MODIFY_FAILED(500, "B005", "가계부 거래 내역 수정에 실패했습니다."),
    BUDGET_TRANSACTION_DELETE_FAILED(500, "B006", "가계부 거래 내역 삭제에 실패했습니다."),
    
    // Account 관련 에러
    ACCOUNT_NOT_FOUND(404, "A001", "계좌를 찾을 수 없습니다."),
    ACCOUNT_CREATION_FAILED(500, "A002", "계좌 생성에 실패했습니다."),
    ACCOUNT_UPDATE_FAILED(500, "A003", "계좌 정보 업데이트에 실패했습니다."),
    
    // 인증 및 토큰 관련 에러
    INVALID_TOKEN(401, "AUTH001", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(401, "AUTH002", "토큰이 만료되었습니다."),
    UNAUTHORIZED(401, "AUTH003", "인증되지 않은 사용자입니다."),
    OAUTH_AUTHENTICATION_FAILED(401, "AUTH004", "OAuth 인증에 실패했습니다."),
    INVALID_ID_TOKEN_FORMAT(400, "AUTH005", "ID 토큰 형식이 올바르지 않습니다."),
    STATE_TOKEN_INVALID(400, "AUTH006", "유효하지 않은 상태 토큰입니다."),
    TOKEN_PARSING_FAILED(400, "AUTH007", "토큰 파싱에 실패했습니다."),
    ID_TOKEN_FORMAT_INVALID(400, "AUTH005", "ID 토큰 형식이 올바르지 않습니다."),
    BASE64_DECODING_FAILED(400, "AUTH006", "ID 토큰 Base64 디코딩에 실패했습니다."),
    JSON_PARSING_FAILED(400, "AUTH007", "ID 토큰의 JSON 파싱에 실패했습니다."),
    AUTHENTICATION_NOT_FOUND(401, "A001", "인증 정보를 찾을 수 없습니다."),
    INVALID_AUTHENTICATION_DATA(401, "A002", "잘못된 인증 데이터입니다."),
    
    // 사용자 관련 에러
    MEMBER_NOT_FOUND(404, "M001", "사용자를 찾을 수 없습니다."),
    MEMBER_DATA_FETCH_FAILED(500, "M002", "사용자 데이터를 불러오는 데 실패했습니다."),
    
    // OpenAI 관련 에러
  
    OPENAI_NO_RESPONSE(500, "AI001", "OpenAI 응답이 없습니다."),
    OPENAI_NO_CHOICES_FIELD(500, "AI002", "OpenAI 응답에서 choices 필드가 없습니다."),
    OPENAI_EMPTY_CHOICES(500, "AI003", "OpenAI 응답에서 choices가 비어 있습니다."),
    OPENAI_NO_MESSAGE_CONTENT(500, "AI004", "OpenAI 응답에서 message 내용이 없습니다."), 
    BUDGET_CREATION_FAILED(500, "B007", "가계부 생성 실패");
	
	
	private final int status;	// HTTP 상태 코드
	private final String code;	// 커스텀 상태 코드
	private final String message;	// 프론트에게 전하고싶은 메시지


}

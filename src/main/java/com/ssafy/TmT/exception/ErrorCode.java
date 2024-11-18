package com.ssafy.TmT.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통 에러
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),

    // Budget 관련 에러
    BUDGET_NOT_FOUND(404, "B001", "해당 가계부가 없습니다."),
    BUDGET_TRANSACTION_UPDATE_FAILED(500, "B002", "가계부 거래 내역 업데이트 중 오류가 발생했습니다."),
    BUDGET_AMOUNT_NOT_FOUND(404, "B003", "가계부의 예산을 찾을 수 없습니다."),


    // Account 관련 에러
    ACCOUNT_NOT_FOUND(404, "A001", "해당 계좌를 찾을 수 없습니다."),

    // 인증 및 토큰 관련 에러
    INVALID_TOKEN(401, "T001", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(401, "T002", "토큰이 만료되었습니다.");
    
	
    private final int status;
    private final String code;
    private final String message;

}

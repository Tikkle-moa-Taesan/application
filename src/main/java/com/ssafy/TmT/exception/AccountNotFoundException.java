package com.ssafy.TmT.exception;

public class AccountNotFoundException extends RuntimeException {

    // 기본 생성자
    public AccountNotFoundException(String message) {
        super(message);
    }

    // 원본 예외를 포함하는 생성자
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

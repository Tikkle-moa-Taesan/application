package com.ssafy.TmT.exception;

public class InvalidTokenException extends RuntimeException {
    // 기본 생성자
    public InvalidTokenException(String message) {
        super(message);
    }

    // 원본 예외를 포함하는 생성자
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}

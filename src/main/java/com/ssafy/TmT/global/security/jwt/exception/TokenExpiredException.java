package com.ssafy.TmT.global.security.jwt.exception;


public class TokenExpiredException extends RuntimeException {

    // 기본 생성자
    public TokenExpiredException(String message) {
        super(message);
    }

    // 원본 예외를 포함하는 생성자
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ssafy.TmT.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        CustomExceptionResponse response = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now()) 		 // 현재 시간
                .status(ex.getErrorCode().getStatus())	 // HTTP 상태 코드
                .code(ex.getErrorCode().getCode())		 // 에러 코드
                .message(ex.getErrorCode().getMessage()) // 에러 메시지
                .path(request.getRequestURI())			 // 요청 경로
                .build();

        return ResponseEntity
                .status(ex.getErrorCode().getStatus()) // HTTP 상태 코드
                .body(response); // 응답 바디
    }

    // 그 외 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        CustomExceptionResponse response = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500) // Internal Server Error
                .code("E000") // 일반 오류 코드
                .message("서버 내부 오류가 발생했습니다.") // 기본 메시지
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(500)
                .body(response);
    }
}

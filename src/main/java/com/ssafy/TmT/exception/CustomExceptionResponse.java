package com.ssafy.TmT.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "예외 응답 형식")
public class CustomExceptionResponse {
	
    @Schema(description = "에러 발생 시간", example = "2024-12-31T23:59:59.999Z")
    private final LocalDateTime timestamp;

    @Schema(description = "HTTP 상태 코드", example = "000")
    private final int status;

    @Schema(description = "에러 코드", example = "A000")
    private final String code;

    @Schema(description = "에러 메시지", example = "어떤 예외가 발생했습니다.")
    private final String message;

    @Schema(description = "에러 발생 경로", example = "/api/error/example")
    private final String path;
}

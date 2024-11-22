package com.ssafy.TmT.dto.openAI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIResponse {

    private String id; // 응답 ID
    private String object; // 응답 객체 타입 (e.g., "chat.completion")
    private long created; // 응답 생성 시간 (Unix 타임스탬프)
    private String model; // 사용된 모델 (e.g., "gpt-4")
    private List<Choice> choices; // 주요 응답 데이터
    private Usage usage; // 토큰 사용량 정보

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index; // 응답 선택 인덱스
        private Message message; // 응답 메시지
        private String finishReason; // 응답 종료 이유 (e.g., "stop", "length")
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role; // 메시지 역할 (e.g., "system", "user", "assistant")
        private String content; // 메시지 내용
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private int promptTokens; // 프롬프트에 사용된 토큰 수
        private int completionTokens; // 응답에 사용된 토큰 수
        private int totalTokens; // 총 토큰 수 (prompt + completion)
    }
}
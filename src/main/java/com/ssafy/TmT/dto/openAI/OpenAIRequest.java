package com.ssafy.TmT.dto.openAI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIRequest {

    private String model; // 사용할 모델 이름 (e.g., "gpt-4")
    private List<Message> messages; // 대화 메시지 목록
    private int maxTokens; // 응답의 최대 토큰 수
    private double temperature; // 응답 생성 다양성 (0.0 ~ 1.0)
    private double topP; // nucleus sampling (0.0 ~ 1.0), 선택적으로 설정 가능
    private int n; // 반환할 응답 수, 기본값 1
    private boolean stream; // 스트리밍 여부, 기본값 false

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role; // 역할 ("system", "user", "assistant")
        private String content; // 메시지 내용
    }
}

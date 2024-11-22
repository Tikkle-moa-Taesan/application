package com.ssafy.TmT.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssafy.TmT.dto.openAI.OpenAIRequest;
import com.ssafy.TmT.dto.openAI.OpenAIResponse;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpenAIUtil {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final String apiEndpoint = "https://api.openai.com/v1/chat/completions";

    public OpenAIResponse generateInsights(String jsonData) {
        OpenAIRequest request = OpenAIRequest.builder()
                .model("gpt-3.5-turbo")  // 적절한 모델로 변경
                .messages(List.of(
                    OpenAIRequest.Message.builder()
                        .role("system")
                        .content("다음은 사용자의 예산 및 지출 데이터입니다. 이에 기반해 조언을 제공하세요.")
                        .build(),
                    OpenAIRequest.Message.builder()
                        .role("user")
                        .content(jsonData)
                        .build()
                ))
                .maxTokens(500)
                .temperature(0.7)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        int retryCount = 0;
        int maxRetries = 3;
        long waitTime = 1000; // 초기 대기 시간 (1초)

        while (retryCount < maxRetries) {
            try {
                ResponseEntity<OpenAIResponse> responseEntity =
                    restTemplate.postForEntity(apiEndpoint, entity, OpenAIResponse.class);

                OpenAIResponse response = responseEntity.getBody();

                if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                    throw new CustomException(ErrorCode.OPENAI_RESPONSE_INVALID);
                }

                return response; // 성공적으로 응답 받음
            } catch (HttpClientErrorException.TooManyRequests e) {
                retryCount++;
                System.out.println("429 Too Many Requests: Retrying... (" + retryCount + ")");
                if (retryCount >= maxRetries) {
                    throw new CustomException(ErrorCode.OPENAI_API_CALL_FAILED);
                }

                try {
                    Thread.sleep(waitTime); // 대기 시간 동안 잠시 중단
                    waitTime *= 2; // 대기 시간 두 배 증가
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 복구
                    throw new CustomException(ErrorCode.OPENAI_API_CALL_FAILED);
                }
            }
        }

        throw new CustomException(ErrorCode.OPENAI_API_CALL_FAILED);
    }

}

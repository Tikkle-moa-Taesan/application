package com.ssafy.TmT.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final ApiUtil apiUtil;
    

    public OpenAIResponse generateInsights(String jsonData) {
        String apiEndpoint = "https://api.openai.com/v1/chat/completions";

        // Body 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", "다음은 사용자의 예산 및 지출 데이터입니다. 이에 기반해 조언을 제공하세요."),
            Map.of("role", "user", "content", jsonData)
        ));
        requestBody.put("max_completion_tokens", 2000);
        requestBody.put("temperature", 0.7);
        requestBody.put("n", 1);

        // 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("Content-Type", "application/json");

        
        // 요청 전송
        ResponseEntity<OpenAIResponse> responseEntity = apiUtil.sendPostRequest(apiEndpoint, requestBody, headers, OpenAIResponse.class);

        // 응답 Body 반환
        return responseEntity.getBody();
    }
}

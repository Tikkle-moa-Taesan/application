//package com.ssafy.TmT.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import com.ssafy.TmT.dto.openAI.OpenAIRequest;
//import com.ssafy.TmT.dto.openAI.OpenAIResponse;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class OpenAIUtil {
//
//    @Value("${openai.api.key}")
//    private String apiKey;
//    
//    private final ApiUtil apiUtil;
//
//    private final RestTemplate restTemplate;
//    private final String apiEndpoint = "https://api.openai.com/v1/completions";
//
//    public String generateInsights(String jsonData) {
//        // 1. 자연어 지시사항 생성
//        String prompt = String.format(
//                "다음은 사용자의 예산 및 지출 데이터입니다. 이를 바탕으로 예산 관리 팁과 개선점을 작성해 주세요:\n%s",
//                jsonData
//        );
//
//        // 2. OpenAI 요청 생성
//        OpenAIRequest request = OpenAIRequest.builder()
//                .model("gpt-4") // OpenAI 모델
//                .prompt(prompt)
//                .maxTokens(500)
//                .temperature(0.7)
//                .build();
//
//        // 3. OpenAI API 호출
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);
//
//        try {
//            ResponseEntity<OpenAIResponse> responseEntity =
//                    restTemplate.postForEntity(apiEndpoint, entity, OpenAIResponse.class);
//
//            OpenAIResponse response = responseEntity.getBody();
//
//            // 4. OpenAI 응답 처리
//            return response != null && !response.getChoices().isEmpty()
//                    ? response.getChoices().get(0).getText()
//                    : "분석 결과를 생성할 수 없습니다.";
//        } catch (Exception e) {
//            // API 호출 실패 시 예외 처리
//            throw new RuntimeException("OpenAI API 호출 중 문제가 발생했습니다.", e);
//        }
//    }
//}
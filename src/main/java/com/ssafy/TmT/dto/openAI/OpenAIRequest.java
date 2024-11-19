package com.ssafy.TmT.dto.openAI;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAIRequest {
    private String model;
    private String prompt;
    private int maxTokens;
    private double temperature;
}

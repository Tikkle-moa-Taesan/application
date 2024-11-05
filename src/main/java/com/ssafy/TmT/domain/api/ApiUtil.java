package com.ssafy.TmT.domain.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ssafy.TmT.domain.finace.dto.FinanceResponse;

public class ApiUtil {

	// 계좌통합조회
	public FinanceResponse 계좌통합조회(String accessToken, String authorizationCode) {
		
		RestTemplate restTemplate = new RestTemplate();
        String url = "https://openapi.openbanking.or.kr/v2.0/accountinfo/list";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("acceessToken 값");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		
		Map<String, Object> body = new HashMap<>();
        body.put("auth_code", authorizationCode);       // 사용자 인증 코드
        body.put("inquiry_bank_type", "1");             // 금융기관 업권 구분
        body.put("org_ainfo_tran_id", "");              // 조회 원거래 전문관리번호 (선택)
        body.put("trace_no", "1");                      // 지정 번호
        body.put("inquiry_record_cnt", "30");           // 조회 건수
        
        
		// 요청 바디와 헤더를 HttpEntity에 담기
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
		FinanceResponse response = restTemplate.exchange(url, HttpMethod.POST, request, FinanceResponse.class).getBody();
	
		// 응답 출력
		System.out.println("Response body: " + response);
		return response;
	}
}

//https://openapi.openbanking.or.kr/v2.0/accountinfo/list
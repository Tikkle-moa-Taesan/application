package com.ssafy.TmT.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApiUtil {

    private final RestTemplate restTemplate;


    // GET 요청 메서드
    public <T> ResponseEntity<T> sendGetRequest(String url, HttpHeaders headers, Class<T> responseType, String... queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        // 쿼리 파라미터 가변 인자를 Map으로 변환하여 추가
        Map<String, String> params = Arrays.stream(queryParams)
            .collect(Collectors.groupingBy(
                param -> param.split("=")[0],
                Collectors.mapping(param -> param.split("=")[1], Collectors.joining())
            ));
        params.forEach(uriBuilder::queryParam);

        URI uri = uriBuilder.build().toUri();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        return restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
    }

    // POST 요청 메서드
    public <T> ResponseEntity<T> sendPostRequest(String url, Object body, HttpHeaders headers, Class<T> responseType) {
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }

    // PUT 요청 메서드
    public <T> ResponseEntity<T> sendPutRequest(String url, Object body, HttpHeaders headers, Class<T> responseType) {
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    }

    // DELETE 요청 메서드
    public <T> ResponseEntity<T> sendDeleteRequest(String url, HttpHeaders headers, Class<T> responseType) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    }
}
package com.ssafy.TmT.controller.interf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.TmT.dto.oauth.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/api/oauth")
public interface OAuthController {

	// 카카오 로그인
    @Operation(summary = "0. 카카오 로그인", description = "카카오 회원 정보를 가져옵니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "401", description = "인증 실패 또는 잘못된 카카오 코드"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
	@GetMapping("/kakao/login/{code}")
	public ResponseEntity<LoginResponse> kakaoLogin(@PathVariable String code) throws Exception;
}

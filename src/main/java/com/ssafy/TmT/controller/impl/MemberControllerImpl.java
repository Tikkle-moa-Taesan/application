package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.MemberController;
import com.ssafy.TmT.dto.oauth.Profile;
import com.ssafy.TmT.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController {

	private final MemberService memberService;
	
	public ResponseEntity<Profile> findFreeAccounts() {
		log.info("컨트롤러 : 자유 계좌 조회");
		Profile response = memberService.getProfile();	
		return ResponseEntity.ok(response);
	}
	
}

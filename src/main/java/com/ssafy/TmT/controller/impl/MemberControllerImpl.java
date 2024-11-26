package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.MemberController;
import com.ssafy.TmT.dto.member.ModifyBudgetTransactionRequest;
import com.ssafy.TmT.dto.oauth.Profile;
import com.ssafy.TmT.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

	@Override
	public ResponseEntity<String> modifyBudgetTransaction(@PathVariable Long budgetTransactionId, @RequestBody ModifyBudgetTransactionRequest request) {
		log.info("컨트롤러 : 가계부 내역 수정");
		System.out.println("컨트롤러 : " + budgetTransactionId);
		System.out.println("컨트롤러 : " + request);
		memberService.modifyBudgetTransaction(budgetTransactionId, request);
		return ResponseEntity.ok("가계부 내역이 성공적으로 수정되었습니다");
	}

	@Override
	public ResponseEntity<String> removeBudgetTransaction(Long budgetTransactionId) {
		log.info("컨트롤러 : 가계부 내역 삭제");
		memberService.removeBudgetTransaction(budgetTransactionId);
		return ResponseEntity.ok("가계부 내역이 성공적으로 삭제되었습니다.");
	}
	
}

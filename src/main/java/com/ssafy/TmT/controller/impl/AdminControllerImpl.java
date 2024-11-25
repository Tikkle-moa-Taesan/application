package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.AdminController;
import com.ssafy.TmT.dto.admin.InsertAccountRequest;
import com.ssafy.TmT.dto.admin.InsertTransactionRequest;
import com.ssafy.TmT.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

	private final AdminService adminService;
	
	@Override
	public ResponseEntity<String> insertTransaction(InsertTransactionRequest request) {
		log.info("컨트롤러 : 거래 내역 추가");
		adminService.insertTransaction(request);
		return ResponseEntity.ok("거래내역이 추가되었습니다.");
	}

	@Override
	public ResponseEntity<String> insertAccount(InsertAccountRequest request) {
		log.info("컨트롤러 : 계좌 추가");
		adminService.insertAccount(request);
		return ResponseEntity.ok("계좌가 생성되었습니다.");
	}

}

package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;

import com.ssafy.TmT.controller.interf.AdminController;
import com.ssafy.TmT.dto.admin.InsertTransactionRequest;
import com.ssafy.TmT.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

	private final AdminService adminService;
	
	@Override
	public ResponseEntity<String> insertTransaction(InsertTransactionRequest request) {
		adminService.insertTransaction(request);
		return ResponseEntity.ok("거래내역이 추가되었습니다.");
	}

}

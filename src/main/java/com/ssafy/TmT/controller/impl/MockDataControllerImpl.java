package com.ssafy.TmT.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.controller.interf.MockDataController;
import com.ssafy.TmT.service.MockDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MockDataControllerImpl implements MockDataController {
	
	private final MockDataService mockDataService;
	
	@Override
	public ResponseEntity<String> insertMockData() {
		mockDataService.insertMockData();
		return ResponseEntity.ok("목데이터 제작 성공");
	}

}

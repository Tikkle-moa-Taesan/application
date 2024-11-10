package com.ssafy.TmT.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.TmT.dto.AccountResponse;
import com.ssafy.TmT.dto.BudgetDTO;
import com.ssafy.TmT.service.AccountService;
import com.ssafy.TmT.service.BudgetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/budget")
public class BudgetController {
	
	private final BudgetService budgetService;
	
	// 5번 api. 예산 통계 : 이번달 예산 가져옴
	@GetMapping("")
	public ResponseEntity<BudgetDTO> totalBudget(HttpHeaders headers) {
		log.info("예산 통계");
		BudgetDTO response = budgetService.findBudget(headers);
		return ResponseEntity.ok(response);
	}
	

}

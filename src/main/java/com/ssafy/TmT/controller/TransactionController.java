//package com.ssafy.TmT.controller;
//
//import java.util.List;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.TmT.dto.budget.ExpenseResponse;
//import com.ssafy.TmT.dto.transaction.TransactionDTO;
//import com.ssafy.TmT.service.AccountService;
//import com.ssafy.TmT.service.TransactionService;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/transaction")
//public class TransactionController {
//
//	// searchTransactionsByCategory
//	private final TransactionService transactionService;
//	
////	@GetMapping("/{transactionId}")
////	@Operation(summary = "8. 상세 거래내역 조회", description = "JWT를 이용해 지출 통계를 조회합니다.")
////	@ApiResponse(responseCode = "200", description = "요청 성공")
////	@ApiResponse(responseCode = "400", description = "요청 실패")
////	public ResponseEntity<AccountResponse> getDetail(HttpHeaders headers) {
////		log.info("컨트롤러 : 상세 계좌 조회");
////		return ResponseEntity.ok(transactionService.findAccountById(headers));
////	}
//	
//}

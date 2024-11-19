//package com.ssafy.TmT.controller.impl;
//
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.TmT.service.AccountService;
//import com.ssafy.TmT.service.OpenAIService;
//import com.ssafy.TmT.controller.interf.OpenAIInterface;
//import com.ssafy.TmT.dto.budget.*;
//import com.ssafy.TmT.dto.openAI.OpenAIResponse;
//import com.ssafy.TmT.dto.openAI.TotalAdviceResponse;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/openAI")
//public class OpenAIControllerImpl implements OpenAIInterface{@Override
//	
//	private final OpenAIService openAIService;
//	
//public ResponseEntity<OpenAIResponse> getAdvice() {
//	OpenAIResponse response = openAIService.getAdvice();
//	return response;
//}
//
////    // 지출 분류 기능
////    @GetMapping("/categories")
////    public ResponseEntity<CategorySummaryResponse> getCategorySummary() {
////        // 서비스 호출
////        return ResponseEntity.ok(new CategorySummaryResponse());
////    }
////
////    // 월별 예산 대비 지출 비교
////    @GetMapping("/compare")
////    @Operation(summary = "예산 대비 지출 비교", description = "예산 대비 지출 데이터를 제공합니다.")
////    @ApiResponses({
////        @ApiResponse(responseCode = "200", description = "요청 성공"),
////        @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음")
////    })
////    public ResponseEntity<BudgetComparisonResponse> getBudgetComparison(@RequestParam Integer month) {
////        // 서비스 호출
////        return ResponseEntity.ok(new BudgetComparisonResponse());
////    }
////
////    // 지출 트렌드 분석
////    @GetMapping("/trend")
////    @Operation(summary = "지출 트렌드 분석", description = "주간 및 월간 지출 트렌드를 분석합니다.")
////    @ApiResponses({
////        @ApiResponse(responseCode = "200", description = "요청 성공"),
////        @ApiResponse(responseCode = "500", description = "서버 오류")
////    })
////    public ResponseEntity<SpendingTrendResponse> getSpendingTrend() {
////        // 서비스 호출
////        return ResponseEntity.ok(new SpendingTrendResponse());
////    }
////
////    // 저축 목표 추적
////    @GetMapping("/savings/goal")
////    @Operation(summary = "저축 목표 추적", description = "저축 목표 대비 현재 상태를 보여줍니다.")
////    @ApiResponses({
////        @ApiResponse(responseCode = "200", description = "요청 성공"),
////        @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음")
////    })
////    public ResponseEntity<SavingsGoalResponse> getSavingsGoalStatus() {
////        // 서비스 호출
////        return ResponseEntity.ok(new SavingsGoalResponse());
////    }
////
////    // 예산 추천 기능
////    @GetMapping("/recommend")
////    @Operation(summary = "예산 추천", description = "다음 달 예산을 추천합니다.")
////    @ApiResponses({
////        @ApiResponse(responseCode = "200", description = "요청 성공"),
////        @ApiResponse(responseCode = "500", description = "서버 오류")
////    })
////    public ResponseEntity<BudgetRecommendationResponse> recommendBudget() {
////        // 서비스 호출
////        return ResponseEntity.ok(new BudgetRecommendationResponse());
////    }
////
////    // 커스터마이징 태그 추가
////    @PostMapping("/tags")
////    @Operation(summary = "커스텀 태그 추가", description = "지출 항목에 커스텀 태그를 추가합니다.")
////    @ApiResponses({
////        @ApiResponse(responseCode = "200", description = "태그 추가 성공"),
////        @ApiResponse(responseCode = "400", description = "잘못된 요청")
////    })
////    public ResponseEntity<Void> addCustomTag(@RequestBody TagRequest tagRequest) {
////        // 서비스 호출
////        return ResponseEntity.ok().build();
////    }
//}

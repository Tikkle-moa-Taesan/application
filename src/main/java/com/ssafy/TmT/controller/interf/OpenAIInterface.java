package com.ssafy.TmT.controller.interf;

import com.ssafy.TmT.dto.openAI.AITextResponse;
import com.ssafy.TmT.dto.openAI.OpenAIResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/openAI")
public interface OpenAIInterface {

	
	// 1. 내 전체 데이터를 기반으로, 훈수 듣기
	@GetMapping("/data")
	@Operation(summary = "모든 정보 다 넘기고 조언받기", description = "내가 가진 모든 데이터를 통해서, ai 조언을 받습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
        @ApiResponse(responseCode = "404", description = "openAI 오류"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")    })
	ResponseEntity<AITextResponse> getAdvice();
	
	// 1. 내 전체 데이터를 기반으로, 훈수 듣기
	@GetMapping("/preview")
	@Operation(summary = "간략한 정보 넘기고 조언받기", description = "최근 지출통계를 통해, ai 조언을 받습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
        @ApiResponse(responseCode = "404", description = "openAI 오류"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
	ResponseEntity<AITextResponse> preview();
	
//	
//    // 월별 예산 대비 지출 비교
//    @GetMapping("/compare")
//    @Operation(summary = "예산 대비 지출 비교", description = "예산 대비 지출 데이터를 제공합니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "요청 성공"),
//        @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음")
//    })
//    ResponseEntity<BudgetComparisonResponse> getBudgetComparison(@RequestParam Integer month);
//
//    
//    
//    // 지출 분류 기능
//    @GetMapping("/categories")
//    @Operation(summary = "지출 카테고리별 요약", description = "지출 내역을 카테고리별로 분류하여 요약 데이터를 제공합니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "요청 성공"),
//        @ApiResponse(responseCode = "500", description = "서버 오류")
//    })
//    ResponseEntity<CategorySummaryResponse> getCategorySummary();
//
//
//    // 지출 트렌드 분석
//    @GetMapping("/trend")
//    @Operation(summary = "지출 트렌드 분석", description = "주간 및 월간 지출 트렌드를 분석합니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "요청 성공"),
//        @ApiResponse(responseCode = "500", description = "서버 오류")
//    })
//    public ResponseEntity<SpendingTrendResponse> getSpendingTrend();
//    // 저축 목표 추적
//    @GetMapping("/savings/goal")
//    @Operation(summary = "저축 목표 추적", description = "저축 목표 대비 현재 상태를 보여줍니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "요청 성공"),
//        @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음")
//    })
//    ResponseEntity<SavingsGoalResponse> getSavingsGoalStatus();
//
//    // 예산 추천 기능
//    @GetMapping("/recommend")
//    @Operation(summary = "예산 추천", description = "다음 달 예산을 추천합니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "요청 성공"),
//        @ApiResponse(responseCode = "500", description = "서버 오류")
//    })
//    ResponseEntity<BudgetRecommendationResponse> recommendBudget();
//
//    // 커스터마이징 태그 추가
//    @PostMapping("/tags")
//    @Operation(summary = "커스텀 태그 추가", description = "지출 항목에 커스텀 태그를 추가합니다.")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "태그 추가 성공"),
//        @ApiResponse(responseCode = "400", description = "잘못된 요청")
//    })
//    ResponseEntity<Void> addCustomTag(@RequestBody TagRequest tagRequest);
//    
}

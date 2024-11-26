package com.ssafy.TmT.controller.interf;

import com.ssafy.TmT.dto.budget.*;
import com.ssafy.TmT.exception.CustomExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/budget")
public interface BudgetController {

    // 가계부 만드는 API
    @PostMapping("/create")
    @Operation(summary = "8. 가계부 생성하기", description = "입력받은 예산을 통해 가계부 생성")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "가계부 생성 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateBudgetResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "가계부를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<CreateBudgetResponse> createBudget(@RequestBody CreateBudgetRequest request);

    // 최신 거래 내역 다운로드 API
    @PostMapping("/download")
    @Operation(summary = "9. 최신 거래 내역 다운로드", description = "현재 예산의 최신 거래 내역을 업데이트합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "거래 내역 다운로드 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "예산을 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "거래 내역 업데이트 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<String> downloadTransactions();

    // 지출 통계 조회 API
    @GetMapping("/expense")
    @Operation(summary = "4. 지출 통계 조회", description = "JWT를 이용해 지출 통계를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "지출 통계 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "예산 데이터를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "지출 통계 조회 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<ExpenseResponse> calculateExpenseSummary();

    // 예산 통계 조회 API
    @GetMapping("/rate")
    @Operation(summary = "5. 예산 통계 조회", description = "예산과 예산 대비 사용 비율을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "예산 통계 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetRateResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "예산 데이터를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "예산 통계 조회 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetRateResponse> totalBudget();

    // 특정 가계부 조회 API
    @GetMapping("/{budgetId}")
    @Operation(summary = "가계부 조회", description = "budgetId를 이용해 해당 가계부의 거래 내역을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "가계부 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetDetailResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "가계부를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "가계부 조회 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetDetailResponse> findBudgetTransactions(@PathVariable Long budgetId,
                                                                @RequestParam(defaultValue = "0") int page);

    // 특정 날짜의 가계부 조회 API
    @GetMapping("/date/{date}")
    @Operation(summary = "해당 달 가계부 조회", description = "입력된 날짜의 가계부 중요 내용을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "가계부 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetProfileResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 날짜에 해당하는 가계부를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "가계부 조회 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetProfileResponse> getBudgetProfile(@PathVariable Integer date);
    
    
    @PutMapping("/category")
    @Operation(summary = "카테고리 예산 수정", description = "입력된 요청으로 카테고리 예산을 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카테고리 예산 수정 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetCategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetCategoryResponse> modifyCategoryBudget(@RequestBody BudgetCategoryRequest request);
    
    
    // 전체 거래 내역 받아오기
    // 특정 가계부 조회 API
    @GetMapping("/{budgetId}/all")
    @Operation(summary = "가계부 조회", description = "budgetId를 이용해 해당 가계부의 거래 내역을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "가계부 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetDetailResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "가계부를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "가계부 조회 중 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetDetailResponse> findAllBudgetTransactions(@PathVariable Long budgetId);
 
    
    
    @GetMapping("/category")
    @Operation(summary = "카테고리 예산 수정", description = "이번달 가계부의 카테고리별 예산을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카테고리별 예산 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetCategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "예산을 찾을 수 없음.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BudgetCategoryResponse> findCategoryBudget();
    
    @GetMapping("/graph")
    @Operation(summary = "최근 6개월 지출/예산 조회하기", description = "최근 6개월의 지출과 예산을 조회합니다. 그래프를 그리기 위한 정보 제공.")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "카테고리별 예산 조회 성공",
    			content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetCategoryResponse.class))),
    	@ApiResponse(responseCode = "400", description = "잘못된 요청",
    	content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
    	@ApiResponse(responseCode = "404", description = "예산을 찾을 수 없음.",
    	content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
    	@ApiResponse(responseCode = "500", description = "서버 오류",
    	content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<GraphResponse> findGraphExpense();
    
    @PutMapping("")
    @Operation(summary = "총 예산 수정", description = "입력된 요청으로 총 예산을 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "총 예산 수정 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BudgetCategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "가계부를 찾을 수 없음",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<String> modifyBudget(@RequestBody UpdateBudgetRequest request);
    
    
    
}

package com.ssafy.TmT.controller.interf;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.TmT.dto.account.BalanceResponse;
import com.ssafy.TmT.dto.account.FreeAccountDetailResponse;
import com.ssafy.TmT.dto.account.FreeAccountResponse;
import com.ssafy.TmT.dto.account.SavingsAccountDetailResponse;
import com.ssafy.TmT.dto.account.SavingsAccountResponse;
import com.ssafy.TmT.exception.CustomExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface AccountController {

    @GetMapping("/free")
    @Operation(summary = "1. 자유 계좌 전체 조회", description = "JWT를 이용해 자유 입출금 계좌를 전부 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "자유 계좌 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FreeAccountResponse.class))),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 계좌를 찾을 수 없습니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<List<FreeAccountResponse>> findFreeAccounts();

    @GetMapping("/savings")
    @Operation(summary = "2. 적금 계좌 전체 조회", description = "JWT를 이용해 적금 계좌를 전부 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "적금 계좌 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SavingsAccountResponse.class))),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 계좌를 찾을 수 없습니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<List<SavingsAccountResponse>> findSavingsAccounts();

    @GetMapping("/balance")
    @Operation(summary = "3. 총 자산 조회", description = "JWT를 이용해 총 자산을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "총 자산 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<BalanceResponse> getTotalBalance();

    @GetMapping("/free/{accountId}")
    @Operation(summary = "6. 자유 계좌 단일 조회", description = "accountId를 이용해 자유 입출금 계좌를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "자유 계좌 단일 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FreeAccountDetailResponse.class))),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 계좌를 찾을 수 없습니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<FreeAccountDetailResponse> getFreeAccountDetail(@PathVariable Long accountId, @RequestParam(defaultValue = "0") int page);

    @GetMapping("/savings/{accountId}")
    @Operation(summary = "7. 적금 계좌 단일 조회", description = "accountId를 이용해 적금 계좌를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "적금 계좌 단일 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SavingsAccountDetailResponse.class))),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 계좌를 찾을 수 없습니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionResponse.class)))
    })
    ResponseEntity<SavingsAccountDetailResponse> getSavingAccountDetail(@PathVariable Long accountId, @RequestParam(defaultValue = "0") int page);
}

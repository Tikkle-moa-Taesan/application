//package com.ssafy.TmT.trashbin;
//
//import java.net.URI;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.ssafy.TmT.domain.api.OAuth.controller.OAuthController;
//import com.ssafy.TmT.domain.api.OAuth.dto.OAuthResponse;
//import com.ssafy.TmT.domain.api.OAuth.service.OAuthService;
//import com.ssafy.TmT.domain.api.controller.FinanceController;
//import com.ssafy.TmT.domain.api.dto.AccountInfoRequest;
//import com.ssafy.TmT.domain.api.dto.AccountInfoResponse;
//import com.ssafy.TmT.domain.api.dto.AccountInfoResponse.AccountInfo;
//import com.ssafy.TmT.domain.api.dto.AccountInfoResponse.ExcludeBank;
//import com.ssafy.TmT.domain.api.service.AccountService;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
////public class trash {
////
////}
///*
//
//
//
//@Component
//public class ApiUtil {
//
//	// 계좌통합조회
//	public AccountInfoResponse 계좌통합조회(AccountInfoRequest request, String accessToken) {
//		
//		RestTemplate restTemplate = new RestTemplate();
//        String url = "https://openapi.openbanking.or.kr/v2.0/accountinfo/list";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setBearerAuth("acceessToken 값");
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//		
//		
//		Map<String, Object> body = new HashMap<>();
//        body.put("auth_code", authorizationCode);       // 사용자 인증 코드
//        body.put("inquiry_bank_type", "1");             // 금융기관 업권 구분
//        body.put("org_ainfo_tran_id", "");              // 조회 원거래 전문관리번호 (선택)
//        body.put("trace_no", "1");                      // 지정 번호
//        body.put("inquiry_record_cnt", "30");           // 조회 건수
//        
//        
//		// 요청 바디와 헤더를 HttpEntity에 담기
//		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//		AccountInfoResponse response = restTemplate.exchange(url, HttpMethod.POST, request, AccountInfoResponse.class).getBody();
//	
//		// 응답 출력
//		System.out.println("Response body: " + response);
//		return response;
//	}
//}
//
//@Getter
//@Setter
//public class AccountInfoRequest {
//    
//    @JsonProperty("auth_code")
//    private String authCode; // 사용자정보확인으로 수신한 키
//
//    @JsonProperty("inquiry_bank_type")
//    private String inquiryBankType; // 금융기관 업권 구분 (1:은행)
//
//    @JsonProperty("org_ainfo_tran_id")
//    private String orgAinfoTranId; // 조회 원거래 전문관리번호
//
//    @JsonProperty("trace_no")
//    private int traceNo; // 지정 번호
//
//    @JsonProperty("inquiry_record_cnt")
//    private int inquiryRecordCnt; // 조회 건수
//
//}
//@Getter
//@Setter
//public class AccountInfoResponse {
//    
//    @JsonProperty("api_tran_id")
//    private String apiTranId; // 거래고유번호(API)
//
//    @JsonProperty("api_tran_dtm")
//    private String apiTranDtm; // 거래일시(밀리세컨드)
//
//    @JsonProperty("rsp_code")
//    private String rspCode; // 응답코드(API)
//
//    @JsonProperty("rsp_message")
//    private String rspMessage; // 응답메시지(API)
//
//    @JsonProperty("ainfo_tran_id")
//    private String ainfoTranId; // 전문관리번호
//
//    @JsonProperty("ainfo_tran_date")
//    private String ainfoTranDate; // 거래일자(계좌통합)
//
//    @JsonProperty("rsp_type")
//    private String rspType; // 응답코드 부여 기관
//
//    @JsonProperty("ainfo_rsp_code")
//    private String ainfoRspCode; // 응답코드(계좌통합)
//
//    @JsonProperty("ainfo_rsp_message")
//    private String ainfoRspMessage; // 응답메시지(계좌통합)
//
//    @JsonProperty("inquiry_bank_type")
//    private String inquiryBankType; // 금융기관 구분
//
//    @JsonProperty("exclude_cnt")
//    private int excludeCnt; // 조회 제외기관 수
//
//    @JsonProperty("exclude_list")
//    private List<ExcludeBank> excludeList; // 조회 제외기관 목록
//
//    @JsonProperty("org_ainfo_tran_id")
//    private String orgAinfoTranId; // 조회 원거래 전문관리번호
//
//    @JsonProperty("trace_no")
//    private int traceNo; // 지정 번호
//
//    @JsonProperty("total_record_cnt")
//    private int totalRecordCnt; // 총 조회 건수
//
//    @JsonProperty("page_record_cnt")
//    private int pageRecordCnt; // 현재 페이지 조회 건수
//
//    @JsonProperty("res_list")
//    private List<AccountInfo> resList; // 조회 계좌 목록
//
//    // ExcludeBank Class
//    @Getter
//    @Setter
//    public static class ExcludeBank {
//        @JsonProperty("exclude_bank_code")
//        private String excludeBankCode; // 조회 제외기관 코드
//    }
//
//    // AccountInfo Class
//    @Getter
//    @Setter
//    public static class AccountInfo {
//        @JsonProperty("bank_code_std")
//        private String bankCodeStd; // 개설기관 코드
//
//        @JsonProperty("activity_type")
//        private String activityType; // 유형구분 (A:활동성계좌, I:비활동성계좌)
//
//        @JsonProperty("account_type")
//        private String accountType; // 계좌종류
//
//        @JsonProperty("account_num")
//        private String accountNum; // 계좌번호
//
//        @JsonProperty("account_seq")
//        private String accountSeq; // 회차번호
//
//        @JsonProperty("account_local_code")
//        private String accountLocalCode; // 계좌 관리점 코드
//
//        @JsonProperty("account_issue_date")
//        private String accountIssueDate; // 계좌개설일
//
//        @JsonProperty("maturity_date")
//        private String maturityDate; // 만기일
//
//        @JsonProperty("last_tran_date")
//        private String lastTranDate; // 최종거래일
//
//        @JsonProperty("product_name")
//        private String productName; // 상품명(계좌명)
//
//        @JsonProperty("product_sub_name")
//        private String productSubName; // 부기명
//
//        @JsonProperty("dormancy_yn")
//        private String dormancyYn; // 휴면계좌 여부 (Y/N)
//
//        @JsonProperty("balance_amt")
//        private String balanceAmt; // 계좌잔액 (-금액가능)
//    }
//
//}
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/finance")
//public class FinanceController {
//	
//	private final AccountInfoService accountInfoService;
//	
//	@PostMapping("/계좌통합조회")
//	public ResponseEntity<AccountInfoResponse> 계좌통합조회(@RequestBody AccountInfoRequest request, HttpHeaders headers) {
//		System.out.println("계좌통합조회 실행");
//		AccountInfoResponse response = accountInfoService.계좌통합조회(request, headers);	
//		return ResponseEntity.ok(response);
//	}
//	
//}
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/oauth")
//public class OAuthController {
//	
//	private final OAuthService oAuthService;
//	
//    // 사용자에게 인증 페이지로 리다이렉트
//    @GetMapping("/authorize")
//    public ResponseEntity<String> authorize() {
//        String redirectUrl = oAuthService.buildAuthorizeUrl();
//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
//    }
//	
//    // 리다이렉트된 후 code 값을 받아오는 엔드포인트
//    @GetMapping("/callback")
//    public ResponseEntity<OAuthResponse> callback(@RequestParam("code") String code) {
//    	OAuthResponse response = oAuthService.exchangeCodeForAccessToken(code);
//        return ResponseEntity.ok(response);
//    }
//}
//@Getter
//@Setter
//public class OAuthResponse {
//
//	private String accessToken;
//	private String tokenType;
//	private String refresh_token;
//	private int expires_in;
//	private String scope;
//	private int user_seq_no;
//	
//}
//
////    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDU5Njk5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE3Mzg1NzM4MDAsImp0aSI6ImRiMjAxN2UyLWYzYzEtNGRmNS1iMGFiLTc1MWIxOTQ0ZDYwNSJ9.VpnC4HzPCtXCoHycch8Je28T2S_DrI5DMnhy4P2AFjA",
////    "token_type": "Bearer",
////    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDU5Njk5Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE3Mzk0Mzc4MDAsImp0aSI6ImM0Y2I4ODY5LTZkZDgtNDI2OS1iMjJiLWM5OTY3MjI2Nzc4YSJ9._yNJDrzAItZuTgCE3KlB8-7FzRfSRbxYhqSdQ6VnLoM",
////    "expires_in": 7775999,
////    "scope": "inquiry login transfer",
////    "user_seq_no": "1101059699"
// * 
// * @Service
//public class OAuthServiceImpl implements OAuthService {
//
//	// 데이터 일단은 보관하고 있자 나중에 옮겨
//	private static final String AUTHORIZATION_URL = "https://testapi.openbanking.or.kr/oauth/2.0/authorize";
//	private static final String CLIENT_ID = "53798d71-78d0-401f-adf7-a388f979dea6";
//	private static final String REDIRECT_URI = "http://localhost:3000/callbackURL";
//	private static final String SCOPE = "login inquiry transfer";
//	private static final String STATE = "12345678901234567890123456789012";
//	private static final String CLIENT_SECRET = "84f09c3c-d3f1-4b3e-9655-55e4b9f5183f";
//
//	// 인증 페이지 URL 생성
//	public String buildAuthorizeUrl() {
//		return AUTHORIZATION_URL + "?response_type=code" + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
//				+ "&scope=" + SCOPE + "&state=" + STATE + "&auth_type=0" + "&client_secret=" + CLIENT_SECRET;
//	}
//
//	// code로 Access Token 교환
//	public OAuthResponse exchangeCodeForAccessToken(String code) {
//		 RestTemplate restTemplate = new RestTemplate();
//		 String accessTokenUrl = "https://testapi.openbanking.or.kr/oauth/2.0/token";
//		 HttpHeaders headers = new HttpHeaders();
//		 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		 Map<String, Object> params = new HashMap<>();
//		 params.put("grant_type", "authorization_code");
//		 params.put("code", code);
//		 params.put("client_id", CLIENT_ID);
//		 params.put("client_secret", CLIENT_SECRET);
//		 params.put("redirect_uri", REDIRECT_URI);
//
//		 HttpEntity<Map<String,Object>> request = new HttpEntity<>(params, headers);
//		 
//		OAuthResponse response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request, OAuthResponse.class).getBody();
//
//		return response;
//	}
//
//	// 실제로는 JSON 응답에서 access token을 추출해야 합니다.
//	private String extractAccessToken(String responseBody) {
//		return "parsed_access_token";
//	}
//}
//
//*/

// 다 삭제해버려야할듯
//

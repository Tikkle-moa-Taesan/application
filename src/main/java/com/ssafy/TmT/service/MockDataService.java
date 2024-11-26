package com.ssafy.TmT.service;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.controller.impl.OAuthControllerImpl;
import com.ssafy.TmT.dao.AccountDao;
import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.TransactionDao;
import com.ssafy.TmT.dto.account.AccountDTO;
import com.ssafy.TmT.dto.account.AccountType;
import com.ssafy.TmT.dto.transaction.TransactionType;
import com.ssafy.TmT.entity.Account;
import com.ssafy.TmT.entity.Transaction;
import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;
import com.ssafy.TmT.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MockDataService {

	private final AccountDao accountDao;
	private final TransactionDao transactionDao;

	public void insertMockData() {
		Long memberId = SecurityUtil.getAuthenticatedMemberId();

		// 1. 계좌 생성
		List<Account> accounts = createAccounts(memberId);

		createTransactions(accounts);

		log.info("Mock data inserted for member_id={}", memberId);
	}

	private List<Account> createAccounts(Long memberId) {
		List<Account> accounts = new ArrayList<>();
		String[] bankNames = { "신한은행", "국민은행", "하나은행", "우리은행", "농협은행" };
		Random random = new Random();
		for (String bankName : bankNames) {
			accounts.add(createAccount(memberId, AccountType.free, bankName + " 자유 입출금 계좌", bankName));
			accounts.add(createAccount(memberId, AccountType.savings, bankName + " 적금 계좌", bankName,
					generateRandomMaturityDate(random), generateRandomInterestRate(random)));
		}

		int result = accountDao.insertAccounts(accounts); // Mapper 호출

		if (result == 0)
			throw new CustomException(ErrorCode.ACCOUNT_CREATION_FAILED);
		return accounts;
	}

	private Account createAccount(Long memberId, AccountType type, String name, String bankName) {
		Account account = new Account();
		account.setMemberId(memberId);
		account.setAccountType(type);
		account.setAccountName(name);
		account.setAccountNumber(generateAccountNumber());
		account.setBalance(1_000_000L);
		account.setBankName(bankName);
		return account;
	}

	private Account createAccount(Long memberId, AccountType type, String name, String bankName, LocalDate maturityDate,
			Double interestRate) {
		Account account = new Account();
		account.setMemberId(memberId);
		account.setAccountType(type);
		account.setAccountName(name);
		account.setAccountNumber(generateAccountNumber());
		account.setBalance(1_000_000L);
		account.setBankName(bankName);
		account.setMaturityDate(maturityDate);
		account.setInterestRate(interestRate);
		return account;
	}

//	private void createTransactions(List<Account> accounts) {
//		List<Transaction> transactions = new ArrayList<>();
//		Random random = new Random();
//	    for (Account account : accounts) {
//            long currentBalance = account.getBalance(); // 계좌별 초기 잔액
//            List<Transaction> accountTransactions = new ArrayList<>();
//            
//            // 거래 데이터를 시간 순서대로 생성
//            for (int i = 0; i < 30; i++) {
//                LocalDateTime transactionDate = generateSequentialDate(i,6); // i번째 거래의 시간 생성
//                Transaction transaction = createTransaction(account, transactionDate, currentBalance, random);
//
//                // 거래 내역의 balanceAfter 업데이트
//                currentBalance = transaction.getBalanceAfter();
//                accountTransactions.add(transaction);
//            }
//            // 계좌의 최종 거래 내역을 추가
//            transactions.addAll(accountTransactions);
//
//            // 최종 계좌 잔액 업데이트
//            account.setBalance(currentBalance);
//            accountDao.updateBalance(account);
//        }
//
//        // 모든 거래 데이터를 DB에 삽입
//        int result = transactionDao.insertTransactions(transactions);
//        if (result == 0)
//            throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);
//    }
	
//	private void createTransactions(List<Account> accounts) {
//	    List<Transaction> transactions = new ArrayList<>();
//	    Random random = new Random();
//
//	    for (Account account : accounts) {
//	        long currentBalance = account.getBalance(); // 계좌별 초기 잔액
//	        List<Transaction> accountTransactions = new ArrayList<>();
//
//	        // 최근 6개월 동안 계좌당 100개의 거래 생성
//	        for (int i = 0; i < 50; i++) {
//	            LocalDateTime transactionDate = generateSequentialDate(i, 6); // i번째 거래의 시간 생성
//	            Transaction transaction = createTransaction(account, transactionDate, currentBalance, random);
//
//	            // 거래 내역의 balanceAfter 업데이트
//	            currentBalance = transaction.getBalanceAfter();
//	            accountTransactions.add(transaction);
//	        }
//	        // 계좌별 생성된 거래를 전체 목록에 추가
//	        transactions.addAll(accountTransactions);
//
//	        // 최종 계좌 잔액 업데이트
//	        account.setBalance(currentBalance);
//	        accountDao.updateBalance(account);
//	    }
//
//	    // 모든 거래 데이터를 DB에 삽입
//	    int result = transactionDao.insertTransactions(transactions);
//	    if (result == 0) throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);
//	}
	
	private void createTransactions(List<Account> accounts) {
	    List<Transaction> transactions = new ArrayList<>();
	    Random random = new Random();

	    for (Account account : accounts) {
	        long currentBalance = account.getBalance(); // 계좌별 초기 잔액
	        List<Transaction> accountTransactions = new ArrayList<>();

	        // 거래 데이터를 무작위로 100개의 시간에 배치
	        List<LocalDateTime> randomDates = generateRandomDatesWithinRange(100, random);

	        for (LocalDateTime transactionDate : randomDates) {
	            Transaction transaction = createTransaction(account, transactionDate, currentBalance, random);

	            // 거래 내역의 balanceAfter 업데이트
	            currentBalance = transaction.getBalanceAfter();
	            accountTransactions.add(transaction);
	        }
	        // 계좌별 생성된 거래를 전체 목록에 추가
	        transactions.addAll(accountTransactions);

	        // 최종 계좌 잔액 업데이트
	        account.setBalance(currentBalance);
	        accountDao.updateBalance(account);
	    }

	    // 모든 거래 데이터를 DB에 삽입
	    int result = transactionDao.insertTransactions(transactions);
	    if (result == 0) throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);
	}
	
	private List<LocalDateTime> generateRandomDatesWithinRange(int count, Random random) {
	    // 시작일과 종료일 정의
	    LocalDateTime startDate = LocalDate.of(LocalDate.now().getYear(), 6, 1).atStartOfDay();
	    LocalDateTime endDate = LocalDateTime.now();

	    // 전체 기간을 초 단위로 계산
	    long totalSeconds = ChronoUnit.SECONDS.between(startDate, endDate);

	    // 랜덤 가중치를 기반으로 시간 분포 생성
	    List<Long> randomOffsets = new ArrayList<>();
	    for (int i = 0; i < count; i++) {
	        randomOffsets.add((long) (random.nextDouble() * totalSeconds)); // 0 ~ totalSeconds 범위의 랜덤 값
	    }

	    // 오름차순으로 정렬하여 시간순서 보장
	    randomOffsets.sort(Long::compare);

	    // 각 오프셋을 시작일에 더해 LocalDateTime 생성
	    List<LocalDateTime> randomDates = new ArrayList<>();
	    for (Long offset : randomOffsets) {
	        randomDates.add(startDate.plusSeconds(offset));
	    }

	    return randomDates;
	}

	private LocalDateTime generateSequentialDate(int transactionIndex, int months) {
	    // 날짜는 현재 월 기준으로 'months'개월 전부터 시작
	    int totalDays = months * 30; // 최근 'months'개월 동안 30일 기준
	    LocalDate startDate = LocalDate.now().minusMonths(months).withDayOfMonth(1);

	    // transactionIndex를 기준으로 날짜 증가
	    LocalDate transactionDate = startDate.plusDays(transactionIndex % totalDays);

	    // 시간은 0~23시 사이로 랜덤
	    Random random = new Random();
	    int hour = random.nextInt(24);  // 0~23시
	    int minute = random.nextInt(60); // 0~59분
	    int second = random.nextInt(60); // 0~59초

	    return transactionDate.atTime(hour, minute, second);
	}

    
//	private void createTransactions(List<Account> accounts) {
//		List<Transaction> transactions = new ArrayList<>();
//		Random random = new Random();
//		for (Account account : accounts) {
//			
//			for (int i = 0; i < 30; i++) { // 계좌당 30개의 거래 생성
//				// 최근 6개월 내에서 랜덤 날짜 생성
//				LocalDateTime transactionDate = generateRandomDateWithinSixMonths(random);
//				
//				Transaction transaction = createTransaction(account, transactionDate, random);
//				transactions.add(transaction);
//				
//				// Account balance 업데이트
//				account.setBalance(transaction.getBalanceAfter());
//				accountDao.updateBalance(account);
//			}
//		}
//		int result = transactionDao.insertTransactions(transactions);
//		if (result == 0)
//			throw new CustomException(ErrorCode.TRANSACTION_CREATE_FAIL);
//	}
	
	private LocalDateTime generateRandomDateWithinSixMonths(Random random) {
	    // 최근 6개월 내에서 랜덤 월 선택
	    int monthOffset = random.nextInt(6); // 0부터 5까지
	    YearMonth selectedMonth = YearMonth.now().minusMonths(monthOffset);

	    // 선택된 월의 최대 일수에서 랜덤 일 선택
	    int day = random.nextInt(selectedMonth.lengthOfMonth()) + 1;

	    // 시간, 분, 초 랜덤 설정
	    int hour = random.nextInt(24);
	    int minute = random.nextInt(60);
	    int second = random.nextInt(60);

	    return LocalDateTime.of(selectedMonth.getYear(), selectedMonth.getMonth(), day, hour, minute, second);
	}

	private Transaction createTransaction(Account account, LocalDateTime transactionDate, long currentBalance, Random random) {
	    boolean isSavingsAccount = account.getAccountType() == AccountType.savings;
	    boolean isExpense = !isSavingsAccount && random.nextBoolean(); // 자유 입출금 계좌일 경우 랜덤하게 출금(true) 또는 입금(false)

	    long amount = random.nextInt(150_000) + 10_000; // 금액은 10,000 ~ 160,000 사이
	    long newBalance = isExpense ? currentBalance - amount : currentBalance + amount;

	    // 자유입출금 계좌의 지출과 적금 계좌의 이름 리스트
	    String[] expenseNames = {
	        "편의점 구매", "커피숍 결제", "마트 장보기", "교통비", "배달음식", "엄마 용돈", "아빠 용돈",
	        "휴대폰 요금", "자동차 주유", "영화관 결제", "인터넷 쇼핑", "헬스장 결제", "의료비", "보험료"
	    };

	    String[] savingsNames = {
	        "미래를 위한 저축", "내 집 마련 적금", "꿈꾸는 여행 자금", "자동차 구매 준비", "자녀 교육 적금",
	        "비상금 통장", "노후 대비 저축", "건강 보험 적립금", "결혼 자금 모으기", "장기 투자 플랜",
	        "연말 선물 준비", "가족 여행 준비", "학자금 마련 적금", "취미 생활 자금", "사업자금 모으기"
	    };

	    String merchantName = isSavingsAccount
	            ? savingsNames[random.nextInt(savingsNames.length)]
	            : expenseNames[random.nextInt(expenseNames.length)];

	    return Transaction.builder()
	            .accountId(account.getAccountId())
	            .categoryCode(random.nextInt(8) + 1)
	            .transactionDatetime(transactionDate)
	            .amount(amount)
	            .balanceAfter(newBalance)
	            .merchantName(merchantName)
	            .transactionType(isExpense ? TransactionType.expense : TransactionType.income)
	            .build();
	}

	private String generateAccountNumber() {
		Random random = new Random();
		return String.format("%03d-%05d-%03d-%04d", random.nextInt(1000), // 000-999
				random.nextInt(100000), // 00000-99999
				random.nextInt(1000), // 000-999
				random.nextInt(10000)); // 0000-9999
	}

	private LocalDate generateRandomMaturityDate(Random random) {
		return LocalDate.now().plusYears(1 + random.nextInt(5)); // 1년 ~ 5년 이내
	}

	private double generateRandomInterestRate(Random random) {
	    return Math.round((1.0 + (5.0 - 1.0) * random.nextDouble()) * 10) / 10.0;
	}

}

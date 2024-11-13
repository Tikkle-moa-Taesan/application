package com.ssafy.TmT.service;

import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.TmT.dao.BudgetDao;
import com.ssafy.TmT.dao.MemberDao;
import com.ssafy.TmT.dto.BalanceDTO;
import com.ssafy.TmT.dto.BudgetRateDTO;
import com.ssafy.TmT.dto.Category;
import com.ssafy.TmT.dto.ExpenseResponse;
import com.ssafy.TmT.dto.IdTokenPayload;
import com.ssafy.TmT.dto.Profile;
import com.ssafy.TmT.entity.Member;
import com.ssafy.TmT.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final JwtUtil jwtUtil;
	private final MemberDao memberDao;
	private final BudgetDao budgetDao;

//
//	public BalanceDTO getTotalBalance(String jwt) {
//		Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
//		Long total = getTotalBalance(memberId);
//		BalanceDTO response = new BalanceDTO(total);
//		return response;
//	}
	

	@Transactional
	public void register(String subject) {
		System.out.println("회원가입 메서드 실행");
		memberDao.regist(subject);
		System.out.println("회원가입 완료");
	}

	@Transactional
	public Profile login(String subject) {
		// 처음 로그인하는 경우 : 회원가입
		if ((memberDao.login(subject) == null)) {
			System.out.println("회원가입으로 연결");
			register(subject);
		}
		
		Profile profile = memberDao.login(subject);
		System.out.println("로그인 완료 : " + profile);
		return profile;
	}
	
	
	
}

//public BudgetRateDTO getBudgetRate(String jwt) {
//    Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
//    Long thisMonthBudget = memberDao.getThisMonthBudget(memberId);
//    Long thisMonthExpense = memberDao.getThisMonthExpense(memberId);
//    
//    Float expensePercent;
//    if (thisMonthBudget != null && thisMonthBudget > 0) {
//        expensePercent = (float) thisMonthExpense / thisMonthBudget;
//    } else {
//        expensePercent = 0.0f; // 예산이 없거나 0인 경우 비율을 0으로 설정
//    }
//    
//    BudgetRateDTO response = new BudgetRateDTO(thisMonthBudget, expensePercent);
//    return response;
//}
//public ExpenseResponse getExpenseStatistics(String jwt) {
//	Long memberId = jwtUtil.getMemberIdFromJwt(jwt);
//	Long lastMonthExpense = memberDao.getLastMonthExpense(memberId);
//	Long thisMonthExpense = memberDao.getThisMonthExpense(memberId);
//	Long lastWeekExpense = memberDao.getLastWeekExpense(memberId);
//	Long thisWeekExpense = memberDao.getThisWeekExpense(memberId);
//	Category category = memberDao.getExpenseWithCategory(memberId);
//	ExpenseResponse response = new ExpenseResponse(lastMonthExpense,thisMonthExpense,lastWeekExpense,thisWeekExpense,category);
//	return response;		
//}

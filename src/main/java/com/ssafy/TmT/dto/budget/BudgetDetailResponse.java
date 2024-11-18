package com.ssafy.TmT.dto.budget;

import java.util.List;

import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BudgetDetailResponse {

//	private BudgetDTO budgetDetail;	// 뭘 넣을지 고민
	
	private List<BudgetTransactionDTO> budgetTransactions;
}

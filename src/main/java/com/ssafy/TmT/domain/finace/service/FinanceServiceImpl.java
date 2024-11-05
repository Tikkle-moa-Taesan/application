package com.ssafy.TmT.domain.finace.service;

import org.springframework.stereotype.Service;

import com.ssafy.TmT.domain.api.ApiUtil;
import com.ssafy.TmT.domain.finace.dto.FinanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinaneService {

	private final ApiUtil apiUtil;
	
	@Override
	public void 계좌통합조회(FinanceRequest request) {
		FundResponse response = apiUtil.계좌통합조회();
		return response;
	}

}

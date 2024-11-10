package com.ssafy.TmT.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.TmT.dto.BalanceDTO;

public interface MemberDao {

	BalanceDTO getTotalBalance(Long memberId);

}

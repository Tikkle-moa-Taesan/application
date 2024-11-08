package com.ssafy.TmT.domain.account.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.TmT.domain.account.dto.AccountResponse;
import com.ssafy.TmT.trashbin.Account;

@Mapper
public interface AccountMapper {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

         // RequestDto -> MessageBodyDto 매핑
	AccountResponse toAccountResponse(Account account);
}

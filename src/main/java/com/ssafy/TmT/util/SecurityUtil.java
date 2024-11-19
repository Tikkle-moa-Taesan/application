package com.ssafy.TmT.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssafy.TmT.exception.CustomException;
import com.ssafy.TmT.exception.ErrorCode;

public class SecurityUtil {

	public static Long getAuthenticatedMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
			throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
		}
		try {
			return (Long) authentication.getPrincipal();
		} catch (ClassCastException e) {
			throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
		}
	}
}

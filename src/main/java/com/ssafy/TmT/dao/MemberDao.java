package com.ssafy.TmT.dao;


import com.ssafy.TmT.dto.oauth.IdTokenPayload;
import com.ssafy.TmT.dto.oauth.Profile;

public interface MemberDao {

	void regist(IdTokenPayload subject);

	Profile login(String subject);

	Profile getProfile(Long memberId);

}

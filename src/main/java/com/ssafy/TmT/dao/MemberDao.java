package com.ssafy.TmT.dao;


import java.util.Optional;

import com.ssafy.TmT.dto.member.MemberTotalDataDTO;
import com.ssafy.TmT.dto.oauth.IdTokenPayload;
import com.ssafy.TmT.dto.oauth.Profile;

public interface MemberDao {

	void regist(IdTokenPayload subject);

	Profile login(String subject);

	Profile getProfile(Long memberId);

	MemberTotalDataDTO getAllData(Long memberId);

}

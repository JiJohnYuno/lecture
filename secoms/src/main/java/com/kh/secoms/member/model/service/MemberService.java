package com.kh.secoms.member.model.service;

import com.kh.secoms.member.model.vo.ChangePasswordDTO;
import com.kh.secoms.member.model.vo.MemberDTO;

import jakarta.validation.Valid;

public interface MemberService {
	
	void save(MemberDTO requestMember);

	void changePassword(@Valid ChangePasswordDTO changeEntity);
}

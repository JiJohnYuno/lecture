package com.kh.secoms.auth.service;

import java.util.Map;

import com.kh.secoms.member.model.vo.MemberDTO;

public interface AuthenticationService {
	
	Map<String, String> login(MemberDTO requestMember);
}

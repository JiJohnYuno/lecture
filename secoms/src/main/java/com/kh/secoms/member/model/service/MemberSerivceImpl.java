package com.kh.secoms.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secoms.auth.model.vo.CustomUserDetails;
import com.kh.secoms.exception.DuplicateUserException;
import com.kh.secoms.exception.InvalidParameterException;
import com.kh.secoms.exception.MissmatchPasswordException;
import com.kh.secoms.member.model.mapper.MemberMapper;
import com.kh.secoms.member.model.vo.ChangePasswordDTO;
import com.kh.secoms.member.model.vo.Member;
import com.kh.secoms.member.model.vo.MemberDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberSerivceImpl implements MemberService { 
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void save(MemberDTO requestMember) { //일반 사용자용 가입 메소드
		if("".equals(requestMember.getUserId()) ||
		"".equals(requestMember.getUserPwd())) {
			throw new InvalidParameterException("유효하지 않은 값입니다.");
		}
		// DB에 이미 사용자가 입력한 사용자가 존재해서는 안됨
		Member searched = memberMapper.findByUserId(requestMember.getUserId());
		if(searched != null) {
			throw new DuplicateUserException("이미 존재하는 아이디 입니다.");
		}
		
		
		//비밀번호 평문이라 그냥 들어가면 안됨
		// + ROLE == USER라고 저장할 예정
		Member member = Member.builder()
							  .userId(requestMember.getUserId())
							  .userPwd(passwordEncoder.encode(requestMember.getUserPwd()))
							  .role("ROLE_USER")
							  .build();
		memberMapper.save(member);
		log.info("회원 가입 성공");
	}

	@Override
	public void changePassword(@Valid ChangePasswordDTO changeEntity) {
		
		// 비밀번호 바꿔주세요. 
		// 제 현재 비밀번호는 currentPassword입니다.
		// 이게 만약에 맞다면 newPassword로 바꾸고싶어요.
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		
		if(!(passwordEncoder.matches(changeEntity.getCurrentPassword(), user.getPassword()))) {
			throw new MissmatchPasswordException("비밀번호가 틀렸습니다.");
		}
		
		String encodedPassword = passwordEncoder.encode(changeEntity.getNewPassword());
		
		Map<String, String> changeRequest = new HashMap();
		changeRequest.put("userNo",String.valueOf(user.getUserNo()));
		changeRequest.put("password", encodedPassword);
		memberMapper.changePassword(changeRequest);
	}
}

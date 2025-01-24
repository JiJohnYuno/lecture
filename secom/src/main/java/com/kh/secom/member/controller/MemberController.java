package com.kh.secom.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	private final MemberService memberService;
	private final AuthenticationService authService;
	// 새롭게 데이터를 만들어내는 요청(INSERT) == POST
	@PostMapping
	public ResponseEntity<String> save(@RequestBody MemberDTO requestMember) {
		
		//log.info("요청 한 사용자의 데이터 : {}", requestMember);
		memberService.save(requestMember);
		return ResponseEntity.ok("회원가입에 성공했습니다.");
	
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody MemberDTO requestMember){
		
		// 로그인 구현
		
		/*
		 * 로그인에 성공했을 때???? ===> 인증 ===> 이거 원래 개발자가 했음	
		 * 아이디 / 비밀번호(평문)
		 *
		 * 아이디 / 비밀번호(암호문)
		 *
		 */
		authService.login(requestMember);
		// 로그인에 성공 했을 때
		// AccessToken
		// RefreshToken 반환
		
		return null;
	}
	
}

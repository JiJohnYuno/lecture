package com.kh.secoms.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.kh.secoms.auth.model.vo.CustomUserDetails;
import com.kh.secoms.auth.util.JwtUtil;
import com.kh.secoms.exception.InvalidParameterException;
import com.kh.secoms.member.model.vo.MemberDTO;
import com.kh.secoms.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	//private final JwtUtil jwt;
	private final TokenService tokenService;
	@Override 
	public Map<String, String> login(MemberDTO requestMember) {
		try {
		// 사용자 인증
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMember.getUserId(),requestMember.getUserPwd()));
		
		// UsernamePasswordAuthenticationToken
		/*
		 * 사용자가 입력한 username과 password를 검증하는 용도로 사용하는 클래스
		 * 주로, SpringSecurity에서 인증을 시도할 때 사용함
		 * 
		 */
		
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		log.info("로그인 절차 성공!");
		log.info("DB에서 조회된 사용자의 정보 : {}", user);
		
		//jwt.view();
		//String accessToken = jwt.getAccessToken(user.getUsername());
		//String refreshToken = jwt.getRefreshToken(user.getUsername());
		//log.info("액세스토큰 발급 : {}",accessToken);
		//log.info("리프레시토큰 발급 : {}",refreshToken);
		
		Map<String, String> tokens = tokenService.generateToken(user.getUsername(), user.getUserNo());
		return tokens;
		}catch(AuthenticationException e) {
			throw new InvalidParameterException("아이디 또는 비밀번호가 잘못되었습니다.");
		}
	}

}

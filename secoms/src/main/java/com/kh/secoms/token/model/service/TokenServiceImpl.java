package com.kh.secoms.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.secoms.auth.util.JwtUtil;
import com.kh.secoms.token.model.dto.RefreshTokenDTO;
import com.kh.secoms.token.model.mapper.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
	
	private final JwtUtil tokenUtil;
	private final TokenMapper mapper;
	@Override
	public Map<String, String> generateToken(String username, Long userNo) {
		
		// 1.액세스 토큰 만들기
		
		// 2. 리프레시 토큰 만들기
		Map<String, String> tokens = createTokens(username);
		// 3. 리프레시 토큰 DB에 저장하기
		saveToken(tokens.get("refreshToken"),userNo);
		// 4. 만료기간이 지난 리프레시 토큰이 있을 수 있으니 지워버리기
		
		
		// 5. 사용자가 리프레시 토큰 증명하려 할때 DB가서 조회해오기
		return tokens;
	}
	//1, 2번
	private Map<String, String> createTokens(String userName){
		String accessToken = tokenUtil.getAccessToken(userName);
		String refreshToken = tokenUtil.getRefreshToken(userName);
		
		Map<String, String> tokens = new HashMap();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		
		return tokens;
	}
	//3번
	private void saveToken(String refreshToken,Long userNo) {
		
		RefreshTokenDTO token = RefreshTokenDTO.builder()
													    .token(refreshToken)
													    .userNo(userNo)
													    .expiration(System.currentTimeMillis() + 3600000L * 72)
													    .build();
		mapper.saveToken(token);
	}
	
}

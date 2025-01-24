package com.kh.secoms.token.model.service;

import java.util.Map;

public interface TokenService {

	
	Map<String, String> generateToken(String username, Long userNo);
}

package com.kh.secom.auth.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secretKey;
	private SecretKey key;
	

	
	
}

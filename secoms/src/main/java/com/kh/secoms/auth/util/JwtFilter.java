package com.kh.secoms.auth.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.secoms.auth.service.UserServiceImpl;
import com.kh.secoms.exception.AccessTokenExpiredException;
import com.kh.secoms.exception.JwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil tokenUtil;
	private final UserServiceImpl userService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		log.info(authorization);

		if (authorization == null || !authorization.startsWith("Bearer")) {
			log.error("authorization이 존재하지 않습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		// 토큰만 쏙 뽑아내기
		String token = authorization.split(" ")[1];
		// 1. 이거 내 비밀키로 만든거임?
		// 2. 이거 유효기간 안지남?
		try {
			Claims claims = tokenUtil.parseJwt(token);

			String username = claims.getSubject();

			log.info("토큰 주인 아이디 : {}", username);
			
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities()
					);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
			
		} catch (ExpiredJwtException e) {
			log.info("AccessToken이 만료되었습니다.");
			//throw new AccessTokenExpiredException("토큰이 만료되었습니다.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Expired Token");
			return;
		} catch (JwtException e) {
			log.info("Token 검증에 실패했습니다.");
			//throw new JwtTokenException("이상");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("이상");
		}
		filterChain.doFilter(request, response);
	}

}

package com.kh.secoms.token.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.secoms.token.model.dto.RefreshTokenDTO;

@Mapper
public interface TokenMapper {
	
	@Insert("INSERT INTO REFRESH_TOKEN VALUES(#{userNo},#{token},#{expiration})")
	void saveToken(RefreshTokenDTO refreshToken);
	
}

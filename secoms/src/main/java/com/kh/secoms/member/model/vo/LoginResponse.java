package com.kh.secoms.member.model.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResponse {
	private String username;
	private Map<String, String> tokens;

	
}

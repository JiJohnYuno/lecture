package com.kh.secom.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor	
@ToString
public class MemberDTO {
	private Long userNo;
	private String userId;
	private String userPwd;
	private String role;
}

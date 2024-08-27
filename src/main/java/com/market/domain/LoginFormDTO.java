package com.market.domain;

import com.market.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginFormDTO {
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Email(message = "유효한 이메일 주소를 입력해 주세요.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Size(min = 4, message = "비밀번호는 최소 4글자 이상이어야 합니다.")
	private String password;
	
	public User toUser() {
		return User.builder().email(getUsername()).password(getPassword()).build();
	}	
}

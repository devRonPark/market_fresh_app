package com.market.domain;

import com.market.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JoinFormDTO {
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Email(message = "유효한 이메일 주소를 입력해 주세요.")
	private String email;
	
	@NotBlank(message = "이름은 필수 입력 항목입니다.")
	@Size(min = 3, message = "이름은 최소 3글자 이상이어야 합니다.")
	private String name;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Size(min = 4, message = "비밀번호는 최소 4글자 이상이어야 합니다.")
	private String password;

	@NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
	private String passwordConfirm;
	
	private UserRole role = UserRole.CUSTOMER;
	
	public User toUser() {
		return User.builder().name(getName()).email(getEmail()).password(getPassword()).role(role).build();
	}
	
	public boolean isPasswordMatching() {
		return password != null && password.equals(passwordConfirm);
	}
}

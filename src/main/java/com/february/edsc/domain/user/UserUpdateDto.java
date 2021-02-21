package com.february.edsc.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateDto {
	private final String name;
	private final String nickname;
	private final String phone;
	private final String password;
	private final String profileImage;

	public boolean isRequiredFieldNull() {
		return (name == null) || (name.isEmpty()) ||
			(nickname == null) || (nickname.isEmpty()) ||
			(phone == null) || (phone.isEmpty()) ||
			(password == null) || (password.isEmpty());
	}
}

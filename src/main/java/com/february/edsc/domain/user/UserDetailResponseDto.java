package com.february.edsc.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailResponseDto {
	private final String email;
	private final String name;
	private final String nickname;
	private final String phone;
	private final String profileImage;
	private final Long postNum;
}

package com.february.edsc.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserListResponseDto {
	private final int totalNum;
	private final List<UserDetailResponseDto> userList;
}

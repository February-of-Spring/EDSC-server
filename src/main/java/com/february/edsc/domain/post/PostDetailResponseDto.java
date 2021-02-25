package com.february.edsc.domain.post;

import com.february.edsc.domain.category.CategoryResponseDto;
import com.february.edsc.domain.post.comment.CommentPackResponseDto;
import com.february.edsc.domain.post.file.FileResponseDto;
import com.february.edsc.domain.post.image.ImageResponseDto;
import com.february.edsc.domain.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
	private final Long id;
	private final UserResponseDto user;
	private final String title;
	private final String content;
	private final int likeCount;
	private final int viewCount;
	private final Timestamp createdAt;
	private final Timestamp modifiedAt;
	private final CategoryResponseDto category;
	private final List<ImageResponseDto> imageList;
	private final List<FileResponseDto> fileList;
	private final int commentNum;
	private final List<CommentPackResponseDto> commentList;
}

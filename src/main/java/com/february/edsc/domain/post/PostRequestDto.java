package com.february.edsc.domain.post;


import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.comment.Comment;
import com.february.edsc.domain.post.file.File;
import com.february.edsc.domain.post.image.Image;
import com.february.edsc.domain.user.User;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class PostRequestDto {
	private User user;
	private String title;
	private String content;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	private Category category;
	private List<Image> images;
	private List<File> files;
}

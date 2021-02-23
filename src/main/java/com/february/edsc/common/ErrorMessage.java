package com.february.edsc.common;

public class ErrorMessage {
	public static final String NO_SUCH_USER = "존재하지 않는 유저입니다.";
	public static final String NO_SUCH_POST = "존재하지 않는 게시물입니다.";
	public static final String REQUIRED_FIELD_NULL = "필수 항목을 입력해주세요.";
	public static final String NO_SUCH_CATEGORY = "존재하지 않는 카테고리입니다.";
	public static final String DUPLICATE_CATEGORY = "이미 존재하는 카테고리입니다.";
	public static final String NO_SUCH_PARENT_CATEGORY = "카테고리의 상위 항목이 존재하지 않습니다.";
	public static final String CATEGORY_HAS_POSTS = "게시물이 존재하는 카테고리는 삭제할 수 없습니다.";
	public static final String CATEGORY_HAS_CHILD = "하위 카테고리가 존재하는 그룹은 삭제할 수 없습니다.";
	public static final String NO_SUCH_COMMENT = "존재하지 않는 댓글입니다.";
	public static final String NO_SUCH_PARENT_COMMENT = "상위 댓글이 존재하지 않습니다.";
	public static final String BAD_REQUEST = "잘못된 경로로 접근하셨습니다.";
	public static final String UNAUTHORIZED_TO_UPDATE = "수정할 수 있는 권한이 없습니다.";
	public static final String UNAUTHORIZED_TO_DELETE = "삭제할 수 있는 권한이 없습니다.";
}

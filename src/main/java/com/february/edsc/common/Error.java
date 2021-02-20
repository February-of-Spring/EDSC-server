package com.february.edsc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Error {
	HttpStatus httpStatus;
	String errorMessage;
}

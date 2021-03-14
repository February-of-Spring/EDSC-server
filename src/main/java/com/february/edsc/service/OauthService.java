package com.february.edsc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {
	private final GoogleOauth googleOauth;
	private final HttpServletResponse response;

	public void request() {
		String redirectURL = googleOauth.getOauthRedirectURL();
		try {
			response.sendRedirect(redirectURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String requestAccessToken(String code) {
		return googleOauth.requestAccessToken(code);
	}
}

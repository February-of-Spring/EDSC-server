package com.february.edsc.controller;

import com.february.edsc.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class OauthController {

	private final OauthService oauthService;

	@GetMapping("/google")
	public void login() {
		log.info("[*] 사용자로부터 GOOGLE 로그인 요청을 받음 :: GOOGLE Login");
		oauthService.request();
	}

	@GetMapping(value = "/google/callback")
	public String callback(@RequestParam(name = "code") String code) {
		log.info("[*] 소셜 로그인 API 서버로부터 받은 code :: {}", code);
		return oauthService.requestAccessToken(code);
	}
}

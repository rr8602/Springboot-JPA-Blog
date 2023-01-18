package com.rr8602.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 자동으로 Bean에 등록
public class BoardController {

	@GetMapping("/")
	public String index() {
		// /WEB-INF/views/index.jsp
		return "index";
	}
}

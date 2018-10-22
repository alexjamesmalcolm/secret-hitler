package com.alexjamesmalcolm.secrethitler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LobbyController {
	
	@RequestMapping("/")
	public String loginView() {
		return "login";
	}
}

package com.alexjamesmalcolm.secrethitler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {
	
	@GetMapping("/")
	public String loginView() {
		return "login";
	}
}

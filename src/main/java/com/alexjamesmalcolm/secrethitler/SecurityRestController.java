package com.alexjamesmalcolm.secrethitler;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityRestController {
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
}

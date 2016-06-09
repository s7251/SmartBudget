package pl.smartbudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/user-login")
	public String login() {
		return "user-login";
	}
}

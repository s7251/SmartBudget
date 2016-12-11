package pl.smartbudget.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.forms.ResetPasswordForm;
import pl.smartbudget.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user-login")
	public String login(Model model) {		
		model.addAttribute("ResetPasswordForm", new ResetPasswordForm());
		return "user-login";
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute("ResetPasswordForm") ResetPasswordForm resetPasswordForm)	throws ParseException {		
		userService.ResetPassword(resetPasswordForm.getName(), resetPasswordForm.getEmail());
		return "redirect:/user-login.html";
	}	
}

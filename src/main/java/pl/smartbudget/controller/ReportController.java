package pl.smartbudget.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.smartbudget.service.UserService;

@Controller
public class ReportController {

	@Autowired
	private UserService userService;

	@RequestMapping("/user-reports")
	public String reports(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		return "user-reports";
	}

}

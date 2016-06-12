package pl.smartbudget.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.entity.User;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
	
	@ModelAttribute("user")
	public User construct() {
		return new User();
	}

	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users";
	}

	@RequestMapping("/users/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("user", userService.findOne(id));
		return "user-detail";
	}
	
	@RequestMapping("/user-profile")
	public String profile(Model model, Principal principal) {
		String name = principal.getName();		
		model.addAttribute("user", userService.findOneByName(name));
		return "user-profile";
	}

	@RequestMapping("/user-register")
	public String showRegister() {
		return "user-register";
	}

	@RequestMapping(value = "user-register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute("user") User user) {
		userService.save(user);		
		return "redirect:/user-register.html?success=true";
	}

	@RequestMapping("/user-transactions")
	public String transactions(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccounts(name));
		return "user-transactions";
	}
	
	@RequestMapping("/user-accounts")
	public String accounts(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccounts(name));
		model.addAttribute("summaryOfAccounts", transactionService.summaryTransactionsOfAccounts(name));
		return "user-accounts";
	}
	
	@RequestMapping("/user-categories")
	public String categories(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategories(name));
		
		return "user-categories";
	}

	@RequestMapping("/user-budgetplan")
	public String budgetplan(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategories(name));
		
		return "user-budgetplan";
	}
	
}

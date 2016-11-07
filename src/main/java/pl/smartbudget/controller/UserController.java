package pl.smartbudget.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.entity.User;
import pl.smartbudget.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}

	@RequestMapping("/users")
	public String users(Model model, Principal principal) {		
		String name = principal.getName();
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", userService.findOneByName(name));
		return "users";
	}
	
	@RequestMapping("/users/removeuser/{id}")
	public String removeUser(@PathVariable int id){
		String name = userService.findOne(id).getName();
		userService.delete(id, name);
		return "redirect:/users.html";
	}
	
	@RequestMapping(value = "/renameUser", method = RequestMethod.POST)
	public String renameUser(@ModelAttribute("user") User user)	throws ParseException {	
		userService.save(user);
		return "redirect:/users.html";
	}
	
	@RequestMapping("/user-profile/removeprofile/{id}")
	public String removeProfile(@PathVariable int id){
		String name = userService.findOne(id).getName();
		userService.delete(id, name);
		return "redirect:/logout";
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

	@RequestMapping(value = "/user-register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute("user") User user) {
		userService.save(user);
		return "redirect:/user-register.html?success=true";
		}

}

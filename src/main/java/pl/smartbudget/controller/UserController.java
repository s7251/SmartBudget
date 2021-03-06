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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.smartbudget.entity.User;
import pl.smartbudget.forms.AddUserByAdminForm;
import pl.smartbudget.forms.ChangeRolesForm;
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
		model.addAttribute("AddUserByAdminForm", new AddUserByAdminForm());
		model.addAttribute("ChangeRolesForm", new ChangeRolesForm());
		model.addAttribute("User", new User());
		model.addAttribute("users", userService.findAllWithRoles());
		model.addAttribute("loginName", name);	
		return "users";
	}
	
	@RequestMapping("/user-profile")
	public String profile(Model model, Principal principal) {				
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithRoles(name));
		return "user-profile";
	}

	@RequestMapping("/user-register")
	public String showRegister() {
		return "user-register";
	}
	
	@RequestMapping("/users/{id}")
	public String detail(Model model, @PathVariable int id, Principal principal) {
		String nameUrl = userService.findOne(id).getName();
		String name = principal.getName();
		model.addAttribute("loginName", name);
		model.addAttribute("userDetail", userService.findOneWithRoles(nameUrl));
		return "user-detail";
	}
	
	@RequestMapping("/user-available")
	@ResponseBody
	public String userAvailable(@RequestParam String name) {
		Boolean userAvailable = userService.findOne(name) == null;
		return userAvailable.toString();
	}
	
	@RequestMapping("/email-available")
	@ResponseBody
	public String emailAvailable(@RequestParam String email) {
		Boolean emailAvailable = userService.findOneByEmail(email) == null;
		return emailAvailable.toString();
	}
	
	@RequestMapping("/users/removeuser/{id}")
	public String removeUser(@PathVariable int id){
		String name = userService.findOne(id).getName();
		userService.delete(id, name);
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/renameUser", method = RequestMethod.POST)
	public String renameUser(@ModelAttribute("user") User user)	throws ParseException {	
		userService.save(user);
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/user-register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute("user") User user) {
		userService.save(user);
		return "redirect:/user-register?success=true";
		}
	
	@RequestMapping(value = "/add-user-by-admin", method = RequestMethod.POST)
	public String addUserByAdmin(@ModelAttribute("AddUserByAdminForm") AddUserByAdminForm form) {
		User user = new User();		
		userService.userSetRoles(user, form);		
		userService.saveUserByAdmin(user, form);
		return "redirect:/users";
		}
	
	@RequestMapping(value = "/change-roles", method = RequestMethod.POST)
	public String changeRoles(@ModelAttribute("ChangeRolesForm") ChangeRolesForm form) {
		userService.changeRoles(form);
		
		return "redirect:/users";
		}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("user") User user) {
		userService.changePassword(user);
		return "redirect:/users";
		}
	
	@RequestMapping(value = "/change-password-from-profile", method = RequestMethod.POST)
	public String changePasswordFromProfile(@ModelAttribute("user") User user) {
		userService.changePassword(user);
		return "redirect:/user-profile";
		}

	@RequestMapping(value = "/change-email", method = RequestMethod.POST)
	public String changeEmail(@ModelAttribute("user") User user) {
		userService.changeEmail(user);
		return "redirect:/users";
		}
	
	@RequestMapping(value = "/change-email-from-profile", method = RequestMethod.POST)
	public String changeEmailFromProfile(@ModelAttribute("user") User user) {
		userService.changeEmail(user);
		return "redirect:/user-profile";
		}
	
	@RequestMapping("/user-profile/removeprofile/{id}")
	public String removeProfile(@PathVariable int id){
		String name = userService.findOne(id).getName();
		userService.delete(id, name);
		return "redirect:/logout";
	}
}

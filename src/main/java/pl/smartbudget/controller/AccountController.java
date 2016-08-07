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

import pl.smartbudget.entity.Account;
import pl.smartbudget.forms.AlignBalanceForm;
import pl.smartbudget.service.AccountService;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/user-accounts")
	public String accounts(Model model, Principal principal) {
		model.addAttribute("account", new Account());
		model.addAttribute("AlignBalanceForm", new AlignBalanceForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccounts(name));
		model.addAttribute("summaryOfAccounts", transactionService.summaryTransactionsOfAccounts(name));
		model.addAttribute("summaryOfAllAccounts", transactionService.summaryTransactionsOfAllAccounts(name));		
		return "user-accounts";
	}
	
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute("account") Account account, Principal principal)	throws ParseException {
		String name = principal.getName();
		accountService.save(account, name);
		return "redirect:/user-accounts.html";
	}
	
	@RequestMapping(value = "/renameAccount", method = RequestMethod.POST)
	public String renameAccount(@ModelAttribute("account") Account account, Principal principal)	throws ParseException {
		String name = principal.getName();
		accountService.save(account, name);
		return "redirect:/user-accounts.html";
	}
	
	@RequestMapping("/user-accounts/removeaccount/{id}")
	public String removeAccount(@PathVariable int id ){
		accountService.delete(id);
		return "redirect:/user-accounts.html";
	}
		
	@RequestMapping(value = "/alignBalance", method = RequestMethod.POST)
	public String alignBalance(@ModelAttribute("AlignBalanceForm") AlignBalanceForm alignBalanceForm, Principal principal)	throws ParseException {
		String name = principal.getName();
		transactionService.saveAlignBalance(alignBalanceForm, name);
		return "redirect:/user-transactions.html";
	}

	

}

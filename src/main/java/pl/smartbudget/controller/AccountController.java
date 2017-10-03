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
import pl.smartbudget.forms.InternalTransferForm;
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
		String name = principal.getName();
		model.addAttribute("Account", new Account());
		model.addAttribute("AlignBalanceForm", new AlignBalanceForm());
		model.addAttribute("InternalTransferForm", new InternalTransferForm());
		model.addAttribute("accountsMap", userService.getAccountsMapOfUser(name));
		model.addAttribute("user", userService.findOneWithAccounts(name));
		model.addAttribute("summaryOfAccounts", transactionService.summaryTransactionsOfAccounts(name));
		model.addAttribute("summaryOfAllAccounts", transactionService.summaryTransactionsOfAllAccounts(name));
		return "user-accounts";
	}

	@RequestMapping("/account-forecast/{id}")
	public String detail(Model model, Principal principal, @PathVariable int id) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccounts(name));
		model.addAttribute("accountName", accountService.getForecastAccount(id));
		model.addAttribute("dataForForecast", transactionService.forecastByAccount(id, name).get(0));
		model.addAttribute("forecastDataByAccount", transactionService.forecastByAccount(id, name).get(1));
		return "account-forecast";
	}
	
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute("account") Account account, Principal principal) throws ParseException {
		String name = principal.getName();
		accountService.save(account, name);
		return "redirect:/user-accounts";
	}

	@RequestMapping(value = "/renameAccount", method = RequestMethod.POST)
	public String renameAccount(@ModelAttribute("account") Account account, Principal principal) throws ParseException {
		String name = principal.getName();
		accountService.save(account, name);
		return "redirect:/user-accounts";
	}

	@RequestMapping(value = "/alignBalance", method = RequestMethod.POST)
	public String alignBalance(@ModelAttribute("AlignBalanceForm") AlignBalanceForm alignBalanceForm,
			Principal principal) throws ParseException {
		String name = principal.getName();
		transactionService.saveAlignBalance(alignBalanceForm, name);
		return "redirect:/user-transactions";
	}

	@RequestMapping(value = "/internalTransfer", method = RequestMethod.POST)
	public String internalTransfer(@ModelAttribute("InternalTransferForm") InternalTransferForm internalTransferForm,
			Principal principal) throws ParseException {
		String name = principal.getName();
		transactionService.saveInternalTransfer(internalTransferForm, name);
		return "redirect:/user-transactions";
	}
	
	@RequestMapping("/user-accounts/removeaccount/{id}")
	public String removeAccount(@PathVariable int id, Principal principal) {
		Account account = accountService.findOne(id);
		String name = principal.getName();
		accountService.delete(account, name);
		return "redirect:/user-accounts";
	}



}

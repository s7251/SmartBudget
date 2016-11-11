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

import pl.smartbudget.entity.Transaction;
import pl.smartbudget.forms.TransactionForm;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class TransactionController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
		
	@RequestMapping("/user-transactions")
	public String transactions(Model model, Principal principal) throws ParseException {
		model.addAttribute("TransactionForm", new TransactionForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccountsAndTransactions(name));
		model.addAttribute("userTransactions", transactionService.findAllTransactionOfUserByActualMonth(name));
		model.addAttribute("monthSummary", transactionService.getSummaryOfActualMonth(name));
		model.addAttribute("subcategoriesMap", userService.getSubcategoriesMapOfUser(name));
		model.addAttribute("accountsMap", userService.getAccountsMapOfUser(name));
		model.addAttribute("actualMonth", transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));	
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));		
		model.addAttribute("subcategoriesForecast", transactionService.getSubcategoriesForecastForActualMonth(name));
		model.addAttribute("month", transactionService.getMonthByViewedTransactionsByActualMonth(transactionService.findAllTransactionOfUserByActualMonth(name)));
		model.addAttribute("year", transactionService.geYearByViewedTransactionsByActualMonth(transactionService.findAllTransactionOfUserByActualMonth(name)));
		return "user-transactions";
	}
	
	@RequestMapping("/user-transactions/{date}")
	public String transactions(Model model, Principal principal, @PathVariable String date) throws ParseException {
		model.addAttribute("TransactionForm", new TransactionForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccountsAndTransactions(name));
		model.addAttribute("userTransactions", transactionService.findAllTransactionOfUserByDate(name, date));
		model.addAttribute("monthSummary", transactionService.getSummaryOfMonthByDate(name, date));
		model.addAttribute("subcategoriesMap", userService.getSubcategoriesMapOfUser(name));
		model.addAttribute("accountsMap", userService.getAccountsMapOfUser(name));	
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("actualMonthNav", transactionService.geActualtMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("date", date);
		model.addAttribute("month", transactionService.getMonthByOtherMonth(date));
		model.addAttribute("year", transactionService.getYearByOtherMonth(date));
		return "user-transactions";
	}
	
	@RequestMapping("/user-transactions/removetransaction/{id}/{date}")
	public String removeTransaction(@PathVariable int id, @PathVariable String date, Principal principal){
		String name = principal.getName();
		Transaction transaction = transactionService.findOne(id);
		String userNameByTransactionId = userService.findUserNameByTransactionId(transaction);		
		transactionService.delete(transaction, name, date, userNameByTransactionId);
		return "redirect:/user-transactions.html";
	}

	@RequestMapping(value = "/user-transactions", method = RequestMethod.POST)
	public String addTransaction(@ModelAttribute("TransactionForm") TransactionForm transaction, Principal principal)	throws ParseException {
		String name = principal.getName();
		transactionService.save(transaction, name);
		return "redirect:/user-transactions.html";
	}
	
	@RequestMapping(value = "/user-transactions/{date}", method = RequestMethod.POST)
	public String addTransaction(@ModelAttribute("TransactionForm") TransactionForm transaction, Principal principal, @PathVariable String date)	throws ParseException {
		String name = principal.getName();
		transactionService.save(transaction, name);
		return "redirect:/user-transactions/{date}.html";				
	}
	
	@RequestMapping(value = "/editTransaction/{date}", method = RequestMethod.POST)
	public String editTransaction(@ModelAttribute("TransactionForm") TransactionForm transaction, Principal principal, @PathVariable String date)	throws ParseException {
		String name = principal.getName();
		transactionService.edit(transaction, name);
		return "redirect:/user-transactions.html";
	}
		
	}

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
import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.User;
import pl.smartbudget.forms.SubcategoryForm;
import pl.smartbudget.forms.SubcategoryLimitForm;
import pl.smartbudget.forms.TransactionForm;
import pl.smartbudget.service.AccountService;
import pl.smartbudget.service.CategoryService;
import pl.smartbudget.service.SubcategoryLimitService;
import pl.smartbudget.service.SubcategoryService;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SubcategoryService subcategoryService;
	
	@Autowired
	private SubcategoryLimitService subcategoryLimitService;	

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
	public String removeUser(@PathVariable int id ){
		userService.delete(id);
		return "redirect:/users.html";
	}
	
	@RequestMapping("/user-profile/removeprofile/{id}")
	public String removeProfile(@PathVariable int id ){
		userService.delete(id);
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

	@RequestMapping("/user-transactions")
	public String transactions(Model model, Principal principal) throws ParseException {
		model.addAttribute("TransactionForm", new TransactionForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccountsAndTransactions(name));
		model.addAttribute("userTransactions", transactionService.findAllTransactionOfUserByActualMonth(name));
		model.addAttribute("subcategoriesMap", userService.getSubcategoriesMapOfUser(name));
		model.addAttribute("accountsMap", userService.getAccountsMapOfUser(name));
		model.addAttribute("actualMonth", transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));	
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));	
		return "user-transactions";
	}
	
	@RequestMapping("/user-transactions/{date}")
	public String transactions(Model model, Principal principal, @PathVariable String date) throws ParseException {
		model.addAttribute("TransactionForm", new TransactionForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccountsAndTransactions(name));
		model.addAttribute("userTransactions", transactionService.findAllTransactionOfUserByDate(name, date));
		model.addAttribute("subcategoriesMap", userService.getSubcategoriesMapOfUser(name));
		model.addAttribute("accountsMap", userService.getAccountsMapOfUser(name));
		model.addAttribute("actualMonth", transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));	
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));	
		return "user-transactions";
	}
	
	@RequestMapping("/user-transactions/removetransaction/{id}")
	public String removeTransaction(@PathVariable int id ){
		transactionService.delete(id);
		return "redirect:/user-transactions.html";
	}

	@RequestMapping(value = "/user-transactions", method = RequestMethod.POST)
	public String addTransaction(@ModelAttribute("TransactionForm") TransactionForm transaction, Principal principal)	throws ParseException {
		String name = principal.getName();
		transactionService.save(transaction, name);
		return "redirect:/user-transactions.html";
	}
	
	@RequestMapping(value = "/user-transactions/{date}", method = RequestMethod.POST)
	public String addTransaction(@ModelAttribute("TransactionForm") TransactionForm transaction, Principal principal, String date)	throws ParseException {
		String name = principal.getName();
		transactionService.save(transaction, name);
		return "redirect:/user-transactions/{date}.html";
	}

	@RequestMapping("/user-accounts")
	public String accounts(Model model, Principal principal) {
		model.addAttribute("account", new Account());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithAccounts(name));
		model.addAttribute("summaryOfAccounts", transactionService.summaryTransactionsOfAccounts(name));
		model.addAttribute("summaryOfAllAccounts", transactionService.summaryTransactionsOfAllAccounts(name));		
		return "user-accounts";
	}
	
	@RequestMapping("/user-accounts/removeaccount/{id}")
	public String removeAccount(@PathVariable int id ){
		accountService.delete(id);
		return "redirect:/user-accounts.html";
	}
	
	@RequestMapping(value = "/user-accounts", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute("account") Account account, Principal principal)	throws ParseException {
		String name = principal.getName();
		accountService.save(account, name);
		return "redirect:/user-accounts.html";
	}

	@RequestMapping("/user-categories")
	public String categories(Model model, Principal principal) {
		model.addAttribute("subcategory", new SubcategoryForm());
		model.addAttribute("category", new Category());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));		
		return "user-categories";
	}
	
	@RequestMapping("/user-categories/removecategory/{id}")
	public String removeCategory(@PathVariable int id ){
		categoryService.delete(id);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping("/user-categories/removesubcategory/{id}")
	public String removeSubcategory(@PathVariable int id ){
		subcategoryService.delete(id);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping(value = "/addCategories", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category, Principal principal)	throws ParseException {
		String name = principal.getName();
		categoryService.save(category, name);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping(value = "/addSubcategories", method = RequestMethod.POST)
	public String addSubcategory(@ModelAttribute("subcategory") SubcategoryForm subcategory) {		
		subcategoryService.save(subcategory);
		return "redirect:/user-categories.html";
	}


	@RequestMapping("/user-budgetplan")
	public String budgetplan(Model model, Principal principal) {
		model.addAttribute("subcategorylimit", new SubcategoryLimitForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesSubcategoriesAndSubcategoryLimit(name));
		return "user-budgetplan";
	}
	
	@RequestMapping(value = "/addSubcategoryLimit", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("subcategorylimit") SubcategoryLimitForm subcategoryLimitForm, Principal principal)	throws ParseException {
		String name = principal.getName();
		subcategoryLimitService.save(subcategoryLimitForm, name);
		return "redirect:/user-budgetplan.html";
	}

	@RequestMapping("/user-reports")
	public String reports(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		return "user-reports";
	}

}

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

import pl.smartbudget.forms.SubcategoryLimitForm;
import pl.smartbudget.service.SubcategoryLimitService;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class SubcategoryLimitController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SubcategoryLimitService subcategoryLimitService;	


	@RequestMapping("/user-budgetplan")
	public String budgetplan(Model model, Principal principal) throws ParseException {
		String name = principal.getName();
		model.addAttribute("subcategorylimit", new SubcategoryLimitForm());
		String date = transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name));
		model.addAttribute("actualMonth", transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));	
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));		
		model.addAttribute("user", userService.findOneWithCategoriesSubcategoriesAndSubcategoryLimit(name, date));
		model.addAttribute("date", transactionService.getActualDateByViewedTransactions(transactionService.findAllTransactionOfUserByActualMonth(name)));
		model.addAttribute("actualMonth", true);
		return "user-budgetplan";
	}
	
	@RequestMapping("/user-budgetplan/{date}")
	public String budgetplan(Model model, Principal principal, @PathVariable String date) throws ParseException {
		String name = principal.getName();
		model.addAttribute("subcategorylimit", new SubcategoryLimitForm());
		model.addAttribute("nextMonthNav", transactionService.getNextMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("actualMonthNav", transactionService.geActualtMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));
		model.addAttribute("prevMonthNav", transactionService.getPrevMonthForNavigationByViewedTransactions(transactionService.findAllTransactionOfUserByDate(name, date)));		
		model.addAttribute("user", userService.findOneWithCategoriesSubcategoriesAndSubcategoryLimit(name, date));
		model.addAttribute("date", date);
		model.addAttribute("actualMonth", transactionService.CheckIfActualMonth(transactionService.getMonthByOtherMonth(date), transactionService.getYearByOtherMonth(date)));
		return "user-budgetplan";
	}
	
	@RequestMapping(value = "/changeSubcategoryLimit", method = RequestMethod.POST)
	public String changeSubcategoryLimit(@ModelAttribute("subcategorylimit") SubcategoryLimitForm subcategoryLimitForm)	throws ParseException {
		subcategoryLimitService.change(subcategoryLimitForm);
		return "redirect:/user-budgetplan";
	}	
	
	@RequestMapping(value = "/addSubcategoryLimit", method = RequestMethod.POST)
	public String addSubcategoryLimit(@ModelAttribute("subcategorylimit") SubcategoryLimitForm subcategoryLimitForm)	throws ParseException {
		subcategoryLimitService.add(subcategoryLimitForm);
		return "redirect:/user-budgetplan";
	}	
	
	@RequestMapping(value = "/changeSubcategoryLimit/{date}", method = RequestMethod.POST)
	public String changeSubcategoryLimit(@ModelAttribute("subcategorylimit") SubcategoryLimitForm subcategoryLimitForm, @PathVariable String date)	throws ParseException {
		subcategoryLimitService.change(subcategoryLimitForm);
		return "redirect:/user-budgetplan/{date}";
	}	
	
	@RequestMapping(value = "/addSubcategoryLimit/{date}", method = RequestMethod.POST)
	public String addSubcategoryLimit(@ModelAttribute("subcategorylimit") SubcategoryLimitForm subcategoryLimitForm, @PathVariable String date)	throws ParseException {
		subcategoryLimitService.add(subcategoryLimitForm);
		return "redirect:/user-budgetplan/{date}";
	}	

}

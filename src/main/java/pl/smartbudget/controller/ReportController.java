package pl.smartbudget.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.forms.ReportForm;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class ReportController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping("/user-reports")
	public String reports(Model model, Principal principal) {
		model.addAttribute("ReportForm", new ReportForm());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		return "user-reports";
	}
	
	@RequestMapping("/report-incomes-by-categories/{date}")
	public String getIncomeReport(Model model, Principal principal, @PathVariable String date) {				
		String name = principal.getName();
		model.addAttribute("date", date);
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("summaryOfAllAccounts", transactionService.getMapOfSubcategoriesWithIncomesByDate(name, date));			
		return "report-incomes-by-categories";
	}
	
	@RequestMapping(value = "/report-incomes-by-categories", method = RequestMethod.POST)
	public String incomeReport(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-incomes-by-categories/"+date+".html";				
	}
	
	@RequestMapping("/report-expenses-by-categories/{date}")
	public String getExpenseReport(Model model, Principal principal, @PathVariable String date) {				
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("date", date);
		model.addAttribute("summaryOfAllAccounts", transactionService.getMapOfSubcategoriesWithExpensesByDate(name, date));			
		return "report-expenses-by-categories";
	}
	
	@RequestMapping(value = "/report-expenses-by-categories", method = RequestMethod.POST)
	public String expenseReport(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-expenses-by-categories/"+date+".html";				
	}
	
}

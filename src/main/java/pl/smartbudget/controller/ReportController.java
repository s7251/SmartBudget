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
	
	@RequestMapping("/report-influences-by-categories/{date}")
	public String getInfluenceReport(Model model, Principal principal, @PathVariable String date) {				
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("summaryOfAllAccounts", transactionService.getMapOfSubcategoriesWithInfluencesByDate(name, date));	
		
		return "report-influences-by-categories";
	}
	
	@RequestMapping(value = "/report-influences-by-categories", method = RequestMethod.POST)
	public String influenceReport(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-influences-by-categories/"+date+".html";				
	}
	
}

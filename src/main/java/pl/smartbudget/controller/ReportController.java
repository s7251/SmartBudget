package pl.smartbudget.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.forms.ReportForm;
import pl.smartbudget.service.ReportService;
import pl.smartbudget.service.TransactionService;
import pl.smartbudget.service.UserService;

@Controller
public class ReportController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;
	
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
		model.addAttribute("summary", transactionService.getSummaryOfIncomesBySubcategoriesAndDate(name, date));	
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
		model.addAttribute("summary", transactionService.getSummaryOfExpensesBySubcategoriesAndDate(name, date));		
		return "report-expenses-by-categories";
	}
	
	@RequestMapping(value = "/report-expenses-by-categories", method = RequestMethod.POST)
	public String expenseReport(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-expenses-by-categories/"+date+".html";				
	}
	
	@RequestMapping("/report-incomes-in-time/{date}")
	public String getIncomeInTimeReport(Model model, Principal principal, @PathVariable String date) {				
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("date", date);
		model.addAttribute("transactionsInTime", transactionService.getMapOfIncomeTransactionInTime(name, date));			
		return "report-incomes-in-time";
	}
	
	@RequestMapping(value = "/report-incomes-in-time", method = RequestMethod.POST)
	public String incomeInTime(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-incomes-in-time/"+date+".html";				
	}
	
	@RequestMapping("/report-expenses-in-time/{date}")
	public String getExpenseInTimeReport(Model model, Principal principal, @PathVariable String date) {				
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("date", date);
		model.addAttribute("transactionsInTime", transactionService.getMapOfExpenseTransactionInTime(name, date));			
		return "report-expenses-in-time";
	}
	
	@RequestMapping(value = "/report-expenses-in-time", method = RequestMethod.POST)
	public String expenseInTime(@ModelAttribute("ReportForm") ReportForm report)	 {
		String date = report.getDate();
    	return "redirect:/report-expenses-in-time/"+date+".html";				
	}
	
	@RequestMapping(value = "/csv-report-incomes-in-time/{date}")
	public void downloadCsvReportIncomesInTime(HttpServletResponse response, Principal principal, @PathVariable String date)
			throws IOException {
		String name = principal.getName();
		reportService.downloadCsvReportIncomesInTime(name, date, response);
	}
	
	@RequestMapping(value = "/csv-report-expenses-in-time/{date}")
	public void downloadCsvReportExpensesInTime(HttpServletResponse response, Principal principal, @PathVariable String date)
			throws IOException {
		String name = principal.getName();
		reportService.downloadCsvReportExpensesInTime(name, date, response);
	}
	
	@RequestMapping(value = "/csv-report-expenses-by-subcategories/{date}")
	public void downloadCsvReportExpensesBySubcategories(HttpServletResponse response, Principal principal, @PathVariable String date)
			throws IOException {
		String name = principal.getName();
		reportService.downloadCsvReportExpensesBySubcategories(name, date, response);
	}
	
	@RequestMapping(value = "/csv-report-incomes-by-subcategories/{date}")
	public void downloadCsvReportIncomesBySubcategories(HttpServletResponse response, Principal principal, @PathVariable String date)
			throws IOException {
		String name = principal.getName();
		reportService.downloadCsvReportIncomesBySubcategories(name, date, response);
	}
}

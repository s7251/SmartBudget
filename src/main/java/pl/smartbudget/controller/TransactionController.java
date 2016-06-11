package pl.smartbudget.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.smartbudget.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/transactions")
	public String transactions(Model model) {
		model.addAttribute("transactions", transactionService.findAll());
		return "transactions";
	}	
	}

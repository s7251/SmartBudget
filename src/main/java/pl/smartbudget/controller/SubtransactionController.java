package pl.smartbudget.controller;
import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import pl.smartbudget.entity.Subtransaction;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.service.SubtransactionService;



@RestController
@RequestMapping(value = "/user-subtransaction", produces = MediaType.APPLICATION_JSON_VALUE)
@EnableWebMvc
public class SubtransactionController {
	@Autowired
	private TransactionRepository transactionRepository;
	
	private static final Logger logger = Logger.getLogger(Subtransaction.class);
	
	@Autowired
	private SubtransactionService subtransactionService;
	

	@RequestMapping(value = "/add", headers="Accept=*/*", method = RequestMethod.POST)
	public @ResponseBody String addSubtransaction(@RequestBody String subtransaction, Principal princpial) {
		System.out.println("Add sutransaction " + subtransaction);
		return "ok";
	}
	
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = "/getData", headers="Accept=*/*", method = RequestMethod.GET)
	public @ResponseBody Transaction getSubtransaction(Principal princpial) {		
		Transaction transaction = new Transaction();
		transaction.setMemo("asd");
		return transaction;
	}
	
		
}

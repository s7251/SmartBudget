package pl.smartbudget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;
import pl.smartbudget.forms.TransactionForm;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.SubcategoryRepository;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.repository.UserRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	
	
	
	public void save(TransactionForm transactionForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();
		Subcategory subcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getSubcategoryId());
		Account accountOfTransaction = accountRepository.findOne(transactionForm.getAccountId());
		transaction.setType(transactionForm.getType());
		transaction.setAmount(transactionForm.getAmount());
		transaction.setName(transactionForm.getName());
		transaction.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(transactionForm.getDate()));			
		transaction.setSubcategory(subcategoryOfAccount);
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);								
		
		transactionRepository.save(transaction);		
	}
	
	public List<Transaction> findInfluenceTransactionsByUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> influenceTransactions = new ArrayList();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				if (transaction.getType() == "influence") {
					influenceTransactions.add(transaction);
				}
			}
			
		}
		return influenceTransactions;
	}
	
	public List<Transaction> findExpenseTransactionsByUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> expenseTransactions = new ArrayList();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				if (transaction.getType() == "influence") {
					expenseTransactions.add(transaction);
				}
			}
			
		}
		return expenseTransactions;
	}

	
	public Map<String, Double> summaryTransactionsOfAccounts(String name) {
		
		Map<String, Double> summaryOfAccounts = new HashMap<String, Double>();
		
		User user = userRepository.findByName(name);
		List<Account> accounts = accountRepository.findByUser(user);
		
		for(Account account : accounts) {
			List<Transaction> transactions =  transactionRepository.findByAccount(account);
			Double transactionsSum = processingTransactions(transactions);
			
			summaryOfAccounts.put(account.getName(), transactionsSum);
		}
		
		return summaryOfAccounts;
	}

	private Double processingTransactions(List<Transaction> transactions) {
		Double transactionsSum = new Double(0); 
		
		for(Transaction transaction : transactions) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		
		return transactionsSum;
	}

	private Double transactionsCalculate(Double transactionsSum, Transaction transaction) {
		
		if(transaction.getType().equals("influence")) {
			transactionsSum += transaction.getAmount();
		} else {
			transactionsSum -= transaction.getAmount();
		}
		
		return transactionsSum;
	}
	
	public List<Transaction> findAllTransactionOfUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
				Date actualDate = new Date();
				SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
				String actualDateFormat = mdyFormat.format(actualDate);
				
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(actualDate);
				int actualMonth = cal.get(Calendar.MONTH);
				int actualYear = cal.get(Calendar.YEAR);
				
//			    int actualMonth = Integer.parseInt(actualDateFormat.substring(5, 7));
//				int actualYear = Integer.parseInt(actualDateFormat.substring(0, 4));
								
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(transaction.getDate());
				int transactionMonth = cal2.get(Calendar.MONTH);
				int transactionYear = cal2.get(Calendar.YEAR);
					
						
//			    int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
//				int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));			    
								
				if(actualMonth==transactionMonth && actualYear==transactionYear){
				allTransactionsByUser.add(transaction);}
			}
		}		
		
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			  public int compare(Transaction t1, Transaction t2) {
			      return t1.getDate().compareTo(t2.getDate());
			  }
			});

		return allTransactionsByUser;
	}

	
}

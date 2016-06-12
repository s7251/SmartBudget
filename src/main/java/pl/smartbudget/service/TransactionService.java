package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;
import pl.smartbudget.repository.AccountRepository;
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
}

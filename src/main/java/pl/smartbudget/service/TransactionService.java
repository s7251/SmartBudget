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
import pl.smartbudget.forms.AlignBalanceForm;
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
	
	public void edit(TransactionForm transactionForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();
		Subcategory subcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getSubcategoryId());
		Account accountOfTransaction = accountRepository.findOne(transactionForm.getAccountId());
		transaction.setId(transactionForm.getId());
		transaction.setType(transactionForm.getType());
		transaction.setAmount(transactionForm.getAmount());
		transaction.setName(transactionForm.getName());
		transaction.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(transactionForm.getDate()));
		transaction.setSubcategory(subcategoryOfAccount);
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(transaction);
	}
	
	
	public void saveAlignBalance(AlignBalanceForm alignBalanceForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();	
		Account accountOfTransaction = accountRepository.findOne(alignBalanceForm.getAccountId());
		transaction.setType(alignBalanceForm.getType());
		transaction.setAmount(alignBalanceForm.getAmount());
		transaction.setName(alignBalanceForm.getName());
		transaction.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(alignBalanceForm.getDate()));	
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(transaction);
	}

	public List<Transaction> findInfluenceTransactionsByUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> influenceTransactions = new ArrayList<Transaction>();

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
		List<Transaction> expenseTransactions = new ArrayList<Transaction>();

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

	public Map<Integer, Account> summaryTransactionsOfAccounts(String name) {
		
		

		Map<Integer, Account> summaryOfAccounts = new HashMap<Integer, Account>();

		User user = userRepository.findByName(name);
		List<Account> accounts = accountRepository.findByUser(user);		
		
		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			Double transactionsSum = processingTransactions(transactions);
			
			account.setSummaryOfAccount(transactionsSum);
			summaryOfAccounts.put(account.getId(), account);
		}

		return summaryOfAccounts;
	}
	
	public Double summaryTransactionsOfAllAccounts(String name){
		Double sum = new Double(0);
		Map<Integer, Account> summaryOfAccounts = summaryTransactionsOfAccounts(name);
		
		for (Map.Entry<Integer, Account> entry : summaryOfAccounts.entrySet()){
			sum+=entry.getValue().getSummaryOfAccount();
		}
		
				
		return sum;		
	}

	private Double processingTransactions(List<Transaction> transactions) {
		Double transactionsSum = new Double(0);

		for (Transaction transaction : transactions) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}

		return transactionsSum;
	}

	private Double transactionsCalculate(Double transactionsSum, Transaction transaction) {

		if (transaction.getType().equals("influence")) {
			transactionsSum += transaction.getAmount();
		} else {
			transactionsSum -= transaction.getAmount();
		}

		return transactionsSum;
	}
	
	public List<Transaction> findAllTransactionOfUserByActualMonth(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
				Date actualDate = new Date();
				//SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
				//String actualDateFormat = mdyFormat.format(actualDate);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(actualDate);
				int actualMonth = cal.get(Calendar.MONTH);
				int actualYear = cal.get(Calendar.YEAR);
			
				//int actualMonth = Integer.parseInt(actualDateFormat.substring(5, 7));
				//int actualYear = Integer.parseInt(actualDateFormat.substring(0, 4));
								
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(transaction.getDate());
				int transactionMonth = cal2.get(Calendar.MONTH);
				int transactionYear = cal2.get(Calendar.YEAR);
									
				//int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
				//int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));			    
								
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
	
	public List<Transaction> findAllTransactionOfUserByDate(String name, String date) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
				//Date actualDate = new Date();
				//SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
				//String actualDateFormat = mdyFormat.format(actualDate);
				
				//Calendar cal = Calendar.getInstance();
				//cal.setTime(actualDate);
				//int actualMonth = cal.get(Calendar.MONTH);
				//int actualYear = cal.get(Calendar.YEAR);
			
				int dateMonth = Integer.parseInt(date.substring(0, 2));
				int dateYear = Integer.parseInt(date.substring(3, 7));
								
				//Calendar cal2 = Calendar.getInstance();
				//cal2.setTime(transaction.getDate());
				//int transactionMonth = cal2.get(Calendar.MONTH);
				//int transactionYear = cal2.get(Calendar.YEAR);
									
				int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
				int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));			    
								
				if(dateMonth==transactionMonth && dateYear==transactionYear){
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
	

	public String getActualDateByViewedTransactions(List<Transaction> transactions) throws ParseException {
		String actualMonthOfTransactions;
		String actualYearOfTransactions ;
		
		actualMonthOfTransactions = transactions.get(0).getDate().toString().substring(5, 7);
		actualYearOfTransactions = transactions.get(0).getDate().toString().substring(0, 4);
		
		return actualMonthOfTransactions+"-"+actualYearOfTransactions;
	}
	
	
	public String geActualtMonthForNavigationByViewedTransactions(List<Transaction> transactions) throws ParseException {
		String actualDateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (!transactions.isEmpty()) {
			String nextMonthOfTransactions = transactions.get(0).getDate().toString();
			Calendar c = Calendar.getInstance();			
			c.setTime(sdf.parse(nextMonthOfTransactions));			
			nextMonthOfTransactions = sdf.format(c.getTime()).substring(0, 7);
			String nextYear = nextMonthOfTransactions.substring(0, 4);
			String nextMonth = nextMonthOfTransactions.substring(5, 7);
			actualDateString = nextMonth +"-"+ nextYear;
		} else {
			Date actualDate = new Date();			
			String actualDateFormat = sdf.format(actualDate);
			String actualMonth = actualDateFormat.substring(5, 7);
			String actualYear = actualDateFormat.substring(0, 4);
			actualDateString = actualMonth +"-"+ actualYear;
		}

		return actualDateString;
	}
	
	public String getNextMonthForNavigationByViewedTransactions(List<Transaction> transactions) throws ParseException {
		String nextDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (!transactions.isEmpty()) {
			String nextMonthOfTransactions = transactions.get(0).getDate().toString();
			Calendar c = Calendar.getInstance();			
			c.setTime(sdf.parse(nextMonthOfTransactions));
			c.add(Calendar.MONTH, 1); // number of days to add
			nextMonthOfTransactions = sdf.format(c.getTime()).substring(0, 7);
			String nextYear = nextMonthOfTransactions.substring(0, 4);
			String nextMonth = nextMonthOfTransactions.substring(5, 7);
			nextDate = nextMonth +"-"+ nextYear;
		} else {
			Date actualDate = new Date();			
			String actualDateFormat = sdf.format(actualDate);
			String actualMonth = actualDateFormat.substring(5, 7);
			String actualYear = actualDateFormat.substring(0, 4);
			nextDate = actualMonth +"-"+ actualYear;
		}

		return nextDate;
	}
	
	public String getPrevMonthForNavigationByViewedTransactions(List<Transaction> transactions) throws ParseException {					
		String prevDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (!transactions.isEmpty()) {
			String nextMonthOfTransactions = transactions.get(0).getDate().toString();
			Calendar c = Calendar.getInstance();			
			c.setTime(sdf.parse(nextMonthOfTransactions));
			c.add(Calendar.MONTH, -1); // number of days to add
			nextMonthOfTransactions = sdf.format(c.getTime()).substring(0, 7);
			String prevYear = nextMonthOfTransactions.substring(0, 4);
			String prevMonth = nextMonthOfTransactions.substring(5, 7);
			prevDate = prevMonth +"-"+ prevYear;
		} else {
			Date actualDate = new Date();			
			String actualDateFormat = sdf.format(actualDate);
			String actualMonth = actualDateFormat.substring(5, 7);
			String actualYear = actualDateFormat.substring(0, 4);
			prevDate = actualMonth +"-"+ actualYear;
		}

		return prevDate;
	}

	public void delete(int id) {
		transactionRepository.delete(id);
		
	}

}

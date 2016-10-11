package pl.smartbudget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;
import pl.smartbudget.forms.AlignBalanceForm;
import pl.smartbudget.forms.TransactionForm;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.SubcategoryRepository;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.repository.UserRepository;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;

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
	
	@Autowired
	private CategoryRepository categoryRepository;

	public void save(TransactionForm transactionForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();
		Subcategory subcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getSubcategoryId());
		Account accountOfTransaction = accountRepository.findOne(transactionForm.getAccountId());
		transaction.setType(transactionForm.getType());
		transaction.setAmount(transactionForm.getAmount());
		transaction.setName(transactionForm.getName());
		transaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(transactionForm.getDate()));
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
		transaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(transactionForm.getDate()));
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
		transaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(alignBalanceForm.getDate()));	
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(transaction);
	}
	
	public Map<String, Double> getMapOfSubcategoriesWithIncomesByDate(String name, String date) {
		User user = userRepository.findByName(name);
		
		int reportMonth = Integer.parseInt(date.substring(0, 2));
		int reportYear= Integer.parseInt(date.substring(3, 7));
		
		Map<String, Double> mapOfSubcategories = new HashMap<String, Double>();

		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			category.setSubcategories(subcategories);

			for (Subcategory subcategory : subcategories) {
				List<Transaction> transactions = transactionRepository.findBySubcategory(subcategory);
				Double amountOfTransactions = new Double(0);
				for (Transaction transaction : transactions) {
									
					int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
					int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));	

					if (transaction.getType().equals("income") && reportMonth==transactionMonth && reportYear==transactionYear) {
						amountOfTransactions += transaction.getAmount();
					}
				}
				if (amountOfTransactions > 0) {
					mapOfSubcategories.put(subcategory.getName(), amountOfTransactions);
				}
			}
			user.setCategories(categories);

		}
		Map<String, Double> sortedMapOfSubcategories = new TreeMap<String, Double>(mapOfSubcategories);
		return sortedMapOfSubcategories;
	}
	
	public Map<String, Double> getMapOfSubcategoriesWithExpensesByDate(String name, String date) {
		User user = userRepository.findByName(name);
		
		int reportMonth = Integer.parseInt(date.substring(0, 2));
		int reportYear= Integer.parseInt(date.substring(3, 7));
		
		Map<String, Double> mapOfSubcategories = new HashMap<String, Double>();

		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			category.setSubcategories(subcategories);

			for (Subcategory subcategory : subcategories) {
				List<Transaction> transactions = transactionRepository.findBySubcategory(subcategory);
				Double amountOfTransactions = new Double(0);
				for (Transaction transaction : transactions) {
									
					int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
					int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));	

					if (transaction.getType().equals("expense") && reportMonth==transactionMonth && reportYear==transactionYear) {
						amountOfTransactions += transaction.getAmount();
					}
				}
				if (amountOfTransactions > 0) {
					mapOfSubcategories.put(subcategory.getName(), amountOfTransactions);
				}
			}
			user.setCategories(categories);

		}
		Map<String, Double> sortedMapOfSubcategories = new TreeMap<String, Double>(mapOfSubcategories);
		return sortedMapOfSubcategories;
	}
	

	public List<Transaction> findIncomeTransactionsByUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> incomeTransactions = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				if (transaction.getType() == "income") {
					incomeTransactions.add(transaction);
				}
			}

		}
		return incomeTransactions;
	}

	public List<Transaction> findExpenseTransactionsByUser(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> expenseTransactions = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				if (transaction.getType() == "income") {
					expenseTransactions.add(transaction);
				}
			}
		}
		return expenseTransactions;
	}
	
	
	/////////////////
	
	
	public ArrayList<Double> forecasting(Map<String, Double> summaryOfAccounts){
		
		ArrayList<Double> listOfPredictedData = new ArrayList<Double>();
		try {			 
			
			// load the summary data
			Attribute Attribute1 = new Attribute("monthsummary_forecast");
			Attribute Attribute2 = new Attribute("Date", "yyyy-MM-dd");

			// Make the feature vector
			FastVector fvWekaAttributes = new FastVector(2);
			fvWekaAttributes.addElement(Attribute1);
			fvWekaAttributes.addElement(Attribute2);

			// Create an empty training set
			Instances summary = new Instances("month_summary", fvWekaAttributes, 10);

			// Set class index
			summary.setClassIndex(0);		

			double[] attValues = new double[summary.numAttributes()];			
			
			for (Map.Entry<String, Double> entry : summaryOfAccounts.entrySet()) {
				attValues = new double[summary.numAttributes()];
			    attValues[0] = entry.getValue();
				attValues[1] = summary.attribute("Date").parseDate(entry.getKey());
				summary.add(new DenseInstance(1.0, attValues));
			}
			
			// new forecaster
			WekaForecaster forecaster = new WekaForecaster();

			// set the targets we want to forecast. This method calls
			// setFieldsToLag() on the lag maker object for us
			forecaster.setFieldsToForecast("monthsummary_forecast");

			// default underlying classifier is SMOreg (SVM) - we'll use
			// gaussian processes for regression instead
			forecaster.setBaseForecaster(new GaussianProcesses());
			//forecaster.setBaseForecaster(new LinearRegression());
			

			forecaster.getTSLagMaker().setTimeStampField("Date"); // date time																
			forecaster.getTSLagMaker().setMinLag(1);
			forecaster.getTSLagMaker().setMaxLag(12); // monthly data

			// add a month of the year indicator field
			forecaster.getTSLagMaker().setAddMonthOfYear(true);

			// add a quarter of the year indicator field
			forecaster.getTSLagMaker().setAddQuarterOfYear(true);

			// build the model
			forecaster.buildForecaster(summary, System.out);

			// prime the forecaster with enough recent historical data
			// to cover up to the maximum lag. In our case, we could just supply
			// the 12 most recent historical instances, as this covers our
			// maximum
			// lag period
			forecaster.primeForecaster(summary);

			// forecast for 12 units (months) beyond the end of the
			// training data
			List<List<NumericPrediction>> forecast = forecaster.forecast(24, System.out);

			// output the predictions. Outer list is over the steps; inner list
			// is over
			// the targets
			for (int i = 0; i < 24; i++) {
				List<NumericPrediction> predsAtStep = forecast.get(i);
				for (int j = 0; j < 1; j++) {
					NumericPrediction predForTarget = predsAtStep.get(j);
					listOfPredictedData.add(predForTarget.predicted());
					System.out.print("" + predForTarget.predicted() + " ");
				}
				System.out.println();
			}

			// we can continue to use the trained forecaster for further
			// forecasting
			// by priming with the most recent historical data (as it becomes
			// available).
			// At some stage it becomes prudent to re-build the model using
			// current
			// historical data.

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return listOfPredictedData;
	}
	
	
	public Map<String, Double> summaryAccountsByMonths(int id, String name) {
		Map<String, Double> summaryOfAccounts = new LinkedHashMap<String, Double>();

		Account account = accountRepository.findById(id);
		List<Transaction> transactions = transactionRepository.findByAccount(account);

		Calendar initDate = Calendar.getInstance();
		initDate.setTime(transactions.get(0).getDate());
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(transactions.get(transactions.size()-1).getDate());
		lastDate.add(Calendar.MONTH, 1);
		Double transactionsSum = new Double(0);		

		for (Transaction transaction : transactions) {
			Calendar nextDate = Calendar.getInstance();
			nextDate.setTime(transaction.getDate());
			String transactionDate = String.valueOf(nextDate.get(Calendar.YEAR)) + "-"	+ String.valueOf(nextDate.get(Calendar.MONTH)+1)  + "-1";			
			String lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)) + "-"	+ String.valueOf(lastDate.get(Calendar.MONTH)+1) + "-1";
			double initDateMonth = initDate.get(Calendar.MONTH)+1;
			double nextDateMonth = nextDate.get(Calendar.MONTH)+1;
			if (initDateMonth == nextDateMonth) {
				transactionsSum = transactionsCalculate(transactionsSum, transaction);
			} else {
				summaryOfAccounts.put(transactionDate, transactionsSum);
				transactionsSum = transactionsCalculate(transactionsSum, transaction);
				initDate.add(Calendar.MONTH, 1);
			}			
			if(transaction.equals(transactions.get(transactions.size()-1))){
				summaryOfAccounts.put(lastTransactionDate, transactionsSum);
			}
		}
		
		Calendar lastDateforForecast = Calendar.getInstance();
		lastDateforForecast=lastDate;
		for(Double forecastEntry : forecasting(summaryOfAccounts)){
			lastDateforForecast.add(Calendar.MONTH, 1);
			String lastDateforForecastEntry = String.valueOf(lastDateforForecast.get(Calendar.YEAR)) + "-"	+ String.valueOf(lastDateforForecast.get(Calendar.MONTH)+1) + "-1";
			summaryOfAccounts.put(lastDateforForecastEntry, (double)Math.round(forecastEntry));			
		}		
		return summaryOfAccounts;
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
	

	private Double processingTransactions(List<Transaction> transactions) {
		Double transactionsSum = new Double(0);

		for (Transaction transaction : transactions) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
	}

	private Double transactionsCalculate(Double transactionsSum, Transaction transaction) {

		if (transaction.getType().equals("income") || transaction.getType().equals("alignment +")) {
			transactionsSum += transaction.getAmount();
		} else {
			transactionsSum -= transaction.getAmount();
		}
		return transactionsSum;
	}
	
	public Double summaryTransactionsOfAllAccounts(String name){
		Double sum = new Double(0);
		Map<Integer, Account> summaryOfAccounts = summaryTransactionsOfAccounts(name);
		
		for (Map.Entry<Integer, Account> entry : summaryOfAccounts.entrySet()){
			sum+=entry.getValue().getSummaryOfAccount();
		}
		
				
		return sum;		
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
	
	public Double getSummaryOfActualMonth(String name) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfActualMonth = new ArrayList<Transaction>();
		transactionOfActualMonth.addAll(findAllTransactionOfUserByActualMonth(name));

		for (Transaction transaction : transactionOfActualMonth) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
	}
	
	public Double getSummaryOfMonthByDate(String name, String date) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserByDate(name, date));

		for (Transaction transaction : transactionOfMonthByDate) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
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
		String actualYearOfTransactions;
		
		actualMonthOfTransactions = transactions.get(0).getDate().toString().substring(5, 7);
		actualYearOfTransactions = transactions.get(0).getDate().toString().substring(0, 4);
		
		return actualMonthOfTransactions+"-"+actualYearOfTransactions;
	}
	
	
	public String geActualtMonthForNavigationByViewedTransactions(List<Transaction> transactions)	throws ParseException {
		String actualDateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (!transactions.isEmpty()) {
			String nextMonthOfTransactions = transactions.get(0).getDate().toString();
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(nextMonthOfTransactions));
			nextMonthOfTransactions = sdf.format(c.getTime()).substring(0, 7);
			String nextYear = nextMonthOfTransactions.substring(0, 4);
			String nextMonth = nextMonthOfTransactions.substring(5, 7);
			actualDateString = nextMonth + "-" + nextYear;
		} else {
			Date actualDate = new Date();
			String actualDateFormat = sdf.format(actualDate);
			String actualMonth = actualDateFormat.substring(5, 7);
			String actualYear = actualDateFormat.substring(0, 4);
			actualDateString = actualMonth + "-" + actualYear;
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

	
	public void delete(int id, String name, String date) {
		List<Account> userAccounts = accountRepository.findByUser(userRepository.findByName(name));
		List<Transaction> userTransactions = new ArrayList<Transaction>();
		
		int viewedMonth = Integer.parseInt(date.substring(0, 2));
		int viewedYear= Integer.parseInt(date.substring(3, 7));
	
		for(Account account : userAccounts){
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for(Transaction transaction: transactions){
				
				int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
				int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));	
				
				if(viewedMonth==transactionMonth && viewedYear==transactionYear){
				userTransactions.add(transaction);}
			}
		}
		
		if (userTransactions.size() != 1) {
			transactionRepository.delete(id);
		}

	}

}

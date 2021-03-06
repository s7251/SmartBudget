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
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;
import pl.smartbudget.forms.AlignBalanceForm;
import pl.smartbudget.forms.InternalTransferForm;
import pl.smartbudget.forms.TransactionForm;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.SubcategoryRepository;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.repository.UserRepository;
import weka.classifiers.evaluation.NumericPrediction;
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
	
	public Transaction findOne(int id) {			
		return transactionRepository.findOne(id);
	}

	public void save(TransactionForm transactionForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();
		Subcategory subcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getSubcategoryId());
		Account accountOfTransaction = accountRepository.findOne(transactionForm.getAccountId());
		transaction.setType(transactionForm.getType());
		transaction.setAmount(transactionForm.getAmount());
		transaction.setMemo(transactionForm.getMemo());
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
		transaction.setMemo(transactionForm.getMemo());
		transaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(transactionForm.getDate()));
		transaction.setSubcategory(subcategoryOfAccount);
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(transaction);
	}
	
	public void split(TransactionForm transactionForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();
		transaction = transactionRepository.findById(transactionForm.getId());
		Transaction firstSplitTransaction = new Transaction();
		Transaction secondarySplitTransaction = new Transaction();
		Subcategory firstSplitSubcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getFirstSplitSubcategoryId());
		Subcategory secondarySplitSubcategoryOfAccount = subcategoryRepository.getOne(transactionForm.getSecondarySplitSubcategoryId());
		Account accountOfTransaction = accountRepository.findOne(transactionForm.getAccountId());	
		firstSplitTransaction.setType(transactionForm.getType());
		firstSplitTransaction.setAmount(transactionForm.getFirstSplitAmount());
		firstSplitTransaction.setMemo(transactionForm.getFirstSplitMemo());
		String day = transactionForm.getDate().substring(8, 10);
		String month = transactionForm.getDate().substring(5, 7);
		String year = transactionForm.getDate().substring(0, 4);		
		firstSplitTransaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(day+"."+month+"."+year));
		firstSplitTransaction.setSubcategory(firstSplitSubcategoryOfAccount);
		firstSplitTransaction.setAccount(accountOfTransaction);
		
		
		secondarySplitTransaction.setType(transactionForm.getType());
		secondarySplitTransaction.setAmount(transactionForm.getSecondarySplitAmount());
		secondarySplitTransaction.setMemo(transactionForm.getSecondarySplitMemo());
		secondarySplitTransaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(day+"."+month+"."+year));
		secondarySplitTransaction.setSubcategory(secondarySplitSubcategoryOfAccount);
		secondarySplitTransaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(firstSplitTransaction);
		transactionRepository.save(secondarySplitTransaction);
		transactionRepository.delete(transaction);
	}
	
	public void saveAlignBalance(AlignBalanceForm alignBalanceForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transaction = new Transaction();	
		Account accountOfTransaction = accountRepository.findOne(alignBalanceForm.getAccountId());
		transaction.setType(alignBalanceForm.getType());
		transaction.setAmount(alignBalanceForm.getAmount());
		transaction.setMemo(alignBalanceForm.getMemo());
		transaction.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(alignBalanceForm.getDate()));	
		transaction.setAccount(accountOfTransaction);
		accountOfTransaction.setUser(user);

		transactionRepository.save(transaction);
	}
	
	public void saveInternalTransfer(InternalTransferForm internalTransferForm, String name) throws ParseException {
		User user = userRepository.findByName(name);
		Transaction transactionFromAccount = new Transaction();		
		Account accountFrom = accountRepository.findOne(internalTransferForm.getFromAccountId());
		transactionFromAccount.setType("internal transfer -");
		transactionFromAccount.setAmount(internalTransferForm.getAmount());
		transactionFromAccount.setMemo(internalTransferForm.getMemo());
		transactionFromAccount.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(internalTransferForm.getDate()));
		transactionFromAccount.setAccount(accountFrom);
		accountFrom.setUser(user);

		transactionRepository.save(transactionFromAccount);
		
		Transaction transactionToAccount = new Transaction();
		Account accountTo = accountRepository.findOne(internalTransferForm.getToAccountId());
		transactionToAccount.setType("internal transfer +");
		transactionToAccount.setAmount(internalTransferForm.getAmount());
		transactionToAccount.setMemo(internalTransferForm.getMemo());
		transactionToAccount.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(internalTransferForm.getDate()));
		transactionToAccount.setAccount(accountTo);
		accountTo.setUser(user);

		transactionRepository.save(transactionToAccount);
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
	
	public double getSummaryOfIncomesBySubcategoriesAndDate(String name, String date){
		double summary=0;
		Map<String, Double> sortedMapOfSubcategoriesWithAmount = new HashMap<String, Double>(getMapOfSubcategoriesWithIncomesByDate(name, date));
		
		for (Double value : sortedMapOfSubcategoriesWithAmount.values()) {
		    summary+=value;
		}
		return summary;
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
	
	public double getSummaryOfExpensesBySubcategoriesAndDate(String name, String date){
		double summary=0;
		Map<String, Double> sortedMapOfSubcategoriesWithAmount = new HashMap<String, Double>(getMapOfSubcategoriesWithExpensesByDate(name, date));
		
		for (Double value : sortedMapOfSubcategoriesWithAmount.values()) {
		    summary+=value;
		}
		return summary;
	}
	
		
	public Map<String, Double> getMapOfIncomeTransactionInTime(String name, String year){
		Map<String, Double> incomeTransactionsInTime = new LinkedHashMap<String, Double>();
		
		User user = userRepository.findByName(name);
		
		for(int month=1; month<13; month++){
	    String textMonth;
		Double sumOfMonth = transactionRepository.getSummaryOfIncomeTransaction(user.getId(), month, Integer.parseInt(year));
		if(sumOfMonth==null){
			sumOfMonth=(double) 0;			
		}
		if (month!=10 && month!=11 && month!=12){
			textMonth = "0" + String.valueOf(month);
		}
		else{
			textMonth = String.valueOf(month);
		}
		incomeTransactionsInTime.put(textMonth+"-"+year, sumOfMonth);
		}
		
			
		return incomeTransactionsInTime;
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
				if (transaction.getType() == "expense") {
					expenseTransactions.add(transaction);
				}
			}
		}
		return expenseTransactions;
	}
	
		
	
	public ArrayList<Double> accountBalanceForecasting(Map<String, Double> summaryOfAccounts){
		System.out.println("Data for prediction:");
		System.out.println(summaryOfAccounts);
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
			//forecaster.setBaseForecaster(new GaussianProcesses());
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
			List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

			// output the predictions. Outer list is over the steps; inner list
			// is over
			// the targets
			for (int i = 0; i < 12; i++) {
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
	
	
	public List<Map<String, Double>> forecastByAccount(int id, String name) {
		Map<String, Double> summaryOfAccounts = new LinkedHashMap<String, Double>();	
		Map<String, Double> summaryOfForecast = new LinkedHashMap<String, Double>();			
		List<Transaction> transactions = null;
if(id>0){
		Account account = accountRepository.findById(id);
		 transactions = transactionRepository.findByAccount(account);}
else{
	transactions = transactionRepository.getTransactionsByUser(userRepository.findByName(name).getId());
}

List<Transaction> found = new ArrayList<Transaction>();
for(Transaction transaction : transactions){
	Calendar nextDate = Calendar.getInstance();
	nextDate.setTime(transaction.getDate());						
	Calendar todayDate = Calendar.getInstance();
	todayDate.setTime(new Date());	
	//todayDate.add(Calendar.MONTH, -1);	
	if(todayDate.get(Calendar.YEAR)<nextDate.get(Calendar.YEAR)){
		found.add(transaction);
	}
	else if(todayDate.get(Calendar.YEAR)==nextDate.get(Calendar.YEAR) && todayDate.get(Calendar.MONTH)<=nextDate.get(Calendar.MONTH)){
		found.add(transaction);
	}
	
}
transactions.removeAll(found);

		Collections.sort(transactions, new Comparator<Transaction>() {
			public int compare(Transaction o1, Transaction o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
if(transactions.isEmpty() == false){
		Calendar initDate = Calendar.getInstance();
		initDate.setTime(transactions.get(0).getDate());
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(transactions.get(transactions.size() - 1).getDate());
		lastDate.add(Calendar.MONTH, 1);	
		Double transactionsSum = new Double(0);		
		
	
	
		for (Transaction transaction : transactions) {			
			Calendar nextDate = Calendar.getInstance();
			nextDate.setTime(transaction.getDate());			
			//if(todayDate.get(Calendar.MONTH)>nextDate.get(Calendar.MONTH) && todayDate.get(Calendar.YEAR)>=nextDate.get(Calendar.YEAR)){
			String lastTransactionDate=null;
			if(lastDate.get(Calendar.MONTH)==0){
			lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)-1) + "-"
					+ "12" + "-1";
			}
			else{
				lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)) + "-"
						+ String.valueOf(lastDate.get(Calendar.MONTH)) + "-1";
			}
			double initDateMonth = initDate.get(Calendar.MONTH) + 1;	
			double nextDateMonth = nextDate.get(Calendar.MONTH) + 1;
			int diffYear = nextDate.get(Calendar.YEAR) - initDate.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + nextDate.get(Calendar.MONTH) - initDate.get(Calendar.MONTH);
			Calendar prevDate = Calendar.getInstance();
			prevDate = nextDate;		
			while (diffMonth > 1) {		
				initDate.add(Calendar.MONTH, 1);
				initDateMonth = initDate.get(Calendar.MONTH) + 1;
				diffMonth--;		
				prevDate.add(Calendar.MONTH, -1);
			}
			String transactionDate=null;
			if(prevDate.get(Calendar.MONTH)==0){
			transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)-1) + "-"
					+ "12" + "-1";
			}
			else{
				transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)) + "-"
						+ String.valueOf(prevDate.get(Calendar.MONTH)) + "-1";
			}
			if (initDateMonth == nextDateMonth) {
				transactionsSum = transactionsCalculate(transactionsSum, transaction);
			} else {
				summaryOfAccounts.put(transactionDate, transactionsSum);
				transactionsSum = transactionsCalculate(transactionsSum, transaction);
				initDate.add(Calendar.MONTH, 1);
			}

			if (transaction.equals(transactions.get(transactions.size() - 1))) {
				summaryOfAccounts.put(lastTransactionDate, transactionsSum);
			}
		//	}
		}
	
		Calendar lastDateforForecast = Calendar.getInstance();
		lastDateforForecast = lastDate;
		ArrayList<Double> listOfPredictedData = new ArrayList<Double>();
		listOfPredictedData = accountBalanceForecasting(summaryOfAccounts);
		String lastDateforForecastEntry=null;
		for (Double forecastEntry : listOfPredictedData) {
			lastDateforForecast.add(Calendar.MONTH, 1);
			
			if(lastDateforForecast.get(Calendar.MONTH)==0){
				lastDateforForecastEntry = String.valueOf(lastDateforForecast.get(Calendar.YEAR)-1) + "-"
						+ "12" + "-1";
			}
			else{				
				lastDateforForecastEntry = String.valueOf(lastDateforForecast.get(Calendar.YEAR)) + "-"
						+ String.valueOf(lastDateforForecast.get(Calendar.MONTH) ) + "-1";}
			
			summaryOfForecast.put(lastDateforForecastEntry, (double) Math.round(forecastEntry));
		}
}
			List<Map<String, Double>> forecastResult = new ArrayList<Map<String, Double>>();
			if(summaryOfAccounts.size()>1){
			forecastResult.add(summaryOfAccounts);
			forecastResult.add(summaryOfForecast);}
			if(summaryOfAccounts.size()==1){				
				Map<String, Double> emptyForecast = new LinkedHashMap<String, Double>();
				emptyForecast.put("add more data for forecast (more than 1 month)  ", 0.0);					
				forecastResult.add(summaryOfAccounts);			
				forecastResult.add(emptyForecast);}
		if(summaryOfAccounts.isEmpty()){
			Map<String, Double> emptyData = new LinkedHashMap<String, Double>();
			emptyData.put("dataset is empty  ", 0.0);
			Map<String, Double> emptyForecast = new LinkedHashMap<String, Double>();
			emptyForecast.put("add more data for forecast  ", 0.0);
			
			Calendar actualDate = Calendar.getInstance();
			summaryOfAccounts.put(actualDate.get(Calendar.YEAR) + "-" + String.valueOf(actualDate.get(Calendar.MONTH) ) + "-1", 0.0);
			forecastResult.add(emptyData);			
			forecastResult.add(emptyForecast);
		}		
		
		return forecastResult;
	}
	
	

	public Map<Integer, Account> getBalanceOfAccount(String name) {		

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

		if (transaction.getType().equals("income") || transaction.getType().equals("alignment +") || transaction.getType().equals("internal transfer +"))   {
			transactionsSum += transaction.getAmount();
		} else {
			transactionsSum -= transaction.getAmount();
		}
		return transactionsSum;
	}
	
	public Double getTotalBalaceOfAccounts(String name){
		Double sum = new Double(0);
		Map<Integer, Account> summaryOfAccounts = getBalanceOfAccount(name);
		
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
	
	public List<Transaction> findAllTransactionOfUserBeforeActualMonthAtBeginning(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
				Date actualDate = new Date();
								
				Calendar cal = Calendar.getInstance();
				cal.setTime(actualDate);
				int actualMonth = cal.get(Calendar.MONTH);
				int actualYear = cal.get(Calendar.YEAR);			
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(transaction.getDate());
				int transactionMonth = cal2.get(Calendar.MONTH);
				int transactionYear = cal2.get(Calendar.YEAR);									
					
				if(actualYear>transactionYear){
				allTransactionsByUser.add(transaction);}
				else if(actualYear==transactionYear && actualMonth>transactionMonth){
					allTransactionsByUser.add(transaction);
				}
			}
		}		
	
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});

		return allTransactionsByUser;
	}
	
	public List<Transaction> findAllTransactionOfUserBeforeActualMonthAtEnd(String name) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
				Date actualDate = new Date();
								
				Calendar cal = Calendar.getInstance();
				cal.setTime(actualDate);
				int actualMonth = cal.get(Calendar.MONTH);
				int actualYear = cal.get(Calendar.YEAR);			
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(transaction.getDate());
				int transactionMonth = cal2.get(Calendar.MONTH);
				int transactionYear = cal2.get(Calendar.YEAR);									
					
				if(actualYear>transactionYear){
				allTransactionsByUser.add(transaction);}
				else if(actualYear==transactionYear && actualMonth>=transactionMonth){
					allTransactionsByUser.add(transaction);
				}
			}
		}		
	
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});

		return allTransactionsByUser;
	}
	
	public Double getBalanceByActualMonthAtBeggining(String name) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfActualMonth = new ArrayList<Transaction>();
		transactionOfActualMonth.addAll(findAllTransactionOfUserBeforeActualMonthAtBeginning(name));

		for (Transaction transaction : transactionOfActualMonth) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
	}
	
	public Double getBalanceByActualMonthAtEnd(String name) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfActualMonth = new ArrayList<Transaction>();
		transactionOfActualMonth.addAll(findAllTransactionOfUserBeforeActualMonthAtEnd(name));

		for (Transaction transaction : transactionOfActualMonth) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
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
	
	public List<Transaction> findAllTransactionOfUserBeforeActualMonthAtBeginning(String name, String date) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
			
				int dateMonth = Integer.parseInt(date.substring(0, 2));
				int dateYear = Integer.parseInt(date.substring(3, 7));
								
							
				int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
				int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));			    
								
				if(dateYear>transactionYear){
					allTransactionsByUser.add(transaction);}
					else if(dateYear==transactionYear && dateMonth>transactionMonth){
						allTransactionsByUser.add(transaction);
					}
			}
		}				
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});

		return allTransactionsByUser;
	}
	
	public Double getBalanceByDateAtBeggining(String name, String date) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserBeforeActualMonthAtBeginning(name, date));

		for (Transaction transaction : transactionOfMonthByDate) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
	}
	
	
	public Double getBalanceByDateAndAccountIdAtBeggining(String name, String date, int accountId) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserBeforeActualMonthAtBeginning(name, date));

		for (Transaction transaction : transactionOfMonthByDate) {
			if(transaction.getAccount().getId()==accountId){
			transactionsSum = transactionsCalculate(transactionsSum, transaction);}
		}
		return transactionsSum;
	}
	
	public List<Transaction> findAllTransactionOfUserBeforeActualMonthAtEnd(String name, String date) {
		User user = userRepository.findByName(name);
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();

		List<Account> accounts = accountRepository.findByUser(user);

		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for (Transaction transaction : transactions) {
				
			
				int dateMonth = Integer.parseInt(date.substring(0, 2));
				int dateYear = Integer.parseInt(date.substring(3, 7));
								
							
				int transactionMonth = Integer.parseInt(transaction.getDate().toString().substring(5, 7));
				int transactionYear = Integer.parseInt(transaction.getDate().toString().substring(0, 4));			    
								
				if(dateYear>transactionYear){
					allTransactionsByUser.add(transaction);}
					else if(dateYear==transactionYear && dateMonth>=transactionMonth){
						allTransactionsByUser.add(transaction);
					}
			}
		}				
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});

		return allTransactionsByUser;
	}
	
	public Double getBalanceByDateAtEnd(String name, String date) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserBeforeActualMonthAtEnd(name, date));

		for (Transaction transaction : transactionOfMonthByDate) {
			transactionsSum = transactionsCalculate(transactionsSum, transaction);
		}
		return transactionsSum;
	}
	
	public Double getBalanceByDateAndAccountIdAtEnd(String name, String date, int accountId) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserBeforeActualMonthAtEnd(name, date));

		for (Transaction transaction : transactionOfMonthByDate) {
			if(transaction.getAccount().getId()==accountId){
			transactionsSum = transactionsCalculate(transactionsSum, transaction);}
		}
		return transactionsSum;
	}
	
	public Double getSummaryOfMonthByDateAndAccountId(String name, String date, int accountId) {
		Double transactionsSum = new Double(0);		
		
		List<Transaction> transactionOfMonthByDate = new ArrayList<Transaction>();
		transactionOfMonthByDate.addAll(findAllTransactionOfUserByDate(name, date));
		
		
		for (Transaction transaction : transactionOfMonthByDate) {
			if(transaction.getAccount().getId()==accountId){
			transactionsSum = transactionsCalculate(transactionsSum, transaction);}
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
	
	public List<Transaction> findAllTransactionOfUserByDateAndAccountId(String date, int accountId) {
		List<Transaction> allTransactionsByUser = new ArrayList<Transaction>();
		Account account = accountRepository.findById(accountId);

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
						
				
		Collections.sort(allTransactionsByUser, new Comparator<Transaction>() {
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});

		return allTransactionsByUser;
	}
	

	public String getActualDateByViewedTransactions(List<Transaction> transactions) throws ParseException {
		Date actualDate = new Date();		
		Calendar cal = Calendar.getInstance();
		cal.setTime(actualDate);
		int actualMonth = cal.get(Calendar.MONTH)+1;
		int actualYear = cal.get(Calendar.YEAR);
		String actualMonthOfTransactions=String.valueOf(actualMonth);
		String actualYearOfTransactions=String.valueOf(actualYear);
		
		if(transactions.size()>0){
		actualMonthOfTransactions = transactions.get(0).getDate().toString().substring(5, 7);
		actualYearOfTransactions = transactions.get(0).getDate().toString().substring(0, 4);
		}
		return actualMonthOfTransactions+"-"+actualYearOfTransactions;
	}
	
	public String getMonthByViewedTransactionsByActualMonth(List<Transaction> transactions) throws ParseException {
		Date actualDate = new Date();		
		Calendar cal = Calendar.getInstance();
		cal.setTime(actualDate);
		int actualMonth = cal.get(Calendar.MONTH)+1;		
		String actualMonthOfTransactions=String.valueOf(actualMonth);
		
		if(transactions.size()>0){
		actualMonthOfTransactions = transactions.get(0).getDate().toString().substring(5, 7);		
		}
		return actualMonthOfTransactions;
	}
	
	public String getMonthByOtherMonth(String date) throws ParseException {		
		return date.toString().substring(0, 2);
	}
	
	public String getYearByOtherMonth(String date) throws ParseException {		
		return date.toString().substring(3, 7);
	}
	
	public String geYearByViewedTransactionsByActualMonth(List<Transaction> transactions) throws ParseException {
		Date actualDate = new Date();		
		Calendar cal = Calendar.getInstance();
		cal.setTime(actualDate);
		int actualYear = cal.get(Calendar.YEAR);
		String actualYearOfTransactions=String.valueOf(actualYear);		
		if(transactions.size()>0){
		actualYearOfTransactions = transactions.get(0).getDate().toString().substring(0, 4);
		}
		return actualYearOfTransactions;
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
	
	public boolean CheckIfActualMonth(String month, String year){
		boolean isActualMonth=false;
		int dateMonth = Integer.parseInt(month);
		int dateYear = Integer.parseInt(year);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date actualDate = new Date();			
		String actualDateFormat = sdf.format(actualDate);
		String actualMonth = actualDateFormat.substring(5, 7);
		String actualYear = actualDateFormat.substring(0, 4);
		int actualM = Integer.parseInt(actualMonth);		
		int actualY = Integer.parseInt(actualYear);
		if(dateMonth==actualM && dateYear==actualY){
			isActualMonth=true;}	
//		System.out.println("miesiac wysw " + dateMonth);
//		System.out.println("rok transakcji " + dateYear);
//		System.out.println("miesiac ak " + actualM);
//		System.out.println("rok ak " + actualY);
		return isActualMonth;
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

	@PreAuthorize("#userNameByTransactionId == authentication.name")
	public void delete(Transaction transaction, String name, String date, @P("userNameByTransactionId") String userNameByTransactionId) {
		List<Account> userAccounts = accountRepository.findByUser(userRepository.findByName(name));
		List<Transaction> userTransactions = new ArrayList<Transaction>();
		
		int viewedMonth = Integer.parseInt(date.substring(0, 2));
		int viewedYear= Integer.parseInt(date.substring(3, 7));
	
		for(Account account : userAccounts){
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			for(Transaction t: transactions){
				
				int transactionMonth = Integer.parseInt(t.getDate().toString().substring(5, 7));
				int transactionYear = Integer.parseInt(t.getDate().toString().substring(0, 4));	
				
				if(viewedMonth==transactionMonth && viewedYear==transactionYear){
				userTransactions.add(t);}
			}
		}
		
		if (userTransactions.size() > 1) {
			transactionRepository.delete(transaction);
		}

	}
	
	public Map<String, Double> getMapOfExpenseTransactionInTime(String name, String year){
		Map<String, Double> expenseTransactionsInTime = new LinkedHashMap<String, Double>();
		
		User user = userRepository.findByName(name);
		
		for(int month=1; month<13; month++){
	    String textMonth;
		Double sumOfMonth = transactionRepository.getSummaryOfExpenseTransaction(user.getId(), month, Integer.parseInt(year));
		if(sumOfMonth==null){
			sumOfMonth=(double) 0;			
		}	
		if (month!=10 && month!=11 && month!=12){
			textMonth = "0" + String.valueOf(month);
		}
		else{
			textMonth = String.valueOf(month);
		}
		expenseTransactionsInTime.put(textMonth+"-"+year, sumOfMonth);
		}
		
		return expenseTransactionsInTime;
	}
	
	
public Double actualMonthSubcategoriesForecasting(Map<String, Double> summaryOfAccounts){
	System.out.println("Data for prediction:");
		System.out.println(summaryOfAccounts);
		
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
			//forecaster.setBaseForecaster(new GaussianProcesses());
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
			List<List<NumericPrediction>> forecast = forecaster.forecast(1, System.out);

			// output the predictions. Outer list is over the steps; inner list
			// is over
			// the targets
			for (int i = 0; i < 1; i++) {
				List<NumericPrediction> predsAtStep = forecast.get(i);
				for (int j = 0; j < 1; j++) {
					NumericPrediction predForTarget = predsAtStep.get(j);
					listOfPredictedData.add(predForTarget.predicted());
					System.out.print("" + predForTarget.predicted() + " ");
				}
				System.out.println();
			}
			System.out.println("");
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
		
		if(listOfPredictedData.size()>0){
		return listOfPredictedData.get(listOfPredictedData.size() - 1);}
		else{
			return 0.0;
		}
		
	}
	

		public Map<String, Double> getSubcategoriesForecastForActualMonth(String name){
			Map<String, Double> subcategoriesForecast = new LinkedHashMap<String, Double>();
			Map<String, LinkedHashMap<String, Double>> dataForForecast = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
			User user = userRepository.findByName(name);
			
			Date actualDate = new Date();
			Calendar actualDateCal = Calendar.getInstance();
			actualDateCal.setTime(actualDate);	
			
			Date beforeActualDate = new Date();
			Calendar beforeActualDateCal = Calendar.getInstance();
			beforeActualDateCal.setTime(beforeActualDate);
			beforeActualDateCal.add(Calendar.MONTH, -23);
			
			List<Category> categories = categoryRepository.findByUser(user);
			for (Category category : categories) {
				List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
				category.setSubcategories(subcategories);
				for (Subcategory subcategory : subcategories) {
					List<Transaction> transactions = transactionRepository.getExpensesByUserAndSubcategory(user.getId(), subcategory.getId());
					List<Transaction> found = new ArrayList<Transaction>();
					for(Transaction transaction : transactions){
						Calendar nextDate = Calendar.getInstance();
						nextDate.setTime(transaction.getDate());						
						
						if(actualDateCal.get(Calendar.YEAR)<nextDate.get(Calendar.YEAR)){
							found.add(transaction);
						}
						else if(actualDateCal.get(Calendar.YEAR)==nextDate.get(Calendar.YEAR) && actualDateCal.get(Calendar.MONTH)<=nextDate.get(Calendar.MONTH)){
							found.add(transaction);
						}
						if(beforeActualDateCal.get(Calendar.YEAR)>nextDate.get(Calendar.YEAR)){
							found.add(transaction);									
							
						}
						else if(beforeActualDateCal.get(Calendar.YEAR)==nextDate.get(Calendar.YEAR) && beforeActualDateCal.get(Calendar.MONTH)>=nextDate.get(Calendar.MONTH)){
							found.add(transaction);//								
						}
					}
					transactions.removeAll(found);
					Collections.sort(transactions, new Comparator<Transaction>() {
						public int compare(Transaction o1, Transaction o2) {
							return o1.getDate().compareTo(o2.getDate());
						}
					});
					LinkedHashMap<String, Double> subcategoryYearSummary = new LinkedHashMap<String, Double>();
					if(transactions.size()!=0){
					Calendar initDate = Calendar.getInstance();
				    initDate.setTime(transactions.get(0).getDate());
					Double transactionsSum = new Double(0);	
					
					for (Transaction transaction : transactions){												
						Calendar nextDate = Calendar.getInstance();
						nextDate.setTime(transaction.getDate());			
						Calendar lastDate = Calendar.getInstance();
						//
						String lastTransactionDate=null;
						if(lastDate.get(Calendar.MONTH)==0){
						lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)-1) + "-"
								+ "12" + "-1";
						}
						else{
							lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)) + "-"
									+ String.valueOf(lastDate.get(Calendar.MONTH)) + "-1";
						}
						//
						lastDate.setTime(transactions.get(transactions.size() - 1).getDate());
						lastDate.add(Calendar.MONTH, 1);
						int diffYear = nextDate.get(Calendar.YEAR) - initDate.get(Calendar.YEAR);
						int diffMonth = diffYear * 12 + nextDate.get(Calendar.MONTH) - initDate.get(Calendar.MONTH);
						Calendar prevDate = Calendar.getInstance();
						prevDate = nextDate;
						
						double initDateMonth = initDate.get(Calendar.MONTH)+1;
						double nextDateMonth = nextDate.get(Calendar.MONTH)+1;
						
						while (diffMonth > 1) {							
							initDate.add(Calendar.MONTH, 1);
							initDateMonth = initDate.get(Calendar.MONTH) + 1;
							diffMonth--;						
							prevDate.add(Calendar.MONTH, -1);
						}
						
						String transactionDate = null;
						
						if(prevDate.get(Calendar.MONTH)==0){
							transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)-1) + "-"
									+ "12" + "-1";
							}
							else{
								transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)) + "-"	+ String.valueOf(prevDate.get(Calendar.MONTH))  + "-1";
							}
						
										
												
						if (initDateMonth == nextDateMonth) {
							transactionsSum = transactionsCalculate(transactionsSum, transaction);
						} else {
							subcategoryYearSummary.put(transactionDate, Math.abs(transactionsSum));
							transactionsSum = new Double(0);	
							transactionsSum = transactionsCalculate(transactionsSum, transaction);
							initDate.add(Calendar.MONTH, 1);								
						}
						if (transaction.equals(transactions.get(transactions.size() - 1))) {
							subcategoryYearSummary.put(lastTransactionDate, Math.abs(transactionsSum));
						}
					}
					dataForForecast.put(subcategory.getName(), subcategoryYearSummary);
				}
					
				}
			}
						
			for (Map.Entry<String, LinkedHashMap<String, Double>> entry : dataForForecast.entrySet()) {
			     LinkedHashMap<String, Double> value = entry.getValue();
			     double subcategoryValue = (double) Math.round(actualMonthSubcategoriesForecasting(value));
			    			    if(subcategoryValue>0){
			    subcategoriesForecast.put(entry.getKey(), subcategoryValue );}			    
			}
			
		return subcategoriesForecast;
		}
		
		public ArrayList<Double> nextMonthsSubcategoriesForecasting(Map<String, Double> summaryOfAccounts){
			System.out.println("Data for prediction:");
				System.out.println(summaryOfAccounts);
				
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
					//forecaster.setBaseForecaster(new GaussianProcesses());
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
					List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

					// output the predictions. Outer list is over the steps; inner list
					// is over
					// the targets
					for (int i = 0; i < 12; i++) {
						List<NumericPrediction> predsAtStep = forecast.get(i);
						for (int j = 0; j < 1; j++) {
							NumericPrediction predForTarget = predsAtStep.get(j);
							listOfPredictedData.add(predForTarget.predicted());
							System.out.print("" + predForTarget.predicted() + " ");
						}
						System.out.println();
					}
					System.out.println("");
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

				public Map<String, LinkedHashMap<String, Double>> getSubcategoriesForecastForNextMonths(String name){				
					Map<String, LinkedHashMap<String, Double>> dataForForecast = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
					Map<String, LinkedHashMap<String, Double>> forecastedData = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
					User user = userRepository.findByName(name);
					
					Date actualDate = new Date();
					Calendar actualDateCal = Calendar.getInstance();
					actualDateCal.setTime(actualDate);
					
					Date beforeActualDate = new Date();
					Calendar beforeActualDateCal = Calendar.getInstance();
					beforeActualDateCal.setTime(beforeActualDate);
					beforeActualDateCal.add(Calendar.MONTH, -23);
				
					List<Category> categories = categoryRepository.findByUser(user);
					for (Category category : categories) {
						List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
						category.setSubcategories(subcategories);
						for (Subcategory subcategory : subcategories) {
							List<Transaction> transactions = transactionRepository.getExpensesByUserAndSubcategory(user.getId(), subcategory.getId());
							List<Transaction> found = new ArrayList<Transaction>();
							for(Transaction transaction : transactions){
								Calendar nextDate = Calendar.getInstance();
								nextDate.setTime(transaction.getDate());						
								
								if(actualDateCal.get(Calendar.YEAR)<nextDate.get(Calendar.YEAR)){
									found.add(transaction);
									System.out.println(actualDateCal.get(Calendar.YEAR)+" czy rowne? " + nextDate.get(Calendar.YEAR));
									System.out.println(transaction.getMemo());
									
								}
								else if(actualDateCal.get(Calendar.YEAR)==nextDate.get(Calendar.YEAR) && actualDateCal.get(Calendar.MONTH)<=nextDate.get(Calendar.MONTH)){
									found.add(transaction);
//									System.out.println(actualDateCal.get(Calendar.YEAR)+" czy rowne? " + nextDate.get(Calendar.YEAR));
//									System.out.println(actualDateCal.get(Calendar.MONTH)+" czy rowne? " + nextDate.get(Calendar.MONTH));
//									System.out.println(transaction.getMemo());
								}
								if(beforeActualDateCal.get(Calendar.YEAR)>nextDate.get(Calendar.YEAR)){
									found.add(transaction);									
									
								}
								else if(beforeActualDateCal.get(Calendar.YEAR)==nextDate.get(Calendar.YEAR) && beforeActualDateCal.get(Calendar.MONTH)>=nextDate.get(Calendar.MONTH)){
									found.add(transaction);//								
								}
							}
							transactions.removeAll(found);
							Collections.sort(transactions, new Comparator<Transaction>() {
								public int compare(Transaction o1, Transaction o2) {
									return o1.getDate().compareTo(o2.getDate());
								}
							});
							LinkedHashMap<String, Double> subcategoryYearSummary = new LinkedHashMap<String, Double>();
							if(transactions.size()!=0){
							Calendar initDate = Calendar.getInstance();
						    initDate.setTime(transactions.get(0).getDate());
							Double transactionsSum = new Double(0);	
							
							for (Transaction transaction : transactions){												
								Calendar nextDate = Calendar.getInstance();
								nextDate.setTime(transaction.getDate());			
								Calendar lastDate = Calendar.getInstance();
								lastDate.setTime(transactions.get(transactions.size() - 1).getDate());
								lastDate.add(Calendar.MONTH, 1);
								//
								String lastTransactionDate=null;
								if(lastDate.get(Calendar.MONTH)==0){
								lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)-1) + "-"
										+ "12" + "-1";
								}
								else{
									lastTransactionDate = String.valueOf(lastDate.get(Calendar.YEAR)) + "-"
											+ String.valueOf(lastDate.get(Calendar.MONTH)) + "-1";
								}
								//
								int diffYear = nextDate.get(Calendar.YEAR) - initDate.get(Calendar.YEAR);
								int diffMonth = diffYear * 12 + nextDate.get(Calendar.MONTH) - initDate.get(Calendar.MONTH);
								Calendar prevDate = Calendar.getInstance();
								prevDate = nextDate;
								
								double initDateMonth = initDate.get(Calendar.MONTH)+1;
								double nextDateMonth = nextDate.get(Calendar.MONTH)+1;
								
								
								while (diffMonth > 1) {							
									initDate.add(Calendar.MONTH, 1);
									initDateMonth = initDate.get(Calendar.MONTH) + 1;
									diffMonth--;						
									prevDate.add(Calendar.MONTH, -1);
								}
								
								String transactionDate = null;
								
								if(prevDate.get(Calendar.MONTH)==0){
									transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)-1) + "-"
											+ "12" + "-1";
									}
									else{
										transactionDate = String.valueOf(prevDate.get(Calendar.YEAR)) + "-"	+ String.valueOf(prevDate.get(Calendar.MONTH))  + "-1";
									}
								
												
														
								if (initDateMonth == nextDateMonth) {
									transactionsSum = transactionsCalculate(transactionsSum, transaction);
								} else {
									subcategoryYearSummary.put(transactionDate, Math.abs(transactionsSum));
									transactionsSum = new Double(0);	
									transactionsSum = transactionsCalculate(transactionsSum, transaction);
									initDate.add(Calendar.MONTH, 1);									
								}
								if (transaction.equals(transactions.get(transactions.size() - 1))) {
									subcategoryYearSummary.put(lastTransactionDate, Math.abs(transactionsSum));
								}
							}
							if(subcategoryYearSummary.size()>1){
							dataForForecast.put(subcategory.getName(), subcategoryYearSummary);}
						}
							
						}
					}
					Calendar lastDateforForecast = Calendar.getInstance();
					lastDateforForecast.setTime(new Date());
					ArrayList<Double> listOfPredictedData = new ArrayList<Double>();
					LinkedHashMap<String, Double> summaryOfForecast = new LinkedHashMap<String, Double>();
					String lastDateforForecastEntry=null;
					for (Map.Entry<String, LinkedHashMap<String, Double>> entry : dataForForecast.entrySet()) {
					     LinkedHashMap<String, Double> value = entry.getValue();					     
					     listOfPredictedData = nextMonthsSubcategoriesForecasting(value);					     
					 	for (Double forecastEntry : listOfPredictedData) {
							lastDateforForecast.add(Calendar.MONTH, 1);
							
							if(lastDateforForecast.get(Calendar.MONTH)==0){
								lastDateforForecastEntry = String.valueOf(lastDateforForecast.get(Calendar.YEAR)-1) + "-"
										+ "12" + "-1";
							}
							else{				
								lastDateforForecastEntry = String.valueOf(lastDateforForecast.get(Calendar.YEAR)) + "-"
										+ String.valueOf(lastDateforForecast.get(Calendar.MONTH) ) + "-1";}
							
							summaryOfForecast.put(lastDateforForecastEntry, (double) Math.round(forecastEntry));
						}
					     
					     
					     forecastedData.put(entry.getKey(), summaryOfForecast);
					     summaryOfForecast = new LinkedHashMap<String, Double>();
					     listOfPredictedData = null;
					     lastDateforForecastEntry = null;
					     lastDateforForecast.setTime(new Date());
					}
					
				return forecastedData;
				}
		


			

}

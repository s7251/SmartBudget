package pl.smartbudget.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
	
	@Autowired
	private TransactionService transactionService;
	
	public void downloadCsvReportIncomesInTime(String name, String date, HttpServletResponse response) throws IOException  {
		
	Map<String, Double> incomeTransactionsInTime = new LinkedHashMap<String, Double>();
	incomeTransactionsInTime = transactionService.getMapOfIncomeTransactionInTime(name, date);

	response.setContentType("text/csv");
	String reportName = "CSV_Report_Incomes_In_Time_"+date+".csv";
	response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		ArrayList<String> rows = new ArrayList<String>();

	for (Map.Entry<String, Double> entry : incomeTransactionsInTime.entrySet()) {
		String key = entry.getKey();
		Double value = entry.getValue();
		rows.add(key + "," + value);
		rows.add("\n");
	}

	Iterator<String> iter = rows.iterator();
	while (iter.hasNext()) {
		String outputString = (String) iter.next();
		response.getOutputStream().print(outputString);
	}

	response.getOutputStream().flush();
}
	
	public void downloadCsvReportExpensesInTime(String name, String date, HttpServletResponse response) throws IOException  {
		
	Map<String, Double> expenseTransactionsInTime = new LinkedHashMap<String, Double>();
	expenseTransactionsInTime = transactionService.getMapOfExpenseTransactionInTime(name, date);

	response.setContentType("text/csv");
	String reportName = "CSV_Report_Expenses_In_Time_"+date+".csv";
	response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		ArrayList<String> rows = new ArrayList<String>();

	for (Map.Entry<String, Double> entry : expenseTransactionsInTime.entrySet()) {
		String key = entry.getKey();
		Double value = entry.getValue();
		rows.add(key + "," + value);
		rows.add("\n");
	}

	Iterator<String> iter = rows.iterator();
	while (iter.hasNext()) {
		String outputString = (String) iter.next();
		response.getOutputStream().print(outputString);
	}

	response.getOutputStream().flush();
}
	
	public void downloadCsvReportExpensesBySubcategories(String name, String date, HttpServletResponse response) throws IOException  {
		
	Map<String, Double> expenseTransactionsByCategories = new LinkedHashMap<String, Double>();
	expenseTransactionsByCategories = transactionService.getMapOfSubcategoriesWithExpensesByDate(name, date);

	response.setContentType("text/csv");
	String reportName = "CSV_Report_Expenses_By_Subcategories_"+date+".csv";
	response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		ArrayList<String> rows = new ArrayList<String>();

	for (Map.Entry<String, Double> entry : expenseTransactionsByCategories.entrySet()) {
		String key = entry.getKey();
		Double value = entry.getValue();
		rows.add(key + "," + value);
		rows.add("\n");
	}

	Iterator<String> iter = rows.iterator();
	while (iter.hasNext()) {
		String outputString = (String) iter.next();
		response.getOutputStream().print(outputString);
	}

	response.getOutputStream().flush();
}
	
	public void downloadCsvReportIncomesBySubcategories(String name, String date, HttpServletResponse response) throws IOException  {
		
	Map<String, Double> incomesTransactionsByCategories = new LinkedHashMap<String, Double>();
	incomesTransactionsByCategories = transactionService.getMapOfSubcategoriesWithIncomesByDate(name, date);

	response.setContentType("text/csv");
	String reportName = "CSV_Report_Incomes_By_Subcategories_"+date+".csv";
	response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		ArrayList<String> rows = new ArrayList<String>();

	for (Map.Entry<String, Double> entry : incomesTransactionsByCategories.entrySet()) {
		String key = entry.getKey();
		Double value = entry.getValue();
		rows.add(key + "," + value);
		rows.add("\n");
	}

	Iterator<String> iter = rows.iterator();
	while (iter.hasNext()) {
		String outputString = (String) iter.next();
		response.getOutputStream().print(outputString);
	}

	response.getOutputStream().flush();
}
	
}
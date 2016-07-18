package pl.smartbudget.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Spy
	@InjectMocks
	private TransactionService transactionService;
	
	@Test
	public void should_sum_if_accounts_dont_have_any_transactions() {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		setUpMocks(transactions);
		
		Map<String, Double> accountsValues = transactionService.summaryTransactionsOfAccounts("jan");
		
		assertEquals(accountsValues.size(), 1);
		assertEquals(accountsValues.get("Wallet"), new Double(0));
	}

	@Test
	public void should_sum_account_transactions() {
		List<Transaction> transactions = Arrays.asList(new Transaction(150, "influence"), new Transaction(50, "expense"));
		setUpMocks(transactions);
		
		Map<String, Double> accountsValues = transactionService.summaryTransactionsOfAccounts("jan");
		
		assertEquals(accountsValues.size(), 1);
		assertEquals(accountsValues.get("Wallet"), new Double(100));
	}
	
	private void setUpMocks(List<Transaction> transactions) {
		Account account = new Account("Wallet");
		
		when(accountRepository.findByUser(null)).thenReturn(Arrays.asList(account));
		when(transactionRepository.findByAccount(account)).thenReturn(transactions);
	}
	
}

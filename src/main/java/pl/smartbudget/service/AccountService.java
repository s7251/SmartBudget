package pl.smartbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.User;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.UserRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Account> findAll() {
		return accountRepository.findAll();

	}

	public void save(Account account, String name) {
		User user = userRepository.findByName(name);
		//Account account = new Account();		
		account.setName(account.getName());	
		account.setUser(user);
		accountRepository.save(account);		
	}

	public void delete(int id) {
		accountRepository.delete(id);	
		
	}

}

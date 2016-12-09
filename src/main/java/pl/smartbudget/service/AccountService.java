package pl.smartbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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
		account.setName(account.getName());
		account.setUser(user);
		accountRepository.save(account);
	}

	@PreAuthorize("#account.user.name == authentication.name")
	public void delete(@P("account") Account account, String name) {
		User user = userRepository.findByName(name);
		List<Account> accounts = accountRepository.findByUser(user);
		//if (accounts.size() > 1) {
		accountRepository.delete(account);
		//}
	}

	public String findById(int id) {
		Account account = accountRepository.findById(id);
		return account.getName();
	}

	public Account findOne(int id) {		
		return accountRepository.findOne(id);
	}

}

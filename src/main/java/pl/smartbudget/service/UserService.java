package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Role;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.SubcategoryLimit;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;
import pl.smartbudget.repository.AccountRepository;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.RoleRepository;
import pl.smartbudget.repository.SubcategoryLimitRepository;
import pl.smartbudget.repository.SubcategoryRepository;
import pl.smartbudget.repository.TransactionRepository;
import pl.smartbudget.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	
	@Autowired
	private SubcategoryLimitRepository subcategoryLimitRepository;
	

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}
	
	public User findOneByName(String name) {
		return userRepository.findByName(name);
	}

	public void save(User user) {
		user.setEnabled(true);
		user.setPassword(user.getPassword());

		List<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(userRoles);

		userRepository.save(user);

	}

	@Transactional
	public User findOneWithAccounts(int id) {
		User user = findOne(id);
		List<Account> accounts = accountRepository.findByUser(user);
		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			account.setTransactions(transactions);
		}
		user.setAccounts(accounts);
		return user;
	}

	public User findOneWithAccounts(String name) {
		User user = userRepository.findByName(name);
		return findOneWithAccounts(user.getId());
	}

	private User findOneWithCategories(int id) {
		User user = findOne(id);
		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			for(Subcategory subcategory : subcategories){
				List<SubcategoryLimit> subcategoryLimit = subcategoryLimitRepository.findBySubcategory(subcategory);
				subcategory.setSubcategoryLimits(subcategoryLimit);
			}
			category.setSubcategories(subcategories);
		}
		user.setCategories(categories);
		return user;
	}

	public User findOneWithCategories(String name) {
		User user = userRepository.findByName(name);
		return findOneWithCategories(user.getId());
	}

	

}

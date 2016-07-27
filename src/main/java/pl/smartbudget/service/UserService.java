package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public User findOneWithAccounts(String name) {
		User user = userRepository.findByName(name);
		return findOneWithAccountsAndTransactions(user.getId());
	}

	@Transactional
	public User findOneWithAccounts(int id) {
		User user = findOne(id);
		List<Account> accounts = accountRepository.findByUser(user);
		user.setAccounts(accounts);
		return user;
	}
	
	public User findOneWithAccountsAndTransactions(String name) {
		User user = userRepository.findByName(name);
		return findOneWithAccountsAndTransactions(user.getId());
	}

	@Transactional
	public User findOneWithAccountsAndTransactions(int id) {
		User user = findOne(id);
		List<Account> accounts = accountRepository.findByUser(user);
		for (Account account : accounts) {
			List<Transaction> transactions = transactionRepository.findByAccount(account);
			account.setTransactions(transactions);
		}
		user.setAccounts(accounts);
		return user;
	}

	public User findOneWithCategoriesSubcategoriesAndSubcategoryLimit(String name) {
		User user = userRepository.findByName(name);
		return findOneWithCategoriesSubcategoriesAndSubcategoryLimit(user.getId());
	}

	@Transactional
	public User findOneWithCategoriesSubcategoriesAndSubcategoryLimit(int id) {
		User user = findOne(id);
		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			category.setSubcategories(subcategories);
			for (Subcategory subcategory : subcategories) {
				List<SubcategoryLimit> subcategoryLimit = subcategoryLimitRepository.findBySubcategory(subcategory);
				subcategory.setSubcategoryLimits(subcategoryLimit);
			}
		}
		user.setCategories(categories);
		return user;
	}

	@Transactional
	public Map<Integer, String> getSubcategoriesMapOfUser(String name) {

		Map<Integer, String> subcategoriesMap = new HashMap<Integer, String>();
		List<Category> categories = findOneWithCategoriesSubcategoriesAndSubcategoryLimit(name).getCategories();

		for (Category category : categories) {
			List<Subcategory> subcategories = category.getSubcategories();
			for (Subcategory subcategory : subcategories)
				subcategoriesMap.put(subcategory.getId(), subcategory.getName());
		}
		return subcategoriesMap;
	}

	@Transactional
	public Map<Integer, String> getAccountsMapOfUser(String name) {

		Map<Integer, String> accountsMap = new HashMap<Integer, String>();
		List<Account> accounts = findOneWithAccountsAndTransactions(name).getAccounts();

		for (Account account : accounts) {
			accountsMap.put(account.getId(), account.getName());
		}
		return accountsMap;
	}

	public void delete(int id) {
		userRepository.delete(id);
		
	}

}

package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		user.setPassword(bcrypt.encode(user.getPassword()));

		List<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(userRoles);
		
			
		Account account = new Account();
		account.setUser(user);
		account.setName("SampleAccount1");
		accountRepository.save(account);
		
		Category categorySample = new Category();
		categorySample.setUser(user);
		categorySample.setName("SampleCategory1");
		categoryRepository.save(categorySample);
		
		Subcategory subcategorySample = new Subcategory();
		subcategorySample.setCategory(categorySample);
		subcategorySample.setName("SampleSubcategory1");
		subcategoryRepository.save(subcategorySample);
		
		SubcategoryLimit subcategoryLimitSample = new SubcategoryLimit();
		subcategoryLimitSample.setSubcategory(subcategorySample);
		subcategoryLimitSample.setAmount(200.00);
		subcategoryLimitSample.setDate(new Date());		
		subcategoryLimitRepository.save(subcategoryLimitSample);
		
		Transaction transaction = new Transaction();
		transaction.setSubcategory(subcategorySample);
		transaction.setAccount(account);
		transaction.setType("income");
		transaction.setMemo("your first income!");
		transaction.setDate(new Date());
		transaction.setAmount(999.00);
		transactionRepository.save(transaction);
		
		Transaction transaction2 = new Transaction();
		transaction2.setSubcategory(subcategorySample);
		transaction2.setAccount(account);
		transaction2.setType("expense");
		transaction2.setMemo("your first expense!");
		transaction2.setDate(new Date());
		transaction2.setAmount(499.00);
		transactionRepository.save(transaction2);

		userRepository.save(user);
	}
	
	
	public void changePassword(User user) {		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		user.setPassword(bcrypt.encode(user.getPassword()));

		List<Role> userRoles = new ArrayList<Role>();
		if(user.getName().equals("admin")){
			userRoles.add(roleRepository.findByName("ROLE_ADMIN"));		
		}	
			userRoles.add(roleRepository.findByName("ROLE_USER"));
			user.setRoles(userRoles);	
				
		userRepository.save(user);
	}
	
	public void changeEmail(User user) {	
		user.setEmail(user.getEmail());
		List<Role> userRoles = new ArrayList<Role>();
		if(user.getName().equals("admin")){
			userRoles.add(roleRepository.findByName("ROLE_ADMIN"));				
		}	
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

	public User findOneWithCategoriesAndSubcategories(String name) {
		User user = userRepository.findByName(name);
		return findOneWithCategoriesAndSubcategories(user.getId());
	}

	@Transactional
	public User findOneWithCategoriesAndSubcategories(int id) {
		User user = findOne(id);
		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			category.setSubcategories(subcategories);			
		}
		user.setCategories(categories);
		return user;
	}
	
	public User findOneWithCategoriesSubcategoriesAndSubcategoryLimit(String name, String date) {
		User user = userRepository.findByName(name);
		return findOneWithCategoriesSubcategoriesAndSubcategoryLimit(user.getId(), date);
	}

	@Transactional
	public User findOneWithCategoriesSubcategoriesAndSubcategoryLimit(int id, String date) {
		User user = findOne(id);
		List<Category> categories = categoryRepository.findByUser(user);
		for (Category category : categories) {
			List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
			category.setSubcategories(subcategories);
			for (Subcategory subcategory : subcategories) {				
				int month = Integer.parseInt(date.substring(0, 2));
				int year = Integer.parseInt(date.substring(3, 7));				
				List<SubcategoryLimit> subcategoryLimits = subcategoryLimitRepository.findBySubcategoryAndDate(subcategory.getId(), month, year );
				subcategory.setSubcategoryLimits(subcategoryLimits);
				for(SubcategoryLimit subcategoryLimit : subcategoryLimits){
				
					subcategoryLimit.setSummaryOfSpentMoney(transactionRepository.getSummaryOfSpentMoney(subcategory.getId(), month, year ));					
					
				}								
			}
		}
		user.setCategories(categories);
		return user;
	}

	@Transactional
	public Map<Integer, String> getSubcategoriesMapOfUser(String name) {

		Map<Integer, String> subcategoriesMap = new HashMap<Integer, String>();
		List<Category> categories = findOneWithCategoriesAndSubcategories(name).getCategories();

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

	@PreAuthorize("#userName == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(int id, @P("userName") String userName) {
		userRepository.delete(id);
		
	}
	
	public String findUserNameByTransactionId(Transaction transaction){
		String userName = userRepository.findUserNameByTransactionId(transaction);
		
		return userName;		
	}

	public String findUserNameByCategoryId(Category category) {
		String userName = userRepository.findUserNameByCategoryId(category);
		return userName;
	}

	public String findUserNameBySubcategoryId(Subcategory subcategory) {
		String userName = userRepository.findUserNameBySubcategoryId(subcategory);
		return userName;
	}

	public User findOneByEmail(String email) {		
		return userRepository.findByEmail(email);
	}

	}

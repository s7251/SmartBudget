package pl.smartbudget.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import pl.smartbudget.forms.AddUserByAdminForm;
import pl.smartbudget.forms.ChangeRolesForm;
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

	public User findOne(String name) {
		return userRepository.findByName(name);
	}
	
	public User findOne(int id) {
		return userRepository.findOne(id);
	}
	
	public User findOneWithAccounts(String name) {
		User user = userRepository.findByName(name);		
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
	
	public User findOneByEmail(String email) {		
		return userRepository.findByEmail(email);
	}
	
	public User findOneWithRoles(String name) {		
		User user = userRepository.findByName(name);
		user.setPermissions("User");		
			user.setRoles(roleRepository.getRolesByUser(user.getName()));			
			for(Role role : roleRepository.getRolesByUser(user.getName())){
				if(role.getName().equals("ROLE_ADMIN")){					
					user.setPermissions("Administrator");
				}				
			}
		return user;
	}
	
	public List<User> findAllWithRoles() {		
		List<User> users = findAll();
		for(User user: users){
			user.setPermissions("User");	
			user.setRoles(roleRepository.getRolesByUser(user.getName()));	
			for(Role role : roleRepository.getRolesByUser(user.getName())){
				if(role.getName().equals("ROLE_ADMIN")){					
					user.setPermissions("Administrator");
				}				
			}
		}		
		return users;
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
		List<Account> accounts = findOneWithAccounts(name).getAccounts();

		for (Account account : accounts) {
			accountsMap.put(account.getId(), account.getName());
		}
		return accountsMap;
	}

	@PreAuthorize("#userName == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(int id, @P("userName") String userName) {
		userRepository.delete(id);		
	}
	
	public void ResetPassword(String name, String email){		
		User user = userRepository.findByName(name);
	    SecureRandom random = new SecureRandom();
	    String newPassword = new BigInteger(100, random).toString(32);
	    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();		
			  
		if(user.getEmail().equals(email)){
		user.setPassword(bcrypt.encode(newPassword));
		List<Role> userRoles = new ArrayList<Role>();
		if(user.getName().equals("admin")){
			userRoles.add(roleRepository.findByName("ROLE_ADMIN"));		
		}	
			userRoles.add(roleRepository.findByName("ROLE_USER"));
			user.setRoles(userRoles);	
			user.setEnabled(true);
			user.setName(name);
			user.setEmail(email);		
		
		final String username = "smartbudget.no.reply@gmail.com";
		final String password = "PoliBuda2016#@123%";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("smartbudget.no.reply@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("SmartBudget password reset notification");
			message.setText("Hello "+name+","
				+ "\n\nWe received a request to reset the password for your SmartBudget account. your new password is: " + newPassword +"\n\nPlease change your password after next login.");

			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	}



	public void userSetRoles(User user, AddUserByAdminForm form) {
		List<Role> userRoles = new ArrayList<Role>();
		if(form.getPermissions().equals("administrator")){			
			userRoles.add(roleRepository.findByName("ROLE_USER"));
			userRoles.add(roleRepository.findByName("ROLE_ADMIN"));
			user.setRoles(userRoles);
		}
		else if(form.getPermissions().equals("user")){
			userRoles.add(roleRepository.findByName("ROLE_USER"));
			user.setRoles(userRoles);
		}		
	}

	public void changeRoles(ChangeRolesForm form) {
		List<Role> userRoles = new ArrayList<Role>();
		User user = userRepository.findById(form.getId());
		user.setRoles(userRoles);
		if(form.getPermissions().equals("admin")){
			userRoles.add(roleRepository.findByName("ROLE_ADMIN"));		
			userRoles.add(roleRepository.findByName("ROLE_USER"));
		}	
		else{
			userRoles.add(roleRepository.findByName("ROLE_USER"));
		}
			user.setRoles(userRoles);					
		userRepository.save(user);		
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
	
	public void saveUserByAdmin(User user, AddUserByAdminForm form) {		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		user.setPassword(bcrypt.encode(form.getPass()));
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setEnabled(form.isEnabled());
		
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
	}

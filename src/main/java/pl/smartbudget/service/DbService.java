package pl.smartbudget.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
public class DbService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	@Autowired
	private SubcategoryLimitRepository subcategoryLimitRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	@PostConstruct
	public void initDb() {
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleUser);
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);
		
		// Admin
		
		User userAdmin = new User();
		userAdmin.setName("admin");
		userAdmin.setPassword("admin");
		userAdmin.setEmail("admin@admin.pl");
		userAdmin.setEnabled(true);
		List<Role> adminRoles = new ArrayList<Role>();
		adminRoles.add(roleAdmin);
		adminRoles.add(roleUser);
		userAdmin.setRoles(adminRoles);
		userRepository.save(userAdmin);
		
		Account account1 = new Account();
		account1.setUser(userAdmin);
		account1.setName("Bank account");
		accountRepository.save(account1);
		
		Account account2 = new Account();
		account2.setUser(userAdmin);
		account2.setName("Wallet");
		accountRepository.save(account2);
		
		Category categorySample1 = new Category();
		categorySample1.setUser(userAdmin);
		categorySample1.setName("Work");
		categoryRepository.save(categorySample1);
		
		Category categorySample2 = new Category();
		categorySample2.setUser(userAdmin);
		categorySample2.setName("Food");
		categoryRepository.save(categorySample2);
		
		Category categorySample3 = new Category();
		categorySample3.setUser(userAdmin);
		categorySample3.setName("Health and beauty");
		categoryRepository.save(categorySample3);
		
		Subcategory subcategorySample1 = new Subcategory();
		subcategorySample1.setCategory(categorySample1);
		subcategorySample1.setName("Salary");
		subcategoryRepository.save(subcategorySample1);
		
		Subcategory subcategorySample2 = new Subcategory();
		subcategorySample2.setCategory(categorySample1);
		subcategorySample2.setName("Bonus");
		subcategoryRepository.save(subcategorySample2);
		
		Subcategory subcategorySample3 = new Subcategory();
		subcategorySample3.setCategory(categorySample2);
		subcategorySample3.setName("Supermarket");
		subcategoryRepository.save(subcategorySample3);
		
		Subcategory subcategorySample4 = new Subcategory();
		subcategorySample4.setCategory(categorySample2);
		subcategorySample4.setName("Restaurant");
		subcategoryRepository.save(subcategorySample4);
		
		Subcategory subcategorySample5 = new Subcategory();
		subcategorySample5.setCategory(categorySample3);
		subcategorySample5.setName("Barber");
		subcategoryRepository.save(subcategorySample5);
		
		SubcategoryLimit subcategoryLimitSample1 = new SubcategoryLimit();
		subcategoryLimitSample1.setSubcategory(subcategorySample1);
		subcategoryLimitSample1.setAmount(200.00);
		subcategoryLimitSample1.setDate(new Date());
		subcategoryLimitSample1.setName("Limit on Subcategory1");
		subcategoryLimitRepository.save(subcategoryLimitSample1);
		
		Transaction transaction1 = new Transaction();
		transaction1.setSubcategory(subcategorySample1);
		transaction1.setAccount(account1);
		transaction1.setType("influence");
		transaction1.setName("monthly salary");
		transaction1.setDate(new Date());
		transaction1.setAmount(3600.00);
		transactionRepository.save(transaction1);
		
		Transaction transaction2 = new Transaction();
		transaction2.setSubcategory(subcategorySample3);
		transaction2.setAccount(account2);
		transaction2.setType("expense");
		transaction2.setName("shopping in supermarket");
		transaction2.setDate(new Date());
		transaction2.setAmount(89.00);
		transactionRepository.save(transaction2);
		
		Transaction transaction3 = new Transaction();
		transaction3.setSubcategory(subcategorySample4);
		transaction3.setAccount(account1);
		transaction3.setType("expense");
		transaction3.setName("dinner in restaurant");
		transaction3.setDate(new Date());
		transaction3.setAmount(101.00);
		transactionRepository.save(transaction3);
		
		Transaction transaction4 = new Transaction();
		transaction4.setSubcategory(subcategorySample5);
		transaction4.setAccount(account1);
		transaction4.setType("expense");
		transaction4.setName("haircut");
		transaction4.setDate(new Date());
		transaction4.setAmount(25.00);
		transactionRepository.save(transaction4);
		
		// User
		
		User user1 = new User();
		user1.setName("user");
		user1.setPassword("user");
		user1.setEmail("user@user.pl");	
		user1.setEnabled(true);
		List<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleUser);
		user1.setRoles(userRoles);
		userRepository.save(user1);
		
		Account account3 = new Account();
		account3.setUser(user1);
		account3.setName("Bank account");
		accountRepository.save(account3);
		
		Category categorySample10 = new Category();
		categorySample10.setUser(user1);
		categorySample10.setName("SampleCategory1");
		categoryRepository.save(categorySample10);
		
		Subcategory subcategorySample10 = new Subcategory();
		subcategorySample10.setCategory(categorySample10);
		subcategorySample10.setName("SampleSubcategory3");
		subcategoryRepository.save(subcategorySample10);
		
		SubcategoryLimit subcategoryLimitSample10 = new SubcategoryLimit();
		subcategoryLimitSample10.setSubcategory(subcategorySample10);
		subcategoryLimitSample10.setAmount(200.00);
		subcategoryLimitSample10.setDate(new Date());
		subcategoryLimitSample10.setName("Limit on Subcategory3");
		subcategoryLimitRepository.save(subcategoryLimitSample10);
		
		Transaction transaction10 = new Transaction();
		transaction10.setSubcategory(subcategorySample10);
		transaction10.setAccount(account3);
		transaction10.setType("influence");
		transaction10.setName("tax refund");
		transaction10.setDate(new Date());
		transaction10.setAmount(999.00);
		transactionRepository.save(transaction10);
	
	}
}

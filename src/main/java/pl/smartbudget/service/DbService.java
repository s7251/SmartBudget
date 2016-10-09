package pl.smartbudget.service;

import java.text.ParseException;
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
		
		Category categorySample4 = new Category();
		categorySample4.setUser(userAdmin);
		categorySample4.setName("Bank");
		categoryRepository.save(categorySample4);
		
		Subcategory subcategorySample1 = new Subcategory();
		subcategorySample1.setCategory(categorySample1);
		subcategorySample1.setName("Salary");
		subcategoryRepository.save(subcategorySample1);
		
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
		
		Subcategory subcategorySample6 = new Subcategory();
		subcategorySample6.setCategory(categorySample4);
		subcategorySample6.setName("Interest on bank deposits");
		subcategoryRepository.save(subcategorySample6);
		
		SubcategoryLimit subcategoryLimitSample1 = new SubcategoryLimit();
		subcategoryLimitSample1.setSubcategory(subcategorySample3);
		subcategoryLimitSample1.setAmount(139.00);
		subcategoryLimitSample1.setDate(new Date());
		subcategoryLimitRepository.save(subcategoryLimitSample1);		
		
		SubcategoryLimit subcategoryLimitSample2 = new SubcategoryLimit();
		subcategoryLimitSample2.setSubcategory(subcategorySample4);
		subcategoryLimitSample2.setAmount(201.00);
		subcategoryLimitSample2.setDate(new Date());
		subcategoryLimitRepository.save(subcategoryLimitSample2);
		
		Transaction transaction7 = new Transaction();
		transaction7.setSubcategory(subcategorySample1);
		transaction7.setAccount(account1);
		transaction7.setType("income");
		transaction7.setName("monthly salary");
		try {
			transaction7.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.07.2016"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction7.setAmount(4000.00);
		transactionRepository.save(transaction7);
		
		Transaction transaction8 = new Transaction();
		transaction8.setSubcategory(subcategorySample1);
		transaction8.setAccount(account1);
		transaction8.setType("income");
		transaction8.setName("monthly salary");
		try {
			transaction8.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.08.2016"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction8.setAmount(5000.00);
		transactionRepository.save(transaction8);
		
		Transaction transaction9 = new Transaction();
		transaction9.setSubcategory(subcategorySample1);
		transaction9.setAccount(account1);
		transaction9.setType("income");
		transaction9.setName("monthly salary");
		try {
			transaction9.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.08.2016"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction9.setAmount(1000.00);
		transactionRepository.save(transaction9);
		
		Transaction transaction10 = new Transaction();
		transaction10.setSubcategory(subcategorySample1);
		transaction10.setAccount(account1);
		transaction10.setType("income");
		transaction10.setName("monthly salary");
		try {
			transaction10.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2016"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transaction10.setAmount(1000.00);
		transactionRepository.save(transaction10);
		
		Transaction transaction1 = new Transaction();
		transaction1.setSubcategory(subcategorySample1);
		transaction1.setAccount(account1);
		transaction1.setType("income");
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
		transaction3.setAmount(237.00);
		transactionRepository.save(transaction3);
		
		Transaction transaction4 = new Transaction();
		transaction4.setSubcategory(subcategorySample5);
		transaction4.setAccount(account1);
		transaction4.setType("expense");
		transaction4.setName("haircut");
		transaction4.setDate(new Date());
		transaction4.setAmount(25.00);
		transactionRepository.save(transaction4);
		
		Transaction transaction5 = new Transaction();
		transaction5.setSubcategory(subcategorySample6);
		transaction5.setAccount(account1);
		transaction5.setType("income");
		transaction5.setName("bank deposit (6months)");
		transaction5.setDate(new Date());
		transaction5.setAmount(77.00);
		transactionRepository.save(transaction5);
		
		Transaction transaction6 = new Transaction();
		transaction6.setSubcategory(subcategorySample1);
		transaction6.setAccount(account2);
		transaction6.setType("income");
		transaction6.setName("bonus");
		transaction6.setDate(new Date());
		transaction6.setAmount(300.00);
		transactionRepository.save(transaction6);
		
	
		
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
		subcategoryLimitRepository.save(subcategoryLimitSample10);
		
		Transaction transaction11 = new Transaction();
		transaction11.setSubcategory(subcategorySample10);
		transaction11.setAccount(account3);
		transaction11.setType("income");
		transaction11.setName("tax refund");
		transaction11.setDate(new Date());
		transaction11.setAmount(999.00);		
		transactionRepository.save(transaction11);
		
	
	}
}

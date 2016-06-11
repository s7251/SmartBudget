package pl.smartbudget.service;

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
		accountRepository.save(account1);
		
		Category categorySample1 = new Category();
		categorySample1.setUser(userAdmin);
		categorySample1.setName("SampleCategory1");
		categoryRepository.save(categorySample1);
		
		Category categorySample2 = new Category();
		categorySample2.setUser(userAdmin);
		categorySample2.setName("SampleCategory2");
		categoryRepository.save(categorySample2);
		
		Subcategory subcategorySample1 = new Subcategory();
		subcategorySample1.setCategory(categorySample1);
		subcategorySample1.setName("SampleSubcategory1");
		subcategoryRepository.save(subcategorySample1);
		
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
		transaction1.setName("tax refund");
		transaction1.setDate(new Date());
		transaction1.setAmount(123.00);
		transactionRepository.save(transaction1);
		
		Transaction transaction2 = new Transaction();
		transaction2.setSubcategory(subcategorySample1);
		transaction2.setAccount(account1);
		transaction2.setType("expense");
		transaction2.setName("shopping in supermarket");
		transaction2.setDate(new Date());
		transaction2.setAmount(500.00);
		transactionRepository.save(transaction2);
		
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
		
		Category categorySample3 = new Category();
		categorySample3.setUser(user1);
		categorySample3.setName("SampleCategory1");
		categoryRepository.save(categorySample3);
		
		Subcategory subcategorySample3 = new Subcategory();
		subcategorySample3.setCategory(categorySample3);
		subcategorySample3.setName("SampleSubcategory3");
		subcategoryRepository.save(subcategorySample3);
		
		SubcategoryLimit subcategoryLimitSample3 = new SubcategoryLimit();
		subcategoryLimitSample3.setSubcategory(subcategorySample3);
		subcategoryLimitSample3.setAmount(200.00);
		subcategoryLimitSample3.setDate(new Date());
		subcategoryLimitSample3.setName("Limit on Subcategory3");
		subcategoryLimitRepository.save(subcategoryLimitSample3);
		
		Transaction transaction3 = new Transaction();
		transaction3.setSubcategory(subcategorySample3);
		transaction3.setAccount(account3);
		transaction3.setType("influence");
		transaction3.setName("tax refund");
		transaction3.setDate(new Date());
		transaction3.setAmount(999.00);
		transactionRepository.save(transaction3);
	
	}
}

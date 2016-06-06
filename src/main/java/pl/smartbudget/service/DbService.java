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
		
		User userAdmin = new User();
		userAdmin.setName("admin");
		userAdmin.setPassword("admin");
		userAdmin.setEmail("admin@admin.pl");
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		userAdmin.setRoles(roles);
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
		transaction1.setName("Wp³ata");
		transaction1.setDate(new Date());
		transaction1.setAmount(123.00);
		transactionRepository.save(transaction1);
	
	}
}

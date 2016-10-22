package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.Calendar;
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
		
		Transaction transaction1 = new Transaction();
		transaction1.setSubcategory(subcategorySample1);
		transaction1.setAccount(account1);
		transaction1.setType("income");
		transaction1.setName("monthly salary");
		Calendar transaction1Cal = Calendar.getInstance();
		transaction1Cal.add(Calendar.MONTH, -11); //10.2015
		Date transaction1Date = transaction1Cal.getTime();
		transaction1.setDate(transaction1Date);	
		transaction1.setAmount(4000.00);
		transactionRepository.save(transaction1);
		
		Transaction transaction2 = new Transaction();
		transaction2.setSubcategory(subcategorySample3);
		transaction2.setAccount(account1);
		transaction2.setType("expense");
		transaction2.setName("shopping in supermarket");
		Calendar transaction2Cal = Calendar.getInstance();
		transaction2Cal.add(Calendar.MONTH, -11); //10.2015
		Date transaction2Date = transaction2Cal.getTime();
		transaction2.setDate(transaction2Date);
		transaction2.setAmount(100.00);
		transactionRepository.save(transaction2);
		
		Transaction transaction3 = new Transaction();
		transaction3.setSubcategory(subcategorySample1);
		transaction3.setAccount(account1);
		transaction3.setType("income");
		transaction3.setName("monthly salary");
		Calendar transaction3Cal = Calendar.getInstance();
		transaction3Cal.add(Calendar.MONTH, -10); //11.2015
		Date transaction3Date = transaction3Cal.getTime();
		transaction3.setDate(transaction3Date);	
		transaction3.setAmount(4000.00);
		transactionRepository.save(transaction3);
		
		Transaction transaction4 = new Transaction();
		transaction4.setSubcategory(subcategorySample3);
		transaction4.setAccount(account1);
		transaction4.setType("expense");
		transaction4.setName("shopping in supermarket");
		Calendar transaction4Cal = Calendar.getInstance();
		transaction4Cal.add(Calendar.MONTH, -10); //11.2015
		Date transaction4Date = transaction4Cal.getTime();
		transaction4.setDate(transaction4Date);
		transaction4.setAmount(100.00);
		transactionRepository.save(transaction4);
		
		Transaction transaction5 = new Transaction();
		transaction5.setSubcategory(subcategorySample1);
		transaction5.setAccount(account1);
		transaction5.setType("income");
		transaction5.setName("monthly salary");
		Calendar transaction5Cal = Calendar.getInstance();
		transaction5Cal.add(Calendar.MONTH, -9); //12.2015
		Date transaction5Date = transaction5Cal.getTime();
		transaction5.setDate(transaction5Date);	
		transaction5.setAmount(4000.00);
		transactionRepository.save(transaction5);
		
		Transaction transaction6 = new Transaction();
		transaction6.setSubcategory(subcategorySample3);
		transaction6.setAccount(account1);
		transaction6.setType("expense");
		transaction6.setName("shopping in supermarket");
		Calendar transaction6Cal = Calendar.getInstance();
		transaction6Cal.add(Calendar.MONTH, -9); //12.2015
		Date transaction6Date = transaction6Cal.getTime();
		transaction6.setDate(transaction6Date);
		transaction6.setAmount(100.00);
		transactionRepository.save(transaction6);
		
		Transaction transaction7 = new Transaction();
		transaction7.setSubcategory(subcategorySample1);
		transaction7.setAccount(account1);
		transaction7.setType("income");
		transaction7.setName("monthly salary");
		Calendar transaction7Cal = Calendar.getInstance();
		transaction7Cal.add(Calendar.MONTH, -8); //01.2016
		Date transaction7Date = transaction7Cal.getTime();
		transaction7.setDate(transaction7Date);	
		transaction7.setAmount(4000.00);
		transactionRepository.save(transaction7);
		
		Transaction transaction8 = new Transaction();
		transaction8.setSubcategory(subcategorySample3);
		transaction8.setAccount(account1);
		transaction8.setType("expense");
		transaction8.setName("shopping in supermarket");
		Calendar transaction8Cal = Calendar.getInstance();
		transaction8Cal.add(Calendar.MONTH, -8); //01.2016
		Date transaction8Date = transaction8Cal.getTime();
		transaction8.setDate(transaction8Date);
		transaction8.setAmount(100.00);
		transactionRepository.save(transaction8);
		
		Transaction transaction9 = new Transaction();
		transaction9.setSubcategory(subcategorySample1);
		transaction9.setAccount(account1);
		transaction9.setType("income");
		transaction9.setName("monthly salary");
		Calendar transaction9Cal = Calendar.getInstance();
		transaction9Cal.add(Calendar.MONTH, -7); //02.2016
		Date transaction9Date = transaction9Cal.getTime();
		transaction9.setDate(transaction9Date);	
		transaction9.setAmount(4000.00);
		transactionRepository.save(transaction9);
		
		Transaction transaction10 = new Transaction();
		transaction10.setSubcategory(subcategorySample3);
		transaction10.setAccount(account1);
		transaction10.setType("expense");
		transaction10.setName("shopping in supermarket");
		Calendar transaction10Cal = Calendar.getInstance();
		transaction10Cal.add(Calendar.MONTH, -7); //02.2016
		Date transaction10Date = transaction10Cal.getTime();
		transaction10.setDate(transaction10Date);
		transaction10.setAmount(100.00);
		transactionRepository.save(transaction10);
		
		Transaction transaction11 = new Transaction();
		transaction11.setSubcategory(subcategorySample1);
		transaction11.setAccount(account1);
		transaction11.setType("income");
		transaction11.setName("monthly salary");
		Calendar transaction11Cal = Calendar.getInstance();
		transaction11Cal.add(Calendar.MONTH, -6); //03.2016
		Date transaction11Date = transaction11Cal.getTime();
		transaction11.setDate(transaction11Date);	
		transaction11.setAmount(4000.00);
		transactionRepository.save(transaction11);
		
		Transaction transaction12 = new Transaction();
		transaction12.setSubcategory(subcategorySample3);
		transaction12.setAccount(account1);
		transaction12.setType("expense");
		transaction12.setName("shopping in supermarket");
		Calendar transaction12Cal = Calendar.getInstance();
		transaction12Cal.add(Calendar.MONTH, -6); //03.2016
		Date transaction12Date = transaction12Cal.getTime();
		transaction12.setDate(transaction12Date);
		transaction12.setAmount(100.00);
		transactionRepository.save(transaction12);
		
		Transaction transaction13 = new Transaction();
		transaction13.setSubcategory(subcategorySample1);
		transaction13.setAccount(account1);
		transaction13.setType("income");
		transaction13.setName("monthly salary");
		Calendar transaction13Cal = Calendar.getInstance();
		transaction13Cal.add(Calendar.MONTH, -5); //04.2016
		Date transaction13Date = transaction13Cal.getTime();
		transaction13.setDate(transaction13Date);	
		transaction13.setAmount(4000.00);
		transactionRepository.save(transaction13);
		
		Transaction transaction14 = new Transaction();
		transaction14.setSubcategory(subcategorySample3);
		transaction14.setAccount(account1);
		transaction14.setType("expense");
		transaction14.setName("shopping in supermarket");
		Calendar transaction14Cal = Calendar.getInstance();
		transaction14Cal.add(Calendar.MONTH, -5); //04.2016
		Date transaction14Date = transaction14Cal.getTime();
		transaction14.setDate(transaction14Date);
		transaction14.setAmount(100.00);
		transactionRepository.save(transaction14);
		
		Transaction transaction15 = new Transaction();
		transaction15.setSubcategory(subcategorySample1);
		transaction15.setAccount(account1);
		transaction15.setType("income");
		transaction15.setName("monthly salary");
		Calendar transaction15Cal = Calendar.getInstance();
		transaction15Cal.add(Calendar.MONTH, -4); //05.2016
		Date transaction15Date = transaction15Cal.getTime();
		transaction15.setDate(transaction15Date);	
		transaction15.setAmount(4000.00);
		transactionRepository.save(transaction15);
		
		Transaction transaction16 = new Transaction();
		transaction16.setSubcategory(subcategorySample3);
		transaction16.setAccount(account1);
		transaction16.setType("expense");
		transaction16.setName("shopping in supermarket");
		Calendar transaction16Cal = Calendar.getInstance();
		transaction16Cal.add(Calendar.MONTH, -4); //05.2016
		Date transaction16Date = transaction16Cal.getTime();
		transaction16.setDate(transaction16Date);
		transaction16.setAmount(100.00);
		transactionRepository.save(transaction16);
			
		Transaction transaction19 = new Transaction();
		transaction19.setSubcategory(subcategorySample1);
		transaction19.setAccount(account1);
		transaction19.setType("income");
		transaction19.setName("monthly salary");
		Calendar transaction19Cal = Calendar.getInstance();
		transaction19Cal.add(Calendar.MONTH, -3); //07.2016
		Date transaction19Date = transaction19Cal.getTime();
		transaction19.setDate(transaction19Date);	
		transaction19.setAmount(4000.00);
		transactionRepository.save(transaction19);
		
		Transaction transaction20 = new Transaction();
		transaction20.setSubcategory(subcategorySample3);
		transaction20.setAccount(account1);
		transaction20.setType("expense");
		transaction20.setName("shopping in supermarket");
		Calendar transaction20Cal = Calendar.getInstance();
		transaction20Cal.add(Calendar.MONTH, -3); //07.2016
		Date transaction20Date = transaction20Cal.getTime();
		transaction20.setDate(transaction20Date);
		transaction20.setAmount(100.00);
		transactionRepository.save(transaction20);
		
		Transaction transaction21 = new Transaction();
		transaction21.setSubcategory(subcategorySample1);
		transaction21.setAccount(account1);
		transaction21.setType("income");
		transaction21.setName("monthly salary");
		Calendar transaction21Cal = Calendar.getInstance();
		transaction21Cal.add(Calendar.MONTH, -2); //08.2016
		Date transaction21Date = transaction21Cal.getTime();
		transaction21.setDate(transaction21Date);
		transaction21.setAmount(5000.00);
		transactionRepository.save(transaction21);
		
		Transaction transaction22 = new Transaction();
		transaction22.setSubcategory(subcategorySample1);
		transaction22.setAccount(account1);
		transaction22.setType("income");
		transaction22.setName("monthly salary");
		Calendar transaction22Cal = Calendar.getInstance();
		transaction22Cal.add(Calendar.MONTH, -2); //08.2016
		Date transaction22Date = transaction22Cal.getTime();
		transaction22.setDate(transaction22Date);
		transaction22.setAmount(1000.00);
		transactionRepository.save(transaction22);
		
		Transaction transaction23 = new Transaction();
		transaction23.setSubcategory(subcategorySample3);
		transaction23.setAccount(account1);
		transaction23.setType("expense");
		transaction23.setName("shopping in supermarket");
		Calendar transaction23Cal = Calendar.getInstance();
		transaction23Cal.add(Calendar.MONTH, -2); //08.2016
		Date transaction23Date = transaction23Cal.getTime();
		transaction23.setDate(transaction23Date);
		transaction23.setAmount(200.00);
		transactionRepository.save(transaction23);
		
		Transaction transaction24 = new Transaction();
		transaction24.setSubcategory(subcategorySample1);
		transaction24.setAccount(account1);
		transaction24.setType("income");
		transaction24.setName("monthly salary");
		Calendar transaction24Cal = Calendar.getInstance();
		transaction24Cal.add(Calendar.MONTH, -1); //09.2016
		Date transaction24Date = transaction10Cal.getTime();
		transaction24.setDate(transaction24Date);
		transaction24.setAmount(1000.00);
		transactionRepository.save(transaction24);
		
		Transaction transaction25 = new Transaction();
		transaction25.setSubcategory(subcategorySample4);
		transaction25.setAccount(account1);
		transaction25.setType("expense");
		transaction25.setName("dinner in restaurant");
		Calendar transaction25Cal = Calendar.getInstance();
		transaction25Cal.add(Calendar.MONTH, -1); //09.2016
		Date transaction25Date = transaction25Cal.getTime();
		transaction25.setDate(transaction25Date);
		transaction25.setAmount(300.00);
		transactionRepository.save(transaction25);
		
		Transaction transaction26 = new Transaction();
		transaction26.setSubcategory(subcategorySample4);
		transaction26.setAccount(account1);
		transaction26.setType("expense");
		transaction26.setName("dinner in restaurant");
		Calendar transaction26Cal = Calendar.getInstance();
		transaction26Cal.add(Calendar.MONTH, -1); //09.2016
		Date transaction26Date = transaction26Cal.getTime();
		transaction26.setDate(transaction26Date);
		transaction26.setAmount(300.00);
		transactionRepository.save(transaction26);
		
		Transaction transaction27 = new Transaction();
		transaction27.setSubcategory(subcategorySample4);
		transaction27.setAccount(account1);
		transaction27.setType("expense");
		transaction27.setName("dinner in restaurant");
		Calendar transaction27Cal = Calendar.getInstance();
		transaction27Cal.add(Calendar.MONTH, -1); //09.2016
		Date transaction27Date = transaction27Cal.getTime();
		transaction27.setDate(transaction27Date);
		transaction27.setAmount(300.00);
		transactionRepository.save(transaction27);
		
		Transaction transaction28 = new Transaction();
		transaction28.setSubcategory(subcategorySample1);
		transaction28.setAccount(account1);
		transaction28.setType("income");
		transaction28.setName("monthly salary");
		transaction28.setDate(new Date());
		transaction28.setAmount(3600.00);
		transactionRepository.save(transaction28);
		
		Transaction transaction29 = new Transaction();
		transaction29.setSubcategory(subcategorySample3);
		transaction29.setAccount(account2);
		transaction29.setType("expense");
		transaction29.setName("shopping in supermarket");
		transaction29.setDate(new Date());
		transaction29.setAmount(89.00);
		transactionRepository.save(transaction29);
		
		Transaction transaction30 = new Transaction();
		transaction30.setSubcategory(subcategorySample4);
		transaction30.setAccount(account1);
		transaction30.setType("expense");
		transaction30.setName("dinner in restaurant");
		transaction30.setDate(new Date());
		transaction30.setAmount(237.00);
		transactionRepository.save(transaction30);
		
		Transaction transaction31 = new Transaction();
		transaction31.setSubcategory(subcategorySample5);
		transaction31.setAccount(account1);
		transaction31.setType("expense");
		transaction31.setName("haircut");
		transaction31.setDate(new Date());
		transaction31.setAmount(25.00);
		transactionRepository.save(transaction31);
		
		Transaction transaction32 = new Transaction();
		transaction32.setSubcategory(subcategorySample6);
		transaction32.setAccount(account1);
		transaction32.setType("income");
		transaction32.setName("bank deposit (6months)");
		transaction32.setDate(new Date());
		transaction32.setAmount(77.00);
		transactionRepository.save(transaction32);
		
		Transaction transaction33 = new Transaction();
		transaction33.setSubcategory(subcategorySample1);
		transaction33.setAccount(account2);
		transaction33.setType("income");
		transaction33.setName("bonus");
		transaction33.setDate(new Date());
		transaction33.setAmount(300.00);
		transactionRepository.save(transaction33);


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
		
		Transaction transaction100 = new Transaction();
		transaction100.setSubcategory(subcategorySample10);
		transaction100.setAccount(account3);
		transaction100.setType("income");
		transaction100.setName("tax refund");
		transaction100.setDate(new Date());
		transaction100.setAmount(999.00);		
		transactionRepository.save(transaction100);
		
	
	}
}

package pl.smartbudget.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		userAdmin.setPassword(bcrypt.encode("admin"));
		userAdmin.setEmail("kulkry@gmail.com");
		userAdmin.setEnabled(true);
		List<Role> adminRoles = new ArrayList<Role>();
		adminRoles.add(roleAdmin);
		adminRoles.add(roleUser);
		userAdmin.setRoles(adminRoles);
		userRepository.save(userAdmin);
		
		Account account1 = new Account();
		account1.setUser(userAdmin);
		account1.setName("Inteligo");
		accountRepository.save(account1);
		
		Account account3 = new Account();
		account3.setUser(userAdmin);
		account3.setName("MBank");
		accountRepository.save(account3);
		
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
		
		Category categorySample5 = new Category();
		categorySample5.setUser(userAdmin);
		categorySample5.setName("House");
		categoryRepository.save(categorySample5);
		
		Category categorySample6 = new Category();
		categorySample6.setUser(userAdmin);
		categorySample6.setName("Entertainment");
		categoryRepository.save(categorySample6);
		
		Category categorySample7 = new Category();
		categorySample7.setUser(userAdmin);
		categorySample7.setName("Car");
		categoryRepository.save(categorySample7);
		
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
		
		Subcategory subcategorySample7 = new Subcategory();
		subcategorySample7.setCategory(categorySample5);
		subcategorySample7.setName("House Charges");
		subcategoryRepository.save(subcategorySample7);
		
		Subcategory subcategorySample8 = new Subcategory();
		subcategorySample8.setCategory(categorySample6);
		subcategorySample8.setName("Vacation");
		subcategoryRepository.save(subcategorySample8);
		
		Subcategory subcategorySample9 = new Subcategory();
		subcategorySample9.setCategory(categorySample7);
		subcategorySample9.setName("Fuel");
		subcategoryRepository.save(subcategorySample9);
		
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
		
		// 60 months before
		
		Transaction transaction1 = new Transaction();
		transaction1.setSubcategory(subcategorySample1);
		transaction1.setAccount(account1);
		transaction1.setType("income");
		transaction1.setMemo("monthly salary");
		Calendar transaction1Cal = Calendar.getInstance();
		transaction1Cal.add(Calendar.MONTH, -60); 
		Date transaction1Date = transaction1Cal.getTime();
		transaction1.setDate(transaction1Date);	
		transaction1.setAmount(4282.50);
		transactionRepository.save(transaction1);
		
		Transaction transaction2 = new Transaction();
		transaction2.setSubcategory(subcategorySample3);
		transaction2.setAccount(account1);
		transaction2.setType("expense");
		transaction2.setMemo("shopping in supermarket");
		Calendar transaction2Cal = Calendar.getInstance();
		transaction2Cal.add(Calendar.MONTH, -60); 
		Date transaction2Date = transaction2Cal.getTime();
		transaction2.setDate(transaction2Date);	
		transaction2.setAmount(1126.00);
		transactionRepository.save(transaction2);
		
		Transaction transaction3 = new Transaction();
		transaction3.setSubcategory(subcategorySample4);
		transaction3.setAccount(account1);
		transaction3.setType("expense");
		transaction3.setMemo("dinner in restaurant");
		Calendar transaction3Cal = Calendar.getInstance();
		transaction3Cal.add(Calendar.MONTH, -60); 
		Date transaction3Date = transaction3Cal.getTime();
		transaction3.setDate(transaction3Date);	
		transaction3.setAmount(751.00);
		transactionRepository.save(transaction3);
		
		Transaction transaction4 = new Transaction();
		transaction4.setSubcategory(subcategorySample7);
		transaction4.setAccount(account1);
		transaction4.setType("expense");
		transaction4.setMemo("bills");
		Calendar transaction4Cal = Calendar.getInstance();
		transaction4Cal.add(Calendar.MONTH, -60); 
		Date transaction4Date = transaction4Cal.getTime();
		transaction4.setDate(transaction4Date);	
		transaction4.setAmount(1502.00);
		transactionRepository.save(transaction4);
		
		Transaction transaction5 = new Transaction();
		transaction5.setSubcategory(subcategorySample9);
		transaction5.setAccount(account1);
		transaction5.setType("expense");
		transaction5.setMemo("car fuel");
		Calendar transaction5Cal = Calendar.getInstance();
		transaction5Cal.add(Calendar.MONTH, -60); 
		Date transaction5Date = transaction5Cal.getTime();
		transaction5.setDate(transaction5Date);	
		transaction5.setAmount(375.00);
		transactionRepository.save(transaction5);
		
		Transaction transaction6 = new Transaction();
		transaction6.setSubcategory(subcategorySample5);
		transaction6.setAccount(account1);
		transaction6.setType("expense");
		transaction6.setMemo("barber");
		Calendar transaction6Cal = Calendar.getInstance();
		transaction6Cal.add(Calendar.MONTH, -60); 
		Date transaction6Date = transaction6Cal.getTime();
		transaction6.setDate(transaction6Date);	
		transaction6.setAmount(10.00);
		transactionRepository.save(transaction6);


		// 59 months before
		
		Transaction transaction7 = new Transaction();
		transaction7.setSubcategory(subcategorySample1);
		transaction7.setAccount(account1);
		transaction7.setType("income");
		transaction7.setMemo("monthly salary");
		Calendar transaction7Cal = Calendar.getInstance();
		transaction7Cal.add(Calendar.MONTH, -59); 
		Date transaction7Date = transaction7Cal.getTime();
		transaction7.setDate(transaction7Date);	
		transaction7.setAmount(4615.00);
		transactionRepository.save(transaction7);
		
		Transaction transaction8 = new Transaction();
		transaction8.setSubcategory(subcategorySample3);
		transaction8.setAccount(account1);
		transaction8.setType("expense");
		transaction8.setMemo("shopping in supermarket");
		Calendar transaction8Cal = Calendar.getInstance();
		transaction8Cal.add(Calendar.MONTH, -59); 
		Date transaction8Date = transaction8Cal.getTime();
		transaction8.setDate(transaction8Date);	
		transaction8.setAmount(1503.00);
		transactionRepository.save(transaction8);
		
		Transaction transaction9 = new Transaction();
		transaction9.setSubcategory(subcategorySample4);
		transaction9.setAccount(account1);
		transaction9.setType("expense");
		transaction9.setMemo("dinner in restaurant");
		Calendar transaction9Cal = Calendar.getInstance();
		transaction9Cal.add(Calendar.MONTH, -59); 
		Date transaction9Date = transaction9Cal.getTime();
		transaction9.setDate(transaction9Date);	
		transaction9.setAmount(1002.00);
		transactionRepository.save(transaction9);
		
		Transaction transaction10 = new Transaction();
		transaction10.setSubcategory(subcategorySample7);
		transaction10.setAccount(account1);
		transaction10.setType("expense");
		transaction10.setMemo("bills");
		Calendar transaction10Cal = Calendar.getInstance();
		transaction10Cal.add(Calendar.MONTH, -59); 
		Date transaction10Date = transaction10Cal.getTime();
		transaction10.setDate(transaction10Date);	
		transaction10.setAmount(1503.00);
		transactionRepository.save(transaction10);
		
		Transaction transaction11 = new Transaction();
		transaction11.setSubcategory(subcategorySample9);
		transaction11.setAccount(account1);
		transaction11.setType("expense");
		transaction11.setMemo("car fuel");
		Calendar transaction11Cal = Calendar.getInstance();
		transaction11Cal.add(Calendar.MONTH, -59); 
		Date transaction11Date = transaction11Cal.getTime();
		transaction11.setDate(transaction11Date);	
		transaction11.setAmount(1002.00);
		transactionRepository.save(transaction11);
		
		Transaction transaction12 = new Transaction();
		transaction12.setSubcategory(subcategorySample5);
		transaction12.setAccount(account1);
		transaction12.setType("expense");
		transaction12.setMemo("barber");
		Calendar transaction12Cal = Calendar.getInstance();
		transaction12Cal.add(Calendar.MONTH, -59); 
		Date transaction12Date = transaction12Cal.getTime();
		transaction12.setDate(transaction12Date);	
		transaction12.setAmount(10.00);
		transactionRepository.save(transaction12);
		
		// 58 months before
		
		Transaction transaction13 = new Transaction();
		transaction13.setSubcategory(subcategorySample1);
		transaction13.setAccount(account1);
		transaction13.setType("income");
		transaction13.setMemo("monthly salary");
		Calendar transaction13Cal = Calendar.getInstance();
		transaction13Cal.add(Calendar.MONTH, -58); 
		Date transaction13Date = transaction13Cal.getTime();
		transaction13.setDate(transaction13Date);	
		transaction13.setAmount(3192.75);
		transactionRepository.save(transaction13);
		
		Transaction transaction14 = new Transaction();
		transaction14.setSubcategory(subcategorySample3);
		transaction14.setAccount(account1);
		transaction14.setType("expense");
		transaction14.setMemo("shopping in supermarket");
		Calendar transaction14Cal = Calendar.getInstance();
		transaction14Cal.add(Calendar.MONTH, -58); 
		Date transaction14Date = transaction14Cal.getTime();
		transaction14.setDate(transaction14Date);	
		transaction14.setAmount(936.00);
		transactionRepository.save(transaction14);
		
		Transaction transaction15 = new Transaction();
		transaction15.setSubcategory(subcategorySample4);
		transaction15.setAccount(account1);
		transaction15.setType("expense");
		transaction15.setMemo("dinner in restaurant");
		Calendar transaction15Cal = Calendar.getInstance();
		transaction15Cal.add(Calendar.MONTH, -58); 
		Date transaction15Date = transaction15Cal.getTime();
		transaction15.setDate(transaction15Date);	
		transaction15.setAmount(624.00);
		transactionRepository.save(transaction15);
		
		Transaction transaction16 = new Transaction();
		transaction16.setSubcategory(subcategorySample7);
		transaction16.setAccount(account1);
		transaction16.setType("expense");
		transaction16.setMemo("bills");
		Calendar transaction16Cal = Calendar.getInstance();
		transaction16Cal.add(Calendar.MONTH, -58); 
		Date transaction16Date = transaction16Cal.getTime();
		transaction16.setDate(transaction16Date);	
		transaction16.setAmount(1248.00);
		transactionRepository.save(transaction16);
		
		Transaction transaction17 = new Transaction();
		transaction17.setSubcategory(subcategorySample9);
		transaction17.setAccount(account1);
		transaction17.setType("expense");
		transaction17.setMemo("car fuel");
		Calendar transaction17Cal = Calendar.getInstance();
		transaction17Cal.add(Calendar.MONTH, -58); 
		Date transaction17Date = transaction17Cal.getTime();
		transaction17.setDate(transaction17Date);	
		transaction17.setAmount(312.00);
		transactionRepository.save(transaction17);
		
		Transaction transaction18 = new Transaction();
		transaction18.setSubcategory(subcategorySample5);
		transaction18.setAccount(account1);
		transaction18.setType("expense");
		transaction18.setMemo("barber");
		Calendar transaction18Cal = Calendar.getInstance();
		transaction18Cal.add(Calendar.MONTH, -58); 
		Date transaction18Date = transaction18Cal.getTime();
		transaction18.setDate(transaction18Date);	
		transaction18.setAmount(10.00);
		transactionRepository.save(transaction18);
		
		// 57 months before
		
		Transaction transaction19 = new Transaction();
		transaction19.setSubcategory(subcategorySample1);
		transaction19.setAccount(account1);
		transaction19.setType("income");
		transaction19.setMemo("monthly salary");
		Calendar transaction19Cal = Calendar.getInstance();
		transaction19Cal.add(Calendar.MONTH, -57); 
		Date transaction19Date = transaction19Cal.getTime();
		transaction19.setDate(transaction19Date);	
		transaction19.setAmount(3370.25);
		transactionRepository.save(transaction19);
		
		Transaction transaction20 = new Transaction();
		transaction20.setSubcategory(subcategorySample3);
		transaction20.setAccount(account1);
		transaction20.setType("expense");
		transaction20.setMemo("shopping in supermarket");
		Calendar transaction20Cal = Calendar.getInstance();
		transaction20Cal.add(Calendar.MONTH, -57); 
		Date transaction20Date = transaction20Cal.getTime();
		transaction20.setDate(transaction20Date);	
		transaction20.setAmount(992.00);
		transactionRepository.save(transaction20);
		
		Transaction transaction21 = new Transaction();
		transaction21.setSubcategory(subcategorySample4);
		transaction21.setAccount(account1);
		transaction21.setType("expense");
		transaction21.setMemo("dinner in restaurant");
		Calendar transaction21Cal = Calendar.getInstance();
		transaction21Cal.add(Calendar.MONTH, -57); 
		Date transaction21Date = transaction21Cal.getTime();
		transaction21.setDate(transaction21Date);	
		transaction21.setAmount(661.00);
		transactionRepository.save(transaction21);
		
		Transaction transaction22 = new Transaction();
		transaction22.setSubcategory(subcategorySample7);
		transaction22.setAccount(account1);
		transaction22.setType("expense");
		transaction22.setMemo("bills");
		Calendar transaction22Cal = Calendar.getInstance();
		transaction22Cal.add(Calendar.MONTH, -57); 
		Date transaction22Date = transaction22Cal.getTime();
		transaction22.setDate(transaction22Date);	
		transaction22.setAmount(1322.00);
		transactionRepository.save(transaction22);
		
		Transaction transaction23 = new Transaction();
		transaction23.setSubcategory(subcategorySample9);
		transaction23.setAccount(account1);
		transaction23.setType("expense");
		transaction23.setMemo("car fuel");
		Calendar transaction23Cal = Calendar.getInstance();
		transaction23Cal.add(Calendar.MONTH, -57); 
		Date transaction23Date = transaction23Cal.getTime();
		transaction23.setDate(transaction23Date);	
		transaction23.setAmount(331.00);
		transactionRepository.save(transaction23);
		
		Transaction transaction24 = new Transaction();
		transaction24.setSubcategory(subcategorySample5);
		transaction24.setAccount(account1);
		transaction24.setType("expense");
		transaction24.setMemo("barber");
		Calendar transaction24Cal = Calendar.getInstance();
		transaction24Cal.add(Calendar.MONTH, -57); 
		Date transaction24Date = transaction24Cal.getTime();
		transaction24.setDate(transaction24Date);	
		transaction24.setAmount(10.00);
		transactionRepository.save(transaction24);
		
		// 56 months before
		
		Transaction transaction25 = new Transaction();
		transaction25.setSubcategory(subcategorySample1);
		transaction25.setAccount(account1);
		transaction25.setType("income");
		transaction25.setMemo("monthly salary");
		Calendar transaction25Cal = Calendar.getInstance();
		transaction25Cal.add(Calendar.MONTH, -56); 
		Date transaction25Date = transaction25Cal.getTime();
		transaction25.setDate(transaction25Date);	
		transaction25.setAmount(3954.75);
		transactionRepository.save(transaction25);
		
		Transaction transaction26 = new Transaction();
		transaction26.setSubcategory(subcategorySample3);
		transaction26.setAccount(account1);
		transaction26.setType("expense");
		transaction26.setMemo("shopping in supermarket");
		Calendar transaction26Cal = Calendar.getInstance();
		transaction26Cal.add(Calendar.MONTH, -56); 
		Date transaction26Date = transaction26Cal.getTime();
		transaction26.setDate(transaction26Date);	
		transaction26.setAmount(1049.00);
		transactionRepository.save(transaction26);
		
		Transaction transaction27 = new Transaction();
		transaction27.setSubcategory(subcategorySample4);
		transaction27.setAccount(account1);
		transaction27.setType("expense");
		transaction27.setMemo("dinner in restaurant");
		Calendar transaction27Cal = Calendar.getInstance();
		transaction27Cal.add(Calendar.MONTH, -56); 
		Date transaction27Date = transaction27Cal.getTime();
		transaction27.setDate(transaction27Date);	
		transaction27.setAmount(699.00);
		transactionRepository.save(transaction27);
		
		Transaction transaction28 = new Transaction();
		transaction28.setSubcategory(subcategorySample7);
		transaction28.setAccount(account1);
		transaction28.setType("expense");
		transaction28.setMemo("bills");
		Calendar transaction28Cal = Calendar.getInstance();
		transaction28Cal.add(Calendar.MONTH, -56); 
		Date transaction28Date = transaction28Cal.getTime();
		transaction28.setDate(transaction28Date);	
		transaction28.setAmount(1398.00);
		transactionRepository.save(transaction28);
		
		Transaction transaction29 = new Transaction();
		transaction29.setSubcategory(subcategorySample9);
		transaction29.setAccount(account1);
		transaction29.setType("expense");
		transaction29.setMemo("car fuel");
		Calendar transaction29Cal = Calendar.getInstance();
		transaction29Cal.add(Calendar.MONTH, -56); 
		Date transaction29Date = transaction29Cal.getTime();
		transaction29.setDate(transaction29Date);	
		transaction29.setAmount(350.00);
		transactionRepository.save(transaction29);
		
		Transaction transaction30 = new Transaction();
		transaction30.setSubcategory(subcategorySample5);
		transaction30.setAccount(account1);
		transaction30.setType("expense");
		transaction30.setMemo("barber");
		Calendar transaction30Cal = Calendar.getInstance();
		transaction30Cal.add(Calendar.MONTH, -56); 
		Date transaction30Date = transaction30Cal.getTime();
		transaction30.setDate(transaction30Date);	
		transaction30.setAmount(10.00);
		transactionRepository.save(transaction30);
		
		// 55 months before
		
		Transaction transaction31 = new Transaction();
		transaction31.setSubcategory(subcategorySample1);
		transaction31.setAccount(account1);
		transaction31.setType("income");
		transaction31.setMemo("monthly salary");
		Calendar transaction31Cal = Calendar.getInstance();
		transaction31Cal.add(Calendar.MONTH, -55); 
		Date transaction31Date = transaction31Cal.getTime();
		transaction31.setDate(transaction31Date);	
		transaction31.setAmount(4001.75);
		transactionRepository.save(transaction31);
		
		Transaction transaction32 = new Transaction();
		transaction32.setSubcategory(subcategorySample3);
		transaction32.setAccount(account1);
		transaction32.setType("expense");
		transaction32.setMemo("shopping in supermarket");
		Calendar transaction32Cal = Calendar.getInstance();
		transaction32Cal.add(Calendar.MONTH, -55); 
		Date transaction32Date = transaction32Cal.getTime();
		transaction32.setDate(transaction32Date);	
		transaction32.setAmount(1016.00);
		transactionRepository.save(transaction32);
		
		Transaction transaction33 = new Transaction();
		transaction33.setSubcategory(subcategorySample4);
		transaction33.setAccount(account1);
		transaction33.setType("expense");
		transaction33.setMemo("dinner in restaurant");
		Calendar transaction33Cal = Calendar.getInstance();
		transaction33Cal.add(Calendar.MONTH, -55); 
		Date transaction33Date = transaction33Cal.getTime();
		transaction33.setDate(transaction33Date);	
		transaction33.setAmount(677.00);
		transactionRepository.save(transaction33);
		
		Transaction transaction34 = new Transaction();
		transaction34.setSubcategory(subcategorySample7);
		transaction34.setAccount(account1);
		transaction34.setType("expense");
		transaction34.setMemo("bills");
		Calendar transaction34Cal = Calendar.getInstance();
		transaction34Cal.add(Calendar.MONTH, -55); 
		Date transaction34Date = transaction34Cal.getTime();
		transaction34.setDate(transaction34Date);	
		transaction34.setAmount(1355.00);
		transactionRepository.save(transaction34);
		
		Transaction transaction35 = new Transaction();
		transaction35.setSubcategory(subcategorySample9);
		transaction35.setAccount(account1);
		transaction35.setType("expense");
		transaction35.setMemo("car fuel");
		Calendar transaction35Cal = Calendar.getInstance();
		transaction35Cal.add(Calendar.MONTH, -55); 
		Date transaction35Date = transaction35Cal.getTime();
		transaction35.setDate(transaction35Date);	
		transaction35.setAmount(339.00);
		transactionRepository.save(transaction35);
		
		Transaction transaction36 = new Transaction();
		transaction36.setSubcategory(subcategorySample5);
		transaction36.setAccount(account1);
		transaction36.setType("expense");
		transaction36.setMemo("barber");
		Calendar transaction36Cal = Calendar.getInstance();
		transaction36Cal.add(Calendar.MONTH, -55); 
		Date transaction36Date = transaction36Cal.getTime();
		transaction36.setDate(transaction36Date);	
		transaction36.setAmount(10.00);
		transactionRepository.save(transaction36);
		
		// 54 months before
		
		Transaction transaction37 = new Transaction();
		transaction37.setSubcategory(subcategorySample1);
		transaction37.setAccount(account1);
		transaction37.setType("income");
		transaction37.setMemo("monthly salary");
		Calendar transaction37Cal = Calendar.getInstance();
		transaction37Cal.add(Calendar.MONTH, -54); 
		Date transaction37Date = transaction37Cal.getTime();
		transaction37.setDate(transaction37Date);	
		transaction37.setAmount(3611.50);
		transactionRepository.save(transaction37);
		
		Transaction transaction38 = new Transaction();
		transaction38.setSubcategory(subcategorySample3);
		transaction38.setAccount(account1);
		transaction38.setType("expense");
		transaction38.setMemo("shopping in supermarket");
		Calendar transaction38Cal = Calendar.getInstance();
		transaction38Cal.add(Calendar.MONTH, -54); 
		Date transaction38Date = transaction38Cal.getTime();
		transaction38.setDate(transaction38Date);	
		transaction38.setAmount(895.00);
		transactionRepository.save(transaction38);
		
		Transaction transaction39 = new Transaction();
		transaction39.setSubcategory(subcategorySample4);
		transaction39.setAccount(account1);
		transaction39.setType("expense");
		transaction39.setMemo("dinner in restaurant");
		Calendar transaction39Cal = Calendar.getInstance();
		transaction39Cal.add(Calendar.MONTH, -54); 
		Date transaction39Date = transaction39Cal.getTime();
		transaction39.setDate(transaction39Date);	
		transaction39.setAmount(597.00);
		transactionRepository.save(transaction39);
		
		Transaction transaction40 = new Transaction();
		transaction40.setSubcategory(subcategorySample7);
		transaction40.setAccount(account1);
		transaction40.setType("expense");
		transaction40.setMemo("bills");
		Calendar transaction40Cal = Calendar.getInstance();
		transaction40Cal.add(Calendar.MONTH, -54); 
		Date transaction40Date = transaction40Cal.getTime();
		transaction40.setDate(transaction40Date);	
		transaction40.setAmount(1194.00);
		transactionRepository.save(transaction40);
		
		Transaction transaction41 = new Transaction();
		transaction41.setSubcategory(subcategorySample9);
		transaction41.setAccount(account1);
		transaction41.setType("expense");
		transaction41.setMemo("car fuel");
		Calendar transaction41Cal = Calendar.getInstance();
		transaction41Cal.add(Calendar.MONTH, -54); 
		Date transaction41Date = transaction41Cal.getTime();
		transaction41.setDate(transaction41Date);	
		transaction41.setAmount(298.00);
		transactionRepository.save(transaction41);
		
		Transaction transaction42 = new Transaction();
		transaction42.setSubcategory(subcategorySample5);
		transaction42.setAccount(account1);
		transaction42.setType("expense");
		transaction42.setMemo("barber");
		Calendar transaction42Cal = Calendar.getInstance();
		transaction42Cal.add(Calendar.MONTH, -54); 
		Date transaction42Date = transaction42Cal.getTime();
		transaction42.setDate(transaction42Date);	
		transaction42.setAmount(10.00);
		transactionRepository.save(transaction42);
		
		// 53 months before
		
		Transaction transaction43 = new Transaction();
		transaction43.setSubcategory(subcategorySample1);
		transaction43.setAccount(account1);
		transaction43.setType("income");
		transaction43.setMemo("monthly salary");
		Calendar transaction43Cal = Calendar.getInstance();
		transaction43Cal.add(Calendar.MONTH, -53); 
		Date transaction43Date = transaction43Cal.getTime();
		transaction43.setDate(transaction43Date);	
		transaction43.setAmount(3260.25);
		transactionRepository.save(transaction43);
		
		Transaction transaction44 = new Transaction();
		transaction44.setSubcategory(subcategorySample3);
		transaction44.setAccount(account1);
		transaction44.setType("expense");
		transaction44.setMemo("shopping in supermarket");
		Calendar transaction44Cal = Calendar.getInstance();
		transaction44Cal.add(Calendar.MONTH, -53); 
		Date transaction44Date = transaction44Cal.getTime();
		transaction44.setDate(transaction44Date);	
		transaction44.setAmount(935.00);
		transactionRepository.save(transaction44);
		
		Transaction transaction45 = new Transaction();
		transaction45.setSubcategory(subcategorySample4);
		transaction45.setAccount(account1);
		transaction45.setType("expense");
		transaction45.setMemo("dinner in restaurant");
		Calendar transaction45Cal = Calendar.getInstance();
		transaction45Cal.add(Calendar.MONTH, -53); 
		Date transaction45Date = transaction45Cal.getTime();
		transaction45.setDate(transaction45Date);	
		transaction45.setAmount(624.00);
		transactionRepository.save(transaction45);
		
		Transaction transaction46 = new Transaction();
		transaction46.setSubcategory(subcategorySample7);
		transaction46.setAccount(account1);
		transaction46.setType("expense");
		transaction46.setMemo("bills");
		Calendar transaction46Cal = Calendar.getInstance();
		transaction46Cal.add(Calendar.MONTH, -53); 
		Date transaction46Date = transaction46Cal.getTime();
		transaction46.setDate(transaction46Date);	
		transaction46.setAmount(1247.00);
		transactionRepository.save(transaction46);
		
		Transaction transaction47 = new Transaction();
		transaction47.setSubcategory(subcategorySample9);
		transaction47.setAccount(account1);
		transaction47.setType("expense");
		transaction47.setMemo("car fuel");
		Calendar transaction47Cal = Calendar.getInstance();
		transaction47Cal.add(Calendar.MONTH, -53); 
		Date transaction47Date = transaction47Cal.getTime();
		transaction47.setDate(transaction47Date);	
		transaction47.setAmount(312.00);
		transactionRepository.save(transaction47);
		
		Transaction transaction48 = new Transaction();
		transaction48.setSubcategory(subcategorySample5);
		transaction48.setAccount(account1);
		transaction48.setType("expense");
		transaction48.setMemo("barber");
		Calendar transaction48Cal = Calendar.getInstance();
		transaction48Cal.add(Calendar.MONTH, -53); 
		Date transaction48Date = transaction48Cal.getTime();
		transaction48.setDate(transaction48Date);	
		transaction48.setAmount(10.00);
		transactionRepository.save(transaction48);
		
		// 52 months before
		
		Transaction transaction49 = new Transaction();
		transaction49.setSubcategory(subcategorySample1);
		transaction49.setAccount(account1);
		transaction49.setType("income");
		transaction49.setMemo("monthly salary");
		Calendar transaction49Cal = Calendar.getInstance();
		transaction49Cal.add(Calendar.MONTH, -52); 
		Date transaction49Date = transaction49Cal.getTime();
		transaction49.setDate(transaction49Date);	
		transaction49.setAmount(3135.00);
		transactionRepository.save(transaction49);
		
		Transaction transaction50 = new Transaction();
		transaction50.setSubcategory(subcategorySample3);
		transaction50.setAccount(account1);
		transaction50.setType("expense");
		transaction50.setMemo("shopping in supermarket");
		Calendar transaction50Cal = Calendar.getInstance();
		transaction50Cal.add(Calendar.MONTH, -52); 
		Date transaction50Date = transaction50Cal.getTime();
		transaction50.setDate(transaction50Date);	
		transaction50.setAmount(905.00);
		transactionRepository.save(transaction50);
		
		Transaction transaction51 = new Transaction();
		transaction51.setSubcategory(subcategorySample4);
		transaction51.setAccount(account1);
		transaction51.setType("expense");
		transaction51.setMemo("dinner in restaurant");
		Calendar transaction51Cal = Calendar.getInstance();
		transaction51Cal.add(Calendar.MONTH, -52); 
		Date transaction51Date = transaction51Cal.getTime();
		transaction51.setDate(transaction51Date);	
		transaction51.setAmount(604.00);
		transactionRepository.save(transaction51);
		
		Transaction transaction52 = new Transaction();
		transaction52.setSubcategory(subcategorySample7);
		transaction52.setAccount(account1);
		transaction52.setType("expense");
		transaction52.setMemo("bills");
		Calendar transaction52Cal = Calendar.getInstance();
		transaction52Cal.add(Calendar.MONTH, -52); 
		Date transaction52Date = transaction52Cal.getTime();
		transaction52.setDate(transaction52Date);	
		transaction52.setAmount(1207.00);
		transactionRepository.save(transaction52);
		
		Transaction transaction53 = new Transaction();
		transaction53.setSubcategory(subcategorySample9);
		transaction53.setAccount(account1);
		transaction53.setType("expense");
		transaction53.setMemo("car fuel");
		Calendar transaction53Cal = Calendar.getInstance();
		transaction53Cal.add(Calendar.MONTH, -52); 
		Date transaction53Date = transaction53Cal.getTime();
		transaction53.setDate(transaction53Date);	
		transaction53.setAmount(302.00);
		transactionRepository.save(transaction53);
		
		Transaction transaction54 = new Transaction();
		transaction54.setSubcategory(subcategorySample5);
		transaction54.setAccount(account1);
		transaction54.setType("expense");
		transaction54.setMemo("barber");
		Calendar transaction54Cal = Calendar.getInstance();
		transaction54Cal.add(Calendar.MONTH, -52); 
		Date transaction54Date = transaction54Cal.getTime();
		transaction54.setDate(transaction54Date);	
		transaction54.setAmount(10.00);
		transactionRepository.save(transaction54);
		
		
		// 51 months before
		
		Transaction transaction55 = new Transaction();
		transaction55.setSubcategory(subcategorySample1);
		transaction55.setAccount(account1);
		transaction55.setType("income");
		transaction55.setMemo("monthly salary");
		Calendar transaction55Cal = Calendar.getInstance();
		transaction55Cal.add(Calendar.MONTH, -51); 
		Date transaction55Date = transaction55Cal.getTime();
		transaction55.setDate(transaction55Date);	
		transaction55.setAmount(5003.00);
		transactionRepository.save(transaction55);
		
		Transaction transaction56 = new Transaction();
		transaction56.setSubcategory(subcategorySample3);
		transaction56.setAccount(account1);
		transaction56.setType("expense");
		transaction56.setMemo("shopping in supermarket");
		Calendar transaction56Cal = Calendar.getInstance();
		transaction56Cal.add(Calendar.MONTH, -51); 
		Date transaction56Date = transaction56Cal.getTime();
		transaction56.setDate(transaction56Date);	
		transaction56.setAmount(1336.00);
		transactionRepository.save(transaction56);
		
		Transaction transaction57 = new Transaction();
		transaction57.setSubcategory(subcategorySample4);
		transaction57.setAccount(account1);
		transaction57.setType("expense");
		transaction57.setMemo("dinner in restaurant");
		Calendar transaction57Cal = Calendar.getInstance();
		transaction57Cal.add(Calendar.MONTH, -51); 
		Date transaction57Date = transaction57Cal.getTime();
		transaction57.setDate(transaction57Date);	
		transaction57.setAmount(890.00);
		transactionRepository.save(transaction57);
		
		Transaction transaction58 = new Transaction();
		transaction58.setSubcategory(subcategorySample7);
		transaction58.setAccount(account1);
		transaction58.setType("expense");
		transaction58.setMemo("bills");
		Calendar transaction58Cal = Calendar.getInstance();
		transaction58Cal.add(Calendar.MONTH, -51); 
		Date transaction58Date = transaction58Cal.getTime();
		transaction58.setDate(transaction58Date);	
		transaction58.setAmount(1781.00);
		transactionRepository.save(transaction58);
		
		Transaction transaction59 = new Transaction();
		transaction59.setSubcategory(subcategorySample9);
		transaction59.setAccount(account1);
		transaction59.setType("expense");
		transaction59.setMemo("car fuel");
		Calendar transaction59Cal = Calendar.getInstance();
		transaction59Cal.add(Calendar.MONTH, -51); 
		Date transaction59Date = transaction59Cal.getTime();
		transaction59.setDate(transaction59Date);	
		transaction59.setAmount(445.00);
		transactionRepository.save(transaction59);
		
		Transaction transaction60 = new Transaction();
		transaction60.setSubcategory(subcategorySample5);
		transaction60.setAccount(account1);
		transaction60.setType("expense");
		transaction60.setMemo("barber");
		Calendar transaction60Cal = Calendar.getInstance();
		transaction60Cal.add(Calendar.MONTH, -51); 
		Date transaction60Date = transaction60Cal.getTime();
		transaction60.setDate(transaction60Date);	
		transaction60.setAmount(10.00);
		transactionRepository.save(transaction60);
		
		// 50 months before
		
		Transaction transaction61 = new Transaction();
		transaction61.setSubcategory(subcategorySample1);
		transaction61.setAccount(account1);
		transaction61.setType("income");
		transaction61.setMemo("monthly salary");
		Calendar transaction61Cal = Calendar.getInstance();
		transaction61Cal.add(Calendar.MONTH, -50); 
		Date transaction61Date = transaction61Cal.getTime();
		transaction61.setDate(transaction61Date);	
		transaction61.setAmount(3782.25);
		transactionRepository.save(transaction61);
		
		Transaction transaction62 = new Transaction();
		transaction62.setSubcategory(subcategorySample3);
		transaction62.setAccount(account1);
		transaction62.setType("expense");
		transaction62.setMemo("shopping in supermarket");
		Calendar transaction62Cal = Calendar.getInstance();
		transaction62Cal.add(Calendar.MONTH, -50); 
		Date transaction62Date = transaction62Cal.getTime();
		transaction62.setDate(transaction62Date);	
		transaction62.setAmount(1012.00);
		transactionRepository.save(transaction62);
		
		Transaction transaction63 = new Transaction();
		transaction63.setSubcategory(subcategorySample4);
		transaction63.setAccount(account1);
		transaction63.setType("expense");
		transaction63.setMemo("dinner in restaurant");
		Calendar transaction63Cal = Calendar.getInstance();
		transaction63Cal.add(Calendar.MONTH, -50); 
		Date transaction63Date = transaction63Cal.getTime();
		transaction63.setDate(transaction63Date);	
		transaction63.setAmount(674.00);
		transactionRepository.save(transaction63);
		
		Transaction transaction64 = new Transaction();
		transaction64.setSubcategory(subcategorySample7);
		transaction64.setAccount(account1);
		transaction64.setType("expense");
		transaction64.setMemo("bills");
		Calendar transaction64Cal = Calendar.getInstance();
		transaction64Cal.add(Calendar.MONTH, -50); 
		Date transaction64Date = transaction64Cal.getTime();
		transaction64.setDate(transaction64Date);	
		transaction64.setAmount(1349.00);
		transactionRepository.save(transaction64);
		
		Transaction transaction65 = new Transaction();
		transaction65.setSubcategory(subcategorySample9);
		transaction65.setAccount(account1);
		transaction65.setType("expense");
		transaction65.setMemo("car fuel");
		Calendar transaction65Cal = Calendar.getInstance();
		transaction65Cal.add(Calendar.MONTH, -50); 
		Date transaction65Date = transaction65Cal.getTime();
		transaction65.setDate(transaction65Date);	
		transaction65.setAmount(337.00);
		transactionRepository.save(transaction65);
		
		Transaction transaction66 = new Transaction();
		transaction66.setSubcategory(subcategorySample5);
		transaction66.setAccount(account1);
		transaction66.setType("expense");
		transaction66.setMemo("barber");
		Calendar transaction66Cal = Calendar.getInstance();
		transaction66Cal.add(Calendar.MONTH, -50); 
		Date transaction66Date = transaction66Cal.getTime();
		transaction66.setDate(transaction66Date);	
		transaction66.setAmount(10.00);
		transactionRepository.save(transaction66);
		
		// 49 months before
		
		Transaction transaction67 = new Transaction();
		transaction67.setSubcategory(subcategorySample1);
		transaction67.setAccount(account1);
		transaction67.setType("income");
		transaction67.setMemo("monthly salary");
		Calendar transaction67Cal = Calendar.getInstance();
		transaction67Cal.add(Calendar.MONTH, -49); 
		Date transaction67Date = transaction67Cal.getTime();
		transaction67.setDate(transaction67Date);	
		transaction67.setAmount(3048.75);
		transactionRepository.save(transaction67);
		
		Transaction transaction68 = new Transaction();
		transaction68.setSubcategory(subcategorySample3);
		transaction68.setAccount(account1);
		transaction68.setType("expense");
		transaction68.setMemo("shopping in supermarket");
		Calendar transaction68Cal = Calendar.getInstance();
		transaction68Cal.add(Calendar.MONTH, -49); 
		Date transaction68Date = transaction68Cal.getTime();
		transaction68.setDate(transaction68Date);	
		transaction68.setAmount(865.00);
		transactionRepository.save(transaction68);
		
		Transaction transaction69 = new Transaction();
		transaction69.setSubcategory(subcategorySample4);
		transaction69.setAccount(account1);
		transaction69.setType("expense");
		transaction69.setMemo("dinner in restaurant");
		Calendar transaction69Cal = Calendar.getInstance();
		transaction69Cal.add(Calendar.MONTH, -49); 
		Date transaction69Date = transaction69Cal.getTime();
		transaction69.setDate(transaction69Date);	
		transaction69.setAmount(577.00);
		transactionRepository.save(transaction69);
		
		Transaction transaction70 = new Transaction();
		transaction70.setSubcategory(subcategorySample7);
		transaction70.setAccount(account1);
		transaction70.setType("expense");
		transaction70.setMemo("bills");
		Calendar transaction70Cal = Calendar.getInstance();
		transaction70Cal.add(Calendar.MONTH, -49); 
		Date transaction70Date = transaction70Cal.getTime();
		transaction70.setDate(transaction70Date);	
		transaction70.setAmount(1153.00);
		transactionRepository.save(transaction70);
		
		Transaction transaction71 = new Transaction();
		transaction71.setSubcategory(subcategorySample9);
		transaction71.setAccount(account1);
		transaction71.setType("expense");
		transaction71.setMemo("car fuel");
		Calendar transaction71Cal = Calendar.getInstance();
		transaction71Cal.add(Calendar.MONTH, -49); 
		Date transaction71Date = transaction71Cal.getTime();
		transaction71.setDate(transaction71Date);	
		transaction71.setAmount(288.00);
		transactionRepository.save(transaction71);
		
		Transaction transaction72 = new Transaction();
		transaction72.setSubcategory(subcategorySample5);
		transaction72.setAccount(account1);
		transaction72.setType("expense");
		transaction72.setMemo("barber");
		Calendar transaction72Cal = Calendar.getInstance();
		transaction72Cal.add(Calendar.MONTH, -49); 
		Date transaction72Date = transaction72Cal.getTime();
		transaction72.setDate(transaction72Date);	
		transaction72.setAmount(10.00);
		transactionRepository.save(transaction72);
		
		// 48 months before
		
		Transaction transaction73 = new Transaction();
		transaction73.setSubcategory(subcategorySample1);
		transaction73.setAccount(account1);
		transaction73.setType("income");
		transaction73.setMemo("monthly salary");
		Calendar transaction73Cal = Calendar.getInstance();
		transaction73Cal.add(Calendar.MONTH, -48); 
		Date transaction73Date = transaction73Cal.getTime();
		transaction73.setDate(transaction73Date);	
		transaction73.setAmount(4695.25);
		transactionRepository.save(transaction73);
		
		Transaction transaction74 = new Transaction();
		transaction74.setSubcategory(subcategorySample3);
		transaction74.setAccount(account1);
		transaction74.setType("expense");
		transaction74.setMemo("shopping in supermarket");
		Calendar transaction74Cal = Calendar.getInstance();
		transaction74Cal.add(Calendar.MONTH, -48); 
		Date transaction74Date = transaction74Cal.getTime();
		transaction74.setDate(transaction74Date);	
		transaction74.setAmount(1221.00);
		transactionRepository.save(transaction74);
		
		Transaction transaction75 = new Transaction();
		transaction75.setSubcategory(subcategorySample4);
		transaction75.setAccount(account1);
		transaction75.setType("expense");
		transaction75.setMemo("dinner in restaurant");
		Calendar transaction75Cal = Calendar.getInstance();
		transaction75Cal.add(Calendar.MONTH, -48); 
		Date transaction75Date = transaction75Cal.getTime();
		transaction75.setDate(transaction75Date);	
		transaction75.setAmount(814.00);
		transactionRepository.save(transaction75);
		
		Transaction transaction76 = new Transaction();
		transaction76.setSubcategory(subcategorySample7);
		transaction76.setAccount(account1);
		transaction76.setType("expense");
		transaction76.setMemo("bills");
		Calendar transaction76Cal = Calendar.getInstance();
		transaction76Cal.add(Calendar.MONTH, -48); 
		Date transaction76Date = transaction76Cal.getTime();
		transaction76.setDate(transaction76Date);	
		transaction76.setAmount(1628.00);
		transactionRepository.save(transaction76);
		
		Transaction transaction77 = new Transaction();
		transaction77.setSubcategory(subcategorySample9);
		transaction77.setAccount(account1);
		transaction77.setType("expense");
		transaction77.setMemo("car fuel");
		Calendar transaction77Cal = Calendar.getInstance();
		transaction77Cal.add(Calendar.MONTH, -48); 
		Date transaction77Date = transaction77Cal.getTime();
		transaction77.setDate(transaction77Date);	
		transaction77.setAmount(407.00);
		transactionRepository.save(transaction77);
		
		Transaction transaction78 = new Transaction();
		transaction78.setSubcategory(subcategorySample5);
		transaction78.setAccount(account1);
		transaction78.setType("expense");
		transaction78.setMemo("barber");
		Calendar transaction78Cal = Calendar.getInstance();
		transaction78Cal.add(Calendar.MONTH, -48); 
		Date transaction78Date = transaction78Cal.getTime();
		transaction78.setDate(transaction78Date);	
		transaction78.setAmount(15.00);
		transactionRepository.save(transaction78);
		
		// 47 months before
		
		Transaction transaction79 = new Transaction();
		transaction79.setSubcategory(subcategorySample1);
		transaction79.setAccount(account1);
		transaction79.setType("income");
		transaction79.setMemo("monthly salary");
		Calendar transaction79Cal = Calendar.getInstance();
		transaction79Cal.add(Calendar.MONTH, -47); 
		Date transaction79Date = transaction79Cal.getTime();
		transaction79.setDate(transaction79Date);	
		transaction79.setAmount(4736.5);
		transactionRepository.save(transaction79);
		
		Transaction transaction80 = new Transaction();
		transaction80.setSubcategory(subcategorySample3);
		transaction80.setAccount(account1);
		transaction80.setType("expense");
		transaction80.setMemo("shopping in supermarket");
		Calendar transaction80Cal = Calendar.getInstance();
		transaction80Cal.add(Calendar.MONTH, -47); 
		Date transaction80Date = transaction80Cal.getTime();
		transaction80.setDate(transaction80Date);	
		transaction80.setAmount(1538.00);
		transactionRepository.save(transaction80);
		
		Transaction transaction81 = new Transaction();
		transaction81.setSubcategory(subcategorySample4);
		transaction81.setAccount(account1);
		transaction81.setType("expense");
		transaction81.setMemo("dinner in restaurant");
		Calendar transaction81Cal = Calendar.getInstance();
		transaction81Cal.add(Calendar.MONTH, -47); 
		Date transaction81Date = transaction81Cal.getTime();
		transaction81.setDate(transaction81Date);	
		transaction81.setAmount(1025.00);
		transactionRepository.save(transaction81);
		
		Transaction transaction82 = new Transaction();
		transaction82.setSubcategory(subcategorySample7);
		transaction82.setAccount(account1);
		transaction82.setType("expense");
		transaction82.setMemo("bills");
		Calendar transaction82Cal = Calendar.getInstance();
		transaction82Cal.add(Calendar.MONTH, -47); 
		Date transaction82Date = transaction82Cal.getTime();
		transaction82.setDate(transaction82Date);	
		transaction82.setAmount(1538.00);
		transactionRepository.save(transaction82);
		
		Transaction transaction83 = new Transaction();
		transaction83.setSubcategory(subcategorySample9);
		transaction83.setAccount(account1);
		transaction83.setType("expense");
		transaction83.setMemo("car fuel");
		Calendar transaction83Cal = Calendar.getInstance();
		transaction83Cal.add(Calendar.MONTH, -47); 
		Date transaction83Date = transaction83Cal.getTime();
		transaction83.setDate(transaction83Date);	
		transaction83.setAmount(1025.00);
		transactionRepository.save(transaction83);
		
		Transaction transaction84 = new Transaction();
		transaction84.setSubcategory(subcategorySample5);
		transaction84.setAccount(account1);
		transaction84.setType("expense");
		transaction84.setMemo("barber");
		Calendar transaction84Cal = Calendar.getInstance();
		transaction84Cal.add(Calendar.MONTH, -47); 
		Date transaction84Date = transaction84Cal.getTime();
		transaction84.setDate(transaction84Date);	
		transaction84.setAmount(15.00);
		transactionRepository.save(transaction84);
		
		// 46 months before
		
		Transaction transaction85 = new Transaction();
		transaction85.setSubcategory(subcategorySample1);
		transaction85.setAccount(account1);
		transaction85.setType("income");
		transaction85.setMemo("monthly salary");
		Calendar transaction85Cal = Calendar.getInstance();
		transaction85Cal.add(Calendar.MONTH, -46); 
		Date transaction85Date = transaction85Cal.getTime();
		transaction85.setDate(transaction85Date);	
		transaction85.setAmount(3285.0);
		transactionRepository.save(transaction85);
				
		Transaction transaction86 = new Transaction();
		transaction86.setSubcategory(subcategorySample3);
		transaction86.setAccount(account1);
		transaction86.setType("expense");
		transaction86.setMemo("shopping in supermarket");
		Calendar transaction86Cal = Calendar.getInstance();
		transaction86Cal.add(Calendar.MONTH, -46); 
		Date transaction86Date = transaction86Cal.getTime();
		transaction86.setDate(transaction86Date);	
		transaction86.setAmount(953.00);
		transactionRepository.save(transaction86);
				
		Transaction transaction87 = new Transaction();
		transaction87.setSubcategory(subcategorySample4);
		transaction87.setAccount(account1);
		transaction87.setType("expense");
		transaction87.setMemo("dinner in restaurant");
		Calendar transaction87Cal = Calendar.getInstance();
		transaction87Cal.add(Calendar.MONTH, -46); 
		Date transaction87Date = transaction87Cal.getTime();
		transaction87.setDate(transaction87Date);	
		transaction87.setAmount(635.00);
		transactionRepository.save(transaction87);
				
		Transaction transaction88 = new Transaction();
		transaction88.setSubcategory(subcategorySample7);
		transaction88.setAccount(account1);
		transaction88.setType("expense");
		transaction88.setMemo("bills");
		Calendar transaction88Cal = Calendar.getInstance();
		transaction88Cal.add(Calendar.MONTH, -46); 
		Date transaction88Date = transaction88Cal.getTime();
		transaction88.setDate(transaction88Date);	
		transaction88.setAmount(1270.00);
		transactionRepository.save(transaction88);
				
		Transaction transaction89 = new Transaction();
		transaction89.setSubcategory(subcategorySample9);
		transaction89.setAccount(account1);
		transaction89.setType("expense");
		transaction89.setMemo("car fuel");
		Calendar transaction89Cal = Calendar.getInstance();
		transaction89Cal.add(Calendar.MONTH, -46); 
		Date transaction89Date = transaction89Cal.getTime();
		transaction89.setDate(transaction89Date);	
		transaction89.setAmount(318.00);
		transactionRepository.save(transaction89);
				
		Transaction transaction90 = new Transaction();
		transaction90.setSubcategory(subcategorySample5);
		transaction90.setAccount(account1);
		transaction90.setType("expense");
		transaction90.setMemo("barber");
		Calendar transaction90Cal = Calendar.getInstance();
		transaction90Cal.add(Calendar.MONTH, -46); 
		Date transaction90Date = transaction90Cal.getTime();
		transaction90.setDate(transaction90Date);	
		transaction90.setAmount(15.00);
		transactionRepository.save(transaction90);
		
		// 45 months before
		
		Transaction transaction91 = new Transaction();
		transaction91.setSubcategory(subcategorySample1);
		transaction91.setAccount(account1);
		transaction91.setType("income");
		transaction91.setMemo("monthly salary");
		Calendar transaction91Cal = Calendar.getInstance();
		transaction91Cal.add(Calendar.MONTH, -45); 
		Date transaction91Date = transaction91Cal.getTime();
		transaction91.setDate(transaction91Date);	
		transaction91.setAmount(3322.0);
		transactionRepository.save(transaction91);
				
		Transaction transaction92 = new Transaction();
		transaction92.setSubcategory(subcategorySample3);
		transaction92.setAccount(account1);
		transaction92.setType("expense");
		transaction92.setMemo("shopping in supermarket");
		Calendar transaction92Cal = Calendar.getInstance();
		transaction92Cal.add(Calendar.MONTH, -45); 
		Date transaction92Date = transaction92Cal.getTime();
		transaction92.setDate(transaction92Date);	
		transaction92.setAmount(964.00);
		transactionRepository.save(transaction92);
				
		Transaction transaction93 = new Transaction();
		transaction93.setSubcategory(subcategorySample4);
		transaction93.setAccount(account1);
		transaction93.setType("expense");
		transaction93.setMemo("dinner in restaurant");
		Calendar transaction93Cal = Calendar.getInstance();
		transaction93Cal.add(Calendar.MONTH, -45); 
		Date transaction93Date = transaction93Cal.getTime();
		transaction93.setDate(transaction93Date);	
		transaction93.setAmount(643.00);
		transactionRepository.save(transaction93);
				
		Transaction transaction94 = new Transaction();
		transaction94.setSubcategory(subcategorySample7);
		transaction94.setAccount(account1);
		transaction94.setType("expense");
		transaction94.setMemo("bills");
		Calendar transaction94Cal = Calendar.getInstance();
		transaction94Cal.add(Calendar.MONTH, -45); 
		Date transaction94Date = transaction94Cal.getTime();
		transaction94.setDate(transaction94Date);	
		transaction94.setAmount(1285.00);
		transactionRepository.save(transaction94);
				
		Transaction transaction95 = new Transaction();
		transaction95.setSubcategory(subcategorySample9);
		transaction95.setAccount(account1);
		transaction95.setType("expense");
		transaction95.setMemo("car fuel");
		Calendar transaction95Cal = Calendar.getInstance();
		transaction95Cal.add(Calendar.MONTH, -45); 
		Date transaction95Date = transaction95Cal.getTime();
		transaction95.setDate(transaction95Date);	
		transaction95.setAmount(321.00);
		transactionRepository.save(transaction95);
				
		Transaction transaction96 = new Transaction();
		transaction96.setSubcategory(subcategorySample5);
		transaction96.setAccount(account1);
		transaction96.setType("expense");
		transaction96.setMemo("barber");
		Calendar transaction96Cal = Calendar.getInstance();
		transaction96Cal.add(Calendar.MONTH, -45); 
		Date transaction96Date = transaction96Cal.getTime();
		transaction96.setDate(transaction96Date);	
		transaction96.setAmount(15.00);
		transactionRepository.save(transaction96);
		
		// 44 months before
		
		Transaction transaction97 = new Transaction();
		transaction97.setSubcategory(subcategorySample1);
		transaction97.setAccount(account1);
		transaction97.setType("income");
		transaction97.setMemo("monthly salary");
		Calendar transaction97Cal = Calendar.getInstance();
		transaction97Cal.add(Calendar.MONTH, -44); 
		Date transaction97Date = transaction97Cal.getTime();
		transaction97.setDate(transaction97Date);	
		transaction97.setAmount(4393.75);
		transactionRepository.save(transaction97);
				
		Transaction transaction98 = new Transaction();
		transaction98.setSubcategory(subcategorySample3);
		transaction98.setAccount(account1);
		transaction98.setType("expense");
		transaction98.setMemo("shopping in supermarket");
		Calendar transaction98Cal = Calendar.getInstance();
		transaction98Cal.add(Calendar.MONTH, -44); 
		Date transaction98Date = transaction98Cal.getTime();
		transaction98.setDate(transaction98Date);	
		transaction98.setAmount(1048.00);
		transactionRepository.save(transaction98);
				
		Transaction transaction99 = new Transaction();
		transaction99.setSubcategory(subcategorySample4);
		transaction99.setAccount(account1);
		transaction99.setType("expense");
		transaction99.setMemo("dinner in restaurant");
		Calendar transaction99Cal = Calendar.getInstance();
		transaction99Cal.add(Calendar.MONTH, -44); 
		Date transaction99Date = transaction99Cal.getTime();
		transaction99.setDate(transaction99Date);	
		transaction99.setAmount(699.00);
		transactionRepository.save(transaction99);
				
		Transaction transaction100 = new Transaction();
		transaction100.setSubcategory(subcategorySample7);
		transaction100.setAccount(account1);
		transaction100.setType("expense");
		transaction100.setMemo("bills");
		Calendar transaction100Cal = Calendar.getInstance();
		transaction100Cal.add(Calendar.MONTH, -44); 
		Date transaction100Date = transaction100Cal.getTime();
		transaction100.setDate(transaction100Date);	
		transaction100.setAmount(1398.00);
		transactionRepository.save(transaction100);
				
		Transaction transaction101 = new Transaction();
		transaction101.setSubcategory(subcategorySample9);
		transaction101.setAccount(account1);
		transaction101.setType("expense");
		transaction101.setMemo("car fuel");
		Calendar transaction101Cal = Calendar.getInstance();
		transaction101Cal.add(Calendar.MONTH, -44); 
		Date transaction101Date = transaction101Cal.getTime();
		transaction101.setDate(transaction101Date);	
		transaction101.setAmount(349.00);
		transactionRepository.save(transaction101);
				
		Transaction transaction102 = new Transaction();
		transaction102.setSubcategory(subcategorySample5);
		transaction102.setAccount(account1);
		transaction102.setType("expense");
		transaction102.setMemo("barber");
		Calendar transaction102Cal = Calendar.getInstance();
		transaction102Cal.add(Calendar.MONTH, -44); 
		Date transaction102Date = transaction102Cal.getTime();
		transaction102.setDate(transaction102Date);	
		transaction102.setAmount(15.00);
		transactionRepository.save(transaction102);
		
		// 43 months before
		
		Transaction transaction103 = new Transaction();
		transaction103.setSubcategory(subcategorySample1);
		transaction103.setAccount(account1);
		transaction103.setType("income");
		transaction103.setMemo("monthly salary");
		Calendar transaction103Cal = Calendar.getInstance();
		transaction103Cal.add(Calendar.MONTH, -43); 
		Date transaction103Date = transaction103Cal.getTime();
		transaction103.setDate(transaction103Date);	
		transaction103.setAmount(4377.25);
		transactionRepository.save(transaction103);
				
		Transaction transaction104 = new Transaction();
		transaction104.setSubcategory(subcategorySample3);
		transaction104.setAccount(account1);
		transaction104.setType("expense");
		transaction104.setMemo("shopping in supermarket");
		Calendar transaction104Cal = Calendar.getInstance();
		transaction104Cal.add(Calendar.MONTH, -43); 
		Date transaction104Date = transaction104Cal.getTime();
		transaction104.setDate(transaction104Date);	
		transaction104.setAmount(1174.00);
		transactionRepository.save(transaction104);
				
		Transaction transaction105 = new Transaction();
		transaction105.setSubcategory(subcategorySample4);
		transaction105.setAccount(account1);
		transaction105.setType("expense");
		transaction105.setMemo("dinner in restaurant");
		Calendar transaction105Cal = Calendar.getInstance();
		transaction105Cal.add(Calendar.MONTH, -43); 
		Date transaction105Date = transaction105Cal.getTime();
		transaction105.setDate(transaction105Date);	
		transaction105.setAmount(783.00);
		transactionRepository.save(transaction105);
				
		Transaction transaction106 = new Transaction();
		transaction106.setSubcategory(subcategorySample7);
		transaction106.setAccount(account1);
		transaction106.setType("expense");
		transaction106.setMemo("bills");
		Calendar transaction106Cal = Calendar.getInstance();
		transaction106Cal.add(Calendar.MONTH, -43); 
		Date transaction106Date = transaction106Cal.getTime();
		transaction106.setDate(transaction106Date);	
		transaction106.setAmount(1566.00);
		transactionRepository.save(transaction106);
				
		Transaction transaction107 = new Transaction();
		transaction107.setSubcategory(subcategorySample9);
		transaction107.setAccount(account1);
		transaction107.setType("expense");
		transaction107.setMemo("car fuel");
		Calendar transaction107Cal = Calendar.getInstance();
		transaction107Cal.add(Calendar.MONTH, -43); 
		Date transaction107Date = transaction107Cal.getTime();
		transaction107.setDate(transaction107Date);	
		transaction107.setAmount(391.00);
		transactionRepository.save(transaction107);
				
		Transaction transaction108 = new Transaction();
		transaction108.setSubcategory(subcategorySample5);
		transaction108.setAccount(account1);
		transaction108.setType("expense");
		transaction108.setMemo("barber");
		Calendar transaction108Cal = Calendar.getInstance();
		transaction108Cal.add(Calendar.MONTH, -43); 
		Date transaction108Date = transaction108Cal.getTime();
		transaction108.setDate(transaction108Date);	
		transaction108.setAmount(15.00);
		transactionRepository.save(transaction108);
		
		// 42 months before
		
		Transaction transaction109 = new Transaction();
		transaction109.setSubcategory(subcategorySample1);
		transaction109.setAccount(account1);
		transaction109.setType("income");
		transaction109.setMemo("monthly salary");
		Calendar transaction109Cal = Calendar.getInstance();
		transaction109Cal.add(Calendar.MONTH, -42); 
		Date transaction109Date = transaction109Cal.getTime();
		transaction109.setDate(transaction109Date);	
		transaction109.setAmount(3469.25);
		transactionRepository.save(transaction109);
				
		Transaction transaction110 = new Transaction();
		transaction110.setSubcategory(subcategorySample3);
		transaction110.setAccount(account1);
		transaction110.setType("expense");
		transaction110.setMemo("shopping in supermarket");
		Calendar transaction110Cal = Calendar.getInstance();
		transaction110Cal.add(Calendar.MONTH, -42); 
		Date transaction110Date = transaction110Cal.getTime();
		transaction110.setDate(transaction110Date);	
		transaction110.setAmount(1009.00);
		transactionRepository.save(transaction110);
				
		Transaction transaction111 = new Transaction();
		transaction111.setSubcategory(subcategorySample4);
		transaction111.setAccount(account1);
		transaction111.setType("expense");
		transaction111.setMemo("dinner in restaurant");
		Calendar transaction111Cal = Calendar.getInstance();
		transaction111Cal.add(Calendar.MONTH, -42); 
		Date transaction111Date = transaction111Cal.getTime();
		transaction111.setDate(transaction111Date);	
		transaction111.setAmount(673.00);
		transactionRepository.save(transaction111);
				
		Transaction transaction112 = new Transaction();
		transaction112.setSubcategory(subcategorySample7);
		transaction112.setAccount(account1);
		transaction112.setType("expense");
		transaction112.setMemo("bills");
		Calendar transaction112Cal = Calendar.getInstance();
		transaction112Cal.add(Calendar.MONTH, -42); 
		Date transaction112Date = transaction112Cal.getTime();
		transaction112.setDate(transaction112Date);	
		transaction112.setAmount(1345.00);
		transactionRepository.save(transaction112);
				
		Transaction transaction113 = new Transaction();
		transaction113.setSubcategory(subcategorySample9);
		transaction113.setAccount(account1);
		transaction113.setType("expense");
		transaction113.setMemo("car fuel");
		Calendar transaction113Cal = Calendar.getInstance();
		transaction113Cal.add(Calendar.MONTH, -42); 
		Date transaction113Date = transaction113Cal.getTime();
		transaction113.setDate(transaction113Date);	
		transaction113.setAmount(336.00);
		transactionRepository.save(transaction113);
				
		Transaction transaction114 = new Transaction();
		transaction114.setSubcategory(subcategorySample5);
		transaction114.setAccount(account1);
		transaction114.setType("expense");
		transaction114.setMemo("barber");
		Calendar transaction114Cal = Calendar.getInstance();
		transaction114Cal.add(Calendar.MONTH, -42); 
		Date transaction114Date = transaction114Cal.getTime();
		transaction114.setDate(transaction114Date);	
		transaction114.setAmount(15.00);
		transactionRepository.save(transaction114);
		
		// 41 months before
		
		Transaction transaction115 = new Transaction();
		transaction115.setSubcategory(subcategorySample1);
		transaction115.setAccount(account1);
		transaction115.setType("income");
		transaction115.setMemo("monthly salary");
		Calendar transaction115Cal = Calendar.getInstance();
		transaction115Cal.add(Calendar.MONTH, -41); 
		Date transaction115Date = transaction115Cal.getTime();
		transaction115.setDate(transaction115Date);	
		transaction115.setAmount(3193.50);
		transactionRepository.save(transaction115);
				
		Transaction transaction116 = new Transaction();
		transaction116.setSubcategory(subcategorySample3);
		transaction116.setAccount(account1);
		transaction116.setType("expense");
		transaction116.setMemo("shopping in supermarket");
		Calendar transaction116Cal = Calendar.getInstance();
		transaction116Cal.add(Calendar.MONTH, -41); 
		Date transaction116Date = transaction116Cal.getTime();
		transaction116.setDate(transaction116Date);	
		transaction116.setAmount(902.00);
		transactionRepository.save(transaction116);
				
		Transaction transaction117 = new Transaction();
		transaction117.setSubcategory(subcategorySample4);
		transaction117.setAccount(account1);
		transaction117.setType("expense");
		transaction117.setMemo("dinner in restaurant");
		Calendar transaction117Cal = Calendar.getInstance();
		transaction117Cal.add(Calendar.MONTH, -41); 
		Date transaction117Date = transaction117Cal.getTime();
		transaction117.setDate(transaction117Date);	
		transaction117.setAmount(601.00);
		transactionRepository.save(transaction117);
				
		Transaction transaction118 = new Transaction();
		transaction118.setSubcategory(subcategorySample7);
		transaction118.setAccount(account1);
		transaction118.setType("expense");
		transaction118.setMemo("bills");
		Calendar transaction118Cal = Calendar.getInstance();
		transaction118Cal.add(Calendar.MONTH, -41); 
		Date transaction118Date = transaction118Cal.getTime();
		transaction118.setDate(transaction118Date);	
		transaction118.setAmount(1202.00);
		transactionRepository.save(transaction118);
				
		Transaction transaction119 = new Transaction();
		transaction119.setSubcategory(subcategorySample9);
		transaction119.setAccount(account1);
		transaction119.setType("expense");
		transaction119.setMemo("car fuel");
		Calendar transaction119Cal = Calendar.getInstance();
		transaction119Cal.add(Calendar.MONTH, -41); 
		Date transaction119Date = transaction119Cal.getTime();
		transaction119.setDate(transaction119Date);	
		transaction119.setAmount(301.00);
		transactionRepository.save(transaction119);
				
		Transaction transaction120 = new Transaction();
		transaction120.setSubcategory(subcategorySample5);
		transaction120.setAccount(account1);
		transaction120.setType("expense");
		transaction120.setMemo("barber");
		Calendar transaction120Cal = Calendar.getInstance();
		transaction120Cal.add(Calendar.MONTH, -41); 
		Date transaction120Date = transaction120Cal.getTime();
		transaction120.setDate(transaction120Date);	
		transaction120.setAmount(15.00);
		transactionRepository.save(transaction120);
		
		// 40 months before
		
		Transaction transaction121 = new Transaction();
		transaction121.setSubcategory(subcategorySample1);
		transaction121.setAccount(account1);
		transaction121.setType("income");
		transaction121.setMemo("monthly salary");
		Calendar transaction121Cal = Calendar.getInstance();
		transaction121Cal.add(Calendar.MONTH, -40);
		Date transaction121Date = transaction121Cal.getTime();
		transaction121.setDate(transaction121Date);
		transaction121.setAmount(3787.50);
		transactionRepository.save(transaction121);

		Transaction transaction122 = new Transaction();
		transaction122.setSubcategory(subcategorySample3);
		transaction122.setAccount(account1);
		transaction122.setType("expense");
		transaction122.setMemo("shopping in supermarket");
		Calendar transaction122Cal = Calendar.getInstance();
		transaction122Cal.add(Calendar.MONTH, -40);
		Date transaction122Date = transaction122Cal.getTime();
		transaction122.setDate(transaction122Date);
		transaction122.setAmount(1121.00);
		transactionRepository.save(transaction122);

		Transaction transaction123 = new Transaction();
		transaction123.setSubcategory(subcategorySample4);
		transaction123.setAccount(account1);
		transaction123.setType("expense");
		transaction123.setMemo("dinner in restaurant");
		Calendar transaction123Cal = Calendar.getInstance();
		transaction123Cal.add(Calendar.MONTH, -40);
		Date transaction123Date = transaction123Cal.getTime();
		transaction123.setDate(transaction123Date);
		transaction123.setAmount(747.00);
		transactionRepository.save(transaction123);

		Transaction transaction124 = new Transaction();
		transaction124.setSubcategory(subcategorySample7);
		transaction124.setAccount(account1);
		transaction124.setType("expense");
		transaction124.setMemo("bills");
		Calendar transaction124Cal = Calendar.getInstance();
		transaction124Cal.add(Calendar.MONTH, -40);
		Date transaction124Date = transaction124Cal.getTime();
		transaction124.setDate(transaction124Date);
		transaction124.setAmount(1494.00);
		transactionRepository.save(transaction124);

		Transaction transaction125 = new Transaction();
		transaction125.setSubcategory(subcategorySample9);
		transaction125.setAccount(account1);
		transaction125.setType("expense");
		transaction125.setMemo("car fuel");
		Calendar transaction125Cal = Calendar.getInstance();
		transaction125Cal.add(Calendar.MONTH, -40);
		Date transaction125Date = transaction125Cal.getTime();
		transaction125.setDate(transaction125Date);
		transaction125.setAmount(374.00);
		transactionRepository.save(transaction125);

		Transaction transaction126 = new Transaction();
		transaction126.setSubcategory(subcategorySample5);
		transaction126.setAccount(account1);
		transaction126.setType("expense");
		transaction126.setMemo("barber");
		Calendar transaction126Cal = Calendar.getInstance();
		transaction126Cal.add(Calendar.MONTH, -40);
		Date transaction126Date = transaction126Cal.getTime();
		transaction126.setDate(transaction126Date);
		transaction126.setAmount(15.00);
		transactionRepository.save(transaction126);
		
		
		// 39 months before
		
		Transaction transaction127 = new Transaction();
		transaction127.setSubcategory(subcategorySample1);
		transaction127.setAccount(account1);
		transaction127.setType("income");
		transaction127.setMemo("monthly salary");
		Calendar transaction127Cal = Calendar.getInstance();
		transaction127Cal.add(Calendar.MONTH, -39);
		Date transaction127Date = transaction127Cal.getTime();
		transaction127.setDate(transaction127Date);
		transaction127.setAmount(3795.50);
		transactionRepository.save(transaction127);

		Transaction transaction128 = new Transaction();
		transaction128.setSubcategory(subcategorySample3);
		transaction128.setAccount(account1);
		transaction128.setType("expense");
		transaction128.setMemo("shopping in supermarket");
		Calendar transaction128Cal = Calendar.getInstance();
		transaction128Cal.add(Calendar.MONTH, -39);
		Date transaction128Date = transaction128Cal.getTime();
		transaction128.setDate(transaction128Date);
		transaction128.setAmount(1103.00);
		transactionRepository.save(transaction128);

		Transaction transaction129 = new Transaction();
		transaction129.setSubcategory(subcategorySample4);
		transaction129.setAccount(account1);
		transaction129.setType("expense");
		transaction129.setMemo("dinner in restaurant");
		Calendar transaction129Cal = Calendar.getInstance();
		transaction129Cal.add(Calendar.MONTH, -39);
		Date transaction129Date = transaction129Cal.getTime();
		transaction129.setDate(transaction129Date);
		transaction129.setAmount(736.00);
		transactionRepository.save(transaction129);

		Transaction transaction130 = new Transaction();
		transaction130.setSubcategory(subcategorySample7);
		transaction130.setAccount(account1);
		transaction130.setType("expense");
		transaction130.setMemo("bills");
		Calendar transaction130Cal = Calendar.getInstance();
		transaction130Cal.add(Calendar.MONTH, -39);
		Date transaction130Date = transaction130Cal.getTime();
		transaction130.setDate(transaction130Date);
		transaction130.setAmount(1471.00);
		transactionRepository.save(transaction130);

		Transaction transaction131 = new Transaction();
		transaction131.setSubcategory(subcategorySample9);
		transaction131.setAccount(account1);
		transaction131.setType("expense");
		transaction131.setMemo("car fuel");
		Calendar transaction131Cal = Calendar.getInstance();
		transaction131Cal.add(Calendar.MONTH, -39);
		Date transaction131Date = transaction131Cal.getTime();
		transaction131.setDate(transaction131Date);
		transaction131.setAmount(368.00);
		transactionRepository.save(transaction131);

		Transaction transaction132 = new Transaction();
		transaction132.setSubcategory(subcategorySample5);
		transaction132.setAccount(account1);
		transaction132.setType("expense");
		transaction132.setMemo("barber");
		Calendar transaction132Cal = Calendar.getInstance();
		transaction132Cal.add(Calendar.MONTH, -39);
		Date transaction132Date = transaction132Cal.getTime();
		transaction132.setDate(transaction132Date);
		transaction132.setAmount(15.00);
		transactionRepository.save(transaction132);
		
		// 38 months before
		
		Transaction transaction133 = new Transaction();
		transaction133.setSubcategory(subcategorySample1);
		transaction133.setAccount(account1);
		transaction133.setType("income");
		transaction133.setMemo("monthly salary");
		Calendar transaction133Cal = Calendar.getInstance();
		transaction133Cal.add(Calendar.MONTH, -38);
		Date transaction133Date = transaction133Cal.getTime();
		transaction133.setDate(transaction133Date);
		transaction133.setAmount(3564.25);
		transactionRepository.save(transaction133);

		Transaction transaction134 = new Transaction();
		transaction134.setSubcategory(subcategorySample3);
		transaction134.setAccount(account1);
		transaction134.setType("expense");
		transaction134.setMemo("shopping in supermarket");
		Calendar transaction134Cal = Calendar.getInstance();
		transaction134Cal.add(Calendar.MONTH, -38);
		Date transaction134Date = transaction134Cal.getTime();
		transaction134.setDate(transaction134Date);
		transaction134.setAmount(849.00);
		transactionRepository.save(transaction134);

		Transaction transaction135 = new Transaction();
		transaction135.setSubcategory(subcategorySample4);
		transaction135.setAccount(account1);
		transaction135.setType("expense");
		transaction135.setMemo("dinner in restaurant");
		Calendar transaction135Cal = Calendar.getInstance();
		transaction135Cal.add(Calendar.MONTH, -38);
		Date transaction135Date = transaction135Cal.getTime();
		transaction135.setDate(transaction135Date);
		transaction135.setAmount(566.00);
		transactionRepository.save(transaction135);

		Transaction transaction136 = new Transaction();
		transaction136.setSubcategory(subcategorySample7);
		transaction136.setAccount(account1);
		transaction136.setType("expense");
		transaction136.setMemo("bills");
		Calendar transaction136Cal = Calendar.getInstance();
		transaction136Cal.add(Calendar.MONTH, -38);
		Date transaction136Date = transaction136Cal.getTime();
		transaction136.setDate(transaction136Date);
		transaction136.setAmount(1132.00);
		transactionRepository.save(transaction136);

		Transaction transaction137 = new Transaction();
		transaction137.setSubcategory(subcategorySample9);
		transaction137.setAccount(account1);
		transaction137.setType("expense");
		transaction137.setMemo("car fuel");
		Calendar transaction137Cal = Calendar.getInstance();
		transaction137Cal.add(Calendar.MONTH, -38);
		Date transaction137Date = transaction137Cal.getTime();
		transaction137.setDate(transaction137Date);
		transaction137.setAmount(283.00);
		transactionRepository.save(transaction137);

		Transaction transaction138 = new Transaction();
		transaction138.setSubcategory(subcategorySample5);
		transaction138.setAccount(account1);
		transaction138.setType("expense");
		transaction138.setMemo("barber");
		Calendar transaction138Cal = Calendar.getInstance();
		transaction138Cal.add(Calendar.MONTH, -38);
		Date transaction138Date = transaction138Cal.getTime();
		transaction138.setDate(transaction138Date);
		transaction138.setAmount(15.00);
		transactionRepository.save(transaction138);
		
		// 37 months before
		
		Transaction transaction139 = new Transaction();
		transaction139.setSubcategory(subcategorySample1);
		transaction139.setAccount(account1);
		transaction139.setType("income");
		transaction139.setMemo("monthly salary");
		Calendar transaction139Cal = Calendar.getInstance();
		transaction139Cal.add(Calendar.MONTH, -37);
		Date transaction139Date = transaction139Cal.getTime();
		transaction139.setDate(transaction139Date);
		transaction139.setAmount(3845.75);
		transactionRepository.save(transaction139);

		Transaction transaction140 = new Transaction();
		transaction140.setSubcategory(subcategorySample3);
		transaction140.setAccount(account1);
		transaction140.setType("expense");
		transaction140.setMemo("shopping in supermarket");
		Calendar transaction140Cal = Calendar.getInstance();
		transaction140Cal.add(Calendar.MONTH, -37);
		Date transaction140Date = transaction140Cal.getTime();
		transaction140.setDate(transaction140Date);
		transaction140.setAmount(1104.00);
		transactionRepository.save(transaction140);

		Transaction transaction141 = new Transaction();
		transaction141.setSubcategory(subcategorySample4);
		transaction141.setAccount(account1);
		transaction141.setType("expense");
		transaction141.setMemo("dinner in restaurant");
		Calendar transaction141Cal = Calendar.getInstance();
		transaction141Cal.add(Calendar.MONTH, -37);
		Date transaction141Date = transaction141Cal.getTime();
		transaction141.setDate(transaction141Date);
		transaction141.setAmount(736.00);
		transactionRepository.save(transaction141);

		Transaction transaction142 = new Transaction();
		transaction142.setSubcategory(subcategorySample7);
		transaction142.setAccount(account1);
		transaction142.setType("expense");
		transaction142.setMemo("bills");
		Calendar transaction142Cal = Calendar.getInstance();
		transaction142Cal.add(Calendar.MONTH, -37);
		Date transaction142Date = transaction142Cal.getTime();
		transaction142.setDate(transaction142Date);
		transaction142.setAmount(1472.00);
		transactionRepository.save(transaction142);

		Transaction transaction143 = new Transaction();
		transaction143.setSubcategory(subcategorySample9);
		transaction143.setAccount(account1);
		transaction143.setType("expense");
		transaction143.setMemo("car fuel");
		Calendar transaction143Cal = Calendar.getInstance();
		transaction143Cal.add(Calendar.MONTH, -37);
		Date transaction143Date = transaction143Cal.getTime();
		transaction143.setDate(transaction143Date);
		transaction143.setAmount(368.00);
		transactionRepository.save(transaction143);

		Transaction transaction144 = new Transaction();
		transaction144.setSubcategory(subcategorySample5);
		transaction144.setAccount(account1);
		transaction144.setType("expense");
		transaction144.setMemo("barber");
		Calendar transaction144Cal = Calendar.getInstance();
		transaction144Cal.add(Calendar.MONTH, -37);
		Date transaction144Date = transaction144Cal.getTime();
		transaction144.setDate(transaction144Date);
		transaction144.setAmount(15.00);
		transactionRepository.save(transaction144);
		
		// 36 months before
		
		Transaction transaction145 = new Transaction();
		transaction145.setSubcategory(subcategorySample1);
		transaction145.setAccount(account1);
		transaction145.setType("income");
		transaction145.setMemo("monthly salary");
		Calendar transaction145Cal = Calendar.getInstance();
		transaction145Cal.add(Calendar.MONTH, -36);
		Date transaction145Date = transaction145Cal.getTime();
		transaction145.setDate(transaction145Date);
		transaction145.setAmount(4805.75);
		transactionRepository.save(transaction145);

		Transaction transaction146 = new Transaction();
		transaction146.setSubcategory(subcategorySample3);
		transaction146.setAccount(account1);
		transaction146.setType("expense");
		transaction146.setMemo("shopping in supermarket");
		Calendar transaction146Cal = Calendar.getInstance();
		transaction146Cal.add(Calendar.MONTH, -36);
		Date transaction146Date = transaction146Cal.getTime();
		transaction146.setDate(transaction146Date);
		transaction146.setAmount(1266.00);
		transactionRepository.save(transaction146);

		Transaction transaction147 = new Transaction();
		transaction147.setSubcategory(subcategorySample4);
		transaction147.setAccount(account1);
		transaction147.setType("expense");
		transaction147.setMemo("dinner in restaurant");
		Calendar transaction147Cal = Calendar.getInstance();
		transaction147Cal.add(Calendar.MONTH, -36);
		Date transaction147Date = transaction147Cal.getTime();
		transaction147.setDate(transaction147Date);
		transaction147.setAmount(844.00);
		transactionRepository.save(transaction147);

		Transaction transaction148 = new Transaction();
		transaction148.setSubcategory(subcategorySample7);
		transaction148.setAccount(account1);
		transaction148.setType("expense");
		transaction148.setMemo("bills");
		Calendar transaction148Cal = Calendar.getInstance();
		transaction148Cal.add(Calendar.MONTH, -36);
		Date transaction148Date = transaction148Cal.getTime();
		transaction148.setDate(transaction148Date);
		transaction148.setAmount(1688.00);
		transactionRepository.save(transaction148);

		Transaction transaction149 = new Transaction();
		transaction149.setSubcategory(subcategorySample9);
		transaction149.setAccount(account1);
		transaction149.setType("expense");
		transaction149.setMemo("car fuel");
		Calendar transaction149Cal = Calendar.getInstance();
		transaction149Cal.add(Calendar.MONTH, -36);
		Date transaction149Date = transaction149Cal.getTime();
		transaction149.setDate(transaction149Date);
		transaction149.setAmount(422.00);
		transactionRepository.save(transaction149);

		Transaction transaction150 = new Transaction();
		transaction150.setSubcategory(subcategorySample5);
		transaction150.setAccount(account1);
		transaction150.setType("expense");
		transaction150.setMemo("barber");
		Calendar transaction150Cal = Calendar.getInstance();
		transaction150Cal.add(Calendar.MONTH, -36);
		Date transaction150Date = transaction150Cal.getTime();
		transaction150.setDate(transaction150Date);
		transaction150.setAmount(20.00);
		transactionRepository.save(transaction150);
		
		// 35 months before
		
		Transaction transaction151 = new Transaction();
		transaction151.setSubcategory(subcategorySample1);
		transaction151.setAccount(account1);
		transaction151.setType("income");
		transaction151.setMemo("monthly salary");
		Calendar transaction151Cal = Calendar.getInstance();
		transaction151Cal.add(Calendar.MONTH, -35);
		Date transaction151Date = transaction151Cal.getTime();
		transaction151.setDate(transaction151Date);
		transaction151.setAmount(4691.50);
		transactionRepository.save(transaction151);

		Transaction transaction152 = new Transaction();
		transaction152.setSubcategory(subcategorySample3);
		transaction152.setAccount(account1);
		transaction152.setType("expense");
		transaction152.setMemo("shopping in supermarket");
		Calendar transaction152Cal = Calendar.getInstance();
		transaction152Cal.add(Calendar.MONTH, -35);
		Date transaction152Date = transaction152Cal.getTime();
		transaction152.setDate(transaction152Date);
		transaction152.setAmount(1505.00);
		transactionRepository.save(transaction152);

		Transaction transaction153 = new Transaction();
		transaction153.setSubcategory(subcategorySample4);
		transaction153.setAccount(account1);
		transaction153.setType("expense");
		transaction153.setMemo("dinner in restaurant");
		Calendar transaction153Cal = Calendar.getInstance();
		transaction153Cal.add(Calendar.MONTH, -35);
		Date transaction153Date = transaction153Cal.getTime();
		transaction153.setDate(transaction153Date);
		transaction153.setAmount(1003.00);
		transactionRepository.save(transaction153);

		Transaction transaction154 = new Transaction();
		transaction154.setSubcategory(subcategorySample7);
		transaction154.setAccount(account1);
		transaction154.setType("expense");
		transaction154.setMemo("bills");
		Calendar transaction154Cal = Calendar.getInstance();
		transaction154Cal.add(Calendar.MONTH, -35);
		Date transaction154Date = transaction154Cal.getTime();
		transaction154.setDate(transaction154Date);
		transaction154.setAmount(1505.00);
		transactionRepository.save(transaction154);

		Transaction transaction155 = new Transaction();
		transaction155.setSubcategory(subcategorySample9);
		transaction155.setAccount(account1);
		transaction155.setType("expense");
		transaction155.setMemo("car fuel");
		Calendar transaction155Cal = Calendar.getInstance();
		transaction155Cal.add(Calendar.MONTH, -35);
		Date transaction155Date = transaction155Cal.getTime();
		transaction155.setDate(transaction155Date);
		transaction155.setAmount(1003.00);
		transactionRepository.save(transaction155);

		Transaction transaction156 = new Transaction();
		transaction156.setSubcategory(subcategorySample5);
		transaction156.setAccount(account1);
		transaction156.setType("expense");
		transaction156.setMemo("barber");
		Calendar transaction156Cal = Calendar.getInstance();
		transaction156Cal.add(Calendar.MONTH, -35);
		Date transaction156Date = transaction156Cal.getTime();
		transaction156.setDate(transaction156Date);
		transaction156.setAmount(20.00);
		transactionRepository.save(transaction156);
		
		// 34 months before
		
		Transaction transaction157 = new Transaction();
		transaction157.setSubcategory(subcategorySample1);
		transaction157.setAccount(account1);
		transaction157.setType("income");
		transaction157.setMemo("monthly salary");
		Calendar transaction157Cal = Calendar.getInstance();
		transaction157Cal.add(Calendar.MONTH, -34);
		Date transaction157Date = transaction157Cal.getTime();
		transaction157.setDate(transaction157Date);
		transaction157.setAmount(3354.50);
		transactionRepository.save(transaction157);

		Transaction transaction158 = new Transaction();
		transaction158.setSubcategory(subcategorySample3);
		transaction158.setAccount(account1);
		transaction158.setType("expense");
		transaction158.setMemo("shopping in supermarket");
		Calendar transaction158Cal = Calendar.getInstance();
		transaction158Cal.add(Calendar.MONTH, -34);
		Date transaction158Date = transaction158Cal.getTime();
		transaction158.setDate(transaction158Date);
		transaction158.setAmount(976.00);
		transactionRepository.save(transaction158);

		Transaction transaction159 = new Transaction();
		transaction159.setSubcategory(subcategorySample4);
		transaction159.setAccount(account1);
		transaction159.setType("expense");
		transaction159.setMemo("dinner in restaurant");
		Calendar transaction159Cal = Calendar.getInstance();
		transaction159Cal.add(Calendar.MONTH, -34);
		Date transaction159Date = transaction159Cal.getTime();
		transaction159.setDate(transaction159Date);
		transaction159.setAmount(651.00);
		transactionRepository.save(transaction159);

		Transaction transaction160 = new Transaction();
		transaction160.setSubcategory(subcategorySample7);
		transaction160.setAccount(account1);
		transaction160.setType("expense");
		transaction160.setMemo("bills");
		Calendar transaction160Cal = Calendar.getInstance();
		transaction160Cal.add(Calendar.MONTH, -34);
		Date transaction160Date = transaction160Cal.getTime();
		transaction160.setDate(transaction160Date);
		transaction160.setAmount(1301.00);
		transactionRepository.save(transaction160);

		Transaction transaction161 = new Transaction();
		transaction161.setSubcategory(subcategorySample9);
		transaction161.setAccount(account1);
		transaction161.setType("expense");
		transaction161.setMemo("car fuel");
		Calendar transaction161Cal = Calendar.getInstance();
		transaction161Cal.add(Calendar.MONTH, -34);
		Date transaction161Date = transaction161Cal.getTime();
		transaction161.setDate(transaction161Date);
		transaction161.setAmount(325.00);
		transactionRepository.save(transaction161);

		Transaction transaction162 = new Transaction();
		transaction162.setSubcategory(subcategorySample5);
		transaction162.setAccount(account1);
		transaction162.setType("expense");
		transaction162.setMemo("barber");
		Calendar transaction162Cal = Calendar.getInstance();
		transaction162Cal.add(Calendar.MONTH, -34);
		Date transaction162Date = transaction162Cal.getTime();
		transaction162.setDate(transaction162Date);
		transaction162.setAmount(20.00);
		transactionRepository.save(transaction162);
		
		// 33 months before
		
		Transaction transaction163 = new Transaction();
		transaction163.setSubcategory(subcategorySample1);
		transaction163.setAccount(account1);
		transaction163.setType("income");
		transaction163.setMemo("monthly salary");
		Calendar transaction163Cal = Calendar.getInstance();
		transaction163Cal.add(Calendar.MONTH, -33);
		Date transaction163Date = transaction163Cal.getTime();
		transaction163.setDate(transaction163Date);
		transaction163.setAmount(3630.50);
		transactionRepository.save(transaction163);

		Transaction transaction164 = new Transaction();
		transaction164.setSubcategory(subcategorySample3);
		transaction164.setAccount(account1);
		transaction164.setType("expense");
		transaction164.setMemo("shopping in supermarket");
		Calendar transaction164Cal = Calendar.getInstance();
		transaction164Cal.add(Calendar.MONTH, -33);
		Date transaction164Date = transaction164Cal.getTime();
		transaction164.setDate(transaction164Date);
		transaction164.setAmount(1052.00);
		transactionRepository.save(transaction164);

		Transaction transaction165 = new Transaction();
		transaction165.setSubcategory(subcategorySample4);
		transaction165.setAccount(account1);
		transaction165.setType("expense");
		transaction165.setMemo("dinner in restaurant");
		Calendar transaction165Cal = Calendar.getInstance();
		transaction165Cal.add(Calendar.MONTH, -33);
		Date transaction165Date = transaction165Cal.getTime();
		transaction165.setDate(transaction165Date);
		transaction165.setAmount(701.00);
		transactionRepository.save(transaction165);

		Transaction transaction166 = new Transaction();
		transaction166.setSubcategory(subcategorySample7);
		transaction166.setAccount(account1);
		transaction166.setType("expense");
		transaction166.setMemo("bills");
		Calendar transaction166Cal = Calendar.getInstance();
		transaction166Cal.add(Calendar.MONTH, -33);
		Date transaction166Date = transaction166Cal.getTime();
		transaction166.setDate(transaction166Date);
		transaction166.setAmount(1402.00);
		transactionRepository.save(transaction166);

		Transaction transaction167 = new Transaction();
		transaction167.setSubcategory(subcategorySample9);
		transaction167.setAccount(account1);
		transaction167.setType("expense");
		transaction167.setMemo("car fuel");
		Calendar transaction167Cal = Calendar.getInstance();
		transaction167Cal.add(Calendar.MONTH, -33);
		Date transaction167Date = transaction167Cal.getTime();
		transaction167.setDate(transaction167Date);
		transaction167.setAmount(351.00);
		transactionRepository.save(transaction167);

		Transaction transaction168 = new Transaction();
		transaction168.setSubcategory(subcategorySample5);
		transaction168.setAccount(account1);
		transaction168.setType("expense");
		transaction168.setMemo("barber");
		Calendar transaction168Cal = Calendar.getInstance();
		transaction168Cal.add(Calendar.MONTH, -33);
		Date transaction168Date = transaction168Cal.getTime();
		transaction168.setDate(transaction168Date);
		transaction168.setAmount(20.00);
		transactionRepository.save(transaction168);
		
		// 32 months before
		
		Transaction transaction169 = new Transaction();
		transaction169.setSubcategory(subcategorySample1);
		transaction169.setAccount(account1);
		transaction169.setType("income");
		transaction169.setMemo("monthly salary");
		Calendar transaction169Cal = Calendar.getInstance();
		transaction169Cal.add(Calendar.MONTH, -32);
		Date transaction169Date = transaction169Cal.getTime();
		transaction169.setDate(transaction169Date);
		transaction169.setAmount(4543.75);
		transactionRepository.save(transaction169);

		Transaction transaction170 = new Transaction();
		transaction170.setSubcategory(subcategorySample3);
		transaction170.setAccount(account1);
		transaction170.setType("expense");
		transaction170.setMemo("shopping in supermarket");
		Calendar transaction170Cal = Calendar.getInstance();
		transaction170Cal.add(Calendar.MONTH, -32);
		Date transaction170Date = transaction170Cal.getTime();
		transaction170.setDate(transaction170Date);
		transaction170.setAmount(1141.00);
		transactionRepository.save(transaction170);

		Transaction transaction171 = new Transaction();
		transaction171.setSubcategory(subcategorySample4);
		transaction171.setAccount(account1);
		transaction171.setType("expense");
		transaction171.setMemo("dinner in restaurant");
		Calendar transaction171Cal = Calendar.getInstance();
		transaction171Cal.add(Calendar.MONTH, -32);
		Date transaction171Date = transaction171Cal.getTime();
		transaction171.setDate(transaction171Date);
		transaction171.setAmount(760.00);
		transactionRepository.save(transaction171);

		Transaction transaction172 = new Transaction();
		transaction172.setSubcategory(subcategorySample7);
		transaction172.setAccount(account1);
		transaction172.setType("expense");
		transaction172.setMemo("bills");
		Calendar transaction172Cal = Calendar.getInstance();
		transaction172Cal.add(Calendar.MONTH, -32);
		Date transaction172Date = transaction172Cal.getTime();
		transaction172.setDate(transaction172Date);
		transaction172.setAmount(1521.00);
		transactionRepository.save(transaction172);

		Transaction transaction173 = new Transaction();
		transaction173.setSubcategory(subcategorySample9);
		transaction173.setAccount(account1);
		transaction173.setType("expense");
		transaction173.setMemo("car fuel");
		Calendar transaction173Cal = Calendar.getInstance();
		transaction173Cal.add(Calendar.MONTH, -32);
		Date transaction173Date = transaction173Cal.getTime();
		transaction173.setDate(transaction173Date);
		transaction173.setAmount(380.00);
		transactionRepository.save(transaction173);

		Transaction transaction174 = new Transaction();
		transaction174.setSubcategory(subcategorySample5);
		transaction174.setAccount(account1);
		transaction174.setType("expense");
		transaction174.setMemo("barber");
		Calendar transaction174Cal = Calendar.getInstance();
		transaction174Cal.add(Calendar.MONTH, -32);
		Date transaction174Date = transaction174Cal.getTime();
		transaction174.setDate(transaction174Date);
		transaction174.setAmount(20.00);
		transactionRepository.save(transaction174);
		
		// 31 months before
		
		Transaction transaction175 = new Transaction();
		transaction175.setSubcategory(subcategorySample1);
		transaction175.setAccount(account1);
		transaction175.setType("income");
		transaction175.setMemo("monthly salary");
		Calendar transaction175Cal = Calendar.getInstance();
		transaction175Cal.add(Calendar.MONTH, -31);
		Date transaction175Date = transaction175Cal.getTime();
		transaction175.setDate(transaction175Date);
		transaction175.setAmount(4358.50);
		transactionRepository.save(transaction175);

		Transaction transaction176 = new Transaction();
		transaction176.setSubcategory(subcategorySample3);
		transaction176.setAccount(account1);
		transaction176.setType("expense");
		transaction176.setMemo("shopping in supermarket");
		Calendar transaction176Cal = Calendar.getInstance();
		transaction176Cal.add(Calendar.MONTH, -31);
		Date transaction176Date = transaction176Cal.getTime();
		transaction176.setDate(transaction176Date);
		transaction176.setAmount(1147.00);
		transactionRepository.save(transaction176);

		Transaction transaction177 = new Transaction();
		transaction177.setSubcategory(subcategorySample4);
		transaction177.setAccount(account1);
		transaction177.setType("expense");
		transaction177.setMemo("dinner in restaurant");
		Calendar transaction177Cal = Calendar.getInstance();
		transaction177Cal.add(Calendar.MONTH, -31);
		Date transaction177Date = transaction177Cal.getTime();
		transaction177.setDate(transaction177Date);
		transaction177.setAmount(765.00);
		transactionRepository.save(transaction177);

		Transaction transaction178 = new Transaction();
		transaction178.setSubcategory(subcategorySample7);
		transaction178.setAccount(account1);
		transaction178.setType("expense");
		transaction178.setMemo("bills");
		Calendar transaction178Cal = Calendar.getInstance();
		transaction178Cal.add(Calendar.MONTH, -31);
		Date transaction178Date = transaction178Cal.getTime();
		transaction178.setDate(transaction178Date);
		transaction178.setAmount(1529.00);
		transactionRepository.save(transaction178);

		Transaction transaction179 = new Transaction();
		transaction179.setSubcategory(subcategorySample9);
		transaction179.setAccount(account1);
		transaction179.setType("expense");
		transaction179.setMemo("car fuel");
		Calendar transaction179Cal = Calendar.getInstance();
		transaction179Cal.add(Calendar.MONTH, -31);
		Date transaction179Date = transaction179Cal.getTime();
		transaction179.setDate(transaction179Date);
		transaction179.setAmount(382.00);
		transactionRepository.save(transaction179);

		Transaction transaction180 = new Transaction();
		transaction180.setSubcategory(subcategorySample5);
		transaction180.setAccount(account1);
		transaction180.setType("expense");
		transaction180.setMemo("barber");
		Calendar transaction180Cal = Calendar.getInstance();
		transaction180Cal.add(Calendar.MONTH, -31);
		Date transaction180Date = transaction180Cal.getTime();
		transaction180.setDate(transaction180Date);
		transaction180.setAmount(20.00);
		transactionRepository.save(transaction180);
		
		// 30 months before
		
		Transaction transaction181 = new Transaction();
		transaction181.setSubcategory(subcategorySample1);
		transaction181.setAccount(account1);
		transaction181.setType("income");
		transaction181.setMemo("monthly salary");
		Calendar transaction181Cal = Calendar.getInstance();
		transaction181Cal.add(Calendar.MONTH, -30);
		Date transaction181Date = transaction181Cal.getTime();
		transaction181.setDate(transaction181Date);
		transaction181.setAmount(3806.50);
		transactionRepository.save(transaction181);

		Transaction transaction182 = new Transaction();
		transaction182.setSubcategory(subcategorySample3);
		transaction182.setAccount(account1);
		transaction182.setType("expense");
		transaction182.setMemo("shopping in supermarket");
		Calendar transaction182Cal = Calendar.getInstance();
		transaction182Cal.add(Calendar.MONTH, -30);
		Date transaction182Date = transaction182Cal.getTime();
		transaction182.setDate(transaction182Date);
		transaction182.setAmount(1010.00);
		transactionRepository.save(transaction182);

		Transaction transaction183 = new Transaction();
		transaction183.setSubcategory(subcategorySample4);
		transaction183.setAccount(account1);
		transaction183.setType("expense");
		transaction183.setMemo("dinner in restaurant");
		Calendar transaction183Cal = Calendar.getInstance();
		transaction183Cal.add(Calendar.MONTH, -30);
		Date transaction183Date = transaction183Cal.getTime();
		transaction183.setDate(transaction183Date);
		transaction183.setAmount(673.00);
		transactionRepository.save(transaction183);

		Transaction transaction184 = new Transaction();
		transaction184.setSubcategory(subcategorySample7);
		transaction184.setAccount(account1);
		transaction184.setType("expense");
		transaction184.setMemo("bills");
		Calendar transaction184Cal = Calendar.getInstance();
		transaction184Cal.add(Calendar.MONTH, -30);
		Date transaction184Date = transaction184Cal.getTime();
		transaction184.setDate(transaction184Date);
		transaction184.setAmount(1346.00);
		transactionRepository.save(transaction184);

		Transaction transaction185 = new Transaction();
		transaction185.setSubcategory(subcategorySample9);
		transaction185.setAccount(account1);
		transaction185.setType("expense");
		transaction185.setMemo("car fuel");
		Calendar transaction185Cal = Calendar.getInstance();
		transaction185Cal.add(Calendar.MONTH, -30);
		Date transaction185Date = transaction185Cal.getTime();
		transaction185.setDate(transaction185Date);
		transaction185.setAmount(337.00);
		transactionRepository.save(transaction185);

		Transaction transaction186 = new Transaction();
		transaction186.setSubcategory(subcategorySample5);
		transaction186.setAccount(account1);
		transaction186.setType("expense");
		transaction186.setMemo("barber");
		Calendar transaction186Cal = Calendar.getInstance();
		transaction186Cal.add(Calendar.MONTH, -30);
		Date transaction186Date = transaction186Cal.getTime();
		transaction186.setDate(transaction186Date);
		transaction186.setAmount(20.00);
		transactionRepository.save(transaction186);
		
		// 29 months before
		
		Transaction transaction187 = new Transaction();
		transaction187.setSubcategory(subcategorySample1);
		transaction187.setAccount(account1);
		transaction187.setType("income");
		transaction187.setMemo("monthly salary");
		Calendar transaction187Cal = Calendar.getInstance();
		transaction187Cal.add(Calendar.MONTH, -29);
		Date transaction187Date = transaction187Cal.getTime();
		transaction187.setDate(transaction187Date);
		transaction187.setAmount(3429.25);
		transactionRepository.save(transaction187);

		Transaction transaction188 = new Transaction();
		transaction188.setSubcategory(subcategorySample3);
		transaction188.setAccount(account1);
		transaction188.setType("expense");
		transaction188.setMemo("shopping in supermarket");
		Calendar transaction188Cal = Calendar.getInstance();
		transaction188Cal.add(Calendar.MONTH, -29);
		Date transaction188Date = transaction188Cal.getTime();
		transaction188.setDate(transaction188Date);
		transaction188.setAmount(982.00);
		transactionRepository.save(transaction188);

		Transaction transaction189 = new Transaction();
		transaction189.setSubcategory(subcategorySample4);
		transaction189.setAccount(account1);
		transaction189.setType("expense");
		transaction189.setMemo("dinner in restaurant");
		Calendar transaction189Cal = Calendar.getInstance();
		transaction189Cal.add(Calendar.MONTH, -29);
		Date transaction189Date = transaction189Cal.getTime();
		transaction189.setDate(transaction189Date);
		transaction189.setAmount(654.00);
		transactionRepository.save(transaction189);

		Transaction transaction190 = new Transaction();
		transaction190.setSubcategory(subcategorySample7);
		transaction190.setAccount(account1);
		transaction190.setType("expense");
		transaction190.setMemo("bills");
		Calendar transaction190Cal = Calendar.getInstance();
		transaction190Cal.add(Calendar.MONTH, -29);
		Date transaction190Date = transaction190Cal.getTime();
		transaction190.setDate(transaction190Date);
		transaction190.setAmount(1309.00);
		transactionRepository.save(transaction190);

		Transaction transaction191 = new Transaction();
		transaction191.setSubcategory(subcategorySample9);
		transaction191.setAccount(account1);
		transaction191.setType("expense");
		transaction191.setMemo("car fuel");
		Calendar transaction191Cal = Calendar.getInstance();
		transaction191Cal.add(Calendar.MONTH, -29);
		Date transaction191Date = transaction191Cal.getTime();
		transaction191.setDate(transaction191Date);
		transaction191.setAmount(327.00);
		transactionRepository.save(transaction191);

		Transaction transaction192 = new Transaction();
		transaction192.setSubcategory(subcategorySample5);
		transaction192.setAccount(account1);
		transaction192.setType("expense");
		transaction192.setMemo("barber");
		Calendar transaction192Cal = Calendar.getInstance();
		transaction192Cal.add(Calendar.MONTH, -29);
		Date transaction192Date = transaction192Cal.getTime();
		transaction192.setDate(transaction192Date);
		transaction192.setAmount(20.00);
		transactionRepository.save(transaction192);
		
		// 28 months before
		
		Transaction transaction193 = new Transaction();
		transaction193.setSubcategory(subcategorySample1);
		transaction193.setAccount(account1);
		transaction193.setType("income");
		transaction193.setMemo("monthly salary");
		Calendar transaction193Cal = Calendar.getInstance();
		transaction193Cal.add(Calendar.MONTH, -28);
		Date transaction193Date = transaction193Cal.getTime();
		transaction193.setDate(transaction193Date);
		transaction193.setAmount(3708.50);
		transactionRepository.save(transaction193);

		Transaction transaction194 = new Transaction();
		transaction194.setSubcategory(subcategorySample3);
		transaction194.setAccount(account1);
		transaction194.setType("expense");
		transaction194.setMemo("shopping in supermarket");
		Calendar transaction194Cal = Calendar.getInstance();
		transaction194Cal.add(Calendar.MONTH, -28);
		Date transaction194Date = transaction194Cal.getTime();
		transaction194.setDate(transaction194Date);
		transaction194.setAmount(1079.00);
		transactionRepository.save(transaction194);

		Transaction transaction195 = new Transaction();
		transaction195.setSubcategory(subcategorySample4);
		transaction195.setAccount(account1);
		transaction195.setType("expense");
		transaction195.setMemo("dinner in restaurant");
		Calendar transaction195Cal = Calendar.getInstance();
		transaction195Cal.add(Calendar.MONTH, -28);
		Date transaction195Date = transaction195Cal.getTime();
		transaction195.setDate(transaction195Date);
		transaction195.setAmount(719.00);
		transactionRepository.save(transaction195);

		Transaction transaction196 = new Transaction();
		transaction196.setSubcategory(subcategorySample7);
		transaction196.setAccount(account1);
		transaction196.setType("expense");
		transaction196.setMemo("bills");
		Calendar transaction196Cal = Calendar.getInstance();
		transaction196Cal.add(Calendar.MONTH, -28);
		Date transaction196Date = transaction196Cal.getTime();
		transaction196.setDate(transaction196Date);
		transaction196.setAmount(1439.00);
		transactionRepository.save(transaction196);

		Transaction transaction197 = new Transaction();
		transaction197.setSubcategory(subcategorySample9);
		transaction197.setAccount(account1);
		transaction197.setType("expense");
		transaction197.setMemo("car fuel");
		Calendar transaction197Cal = Calendar.getInstance();
		transaction197Cal.add(Calendar.MONTH, -28);
		Date transaction197Date = transaction197Cal.getTime();
		transaction197.setDate(transaction197Date);
		transaction197.setAmount(360.00);
		transactionRepository.save(transaction197);

		Transaction transaction198 = new Transaction();
		transaction198.setSubcategory(subcategorySample5);
		transaction198.setAccount(account1);
		transaction198.setType("expense");
		transaction198.setMemo("barber");
		Calendar transaction198Cal = Calendar.getInstance();
		transaction198Cal.add(Calendar.MONTH, -28);
		Date transaction198Date = transaction198Cal.getTime();
		transaction198.setDate(transaction198Date);
		transaction198.setAmount(20.00);
		transactionRepository.save(transaction198);
		
		// 27 months before
		
		Transaction transaction199 = new Transaction();
		transaction199.setSubcategory(subcategorySample1);
		transaction199.setAccount(account1);
		transaction199.setType("income");
		transaction199.setMemo("monthly salary");
		Calendar transaction199Cal = Calendar.getInstance();
		transaction199Cal.add(Calendar.MONTH, -27);
		Date transaction199Date = transaction199Cal.getTime();
		transaction199.setDate(transaction199Date);
		transaction199.setAmount(4690.0);
		transactionRepository.save(transaction199);

		Transaction transaction200 = new Transaction();
		transaction200.setSubcategory(subcategorySample3);
		transaction200.setAccount(account1);
		transaction200.setType("expense");
		transaction200.setMemo("shopping in supermarket");
		Calendar transaction200Cal = Calendar.getInstance();
		transaction200Cal.add(Calendar.MONTH, -27);
		Date transaction200Date = transaction200Cal.getTime();
		transaction200.setDate(transaction200Date);
		transaction200.setAmount(1293.00);
		transactionRepository.save(transaction200);

		Transaction transaction201 = new Transaction();
		transaction201.setSubcategory(subcategorySample4);
		transaction201.setAccount(account1);
		transaction201.setType("expense");
		transaction201.setMemo("dinner in restaurant");
		Calendar transaction201Cal = Calendar.getInstance();
		transaction201Cal.add(Calendar.MONTH, -27);
		Date transaction201Date = transaction201Cal.getTime();
		transaction201.setDate(transaction201Date);
		transaction201.setAmount(862.00);
		transactionRepository.save(transaction201);

		Transaction transaction202 = new Transaction();
		transaction202.setSubcategory(subcategorySample7);
		transaction202.setAccount(account1);
		transaction202.setType("expense");
		transaction202.setMemo("bills");
		Calendar transaction202Cal = Calendar.getInstance();
		transaction202Cal.add(Calendar.MONTH, -27);
		Date transaction202Date = transaction202Cal.getTime();
		transaction202.setDate(transaction202Date);
		transaction202.setAmount(1724.00);
		transactionRepository.save(transaction202);

		Transaction transaction203 = new Transaction();
		transaction203.setSubcategory(subcategorySample9);
		transaction203.setAccount(account1);
		transaction203.setType("expense");
		transaction203.setMemo("car fuel");
		Calendar transaction203Cal = Calendar.getInstance();
		transaction203Cal.add(Calendar.MONTH, -27);
		Date transaction203Date = transaction203Cal.getTime();
		transaction203.setDate(transaction203Date);
		transaction203.setAmount(431.00);
		transactionRepository.save(transaction203);

		Transaction transaction204 = new Transaction();
		transaction204.setSubcategory(subcategorySample5);
		transaction204.setAccount(account1);
		transaction204.setType("expense");
		transaction204.setMemo("barber");
		Calendar transaction204Cal = Calendar.getInstance();
		transaction204Cal.add(Calendar.MONTH, -27);
		Date transaction204Date = transaction204Cal.getTime();
		transaction204.setDate(transaction204Date);
		transaction204.setAmount(20.00);
		transactionRepository.save(transaction204);
		
		// 26 months before
		
		Transaction transaction205 = new Transaction();
		transaction205.setSubcategory(subcategorySample1);
		transaction205.setAccount(account1);
		transaction205.setType("income");
		transaction205.setMemo("monthly salary");
		Calendar transaction205Cal = Calendar.getInstance();
		transaction205Cal.add(Calendar.MONTH, -26);
		Date transaction205Date = transaction205Cal.getTime();
		transaction205.setDate(transaction205Date);
		transaction205.setAmount(3810.75);
		transactionRepository.save(transaction205);

		Transaction transaction206 = new Transaction();
		transaction206.setSubcategory(subcategorySample3);
		transaction206.setAccount(account1);
		transaction206.setType("expense");
		transaction206.setMemo("shopping in supermarket");
		Calendar transaction206Cal = Calendar.getInstance();
		transaction206Cal.add(Calendar.MONTH, -26);
		Date transaction206Date = transaction206Cal.getTime();
		transaction206.setDate(transaction206Date);
		transaction206.setAmount(965.00);
		transactionRepository.save(transaction206);

		Transaction transaction207 = new Transaction();
		transaction207.setSubcategory(subcategorySample4);
		transaction207.setAccount(account1);
		transaction207.setType("expense");
		transaction207.setMemo("dinner in restaurant");
		Calendar transaction207Cal = Calendar.getInstance();
		transaction207Cal.add(Calendar.MONTH, -26);
		Date transaction207Date = transaction207Cal.getTime();
		transaction207.setDate(transaction207Date);
		transaction207.setAmount(643.00);
		transactionRepository.save(transaction207);

		Transaction transaction208 = new Transaction();
		transaction208.setSubcategory(subcategorySample7);
		transaction208.setAccount(account1);
		transaction208.setType("expense");
		transaction208.setMemo("bills");
		Calendar transaction208Cal = Calendar.getInstance();
		transaction208Cal.add(Calendar.MONTH, -26);
		Date transaction208Date = transaction208Cal.getTime();
		transaction208.setDate(transaction208Date);
		transaction208.setAmount(1287.00);
		transactionRepository.save(transaction208);

		Transaction transaction209 = new Transaction();
		transaction209.setSubcategory(subcategorySample9);
		transaction209.setAccount(account1);
		transaction209.setType("expense");
		transaction209.setMemo("car fuel");
		Calendar transaction209Cal = Calendar.getInstance();
		transaction209Cal.add(Calendar.MONTH, -26);
		Date transaction209Date = transaction209Cal.getTime();
		transaction209.setDate(transaction209Date);
		transaction209.setAmount(322.00);
		transactionRepository.save(transaction209);

		Transaction transaction210 = new Transaction();
		transaction210.setSubcategory(subcategorySample5);
		transaction210.setAccount(account1);
		transaction210.setType("expense");
		transaction210.setMemo("barber");
		Calendar transaction210Cal = Calendar.getInstance();
		transaction210Cal.add(Calendar.MONTH, -26);
		Date transaction210Date = transaction210Cal.getTime();
		transaction210.setDate(transaction210Date);
		transaction210.setAmount(20.00);
		transactionRepository.save(transaction210);
		
		// 25 months before
		
		Transaction transaction211 = new Transaction();
		transaction211.setSubcategory(subcategorySample1);
		transaction211.setAccount(account1);
		transaction211.setType("income");
		transaction211.setMemo("monthly salary");
		Calendar transaction211Cal = Calendar.getInstance();
		transaction211Cal.add(Calendar.MONTH, -25);
		Date transaction211Date = transaction211Cal.getTime();
		transaction211.setDate(transaction211Date);
		transaction211.setAmount(3723.75);
		transactionRepository.save(transaction211);

		Transaction transaction212 = new Transaction();
		transaction212.setSubcategory(subcategorySample3);
		transaction212.setAccount(account1);
		transaction212.setType("expense");
		transaction212.setMemo("shopping in supermarket");
		Calendar transaction212Cal = Calendar.getInstance();
		transaction212Cal.add(Calendar.MONTH, -25);
		Date transaction212Date = transaction212Cal.getTime();
		transaction212.setDate(transaction212Date);
		transaction212.setAmount(1050.00);
		transactionRepository.save(transaction212);

		Transaction transaction213 = new Transaction();
		transaction213.setSubcategory(subcategorySample4);
		transaction213.setAccount(account1);
		transaction213.setType("expense");
		transaction213.setMemo("dinner in restaurant");
		Calendar transaction213Cal = Calendar.getInstance();
		transaction213Cal.add(Calendar.MONTH, -25);
		Date transaction213Date = transaction213Cal.getTime();
		transaction213.setDate(transaction213Date);
		transaction213.setAmount(700.00);
		transactionRepository.save(transaction213);

		Transaction transaction214 = new Transaction();
		transaction214.setSubcategory(subcategorySample7);
		transaction214.setAccount(account1);
		transaction214.setType("expense");
		transaction214.setMemo("bills");
		Calendar transaction214Cal = Calendar.getInstance();
		transaction214Cal.add(Calendar.MONTH, -25);
		Date transaction214Date = transaction214Cal.getTime();
		transaction214.setDate(transaction214Date);
		transaction214.setAmount(1400.00);
		transactionRepository.save(transaction214);

		Transaction transaction215 = new Transaction();
		transaction215.setSubcategory(subcategorySample9);
		transaction215.setAccount(account1);
		transaction215.setType("expense");
		transaction215.setMemo("car fuel");
		Calendar transaction215Cal = Calendar.getInstance();
		transaction215Cal.add(Calendar.MONTH, -25);
		Date transaction215Date = transaction215Cal.getTime();
		transaction215.setDate(transaction215Date);
		transaction215.setAmount(350.00);
		transactionRepository.save(transaction215);

		Transaction transaction216 = new Transaction();
		transaction216.setSubcategory(subcategorySample5);
		transaction216.setAccount(account1);
		transaction216.setType("expense");
		transaction216.setMemo("barber");
		Calendar transaction216Cal = Calendar.getInstance();
		transaction216Cal.add(Calendar.MONTH, -25);
		Date transaction216Date = transaction216Cal.getTime();
		transaction216.setDate(transaction216Date);
		transaction216.setAmount(20.00);
		transactionRepository.save(transaction216);
				
		// 24 months before
		
		Transaction transaction217 = new Transaction();
		transaction217.setSubcategory(subcategorySample1);
		transaction217.setAccount(account1);
		transaction217.setType("income");
		transaction217.setMemo("monthly salary");
		Calendar transaction217Cal = Calendar.getInstance();
		transaction217Cal.add(Calendar.MONTH, -24);
		Date transaction217Date = transaction217Cal.getTime();
		transaction217.setDate(transaction217Date);
		transaction217.setAmount(5088.08);
		transactionRepository.save(transaction217);

		Transaction transaction218 = new Transaction();
		transaction218.setSubcategory(subcategorySample3);
		transaction218.setAccount(account1);
		transaction218.setType("expense");
		transaction218.setMemo("shopping in supermarket");
		Calendar transaction218Cal = Calendar.getInstance();
		transaction218Cal.add(Calendar.MONTH, -24);
		Date transaction218Date = transaction218Cal.getTime();
		transaction218.setDate(transaction218Date);
		transaction218.setAmount(1292.00);
		transactionRepository.save(transaction218);

		Transaction transaction219 = new Transaction();
		transaction219.setSubcategory(subcategorySample4);
		transaction219.setAccount(account1);
		transaction219.setType("expense");
		transaction219.setMemo("dinner in restaurant");
		Calendar transaction219Cal = Calendar.getInstance();
		transaction219Cal.add(Calendar.MONTH, -24);
		Date transaction219Date = transaction219Cal.getTime();
		transaction219.setDate(transaction219Date);
		transaction219.setAmount(862.00);
		transactionRepository.save(transaction219);

		Transaction transaction220 = new Transaction();
		transaction220.setSubcategory(subcategorySample7);
		transaction220.setAccount(account1);
		transaction220.setType("expense");
		transaction220.setMemo("bills");
		Calendar transaction220Cal = Calendar.getInstance();
		transaction220Cal.add(Calendar.MONTH, -24);
		Date transaction220Date = transaction220Cal.getTime();
		transaction220.setDate(transaction220Date);
		transaction220.setAmount(1723.00);
		transactionRepository.save(transaction220);

		Transaction transaction221 = new Transaction();
		transaction221.setSubcategory(subcategorySample9);
		transaction221.setAccount(account1);
		transaction221.setType("expense");
		transaction221.setMemo("car fuel");
		Calendar transaction221Cal = Calendar.getInstance();
		transaction221Cal.add(Calendar.MONTH, -24);
		Date transaction221Date = transaction221Cal.getTime();
		transaction221.setDate(transaction221Date);
		transaction221.setAmount(431.00);
		transactionRepository.save(transaction221);

		Transaction transaction222 = new Transaction();
		transaction222.setSubcategory(subcategorySample5);
		transaction222.setAccount(account1);
		transaction222.setType("expense");
		transaction222.setMemo("barber");
		Calendar transaction222Cal = Calendar.getInstance();
		transaction222Cal.add(Calendar.MONTH, -24);
		Date transaction222Date = transaction222Cal.getTime();
		transaction222.setDate(transaction222Date);
		transaction222.setAmount(25.00);
		transactionRepository.save(transaction222);
		
		// 23 months before
		
		Transaction transaction223 = new Transaction();
		transaction223.setSubcategory(subcategorySample1);
		transaction223.setAccount(account1);
		transaction223.setType("income");
		transaction223.setMemo("monthly salary");
		Calendar transaction223Cal = Calendar.getInstance();
		transaction223Cal.add(Calendar.MONTH, -23);
		Date transaction223Date = transaction223Cal.getTime();
		transaction223.setDate(transaction223Date);
		transaction223.setAmount(5019.54);
		transactionRepository.save(transaction223);

		Transaction transaction224 = new Transaction();
		transaction224.setSubcategory(subcategorySample3);
		transaction224.setAccount(account1);
		transaction224.setType("expense");
		transaction224.setMemo("shopping in supermarket");
		Calendar transaction224Cal = Calendar.getInstance();
		transaction224Cal.add(Calendar.MONTH, -23);
		Date transaction224Date = transaction224Cal.getTime();
		transaction224.setDate(transaction224Date);
		transaction224.setAmount(1524.00);
		transactionRepository.save(transaction224);

		Transaction transaction225 = new Transaction();
		transaction225.setSubcategory(subcategorySample4);
		transaction225.setAccount(account1);
		transaction225.setType("expense");
		transaction225.setMemo("dinner in restaurant");
		Calendar transaction225Cal = Calendar.getInstance();
		transaction225Cal.add(Calendar.MONTH, -23);
		Date transaction225Date = transaction225Cal.getTime();
		transaction225.setDate(transaction225Date);
		transaction225.setAmount(1016.00);
		transactionRepository.save(transaction225);

		Transaction transaction226 = new Transaction();
		transaction226.setSubcategory(subcategorySample7);
		transaction226.setAccount(account1);
		transaction226.setType("expense");
		transaction226.setMemo("bills");
		Calendar transaction226Cal = Calendar.getInstance();
		transaction226Cal.add(Calendar.MONTH, -23);
		Date transaction226Date = transaction226Cal.getTime();
		transaction226.setDate(transaction226Date);
		transaction226.setAmount(1524.00);
		transactionRepository.save(transaction226);

		Transaction transaction227 = new Transaction();
		transaction227.setSubcategory(subcategorySample9);
		transaction227.setAccount(account1);
		transaction227.setType("expense");
		transaction227.setMemo("car fuel");
		Calendar transaction227Cal = Calendar.getInstance();
		transaction227Cal.add(Calendar.MONTH, -23);
		Date transaction227Date = transaction227Cal.getTime();
		transaction227.setDate(transaction227Date);
		transaction227.setAmount(1016.00);
		transactionRepository.save(transaction227);

		Transaction transaction228 = new Transaction();
		transaction228.setSubcategory(subcategorySample5);
		transaction228.setAccount(account1);
		transaction228.setType("expense");
		transaction228.setMemo("barber");
		Calendar transaction228Cal = Calendar.getInstance();
		transaction228Cal.add(Calendar.MONTH, -23);
		Date transaction228Date = transaction228Cal.getTime();
		transaction228.setDate(transaction228Date);
		transaction228.setAmount(25.00);
		transactionRepository.save(transaction228);
		
		// 22 months before
		
		Transaction transaction229 = new Transaction();
		transaction229.setSubcategory(subcategorySample1);
		transaction229.setAccount(account1);
		transaction229.setType("income");
		transaction229.setMemo("monthly salary");
		Calendar transaction229Cal = Calendar.getInstance();
		transaction229Cal.add(Calendar.MONTH, -22);
		Date transaction229Date = transaction229Cal.getTime();
		transaction229.setDate(transaction229Date);
		transaction229.setAmount(3673.06);
		transactionRepository.save(transaction229);

		Transaction transaction230 = new Transaction();
		transaction230.setSubcategory(subcategorySample3);
		transaction230.setAccount(account1);
		transaction230.setType("expense");
		transaction230.setMemo("shopping in supermarket");
		Calendar transaction230Cal = Calendar.getInstance();
		transaction230Cal.add(Calendar.MONTH, -22);
		Date transaction230Date = transaction230Cal.getTime();
		transaction230.setDate(transaction230Date);
		transaction230.setAmount(991.00);
		transactionRepository.save(transaction230);

		Transaction transaction231 = new Transaction();
		transaction231.setSubcategory(subcategorySample4);
		transaction231.setAccount(account1);
		transaction231.setType("expense");
		transaction231.setMemo("dinner in restaurant");
		Calendar transaction231Cal = Calendar.getInstance();
		transaction231Cal.add(Calendar.MONTH, -22);
		Date transaction231Date = transaction231Cal.getTime();
		transaction231.setDate(transaction231Date);
		transaction231.setAmount(661.00);
		transactionRepository.save(transaction231);

		Transaction transaction232 = new Transaction();
		transaction232.setSubcategory(subcategorySample7);
		transaction232.setAccount(account1);
		transaction232.setType("expense");
		transaction232.setMemo("bills");
		Calendar transaction232Cal = Calendar.getInstance();
		transaction232Cal.add(Calendar.MONTH, -22);
		Date transaction232Date = transaction232Cal.getTime();
		transaction232.setDate(transaction232Date);
		transaction232.setAmount(1321.00);
		transactionRepository.save(transaction232);

		Transaction transaction233 = new Transaction();
		transaction233.setSubcategory(subcategorySample9);
		transaction233.setAccount(account1);
		transaction233.setType("expense");
		transaction233.setMemo("car fuel");
		Calendar transaction233Cal = Calendar.getInstance();
		transaction233Cal.add(Calendar.MONTH, -22);
		Date transaction233Date = transaction233Cal.getTime();
		transaction233.setDate(transaction233Date);
		transaction233.setAmount(330.00);
		transactionRepository.save(transaction233);

		Transaction transaction234 = new Transaction();
		transaction234.setSubcategory(subcategorySample5);
		transaction234.setAccount(account1);
		transaction234.setType("expense");
		transaction234.setMemo("barber");
		Calendar transaction234Cal = Calendar.getInstance();
		transaction234Cal.add(Calendar.MONTH, -22);
		Date transaction234Date = transaction234Cal.getTime();
		transaction234.setDate(transaction234Date);
		transaction234.setAmount(25.00);
		transactionRepository.save(transaction234);
		
		// 21 months before
		
		Transaction transaction235 = new Transaction();
		transaction235.setSubcategory(subcategorySample1);
		transaction235.setAccount(account1);
		transaction235.setType("income");
		transaction235.setMemo("monthly salary");
		Calendar transaction235Cal = Calendar.getInstance();
		transaction235Cal.add(Calendar.MONTH, -21);
		Date transaction235Date = transaction235Cal.getTime();
		transaction235.setDate(transaction235Date);
		transaction235.setAmount(3814.09);
		transactionRepository.save(transaction235);

		Transaction transaction236 = new Transaction();
		transaction236.setSubcategory(subcategorySample3);
		transaction236.setAccount(account1);
		transaction236.setType("expense");
		transaction236.setMemo("shopping in supermarket");
		Calendar transaction236Cal = Calendar.getInstance();
		transaction236Cal.add(Calendar.MONTH, -21);
		Date transaction236Date = transaction236Cal.getTime();
		transaction236.setDate(transaction236Date);
		transaction236.setAmount(1060.00);
		transactionRepository.save(transaction236);

		Transaction transaction237 = new Transaction();
		transaction237.setSubcategory(subcategorySample4);
		transaction237.setAccount(account1);
		transaction237.setType("expense");
		transaction237.setMemo("dinner in restaurant");
		Calendar transaction237Cal = Calendar.getInstance();
		transaction237Cal.add(Calendar.MONTH, -21);
		Date transaction237Date = transaction237Cal.getTime();
		transaction237.setDate(transaction237Date);
		transaction237.setAmount(707.00);
		transactionRepository.save(transaction237);

		Transaction transaction238 = new Transaction();
		transaction238.setSubcategory(subcategorySample7);
		transaction238.setAccount(account1);
		transaction238.setType("expense");
		transaction238.setMemo("bills");
		Calendar transaction238Cal = Calendar.getInstance();
		transaction238Cal.add(Calendar.MONTH, -21);
		Date transaction238Date = transaction238Cal.getTime();
		transaction238.setDate(transaction238Date);
		transaction238.setAmount(1413.00);
		transactionRepository.save(transaction238);

		Transaction transaction239 = new Transaction();
		transaction239.setSubcategory(subcategorySample9);
		transaction239.setAccount(account1);
		transaction239.setType("expense");
		transaction239.setMemo("car fuel");
		Calendar transaction239Cal = Calendar.getInstance();
		transaction239Cal.add(Calendar.MONTH, -21);
		Date transaction239Date = transaction239Cal.getTime();
		transaction239.setDate(transaction239Date);
		transaction239.setAmount(353.00);
		transactionRepository.save(transaction239);

		Transaction transaction240 = new Transaction();
		transaction240.setSubcategory(subcategorySample5);
		transaction240.setAccount(account1);
		transaction240.setType("expense");
		transaction240.setMemo("barber");
		Calendar transaction240Cal = Calendar.getInstance();
		transaction240Cal.add(Calendar.MONTH, -21);
		Date transaction240Date = transaction240Cal.getTime();
		transaction240.setDate(transaction240Date);
		transaction240.setAmount(25.00);
		transactionRepository.save(transaction240);
		
		// 20 months before
		
		Transaction transaction241 = new Transaction();
		transaction241.setSubcategory(subcategorySample1);
		transaction241.setAccount(account1);
		transaction241.setType("income");
		transaction241.setMemo("monthly salary");
		Calendar transaction241Cal = Calendar.getInstance();
		transaction241Cal.add(Calendar.MONTH, -20);
		Date transaction241Date = transaction241Cal.getTime();
		transaction241.setDate(transaction241Date);
		transaction241.setAmount(4279.06);
		transactionRepository.save(transaction241);

		Transaction transaction242 = new Transaction();
		transaction242.setSubcategory(subcategorySample3);
		transaction242.setAccount(account1);
		transaction242.setType("expense");
		transaction242.setMemo("shopping in supermarket");
		Calendar transaction242Cal = Calendar.getInstance();
		transaction242Cal.add(Calendar.MONTH, -20);
		Date transaction242Date = transaction242Cal.getTime();
		transaction242.setDate(transaction242Date);
		transaction242.setAmount(1155.00);
		transactionRepository.save(transaction242);

		Transaction transaction243 = new Transaction();
		transaction243.setSubcategory(subcategorySample4);
		transaction243.setAccount(account1);
		transaction243.setType("expense");
		transaction243.setMemo("dinner in restaurant");
		Calendar transaction243Cal = Calendar.getInstance();
		transaction243Cal.add(Calendar.MONTH, -20);
		Date transaction243Date = transaction243Cal.getTime();
		transaction243.setDate(transaction243Date);
		transaction243.setAmount(770.00);
		transactionRepository.save(transaction243);

		Transaction transaction244 = new Transaction();
		transaction244.setSubcategory(subcategorySample7);
		transaction244.setAccount(account1);
		transaction244.setType("expense");
		transaction244.setMemo("bills");
		Calendar transaction244Cal = Calendar.getInstance();
		transaction244Cal.add(Calendar.MONTH, -20);
		Date transaction244Date = transaction244Cal.getTime();
		transaction244.setDate(transaction244Date);
		transaction244.setAmount(1539.00);
		transactionRepository.save(transaction244);

		Transaction transaction245 = new Transaction();
		transaction245.setSubcategory(subcategorySample9);
		transaction245.setAccount(account1);
		transaction245.setType("expense");
		transaction245.setMemo("car fuel");
		Calendar transaction245Cal = Calendar.getInstance();
		transaction245Cal.add(Calendar.MONTH, -20);
		Date transaction245Date = transaction245Cal.getTime();
		transaction245.setDate(transaction245Date);
		transaction245.setAmount(385.00);
		transactionRepository.save(transaction245);

		Transaction transaction246 = new Transaction();
		transaction246.setSubcategory(subcategorySample5);
		transaction246.setAccount(account1);
		transaction246.setType("expense");
		transaction246.setMemo("barber");
		Calendar transaction246Cal = Calendar.getInstance();
		transaction246Cal.add(Calendar.MONTH, -20);
		Date transaction246Date = transaction246Cal.getTime();
		transaction246.setDate(transaction246Date);
		transaction246.setAmount(25.00);
		transactionRepository.save(transaction246);
		
		// 19 months before
		
		Transaction transaction247 = new Transaction();
		transaction247.setSubcategory(subcategorySample1);
		transaction247.setAccount(account1);
		transaction247.setType("income");
		transaction247.setMemo("monthly salary");
		Calendar transaction247Cal = Calendar.getInstance();
		transaction247Cal.add(Calendar.MONTH, -19);
		Date transaction247Date = transaction247Cal.getTime();
		transaction247.setDate(transaction247Date);
		transaction247.setAmount(4273.81);
		transactionRepository.save(transaction247);

		Transaction transaction248 = new Transaction();
		transaction248.setSubcategory(subcategorySample3);
		transaction248.setAccount(account1);
		transaction248.setType("expense");
		transaction248.setMemo("shopping in supermarket");
		Calendar transaction248Cal = Calendar.getInstance();
		transaction248Cal.add(Calendar.MONTH, -19);
		Date transaction248Date = transaction248Cal.getTime();
		transaction248.setDate(transaction248Date);
		transaction248.setAmount(1185.00);
		transactionRepository.save(transaction248);

		Transaction transaction249 = new Transaction();
		transaction249.setSubcategory(subcategorySample4);
		transaction249.setAccount(account1);
		transaction249.setType("expense");
		transaction249.setMemo("dinner in restaurant");
		Calendar transaction249Cal = Calendar.getInstance();
		transaction249Cal.add(Calendar.MONTH, -19);
		Date transaction249Date = transaction249Cal.getTime();
		transaction249.setDate(transaction249Date);
		transaction249.setAmount(790.00);
		transactionRepository.save(transaction249);

		Transaction transaction250 = new Transaction();
		transaction250.setSubcategory(subcategorySample7);
		transaction250.setAccount(account1);
		transaction250.setType("expense");
		transaction250.setMemo("bills");
		Calendar transaction250Cal = Calendar.getInstance();
		transaction250Cal.add(Calendar.MONTH, -19);
		Date transaction250Date = transaction250Cal.getTime();
		transaction250.setDate(transaction250Date);
		transaction250.setAmount(1580.00);
		transactionRepository.save(transaction250);

		Transaction transaction251 = new Transaction();
		transaction251.setSubcategory(subcategorySample9);
		transaction251.setAccount(account1);
		transaction251.setType("expense");
		transaction251.setMemo("car fuel");
		Calendar transaction251Cal = Calendar.getInstance();
		transaction251Cal.add(Calendar.MONTH, -19);
		Date transaction251Date = transaction251Cal.getTime();
		transaction251.setDate(transaction251Date);
		transaction251.setAmount(395.00);
		transactionRepository.save(transaction251);

		Transaction transaction252 = new Transaction();
		transaction252.setSubcategory(subcategorySample5);
		transaction252.setAccount(account1);
		transaction252.setType("expense");
		transaction252.setMemo("barber");
		Calendar transaction252Cal = Calendar.getInstance();
		transaction252Cal.add(Calendar.MONTH, -19);
		Date transaction252Date = transaction252Cal.getTime();
		transaction252.setDate(transaction252Date);
		transaction252.setAmount(25.00);
		transactionRepository.save(transaction252);
		
	// 18 months before
		
		Transaction transaction253 = new Transaction();
		transaction253.setSubcategory(subcategorySample1);
		transaction253.setAccount(account1);
		transaction253.setType("income");
		transaction253.setMemo("monthly salary");
		Calendar transaction253Cal = Calendar.getInstance();
		transaction253Cal.add(Calendar.MONTH, -18);
		Date transaction253Date = transaction253Cal.getTime();
		transaction253.setDate(transaction253Date);
		transaction253.setAmount(3863.06);
		transactionRepository.save(transaction253);

		Transaction transaction254 = new Transaction();
		transaction254.setSubcategory(subcategorySample3);
		transaction254.setAccount(account1);
		transaction254.setType("expense");
		transaction254.setMemo("shopping in supermarket");
		Calendar transaction254Cal = Calendar.getInstance();
		transaction254Cal.add(Calendar.MONTH, -18);
		Date transaction254Date = transaction254Cal.getTime();
		transaction254.setDate(transaction254Date);
		transaction254.setAmount(1052.00);
		transactionRepository.save(transaction254);

		Transaction transaction255 = new Transaction();
		transaction255.setSubcategory(subcategorySample4);
		transaction255.setAccount(account1);
		transaction255.setType("expense");
		transaction255.setMemo("dinner in restaurant");
		Calendar transaction255Cal = Calendar.getInstance();
		transaction255Cal.add(Calendar.MONTH, -18);
		Date transaction255Date = transaction255Cal.getTime();
		transaction255.setDate(transaction255Date);
		transaction255.setAmount(702.00);
		transactionRepository.save(transaction255);

		Transaction transaction256 = new Transaction();
		transaction256.setSubcategory(subcategorySample7);
		transaction256.setAccount(account1);
		transaction256.setType("expense");
		transaction256.setMemo("bills");
		Calendar transaction256Cal = Calendar.getInstance();
		transaction256Cal.add(Calendar.MONTH, -18);
		Date transaction256Date = transaction256Cal.getTime();
		transaction256.setDate(transaction256Date);
		transaction256.setAmount(1403.00);
		transactionRepository.save(transaction256);

		Transaction transaction257 = new Transaction();
		transaction257.setSubcategory(subcategorySample9);
		transaction257.setAccount(account1);
		transaction257.setType("expense");
		transaction257.setMemo("car fuel");
		Calendar transaction257Cal = Calendar.getInstance();
		transaction257Cal.add(Calendar.MONTH, -18);
		Date transaction257Date = transaction257Cal.getTime();
		transaction257.setDate(transaction257Date);
		transaction257.setAmount(351.00);
		transactionRepository.save(transaction257);

		Transaction transaction258 = new Transaction();
		transaction258.setSubcategory(subcategorySample5);
		transaction258.setAccount(account1);
		transaction258.setType("expense");
		transaction258.setMemo("barber");
		Calendar transaction258Cal = Calendar.getInstance();
		transaction258Cal.add(Calendar.MONTH, -18);
		Date transaction258Date = transaction258Cal.getTime();
		transaction258.setDate(transaction258Date);
		transaction258.setAmount(25.00);
		transactionRepository.save(transaction258);
		
		// 17 months before
		
		Transaction transaction259 = new Transaction();
		transaction259.setSubcategory(subcategorySample1);
		transaction259.setAccount(account1);
		transaction259.setType("income");
		transaction259.setMemo("monthly salary");
		Calendar transaction259Cal = Calendar.getInstance();
		transaction259Cal.add(Calendar.MONTH, -17);
		Date transaction259Date = transaction259Cal.getTime();
		transaction259.setDate(transaction259Date);
		transaction259.setAmount(3744.06);
		transactionRepository.save(transaction259);

		Transaction transaction260 = new Transaction();
		transaction260.setSubcategory(subcategorySample3);
		transaction260.setAccount(account1);
		transaction260.setType("expense");
		transaction260.setMemo("shopping in supermarket");
		Calendar transaction260Cal = Calendar.getInstance();
		transaction260Cal.add(Calendar.MONTH, -17);
		Date transaction260Date = transaction260Cal.getTime();
		transaction260.setDate(transaction260Date);
		transaction260.setAmount(1018.00);
		transactionRepository.save(transaction260);

		Transaction transaction261 = new Transaction();
		transaction261.setSubcategory(subcategorySample4);
		transaction261.setAccount(account1);
		transaction261.setType("expense");
		transaction261.setMemo("dinner in restaurant");
		Calendar transaction261Cal = Calendar.getInstance();
		transaction261Cal.add(Calendar.MONTH, -17);
		Date transaction261Date = transaction261Cal.getTime();
		transaction261.setDate(transaction261Date);
		transaction261.setAmount(679.00);
		transactionRepository.save(transaction261);

		Transaction transaction262 = new Transaction();
		transaction262.setSubcategory(subcategorySample7);
		transaction262.setAccount(account1);
		transaction262.setType("expense");
		transaction262.setMemo("bills");
		Calendar transaction262Cal = Calendar.getInstance();
		transaction262Cal.add(Calendar.MONTH, -17);
		Date transaction262Date = transaction262Cal.getTime();
		transaction262.setDate(transaction262Date);
		transaction262.setAmount(1357.00);
		transactionRepository.save(transaction262);

		Transaction transaction263 = new Transaction();
		transaction263.setSubcategory(subcategorySample9);
		transaction263.setAccount(account1);
		transaction263.setType("expense");
		transaction263.setMemo("car fuel");
		Calendar transaction263Cal = Calendar.getInstance();
		transaction263Cal.add(Calendar.MONTH, -17);
		Date transaction263Date = transaction263Cal.getTime();
		transaction263.setDate(transaction263Date);
		transaction263.setAmount(339.00);
		transactionRepository.save(transaction263);

		Transaction transaction264 = new Transaction();
		transaction264.setSubcategory(subcategorySample5);
		transaction264.setAccount(account1);
		transaction264.setType("expense");
		transaction264.setMemo("barber");
		Calendar transaction264Cal = Calendar.getInstance();
		transaction264Cal.add(Calendar.MONTH, -17);
		Date transaction264Date = transaction264Cal.getTime();
		transaction264.setDate(transaction264Date);
		transaction264.setAmount(25.00);
		transactionRepository.save(transaction264);
		
		// 16 months before
		
		Transaction transaction265 = new Transaction();
		transaction265.setSubcategory(subcategorySample1);
		transaction265.setAccount(account1);
		transaction265.setType("income");
		transaction265.setMemo("monthly salary");
		Calendar transaction265Cal = Calendar.getInstance();
		transaction265Cal.add(Calendar.MONTH, -16);
		Date transaction265Date = transaction265Cal.getTime();
		transaction265.setDate(transaction265Date);
		transaction265.setAmount(4071.13);
		transactionRepository.save(transaction265);

		Transaction transaction266 = new Transaction();
		transaction266.setSubcategory(subcategorySample3);
		transaction266.setAccount(account1);
		transaction266.setType("expense");
		transaction266.setMemo("shopping in supermarket");
		Calendar transaction266Cal = Calendar.getInstance();
		transaction266Cal.add(Calendar.MONTH, -16);
		Date transaction266Date = transaction266Cal.getTime();
		transaction266.setDate(transaction266Date);
		transaction266.setAmount(1122.00);
		transactionRepository.save(transaction266);

		Transaction transaction267 = new Transaction();
		transaction267.setSubcategory(subcategorySample4);
		transaction267.setAccount(account1);
		transaction267.setType("expense");
		transaction267.setMemo("dinner in restaurant");
		Calendar transaction267Cal = Calendar.getInstance();
		transaction267Cal.add(Calendar.MONTH, -16);
		Date transaction267Date = transaction267Cal.getTime();
		transaction267.setDate(transaction267Date);
		transaction267.setAmount(748.00);
		transactionRepository.save(transaction267);

		Transaction transaction268 = new Transaction();
		transaction268.setSubcategory(subcategorySample7);
		transaction268.setAccount(account1);
		transaction268.setType("expense");
		transaction268.setMemo("bills");
		Calendar transaction268Cal = Calendar.getInstance();
		transaction268Cal.add(Calendar.MONTH, -16);
		Date transaction268Date = transaction268Cal.getTime();
		transaction268.setDate(transaction268Date);
		transaction268.setAmount(1496.00);
		transactionRepository.save(transaction268);

		Transaction transaction269 = new Transaction();
		transaction269.setSubcategory(subcategorySample9);
		transaction269.setAccount(account1);
		transaction269.setType("expense");
		transaction269.setMemo("car fuel");
		Calendar transaction269Cal = Calendar.getInstance();
		transaction269Cal.add(Calendar.MONTH, -16);
		Date transaction269Date = transaction269Cal.getTime();
		transaction269.setDate(transaction269Date);
		transaction269.setAmount(374.00);
		transactionRepository.save(transaction269);

		Transaction transaction270 = new Transaction();
		transaction270.setSubcategory(subcategorySample5);
		transaction270.setAccount(account1);
		transaction270.setType("expense");
		transaction270.setMemo("barber");
		Calendar transaction270Cal = Calendar.getInstance();
		transaction270Cal.add(Calendar.MONTH, -16);
		Date transaction270Date = transaction270Cal.getTime();
		transaction270.setDate(transaction270Date);
		transaction270.setAmount(25.00);
		transactionRepository.save(transaction270);
		
		// 15 months before
		
		Transaction transaction271 = new Transaction();
		transaction271.setSubcategory(subcategorySample1);
		transaction271.setAccount(account1);
		transaction271.setType("income");
		transaction271.setMemo("monthly salary");
		Calendar transaction271Cal = Calendar.getInstance();
		transaction271Cal.add(Calendar.MONTH, -15);
		Date transaction271Date = transaction271Cal.getTime();
		transaction271.setDate(transaction271Date);
		transaction271.setAmount(4694.18);
		transactionRepository.save(transaction271);

		Transaction transaction272 = new Transaction();
		transaction272.setSubcategory(subcategorySample3);
		transaction272.setAccount(account1);
		transaction272.setType("expense");
		transaction272.setMemo("shopping in supermarket");
		Calendar transaction272Cal = Calendar.getInstance();
		transaction272Cal.add(Calendar.MONTH, -15);
		Date transaction272Date = transaction272Cal.getTime();
		transaction272.setDate(transaction272Date);
		transaction272.setAmount(1307.00);
		transactionRepository.save(transaction272);

		Transaction transaction273 = new Transaction();
		transaction273.setSubcategory(subcategorySample4);
		transaction273.setAccount(account1);
		transaction273.setType("expense");
		transaction273.setMemo("dinner in restaurant");
		Calendar transaction273Cal = Calendar.getInstance();
		transaction273Cal.add(Calendar.MONTH, -15);
		Date transaction273Date = transaction273Cal.getTime();
		transaction273.setDate(transaction273Date);
		transaction273.setAmount(871);
		transactionRepository.save(transaction273);

		Transaction transaction274 = new Transaction();
		transaction274.setSubcategory(subcategorySample7);
		transaction274.setAccount(account1);
		transaction274.setType("expense");
		transaction274.setMemo("bills");
		Calendar transaction274Cal = Calendar.getInstance();
		transaction274Cal.add(Calendar.MONTH, -15);
		Date transaction274Date = transaction274Cal.getTime();
		transaction274.setDate(transaction274Date);
		transaction274.setAmount(1742.00);
		transactionRepository.save(transaction274);

		Transaction transaction275 = new Transaction();
		transaction275.setSubcategory(subcategorySample9);
		transaction275.setAccount(account1);
		transaction275.setType("expense");
		transaction275.setMemo("car fuel");
		Calendar transaction275Cal = Calendar.getInstance();
		transaction275Cal.add(Calendar.MONTH, -15);
		Date transaction275Date = transaction275Cal.getTime();
		transaction275.setDate(transaction275Date);
		transaction275.setAmount(436.00);
		transactionRepository.save(transaction275);

		Transaction transaction276 = new Transaction();
		transaction276.setSubcategory(subcategorySample5);
		transaction276.setAccount(account1);
		transaction276.setType("expense");
		transaction276.setMemo("barber");
		Calendar transaction276Cal = Calendar.getInstance();
		transaction276Cal.add(Calendar.MONTH, -15);
		Date transaction276Date = transaction276Cal.getTime();
		transaction276.setDate(transaction276Date);
		transaction276.setAmount(25.00);
		transactionRepository.save(transaction276);
		
		// 14 months before
		
		Transaction transaction277 = new Transaction();
		transaction277.setSubcategory(subcategorySample1);
		transaction277.setAccount(account1);
		transaction277.setType("income");
		transaction277.setMemo("monthly salary");
		Calendar transaction277Cal = Calendar.getInstance();
		transaction277Cal.add(Calendar.MONTH, -14);
		Date transaction277Date = transaction277Cal.getTime();
		transaction277.setDate(transaction277Date);
		transaction277.setAmount(3706.7);
		transactionRepository.save(transaction277);

		Transaction transaction278 = new Transaction();
		transaction278.setSubcategory(subcategorySample3);
		transaction278.setAccount(account1);
		transaction278.setType("expense");
		transaction278.setMemo("shopping in supermarket");
		Calendar transaction278Cal = Calendar.getInstance();
		transaction278Cal.add(Calendar.MONTH, -14);
		Date transaction278Date = transaction278Cal.getTime();
		transaction278.setDate(transaction278Date);
		transaction278.setAmount(987.00);
		transactionRepository.save(transaction278);

		Transaction transaction279 = new Transaction();
		transaction279.setSubcategory(subcategorySample4);
		transaction279.setAccount(account1);
		transaction279.setType("expense");
		transaction279.setMemo("dinner in restaurant");
		Calendar transaction279Cal = Calendar.getInstance();
		transaction279Cal.add(Calendar.MONTH, -14);
		Date transaction279Date = transaction279Cal.getTime();
		transaction279.setDate(transaction279Date);
		transaction279.setAmount(658);
		transactionRepository.save(transaction279);

		Transaction transaction280 = new Transaction();
		transaction280.setSubcategory(subcategorySample7);
		transaction280.setAccount(account1);
		transaction280.setType("expense");
		transaction280.setMemo("bills");
		Calendar transaction280Cal = Calendar.getInstance();
		transaction280Cal.add(Calendar.MONTH, -14);
		Date transaction280Date = transaction280Cal.getTime();
		transaction280.setDate(transaction280Date);
		transaction280.setAmount(1316.00);
		transactionRepository.save(transaction280);

		Transaction transaction281 = new Transaction();
		transaction281.setSubcategory(subcategorySample9);
		transaction281.setAccount(account1);
		transaction281.setType("expense");
		transaction281.setMemo("car fuel");
		Calendar transaction281Cal = Calendar.getInstance();
		transaction281Cal.add(Calendar.MONTH, -14);
		Date transaction281Date = transaction281Cal.getTime();
		transaction281.setDate(transaction281Date);
		transaction281.setAmount(329.00);
		transactionRepository.save(transaction281);

		Transaction transaction282 = new Transaction();
		transaction282.setSubcategory(subcategorySample5);
		transaction282.setAccount(account1);
		transaction282.setType("expense");
		transaction282.setMemo("barber");
		Calendar transaction282Cal = Calendar.getInstance();
		transaction282Cal.add(Calendar.MONTH, -14);
		Date transaction282Date = transaction282Cal.getTime();
		transaction282.setDate(transaction282Date);
		transaction282.setAmount(25.00);
		transactionRepository.save(transaction282);
		
		// 13 months before
		
		Transaction transaction283 = new Transaction();
		transaction283.setSubcategory(subcategorySample1);
		transaction283.setAccount(account1);
		transaction283.setType("income");
		transaction283.setMemo("monthly salary");
		Calendar transaction283Cal = Calendar.getInstance();
		transaction283Cal.add(Calendar.MONTH, -13);
		Date transaction283Date = transaction283Cal.getTime();
		transaction283.setDate(transaction283Date);
		transaction283.setAmount(3918.56);
		transactionRepository.save(transaction283);

		Transaction transaction284 = new Transaction();
		transaction284.setSubcategory(subcategorySample3);
		transaction284.setAccount(account1);
		transaction284.setType("expense");
		transaction284.setMemo("shopping in supermarket");
		Calendar transaction284Cal = Calendar.getInstance();
		transaction284Cal.add(Calendar.MONTH, -13);
		Date transaction284Date = transaction284Cal.getTime();
		transaction284.setDate(transaction284Date);
		transaction284.setAmount(1088.00);
		transactionRepository.save(transaction284);

		Transaction transaction285 = new Transaction();
		transaction285.setSubcategory(subcategorySample4);
		transaction285.setAccount(account1);
		transaction285.setType("expense");
		transaction285.setMemo("dinner in restaurant");
		Calendar transaction285Cal = Calendar.getInstance();
		transaction285Cal.add(Calendar.MONTH, -13);
		Date transaction285Date = transaction285Cal.getTime();
		transaction285.setDate(transaction285Date);
		transaction285.setAmount(726);
		transactionRepository.save(transaction285);

		Transaction transaction286 = new Transaction();
		transaction286.setSubcategory(subcategorySample7);
		transaction286.setAccount(account1);
		transaction286.setType("expense");
		transaction286.setMemo("bills");
		Calendar transaction286Cal = Calendar.getInstance();
		transaction286Cal.add(Calendar.MONTH, -13);
		Date transaction286Date = transaction286Cal.getTime();
		transaction286.setDate(transaction286Date);
		transaction286.setAmount(1451.00);
		transactionRepository.save(transaction286);

		Transaction transaction287 = new Transaction();
		transaction287.setSubcategory(subcategorySample9);
		transaction287.setAccount(account1);
		transaction287.setType("expense");
		transaction287.setMemo("car fuel");
		Calendar transaction287Cal = Calendar.getInstance();
		transaction287Cal.add(Calendar.MONTH, -13);
		Date transaction287Date = transaction287Cal.getTime();
		transaction287.setDate(transaction287Date);
		transaction287.setAmount(363.00);
		transactionRepository.save(transaction287);

		Transaction transaction288 = new Transaction();
		transaction288.setSubcategory(subcategorySample5);
		transaction288.setAccount(account1);
		transaction288.setType("expense");
		transaction288.setMemo("barber");
		Calendar transaction288Cal = Calendar.getInstance();
		transaction288Cal.add(Calendar.MONTH, -13);
		Date transaction288Date = transaction288Cal.getTime();
		transaction288.setDate(transaction288Date);
		transaction288.setAmount(25.00);
		transactionRepository.save(transaction288);
				
		// 12 months before
		
		Transaction transaction289 = new Transaction();
		transaction289.setSubcategory(subcategorySample1);
		transaction289.setAccount(account1);
		transaction289.setType("income");
		transaction289.setMemo("monthly salary");
		Calendar transaction289Cal = Calendar.getInstance();
		transaction289Cal.add(Calendar.MONTH, -12);
		Date transaction289Date = transaction289Cal.getTime();
		transaction289.setDate(transaction289Date);
		transaction289.setAmount(5187.08);
		transactionRepository.save(transaction289);

		Transaction transaction290 = new Transaction();
		transaction290.setSubcategory(subcategorySample3);
		transaction290.setAccount(account1);
		transaction290.setType("expense");
		transaction290.setMemo("shopping in supermarket");
		Calendar transaction290Cal = Calendar.getInstance();
		transaction290Cal.add(Calendar.MONTH, -12);
		Date transaction290Date = transaction290Cal.getTime();
		transaction290.setDate(transaction290Date);
		transaction290.setAmount(1322.00);
		transactionRepository.save(transaction290);

		Transaction transaction291 = new Transaction();
		transaction291.setSubcategory(subcategorySample4);
		transaction291.setAccount(account1);
		transaction291.setType("expense");
		transaction291.setMemo("dinner in restaurant");
		Calendar transaction291Cal = Calendar.getInstance();
		transaction291Cal.add(Calendar.MONTH, -12);
		Date transaction291Date = transaction291Cal.getTime();
		transaction291.setDate(transaction291Date);
		transaction291.setAmount(881);
		transactionRepository.save(transaction291);

		Transaction transaction292 = new Transaction();
		transaction292.setSubcategory(subcategorySample7);
		transaction292.setAccount(account1);
		transaction292.setType("expense");
		transaction292.setMemo("bills");
		Calendar transaction292Cal = Calendar.getInstance();
		transaction292Cal.add(Calendar.MONTH, -12);
		Date transaction292Date = transaction292Cal.getTime();
		transaction292.setDate(transaction292Date);
		transaction292.setAmount(1763.00);
		transactionRepository.save(transaction292);

		Transaction transaction293 = new Transaction();
		transaction293.setSubcategory(subcategorySample9);
		transaction293.setAccount(account1);
		transaction293.setType("expense");
		transaction293.setMemo("car fuel");
		Calendar transaction293Cal = Calendar.getInstance();
		transaction293Cal.add(Calendar.MONTH, -12);
		Date transaction293Date = transaction293Cal.getTime();
		transaction293.setDate(transaction293Date);
		transaction293.setAmount(441.00);
		transactionRepository.save(transaction293);

		Transaction transaction294 = new Transaction();
		transaction294.setSubcategory(subcategorySample5);
		transaction294.setAccount(account1);
		transaction294.setType("expense");
		transaction294.setMemo("barber");
		Calendar transaction294Cal = Calendar.getInstance();
		transaction294Cal.add(Calendar.MONTH, -12);
		Date transaction294Date = transaction294Cal.getTime();
		transaction294.setDate(transaction294Date);
		transaction294.setAmount(25.00);
		transactionRepository.save(transaction294);
		
		// 11 months before
		
		Transaction transaction295 = new Transaction();
		transaction295.setSubcategory(subcategorySample1);
		transaction295.setAccount(account1);
		transaction295.setType("income");
		transaction295.setMemo("monthly salary");
		Calendar transaction295Cal = Calendar.getInstance();
		transaction295Cal.add(Calendar.MONTH, -11);
		Date transaction295Date = transaction295Cal.getTime();
		transaction295.setDate(transaction295Date);
		transaction295.setAmount(5119.54);
		transactionRepository.save(transaction295);

		Transaction transaction296 = new Transaction();
		transaction296.setSubcategory(subcategorySample3);
		transaction296.setAccount(account1);
		transaction296.setType("expense");
		transaction296.setMemo("shopping in supermarket");
		Calendar transaction296Cal = Calendar.getInstance();
		transaction296Cal.add(Calendar.MONTH, -11);
		Date transaction296Date = transaction296Cal.getTime();
		transaction296.setDate(transaction296Date);
		transaction296.setAmount(1554.00);
		transactionRepository.save(transaction296);

		Transaction transaction297 = new Transaction();
		transaction297.setSubcategory(subcategorySample4);
		transaction297.setAccount(account1);
		transaction297.setType("expense");
		transaction297.setMemo("dinner in restaurant");
		Calendar transaction297Cal = Calendar.getInstance();
		transaction297Cal.add(Calendar.MONTH, -11);
		Date transaction297Date = transaction297Cal.getTime();
		transaction297.setDate(transaction297Date);
		transaction297.setAmount(1036);
		transactionRepository.save(transaction297);

		Transaction transaction298 = new Transaction();
		transaction298.setSubcategory(subcategorySample7);
		transaction298.setAccount(account1);
		transaction298.setType("expense");
		transaction298.setMemo("bills");
		Calendar transaction298Cal = Calendar.getInstance();
		transaction298Cal.add(Calendar.MONTH, -11);
		Date transaction298Date = transaction298Cal.getTime();
		transaction298.setDate(transaction298Date);
		transaction298.setAmount(2072.00);
		transactionRepository.save(transaction298);

		Transaction transaction299 = new Transaction();
		transaction299.setSubcategory(subcategorySample9);
		transaction299.setAccount(account1);
		transaction299.setType("expense");
		transaction299.setMemo("car fuel");
		Calendar transaction299Cal = Calendar.getInstance();
		transaction299Cal.add(Calendar.MONTH, -11);
		Date transaction299Date = transaction299Cal.getTime();
		transaction299.setDate(transaction299Date);
		transaction299.setAmount(518.00);
		transactionRepository.save(transaction299);

		Transaction transaction300 = new Transaction();
		transaction300.setSubcategory(subcategorySample5);
		transaction300.setAccount(account1);
		transaction300.setType("expense");
		transaction300.setMemo("barber");
		Calendar transaction300Cal = Calendar.getInstance();
		transaction300Cal.add(Calendar.MONTH, -11);
		Date transaction300Date = transaction300Cal.getTime();
		transaction300.setDate(transaction300Date);
		transaction300.setAmount(25.00);
		transactionRepository.save(transaction300);
		
		// 10 months before
		
		Transaction transaction301 = new Transaction();
		transaction301.setSubcategory(subcategorySample1);
		transaction301.setAccount(account1);
		transaction301.setType("income");
		transaction301.setMemo("monthly salary");
		Calendar transaction301Cal = Calendar.getInstance();
		transaction301Cal.add(Calendar.MONTH, -10);
		Date transaction301Date = transaction301Cal.getTime();
		transaction301.setDate(transaction301Date);
		transaction301.setAmount(3772.06);
		transactionRepository.save(transaction301);

		Transaction transaction302 = new Transaction();
		transaction302.setSubcategory(subcategorySample3);
		transaction302.setAccount(account1);
		transaction302.setType("expense");
		transaction302.setMemo("shopping in supermarket");
		Calendar transaction302Cal = Calendar.getInstance();
		transaction302Cal.add(Calendar.MONTH, -10);
		Date transaction302Date = transaction302Cal.getTime();
		transaction302.setDate(transaction302Date);
		transaction302.setAmount(1021.00);
		transactionRepository.save(transaction302);

		Transaction transaction303 = new Transaction();
		transaction303.setSubcategory(subcategorySample4);
		transaction303.setAccount(account1);
		transaction303.setType("expense");
		transaction303.setMemo("dinner in restaurant");
		Calendar transaction303Cal = Calendar.getInstance();
		transaction303Cal.add(Calendar.MONTH, -10);
		Date transaction303Date = transaction303Cal.getTime();
		transaction303.setDate(transaction303Date);
		transaction303.setAmount(680);
		transactionRepository.save(transaction303);

		Transaction transaction304 = new Transaction();
		transaction304.setSubcategory(subcategorySample7);
		transaction304.setAccount(account1);
		transaction304.setType("expense");
		transaction304.setMemo("bills");
		Calendar transaction304Cal = Calendar.getInstance();
		transaction304Cal.add(Calendar.MONTH, -10);
		Date transaction304Date = transaction304Cal.getTime();
		transaction304.setDate(transaction304Date);
		transaction304.setAmount(1361.00);
		transactionRepository.save(transaction304);

		Transaction transaction305 = new Transaction();
		transaction305.setSubcategory(subcategorySample9);
		transaction305.setAccount(account1);
		transaction305.setType("expense");
		transaction305.setMemo("car fuel");
		Calendar transaction305Cal = Calendar.getInstance();
		transaction305Cal.add(Calendar.MONTH, -10);
		Date transaction305Date = transaction305Cal.getTime();
		transaction305.setDate(transaction305Date);
		transaction305.setAmount(340.00);
		transactionRepository.save(transaction305);

		Transaction transaction306 = new Transaction();
		transaction306.setSubcategory(subcategorySample5);
		transaction306.setAccount(account1);
		transaction306.setType("expense");
		transaction306.setMemo("barber");
		Calendar transaction306Cal = Calendar.getInstance();
		transaction306Cal.add(Calendar.MONTH, -10);
		Date transaction306Date = transaction306Cal.getTime();
		transaction306.setDate(transaction306Date);
		transaction306.setAmount(25.00);
		transactionRepository.save(transaction306);
		
		// 9 months before
		
		Transaction transaction307 = new Transaction();
		transaction307.setSubcategory(subcategorySample1);
		transaction307.setAccount(account1);
		transaction307.setType("income");
		transaction307.setMemo("monthly salary");
		Calendar transaction307Cal = Calendar.getInstance();
		transaction307Cal.add(Calendar.MONTH, -9);
		Date transaction307Date = transaction307Cal.getTime();
		transaction307.setDate(transaction307Date);
		transaction307.setAmount(3914.09);
		transactionRepository.save(transaction307);

		Transaction transaction308 = new Transaction();
		transaction308.setSubcategory(subcategorySample3);
		transaction308.setAccount(account1);
		transaction308.setType("expense");
		transaction308.setMemo("shopping in supermarket");
		Calendar transaction308Cal = Calendar.getInstance();
		transaction308Cal.add(Calendar.MONTH, -9);
		Date transaction308Date = transaction308Cal.getTime();
		transaction308.setDate(transaction308Date);
		transaction308.setAmount(1090.00);
		transactionRepository.save(transaction308);

		Transaction transaction309 = new Transaction();
		transaction309.setSubcategory(subcategorySample4);
		transaction309.setAccount(account1);
		transaction309.setType("expense");
		transaction309.setMemo("dinner in restaurant");
		Calendar transaction309Cal = Calendar.getInstance();
		transaction309Cal.add(Calendar.MONTH, -9);
		Date transaction309Date = transaction309Cal.getTime();
		transaction309.setDate(transaction309Date);
		transaction309.setAmount(727);
		transactionRepository.save(transaction309);

		Transaction transaction310 = new Transaction();
		transaction310.setSubcategory(subcategorySample7);
		transaction310.setAccount(account1);
		transaction310.setType("expense");
		transaction310.setMemo("bills");
		Calendar transaction310Cal = Calendar.getInstance();
		transaction310Cal.add(Calendar.MONTH, -9);
		Date transaction310Date = transaction310Cal.getTime();
		transaction310.setDate(transaction310Date);
		transaction310.setAmount(1453.00);
		transactionRepository.save(transaction310);

		Transaction transaction311 = new Transaction();
		transaction311.setSubcategory(subcategorySample9);
		transaction311.setAccount(account1);
		transaction311.setType("expense");
		transaction311.setMemo("car fuel");
		Calendar transaction311Cal = Calendar.getInstance();
		transaction311Cal.add(Calendar.MONTH, -9);
		Date transaction311Date = transaction311Cal.getTime();
		transaction311.setDate(transaction311Date);
		transaction311.setAmount(363.00);
		transactionRepository.save(transaction311);

		Transaction transaction312 = new Transaction();
		transaction312.setSubcategory(subcategorySample5);
		transaction312.setAccount(account1);
		transaction312.setType("expense");
		transaction312.setMemo("barber");
		Calendar transaction312Cal = Calendar.getInstance();
		transaction312Cal.add(Calendar.MONTH, -9);
		Date transaction312Date = transaction312Cal.getTime();
		transaction312.setDate(transaction312Date);
		transaction312.setAmount(25.00);
		transactionRepository.save(transaction312);
		
		// 8 months before
		
		Transaction transaction313 = new Transaction();
		transaction313.setSubcategory(subcategorySample1);
		transaction313.setAccount(account1);
		transaction313.setType("income");
		transaction313.setMemo("monthly salary");
		Calendar transaction313Cal = Calendar.getInstance();
		transaction313Cal.add(Calendar.MONTH, -8);
		Date transaction313Date = transaction313Cal.getTime();
		transaction313.setDate(transaction313Date);
		transaction313.setAmount(4379.69);
		transactionRepository.save(transaction313);

		Transaction transaction314 = new Transaction();
		transaction314.setSubcategory(subcategorySample3);
		transaction314.setAccount(account1);
		transaction314.setType("expense");
		transaction314.setMemo("shopping in supermarket");
		Calendar transaction314Cal = Calendar.getInstance();
		transaction314Cal.add(Calendar.MONTH, -8);
		Date transaction314Date = transaction314Cal.getTime();
		transaction314.setDate(transaction314Date);
		transaction314.setAmount(1185.63);
		transactionRepository.save(transaction314);

		Transaction transaction315 = new Transaction();
		transaction315.setSubcategory(subcategorySample4);
		transaction315.setAccount(account1);
		transaction315.setType("expense");
		transaction315.setMemo("dinner in restaurant");
		Calendar transaction315Cal = Calendar.getInstance();
		transaction315Cal.add(Calendar.MONTH, -8);
		Date transaction315Date = transaction315Cal.getTime();
		transaction315.setDate(transaction315Date);
		transaction315.setAmount(790);
		transactionRepository.save(transaction315);

		Transaction transaction316 = new Transaction();
		transaction316.setSubcategory(subcategorySample7);
		transaction316.setAccount(account1);
		transaction316.setType("expense");
		transaction316.setMemo("bills");
		Calendar transaction316Cal = Calendar.getInstance();
		transaction316Cal.add(Calendar.MONTH, -8);
		Date transaction316Date = transaction316Cal.getTime();
		transaction316.setDate(transaction316Date);
		transaction316.setAmount(1579.00);
		transactionRepository.save(transaction316);

		Transaction transaction317 = new Transaction();
		transaction317.setSubcategory(subcategorySample9);
		transaction317.setAccount(account1);
		transaction317.setType("expense");
		transaction317.setMemo("car fuel");
		Calendar transaction317Cal = Calendar.getInstance();
		transaction317Cal.add(Calendar.MONTH, -8);
		Date transaction317Date = transaction317Cal.getTime();
		transaction317.setDate(transaction317Date);
		transaction317.setAmount(395.00);
		transactionRepository.save(transaction317);

		Transaction transaction318 = new Transaction();
		transaction318.setSubcategory(subcategorySample5);
		transaction318.setAccount(account1);
		transaction318.setType("expense");
		transaction318.setMemo("barber");
		Calendar transaction318Cal = Calendar.getInstance();
		transaction318Cal.add(Calendar.MONTH, -8);
		Date transaction318Date = transaction318Cal.getTime();
		transaction318.setDate(transaction318Date);
		transaction318.setAmount(25.00);
		transactionRepository.save(transaction318);
		
		// 7 months before
		
		Transaction transaction319 = new Transaction();
		transaction319.setSubcategory(subcategorySample1);
		transaction319.setAccount(account1);
		transaction319.setType("income");
		transaction319.setMemo("monthly salary");
		Calendar transaction319Cal = Calendar.getInstance();
		transaction319Cal.add(Calendar.MONTH, -7);
		Date transaction319Date = transaction319Cal.getTime();
		transaction319.setDate(transaction319Date);
		transaction319.setAmount(4373.81);
		transactionRepository.save(transaction319);

		Transaction transaction320 = new Transaction();
		transaction320.setSubcategory(subcategorySample3);
		transaction320.setAccount(account1);
		transaction320.setType("expense");
		transaction320.setMemo("shopping in supermarket");
		Calendar transaction320Cal = Calendar.getInstance();
		transaction320Cal.add(Calendar.MONTH, -7);
		Date transaction320Date = transaction320Cal.getTime();
		transaction320.setDate(transaction320Date);
		transaction320.setAmount(1215);
		transactionRepository.save(transaction320);

		Transaction transaction321 = new Transaction();
		transaction321.setSubcategory(subcategorySample4);
		transaction321.setAccount(account1);
		transaction321.setType("expense");
		transaction321.setMemo("dinner in restaurant");
		Calendar transaction321Cal = Calendar.getInstance();
		transaction321Cal.add(Calendar.MONTH, -7);
		Date transaction321Date = transaction321Cal.getTime();
		transaction321.setDate(transaction321Date);
		transaction321.setAmount(810.00);
		transactionRepository.save(transaction321);

		Transaction transaction322 = new Transaction();
		transaction322.setSubcategory(subcategorySample7);
		transaction322.setAccount(account1);
		transaction322.setType("expense");
		transaction322.setMemo("bills");
		Calendar transaction322Cal = Calendar.getInstance();
		transaction322Cal.add(Calendar.MONTH, -7);
		Date transaction322Date = transaction322Cal.getTime();
		transaction322.setDate(transaction322Date);
		transaction322.setAmount(1620.00);
		transactionRepository.save(transaction322);

		Transaction transaction323 = new Transaction();
		transaction323.setSubcategory(subcategorySample9);
		transaction323.setAccount(account1);
		transaction323.setType("expense");
		transaction323.setMemo("car fuel");
		Calendar transaction323Cal = Calendar.getInstance();
		transaction323Cal.add(Calendar.MONTH, -7);
		Date transaction323Date = transaction323Cal.getTime();
		transaction323.setDate(transaction323Date);
		transaction323.setAmount(405.00);
		transactionRepository.save(transaction323);

		Transaction transaction324 = new Transaction();
		transaction324.setSubcategory(subcategorySample5);
		transaction324.setAccount(account1);
		transaction324.setType("expense");
		transaction324.setMemo("barber");
		Calendar transaction324Cal = Calendar.getInstance();
		transaction324Cal.add(Calendar.MONTH, -7);
		Date transaction324Date = transaction324Cal.getTime();
		transaction324.setDate(transaction324Date);
		transaction324.setAmount(25.00);
		transactionRepository.save(transaction324);
		
		// 6 months before
		
		Transaction transaction325 = new Transaction();
		transaction325.setSubcategory(subcategorySample1);
		transaction325.setAccount(account1);
		transaction325.setType("income");
		transaction325.setMemo("monthly salary");
		Calendar transaction325Cal = Calendar.getInstance();
		transaction325Cal.add(Calendar.MONTH, -6);
		Date transaction325Date = transaction325Cal.getTime();
		transaction325.setDate(transaction325Date);
		transaction325.setAmount(3963.05);
		transactionRepository.save(transaction325);

		Transaction transaction326 = new Transaction();
		transaction326.setSubcategory(subcategorySample3);
		transaction326.setAccount(account1);
		transaction326.setType("expense");
		transaction326.setMemo("shopping in supermarket");
		Calendar transaction326Cal = Calendar.getInstance();
		transaction326Cal.add(Calendar.MONTH, -6);
		Date transaction326Date = transaction326Cal.getTime();
		transaction326.setDate(transaction326Date);
		transaction326.setAmount(1082);
		transactionRepository.save(transaction326);

		Transaction transaction327 = new Transaction();
		transaction327.setSubcategory(subcategorySample4);
		transaction327.setAccount(account1);
		transaction327.setType("expense");
		transaction327.setMemo("dinner in restaurant");
		Calendar transaction327Cal = Calendar.getInstance();
		transaction327Cal.add(Calendar.MONTH, -6);
		Date transaction327Date = transaction327Cal.getTime();
		transaction327.setDate(transaction327Date);
		transaction327.setAmount(722.00);
		transactionRepository.save(transaction327);

		Transaction transaction328 = new Transaction();
		transaction328.setSubcategory(subcategorySample7);
		transaction328.setAccount(account1);
		transaction328.setType("expense");
		transaction328.setMemo("bills");
		Calendar transaction328Cal = Calendar.getInstance();
		transaction328Cal.add(Calendar.MONTH, -6);
		Date transaction328Date = transaction328Cal.getTime();
		transaction328.setDate(transaction328Date);
		transaction328.setAmount(1443.00);
		transactionRepository.save(transaction328);

		Transaction transaction329 = new Transaction();
		transaction329.setSubcategory(subcategorySample9);
		transaction329.setAccount(account1);
		transaction329.setType("expense");
		transaction329.setMemo("car fuel");
		Calendar transaction329Cal = Calendar.getInstance();
		transaction329Cal.add(Calendar.MONTH, -6);
		Date transaction329Date = transaction329Cal.getTime();
		transaction329.setDate(transaction329Date);
		transaction329.setAmount(361.00);
		transactionRepository.save(transaction329);

		Transaction transaction330 = new Transaction();
		transaction330.setSubcategory(subcategorySample5);
		transaction330.setAccount(account1);
		transaction330.setType("expense");
		transaction330.setMemo("barber");
		Calendar transaction330Cal = Calendar.getInstance();
		transaction330Cal.add(Calendar.MONTH, -6);
		Date transaction330Date = transaction330Cal.getTime();
		transaction330.setDate(transaction330Date);
		transaction330.setAmount(25.00);
		transactionRepository.save(transaction330);
		
		// 5 months before
		
		Transaction transaction331 = new Transaction();
		transaction331.setSubcategory(subcategorySample1);
		transaction331.setAccount(account1);
		transaction331.setType("income");
		transaction331.setMemo("monthly salary");
		Calendar transaction331Cal = Calendar.getInstance();
		transaction331Cal.add(Calendar.MONTH, -5);
		Date transaction331Date = transaction331Cal.getTime();
		transaction331.setDate(transaction331Date);
		transaction331.setAmount(3844.07);
		transactionRepository.save(transaction331);

		Transaction transaction332 = new Transaction();
		transaction332.setSubcategory(subcategorySample3);
		transaction332.setAccount(account1);
		transaction332.setType("expense");
		transaction332.setMemo("shopping in supermarket");
		Calendar transaction332Cal = Calendar.getInstance();
		transaction332Cal.add(Calendar.MONTH, -5);
		Date transaction332Date = transaction332Cal.getTime();
		transaction332.setDate(transaction332Date);
		transaction332.setAmount(1048);
		transactionRepository.save(transaction332);

		Transaction transaction333 = new Transaction();
		transaction333.setSubcategory(subcategorySample4);
		transaction333.setAccount(account1);
		transaction333.setType("expense");
		transaction333.setMemo("dinner in restaurant");
		Calendar transaction333Cal = Calendar.getInstance();
		transaction333Cal.add(Calendar.MONTH, -5);
		Date transaction333Date = transaction333Cal.getTime();
		transaction333.setDate(transaction333Date);
		transaction333.setAmount(699.00);
		transactionRepository.save(transaction333);

		Transaction transaction334 = new Transaction();
		transaction334.setSubcategory(subcategorySample7);
		transaction334.setAccount(account1);
		transaction334.setType("expense");
		transaction334.setMemo("bills");
		Calendar transaction334Cal = Calendar.getInstance();
		transaction334Cal.add(Calendar.MONTH, -5);
		Date transaction334Date = transaction334Cal.getTime();
		transaction334.setDate(transaction334Date);
		transaction334.setAmount(1397.00);
		transactionRepository.save(transaction334);

		Transaction transaction335 = new Transaction();
		transaction335.setSubcategory(subcategorySample9);
		transaction335.setAccount(account1);
		transaction335.setType("expense");
		transaction335.setMemo("car fuel");
		Calendar transaction335Cal = Calendar.getInstance();
		transaction335Cal.add(Calendar.MONTH, -5);
		Date transaction335Date = transaction335Cal.getTime();
		transaction335.setDate(transaction335Date);
		transaction335.setAmount(349.00);
		transactionRepository.save(transaction335);

		Transaction transaction336 = new Transaction();
		transaction336.setSubcategory(subcategorySample5);
		transaction336.setAccount(account1);
		transaction336.setType("expense");
		transaction336.setMemo("barber");
		Calendar transaction336Cal = Calendar.getInstance();
		transaction336Cal.add(Calendar.MONTH, -5);
		Date transaction336Date = transaction336Cal.getTime();
		transaction336.setDate(transaction336Date);
		transaction336.setAmount(25.00);
		transactionRepository.save(transaction336);
		
		// 4 months before
		
		Transaction transaction337 = new Transaction();
		transaction337.setSubcategory(subcategorySample1);
		transaction337.setAccount(account1);
		transaction337.setType("income");
		transaction337.setMemo("monthly salary");
		Calendar transaction337Cal = Calendar.getInstance();
		transaction337Cal.add(Calendar.MONTH, -4);
		Date transaction337Date = transaction337Cal.getTime();
		transaction337.setDate(transaction337Date);
		transaction337.setAmount(4171.13);
		transactionRepository.save(transaction337);

		Transaction transaction338 = new Transaction();
		transaction338.setSubcategory(subcategorySample3);
		transaction338.setAccount(account1);
		transaction338.setType("expense");
		transaction338.setMemo("shopping in supermarket");
		Calendar transaction338Cal = Calendar.getInstance();
		transaction338Cal.add(Calendar.MONTH, -4);
		Date transaction338Date = transaction338Cal.getTime();
		transaction338.setDate(transaction338Date);
		transaction338.setAmount(1152);
		transactionRepository.save(transaction338);

		Transaction transaction339 = new Transaction();
		transaction339.setSubcategory(subcategorySample4);
		transaction339.setAccount(account1);
		transaction339.setType("expense");
		transaction339.setMemo("dinner in restaurant");
		Calendar transaction339Cal = Calendar.getInstance();
		transaction339Cal.add(Calendar.MONTH, -4);
		Date transaction339Date = transaction339Cal.getTime();
		transaction339.setDate(transaction339Date);
		transaction339.setAmount(768.00);
		transactionRepository.save(transaction339);

		Transaction transaction340 = new Transaction();
		transaction340.setSubcategory(subcategorySample7);
		transaction340.setAccount(account1);
		transaction340.setType("expense");
		transaction340.setMemo("bills");
		Calendar transaction340Cal = Calendar.getInstance();
		transaction340Cal.add(Calendar.MONTH, -4);
		Date transaction340Date = transaction340Cal.getTime();
		transaction340.setDate(transaction340Date);
		transaction340.setAmount(1536.00);
		transactionRepository.save(transaction340);

		Transaction transaction341 = new Transaction();
		transaction341.setSubcategory(subcategorySample9);
		transaction341.setAccount(account1);
		transaction341.setType("expense");
		transaction341.setMemo("car fuel");
		Calendar transaction341Cal = Calendar.getInstance();
		transaction341Cal.add(Calendar.MONTH, -4);
		Date transaction341Date = transaction341Cal.getTime();
		transaction341.setDate(transaction341Date);
		transaction341.setAmount(384.00);
		transactionRepository.save(transaction341);

		Transaction transaction342 = new Transaction();
		transaction342.setSubcategory(subcategorySample5);
		transaction342.setAccount(account1);
		transaction342.setType("expense");
		transaction342.setMemo("barber");
		Calendar transaction342Cal = Calendar.getInstance();
		transaction342Cal.add(Calendar.MONTH, -4);
		Date transaction342Date = transaction342Cal.getTime();
		transaction342.setDate(transaction342Date);
		transaction342.setAmount(25.00);
		transactionRepository.save(transaction342);
		
		// 3 months before
		
		Transaction transaction343 = new Transaction();
		transaction343.setSubcategory(subcategorySample1);
		transaction343.setAccount(account1);
		transaction343.setType("income");
		transaction343.setMemo("monthly salary");
		Calendar transaction343Cal = Calendar.getInstance();
		transaction343Cal.add(Calendar.MONTH, -3);
		Date transaction343Date = transaction343Cal.getTime();
		transaction343.setDate(transaction343Date);
		transaction343.setAmount(4794.18);
		transactionRepository.save(transaction343);

		Transaction transaction344 = new Transaction();
		transaction344.setSubcategory(subcategorySample3);
		transaction344.setAccount(account1);
		transaction344.setType("expense");
		transaction344.setMemo("shopping in supermarket");
		Calendar transaction344Cal = Calendar.getInstance();
		transaction344Cal.add(Calendar.MONTH, -3);
		Date transaction344Date = transaction344Cal.getTime();
		transaction344.setDate(transaction344Date);
		transaction344.setAmount(1337);
		transactionRepository.save(transaction344);

		Transaction transaction345 = new Transaction();
		transaction345.setSubcategory(subcategorySample4);
		transaction345.setAccount(account1);
		transaction345.setType("expense");
		transaction345.setMemo("dinner in restaurant");
		Calendar transaction345Cal = Calendar.getInstance();
		transaction345Cal.add(Calendar.MONTH, -3);
		Date transaction345Date = transaction345Cal.getTime();
		transaction345.setDate(transaction345Date);
		transaction345.setAmount(891.00);
		transactionRepository.save(transaction345);

		Transaction transaction346 = new Transaction();
		transaction346.setSubcategory(subcategorySample7);
		transaction346.setAccount(account1);
		transaction346.setType("expense");
		transaction346.setMemo("bills");
		Calendar transaction346Cal = Calendar.getInstance();
		transaction346Cal.add(Calendar.MONTH, -3);
		Date transaction346Date = transaction346Cal.getTime();
		transaction346.setDate(transaction346Date);
		transaction346.setAmount(1782.00);
		transactionRepository.save(transaction346);

		Transaction transaction347 = new Transaction();
		transaction347.setSubcategory(subcategorySample9);
		transaction347.setAccount(account1);
		transaction347.setType("expense");
		transaction347.setMemo("car fuel");
		Calendar transaction347Cal = Calendar.getInstance();
		transaction347Cal.add(Calendar.MONTH, -3);
		Date transaction347Date = transaction347Cal.getTime();
		transaction347.setDate(transaction347Date);
		transaction347.setAmount(446.00);
		transactionRepository.save(transaction347);

		Transaction transaction348 = new Transaction();
		transaction348.setSubcategory(subcategorySample5);
		transaction348.setAccount(account1);
		transaction348.setType("expense");
		transaction348.setMemo("barber");
		Calendar transaction348Cal = Calendar.getInstance();
		transaction348Cal.add(Calendar.MONTH, -3);
		Date transaction348Date = transaction348Cal.getTime();
		transaction348.setDate(transaction348Date);
		transaction348.setAmount(25.00);
		transactionRepository.save(transaction348);
						
		// 2 months before
		
		Transaction transaction349 = new Transaction();
		transaction349.setSubcategory(subcategorySample1);
		transaction349.setAccount(account1);
		transaction349.setType("income");
		transaction349.setMemo("monthly salary");
		Calendar transaction349Cal = Calendar.getInstance();
		transaction349Cal.add(Calendar.MONTH, -2);
		Date transaction349Date = transaction349Cal.getTime();
		transaction349.setDate(transaction349Date);
		transaction349.setAmount(3806.69);
		transactionRepository.save(transaction349);

		Transaction transaction350 = new Transaction();
		transaction350.setSubcategory(subcategorySample3);
		transaction350.setAccount(account1);
		transaction350.setType("expense");
		transaction350.setMemo("shopping in supermarket");
		Calendar transaction350Cal = Calendar.getInstance();
		transaction350Cal.add(Calendar.MONTH, -2);
		Date transaction350Date = transaction350Cal.getTime();
		transaction350.setDate(transaction350Date);
		transaction350.setAmount(1017);
		transactionRepository.save(transaction350);

		Transaction transaction351 = new Transaction();
		transaction351.setSubcategory(subcategorySample4);
		transaction351.setAccount(account1);
		transaction351.setType("expense");
		transaction351.setMemo("dinner in restaurant");
		Calendar transaction351Cal = Calendar.getInstance();
		transaction351Cal.add(Calendar.MONTH, -2);
		Date transaction351Date = transaction351Cal.getTime();
		transaction351.setDate(transaction351Date);
		transaction351.setAmount(678.00);
		transactionRepository.save(transaction351);

		Transaction transaction352 = new Transaction();
		transaction352.setSubcategory(subcategorySample7);
		transaction352.setAccount(account1);
		transaction352.setType("expense");
		transaction352.setMemo("bills");
		Calendar transaction352Cal = Calendar.getInstance();
		transaction352Cal.add(Calendar.MONTH, -2);
		Date transaction352Date = transaction352Cal.getTime();
		transaction352.setDate(transaction352Date);
		transaction352.setAmount(1356.00);
		transactionRepository.save(transaction352);

		Transaction transaction353 = new Transaction();
		transaction353.setSubcategory(subcategorySample9);
		transaction353.setAccount(account1);
		transaction353.setType("expense");
		transaction353.setMemo("car fuel");
		Calendar transaction353Cal = Calendar.getInstance();
		transaction353Cal.add(Calendar.MONTH, -2);
		Date transaction353Date = transaction353Cal.getTime();
		transaction353.setDate(transaction353Date);
		transaction353.setAmount(339.00);
		transactionRepository.save(transaction353);

		Transaction transaction354 = new Transaction();
		transaction354.setSubcategory(subcategorySample5);
		transaction354.setAccount(account1);
		transaction354.setType("expense");
		transaction354.setMemo("barber");
		Calendar transaction354Cal = Calendar.getInstance();
		transaction354Cal.add(Calendar.MONTH, -2);
		Date transaction354Date = transaction354Cal.getTime();
		transaction354.setDate(transaction354Date);
		transaction354.setAmount(25.00);
		transactionRepository.save(transaction354);
		
		// 1 months before
		
		Transaction transaction355 = new Transaction();
		transaction355.setSubcategory(subcategorySample1);
		transaction355.setAccount(account1);
		transaction355.setType("income");
		transaction355.setMemo("monthly salary");
		Calendar transaction355Cal = Calendar.getInstance();
		transaction355Cal.add(Calendar.MONTH, -1);
		Date transaction355Date = transaction355Cal.getTime();
		transaction355.setDate(transaction355Date);
		transaction355.setAmount(4018.57);
		transactionRepository.save(transaction355);

		Transaction transaction356 = new Transaction();
		transaction356.setSubcategory(subcategorySample3);
		transaction356.setAccount(account1);
		transaction356.setType("expense");
		transaction356.setMemo("shopping in supermarket");
		Calendar transaction356Cal = Calendar.getInstance();
		transaction356Cal.add(Calendar.MONTH, -1);
		Date transaction356Date = transaction356Cal.getTime();
		transaction356.setDate(transaction356Date);
		transaction356.setAmount(1118);
		transactionRepository.save(transaction356);

		Transaction transaction357 = new Transaction();
		transaction357.setSubcategory(subcategorySample4);
		transaction357.setAccount(account1);
		transaction357.setType("expense");
		transaction357.setMemo("dinner in restaurant");
		Calendar transaction357Cal = Calendar.getInstance();
		transaction357Cal.add(Calendar.MONTH, -1);
		Date transaction357Date = transaction357Cal.getTime();
		transaction357.setDate(transaction357Date);
		transaction357.setAmount(746.00);
		transactionRepository.save(transaction357);

		Transaction transaction358 = new Transaction();
		transaction358.setSubcategory(subcategorySample7);
		transaction358.setAccount(account1);
		transaction358.setType("expense");
		transaction358.setMemo("bills");
		Calendar transaction358Cal = Calendar.getInstance();
		transaction358Cal.add(Calendar.MONTH, -1);
		Date transaction358Date = transaction358Cal.getTime();
		transaction358.setDate(transaction358Date);
		transaction358.setAmount(1491.00);
		transactionRepository.save(transaction358);

		Transaction transaction359 = new Transaction();
		transaction359.setSubcategory(subcategorySample9);
		transaction359.setAccount(account1);
		transaction359.setType("expense");
		transaction359.setMemo("car fuel");
		Calendar transaction359Cal = Calendar.getInstance();
		transaction359Cal.add(Calendar.MONTH, -1);
		Date transaction359Date = transaction359Cal.getTime();
		transaction359.setDate(transaction359Date);
		transaction359.setAmount(373.00);
		transactionRepository.save(transaction359);

		Transaction transaction360 = new Transaction();
		transaction360.setSubcategory(subcategorySample5);
		transaction360.setAccount(account1);
		transaction360.setType("expense");
		transaction360.setMemo("barber");
		Calendar transaction360Cal = Calendar.getInstance();
		transaction360Cal.add(Calendar.MONTH, -1);
		Date transaction360Date = transaction360Cal.getTime();
		transaction360.setDate(transaction360Date);
		transaction360.setAmount(25.00);
		transactionRepository.save(transaction360);
				
		////////////////////////////////////////////////////////////////////////////////////////////////////
		Transaction transaction500 = new Transaction();
		transaction500.setSubcategory(subcategorySample1);
		transaction500.setAccount(account1);
		transaction500.setType("income");
		transaction500.setMemo("monthly salary");
		transaction500.setDate(new Date());
		transaction500.setAmount(5287.08);
		transactionRepository.save(transaction500);
		
		Transaction transaction501 = new Transaction();
		transaction501.setSubcategory(subcategorySample3);
		transaction501.setAccount(account1);
		transaction501.setType("expense");
		transaction501.setMemo("shopping in supermarket");
		transaction501.setDate(new Date());
		transaction501.setAmount(1352.00);
		transactionRepository.save(transaction501);
		
		Transaction transaction502 = new Transaction();
		transaction502.setSubcategory(subcategorySample4);
		transaction502.setAccount(account1);
		transaction502.setType("expense");
		transaction502.setMemo("dinner in restaurant");
		transaction502.setDate(new Date());
		transaction502.setAmount(901.00);
		transactionRepository.save(transaction502);
		
		Transaction transaction503 = new Transaction();
		transaction503.setSubcategory(subcategorySample7);
		transaction503.setAccount(account1);
		transaction503.setType("expense");
		transaction503.setMemo("bills");
		transaction503.setDate(new Date());
		transaction503.setAmount(1803.00);
		transactionRepository.save(transaction503);
		
		Transaction transaction504 = new Transaction();
		transaction504.setSubcategory(subcategorySample9);
		transaction504.setAccount(account1);
		transaction504.setType("expense");
		transaction504.setMemo("car fuel");
		transaction504.setDate(new Date());
		transaction504.setAmount(451.00);
		transactionRepository.save(transaction504);
		
		Transaction transaction505 = new Transaction();
		transaction505.setSubcategory(subcategorySample5);
		transaction505.setAccount(account1);
		transaction505.setType("expense");
		transaction505.setMemo("barber");
		transaction505.setDate(new Date());
		transaction505.setAmount(25.00);
		transactionRepository.save(transaction505);


		// User
		
		User user1 = new User();
		user1.setName("user");
		user1.setPassword(bcrypt.encode("user"));
		user1.setEmail("user@user.pl");	
		user1.setEnabled(true);
		List<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleUser);
		user1.setRoles(userRoles);
		userRepository.save(user1);
		
		Account account1000 = new Account();
		account1000.setUser(user1);
		account1000.setName("Bank account");
		accountRepository.save(account1000);
		
		Category categorySample1000 = new Category();
		categorySample1000.setUser(user1);
		categorySample1000.setName("SampleCategory1");
		categoryRepository.save(categorySample1000);
		
		Subcategory subcategorySample1000 = new Subcategory();
		subcategorySample1000.setCategory(categorySample1000);
		subcategorySample1000.setName("SampleSubcategory3");
		subcategoryRepository.save(subcategorySample1000);
		
		SubcategoryLimit subcategoryLimitSample1000 = new SubcategoryLimit();
		subcategoryLimitSample1000.setSubcategory(subcategorySample1000);
		subcategoryLimitSample1000.setAmount(200.00);
		subcategoryLimitSample1000.setDate(new Date());		
		subcategoryLimitRepository.save(subcategoryLimitSample1000);
		
		Transaction transaction1000 = new Transaction();
		transaction1000.setSubcategory(subcategorySample1000);
		transaction1000.setAccount(account1000);
		transaction1000.setType("income");
		transaction1000.setMemo("tax refund");
		transaction1000.setDate(new Date());
		transaction1000.setAmount(999.00);		
		transactionRepository.save(transaction1000);
		
	
	}
}
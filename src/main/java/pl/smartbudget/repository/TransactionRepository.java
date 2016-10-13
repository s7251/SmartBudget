package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByAccount(Account account);

	List<Transaction> findByType(String type);
	
	@Query(value = "SELECT SUM(AMOUNT)  FROM TRANSACTION WHERE MONTH(DATE)= ?2 AND YEAR(DATE)= ?3 AND SUBCATEGORY_ID = ?1 AND TYPE= 'expense'", nativeQuery = true)	
	String getSummaryOfSpentMoney(Integer id, int month, int year);

	@Query(value = "SELECT SUM(AMOUNT)  FROM TRANSACTION  T JOIN ACCOUNT A ON (T.ACCOUNT_ID = A.ID) WHERE MONTH(DATE)= ?2 AND YEAR(DATE)= ?3 AND USER_ID=?1 AND TYPE= 'income'", nativeQuery = true)	
	Double getSummaryOfIncomeTransaction(Integer id, int month, int year);
	
	@Query(value = "SELECT SUM(AMOUNT)  FROM TRANSACTION  T JOIN ACCOUNT A ON (T.ACCOUNT_ID = A.ID) WHERE MONTH(DATE)= ?2 AND YEAR(DATE)= ?3 AND USER_ID=?1 AND TYPE= 'expense'", nativeQuery = true)	
	Double getSummaryOfExpenseTransaction(Integer id, int month, int year);
	
	List<Transaction> findBySubcategory(Subcategory subcategory);



}

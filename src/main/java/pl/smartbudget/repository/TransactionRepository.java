package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByAccount(Account account);

	List<Transaction> findByType(String type);

}

package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Transaction;


public interface TransactionRepository extends JpaRepository <Transaction, Integer> {

}

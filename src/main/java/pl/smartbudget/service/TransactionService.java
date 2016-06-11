package pl.smartbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Transaction;
import pl.smartbudget.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public List<Transaction> findAll() {
		return transactionRepository.findAll();

	}

}

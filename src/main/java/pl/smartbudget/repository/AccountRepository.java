package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Account;
import pl.smartbudget.entity.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	List<Account> findByUser(User user);
	
	Account findById(Integer id);

}

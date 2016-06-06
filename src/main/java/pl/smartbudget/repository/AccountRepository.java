package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Account;


public interface AccountRepository extends JpaRepository <Account, Integer> {

}

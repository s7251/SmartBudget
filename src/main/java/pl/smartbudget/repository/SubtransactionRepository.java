package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Subtransaction;

public interface SubtransactionRepository extends JpaRepository<Subtransaction, Integer> {

	

}

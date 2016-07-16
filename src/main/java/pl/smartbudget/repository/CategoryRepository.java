package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.User;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findByUser(User user);
	
	}

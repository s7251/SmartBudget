package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

	List<Subcategory> findByCategory(Category category);

}

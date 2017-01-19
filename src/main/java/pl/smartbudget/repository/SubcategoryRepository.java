package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

	List<Subcategory> findByCategory(Category category);

	@Query(value = "SELECT CATEGORY_ID FROM SUBCATEGORY WHERE ID=?1", nativeQuery = true)
	int getCategoryIdBySubcategoryId(Integer id);

}

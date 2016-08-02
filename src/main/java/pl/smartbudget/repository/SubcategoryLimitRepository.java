package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.SubcategoryLimit;

public interface SubcategoryLimitRepository extends JpaRepository<SubcategoryLimit, Integer> {

	List<SubcategoryLimit> findBySubcategory(Subcategory subcategory);
	
	
	@Query(value = "SELECT * FROM SUBCATEGORYLIMIT WHERE MONTH(DATE)= 08 AND YEAR(DATE)=2016 AND SUBCATEGORY_ID = 2", nativeQuery = true)	
	List<SubcategoryLimit> findBySubcategoryAndDate();
}

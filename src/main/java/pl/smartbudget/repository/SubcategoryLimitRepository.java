package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.SubcategoryLimit;

public interface SubcategoryLimitRepository extends JpaRepository<SubcategoryLimit, Integer> {

	List<SubcategoryLimit> findBySubcategory(Subcategory subcategory);
}

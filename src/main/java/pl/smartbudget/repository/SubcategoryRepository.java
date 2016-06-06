package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Subcategory;


public interface SubcategoryRepository extends JpaRepository <Subcategory, Integer> {

}

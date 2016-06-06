package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.SubcategoryLimit;


public interface SubcategoryLimitRepository extends JpaRepository <SubcategoryLimit, Integer> {

}

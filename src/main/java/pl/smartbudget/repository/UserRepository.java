package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);
	
	User findById(Integer id);

	@Query(value = "SELECT NAME FROM USER U JOIN ACCOUNT A ON (A.USER_ID = U.ID) JOIN TRANSACTION T ON (T.ACCOUNT_ID = A.ID) WHERE T.ID=?1", nativeQuery = true)	
	String findUserNameByTransactionId(Transaction transaction);

	@Query(value = "SELECT NAME FROM USER U JOIN CATEGORY C ON (C.USER_ID = U.ID)  WHERE C.ID=?1", nativeQuery = true)	
	String findUserNameByCategoryId(Category category);

	@Query(value = "SELECT NAME FROM USER U JOIN CATEGORY C ON (C.USER_ID = U.ID) JOIN SUBCATEGORY S ON (S.CATEGORY_ID = C.ID) WHERE S.ID=?1", nativeQuery = true)	
	String findUserNameBySubcategoryId(Subcategory subcategory);
	
	@Query(value = "SELECT * FROM USER U JOIN USER_ROLE UR ON (U.ID = UR.USERS_ID) JOIN ROLE R ON (R.ID = UR.ROLES_ID)", nativeQuery = true)	
	List<User> getUsersWithRoles();
	
	User findByEmail(String email);

	
	
}

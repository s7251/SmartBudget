package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.smartbudget.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
	
	@Query(value = "SELECT ID, NAME FROM ROLE R JOIN USER_ROLE UR ON (R.ID = UR.ROLES_ID)  JOIN USER U  ON (U.ID = UR.USERS_ID) WHERE U.NAME=?1 ", nativeQuery = true)	
	List<Role> getRolesByUser(String name);
}

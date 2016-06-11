package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}

package pl.smartbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.Role;
import pl.smartbudget.entity.User;

public interface RoleRepository extends JpaRepository <Role, Integer> {

	Role findByName(String name);

}

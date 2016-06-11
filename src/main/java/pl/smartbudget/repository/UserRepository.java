package pl.smartbudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.smartbudget.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);

}

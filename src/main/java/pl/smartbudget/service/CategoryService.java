package pl.smartbudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Category;
import pl.smartbudget.entity.User;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.UserRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void save(Category category, String name) {
		User user = userRepository.findByName(name);
		//Account account = new Account();		
		category.setName(category.getName());	
		category.setUser(user);
		categoryRepository.save(category);		
	}

	public void delete(int id) {
		categoryRepository.delete(id);		
	}

}

package pl.smartbudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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
		category.setName(category.getName());	
		category.setUser(user);
		categoryRepository.save(category);		
	}

	@PreAuthorize("#userNameByCategoryId == authentication.name")
	public void delete(Category category, String name, @P("userNameByCategoryId") String userNameByCategoryId) {
		//User user = userRepository.findByName(name);
		//List<Category> categories = categoryRepository.findByUser(user);
		//if (categories.size() > 1) {
			categoryRepository.delete(category);
		//}
				
	}

	public Category findOne(int id) {	
		return categoryRepository.findOne(id);
	}	

}

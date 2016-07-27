package pl.smartbudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.forms.SubcategoryForm;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.SubcategoryRepository;

@Service
public class SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	

	public void save(SubcategoryForm subcategoryForm) {
		
		Subcategory subcategory = new Subcategory();
		subcategory.setName(subcategoryForm.getName());	
		subcategory.setCategory(categoryRepository.getOne(subcategoryForm.getCategoryId()));
		subcategoryRepository.save(subcategory);		
	}

	public void delete(int id) {
		subcategoryRepository.delete(id);
		
	}

}

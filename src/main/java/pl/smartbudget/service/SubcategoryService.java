package pl.smartbudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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

	public void rename(SubcategoryForm subcategoryForm) {
		Subcategory subcategory = new Subcategory();
		subcategory.setId(subcategoryForm.getId());
		subcategory.setName(subcategoryForm.getName());
		subcategory.setCategory(categoryRepository.getOne(subcategoryForm.getCategoryId()));
		subcategoryRepository.save(subcategory);
	}

	@PreAuthorize("#userNameBySubcategoryId == authentication.name")
	public void delete(int id,  @P("userNameBySubcategoryId") String userNameBySubcategoryId) {
		subcategoryRepository.delete(id);
	}

	public Subcategory findOne(int id) {		
		return subcategoryRepository.findOne(id);
	}

		
}

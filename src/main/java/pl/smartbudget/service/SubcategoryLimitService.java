package pl.smartbudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.SubcategoryLimit;
import pl.smartbudget.forms.SubcategoryLimitForm;
import pl.smartbudget.repository.SubcategoryLimitRepository;
import pl.smartbudget.repository.SubcategoryRepository;

@Service
public class SubcategoryLimitService {

	@Autowired
	private SubcategoryLimitRepository subcategoryLimitRepository;
	
	@Autowired
	private SubcategoryRepository subcategoryRepository;

	public void save(SubcategoryLimitForm subcategoryLimitForm) {
		SubcategoryLimit subcategoryLimit = new SubcategoryLimit();
		subcategoryLimit.setAmount(subcategoryLimitForm.getAmount());
		subcategoryLimit.setSubcategory(subcategoryRepository.getOne(subcategoryLimitForm.getSubcategoryId()));
		subcategoryLimitRepository.save(subcategoryLimit);			
	}

	public void delete(int id) {
		subcategoryLimitRepository.delete(id);		
	}

}

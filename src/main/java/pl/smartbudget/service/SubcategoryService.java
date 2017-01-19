package pl.smartbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import pl.smartbudget.entity.Subcategory;
import pl.smartbudget.entity.Transaction;
import pl.smartbudget.forms.SubcategoryForm;
import pl.smartbudget.repository.CategoryRepository;
import pl.smartbudget.repository.SubcategoryRepository;
import pl.smartbudget.repository.TransactionRepository;

@Service
public class SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepository;	

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
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
	
	@PreAuthorize("#userNameBySubcategoryId == authentication.name")
	public void delete(int subcategoryId, int newSubcategoryId,  @P("userNameBySubcategoryId") String userNameBySubcategoryId) {
		if(subcategoryId!=newSubcategoryId){
		List<Transaction> transactions=  transactionRepository.findBySubcategory(subcategoryRepository.getOne(subcategoryId));
		for(Transaction transaction: transactions){
			transaction.setSubcategory(subcategoryRepository.getOne(newSubcategoryId));
			transactionRepository.save(transaction);
		}		
		subcategoryRepository.delete(subcategoryId);}
	}

	public Subcategory findOne(int id) {		
		return subcategoryRepository.findOne(id);
	}
		
}

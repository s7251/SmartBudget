package pl.smartbudget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

	public void save(SubcategoryLimitForm subcategoryLimitForm) throws ParseException {
		String date = subcategoryLimitForm.getDate();
		String dateTodb = date.substring(3, 7) + "-" + date.substring(0, 2) + "-01";
		SubcategoryLimit subcategoryLimit = new SubcategoryLimit();
		subcategoryLimit.setAmount(subcategoryLimitForm.getAmount());
		subcategoryLimit.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateTodb));
		subcategoryLimit.setSubcategory(subcategoryRepository.getOne(subcategoryLimitForm.getSubcategoryId()));
		subcategoryLimitRepository.save(subcategoryLimit);
	}

	public void delete(int id) {
		subcategoryLimitRepository.delete(id);
	}

}

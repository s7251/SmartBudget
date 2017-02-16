package pl.smartbudget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

	public void change(SubcategoryLimitForm subcategoryLimitForm) throws ParseException {
		String date = subcategoryLimitForm.getDate();
		String dateTodb = date.substring(3, 7) + "-" + date.substring(0, 2) + "-01";
		SubcategoryLimit subcategoryLimit = new SubcategoryLimit();		
		subcategoryLimit.setAmount(subcategoryLimitForm.getAmount());
		subcategoryLimit.setId(subcategoryLimitForm.getId());
		subcategoryLimit.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateTodb));
		subcategoryLimit.setSubcategory(subcategoryRepository.getOne(subcategoryLimitForm.getSubcategoryId()));
		
		if (subcategoryLimit.getAmount()==0){
			subcategoryLimitRepository.delete(subcategoryLimit);
		}
		else{
			subcategoryLimitRepository.save(subcategoryLimit);
		}
		
	}
	
	public void add(SubcategoryLimitForm subcategoryLimitForm) throws ParseException {
		String date = subcategoryLimitForm.getDate();
		int month = Integer.parseInt(date.substring(0, 2));
		int year = Integer.parseInt(date.substring(3, 7));	
		List<SubcategoryLimit> subcategoryLimits = subcategoryLimitRepository.findBySubcategoryAndDate(subcategoryLimitForm.getSubcategoryId(),  month,  year );
		if(subcategoryLimits.isEmpty()){	
		String dateTodb = date.substring(3, 7) + "-" + date.substring(0, 2) + "-01";
		SubcategoryLimit subcategoryLimit = new SubcategoryLimit();
		subcategoryLimit.setAmount(subcategoryLimitForm.getAmount());	
		subcategoryLimit.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateTodb));
		subcategoryLimit.setSubcategory(subcategoryRepository.getOne(subcategoryLimitForm.getSubcategoryId()));
		if (subcategoryLimit.getAmount()==0){
			
		}
		else{
			subcategoryLimitRepository.save(subcategoryLimit);
		}
		}
	}

	public void delete(int id) {
		subcategoryLimitRepository.delete(id);
	}

}

package pl.smartbudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.forms.SubcategoryForm;
import pl.smartbudget.service.SubcategoryService;

@Controller
public class SubcategoryController {

	@Autowired
	private SubcategoryService subcategoryService;

	@RequestMapping(value = "/addSubcategory", method = RequestMethod.POST)
	public String addSubcategory(@ModelAttribute("subcategory") SubcategoryForm subcategory) {		
		subcategoryService.save(subcategory);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping(value = "/renameSubcategory", method = RequestMethod.POST)
	public String renameSubcategory(@ModelAttribute("subcategory") SubcategoryForm subcategory) {		
		subcategoryService.rename(subcategory);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping("/user-categories/removesubcategory/{id}")
	public String removeSubcategory(@PathVariable int id ){
		subcategoryService.delete(id);
		return "redirect:/user-categories.html";
	}
	




}

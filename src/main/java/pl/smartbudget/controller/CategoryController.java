package pl.smartbudget.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.smartbudget.entity.Category;
import pl.smartbudget.forms.RemoveCategoryForm;
import pl.smartbudget.forms.RemoveSubcategoryForm;
import pl.smartbudget.forms.SubcategoryForm;
import pl.smartbudget.service.CategoryService;
import pl.smartbudget.service.UserService;

@Controller
public class CategoryController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/user-categories")
	public String categories(Model model, Principal principal) {
		model.addAttribute("subcategory", new SubcategoryForm());
		model.addAttribute("category", new Category());
		String name = principal.getName();
		model.addAttribute("user", userService.findOneWithCategoriesAndSubcategories(name));
		model.addAttribute("RemoveSubcategoryForm", new RemoveSubcategoryForm());
		model.addAttribute("RemoveCategoryForm", new RemoveSubcategoryForm());
		model.addAttribute("subcategoriesMap", userService.getSubcategoriesMapOfUser(name));
		return "user-categories";
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category, Principal principal)	throws ParseException {
		String name = principal.getName();
		categoryService.save(category, name);
		return "redirect:/user-categories.html";
	}

	@RequestMapping(value = "/renameCategory", method = RequestMethod.POST)
	public String renameCategory(@ModelAttribute("category") Category category, Principal principal)	throws ParseException {
		String name = principal.getName();
		categoryService.save(category, name);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping("/user-categories/removecategory/{id}")
	public String removeCategory(@PathVariable int id,  Principal principal){
		Category category = categoryService.findOne(id);
		String name = principal.getName();		
		String userNameByCategoryId = userService.findUserNameByCategoryId(category);	
		categoryService.delete(category, name, userNameByCategoryId);
		return "redirect:/user-categories.html";
	}
	
	@RequestMapping("/user-categories/removecategory")
	public String removeCategory(@ModelAttribute("RemoveCategoryForm") RemoveCategoryForm removeCategory, Principal principal){
		Category category = categoryService.findOne(removeCategory.getCategoryId());
		String name = principal.getName();		
		String userNameByCategoryId = userService.findUserNameByCategoryId(category);	
		categoryService.delete(category, removeCategory.getNewSubcategoryId(), name, userNameByCategoryId);
		return "redirect:/user-categories.html";
	}
	
}

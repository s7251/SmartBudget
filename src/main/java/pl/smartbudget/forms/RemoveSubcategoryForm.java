package pl.smartbudget.forms;

public class RemoveSubcategoryForm {

	
	private Integer newSubcategoryId;
	private Integer subcategoryId;
	private Integer categoryId;
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getNewSubcategoryId() {
		return newSubcategoryId;
	}
	public void setNewSubcategoryId(Integer newSubcategoryId) {
		this.newSubcategoryId = newSubcategoryId;
	}
	public Integer getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	

}

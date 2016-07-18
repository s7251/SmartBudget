package pl.smartbudget.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Subcategory {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "subcategory")
	List<Transaction> transactions;

	@OneToMany(mappedBy = "subcategory")
	List<SubcategoryLimit> subcategoryLimits;

	public Subcategory() {
	}

	public Subcategory(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<SubcategoryLimit> getSubcategoryLimits() {
		return subcategoryLimits;
	}

	public void setSubcategoryLimits(List<SubcategoryLimit> subcategoryLimits) {
		this.subcategoryLimits = subcategoryLimits;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}

package pl.smartbudget.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	@Transient 
	private double summaryOfAccount;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	List<Transaction> transactions;

	public Account() {
	}

	public Account(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
	
	public double getSummaryOfAccount() {
		return summaryOfAccount;
	}

	public void setSummaryOfAccount(double summaryOfAccount) {
		this.summaryOfAccount = summaryOfAccount;
	}

}

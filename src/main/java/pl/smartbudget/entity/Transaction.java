package pl.smartbudget.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private Integer id;
	private String memo;
	private String type;
	private double amount;

	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategory;
	
	@OneToMany(mappedBy = "transaction", cascade = CascadeType.REMOVE)
	private List<Subtransaction> subtransactions;

	public Transaction() {
	}

	public Transaction(double amount, String type) {
		this.amount = amount;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public List<Subtransaction> getSubtransactions() {
		return subtransactions;
	}

	public void setSubtransactions(List<Subtransaction> subtransactions) {
		this.subtransactions = subtransactions;
	}

	

}

package pl.smartbudget.forms;

public class TransactionForm {

	private Integer id;
	private String memo;
	private String type;
	private double amount;
	private String date;
	private Integer accountId;
	private Integer subcategoryId;
	
	private String firstSplitMemo;
	private String secondarySplitMemo;
	private double firstSplitAmount;
	private double secondarySplitAmount;
	private Integer firstSplitSubcategoryId;
	private Integer secondarySplitSubcategoryId;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getFirstSplitMemo() {
		return firstSplitMemo;
	}

	public void setFirstSplitMemo(String firstSplitMemo) {
		this.firstSplitMemo = firstSplitMemo;
	}

	public String getSecondarySplitMemo() {
		return secondarySplitMemo;
	}

	public void setSecondarySplitMemo(String secondarySplitMemo) {
		this.secondarySplitMemo = secondarySplitMemo;
	}

	public double getFirstSplitAmount() {
		return firstSplitAmount;
	}

	public void setFirstSplitAmount(double firstSplitAmount) {
		this.firstSplitAmount = firstSplitAmount;
	}

	public double getSecondarySplitAmount() {
		return secondarySplitAmount;
	}

	public void setSecondarySplitAmount(double secondarySplitAmount) {
		this.secondarySplitAmount = secondarySplitAmount;
	}

	public Integer getFirstSplitSubcategoryId() {
		return firstSplitSubcategoryId;
	}

	public void setFirstSplitSubcategoryId(Integer firstSplitSubcategoryId) {
		this.firstSplitSubcategoryId = firstSplitSubcategoryId;
	}

	public Integer getSecondarySplitSubcategoryId() {
		return secondarySplitSubcategoryId;
	}

	public void setSecondarySplitSubcategoryId(Integer secondarySplitSubcategoryId) {
		this.secondarySplitSubcategoryId = secondarySplitSubcategoryId;
	}
	
	

}

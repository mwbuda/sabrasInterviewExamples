package sabras.example.account;

import java.util.Date;

public class Account {

	private String id ;
	private Boolean handled = true ;
	
	private String status ;
	private String category ;
	private String accountName ;
	private Date updateDate ;
	private Integer funds = 0 ;
	private Boolean hasBeenXProcessed = false ;
		
	public String getId() {
		return this.id ;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Boolean getHandled() {
		return handled;
	}
	public void setHandled(Boolean handled) {
		this.handled = handled;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(Date date) {
		this.updateDate = date;
	}

	public Integer getFunds() {
		return funds;
	}
	public void setFunds(Integer fs) {
		this.funds = fs;
	}
	public void addFunds(Integer fs) {
		this.funds += fs ;
	}
	public void subtractFunds(Integer fs) {
		this.funds -= fs ;
	}
	
	public Boolean getHasBeenXProcessed() {
		return hasBeenXProcessed;
	}
	public void setHasBeenXProcessed(Boolean hasBeenXProcessed) {
		this.hasBeenXProcessed = hasBeenXProcessed;
	}
}

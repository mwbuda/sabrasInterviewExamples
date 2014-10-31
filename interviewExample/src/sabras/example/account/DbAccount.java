package sabras.example.account;

import java.util.Date;

public class DbAccount {

	private String id ;
	
	private String accountHolderFirstName ;
	private String accountHolderLastName ;
	private String category ;
	private String status ;
	private Integer funds ;
	private Date creationDate ;
	private Date updateDate ;
	private Boolean hasBeenXd ;
	
	public String getId() {
		return this.id ;
	}
	public void setId(String x) {
		this.id = x ;
	}
	
	public String getAccountHolderFirstName() {
		return accountHolderFirstName;
	}
	public void setAccountHolderFirstName(String firstName) {
		this.accountHolderFirstName = firstName;
	}
	
	public String getAccountHolderLastName() {
		return accountHolderLastName;
	}
	public void setAccountHolderLastName(String lastName) {
		this.accountHolderLastName = lastName;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getFunds() {
		return funds;
	}
	public void setFunds(Integer funds) {
		this.funds = funds;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date date) {
		this.creationDate = date;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date date) {
		this.updateDate = date;
	}
	
	public Boolean getHasBeenXd() {
		return hasBeenXd;
	}
	public void setHasBeenXd(Boolean indicate) {
		this.hasBeenXd = indicate;
	}	
}

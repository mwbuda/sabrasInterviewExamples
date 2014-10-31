package sabras.example.account;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AccountSaveContext {
	
	private final Set<Account> webData = new HashSet<Account>();
	private final Set<DbAccount> daoData = new HashSet<DbAccount>();
	
	public AccountSaveContext(
		Collection<Account> webs,
		Collection<DbAccount> daos
	) {
		if (webs != null) this.webData.addAll(webs) ;
		this.webData.removeAll(null) ;
		if (daos != null) this.daoData.addAll(daos) ;
		this.daoData.removeAll(null) ;
	}

	public Set<Account> getWebData() {
		return webData;
	}

	public Set<DbAccount> getDaoData() {
		return daoData;
	}
	
	
	
}
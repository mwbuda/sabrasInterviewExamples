package sabras.example.account;

import java.util.ArrayList;
import java.util.Collection;

public interface AccountDao {

	public ArrayList<DbAccount> getAccountsByCategory(Collection<String> category);

	public void saveAccounts(Collection<DbAccount> accts);

}
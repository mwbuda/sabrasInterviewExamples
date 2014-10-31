package sabras.example.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class AccountUtility {

	private AccountDao accountDao ;
	
	public Collection<Account> getAccounts(Collection<String> categories) {
		Set<String> uniqCats = new HashSet<String>(categories) ;
		Collection<DbAccount> dbs = this.accountDao.getAccountsByCategory(uniqCats) ;
		Collection<Account> results = new LinkedList<Account>() ;
		
		for (DbAccount db : dbs) {
			Account web = new Account() ;
			this.doCopy(db, web); 
			results.add(web) ;
		}
		
		return results ;
	}
	
	public Collection<Account> processX(ArrayList<Account> accounts) 
	throws Throwable {
		Collection<Account> notProcessedAccounts = new LinkedList<Account>() ;
		Collection<Account> processedAccounts = new LinkedList<Account>() ;
		boolean foundFirst = false ;
		for ( int i = 0 ; i < accounts.size() ; i++) {
			Account account = accounts.get(i) ;
			
			if (
				(account.getStatus() != null)
				&&
				(account.getStatus() != "A" || account.getStatus() != "B" || account.getStatus() != "C")
				&&
				!account.getHasBeenXProcessed()
			) {
				if (!foundFirst) {
					account.addFunds(200);
					foundFirst = true ;
				}
			
				account.setHasBeenXProcessed(true); 
				processedAccounts.add(account) ;
			}
			
			else {
				account.setHandled(false);
				notProcessedAccounts.add(account) ;
			}
		}
		
		this.save(processedAccounts) ;
		
		Collection<Account> results = new ArrayList<Account>() ;
		results.addAll(processedAccounts) ;
		results.addAll(notProcessedAccounts) ;
		return results ;
	}
	
	public Collection<Account> save(Account... accounts) 
	throws Throwable {
		return this.save(Arrays.asList(accounts)) ;
	}
	public Collection<Account> save(Collection<Account> accounts) 
	throws Throwable {
		Date now = new Date() ;
		
		Set<String> categories = new HashSet<String>() ;
		for (Account account : accounts) categories.add(account.getId()) ;
		
		Collection<DbAccount> dbAccts = this.accountDao.getAccountsByCategory(categories) ;
		Collection<DbAccount> saveTheseDbAccts = new LinkedList<DbAccount>() ;
		
		Map<String,DbAccount> seenCache = new HashMap<String,DbAccount>() ;
		Iterator<DbAccount> daosIter = dbAccts.iterator() ;
		
		for (Account web : accounts) {
			String id = web.getId() ;
			DbAccount db = null ;
			
			if (seenCache.containsKey(id)) db = seenCache.get(id) ;
			if (db == null) while (daosIter.hasNext()) {
				DbAccount cdao = daosIter.next() ;
				String cdaoId = cdao.getId() ;
				seenCache.put(cdaoId, cdao) ;
				if (!cdaoId.equals(id)) continue ;
				db = cdao ;
			}
			
			if (db == null) db = new DbAccount() ;
			web.setUpdateDate(now);
			
			this.doCopy(web, db);
			web.setHandled(true);
			saveTheseDbAccts.add(db) ;
		}
		
		this.accountDao.saveAccounts(saveTheseDbAccts);
		return accounts ;
	}
	
	private void doCopy(Account web, DbAccount dao) {
		dao.setId(web.getId());
		dao.setCategory(web.getCategory());
		dao.setStatus(web.getStatus());
		
		int namespaceIndex = web.getAccountName().indexOf(' ') ;
		dao.setAccountHolderFirstName(web.getAccountName().substring(0, namespaceIndex));
		dao.setAccountHolderLastName(web.getAccountName().substring(namespaceIndex + 1));
		
		dao.setFunds(web.getFunds());
		dao.setHasBeenXd(web.getHasBeenXProcessed());
	}
	
	private void doCopy(DbAccount dao, Account web) {
		web.setId(dao.getId());
		web.setCategory(dao.getCategory());
		web.setStatus(dao.getStatus());
		
		web.setAccountName(
			dao.getAccountHolderFirstName() + " " + dao.getAccountHolderLastName()
		);
		web.setFunds(dao.getFunds());
		web.setHasBeenXProcessed(dao.getHasBeenXd());
	}
	
}

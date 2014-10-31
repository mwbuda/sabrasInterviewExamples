package sabras.example.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import sabras.example.restApi.JsonData;
import sabras.example.restApi.PathParam;
import sabras.example.restApi.RequestBody;
import sabras.example.restApi.RequestMapping;
import sabras.example.restApi.RequestParam;
import sabras.example.restApi.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountRestApi {

	private AccountUtility accountUtility ;
	
	@RequestMapping(value = "/{categories}/{ids}", method = "GET")
	public JsonData getAccounts(
		@PathParam("categories") String catsCsv, @PathParam("ids") String idsCsv
	) throws Throwable {
		Set<String> categories = new HashSet<String>(Arrays.asList(catsCsv.split(","))) ;
		Set<String> ids = new HashSet<String>(Arrays.asList(idsCsv.split(","))) ;
		
		Collection<Account> accounts = this.accountUtility.getAccounts(categories) ;
		Collection<JsonData> results = new LinkedList<JsonData>() ;
		
		for (Account account : accounts) {
			if (ids.contains(account.getId())) {
				Map<String,Object> view = new HashMap<String,Object>() ;
				
				view.put("id", account.getId()) ;
				view.put("category", account.getCategory()) ;
				view.put("status", account.getStatus()) ;
				view.put("accountHolder", account.getAccountName()) ;
				view.put("balance", account.getFunds()) ;
				view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
				
				JsonData json = JsonData.convertToJson(view) ;
				results.add(json) ;
			}
		}
		
		Map<String,Object> responseBody = new HashMap<String,Object>() ;
		responseBody.put("accounts", results) ;
		return JsonData.convertToJson(responseBody) ;
	}
	
	private Account convertToAccount(JsonData raw) {
		//TODO: use your imagination
		
		return new Account() ;
	}
	
	private Map<String,Object> convertToMap(JsonData raw) {
		//TODO: use your imagination
		
		return new HashMap<String,Object>() ;
	}
	
	@RequestMapping(method = "PUT")
	public JsonData createAccount(
		@RequestBody JsonData body
	) throws Throwable {
		Collection<Account> serviceResults = this.accountUtility.save(this.convertToAccount(body)) ;
		Account account = (new ArrayList<Account>(serviceResults)).get(0) ;
		
		Map<String,Object> view = new HashMap<String,Object>() ;
		
		view.put("id", account.getId()) ;
		view.put("category", account.getCategory()) ;
		view.put("status", account.getStatus()) ;
		view.put("accountHolder", account.getAccountName()) ;
		view.put("balance", account.getFunds()) ;
		view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
		
		return JsonData.convertToJson(view) ;
	}
	
	@RequestMapping(value = "/{categories}/{ids}", method = "POST")
	public JsonData processAccounts(
		@PathParam("categories") String catsCsv,
		@PathParam("ids") String idsCsv,
		@RequestParam("action") String action,
		@RequestBody JsonData body
	) throws Throwable {
		Set<String> categories = new HashSet<String>(Arrays.asList(catsCsv.split(","))) ;
		Set<String> ids = new HashSet<String>(Arrays.asList(idsCsv.split(","))) ;
		
		Collection<Account> allAccounts = this.accountUtility.getAccounts(categories) ;
		Collection<Account> procAccounts = new LinkedList<Account>() ;
		for (Account account : allAccounts) if (ids.contains(account.getId())) procAccounts.add(account) ;
		
		switch (action) {
			case "xprocess": return this.xProcessAccounts(body, procAccounts) ;
			case "updateStatus": return this.updateAccountStatus(body, procAccounts) ;
			default: throw new Exception("unsupported action") ;
		}
	}
	
	private JsonData updateAccountStatus(JsonData body, Collection<Account> accounts)
	throws Throwable {
		Map<String,Object> bodyAsMap = this.convertToMap(body) ;
		String newStatus = (String) bodyAsMap.get("status") ;
		
		for (Account account : accounts) account.setStatus(newStatus);
		Collection<Account> serviceAccounts = this.accountUtility.save(accounts) ;
		
		Collection<JsonData> handledResults = new LinkedList<JsonData>() ;
		Collection<JsonData> unhandledResults = new LinkedList<JsonData>() ;
		for (Account account : serviceAccounts) {
			if (account.getHandled()) {
				Map<String,Object> view = new HashMap<String,Object>() ;
				
				view.put("id", account.getId()) ;
				view.put("category", account.getCategory()) ;
				view.put("status", account.getStatus()) ;
				view.put("accountHolder", account.getAccountName()) ;
				view.put("balance", account.getFunds()) ;
				view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
				
				JsonData json = JsonData.convertToJson(view) ;
				handledResults.add(json) ;
			}
			
			else {
				Map<String,Object> view = new HashMap<String,Object>() ;
				
				view.put("id", account.getId()) ;
				view.put("category", account.getCategory()) ;
				view.put("status", account.getStatus()) ;
				view.put("accountHolder", account.getAccountName()) ;
				view.put("balance", account.getFunds()) ;
				view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
				
				JsonData json = JsonData.convertToJson(view) ;
				unhandledResults.add(json) ;
			}
		}
		
		Map<String,Object> responseBody = new HashMap<String,Object>() ;
		responseBody.put("handledAccounts", handledResults) ;
		responseBody.put("unhandledAccounts", unhandledResults) ;
		return JsonData.convertToJson(responseBody) ;
	}
	
	private JsonData xProcessAccounts(JsonData body, Collection<Account> accounts)
	throws Throwable {
		ArrayList<Account> asArrayList = new ArrayList<Account>(accounts) ;
		Collection<Account> serviceAccounts = this.accountUtility.processX(asArrayList) ;
		
		Collection<JsonData> handledResults = new LinkedList<JsonData>() ;
		Collection<JsonData> unhandledResults = new LinkedList<JsonData>() ;
		for (Account account : serviceAccounts) {
			if (account.getHandled()) {
				Map<String,Object> view = new HashMap<String,Object>() ;
				
				view.put("id", account.getId()) ;
				view.put("category", account.getCategory()) ;
				view.put("status", account.getStatus()) ;
				view.put("accountHolder", account.getAccountName()) ;
				view.put("balance", account.getFunds()) ;
				view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
				
				JsonData json = JsonData.convertToJson(view) ;
				handledResults.add(json) ;
			}
			
			else {
				Map<String,Object> view = new HashMap<String,Object>() ;
				
				view.put("id", account.getId()) ;
				view.put("category", account.getCategory()) ;
				view.put("status", account.getStatus()) ;
				view.put("accountHolder", account.getAccountName()) ;
				view.put("balance", account.getFunds()) ;
				view.put("hasBeenSomethinged", account.getHasBeenXProcessed()) ;
				
				JsonData json = JsonData.convertToJson(view) ;
				unhandledResults.add(json) ;
			}
		}
		
		Map<String,Object> responseBody = new HashMap<String,Object>() ;
		responseBody.put("handledAccounts", handledResults) ;
		responseBody.put("unhandledAccounts", unhandledResults) ;
		return JsonData.convertToJson(responseBody) ;
	}
	
}

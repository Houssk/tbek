
import java.util.Iterator;
import java.util.Vector;

import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_FORMAT;
import net.billylieurance.azuresearch.AzureSearchNewsQuery;
import net.billylieurance.azuresearch.AzureSearchNewsResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;
public class BingSearch {
	// **Update your Account ID - https://datamarket.azure.com/account.
	
	private static final String ACCOUNT_KEY = "oLnzdYxQ+FfGVysn751kjLkO5GPkWzb5yILRH33G908";
	public static Vector<String> search(String searchParam) {
		Vector<String> lesNews= new Vector<String>();
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setAppid(ACCOUNT_KEY);
		aq.setFormat(AZURESEARCH_FORMAT.XML);
		// searchParam is the field containing the keyword to be searched.
		aq.setQuery(searchParam);
	  //aq.setLocationOverride("FRANCE");
		aq.doQuery();
		AzureSearchResultSet<AzureSearchNewsResult> azureSearchResultSet = aq.getQueryResult();
		for (Iterator<AzureSearchNewsResult> iterator = azureSearchResultSet.iterator(); iterator.hasNext();) {
			AzureSearchNewsResult result = (AzureSearchNewsResult) iterator.next();
			// Populate the data from result object in to your custom objects.
			lesNews.add(result.getTitle());
			lesNews.add(result.getSource());
			lesNews.add(result.getDate());
		}
		return lesNews;
	}
	public static void main(String[] args) {
		final Vector<String> lesTendances = Tendance.getTrend();
		Vector lesnews= new Vector();
		BingSearch bingSearchService = new BingSearch();
		//for(String s:lesTendances)
		//{
			Vector<String> l=bingSearchService.search("fffffffffffffffffffffffffgdfgfdgfdgfffffffff");
			lesnews.add(l);
		//}
		System.out.println(lesnews.size());
		
	}
}
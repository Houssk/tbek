
import java.util.Iterator;
import java.util.Vector;

import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_FORMAT;
import net.billylieurance.azuresearch.AzureSearchNewsQuery;
import net.billylieurance.azuresearch.AzureSearchNewsResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;
/**
 * @author Houssam Karrach
 * @author Ayoub El Fatmi
 * @version 0.1, ecrit le 9 Novembre
 * 
 * Classe faisant le lien avec la bibliotheque azuresearch Elle permet de 
 * recuperer les news d'une tendance, grace a une methode de 
 * asuresearch
 */

public class BingSearch {
/**
 * @author Houssam Karrache
 * @author Ayoub El Fatmi
 * @version 0.1, ecrit le 9 Novembre
 * 
 * Cette methode permet de recuperer les articles d'une tendance
 * passee en parametre
 * 
 * @return Vector<String>   Renvoie un tableau de String
 *                          Ces Strings sont les articles tries par date d'une tendance
 */  
	
	private static final String ACCOUNT_KEY = "oLnzdYxQ+FfGVysn751kjLkO5GPkWzb5yILRH33G908";
	
	public static Vector<String> search(String searchParam) {
		
		
		
		// Codes necessaires a l'authentification lors de la recuperation des articles de Bing news 
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setAppid(ACCOUNT_KEY);
		
		// on ajoute le Market "France" pour recuperer des articles en français 
		aq.setMarket("fr-FR");
		// on trie les articles par date 
		aq.setSortBy("Date");
		// on fixe le nombre d'article a 10 par page 
		aq.setPerPage(10);
		// on fixe le nombre des pages a 1
		aq.setPage(1);
		// on recupere les articles sous format XML
		aq.setFormat(AZURESEARCH_FORMAT.XML);
		
		// on recherche le mot-cle
		aq.setQuery(searchParam);
		aq.doQuery();
		
		
		// Recuperation du titre,source et date d'article et les mettres dans un vecteur de String
		Vector<String> lesNews= new Vector<String>();
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
		BingSearch bingSearchService = new BingSearch();
		Vector<String> l=bingSearchService.search("real madrid");
			System.out.println(l);
		
	}
}
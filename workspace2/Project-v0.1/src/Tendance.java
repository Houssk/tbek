import java.util.Vector;

import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TrendsResources;
import twitter4j.conf.ConfigurationBuilder;


/**
 * @author Houssam Karrach
 * @author Ayoub El Fatmi
 * @author Julien Tissier
 * @version 0.1, ecrit le 1 Novembre
 * 
 * Classe faisant le lien avec la bibliotheque twitter4j. Elle permet de 
 * recuperer les tendances actuelles de twitter, grace a une methode de 
 * twitter4j.
 */
public class Tendance {

/**
 * @author Houssam Karrache
 * @author Ayoub El Fatmi
 * @author Julien Tissier
 * @version 0.1, ecrit le 1 Novembre
 * 
 * Cette methode permet de recuperer les tendances actuelles de Twitter.
 * Elle prend en compte les exceptions eventuelles lors de la recuperation.
 * S'il y avait un quelconque souci qui ne pemet de recuperer correctement
 * les tendances actuelles de Twitter, alors le vector de sortie ne 
 * comporterait qu'un seul element, qui est "Aucune tendances recuperees"
 * 
 * @param Vector<String>    Cette methode renvoie un tableau de String
 *                          Ces Strings sont les tendances actuelles de twitter
 */    
	public static Vector<String> getTrend() {
       
        
        // Codes necessaires a l'authentification lors de la recuperation des tendances de Twitter  
        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("1BgjmryhAhIDhWxn043Ftky77")
            .setOAuthConsumerSecret("zRqOz2gWrv9gF3xt38ezbbXOacDU2TuzlU4v0b3XHlOj8X7y2x")
            .setOAuthAccessToken("537787500-hBjyD0ylvXTGiAhtR1xeaz2q0NVCBnZiTI9x9J67")
            .setOAuthAccessTokenSecret("t29NAKmblM0B1Il4hdn0apshnv9GncxTuMRkm9ESdnX6N");

 
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter tw = tf.getInstance();
		
        // Les tendances se recuperent par location. Chaque location est associee a un numero.
        // Si on veut les tendances d'une location differente, on change le parametre de 
        // l'appel a la fonction getPlaceTrends()
		int monde = 1;
		int paris = 615702;
		int france = 23424819; 
        
        Vector<String> currentTrends = new Vector<String>();
        
        
		try {
            
            // Recupere les tendances suivant la location donnee
            Trends trend =tw.getPlaceTrends(france);
            
            // Pour chaque tendances recuperees, on ajoute son intitule au vecteur de sortie
            for(Trend t : trend.getTrends()) {
         
                String name = t.getName();
                currentTrends.add(name);
		
                }
            }
        // Rattrape les exceptions eventuelles de getPlaceTrends(). La taille
        // du vecteur de sortie indique le bon deroulement :
        // --> taille de 10 : pas de soucis
        // --> taille de 1 : il y a eu un probleme lors de la recuperation des tendances
        catch (TwitterException err) {
            currentTrends.add("Aucunes tendances recuperees.");
            }
        
	
		return currentTrends;	
        
    }
        
        /*  T E S T
         * 
         * public static void main(String[] args)
        {
            Vector<String> b = getTrend();
            for (String s : b)
            {
                System.out.println(s);
            }
        }
        */
        
    }


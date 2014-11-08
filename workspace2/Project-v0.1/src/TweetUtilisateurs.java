import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TrendsResources;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Query;
import twitter4j.QueryResult;

import java.util.List;
import java.util.Vector;

/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 1 Novembre
 * 
 * Classe faisant le lien avec la bibliotheque twitter4j. Elle permet 
 * de recuperer un certain nombre de tweets qui sont relatifs a une tendance
 */
public class TweetUtilisateurs {
    
/**
 * @author Julien Tissier
 * @version 0.1, ecrit le 1 Novembre
 * 
 * Methode recuperant 10 tweets en rapport avec la tendance passee en 
 * parametre. Elle prend en compte les exceptions eventuelles lors de 
 * la recuperation des tweets. S'il y avait un quelconque souci qui ne 
 * pemet de recuperer correctement les tweets, alors le vector de sortie ne 
 * comporterait qu'un seul element, qui est "Erreur lors de la recuperation des Tweets."
 * 
 * @param hashtag     String   C'est la tendance sur laquelle on veut
 *                            obtenir 10 tweets
 * @param Vector<String>      retourne un vector de String. Les auteurs
 *                            et le contenu sont separes. C'est-a-dire
 *                            que le vecteur est de la forme
 *    [auteur tweet 1; contenu tweet #1; auteur tweet 2; contenu tweet 2; ..]   
 */
    public static Vector<String> recupere_Tweets (String hashtag) {
        
        // Initialisation de l'instance de la bibliotheque twitter4j. Besoin de ces codes pour recuperer des donnees via l'API de Twitter
        twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("1BgjmryhAhIDhWxn043Ftky77")
            .setOAuthConsumerSecret("zRqOz2gWrv9gF3xt38ezbbXOacDU2TuzlU4v0b3XHlOj8X7y2x")
            .setOAuthAccessToken("537787500-hBjyD0ylvXTGiAhtR1xeaz2q0NVCBnZiTI9x9J67")
            .setOAuthAccessTokenSecret("t29NAKmblM0B1Il4hdn0apshnv9GncxTuMRkm9ESdnX6N");
        
        
        TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();
        
        
        // Vector qui contiendra les tweets
        Vector<String> liste_tweets = new Vector<String>();
        
        // Les exceptions sont recuperees et traitees dans cette classe. De 
        // la même manière que pour la classe Tendance, la longueur du vecteur de sortie
        // nous indiquera le bon fonctionnement, ou non
        
        try {
            Query query = new Query(hashtag);  
            query.setCount(10); //indique le nombre de tweets par requete          
            QueryResult result;
            
            result = twitter.search(query);// rechereche des tweets contenant le hashtag
            List<Status> tweets = result.getTweets();
                
            for (Status tweet : tweets) {
                // Pour chaque tweet de la requete, on l'ajoute a notre vector de sortie
                // On ajoute d'abord le pseudo de l'auteur du tweet, et 
                // ensuite le contenu du tweet
                liste_tweets.add("@" + tweet.getUser().getScreenName());
                liste_tweets.add(tweet.getText());
                    
            }
                
        } catch (TwitterException te) {
            //En cas de problemes lors de la recuperation des tweets, alors notre vector de sortie ne contiendra qu'un seul element. On peut alors savoir grace à sa taille si cela a echoue.
            // Si il n'y a aucun soucis, le vecteur de sortie a une taille de :
            //      (nombre de tweets) * 2
            liste_tweets.add("Erreur lors de la recuperation des Tweets.");
            }
            
        return liste_tweets;
    }   
    
     
    /*      T E S T
     * 
    public static void main(String[] args) 
    {
        
        Vector<String> l = recupere_Tweets("Lyon");
        for (String s : l)
        {
            System.out.println(s);
        }
        
        // Permet de verifier que l'n a bien 20 elements.
        System.out.println(l.size());
    }
    * 
    */
}

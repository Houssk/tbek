import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.*;

/**
 * @author Julien Tissier
 * @author Nader Ben Abdeljelil
 * @author Houssam Karrach
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Classe representant l'interface graphique. L'ensemble des widgets (bouton, liste)
 * de l'interface graphique sont declares dans cette classe. Les actions
 * listener sont aussi declares dans cette classe.
 * */

public class InterfaceTrendTweets {

/**
 * @author Nader Ben Abdeljelil
 * @author Houssam Karrach
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Methode qui decoupe un tweet en blocs de 40 caracteres.
 * Cette methode prend en compte les emplacements des espaces du message 
 * afin de ne pas couper en plein milieu d'un mot.
 * 
 * @param message   String  C'est le contenu du tweet
 * @param auteur    String  C'est l'auteur du tweet
 * 
 * @return  String      Elle renvoie un message au format html,  qui 
 *                      permet de visualiser de maniere correcte les 
 *                      tweets
 */
    public static String decouper(String message,String auteur) {
		
        // Utilise le html pour rendre les tweets presentables. 
        String element_formate = "<html><i><font color=blue>"+ auteur +"</i><br>";
       
       // oblige de couper les messages sur 40 caracteres, sinon
       // il n'y a pas de line wrapping et l'on est oblige d'utiliser
       // la scrollbar horizontale pour voir l'integralite du message
       
       //on parcourt l'integralite du message, 40 caracteres par 40 caracteres
       for (int start = 0; start < message.length(); start += 40)
       {
           // il reste plus de 40 caracteres
           if (start < message.length() - 40)
           {
                // si l'on ne coupe pas au milieu d'un mot, alors on peut decouper
                if(message.charAt(start+39)==' ')
                {
                    element_formate += message.substring(start,start+40) + "</br><br>";	
                }
                
                // sinon on revient au denier espace avant la coupure
                else
                {
                    int pos=1;
                    while(message.charAt(start+39-pos)!=' ')
                    {
                        pos++;
                    }
               	
                    element_formate += message.substring(start,start+40-pos) + "</br><br>";
                    start -= pos; // on reprend au niveau de cette coupure
                }
           	
           }
            
            // si il reste moins de 40 caracteres, alors on decoupe 
            // jusqu'a la fin du message
           else
           {
               element_formate += message.substring(start);
           }
         }
       
       element_formate += "</br></font></html>";
       return element_formate;
	}
    
    /**
     * @author Nader Ben Abdeljelil
     * @author Houssam Karrach
     * @version 0.1, ecrit le 11 Novembre 2014
     * 
     * Methode qui decoupe un titre de news en blocs de 40 caracteres.
     * Cette methode prend en compte les emplacements des espaces du message 
     * afin de ne pas couper en plein milieu d'un mot.
     * 
     * @param title   String  C'est le titre de l'atricle
     * @param source    String  C'est le source de l'article
     * @param date   String  C'est la date de l'article
     * 
     * @return  String      Elle renvoie un message au format html,  qui 
     *                      permet de visualiser de maniere correcte les 
     *                      news
     */
    
public static String decouperNews(String title,String source,String date) {
		
        // Utilise le html pour rendre les news presentables. 
        String element_formate = "<html><i><font color=blue>";
       
       // oblige de couper les titres sur 40 caracteres, sinon
       // il n'y a pas de line wrapping et l'on est oblige d'utiliser
       // la scrollbar horizontale pour voir l'integralite du news
       
       //on parcourt l'integralite du titre, 40 caracteres par 40 caracteres
       for (int start = 0; start < title.length(); start += 40)
       {
           // il reste plus de 40 caracteres
           if (start < title.length() - 40)
           {
                // si l'on ne coupe pas au milieu d'un mot, alors on peut decouper
                if(title.charAt(start+39)==' ')
                {
                    element_formate += title.substring(start,start+40) + "</br><br>";	
                }
                
                // sinon on revient au denier espace avant la coupure
                else
                {
                    int pos=1;
                    while(title.charAt(start+39-pos)!=' ')
                    {
                        pos++;
                    }
               	
                    element_formate += title.substring(start,start+40-pos) + "</br><br>";
                    start -= pos; // on reprend au niveau de cette coupure
                }
           	
           }
            
            // si il reste moins de 40 caracteres, alors on decoupe 
            // jusqu'a la fin du titre
           else
           {
               element_formate += title.substring(start);
           }
         }
       
       element_formate += "</i><br>"+source+"</br><br>"+date+"</br></font></html>";
       return element_formate;
	}
	
/**
 * @author Julien Tissier
 * @author Nader Ben Abdeljelil
 * @version 0.1, ecrit le 1 Novembre 2014
 * 
 * Methode principal qui cree la fenetre d'affichage ainsi que tous les
 * widgets la composant.
 */    
   public static void main(String[] args) {

        
        // Recuperation des tendances actuelles
		final Vector<String> lesTendances = Tendance.getTrend();
		final Vector lesTweets = new Vector();
		final Vector lesNews = new Vector();
        
		
        // Pour chaque tendances, recuperation de 10 tweets relatifs
        for (String hashtag : lesTendances)
        {
            // some_tweets est un vector de 10 tweets, donc lesTweets
            // est un vector de vector
            Vector<String> some_tweets = TweetUtilisateurs.recupere_Tweets(hashtag);
            lesTweets.add(some_tweets);
        }
        // Pour chaque tendances, recuperation des news relatifs
        for (String hashtag : lesTendances)
        {
        	// some_news est un vector de news 
            Vector<String> some_news = BingSearch.search(hashtag);
            lesNews.add(some_news);
        }    
			
		
		// Creation de la fenetre ainsi que de tous les elements la composant		
		final JFrame f = new JFrame("Liste tweets tendances");
		JPanel global_panel = new JPanel();
		global_panel.setLayout(new BorderLayout());

		JPanel titre_panel = new JPanel();
		JLabel titre = new JLabel("Liste des tendances");
		titre_panel.add(titre);

		JPanel button_panel = new JPanel();
		JButton actualiser = new JButton("Actualiser");
		actualiser.setPreferredSize(new Dimension(100, 30));
		button_panel.add(actualiser);
		
        
        // trois listes avec srollbar : 
        //  -->  une a gauche pour les tendances
        //  -->  une au centre pour les tweets
		//  -->  une a droite pour les news
        
		JScrollPane selection_tendances = new JScrollPane();
        // La liste a gauche doit pouvoir etre modifiable. Ce n'est pas
        // possible avec une JListe, donc on utilise un listModel
        final DefaultListModel listeModel_tendances = new DefaultListModel();
		final JList liste_tendances = new JList(listeModel_tendances);
		selection_tendances.setViewportView(liste_tendances);
		selection_tendances.setPreferredSize(new Dimension(300,260));
        
        // remplit la liste avec les tendances
        for (String tendance : lesTendances)
        {
            listeModel_tendances.addElement(tendance);
        }
		
        
        JScrollPane selection_tweets = new JScrollPane();
		// La liste a droite doit pouvoir etre modifiable. Ce n'est pas
        // possible avec une JListe, donc on utilise un listModel
        final DefaultListModel listeModel_tweets = new DefaultListModel();
        final JList liste_tweets = new JList(listeModel_tweets);
        selection_tweets.setViewportView(liste_tweets);
        selection_tweets.setPreferredSize(new Dimension(350,260));
        
        JScrollPane selection_news = new JScrollPane();
        final DefaultListModel listeModel_news = new DefaultListModel();
        final JList liste_news = new JList(listeModel_news);
        selection_news.setViewportView(liste_news);
        selection_news.setPreferredSize(new Dimension(350,260));
        
		
        
        
		// A chaque fois que l'on clique sur une tendance, il faut 
        // mettre a jour les 2 listes (centre et a droite) , c'est-a-dire la vider et
        // les remplir avec les tweets et les news de la tendance selectionnee
		MouseListener mouseListener = new MouseAdapter() 
        {
		    public void mouseClicked(MouseEvent e) 
            {
		        if (e.getClickCount() == 1) 
                {
                    // enleve tous les elements de la liste tweets+news
                    listeModel_tweets.removeAllElements();
                    listeModel_news.removeAllElements();
                    
                    // Recupere l'indice de l'item selectionne pour avoir les tweets et news corespondants
                    int index = (int) liste_tendances.getSelectedIndex();
                    
                    // On va parcourir le vector des tweets concernant 
                    // la tendance selectionnee
                    int size = ((Vector<String>) lesTweets.get(index)).size();
                    
                    for (int i = 0; i < size; i = i+2)
                    {
                        String auteur = ((Vector<String>) lesTweets.get(index)).get(i);
                        String message = ((Vector<String>) lesTweets.get(index)).get(i+1);
                        String element_formate = decouper(message,auteur);
                            
                        // ajoute l'auteur ainsi que son message a la liste des tweets
                        listeModel_tweets.addElement(element_formate);

                    }
                    // On va parcourir le vector des tweets concernant 
                    // la tendance selectionnee
                    int size2 = ((Vector<String>) lesNews.get(index)).size();
                    String element_news= new String();
                   if(((Vector<String>) lesNews.get(index)).size()!=0){
                    for (int i = 0; i < size2; i = i+3)
                    {
                        String title = ((Vector<String>) lesNews.get(index)).get(i);
                        String source = ((Vector<String>) lesNews.get(index)).get(i+1);
                        String date = ((Vector<String>) lesNews.get(index)).get(i+2);
                        String newDate=date.substring(0, 10)+" à "+ date.substring(11, 19);
                       // Utilise le html pour rendre les tweets presentables. 
                        element_news=decouperNews(title,source,newDate);
                        // ajoute le titre,source et date a la liste des news
                        listeModel_news.addElement(element_news);

                    }
                   }
                   else {
                	   element_news=" Il n ya pas de news relatifs à cette tendance";
                	   listeModel_news.addElement(element_news);
                   }
                    }
                    
              }
		};
		liste_tendances.addMouseListener(mouseListener);
        
        
        // A chaque fois que l'on clique sur le bouton Actualiser, on 
        // doit verifier s'il y a des nouvelles tendances. Si c'est le
        // cas, on met à jour la liste des tendances, la liste des 
        // tweets et news pour chaque nouvelle tendance, et les affichages de 
        // ces deux listes
        ActionListener actualisation = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // On recupere les nouvelles tendances
                Vector<String> nouvelles_tendances = Tendance.getTrend();
                
                // Variable permettant de savoir s'il y a des nouvelles 
                // tendances
                boolean est_different = false;
                
                // On parcourt les deux vectors pour voir s'il y a des differences
                for (int i = 0; i < nouvelles_tendances.size(); i++)
                {
                    // Si l'une des tendances est differente
                    if (!(nouvelles_tendances.get(i).equals(lesTendances.get(i))))
                    {
                        // on supprime les tendances
                        lesTendances.clear();
                        
                        // on supprime l'ensemble des tweets, puisqu'il faut updater
                        lesTweets.clear();
                        
                        //on supprime l'ensemble des news
                        lesNews.clear();
                        
                        // on vide la liste des anciennes tendances et on la remplit a nouveau
                        listeModel_tendances.removeAllElements();
                        for (String tendance : nouvelles_tendances)
                        {
                            lesTendances.add(tendance);
                            // recherche 10 tweets relatifs a la nouvelle tendance
                            Vector<String> some_tweets = TweetUtilisateurs.recupere_Tweets(tendance);
                            lesTweets.add(some_tweets);
                            // recherche les news relatifs a la nouvelle tendance
                            Vector<String> some_news = BingSearch.search(tendance);
                            lesNews.add(some_news);
                            listeModel_tendances.addElement(tendance);
                        }
                        
                        // on indique qu'il y a eu un changement
                        est_different = true;
                        
                        // sort de la boucle for. ca ne sert a rien de 
                        // poursuivre, on a tout updater
                        break; 
                    }
                }
                
                // si apres avoir parcouru les deux vectors, aucune difference
                // n'a ete trouvee, alors on previent l'utilisateur via une 
                // boite de dialogue qu'il n'y a pas de nouvelles tendances
                if (!est_different)
                {
                    JOptionPane.showMessageDialog(f, "Il n'y a pas de nouvelles tendances.");
                }
            }
        };
        actualiser.addActionListener(actualisation);
                        
                        


        // Placement des differents widgets dans la fenetre
		global_panel.add(titre_panel, BorderLayout.NORTH);	
		global_panel.add(button_panel, BorderLayout.SOUTH);
		global_panel.add(selection_tendances, BorderLayout.WEST);	
		global_panel.add(selection_tweets, BorderLayout.CENTER);
		global_panel.add(selection_news, BorderLayout.EAST);


		
		f.setVisible(true);	
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(global_panel);
		f.setSize(1000, 300);
	

    }

}

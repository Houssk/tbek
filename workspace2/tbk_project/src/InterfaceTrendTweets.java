import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.*;


public class InterfaceTrendTweets {

	public static String decouper(String message,String auteur){
		String element_formate = "<html><i><font color=blue>"+ auteur +"</i><br>";
		int length=message.length();
		int i=0;
		int pos=40;
		int pos2=0;
		while(i<length)
		{
          if(i<length-40){
			if(i<40)
			{
				if(message.charAt(i+pos)!=' '){
					while(message.charAt(i+pos)!=' '){
						pos--;
					}
				}
				element_formate+=message.substring(i,i+pos)+ "</br><br>";
			}
			else{
				pos2=40+pos;
				if(message.charAt(pos2)!=' '){
					while(message.charAt(pos2)!=' '){
						pos2--;
					}
				}
				element_formate+=message.substring(pos+1,pos2)+ "</br><br>";
				pos=pos2;
				}
          }
         else{
        	  element_formate += message.substring(pos2+1);
          }
          i+=40;
		}
		element_formate += "</br></font></html>";
		return element_formate;
		 }
	public static void main(String[] args) {

        
        // Recuperation des tendances actuelles
		final Vector<String> lesTendances = Tendance.getTrend();
        final Vector lesTweets = new Vector();
        
        
        // Pour chaque tendances, recuperation de 10 tweets relatifs
        for (String hashtag : lesTendances)
        {
            Vector<String> some_tweets = TweetUtilisateurs.recupere_Tweets(hashtag);
            lesTweets.add(some_tweets);
        }
            
			
		
		// Creation de la fenetre ainsi que de tous les elements la composant		
		JFrame f = new JFrame("Liste tweets tendances");
		JPanel global_panel = new JPanel();
		global_panel.setLayout(new BorderLayout());

		JPanel titre_panel = new JPanel();
		JLabel titre = new JLabel("Liste des tendances");
		titre_panel.add(titre);

		JPanel button_panel = new JPanel();
		JButton actualiser = new JButton("Actualiser");
		actualiser.setPreferredSize(new Dimension(100, 30));
		button_panel.add(actualiser);
        
        // Deux listes avec srollbar : 
        //  -->  une a gauche pour les tendances
        //  -->  une a droite pour les tweets
        
		JScrollPane selection_tendances = new JScrollPane();
        // La liste a gauche doit pouvoir etre modifiable. Ce n'est pas
        // possible avec une JListe, donc on utilise un listModel
        final DefaultListModel listeModel_tendances = new DefaultListModel();
		final JList liste_tendances = new JList(listeModel_tendances);
		selection_tendances.setViewportView(liste_tendances);
        
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
		
        
        
		// A chaque fois que l'on clique sur une tendance, il faut 
        // mettre a jour la liste de droite, c'est-a-dire la vider et
        // la remplir avec les tweets de la tendance selectionnee
		MouseListener mouseListener = new MouseAdapter() 
        {
		    public void mouseClicked(MouseEvent e) 
            {
		        if (e.getClickCount() == 1) 
                {
                    // eneleve tous les elements de la liste
                    listeModel_tweets.removeAllElements();
                    // Recupere l'indice de l'item selectionne pour avoir les tweets corespondants
                    int index = (int) liste_tendances.getSelectedIndex();
                    int size = ((Vector<String>) lesTweets.get(index)).size();
                    
                    for (int i = 0; i < size; i = i+2)
                    {
                        String auteur = ((Vector<String>) lesTweets.get(index)).get(i);
                        String message = ((Vector<String>) lesTweets.get(index)).get(i+1);
                        String element_formate=decouper(message,auteur);
                        
                        // Utilise le html pour rendre les tweets presentables. 
                        // String element_formate = "<html><i><font color=blue>"+ auteur +"</i><br>";
                        
                        // oblige de couper les messages sur 40 caracteres, sinon
                        // il n'y a pas de line wrapping et c'est penible a lire
                        // Decoupage a revoir, parfois on coupe au milieu d'un mot
//                        for (int start = 0; start < message.length(); start += 40)
//                        {
//                            if (start < message.length() - 40)
//                            {
//                                element_formate += message.substring(start, start+40) + "</br><br>";
//                            }
//                            else
//                            {
//                                element_formate += message.substring(start);
//                            }
//                        }
                        
//                        element_formate += "</br></font></html>";
                            
                        // ajoute l'auteur ainsi que son message a la liste des tweets
                        listeModel_tweets.addElement(element_formate);

                    }
		         }
              }
		};
		liste_tendances.addMouseListener(mouseListener);
        
        ActionListener actualisation = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // On recupere les nouvelles tendances
                Vector<String> nouvelles_tendances = Tendance.getTrend();
                
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
                        
                        // on vide la liste des anciennes tendances et on la remplit a nouveau
                        listeModel_tendances.removeAllElements();
                        for (String tendance : nouvelles_tendances)
                        {
                            lesTendances.add(tendance);
                            // recherche 10 tweets relatifs a la nouvelle tendance
                            Vector<String> some_tweets = TweetUtilisateurs.recupere_Tweets(tendance);
                            lesTweets.add(some_tweets);
                            listeModel_tendances.addElement(tendance);
                        }
                        
                        break; // ca ne sert a rien de poursuivre, on a tout updater
                    }
                }
            }
        };
        actualiser.addActionListener(actualisation);
                        
                        



		global_panel.add(titre_panel, BorderLayout.NORTH);	
		global_panel.add(button_panel, BorderLayout.SOUTH);
		global_panel.add(selection_tendances, BorderLayout.WEST);	
		global_panel.add(selection_tweets, BorderLayout.CENTER);	


		
		f.setVisible(true);	
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(global_panel);
		f.setSize(650, 300);
	

}

}

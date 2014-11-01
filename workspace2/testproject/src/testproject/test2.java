package testproject;
public class test2 {
	public static String decouper(String message){
		String element_formate = "";
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
				element_formate+=message.substring(i,i+pos)+ "\n";
			}
			else{
				pos2=40+pos;
				if(message.charAt(pos2)!=' '){
					while(message.charAt(pos2)!=' '){
						pos2--;
					}
				}
				element_formate+=message.substring(pos+1,pos2)+ "\n";
				pos=pos2;
				}
          }
         else{
        	  element_formate += message.substring(pos2+1);
          }
          i+=40;
		}

		return element_formate;
		 }
	
	
	
	
public static void main(String[] args) {
	
	String nouveau=decouper("je m'appelle karrach houssam je suis un étudiant à telecom et maintenant je teste"
			+ " une application de tweeter avec bing");
	System.out.println(nouveau);
}
}

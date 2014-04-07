package saisie;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.SwingUtilities;

import boutons.Xmind2AnkiFenetre;


public class AnkMind {
	public static void main(String[] args) throws IOException{
		

		Locale locale =Locale.getDefault();
		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dat = dateFormat.format(actuelle);

		String dat2=dat.substring(0, 7);
		//if((dat2.equals("2012-03")||(dat2.equals("2012-05")))){
			
		//	new Options();
			
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Xmind2AnkiFenetre fenetre = null;
				try {
					fenetre = new Xmind2AnkiFenetre();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fenetre.setVisible(true);
			}
		});
	//}
		}
}
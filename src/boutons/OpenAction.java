package boutons;
import Browse.*;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.tmatesoft.sqljet.core.SqlJetException;

import boutons.Xmind2AnkiFenetre;

import Xmind2Anki.Scan;


public class OpenAction extends AbstractAction {
	private Xmind2AnkiFenetre fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField();
	public OpenAction(Xmind2AnkiFenetre fenetre, String texte){
		super(texte);
	
		this.fenetre = fenetre;
		
	}
	
	public void actionPerformed(ActionEvent e) { 
		 //Dir=Options.
	
		//cherche si un lieu pour open est enregistré
	
				Properties defaultProps = new Properties();
				Xmind2AnkiFenetre.raz();
				FileInputStream in = null;
				try {
					in = new FileInputStream("appProperties_global");
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					defaultProps.load(in);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String path_to_load=defaultProps.getProperty("opendirectory","");
				
				//fin recherche
		
		
	
		
		  JFileChooser c = new JFileChooser(path_to_load);

		  XmindFormatFilter mfi = new XmindFormatFilter( new String[]{"xmind"},"Xmind files (*.xmind)");
		  c.addChoosableFileFilter(mfi);
		
	      int rVal = c.showOpenDialog(null);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	  Xmind2AnkiFenetre.tabbedPane.setSelectedIndex(0); 
	        filename.setText(c.getSelectedFile().getName());
	        dir.setText((c.getCurrentDirectory().toString()).replace('\\','/'));
	        
	        
	        //Xmind2AnkiFenetre.u
	      //*****sauvegarder lieu d'ouverture
		       
		      
	        defaultProps.setProperty("opendirectory", dir.getText());
	        FileOutputStream out=null;
	        try {
				out = new FileOutputStream("appProperties_global");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	        try {
				defaultProps.store(out, "---No Comment---");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
	
			
	        //******fin sauvegarde
	        
		    //ESSAI........
	        try {
				Xmind2AnkiFenetre.Listproperties=Scan.scanW(dir.getText(),filename.getText());
				Xmind2AnkiFenetre.firstopening=true;
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SqlJetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (org.xmind.core.CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        //ESSAI......
	        
	        
	        
	        //mise à jour des champs - début
	        Xmind2AnkiFenetre.filename=filename;
	        Xmind2AnkiFenetre.dir=dir;
	        	//mise à jour d'un booléen indiquant l'absence de temp 
	        Xmind2AnkiFenetre.incrust=false;
	        	//mise à jour des champs - fin
	       //mise à jour des champs - fin
	        JLabel lab=Xmind2AnkiFenetre.getLabel();
	        String fn=filename.getText();
	        lab.setText(fn);
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("You pressed cancel");
	        //dir.setText("");
	} 
}
	}

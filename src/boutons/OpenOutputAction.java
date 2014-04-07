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

import boutons.Xmind2AnkiFenetre;


public class OpenOutputAction extends AbstractAction {
	private Xmind2AnkiFenetre fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField();
	public OpenOutputAction(Xmind2AnkiFenetre fenetre, String texte){
		super(texte);
		
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) { 
		 //Dir=Options.
	
		//cherche si un lieu pour open est enregistré
				Properties defaultProps = new Properties();
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
				String path_to_load=defaultProps.getProperty("savedirectory","");
				
				//fin recherche
		
		
	
		
		  JFileChooser c = new JFileChooser(path_to_load);
		  
		  //public JFileChooser(String currentDirectoryPath)
		  
		  XmindFormatFilter mfi = new XmindFormatFilter( new String[]{"xmind"},"Xmind files (*.xmind)");
		  c.addChoosableFileFilter(mfi);
		
	      int rVal = c.showSaveDialog(null);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        filename.setText(c.getSelectedFile().getName());
	        dir.setText((c.getCurrentDirectory().toString()).replace('\\','/'));
	        
	        
	      //*****sauvegarder lieu d'ouverture
		       
		      
	        defaultProps.setProperty("savedirectory", dir.getText());
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
	        
	        
	        
	        
	        
	        //mise à jour des champs - début
			 
			String fname=filename.getText();
			if(!fname.contains(".xmind")){fname+=".xmind";}
	        Xmind2AnkiFenetre.outputfilename=fname;
	        Xmind2AnkiFenetre.outputdir=dir.getText();
	        
	        
	    
	        
	        
	        	//mise à jour d'un booléen indiquant l'absence de temp 
	        Xmind2AnkiFenetre.incrust=false;
	        	//mise à jour des champs - fin
	       //mise à jour des champs - fin
	        JLabel lab_output=fenetre.getLabel_output();
	        String fn=fname;
	        lab_output.setText(fn);
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("You pressed cancel");
	        dir.setText("");
	} 
}
	}

package boutons;
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


public class OpenAnkiAction extends AbstractAction {
	private Xmind2AnkiFenetre fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField();
	public OpenAnkiAction(Xmind2AnkiFenetre fenetre, String texte){
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
				String path_to_load=defaultProps.getProperty("ankidirectory","");
				
				//fin recherche
		
		
	
		
		  JFileChooser c = new JFileChooser(path_to_load);
		  c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  
		  //public JFileChooser(String currentDirectoryPath)
		  
	
		
	      int rVal = c.showOpenDialog(null);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        filename.setText(c.getSelectedFile().getName());
	        dir.setText((c.getCurrentDirectory().toString()).replace('\\','/'));
	        
	        
	      //*****sauvegarder lieu d'ouverture
		       
		      
	        defaultProps.setProperty("ankidirectory", dir.getText()+"/"+filename.getText());
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
	        
	        Xmind2AnkiFenetre.ankdir=dir;
	        	//mise à jour d'un booléen indiquant l'absence de temp 
	        Xmind2AnkiFenetre.incrust=false;
	        	//mise à jour des champs - fin
	       //mise à jour des champs - fin
	      
	        JLabel lab=fenetre.getankLabel();
	        String fn=dir.getText()+"/"+filename.getText();
	        lab.setText(fn);
	        fenetre.ankopen=true;
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("You pressed cancel");
	        dir.setText("");
	} 
}
	}

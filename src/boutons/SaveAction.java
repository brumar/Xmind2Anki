package boutons;
import Browse.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import boutons.Xmind2AnkiFenetre;

import Xmind2Anki.Methods;
import Xmind2Anki.Options;

public class SaveAction extends AbstractAction {
	private Xmind2AnkiFenetre fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField();
	
	public SaveAction(Xmind2AnkiFenetre fenetre, String texte,JTextField filename,JTextField dir){
		super(texte);
		this.filename=filename;
		this.dir=dir;
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) { 
		
		//cherche si un lieu pour save est enregistré
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
		String path_to_save=defaultProps.getProperty("savedirectory","../");
		
		//fin recherche
		
		 JFileChooser c = new JFileChooser(path_to_save);
		 XmindFormatFilter mfi = new XmindFormatFilter( new String[]{"xmind","xmap"},"Xmind flies (*.xmind, *.xmap)");
		 c.addChoosableFileFilter(mfi);
		 dir=Xmind2AnkiFenetre.dir;
		 filename=Xmind2AnkiFenetre.filename;
		 String src=dir.getText()+"/"+filename.getText();
		
		
		 
		 if (!dir.getText().equals("")){
		 
		 
	      int rVal = c.showSaveDialog(null);
	      
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	  
	    	 if( Xmind2AnkiFenetre.incrust==true){
	    		src=Options.tempfile;
	    	 } 
	    	
	        String fname=c.getSelectedFile().getName();
	        if(!fname.contains(".xmind")){fname+=".xmind";}
	    	 filename.setText(fname);
	        
	    	 dir.setText((c.getCurrentDirectory().toString()).replace('\\','/'));
	        
	        
	        //*****sauvegarder lieu d'enregistrement
	       
	      
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
	        
	       
	        String dest=dir.getText()+"/"+filename.getText();
	        File src2=new File(src);
	        File dest2=new File(dest);
	        Xmind2AnkiFenetre.getLabel().setText(filename.getText());
	        
	        try {
				Methods.copyFile(src2,dest2); } catch (IOException e1) {e1.printStackTrace();}
	      } 
	      //mise à jour des champs - début
	        Xmind2AnkiFenetre.filename=filename;
	        Xmind2AnkiFenetre.dir=dir;
	       //mise à jour des champs - fin
	 
	      	if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("You pressed cancel");
	        dir.setText("");
	      	}
		 
	      	
		 }else{JOptionPane.showMessageDialog(null, "nothing to save, please select a file") ;}
		 
		 
		 }
	
}

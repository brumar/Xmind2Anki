package boutons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.ProgressMonitor;
import boutons.Xmind2AnkiFenetre;

import progress_monitor.ProgressMonitorDemo;

public class CreateAnkiAction extends AbstractAction implements ActionListener{ 
	public static Xmind2AnkiFenetre fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField();
	private JLabel wbkname;
	
	private ProgressMonitor progressMonitor;
    private JButton startButton;
    private JTextArea taskOutput;
    public static int advanced=0;
	
	public CreateAnkiAction(Xmind2AnkiFenetre fenetre, String texte){
		super(texte);
		this.filename=filename;
		this.dir=dir;
		this.wbkname=wbkname;
		CreateAnkiAction.fenetre = fenetre;
	}
	

	
	public void actionPerformed(ActionEvent e) { 
		//Xmind2AnkiFenetre.updatefilters(false);
		dir=Xmind2AnkiFenetre.dir;
		filename=Xmind2AnkiFenetre.filename;
		String dr=dir.getText();
		String outd=Xmind2AnkiFenetre.wbkname.getText();
		if ((!dr.equals(""))&&(!outd.equals(" no output file "))){
			
			try {
				if(!Xmind2AnkiFenetre.firstopening){
				Xmind2AnkiFenetre.save_properties();}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			 String dest=dir.getText()+"/"+filename.getText();
			
			 
			 ProgressMonitorDemo.dir=dir.getText()+"/";
			ProgressMonitorDemo.file=filename.getText();
		     try {
		    	//go(dir.getText()+"/",filename.getText());
		    	 ProgressMonitorDemo.Runmonitor();
		    	
		    	 //RunWorkbook.runW(dir.getText()+"/",filename.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		 else{ if(dr.equals("")){
		JOptionPane.showMessageDialog(null, " please select a file");}
		else {JOptionPane.showMessageDialog(null, " please select an outputfile");}}
	}

	
	
}


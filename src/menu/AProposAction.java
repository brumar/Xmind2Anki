package menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import boutons.Xmind2AnkiFenetre;


public class AProposAction extends AbstractAction {
	private Xmind2AnkiFenetre fenetre;
	
	public AProposAction(Xmind2AnkiFenetre fenetre, String texte){
		super(texte);
		
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) { 
		JOptionPane.showMessageDialog(fenetre, "Ce programme a été développé par B-Stephane Martin");
	} 
}
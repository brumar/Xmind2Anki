package boutons;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import saisie.FenetreSaisie;


public class GetAction extends AbstractAction {
	private FenetreSaisie fenetre;
	
	public GetAction(FenetreSaisie fenetre, String texte){
		super(texte);
		
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) { 
		String texteUtilisateur = fenetre.getTextField().getText();
		fenetre.getLabel().setText(texteUtilisateur);
	} 
}
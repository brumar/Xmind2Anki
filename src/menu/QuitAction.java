package menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class QuitAction extends AbstractAction {
	public QuitAction(String texte){
		super(texte);
	}
	
	public void actionPerformed(ActionEvent e) { 
		System.exit(0);
	} 
}
package test_lock;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FormFrame extends JFrame {
	private JTextField lastname;
	private JTextField firstname;
	private JTextField comments;
	private JButton send;

	public FormFrame(String name) {
		super(name);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		FormFillActionListener fillListener = new FormFillActionListener();
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(4, 2));
		formPanel.add(new JLabel("Nom:"));
		lastname = new JTextField(25);
		fillListener.registerComponent(lastname);
		formPanel.add(lastname);
		formPanel.add(new JLabel("Prénom:"));
		firstname = new JTextField(25);
		fillListener.registerComponent(firstname);
		formPanel.add(firstname);		
		formPanel.add(new JLabel("Commentaire:"));
		comments = new JTextField(40);
		fillListener.registerComponent(comments);
		formPanel.add(comments);
		send = new JButton("Envoyer");
		fillListener.setTriggerComponent(send);
		formPanel.add(send);
		getContentPane().add(formPanel);
	}
	

	public static void main(String[] args) {
		FormFrame form = new FormFrame("Test formulaire");
		form.pack();
		form.setVisible(true);
	}
}
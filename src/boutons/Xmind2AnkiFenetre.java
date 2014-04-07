package boutons;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import boutons.CreateAnkiAction;
import boutons.Map_string;
import boutons.OpenAction;
import boutons.OpenAnkiAction;
import boutons.OpenOutputAction;

public class Xmind2AnkiFenetre extends JFrame implements ActionListener{
	private JTabbedPane Onglets = null;
	private List<JComboBox>  whens= new ArrayList<JComboBox>();
	private List<JComboBox>  Dos= new ArrayList<JComboBox>();
	private List<JComboBox>  boxactions= new ArrayList<JComboBox>();
	private List<JComboBox> boxselections= new ArrayList<JComboBox>();
	private static JPanel container3=new JPanel();
	private static JPanel container2=new JPanel();
	private static JPanel container1 = new JPanel();
	private  JLabel note;
	
	 private List<JButton> add= new ArrayList<JButton>();
	 private List<JButton> clear= new ArrayList<JButton>();
	static JTextField filename = new JTextField();
	static JTextField dir = new JTextField();
	public static boolean incrust;
	private static JLabel label_file;
	private JLabel ankdirname;
	static JLabel wbkname;
	public ArrayList<String> actions=new ArrayList<String>();
	private JScrollPane scroll;
	public boolean ankopen=false; // permet de savoir si l'ouverture a déjà été fait à la main
	private static JPanel rank;
	static int nbfilter=1;
	public static JTextField ankdir;
	public static String outputfilename;
	public static String outputdir;
	public static List<String> Listproperties= new ArrayList<String>();
	public static List <List<Object>> prefilters = new ArrayList<List<Object>>();
	public static Hashtable<String, String> general_options = new Hashtable<String, String>();
	public static boolean filters_updated=false;
	public static boolean when_updated=false;
	public static boolean firstopening=true;
	public static  JTabbedPane tabbedPane = new JTabbedPane();

	public Xmind2AnkiFenetre() throws IOException{
		super();
		
		build();//On initialise notre fenêtre
	}
	
	private void build() throws IOException{
		
		general_options=load_options(); //load le fichier properties_general
		init(actions);
		setTitle("Xmind2Anki");
		setSize(850,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		setLayout(new GridBagLayout());//the windows is managed by a gridbag layout, with a tabbedPane in last instance
		
		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx=1;
		c.gridx = 0;
	    c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;; 
		c.insets = new Insets(2, 5, 2, 5); 
		
		add(buildContentPane(),c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx=1;
		c.weighty=1;
	    c.gridx = 0;
	    c.gridy = 1;
		
		add(TabbedPaneDemo(),c); //ajout de la tabbedPane
	
        
     //************menu creation
		JMenuBar menuBar = new JMenuBar();
	
		
		 JButton didi=new JButton(new OpenAction(this,""));
		 didi.setContentAreaFilled(false);
		 didi.setSize(48, 48);
		// didi.set
		 Icon icon2 = new ImageIcon("open.png");
		 didi. setFocusPainted( false );
		 didi.setIcon(icon2);
		 didi.setToolTipText("open a workbook");
		 menuBar.add(didi);

		
		JButton dada=new JButton(new CreateAnkiAction(this,""));
		dada.setContentAreaFilled(false);
		 Icon icon = new ImageIcon("run2.png");
		 dada.setFocusPainted( false );
		 dada.setToolTipText("generate anki files");

		
		 dada.setIcon(icon);
		 menuBar.add(dada);
		 
		
		
		setJMenuBar(menuBar);
		 //************menu creation -end
	
	
	}

	private Hashtable<String, String> load_options() throws IOException {
		
		FileInputStream out = new FileInputStream("appProperties_global");
		Properties gen_options = new Properties();
		gen_options.load(out);
		return(Hashtable) gen_options;
		
	
		// TODO Auto-generated method stub
		
	}

	private void init(ArrayList<String> actions2) {
		actions.add("take as a question");
		actions.add("take as an answer");
		actions.add("replace by childs");
		//actions.add("set priority : low");
		//actions.add("set priority : high");
		//actions.add("set priority : very high");
		actions.add("keep link");  
		actions.add("keep note");
		actions.add("keep label");
		actions.add("keep style");
		actions.add("keep image");
		actions.add("keep marker");
		actions.add("nothing particular");
		//actions.add("take answer in label");
		//actions.add("take answer in notes");
		
		Collections.sort(actions);
		
	}

	JTabbedPane TabbedPaneDemo() {
		 
	      
	        tabbedPane.addTab("General", null, getGeneralOptions(),"general options : mode and locations");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addTab("default_options", null, getdefaultOptions(),"default options : preselections");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addTab("filter_options", null, getfilterOptions(),"filter options : fine selections");
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	        tabbedPane.addChangeListener(new ChangeListener() {
	            

				// This method is called whenever the selected tab changes
	            public void stateChanged(ChangeEvent evt) {
	                JTabbedPane pane = (JTabbedPane)evt.getSource();
	                

	                // Get current tab
	                int sel = pane.getSelectedIndex();
	                if ((sel==2)&&(dir.getText().equals(""))){

	                	int dre=1;
	                	pane.setSelectedIndex(0); 
	                	JOptionPane.showMessageDialog(null, "please select a workbook before filtering") ;
	                }
	                else{if((!dir.getText().equals(""))&& (firstopening)){

	                		 firstopening=false;
	                		  nbfilter++;
	                  		addLine();
	                  		removeLine(0);
	                  		
	                  		
	                  		getcontainer3().updateUI();
	                  		 updatefilters(true);
	                  		 
	                  		if(nbfilter==1){
	                  			clear.get(0).setEnabled(false);}
	                  		 
	                	}else if(!dir.getText().equals("")){updatefilters(false);}

	                	}

	            }

				private void update_when() {
					//JPanel p=getcontainer3();
					//JCheckBox c=(JCheckBox) (p.getComponent(i));
					
					
				}


	        });
	        
	        
	        // if (!dir.getText().equals("")){
	        return tabbedPane;
	       
	}

	private Component getfilterOptions() {
		
		JPanel bigscroll=new JPanel();
		bigscroll.setMinimumSize(new Dimension(500,1000));
		bigscroll.setLayout(new FlowLayout());
		getcontainer3().setLayout(new GridBagLayout());
		 GridBagConstraints constraint=getContainer3constraints();
	     getcontainer3().add(container_temp(),constraint);
	     bigscroll.add(getcontainer3());
	     JScrollPane scroll3=new JScrollPane(bigscroll,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	    		 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	     
	     
	     return scroll3;
	    // return getcontainer3();
	}

	private GridBagConstraints getContainer3constraints() {
		GridBagConstraints constraint=new GridBagConstraints();
		// constraint.weightx=0;
		// constraint.weighty=0;
			constraint.gridx = 0;
			constraint.anchor = GridBagConstraints.NORTHWEST; 
			constraint.insets = new Insets(2, 2, 2, 2); 
		return constraint;
	}

	public JPanel container_temp() {
	
	
		//actions=sort(actions);
		
		
		List<String> DO=new ArrayList<String>(); 
		DO.add("DO");
		DO.add("DO NOT"); 
		 Collections.sort(Listproperties);
	     JComboBox boxwhen = new JComboBox();
	     boxwhen.setPreferredSize(new Dimension(150,20));
	     for (int i=0;i<Listproperties.size();i++){
	    	 boxwhen.addItem(Listproperties.get(i)); 
	     }
	    
	     
	    
	     
	     
	     JComboBox boxDO = new JComboBox();
	     boxDO.setPreferredSize(new Dimension(80,20));
	     for (int i=0;i<DO.size();i++){
	    	 boxDO.addItem(DO.get(i)); 
	     }
	    

	     JComboBox boxaction = new JComboBox();
	     boxaction.setPreferredSize(new Dimension(150,20));
	     for (int i=0;i<actions.size();i++){
	    	 boxaction.addItem(actions.get(i)); 
	     } 
	    
	     JComboBox boxselection = new JComboBox();
	     boxselection.setPreferredSize(new Dimension(180,20));
	     boxselection.addItem("for itself");
	     boxselection.addItem("for itself & descendants");
	    /* 
	     whens.add(boxwhen);
	     Dos.add(boxDO);
	     boxactions.add(boxaction);
	     boxselections.add(boxselection);*/
     
	     
	     JButton addb=new JButton("+");
	     addb.addActionListener(this);
	     addb.setName(String.valueOf(nbfilter));
	     add.add(addb);
	     
	     JButton clearb=new JButton("-");
	     clearb.setName(String.valueOf(nbfilter));
	     clearb.addActionListener(this);
	     clearb.setEnabled(nbfilter!=1);
	     clear.add(clearb);
	     
	     note=new JLabel("when");
	     
	     
	     JPanel container_temp = new JPanel();
	    	 container_temp.add(note);
	    	 container_temp.add(boxwhen);
	    	 container_temp.add(boxDO);
	    	 container_temp.add(boxaction);
	    	 container_temp.add(boxselection);
	    	 container_temp.add( add.get(nbfilter-1));
	    	 container_temp.add(clear.get(nbfilter-1));
	    	 
	    	 return container_temp;
	}

	private ArrayList<String> sort(ArrayList<String> actions2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Component getdefaultOptions() {
		
		//container2 = new JPanel();
        
		
		JCheckBox rank_options = new JCheckBox("rank filter");
		
        JCheckBox epurer_topic_vide = new JCheckBox("avoid empty topics");
        epurer_topic_vide.setToolTipText(" in the answer field of Anki, empty topics will be replaced by the list of their childs");
        JCheckBox glisseur_for_unique = new JCheckBox("avoid singleton");
        glisseur_for_unique.setToolTipText("topic with only one child will be displayed with this child");
       
        JCheckBox equal_priority = new JCheckBox("equal priority");
        
        JCheckBox always_keep_your_links = new JCheckBox("keep links");
        JCheckBox lock_keep_your_links = new JCheckBox("lock");
        
        JCheckBox always_keep_your_notes = new JCheckBox("keep notes");
        JCheckBox lock_keep_your_notes = new JCheckBox("lock");
        
        JCheckBox always_keep_your_labels = new JCheckBox("keep labels");
        JCheckBox lock_keep_your_labels = new JCheckBox("lock");
        
        JCheckBox always_keep_your_styles = new JCheckBox("keep styles");
        JCheckBox lock_keep_your_styles = new JCheckBox("lock");
        
        JCheckBox always_keep_your_images = new JCheckBox("keep images");
        JCheckBox lock_keep_your_images = new JCheckBox("lock");
        
        JCheckBox always_keep_your_markers = new JCheckBox("keep markers");
        JCheckBox lock_keep_your_markers = new JCheckBox("lock");
        
        JCheckBox never_content_in_label = new JCheckBox("label as answer");
        JCheckBox lock_content_in_label = new JCheckBox("lock");
        never_content_in_label.setVisible(false);
        lock_content_in_label.setVisible(false);
        
        JCheckBox never_content_in_notes = new JCheckBox("note as answer");
        JCheckBox content_in_notes = new JCheckBox("lock");
        never_content_in_notes.setVisible(false);
        content_in_notes.setVisible(false);
        
        JCheckBox always_questions_all = new JCheckBox("preselection answer");
        JCheckBox lock_questions_all = new JCheckBox("lock");
        
        JCheckBox always_answers_all = new JCheckBox("preselection question");
        JCheckBox lock_answers_all = new JCheckBox("lock");
       

       List<JCheckBox> boxes=new ArrayList<JCheckBox>(); 

       boxes.add(always_questions_all);
       boxes.add(lock_questions_all);
       
       boxes.add(always_answers_all );
       boxes.add(lock_answers_all );
       
       boxes.add(always_keep_your_links );
       boxes.add(lock_keep_your_links);
       
       boxes.add(always_keep_your_notes );
       boxes.add(lock_keep_your_notes );
       
       boxes.add(always_keep_your_labels );
       boxes.add(lock_keep_your_labels );
       
       boxes.add(always_keep_your_styles );
       boxes.add(lock_keep_your_styles );
       
       boxes.add(always_keep_your_images );
       boxes.add(lock_keep_your_images );
       
       boxes.add(always_keep_your_markers );
       boxes.add(lock_keep_your_markers );
       
       boxes.add(never_content_in_label );
       boxes.add(lock_content_in_label );
       
       boxes.add(never_content_in_notes);
       boxes.add(content_in_notes );
       
       boxes.add(epurer_topic_vide); 
       boxes.add(glisseur_for_unique);
       boxes.add(rank_options);
       
     
     
        
               
        
        container2.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.weightx=0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST; 
		c.insets = new Insets(2, 2, 2, 2); 
		GridBagConstraints c1 = new GridBagConstraints();
		c1.weightx=1;
		c1.gridx = 1;
		c1.anchor = GridBagConstraints.NORTHWEST; 
		c1.insets = new Insets(2, 5, 2, 2); 
		Map_string mp=new Map_string();
		
		for(int i=0;i<9;i++){
			int j=2*i;
			boxes.get(j).setToolTipText("default option");
			container2.add(boxes.get(j),c);
			String s=(mp.find( (boxes.get(j)).getText(),true));
			if (s!=null){
				if ((general_options.get(s)!=null)&&(general_options.get(s).equals("true"))){
					boxes.get(j).setSelected(true);}
			}
		
			boxes.get(j+1).setToolTipText("locking forbide the option to be modulated by filters");
			container2.add(boxes.get(j+1),c1);
			
			String s2="lock_"+(boxes.get(j)).getText();
			if (!s2.equals("lock_")){
				String s3=mp.find(s2,true);
				if ((s3!=null)&&(general_options.containsKey(s3))){
					if (general_options.get(s3).equals("true")){
					boxes.get(j+1).setSelected(true);}
					}
			}
		}
		GridBagConstraints c3 = new GridBagConstraints();
		c3.weightx=0;
		c3.weighty=1;
		c3.gridx = 0;
		c3.anchor = GridBagConstraints.NORTHWEST; 
		c3.insets = new Insets(2, 2, 2, 2); 
		
		// container2.add(equal_priority,c);
		//epurer_topic_vide.setSelected(b)
		for(int i=20;i<23;i++){
			container2.add(boxes.get(i),c);
			String s=(mp.find( (boxes.get(i)).getText(),true));
			if (s!=null){
				if ((general_options.get(s)!=null)&&(general_options.get(s).equals("true"))){
					boxes.get(i).setSelected(true);}
			}
		}
	    //container2.add(epurer_topic_vide,c);
	    //container2.add(glisseur_for_unique,c);
		//container2.add(rank_options,c);
		
        
		//getcontainer2().add(top);
        
        rank = new JPanel();
        rank.setLayout(new FlowLayout());
       
        
        JFormattedTextField seuil_root = new JFormattedTextField();
        
        seuil_root.setPreferredSize(new Dimension(20,20));
        seuil_root.setValue(general_options.get("seuil_root"));
        rank.add(seuil_root);
        JLabel da = new JLabel("backward keeper");
        da.setToolTipText("take topics near enough last nodes");
        rank.add(da);
        
        
      
        
        JFormattedTextField seuil_branch = new JFormattedTextField();
        seuil_branch.setPreferredSize(new Dimension(20,20));        
        seuil_branch.setValue(general_options.get("seuil_branch"));
        rank.add(seuil_branch);
        JLabel du = new JLabel("forward filter");
        du.setToolTipText("reject topics enough root topic");
        rank.add(du);
     
        container2.add(rank,c3);
		return container2;
	}

	private Component getGeneralOptions() {

	    
        JRadioButton jr1 = new JRadioButton("inside the map");
        JRadioButton jr2 = new JRadioButton("outside the map");
        JRadioButton jr3 = new JRadioButton("both");
        
        jr1.setToolTipText("anki files will be attached to the new workbook.Note that markers and images can't be conserved in this case");
        jr2.setToolTipText("anki files will be in a separated folder.A link to this file will be added on root topics");
        jr3.setToolTipText("anki files will be attached to the new workbook with a copy in a separated folder");
        
        ButtonGroup bg = new ButtonGroup();
       // container.setBackground(Color.);
        
        getcontainer1().setLayout(new GridBagLayout());
        getcontainer1().setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT);
       
        GridBagConstraints c = new GridBagConstraints();
        c.weightx=1;
        c.weighty=0;
		c.gridx = 0;
	    c.gridy = 0;

		c.insets = new Insets(2, 2, 2, 2); 
		c.anchor=GridBagConstraints.LINE_START;
		
		GridBagConstraints c3 = new GridBagConstraints();
		  
        c3.weightx=1;
        c3.weighty=1;
		c3.gridx = 0;
	    c3.gridy = 2;
	    c3.gridheight=1;
		
		c3.insets = new Insets(2, 2, 2, 2); 
		c3.anchor=GridBagConstraints.NORTHWEST;
               
        JPanel top2 = new JPanel();
    
        
       
        
        //if((general_options.containsKey("ankidirectory"))
      
        int variable=1;
        if((general_options.containsKey("linkToanki"))&&(general_options.containsKey("duplicate"))){
        	boolean l=((general_options.get("linkToanki")).equals("true"));
        	boolean d=((general_options.get("duplicate")).equals("true"));
        	
        	if(l){variable=2;}
        	else {if(d){variable=3;}}
        }
        
     
        switch (variable) {
        case 1 :
        	jr1.setSelected(true);
         break;
        case 2 :
        	jr2.setSelected(true);
         break;
        case 3 :
        	jr3.setSelected(true);
         break;
        }
        //bg.setSelected(jr1, true)
        
        
        
       // jr1.addActionListener(new StateListener());
        //jr2.addActionListener(new StateListener());
        
        bg.add(jr1);
        bg.add(jr2);
        bg.add(jr3);
        
      
        JLabel mode = new JLabel("anki creation            : ");
        top2.add(mode);
        top2.add(jr1);
        top2.add(jr2);
        top2.add(jr3);
        getcontainer1().add(top2,c); 
        
        GridBagConstraints c2 = new GridBagConstraints();
        c2.weightx=1;
        c2.weighty=0;
		c2.gridx = 0;
	    c2.gridy = 1;
	    c2.anchor=GridBagConstraints.LINE_START;
	
		c2.insets = new Insets(2, 2, 2, 2); 
        
        JPanel ank = new JPanel(); 
        JLabel ankdir = new JLabel(".anki location            : ");
       
        

        if((general_options.containsKey("ankidirectory"))&&(!ankopen)){
        	ankdirname = new JLabel(general_options.get("ankidirectory"));
             top2.add(ankdirname);
        }
        
        else {
        ankdirname = new JLabel(" no directory selected ");}
        
        ankdirname.setMinimumSize(new Dimension(125, 20));
        ankdirname.setPreferredSize(new Dimension(190, 20));
        
        JPanel ankdirlab = new JPanel(); 
        ankdirlab.setBackground(Color.white);
        ankdirlab.add(ankdirname);
        ankdirlab.setBorder(BorderFactory.createLineBorder (Color.blue, 1));

       
        
        
        
        
        
        //ankdirname.setBackground(Color.white);
        JButton openankdir = new JButton(new OpenAnkiAction(this,""));
        
        Icon icon = new ImageIcon("folder24.png");
       // openankdir.setContentAreaFilled(false);
        openankdir.setToolTipText("Browse");
        openankdir.setIcon(icon);
       // openankdir.setI
        
        
        ankdir.setMinimumSize(new Dimension(125, 20));
        ankdir.setPreferredSize(new Dimension(125, 20));
        
        ankdirname.setMinimumSize(new Dimension(125, 20));
        ankdirname.setPreferredSize(new Dimension(190, 20));
        
        
        ank.add(ankdir);
        ank.add(ankdirlab);
        ank.add(openankdir);
        getcontainer1().add(ank,c2);
        
        
        
		JPanel wbk = new JPanel(); 
		
        JLabel wbkdir = new JLabel("output file (.xmind)  : ");
        
        JPanel wbklab = new JPanel(); 
        wbklab.setBackground(Color.white);
		
		
        wbkname = new JLabel(" no output file ");
        wbklab.add(wbkname);
        wbklab.setBorder(BorderFactory.createLineBorder (Color.blue, 1));

        wbkname.setMinimumSize(new Dimension(125, 20));
        wbkname.setPreferredSize(new Dimension(190, 20));
      
        JButton openwbkdir = new JButton(new OpenOutputAction(this, ""));
       // openankdir.setBackground(COLOR.white);
        //openwbkdir.setContentAreaFilled(false);
        openwbkdir.setToolTipText("Browse");
        openwbkdir.setIcon(icon);
        
        wbkname.setMinimumSize(new Dimension(125, 20));
        wbkdir.setMinimumSize(new Dimension(125, 20));
        wbkdir.setPreferredSize(new Dimension(125, 20));
        
        
       
     
        wbk.add(wbkdir);
        wbk.add(wbklab);
        wbk.add(openwbkdir);
        getcontainer1().add(wbk,c3);
        
     
        
		return getcontainer1();
	}

	private static JPanel getcontainer1() {
		// TODO Auto-generated method stub
		return container1;
	}

	private JPanel buildContentPane(){
		
		
		 JPanel panel = new JPanel();
		
		 
		
        JLabel lab0 = new JLabel("input file (.xmind)  : ");
        JPanel panel0 = new JPanel(); 
        panel0.add(lab0);
      
		
		

		
        JPanel panel1 = new JPanel();
		panel1.setBackground(Color.white);

		//String filename=CalculatriceFenetre.filename.getText();
		//JLabel label = new JLabel(filename);
		label_file = new JLabel("no workbook selected");
		panel1.add(label_file);
		panel1.setBorder(BorderFactory.createLineBorder (Color.red, 1));
		
		panel.add(panel0);
		panel.add(panel1);
		
		return panel;
	}

	public static JLabel getLabel() {
		return label_file;
	}
	

	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if(add.contains(source)){
			nbfilter++;
			addLine();
			
		}
		if (clear.contains(source)){
			
			removeLine(clear.lastIndexOf(source));
		}
		if(nbfilter==1){
		clear.get(0).setEnabled(false);}
		else{clear.get(0).setEnabled(true);}
		
	    
		
		
	}

	 void addLine() {
	   
		JPanel c=container_temp();
		c.setName(String.valueOf( nbfilter));
	    getcontainer3().add(c,getContainer3constraints());
	    // container3.remov
	     
	     getcontainer3().updateUI();
	}
	
	void removeLine(int i) {
	     
		if(nbfilter>1){ 
	     Component [] comp=getcontainer3().getComponents() ;
	    
	    
	     getcontainer3().remove(i);
	     add.remove(i);
	     clear.remove(i);
	     
	   //  if(i==5){removeitem();}
		
		}
		nbfilter--;
	   
	     
	     getcontainer3().updateUI();
	}
	void removeitem(String f) {
		Component[] c=getcontainer3().getComponents();
		for (int l=0;l<c.length;l++){
	     JPanel b=(JPanel) c[l];
	     JComboBox jb=(JComboBox) b.getComponent(3);
	 	for (int i=0;i<jb.getItemCount();i++){
	     String s=(String) jb.getItemAt(i);
	     if (s.equals(f)){
		   jb.removeItemAt(i);
	   }
	    	 
	     }
		}
		
	}

	public JLabel getankLabel() {
		// TODO Auto-generated method stub
		
			return  ankdirname;
		
	}

	public JLabel getLabel_output() {
		// TODO Auto-generated method stub
		return wbkname;
	}

	public static JPanel getcontainer3() {
		return container3;
	}
	public static JPanel getrank() {
		return rank;
	}
	
	public static JPanel getcontainer2() {
		return container2;
	}
	private void updatefilters(Boolean bool) {
		JPanel p=getcontainer2();
		
		if (bool){process(prefilters);};
		
		UpdateItemBox(1,"take as an answer",p);
		UpdateItemBox(3,"take as a question",p);
		UpdateItemBox(5,"keep link",p);
		UpdateItemBox(7,"keep note",p);
		UpdateItemBox(9,"keep label",p);
		UpdateItemBox(11,"keep style",p);
		UpdateItemBox(13,"keep image",p);
		UpdateItemBox(15,"keep marker",p);
		//UpdateItemBox(17,"take answer in label",p);
		//UpdateItemBox(19,"take answer in notes",p);
		
		getcontainer3().updateUI();
		

		
	}

	private void process(List<List<Object>> prefilters2) {
		
		Map_string mp=new Map_string();
		for(int i=0;i<prefilters2.size();i++){
			nbfilter++;
			
			List<Object> prefilter=prefilters2.get(i);
			
			String action=mp.find((String)prefilter.get(2), false);
			
			
				List<String> DO=new ArrayList<String>(); 
				DO.add("DO");
				DO.add("DO NOT"); 
				 
			     JComboBox boxwhen = new JComboBox();
			     boxwhen.setPreferredSize(new Dimension(150,20));
			     for (int k=0;k<Listproperties.size();k++){
			    	 boxwhen.addItem(Listproperties.get(k)); 
			     }
			     String prop_selected=(String)prefilter.get(0);
			     boxwhen.setSelectedItem(prop_selected);
			     boxwhen.setToolTipText("select a property from your selected workbook");
 

			     JComboBox boxDO = new JComboBox();
			     boxDO.setPreferredSize(new Dimension(80,20));
			     for (int k=0;k<DO.size();k++){
			    	 boxDO.addItem(DO.get(k)); 
			     }
			     if ((Boolean) (prefilter.get(1))){ boxDO.setSelectedItem("DO")	;}else{boxDO.setSelectedItem("DO NOT");}
			     
			     

			     JComboBox boxaction = new JComboBox();
			     boxaction.setPreferredSize(new Dimension(150,20));
			     for (int k=0;k<actions.size();k++){
			    	 boxaction.addItem(actions.get(k)); 
			     } 
			    boxaction.setSelectedItem(action);
			    
			     JComboBox boxselection = new JComboBox();
			     boxselection.setPreferredSize(new Dimension(180,20));
			     boxselection.addItem("for itself");
			     boxselection.addItem("for itself & descendants");
			     boxselection.setToolTipText("choose if the property will be inherited by childs or not");
			     
			     if ((Boolean) (prefilter.get(3))){  boxselection.setSelectedItem("for itself")	;}
			     
			     else{
			    	 boxselection.setSelectedItem("for itself & descendants");}
		     
			     
			     JButton addb=new JButton("+");
			     addb.setToolTipText("add a filter");
			     addb.addActionListener(this);
			     addb.setName(String.valueOf(nbfilter));
			     add.add(addb);
			     
			     JButton clearb=new JButton("-");
			     clearb.setToolTipText("delete this filter");
			     clearb.setName(String.valueOf(nbfilter));
			     clearb.addActionListener(this);
			     clearb.setEnabled(nbfilter!=1);
			     clear.add(clearb);
			     
			     note=new JLabel("when");
			     
			     
			     JPanel container_temp = new JPanel();
			    	 container_temp.add(note);
			    	 container_temp.add(boxwhen);
			    	 container_temp.add(boxDO);
			    	 container_temp.add(boxaction);
			    	 container_temp.add(boxselection);
			    	 container_temp.add( add.get(nbfilter-1));
			    	 container_temp.add(clear.get(nbfilter-1));  
			    	 
			    	 getcontainer3().add(container_temp,getContainer3constraints());
			    	
	
		}
		
		if (prefilters2.size()>0){removeLine(0);}
		
	}

	private void UpdateItemBox(int i, String string, JPanel p) {
		JCheckBox c=(JCheckBox) (p.getComponent(i));
		if(c.isSelected()){
			removeitem(string);
			if (actions.contains(string)){actions.remove(string);}
		}
		else{
			restoreitem(string);
			if (!actions.contains(string)){actions.add(string);}
		}
		Collections.sort(actions);
	}

	private void restoreitem(String string) {
		Component[] c=getcontainer3().getComponents();
		for (int l=0;l<c.length;l++){
	     JPanel b=(JPanel) c[l];
	     JComboBox jb=(JComboBox) b.getComponent(3);
	     boolean inside=true;
	 		for (int i=0;i<jb.getItemCount();i++){
	 		String s=(String) jb.getItemAt(i);
	 			if (s.equals(string)){inside=false;}		
	 		}
	 	if (inside==true){jb.addItem(string);}
		}
		
	}
	public void add_filter(String value, boolean do1, String lowerCase,
			boolean gp) {
		JPanel c3=getcontainer3();
		c3.add(container_temp(),getContainer3constraints());
		
		
	}

	public static void save_properties() throws IOException {
		Map_string mp=new Map_string();

		Component[] c=getcontainer3().getComponents();
		List<List<String>>  props=new ArrayList<List<String>>();
		for (int l=0;l<c.length;l++){
	     JPanel b=(JPanel) c[l];
	     
	     JComboBox when=(JComboBox) b.getComponent(1);
	     JComboBox Do=(JComboBox) b.getComponent(2);
	     JComboBox Action=(JComboBox) b.getComponent(3);
	     JComboBox Selection=(JComboBox) b.getComponent(4);
	     String Swhen=(String) when.getSelectedItem();
	     String Sdo=(String) Do.getSelectedItem();
	     String SAction=(String) Action.getSelectedItem();
	     String SSelection=(String) Selection.getSelectedItem();
	     if(!SAction.equals("nothing particular")&&
	    		 (Swhen!=null)){
	     SAction=mp.find(SAction, true) ;
	     String part1=SAction.substring(0,1);
	     String part2=SAction.substring(1,2);
	     String part3=SAction.substring(2);
			
	     if (Sdo.equals("DO NOT")){
	    	 part1=part1.toUpperCase();
	     }
	     if (SSelection.equals("for itself & descendants")){
	    	 part2=part2.toUpperCase();
	     }
	     SAction=part1+part2+part3;
	     List<String>  prop=new ArrayList<String>();
		 prop.add(Swhen);
	     prop.add(SAction);
		 props.add(prop);
	     
	}}
		FileInputStream in = new FileInputStream("appProperties_local");
		Properties appProp = new Properties();
		
		Properties propout = new Properties();
		Properties propout2 = new Properties();
		
		appProp.load(in);
		Hashtable<String,String> ppp=(Hashtable) appProp;
		
       List<String> Properties = new ArrayList(ppp.keySet()) ;
       
       for(int i=0;i<Properties.size();i++){//mise à jour des valeurs des clef employés dans les filtres
    	   String valueInit=ppp.get(Properties.get(i));
    	   String valueFinal="";
    	   Boolean noCare=true;
    	   for (int j=0;j<props.size();j++){
    		   
    		   if ((Properties.get(i)).equals(props.get(j).get(0))){
    			   valueFinal+=props.get(j).get(1)+"\\";
    			   noCare=false;
    			   props.remove(j);
    			   j--;
    			   
    		   }
    	   }
    	 if(noCare){propout.put(Properties.get(i), valueInit);}
    	   if(!noCare){propout2.put(Properties.get(i), valueFinal); propout.put(Properties.get(i), valueFinal);} //on n'utilise pas les clefs anciennes
       }
       
       boolean end=(props.isEmpty());//on rajoute les clefs dans les filtres non présentes ds le fichier properties
       while(!end){
    	   String key=props.get(0).get(0);
    	   if (key!=null){
    	   String valueFinal=props.get(0).get(1)+"\\";
    	   props.remove(0);
    	  
    	   for (int j=0;j<props.size();j++){
    		   if((!props.get(j).isEmpty())&&(key!=null)&&((props.get(j).get(0))!=null)){
    		   if (key.equals(props.get(j).get(0))){
    			   valueFinal+=props.get(j).get(1)+"\\";
    			   props.remove(j);
    	    	   j--;
    		   }
    		   }
    	   }
    	   propout2.put(key, valueFinal);
    	   propout.put(key, valueFinal);
    	   end=(props.isEmpty());
    	   }
    	   //if(!end){props.remove(0);}
    	   end=(props.isEmpty());
    	  
       }
       
       
       
       
       
       FileOutputStream out2 = new FileOutputStream("appProperties_local2");//c'est le fichier que va charger runsheet
       propout2.store(out2, "---No Comment---");
       out2.close();
       int da=1;
       
       FileOutputStream out = new FileOutputStream("appProperties_local");//c'est le fichier qui va conserver l'ensemble des options utilisateurs
       propout.store(out, "---No Comment---");
       out.close();
       

       
       JPanel p=getcontainer2();
       //1->19
       List<List<String>>  Gprops=new ArrayList<List<String>>();
       for(int i=0;i<9;i++){
    	   int j=2*i;
    	   JCheckBox check=(JCheckBox) (p.getComponent(j));
    	   List<String>  Gprop=new ArrayList<String>();
    		   String txt= mp.find(check.getText(),true);
    		   Gprop.add(txt);
    		   if (check.isSelected()){
    		   Gprop.add("true");}
    		   else{Gprop.add("false");}
    		   Gprops.add(Gprop);
    		   
    		   
    		   JCheckBox check2=(JCheckBox) (p.getComponent(j+1));
        	   List<String>  Gprop2=new ArrayList<String>();
        		   String txt2=mp.find("lock_"+check.getText(),true);
        		   Gprop2.add(txt2);
        		   if (check2.isSelected()){
        		   Gprop2.add("true");}
        		   else{Gprop2.add("false");}
        		   Gprops.add(Gprop2);  
        		   
    	   }
       
       for(int i=18;i<21;i++){
    	   JCheckBox check=(JCheckBox) (p.getComponent(i));
    	   List<String>  Gprop=new ArrayList<String>();
    		   String txt= mp.find(check.getText(),true);
    		   Gprop.add(txt);
    		   if (check.isSelected()){
    		   Gprop.add("true");}
    		   else{Gprop.add("false");}
    		   Gprops.add(Gprop);           	   
       }
       
       
       FileInputStream inG = new FileInputStream("appProperties_global");
		Properties appPropG = new Properties();
		Properties propoutG = new Properties();	
		appPropG.load(inG);
		Hashtable<String,String> pppG=(Hashtable) appPropG;
		
      List<String> PropertiesG = new ArrayList(pppG.keySet()) ;
      
      for(int i=0;i<PropertiesG.size();i++){
   	   String valueInit=pppG.get(PropertiesG.get(i));
   	   String valueFinal="";
   	   Boolean noCare=true;
   	   for (int j=0;j<Gprops.size();j++){
   		   
   		   if ((PropertiesG.get(i)).equals(Gprops.get(j).get(0))){
   			   valueFinal=Gprops.get(j).get(1);
   			   noCare=false;
   			   Gprops.remove(j);
   			   j--;
   			   
   		   }
   	   }
   	  if(noCare){propoutG.put(PropertiesG.get(i), valueInit);}else{
   		  propoutG.put(PropertiesG.get(i), valueFinal);}
   	   
      }
     
      for (int j=0;j< Gprops.size();j++){
    	  String key= Gprops.get(j).get(0);
    	  String valueFinal= Gprops.get(0).get(1);
    	  if ((key!=null)&&(valueFinal!=null)){
    	  propoutG.put(key, valueFinal);}
    	  
      }

      
      
      
      JPanel c1=getcontainer1();
      JRadioButton jr1=(JRadioButton) ((JPanel) c1.getComponent(0)).getComponent(1);
      JRadioButton jr2=(JRadioButton) ((JPanel) c1.getComponent(0)).getComponent(2);
      JRadioButton jr3=(JRadioButton) ((JPanel) c1.getComponent(0)).getComponent(3);
      
      String key1="linkToanki";
      String key2="duplicate";
      String value1="";
      String value2="";
      if(jr1.isSelected()){
    	  value1="false";
    	  value2="false";
      }
      if(jr3.isSelected()){
    	  value1="false";
    	  value2="true";
      }
      if(jr2.isSelected()){
    	  value1="true";
    	  value2="true";
      }
      
      propoutG.put(key1, value1);
      propoutG.put(key2, value2);
      
      JFormattedTextField fw=(JFormattedTextField) ((JPanel) (p.getComponent(21))).getComponent(0);
      JFormattedTextField bw=(JFormattedTextField) ((JPanel) (p.getComponent(21))).getComponent(2);
      String valuefw=fw.getText();
      String valuebw=bw.getText();
      propoutG.put("seuil_branch",valuefw);
      propoutG.put("seuil_root",valuebw);
      
      
      FileOutputStream outG = new FileOutputStream("appProperties_global");
      propoutG.store(outG, "---No Comment---");
      outG.close();
       
      
	}

	public static void raz() {
		
		filters_updated=false;
		Listproperties= new ArrayList<String>();
		//prefilters = new ArrayList<List<Object>>();
		general_options = new Hashtable<String, String>();
		firstopening=true;
		//tabbedPane.setselected(1);
		
		
	}



	
       
       
}  
	

	

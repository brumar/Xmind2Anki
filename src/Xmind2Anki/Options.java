package Xmind2Anki;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Options {
	
	static String opendirectory = "";
	static String savedirectory;
	static String ankidirectory;
	//FILE OPTIONS (start)
	static String directory = "";
	static String filename = "import_4_java_edited.anki";
	static  File src   = new File("test_import_4.anki");
	//FILE OPTIONS (end)
	
	
	
	//GLOBAL OPTIONS (debut)
		//concerning every topic
	static boolean always_keep_your_style=true;
	static boolean always_keep_your_link=true;
	public static boolean always_keep_your_notes=false;
	public static boolean always_keep_your_label=false;
	public static boolean always_keep_your_images=true;
	public static boolean always_equal_priority=false;
	public static boolean always_questions_all=false;
	public static boolean always_answers_all=false;
	

	
	public static boolean never_content_in_notes=true;
	public static boolean never_content_in_label =true;
	
	//public static boolean never_concat =true;
	//public static boolean always_concat =false;
	
		//concerning some topics
	static boolean epurer_topic_vide=true;
	static boolean rank_options=true;
	static boolean question_by_marker=true;
	static boolean answer_by_marker=true;
	static boolean glisseur_for_unique=true;
	//GLOBAL OPTIONS (fin)
	
	//SPECIAL OPTIONS
		//if rank_options=true
	static int seuil_branch=4;
	static int seuil_root=0;
	
		//if question/answer by marker=true
	
	static String markerToQuestion="flag-green";
	static String markerToAnswer="other-question";
	public static String tempfile;
	public static boolean lock_keep_your_marker;
	public static boolean lock_keep_your_label;
	public static boolean lock_keep_your_style;
	public static boolean lock_keep_your_notes;
	public static boolean lock_keep_your_link;
	public static boolean lock_content_in_notes;
	public static boolean lock_content_in_label;
	public static boolean lock_questions_all;
	public static boolean lock_answers_all;
	public static boolean duplicateanki;
	public static boolean linkToanki;
	public static String Xmind_temp_storage;
	public static boolean lock_keep_your_images;
	public static boolean always_keep_your_marker=true;
	public static boolean Css_tree=true;
	
	

	
	public Options() throws IOException{
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream("appProperties_global");
		defaultProps.load(in);
		
		Options.Xmind_temp_storage=defaultProps.getProperty("mind_temp_storage","Xmind_temp/");
		Options.ankidirectory=defaultProps.getProperty("ankidirectory","anki");
		Options.savedirectory=defaultProps.getProperty("savedirectory","");
		Options.opendirectory=defaultProps.getProperty("opendirectory","");
		Options.tempfile=defaultProps.getProperty("tempfile","temp.xmind");
		Options.directory=defaultProps.getProperty("directory");
		Options.filename=defaultProps.getProperty("filename");
		Options.src=new File(defaultProps.getProperty("src")); 
		
		String linkToanki=defaultProps.getProperty("linkToanki","true");
		if (linkToanki.equals("true")){Options.linkToanki=true;}else{Options.linkToanki=false;}
		
		String glisseur_for_unique=defaultProps.getProperty("glisseur_for_unique","true");
		if (glisseur_for_unique.equals("true")){Options.glisseur_for_unique=true;}else{Options.glisseur_for_unique=false;}
		
		String duplicateanki=defaultProps.getProperty("duplicate","true");
		if((duplicateanki.equals("true")||(Options.linkToanki))){Options.duplicateanki=true;}else{Options.duplicateanki=false;}
		
		String epurer_topic_vide=defaultProps.getProperty("epurer_topic_vide");
		if (epurer_topic_vide.equals("true")){Options.epurer_topic_vide=true;}else{Options.epurer_topic_vide=false;}
		
		String always_keep_your_style=defaultProps.getProperty("always_keep_your_style");
		if (always_keep_your_style.equals("true")){Options.always_keep_your_style=true;}else{Options.always_keep_your_style=false;}
		
		String always_keep_your_makers=defaultProps.getProperty("always_keep_your_markers","true");
		if (always_keep_your_makers.equals("true")){Options.always_keep_your_marker=true;}else{Options.always_keep_your_marker=false;}
		
		String always_keep_your_images=defaultProps.getProperty("always_keep_your_images","true");
		if (always_keep_your_images.equals("true")){Options.always_keep_your_images=true;}else{Options.always_keep_your_images=false;}
		
		String always_keep_your_link=defaultProps.getProperty("always_keep_your_link");
		if (always_keep_your_link.equals("true")){Options.always_keep_your_link=true;}else{Options.always_keep_your_link=false;}
		
		String always_keep_your_notes=defaultProps.getProperty("always_keep_your_notes","true");
		if (always_keep_your_notes.equals("true")){Options.always_keep_your_notes=true;}else{Options.always_keep_your_notes=false;}
		
		String always_keep_your_label=defaultProps.getProperty("always_keep_your_label");
		if (always_keep_your_label.equals("true")){Options.always_keep_your_label=true;}else{Options.always_keep_your_label=false;}
		
		String always_equal_priority=defaultProps.getProperty("always_equal_priority");
		if (always_equal_priority.equals("true")){Options.always_equal_priority=true;}else{Options.always_equal_priority=false;}
		
		String always_questions_all=defaultProps.getProperty("always_questions_all");
		if (always_questions_all.equals("true")){Options.always_questions_all=true;}else{Options.always_questions_all=false;}
		
		String always_answers_all=defaultProps.getProperty("always_answers_all");
		if (always_answers_all.equals("true")){Options.always_answers_all=true;}else{Options.always_answers_all=false;}
		
		String never_content_in_notes=defaultProps.getProperty("never_content_in_notes");
		if (never_content_in_notes.equals("true")){Options.never_content_in_notes=true;}else{Options.never_content_in_notes=false;}
		
		String never_content_in_label=defaultProps.getProperty("never_content_in_label");
		if (never_content_in_label.equals("true")){Options.never_content_in_label=true;}else{Options.never_content_in_label=false;}
		
		/*String never_concat=defaultProps.getProperty("never_concat");
		if (never_concat.equals("true")){Options.never_concat=true;}else{Options.never_concat=false;}
		
		String always_concat=defaultProps.getProperty("always_concat");
		if (always_concat.equals("true")){Options.always_concat=true;}else{Options.always_concat=false;}*/
		
		String rank_options=defaultProps.getProperty("rank_options");
		if (rank_options.equals("true")){Options.rank_options=true;}else{Options.rank_options=false;}
		
		String seuil_branch=defaultProps.getProperty("seuil_branch");
		Options.seuil_branch=Integer.parseInt(seuil_branch);
		
		String seuil_root=defaultProps.getProperty("seuil_root");
		Options.seuil_root=Integer.parseInt(seuil_root);
		
		//locks
		String lock_keep_your_label=defaultProps.getProperty("lock_keep_your_label","false");
		if (lock_keep_your_label.equals("true")){Options.lock_keep_your_label=true;}else{Options.lock_keep_your_label=false;}
		
		String lock_keep_your_images=defaultProps.getProperty("lock_keep_your_images","false");
		if (lock_keep_your_images.equals("true")){Options.lock_keep_your_images=true;}else{Options.lock_keep_your_images=false;}
		
		String lock_keep_your_style=defaultProps.getProperty("lock_keep_your_style","false");
		if (lock_keep_your_style.equals("true")){Options.lock_keep_your_style=true;}else{Options.lock_keep_your_style=false;}
		
		String lock_keep_your_notes=defaultProps.getProperty("lock_keep_your_notes","false");
		if (lock_keep_your_notes.equals("true")){Options.lock_keep_your_notes=true;}else{Options.lock_keep_your_notes=false;}
		
		String lock_keep_your_link=defaultProps.getProperty("lock_keep_your_link","false");
		if (lock_keep_your_link.equals("true")){Options.lock_keep_your_link=true;}else{Options.lock_keep_your_link=false;}
		
		String lock_content_in_notes=defaultProps.getProperty("lock_content_in_notes","false");
		if (lock_content_in_notes.equals("true")){Options.lock_content_in_notes=true;}else{Options.lock_content_in_notes=false;}
		
		String lock_content_in_label=defaultProps.getProperty("lock_content_in_label","false");
		if (lock_content_in_label.equals("true")){Options.lock_content_in_label=true;}else{Options.lock_content_in_label=false;}
		
		String lock_always_answers_all=defaultProps.getProperty("lock_answers_all","false");
		if (lock_always_answers_all.equals("true")){Options.lock_answers_all=true;}else{Options.lock_answers_all=false;}
		
		String lock_always_questions_all=defaultProps.getProperty("lock_questions_all","false");
		if (lock_always_questions_all.equals("true")){Options.lock_questions_all=true;}else{Options.lock_questions_all=false;}
		
		String lock_keep_your_marker=defaultProps.getProperty("lock_keep_your_markers","false");
		if (lock_keep_your_marker.equals("true")){Options.lock_keep_your_marker=true;}else{Options.lock_keep_your_marker=false;}
		
	}

}

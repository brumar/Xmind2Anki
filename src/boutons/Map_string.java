package boutons;



import java.util.HashMap;

public class Map_string {
	public static HashMap<String, String> mapStringInterface_to_String = new HashMap<String, String>();
	public static HashMap<String, String> mapString_to_StringInterface = new HashMap<String, String>();
		
	
	Map_string(){
		add("presence question","take as a question");
		add("presence answer","take as an answer");
		add("keep label","keep label");
		add("keep images","keep image");
		add("keep marker","keep marker");
		add("keep style","keep style");
		add("keep notes","keep note");
		add("keep link","keep link");
		add("content in notes","take answer in notes");
		add("content in label","take answer in label");
		add("glisseur","replace by childs");
		add("high priority","set priority : high");
		add("highest priority","set priority : very high");
		add("low priority","set priority : low");
	
		
		add("never_content_in_label","label as answer");
		add("epurer_topic_vide","avoid empty topics");
		add("always_equal_priority","equal priority");
		add("always_questions_all","preselection question");
		add("never_content_in_notes","label as answer");
		add("always_keep_your_images","keep images");
		add("always_keep_your_markers","keep markers");
		add("always_keep_your_link","keep links");
		add("always_keep_your_label","keep labels");
		add("always_answers_all","preselection answer");
		add("always_concat","avoid singleton"); 
		add("always_keep_your_notes","keep notes");
		add("always_keep_your_style","keep styles");
		add("rank_options","rank filter");
		add("never_content_in_notes", "note as answer");
		
		
		add("lock_content_in_notes", "lock_note as answer");
		add("lock_keep_your_markers","lock_keep markers");
		add("lock_keep_your_images","lock_keep images");
		add("lock_content_in_label","lock_label as answer");
		add("epurer_topic_vide","lock_replace empty topics by child");
		add("lock_equal_priority","lock_equal priority");
		add("lock_questions_all","lock_preselection question");
		add("lock_content_in_notes","lock_label as answer");
		add("lock_keep_your_link","lock_keep links");
		add("lock_keep_your_label","lock_keep labels");
		add("lock_answers_all","lock_preselection answer");
		add("lock_concat","lock_replace 1-child-topics by the child"); 
		add("lock_keep_your_notes","lock_keep notes");
		add("lock_keep_your_style","lock_keep styles");
	
				
		
	}
	
	String find(String string,boolean bool){
		if (bool){return mapStringInterface_to_String.get(string);}
		else {return mapString_to_StringInterface.get(string);}
		
	}
	
	static void add(String s, String i){
		mapStringInterface_to_String.put(i, s);
		mapString_to_StringInterface.put(s, i);
	}
	
	
}




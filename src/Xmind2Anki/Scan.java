package Xmind2Anki;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.xmind.core.CoreException;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;

import boutons.Xmind2AnkiFenetre;


public class Scan {

	/**
	 * @param args
	 * @throws CoreException 
	 * @throws IOException 
	 */
	public static HashMap<String, String> Notes = new HashMap<String, String>();
	public static List<List<String>> Map_my_map = new ArrayList<List<String>>();
	public static List<String> TopicList= new ArrayList<String>();
	public static Map map=new Map();
	public static List<String> properties=new ArrayList<String>();
	
	public static List<String> scanW(String dir, String file) throws IOException, CoreException, SqlJetException{
		properties.clear();
		TopicList.clear();
		Map_my_map.clear();
		map.clear();
		Notes.clear();
		
		//String Workbook = "C:/Users/bruno/sheets.xmind";
		String Workbook=dir+"/"+file;
		IWorkbook workbook = org.xmind.core.Core.getWorkbookBuilder().loadFromPath(Workbook); 
		
		List<ISheet> SheetList=workbook.getSheets();
		for (int i=0;i<SheetList.size();i++){
			ISheet sheet=SheetList.get(i);
			ITopic rootTopic=sheet.getRootTopic();
			TopicList.clear();
			TopicList.add(rootTopic.getId());
			Map_my_map.clear();
			map.clear();
			int dar=1;
			recurcive_list_my_childrens(rootTopic);
			map.update(TopicList);
			List_properties prop=new List_properties(map,sheet);
			List<String>p=prop.getList_prop();
			
			properties.addAll(p);
		}	
		Hashtable<String,String> ppp=reloadfilters();
		//Map_string();
		
        Set set = new HashSet() ;
        set.addAll(properties) ;
        properties = new ArrayList(set) ;
        
        List<String> patterns=takepatterns();
        
      //parcour des proprerties debut
		
      		for (int i=0;i<properties.size();i++){
      			if (ppp.containsKey(properties.get(i))){
      				for(int j=0;j<patterns.size();j++){
      					String patt=patterns.get(j);
      					String value=ppp.get(properties.get(i));
      					if(value.contains(patt)){
      						boolean Do=false;
      						boolean Gp=false;
      						if((patt.substring(1,2)).equals((patt.substring(1,2)).toLowerCase())){Gp=true;}
      						if((patt.substring(0,1)).equals((patt.substring(0,1)).toLowerCase())){Do=true;}
      						List<Object> pft= new ArrayList<Object> ();
      						//value,Do, patt.toLowerCase(), Gp
      						pft.add(properties.get(i));
      						pft.add(Do);
      						pft.add(patt.toLowerCase());
      						pft.add(Gp);
      						Xmind2AnkiFenetre.prefilters.add(pft);
      					}
      				}
      			}
      		}
      		
      		
      		//parcour des proprerties fin
      		

		return properties;
		
	}
	
	private static List<String> takepatterns() {
		List<String> patterns=new ArrayList<String>();
		patterns.add("presence question");
		patterns.add("presence answer");
		patterns.add("keep label");
		patterns.add("keep style");
		patterns.add("keep notes");
		patterns.add("keep link");
		patterns.add("content in notes");
		patterns.add("content in label");
		patterns.add("glisseur");
		patterns.add("high priority");
		patterns.add("highest priority");
		patterns.add("low priority");
		//**************** PATTERNS
		
		//************DERIVATED PATTERNS (blocks and opposite)
		List<String> patterns_final=new ArrayList<String>();
		int lg=patterns.size();
		for (int i=0;i<lg;i++){
			String pattern=patterns.get(i);
			patterns_final.add(pattern);
			
			String part1=pattern.substring(0,1);
			String part2=pattern.substring(1,2);
			String part3=pattern.substring(2);
			
			String pattern_final=part1+part2.toUpperCase()+part3;
			patterns_final.add(pattern_final);
			
			String pattern_final2=part1.toUpperCase()+part2+part3;
			patterns_final.add(pattern_final2);
			
			String pattern_final3=part1.toUpperCase()+part2.toUpperCase()+part3;
			patterns_final.add(pattern_final3);
		}
		return patterns_final;
	}
	private static Hashtable<String, String> reloadfilters() throws IOException {
		FileInputStream out = new FileInputStream("appProperties_local");
		Properties appProp = new Properties();
		appProp.load(out);
		return(Hashtable) appProp;
		
	}
	private static void recurcive_list_my_childrens(ITopic Topic) {

			List<String> Map_my_sons = new ArrayList<String>();
		Map_my_sons.add(Topic.getId()); //the first item is the ID of the father, the next ones will be the id of the childrens
		if((Topic.hasChildren(ITopic.ATTACHED))||(Topic.hasChildren(ITopic.DETACHED))){
			List<ITopic> allChildren = Topic.getAllChildren();
			for (int i=0; i<allChildren.size();i++) {
				Map_my_sons.add(allChildren.get(i).getId());
				TopicList.add(allChildren.get(i).getId());//les summaries vont ils poser pbm ?
				
			}
			Map_my_map.add(Map_my_sons);
			for (int i=0; i<allChildren.size();i++) {
			recurcive_list_my_childrens(allChildren.get(i));
			}		
		}
		
		
	}

   }


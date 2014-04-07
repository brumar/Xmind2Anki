package Xmind2Anki;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Update_Options {
	static List<String> Parentlist_for_options= new ArrayList<String>();

	public void Update(Properties_Options prop_op, List_properties prop) throws IOException{
		
		
		
		
		int lp=RunSheet.Map_my_map_pure.size();
		for(int i=0;i<lp;i++){
			Parentlist_for_options.add(RunSheet.Map_my_map_pure.get(i).get(0));
		}
		
		
		
		
		
		List<String> properties_top=prop.getList_prop(); 
		List<List<Integer>> indextopic_prop=prop.getIndextopic_prop();
		
		List<List<String>> properties_options=prop_op.getPo();
		List<String> properties_ut=prop_op.getProperties();
		/*
		properties_ut.add("flag-green");
		List<String> a=new ArrayList<String>();
		a.add("presence question");
		properties_options.add(a);
		
		properties_ut.add("other-question");
		List<String> b=new ArrayList<String>();
		b.add("presence answer");
		properties_options.add(b);
		
		properties_ut.add("other-note");
		List<String> c=new ArrayList<String>();
		c.add("keep notes");
		properties_options.add(c);
		
		properties_ut.add("lab da");
		List<String> d=new ArrayList<String>();
		d.add("keep label");
		properties_options.add(d);
		
		properties_ut.add("bold");
		List<String> e=new ArrayList<String>();
		e.add("very high priority");
		properties_options.add(e);*/
		
		save_settings(properties_options,properties_ut);
		
		
		
		//other-question
		//presence answer
		List<List<Object>>updates_list=new ArrayList<List<Object>>();
		
		int l=properties_ut.size();
		for (int i=0;i<l;i++){
			if (properties_top.contains(properties_ut.get(i))){
				int index=properties_top.lastIndexOf(properties_ut.get(i));
				List<Integer> top=indextopic_prop.get(index);
				int l2=top.size();
				List<String> op=properties_options.get(i);
				int l3=op.size();
				for(int k=0;k<l2;k++){
					
					for (int k2=0;k2<l3;k2++){
						List<Object>update_list=new ArrayList<Object>();
						update_list.add(top.get(k));
						update_list.add(op.get(k2));
						updates_list.add(update_list);
						
						//update_topic_option(top.get(k),op.get(k2));
					}
					
					//update_option(top.get(k))
				}
				
				
			}
		}
		updates_list=sort(updates_list);
		for (int i=0;i<updates_list.size();i++){
			update_topic_option((Integer)updates_list.get(i).get(0),(String) updates_list.get(i).get(1));
		}
		
	}

	private List<List<Object>> sort(List<List<Object>> updates_list) {
		
		List<List<Object>>updates_list1=new ArrayList<List<Object>>();
		List<List<Object>>updates_list2=new ArrayList<List<Object>>();
		List<List<Object>>updates_list3=new ArrayList<List<Object>>();
		List<List<Object>>updates_list4=new ArrayList<List<Object>>();
		
		List<List<Object>>updates_list_final=new ArrayList<List<Object>>();
		
		for(int i=0;i<updates_list.size();i++){
			String s=(String) updates_list.get(i).get(1);
			Boolean bool1=false;
			Boolean bool2=false;
			if((s.substring(1,2)).equals((s.substring(1,2)).toUpperCase())){bool2=true;}//une majuscule à la deuxième lettre indique un bloc
			if((s.substring(0,1)).equals((s.substring(0,1)).toUpperCase())){ bool1=true;} //une minuscule à la première lettre indique un true
				
			if(bool2){
				//if(!bool1){
					updates_list1.add(updates_list.get(i));
				//}
				//else{
					//updates_list2.add(updates_list.get(i));
				//}
			}
			else{
				if(!bool1){
					updates_list3.add(updates_list.get(i));
				}
				else{
					updates_list4.add(updates_list.get(i));
				}
			}
		}
			updates_list1=ordinate(updates_list1);
			//updates_list2=ordinate(updates_list2);	
			updates_list3=ordinate(updates_list3);	
			updates_list4=ordinate(updates_list4);	
			
		
		updates_list_final.addAll(updates_list1);
		//updates_list_final.addAll(updates_list2);
		updates_list_final.addAll(updates_list3);
		updates_list_final.addAll(updates_list4);
		
		return updates_list_final;
	}

	private List<List<Object>> ordinate(List<List<Object>> updates_list) {
		List<List<Object>>ordinate_list=new ArrayList<List<Object>>();
		boolean end=updates_list.isEmpty();
		while (!end){
			int min=(Integer) updates_list.get(0).get(0);
			int index=0;
			for (int i=1;i<updates_list.size();i++){
				if (min>(Integer) updates_list.get(i).get(0)){
					min=(Integer)updates_list.get(i).get(0);
					index=i;
				}
			}
			ordinate_list.add(updates_list.get(index));
			updates_list.remove(index);
			end=updates_list.isEmpty();
		}
		return ordinate_list;
	}

	private void update_topic_option(Integer i, String string) {
		boolean bool=true;
		
		
		if((string.substring(1,2)).equals((string.substring(1,2)).toUpperCase())){//une majuscule à la deuxième lettre indique un bloc
			if((string.substring(0,1)).equals((string.substring(0,1)).toLowerCase())){ bool=true;} //une minuscule à la première lettre indique un true
			else {bool=false;}//même fonctionnement, une majuscule au début permet de faire la négation
			
			
			if((string.toLowerCase().equals("presence question"))&&(Options.lock_questions_all==false)){
				inherit(i,5,bool);
			}
			if((string.toLowerCase().equals("presence answer"))&&(Options.lock_answers_all==false)){
				inherit(i,6,bool);
			}
			if((string.toLowerCase().equals("keep label"))&&(!Options.lock_keep_your_label)){
				inherit(i,3,bool);
			}
			if((string.toLowerCase().equals("keep style"))&&(!Options.lock_keep_your_style)){
				inherit(i,1,bool);
			}
			if((string.toLowerCase().equals("keep notes"))&&(!Options.lock_keep_your_notes)){
				inherit(i,2,bool);
			}
			if((string.toLowerCase().equals("keep link"))&&(!Options.lock_keep_your_link)){
				inherit(i,0,bool);
			}
			if((string.toLowerCase().equals("content in notes"))&&(!Options.lock_content_in_notes)){
				inherit(i,7,bool);
			}
			if((string.toLowerCase().equals("content in label"))&&(!Options.lock_content_in_label)){
				inherit(i,8,bool);
			}
			if(string.toLowerCase().equals("glisseur")){
				inherit(i,9,bool);
			}
			if((string.toLowerCase().equals("keep image"))&&(!Options.lock_keep_your_images)){
				inherit(i,10,bool);
			}
			if(Options.always_equal_priority==false){
				if(string.toLowerCase().equals("high priority")){
					inherit(i,4,3);
				}
				if(string.toLowerCase().equals("highest priority")){
					inherit(i,4,4);
				}
				if(string.toLowerCase().equals("low priority")){
					inherit(i,4,1);
				}
			}
			
		}else{
		
		
		
		
		
		
		
		if((string.substring(0,1)).equals((string.substring(0,1)).toLowerCase())){ bool=true;}
		else {bool=false;}//la présence d'une majuscule => présence d'une négation
		
		if((string.toLowerCase().equals("presence question"))&&(Options.lock_questions_all==false)){
			RunSheet.Opt.option_by_topic[i][5]=bool;
		}
		if((string.toLowerCase().equals("presence answer"))&&(Options.lock_answers_all==false)){
			RunSheet.Opt.option_by_topic[i][6]=bool;
		}

		if(Options.always_equal_priority==false){
			if(string.toLowerCase().equals("high priority")){
				RunSheet.Opt.option_by_topic[i][4]=3;
			}
			if(string.toLowerCase().equals("highest priority")){
				RunSheet.Opt.option_by_topic[i][4]=4;
			}
			if(string.toLowerCase().equals("low priority")){
				RunSheet.Opt.option_by_topic[i][4]=1;
			}
		}
		if((string.toLowerCase().equals("keep label"))&&(!Options.lock_keep_your_label)){
			RunSheet.Opt.option_by_topic[i][3]=bool;
		}
		if((string.toLowerCase().equals("keep style"))&&(!Options.lock_keep_your_style)){
			RunSheet.Opt.option_by_topic[i][1]=bool;
		}
		if((string.toLowerCase().equals("keep notes"))&&(!Options.lock_keep_your_notes)){
			RunSheet.Opt.option_by_topic[i][2]=bool;
		}
		if((string.toLowerCase().equals("keep link"))&&(!Options.lock_keep_your_link)){
			RunSheet.Opt.option_by_topic[i][0]=bool;
		}
		if((string.toLowerCase().equals("content in notes"))&&(!Options.lock_content_in_notes)){
			RunSheet.Opt.option_by_topic[i][7]=bool;
		}
		if((string.toLowerCase().equals("content in label"))&&(!Options.lock_content_in_label)){
			RunSheet.Opt.option_by_topic[i][8]=bool;
		}
		if(string.toLowerCase().equals("glisseur")){
			RunSheet.Opt.option_by_topic[i][9]=bool;
		}
		
		}
	}
	private void inherit(Integer i, int j, Object obj) {
		
		List<String> childrens=new ArrayList<String>();
		if(j!=9){
			RunSheet.Opt.option_by_topic[i][j]=obj;
		int da=1;	
		} //explication pour les glisseurs, on évite que le topic parent prenne la propriété
		String id=RunSheet.map.find(i);
		int index= Parentlist_for_options.indexOf(id);
		
		if (index!=-1){
			childrens=RunSheet.Map_my_map_pure.get(index);
			int l=childrens.size();
		
		//map.find(i);
		//RunSheet.Map_my_map_pure : enfants parents
		
			for (int d=1;d<l;d++){
				int index_child=RunSheet.map.find(childrens.get(d));
				inherit(index_child,j,obj);
				if(j==9){RunSheet.Opt.option_by_topic[index_child][j]=obj;}
				}
		}
	}
	private void save_settings(List<List<String>> properties_options,
			List<String> properties_ut) throws IOException {
			
		FileOutputStream out = new FileOutputStream("appProperties_local");
		Properties appProp = new Properties();
		
		int l=properties_ut.size();	
		
		for(int i=0;i<l;i++){
			String key=properties_ut.get(i);
			String value="";
			for (int j=0;j<properties_options.get(i).size();j++){
				value+=properties_options.get(i).get(j)+"\\";
			}
			appProp.setProperty(key, value);
		}
		
		appProp.store(out, "Ensemble des proprietes disponibles : presence question, presence answer, high priority,\n " +
				"highest priority, low priority, keep label, keep style, keep notes, keep link, content in notes, content in label \n \n" +
				"exemple de declencheurs disponibles : [#0000FF, SimSun, 18pt, italic, bold, #00FF00, #FF00FF, #FFE76D, #FF0000, flag-green, other-note, other-question, \n other-exclam, priority-1, priority-2, priority-3, lab 1, lab 2, lab da]" +
				"");
		//appProp.store(out, "exemples de clés utilisables : presence question, presence answer, high priority, highest priority, low priority, keep label, keep style, keep notes, keep link, content in notes, content in label");
		out.close();
		}
}

package Xmind2Anki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Map {
	public static HashMap<Integer, String> mapInt_to_String = new HashMap<Integer, String>();
	public static HashMap<String, Integer> mapString_to_Int = new HashMap<String, Integer>();
	
		
	int find(String string){
		return mapString_to_Int.get(string);
		
	}
	String find(Integer i){
		return mapInt_to_String.get(i);
	}
	static void add(String s, int i){
		mapInt_to_String.put(i, s);
		mapString_to_Int.put(s, i);
	}
	static void add(int i,String s){
		mapInt_to_String.put(i, s);
		mapString_to_Int.put(s, i);
	}
	void update(List<String> topicList) {
		int l=topicList.size();
		for (int i=0;i<l;i++){
			add(i,topicList.get(i)); 
		}
	}
	List<String> list_me(){
		Set<String> da= mapString_to_Int.keySet();
		List<String> list = new ArrayList<String>(da);
		return list;
	}
	static int size(){
		return mapInt_to_String.size();
	}
	public void clear() {
		mapInt_to_String = new HashMap<Integer, String>();
		mapString_to_Int = new HashMap<String, Integer>();
		
	}
}




package Xmind2Anki;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Properties_Options {

	List<String> properties;
	public List<String> getProperties() {
		return properties;
	}
	public List<List<String>> getPo() {
		return po;
	}
	List<List<String>> po; 
	Properties_Options() throws IOException{
	this.po=new ArrayList<List<String>>();
	this.properties=new ArrayList<String>();
	
	
	//**************** PATTERNS
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
	//*************DERIVATED PATTERNS
	
	
	
	
	
	//******************* test load properties
	
	
	FileInputStream out = new FileInputStream("appProperties_local2");
	Properties appProp = new Properties();
	appProp.load(out);
	
	Set<String> kset=appProp.stringPropertyNames();
	this.properties=new ArrayList<String>(kset);
	
	int l=this.properties.size();
	
	for(int i=0;i<l;i++){
	List<String> a=new ArrayList<String>();
	String prop=appProp.getProperty(properties.get(i));
		for (int j=0;j<patterns_final.size();j++){
			if (prop.contains(patterns_final.get(j))){
				a.add(patterns_final.get(j));
			}
		}
	po.add(a);
	}
	//presence answer
	
	
	
	
	
	
	
	
	
	//*************
	
	
	
	
	
}
	public void add_properties_option(String prop,String opt){
	
		if (properties.contains(prop)){ //si la propriété existe déjà
			int i=properties.lastIndexOf(prop); //on chercher l'index		
			if (!po.get(i).contains(opt)){ //si elle n'existe pas
				po.get(i).add(opt); //on l'a rajoute
		}
	}
		else {properties.add(prop); 
		List<String> op=new ArrayList<String>(); 
		po.add(op);
		}
	
}
}

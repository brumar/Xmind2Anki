package Xmind2Anki;

import java.util.ArrayList;
import java.util.List;

public class Style_convert {

	public static List<String> span_convert(List<List<String>> styles) {
		List<String> span= new ArrayList<String>();
		for(int i=0;i<styles.size();i++){
			String answer="style=\"";
			System.out.println("styke : "+i);
			if((styles.get(i).get(4)!=null)&&(styles.get(i).get(4).equals("bold"))){
				answer+="font-weight:600;";
			}
			if((styles.get(i).get(3)!=null)&&(styles.get(i).get(3).equals("italic"))){
				answer+=" font-style:italic;";
			}
			if(styles.get(i).get(0)!=null){
				String color=styles.get(i).get(0);
				color=color.toLowerCase();
				answer+="color:"+color+";";
			}
			if(styles.get(i).get(5)!=null){
				String colorb=styles.get(i).get(5);
				colorb=colorb.toLowerCase();
				answer+="background:"+colorb+";";
			}
			answer+="\"";
			span.add(answer);
		}
		 //style="font-weight: bold; font-style: italic; color: rgb(0, 0, 1);"
		return span;
		
	}



}

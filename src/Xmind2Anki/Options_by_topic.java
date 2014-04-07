package Xmind2Anki;

public class Options_by_topic {

	Object[][] option_by_topic; 
	
	Options_by_topic(Map map){
		int l=Map.size();
		this.option_by_topic=new Object[l][12];
		//link;
			for(int i=0;i<l;i++){
				option_by_topic[i][0]=Options.always_keep_your_link;
				option_by_topic[i][1]=Options.always_keep_your_style;
				option_by_topic[i][2]=Options.always_keep_your_notes;
				option_by_topic[i][3]=Options.always_keep_your_label;
				option_by_topic[i][4]=2;//par défaut
				option_by_topic[i][5]=Options.always_questions_all ;
				option_by_topic[i][6]=Options.always_answers_all ;
				option_by_topic[i][7]=!Options.never_content_in_notes;
				option_by_topic[i][8]=!Options.never_content_in_label ;
				option_by_topic[i][9]=false;//par défaut aucun topic n'est un glisseur
				option_by_topic[i][10]=Options.always_keep_your_images;
				option_by_topic[i][11]=Options.always_keep_your_marker;
			}
		}
}

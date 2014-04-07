package Xmind2Anki;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.marker.IMarkerRef;

public class List_properties {

	private List<String> list_prop;
	private List<List<Integer>> indextopic_prop;

	public List<String> getList_prop() {
		return list_prop;
	}

	public List<List<Integer>> getIndextopic_prop() {
		return indextopic_prop;
	}

	List_properties(Map map, ISheet sheet) throws FileNotFoundException{
		int l=Map.size();
		IWorkbook w=sheet.getOwnedWorkbook();
		
		//***********LABEL debut******************
		
		
		List<String> list_label=new ArrayList<String>(); //contient la liste des labels possibles
		List<List<Integer>> indextopiclab=new ArrayList<List<Integer>>(); //contient pour chaque label, la liste des topics concernés
		for (int i=0;i<l;i++){
		String id=map.find(i);
		ITopic t=w.findTopic(id);
		List<String> lab=new ArrayList(t.getLabels()); //liste des labels du topic i
		
		for (int j=0;j<lab.size();j++){
			if (!(list_label.contains("lab "+lab.get(j)))){ //si un label n'est pas encore dans la liste des labels possibles
				list_label.add("lab "+lab.get(j));//on ajoute ce label
				List<Integer> newtopiclabel=new ArrayList<Integer>(); //on crée une nouvelle liste de topic
				newtopiclabel.add(i);//auquel on ajoute le topic i
				indextopiclab.add(newtopiclabel);//cette nouvelle liste de topics est ajoutée à la liste principale
			}
			else{ //si le label est déjà dans la liste des labels possibles
				int index=list_label.lastIndexOf("lab "+lab.get(j)); //on cherche en quelle position, cette position est la même que celle de la liste principale
				indextopiclab.get(index).add(i);//on ajoute le topic dans la bonne rangée de la liste principale
			}
		}
		
		}
		//***********LABEL fin******************
		
		
		//***********MARKER debut******************
		
		List<String> list_marker=new ArrayList<String>(); //contient la liste des markers possibles
		List<List<Integer>> indextopicmark=new ArrayList<List<Integer>>(); //contient pour chaque marker, la liste des topics concernés
		for (int i=0;i<l;i++){
		String id=map.find(i);
		ITopic t=w.findTopic(id);
		Set<IMarkerRef> markerSet = t.getMarkerRefs();
		Object[] array=markerSet.toArray();
		//.getMarkerId()
		for (int j=0;j<array.length;j++){
			if (!(list_marker.contains(((IMarkerRef) array[j]).getMarkerId()))){ //si un marker n'est pas encore dans la liste des markers possibles
				list_marker.add(((IMarkerRef) array[j]).getMarkerId());//on ajoute ce marker
				List<Integer> newtopicmarker=new ArrayList<Integer>(); //on crée une nouvelle liste de topic
				newtopicmarker.add(i);//auquel on ajoute le topic i
				indextopicmark.add(newtopicmarker);//cette nouvelle liste de topics est ajoutée à la liste principale
			}
			else{ //si le marker est déjà dans la liste des markers possibles
				int index=list_marker.lastIndexOf(((IMarkerRef) array[j]).getMarkerId()); //on cherche en quelle position, cette position est la même que celle de la liste principale
				indextopicmark.get(index).add(i);//on ajoute le topic dans la bonne rangée de la liste principale
			}
		}
		
		}
		//***********MARKER fin******************
		
		//*************Style debut
		
		List<String> list_style=new ArrayList<String>(); //contient la liste des styles possibles
		List<List<Integer>> indextopicstyl=new ArrayList<List<Integer>>(); //contient pour chaque style, la liste des topics concernés
		for (int i=0;i<l;i++){
		String id=map.find(i);
		ITopic t=w.findTopic(id);
		List<String> styl=RunSheet.find_my_topic_style(i,w); //liste des styles du topic i
		
		for (int j=0;j<styl.size();j++){
			if(styl.get(j)!=null){
			if (!(list_style.contains(styl.get(j)))){ //si un style n'est pas encore dans la liste des styles possibles
				list_style.add(styl.get(j));//on ajoute ce style
				List<Integer> newtopicstyle=new ArrayList<Integer>(); //on crée une nouvelle liste de topic
				newtopicstyle.add(i);//auquel on ajoute le topic i
				indextopicstyl.add(newtopicstyle);//cette nouvelle liste de topics est ajoutée à la liste principale
			}
			else{ //si le style est déjà dans la liste des styles possibles
				int index=list_style.lastIndexOf(styl.get(j)); //on cherche en quelle position, cette position est la même que celle de la liste principale
				indextopicstyl.get(index).add(i);//on ajoute le topic dans la bonne rangée de la liste principale
			}
			}
		}
		//*************Style fin
		
		}
		List<String> list_prop=new ArrayList<String>(); //contient la liste des proprietes possibles
		List<List<Integer>> indextopic_prop=new ArrayList<List<Integer>>(); //contient pour chaque style, la liste des topics concernés
		
		list_prop.addAll(list_style);
		list_prop.addAll(list_marker);
		list_prop.addAll(list_label);
		
		indextopic_prop.addAll(indextopicstyl);
		indextopic_prop.addAll(indextopicmark);
		indextopic_prop.addAll(indextopiclab);
		
		this.list_prop=list_prop;
		this.indextopic_prop=indextopic_prop;
		
	//	save_settings(list_prop,indextopic_prop);
	}

	
}

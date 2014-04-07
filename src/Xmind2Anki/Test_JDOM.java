package Xmind2Anki;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.xmind.core.CoreException;

public class Test_JDOM {
	static org.jdom.Document document;
	 static Element racine;
	
	/**
	 * @param args
	 * @throws SqlJetException 
	 * @throws CoreException 
	 * @throws IOException 
	 */
	public static HashMap<String, String> find_notes(File file) throws IOException, CoreException, SqlJetException {
		HashMap<String, String> Notes = new HashMap<String, String>();
		      SAXBuilder sxb = new SAXBuilder();
		      try
		      {
		         document = sxb.build(file);
		      }
		      catch(Exception e){}

		      racine = document.getRootElement();    
		     List l=racine.getContent(); 
		     Iterator i=l.iterator();   
		     
		      while(i.hasNext())
		      {
		         Element courant = (Element)i.next();
		         recurcive_find(courant,Notes);
		      
		      }
			return Notes;     
		      
		   }      

	private static void recurcive_find(Element e,HashMap<String, String> Notes) {
		if(e.getName().equals("notes")){
			Element e2=(((Element) (e.getContent(1))));
			String texte=e2.getText();
			String id=((Element) e.getParent()).getAttributeValue("id");
			Notes.put(id, texte);
		}
		
		List l=e.getChildren();

			Iterator i=l.iterator();
			
			while(i.hasNext())
		      {
			         Element courant = (Element)i.next();
			         recurcive_find(courant,Notes);
			      
			      }
		
	}

	static void afficheALL()
	{
	   List listEtudiants = racine.getChildren();

	   Iterator i = listEtudiants.iterator();
	   while(i.hasNext())
	   {
	      Element courant = (Element)i.next();
	      List listTopic = courant.getChildren();
	      Iterator j = listTopic.iterator();
		   while(j.hasNext())
		   {
	     // courant.get
			   Element courant2 = (Element)j.next();
			   System.out.println(courant2.getAttributeValue("id"));
		   }
	   }
	}

}

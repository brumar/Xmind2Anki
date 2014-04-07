package Xmind2Anki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.xmind.core.CoreException;
import org.xmind.core.IAdaptable;
import org.xmind.core.IFileEntry;
import org.xmind.core.IImage;
import org.xmind.core.IManifest;
import org.xmind.core.INotes;
import org.xmind.core.INotesContent;
import org.xmind.core.IPlainNotesContent;
import org.xmind.core.IProperties;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.io.IStorage;
import org.xmind.core.marker.IMarker;
import org.xmind.core.marker.IMarkerGroup;
import org.xmind.core.marker.IMarkerRef;
import org.xmind.core.marker.IMarkerResource;
import org.xmind.core.marker.IMarkerSheet;
import org.xmind.core.style.IStyle;
import org.xmind.core.style.IStyleSheet;
import org.xmind.ui.style.Styles;

import boutons.Xmind2AnkiFenetre;

import progress_monitor.ProgressMonitorDemo;

public class RunSheet {
	static List<List<String>> Map_my_map = new ArrayList<List<String>>();
	static List<List<String>> Images = new ArrayList<List<String>>();
	static List<String> Markers = new ArrayList<String>();
	public static HashMap<String, String> HTImages = new HashMap<String, String>();

	static List<List<String>> Map_my_map_pure = new ArrayList<List<String>>();
	//static List<List<String>> Concat = new ArrayList<List<String>>();
	static List<String> AnswerList= new ArrayList<String>();
	static List<String> TopicList= new ArrayList<String>();
	static List<String> Parentlist_final= new ArrayList<String>();
	
	static List<String> unique=new ArrayList<String>();

	
	static String source;
	
	static Map map=new Map();
	static Options_by_topic Opt;

	public ISheet sheet;
	public Rank rank;
	static String ankisave;
	static List<Rank> Rank_list = new ArrayList<Rank>();
	
	RunSheet(ISheet sheet,String file){
		this.sheet=sheet;
		Map_my_map= new ArrayList<List<String>>();
		TopicList.clear();
		Map_my_map.clear();
		map.clear();
		Markers.clear();
		Map_my_map.clear();
		Images.clear();
		Markers.clear();
		HTImages.clear();
		Map_my_map_pure.clear();
		AnswerList.clear();
		TopicList.clear();
		Parentlist_final.clear();
		unique.clear();
		Rank_list.clear();
		Update_Options.Parentlist_for_options.clear();
		
		String nameSheet=sheet.getTitleText();
		nameSheet.replaceAll("\\\\", "\\\\\\\\");
		if(Options.duplicateanki){
			String dirpath=Options.ankidirectory+"/"+Xmind2AnkiFenetre.outputfilename.substring(0,Xmind2AnkiFenetre.outputfilename.length()-6)+"/"+nameSheet;
	        File Sheetdir   = new File(dirpath);
	        Sheetdir.mkdirs();
	        RunSheet.ankisave=dirpath;
		}
	}

	public  IWorkbook processXmind2Anki() throws IOException, CoreException, SqlJetException {
		
		

		String[][]fact_tab=RunSheet.extracts_facts_from_workbook(sheet);
		RunSheet.create_anki_file(fact_tab);
		IWorkbook workbook=RunSheet.enclose_anki_2_Xmind(sheet);
		
		return workbook;
		
	}

private static IWorkbook enclose_anki_2_Xmind(ISheet sheet) throws IOException {
		

	
	IManifest manifest =sheet.getOwnedWorkbook().getManifest();
	String directory = Options.directory;
	String filename =  Options.filename;
	String dirPath = directory + "dir/"+filename;

		FileInputStream fis = new FileInputStream(dirPath);
		File dest = new File(ankisave+"/"+sheet.getTitleText()+".anki");
		
		if(Options.duplicateanki){//si option duplicate
			 
			 
			if(Options.Css_tree){//si CSS tree, on copie cole les images necessaires
				
				List<String> lines=new ArrayList<String>();
				lines.add("vline.png");
				lines.add("node.png");
				lines.add("lastnode.png");
				
				lines.add("notes.png");
				String path=dest.getPath();
			    String dirpath=path.substring(0,path.length()-5)+".media";
				
				for (int l=0;l<lines.size();l++){
					String path_line ="markers/"+lines.get(l);
					File src_line = new File(path_line);
					
					String FileName =dirpath+"/"+lines.get(l);
					File dest_line = new File(FileName);
					File dopath = new File(dirpath);
					dopath.mkdirs();
					Methods.copyFile(src_line, dest_line);
				} 
				
				/*InputStream os=resource.getInputStream();
				String FileName =dirpath+"//"+id+".png";
				FileOutputStream writenFile = new FileOutputStream(FileName);
				int r = 0;
					while ((r = os.read()) != -1) {
						writenFile .write(r);
					}
					writenFile.flush();
					writenFile.close();*/
			}
			
			
			
			
			File src = new File(dirPath);
			 Methods.copyFile(src, dest);//on sauve le fichier anki
			 String path=dest.getPath();
			 String dirpath=path.substring(0,path.length()-5)+".media";
			
		     File media  = new File(dirpath);
		     media.mkdirs();
		     String path_to_Xmind=Options.Xmind_temp_storage;
				 
			    //***************on sauve l'ensemble des images [DEBUT]
		     
			      int l=Images.size();
			     
			      for(int i=0;i<l;i++){
			    	String path_image=path_to_Xmind+Images.get(i).get(1);
			    	 File src_image = new File(path_image); 
			    	 File dest_image= new File(dirpath+"/"+Images.get(i).get(1).substring(12));
			    	 Methods.copyFile(src_image, dest_image);
			      } 
			    //************on sauve l'ensemble des images [FIN]
			    	 
			      
			      
			     //**************on sauve l'ensemble des markers [DEBUT]
			      
			      Properties defaultProps = new Properties();
					FileInputStream inp = new FileInputStream("markers/markerSheetResource.properties");
					defaultProps.load(inp);
					Hashtable<String,String> resources=(Hashtable) defaultProps;
					ITopic rootTopic =sheet.getRootTopic();
					

				      int lm=Markers.size();
				     
				      for(int i=0;i<lm;i++){
				    	String  id=Markers.get(i);
				    	System.out.println(id);
						IMarkerSheet markerSheet = sheet.getOwnedWorkbook().getMarkerSheet();
						IMarker m=markerSheet.findMarker(id);
						if (m!=null){
							IMarkerResource resource=m.getResource();
							//resource.
							InputStream os=null;
							String p=resource.getPath();
							File p2=new File(p);
							if(p2.canRead()){
							os=resource.getInputStream();
					
							String FileName =dirpath+"/"+id;//+".png";
							FileOutputStream writenFile = new FileOutputStream(FileName);
							int r = 0;
								while ((r = os.read()) != -1) {
									writenFile .write(r);
								}
								writenFile.flush();
								writenFile.close();}
					}	else{
						Ressource.saveRessource(id,resources,dirpath);
					}
					}
			      
			      
			      //***************on sauve l'ensemble des markers [FIN]
			
			 
			
			}
		/*Properties defaultProps = new Properties();
		FileInputStream inp = new FileInputStream("..//gen//markers//markerSheetResource.properties");
		defaultProps.load(inp);
		Hashtable<String,String> resources=(Hashtable) defaultProps;
		
		
		Set<IMarkerRef> markerSet = rootTopic.getMarkerRefs();
		if (!markerSet.isEmpty()){
			List<IMarkerRef> markerList=new ArrayList<IMarkerRef>();
			markerList.addAll(markerSet);
			IMarkerRef ref=markerList.get(1);
			String id=ref.getMarkerId();
			IMarkerSheet markerSheet = sheet.getOwnedWorkbook().getMarkerSheet();
			IMarker m=markerSheet.findMarker(id);
			if (m!=null){
			IMarkerResource resource=m.getResource();
			int dahh=1;
			InputStream os=resource.getInputStream();
			String FileName ="C://temp//marker.png";
			FileOutputStream writenFile = new FileOutputStream(FileName);
			int i = 0;
			while ((i = os.read()) != -1) {
				writenFile .write(i);
			}
			writenFile.flush();
			writenFile.close();
		}else{
			Ressource.saveRessource(id,resources,dirpath);
		}
		}*/
		
		ITopic rootTopic =sheet.getRootTopic();
		
		if (Options.linkToanki){
			rootTopic.setHyperlink("file:///"+dest.getCanonicalPath());
		}
		else{
		IFileEntry entry = manifest.createAttachmentFromStream(fis, filename);
		
		String path=entry.getPath();

		

		
		rootTopic.setHyperlink("xap:" + entry.getPath()); }// attachment
		
		
		/*
		
	
		String directory2 ="C://Users//bruno//Pictures//";
		String filename2 =  "bizar.png";
		String dirPath2 = directory2+filename2;
		FileInputStream fis2 = new FileInputStream(dirPath2);
		IFileEntry entry2 = manifest.createAttachmentFromStream(fis2, filename2);
		rootTopic.setHyperlink("xap:" + entry2.getPath());
		
		
		*/
		
		
		
		
		
		
		return sheet.getOwnedWorkbook();
		
		
		
		
		//
	}

	public static void create_anki_file(String[][] tab) throws IOException{
		SecureRandom random = new SecureRandom();
		 Connection connection = null;  
	        ResultSet resultSet = null;  
	        Statement statement = null; 
	        
	        File src   = Options.src;
	        String dirpath=Options.directory+"dir";
	        File dir   = new File(dirpath);
	        dir.mkdirs();
	        String destpath=Options.directory+"dir/"+Options.filename;
	        File dest   = new File(destpath);
	        
	        Methods.copyFile(src,dest);
	  
	        try {  
	            Class.forName("org.sqlite.JDBC");  
	            connection = DriverManager  
	                    .getConnection("jdbc:sqlite:"+destpath);  
	            statement = connection.createStatement();  
	           // statement.setEscapeProcessing(true); 
	            
	          //  int resultat = statement.executeUpdate("insert into Facts values(6323,259,269,5959,'papa','maman',3)")	;
	           
	      
	           statement.executeUpdate("delete from cards");
	           statement.executeUpdate("delete from fields");
	           statement.executeUpdate("delete from cardTags");
	           statement.executeUpdate("delete from facts");
	           statement.executeUpdate("delete from decks");
	           statement.executeUpdate("delete from reviewHistory");
	           int count=tab.length;
	           
	           //CSS [debut]
	          /* String string="ul.tree, ul.tree ul {     list-style-type: none;     background: url(vline.png) repeat-y;     margin: 0;     padding: 0;   }      ul.tree ul {     margin-left: 10px;   }   ul.tree li {     margin: 0;     padding: 0 12px;     line-height: 20px;     background: url(node.png) no-repeat;     color: #369;     font-weight: bold;   }   ul.tree li.last {     background: #fff url(lastnode.png) no-repeat;   }";
	          
	           PreparedStatement prep = connection.prepareStatement("insert into deckVars values (\"mobileCSS\", ?);");
	           prep.setString(1, string);
	           PreparedStatement prep2 = connection.prepareStatement("insert into decks values  (1,0,0,'',65,1,'',0.0,1.0,1.1,3.0,5.0,7.0,9.0,600,0,0.0,1,'PriorityVeryHigh','PriorityHigh','PriorityLow','',1,0,20,20,0,600,10800.0,"+count+","+count+",0,0,1,2,0)"  );
	           
	           connection.setAutoCommit(false);
	           prep2.executeBatch();
	           connection.setAutoCommit(true);
	           // statement.executeUpdate(csscmd);
	           
	           //CSS [fin]*/
	           
	           
	           
	           
	           
	           
	           for(int i=1;i<count+1;i++){
	        	   Integer cardID = new Integer( random.nextInt() );
	        	   Integer factsID = new Integer( random.nextInt() );
	        	   Integer field1ID = new Integer( random.nextInt() );
	        	   Integer field2ID = new Integer( random.nextInt() );
	        	   
	        	  int d1=100*(ProgressMonitorDemo.current_sheet+1);
	        	  double d2=(double)i/(double)count+1;
	        	  double d3=1/((double)ProgressMonitorDemo.nb_sheet+1);
	        	   
	        	   double da=d3*(d1+d2);
	        	  
	        	  
	        	  ProgressMonitorDemo.advancement=(int)Math.round(da);
	        	   System.out.println(da);
	        	   int n=map.find(Map_my_map.get(i-1).get(0));
	        	   int priority=(Integer) Opt.option_by_topic[n][4];
	        	   
	        	  
	        	   
	        	   String question=tab[i-1][0];
	        	   question=question.replace('\'',' ');
	        	   String answer=tab[i-1][1];
	        	   answer=answer.replace('\'',' ');
	        	   
	        	   Date date=new Date();
	       		   long time=date.getTime();
	       		   time=time/1000;
	        	   //String cmd="insert into cards values("+i+","+i+",1,0,0,'',0,'"+question+"','"+answer+"',2,0,0,0,0,2.5,2.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.0,0,2,0)";
	        	 //  String cmd="insert into cards values("+i+","+i+",1,"+time+","+time+",'',0,'"+question+"','"+answer+"',2,0,0,"+time+",0,2.5,2.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.0,0,2,"+time+")";   		   
	       		   String cmd="insert into cards values("+cardID+","+factsID+",1,"+time+","+time+",'',0,'"+question+"','"+answer+"',2,0,0,"+time+",0,2.5,2.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.0,0,2,"+time+")";
	       		  //PreparedStatement pstmt = connection.prepareStatement("insert into cards values("+i+","+i+",1,"+time+","+time+",'',0,'"+anser+"','"+answer+"',2,0,0,"+time+",0,2.5,2.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.0,0,2,"+time+")");
	       		  //pstmt.setString(1, question);
	       		 //pstmt.setString(2, answer);
	       		  
	       		//pstmt.executeUpdate();	
	        	   statement.executeUpdate(cmd);
	           int j=2*i-1;
	           int k=2*i;
	           //'tab["+i+"][0]','tab["+i+"][1]'
	           String command="insert into fields values("+field1ID+","+factsID+",1,0,'"+question+"')";
	           statement.executeUpdate(command);
	           statement.executeUpdate("insert into fields values("+field2ID+","+factsID+",2,1,'"+answer+"')");
	           
	           statement.executeUpdate("insert into cardTags values("+j+","+factsID+","+priority+",1)");
	          // statement.executeUpdate("insert into cardTags values("+k+","+i+",5,2)");
	           
	           statement.executeUpdate("insert into facts values  ("+factsID+",1,0,0,'','"+question+" "+answer+"','')"  );
	           }
	           statement.executeUpdate("insert into decks values  (1,0,0,'',65,1,'',0.0,1.0,1.1,3.0,5.0,7.0,9.0,600,0,0.0,1,'PriorityVeryHigh','PriorityHigh','PriorityLow','',1,0,20,20,0,600,10800.0,"+count+","+count+",0,0,1,2,0)"  );
	            
	    
	            //System.out.println(resultat2);

	         
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	              //  resultSet.close();  
	                statement.close();  
	                connection.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }
	}


		

	private static String[][] extracts_facts_from_workbook(ISheet sheet) throws IOException, CoreException  {
		
		
		ITopic rootTopic = sheet.getRootTopic();
		//rootTopic.getImage();
		/*
		IImage image = rootTopic.getAllChildren().get(0).getImage();
		rootTopic.getImage();
		System.out.println(image.getSource());
		image.setSource(image.getSource() );
		rootTopic.setHyperlink(image.getSource());
		*/
	
		
		System.out.println(rootTopic.getHyperlink());
		TopicList.add(rootTopic.getId());
		Map_my_map.clear();
		recurcive_list_my_childrens(rootTopic,0);
		Map_my_map_pure.addAll(Map_my_map);
		//TopicList_pure.addAll(TopicList);
		
		map.update(TopicList);
		Opt=new Options_by_topic(map);
		List_properties prop=new List_properties(map,sheet);
		Properties_Options prop_op=new Properties_Options();
		Update_Options opopop=new Update_Options();
		if(Options.glisseur_for_unique){
			for (int i=0;i<unique.size();i++){
				String id=unique.get(i);
				Opt.option_by_topic[map.find(id)][9]=true;
			}
		}
		opopop.Update(prop_op, prop);
		
		epurer(sheet);
		List<List<String>> styles=find_my_style(map,sheet);
		List<String> span=Style_convert.span_convert(styles);
		
		
		
		
		
		//6 18 23 25 26 43 44
		String[][] fact_tab=create_facts(sheet,span);
		return fact_tab;
	}

	private static List<List<String>> find_my_style(Map map2, ISheet sheet2) throws IOException, CoreException {
		IWorkbook workbook =sheet2.getOwnedWorkbook();
		
		//test
		//
		String da=Options.Xmind_temp_storage;
		workbook.setTempLocation(da);
		//workbook.saveTemp();		
		IStorage stor=workbook.getTempStorage();
		stor.getFullPath();
		String storage=workbook.getTempLocation();
	    //void setTempStorage(IStorage storage);
	    
		/*
		IStorage workbook.getTempStorage();
	    void setTempLocation(String tempLocation);
	    String getTempLocation();
		*/
		
		
		//test
		
		List<String> id=map2.list_me();
		List<List<String>> styles=new ArrayList<List<String>>();
		int l= id.size();
		for (int i=0;i<l;i++){
			List<String> style_list=find_my_topic_style(i,workbook);
			styles.add(style_list);
		}
		return styles;	
	}

	public static List<String> find_my_topic_style(int i, IWorkbook workbook) {
		List<String> style_list=new ArrayList<String>();
		
		
		
		if(map.find(i)==null){System.out.println(i);}
		else{ITopic top=workbook.findTopic(map.find(i));
		
		IStyleSheet styleSheet = workbook.getStyleSheet();
		IStyle style = styleSheet.findStyle(top.getStyleId());
		if (style == null) {
			  style = styleSheet.createStyle(IStyle.TOPIC);
			  styleSheet.addStyle(style, IStyleSheet.NORMAL_STYLES);
			}
		style_list.add(style.getProperty(Styles.TextColor));
		style_list.add(style.getProperty(Styles.FontFamily));
		style_list.add(style.getProperty(Styles.FontSize));
		style_list.add(style.getProperty(Styles.FontStyle));
		style_list.add(style.getProperty(Styles.FontWeight));
		style_list.add(style.getProperty(Styles.FillColor));
		}
		return style_list;
	
	}

	private static void epurer(ISheet sheet2) {
		
		//creation d'une liste de tous les parents, utile pour épurer [DEBUT]
		List<String> Parentlist=new ArrayList<String>();
		int l=Map_my_map.size();
		for(int i=0;i<l;i++){
			Parentlist.add(Map_my_map.get(i).get(0));
		}
		
		Parentlist_final.addAll(Parentlist);
		
		//creation d'une liste de tous les parents, utile pour épurer [FIN]
	
		
		//epurer les questions : début
		
			l=Map_my_map.size();
			//List<String> questions=MarkerTest.FindTopicWithMarker(sheet2, Parentlist, Options.markerToQuestion);
			for(int i=0;i<l;i++){
				int id=map.find(Parentlist.get(i));
				boolean inside=(Boolean) Opt.option_by_topic[id][5];
				if(!inside){
					//System.out.println("question get");
					Map_my_map.remove(i);
					Parentlist.remove(i);
					i--;
					l--;
				}
			}
		
		//epurer les questions : fin
		
		//epurer par la selection-réponse par marker [DEBUT]
		boolean answer_by_marker=Options.answer_by_marker;
	/*	if (answer_by_marker==true){
			l=Map_my_map.size();
			AnswerList=MarkerTest.FindTopicWithMarker(sheet2, TopicList, Options.markerToAnswer);
		}*/
		//epurer par la selection-réponse par marker [FIN]
		
		boolean epurer_topic_vide=Options.epurer_topic_vide;
		if(epurer_topic_vide==true){
			l=Map_my_map.size();
		for(int i=0;i<l;i++){
			if((sheet2.getOwnedWorkbook().findTopic(Map_my_map.get(i).get(0)).getTitleText().equals(""))&&
			(!sheet2.getOwnedWorkbook().findTopic(Map_my_map.get(i).get(0)).isRoot())){
				String id_parent=sheet2.getOwnedWorkbook().findTopic(Map_my_map.get(i).get(0)).getParent().getId() ;//on récup l'identité du grand père
				int index_parent=Parentlist.lastIndexOf(id_parent);//ainsi que son emplacement sur la liste de la mairie
				boolean hasparent=!(sheet2.getOwnedWorkbook().findTopic(Map_my_map.get(i).get(0)).isRoot());
					while((index_parent==-1)&&(hasparent==true)){
						if(sheet2.getOwnedWorkbook().findTopic(id_parent).isRoot()){
							hasparent=false;
						}
						if(hasparent){
						id_parent=sheet2.getOwnedWorkbook().findTopic(id_parent).getParent().getId(); 
						index_parent=Parentlist.lastIndexOf(id_parent);}
					}
				
				String id_enfant=sheet2.getOwnedWorkbook().findTopic(Map_my_map.get(i).get(0)).getId() ;//on récupère l'identité du fautif
				int index_enfant=Parentlist.lastIndexOf(id_enfant);//ainsi que son emplacement sur la liste de la mairie
				
				Map_my_map.get(i).remove(0);//on tue le père en vrai
				Parentlist.remove(index_enfant);//ainsi que sur la liste de la mairie
				List<String> OrphanList = Map_my_map.get(i);//on liste les orphelins
				if(hasparent){
				Map_my_map.get(index_parent).addAll(OrphanList);}//on les heberge chez le grand père
				//on supprime l'ancienne localisation des orphelins
				if (!OrphanList.isEmpty()){
					Map_my_map.remove(i);}
				i--;//le remove provoque un décalage qu'il faut tenir compte
				l--;
			}
		}
		}
		boolean rank_options=Options.rank_options;
		if(rank_options==true){
			int seuil_root=Options.seuil_root;
			int seuil_branch=Options.seuil_branch;
			
			List<String> Interdiction=new ArrayList<String>();
			int n=Rank_list.size();
			
			for (int i=0;i<n;i++){
				if ((Rank_list.get(i).getRank_nb()<seuil_root+1)||((Rank_list.get(i).getRankmax_nb()-Rank_list.get(i).getRank_nb())>seuil_branch)){
					Interdiction.add(Rank_list.get(i).getId());
					int index=Parentlist.lastIndexOf(Rank_list.get(i).getId());
					if (index!=-1){
					Map_my_map.remove(index);
					Parentlist.remove(index);
					
					}
				}
			}

		}
		/*boolean search_for_combine=Options.always_concat;
			if (search_for_combine){
				
				
				int l2=Map_my_map.size();
				List<String> listinterdite=new ArrayList<String>();
				for (int i=0;i<l2;i++){
					if ((Map_my_map.get(i).size()==2)&&(!listinterdite.contains(Map_my_map.get(i).get(0)))){
						
						List<String> listconcat=new ArrayList<String>();
						String id=Map_my_map.get(i).get(0);
						listconcat.add(id);
						listinterdite.add(id);
						boolean fin_chaine=false;
						int k=i;
						while(!fin_chaine){
							
							id=Map_my_map.get(k).get(1);
							listconcat.add(id);
							listinterdite.add(id);
							
							int index=Parentlist.lastIndexOf(id);
							
							if (index==-1){
								fin_chaine=true;}
							else{
							
							if (!(Map_my_map.get(index).size()==2)){
								fin_chaine=true;
							}
							else k=index;
							
						}
							}
						Concat.add(listconcat);
					}
				}
			}*/
		
	}

	private static String[][] create_facts(
			ISheet sheet2 ,List<String> span) {
		int fact_number=Map_my_map.size();
		IWorkbook workbook=sheet2.getOwnedWorkbook();
		
		List<List<String>>facts=new ArrayList<List<String>>();
		
		for (int i=0;i<Map_my_map.size();i++){
			List<String>fact=new ArrayList<String>();
			ITopic topic=workbook.findTopic(Map_my_map.get(i).get(0));
			if(!(Boolean) Opt.option_by_topic[map.find(topic.getId())][9]){//il ne faut pas que ce soit un glisseur
			
				fact.add(create_question_string(topic,sheet2,span));
			//fact_tab[i][0]=StringEscapeUtils.escapeHtml4(title);
				fact.add(create_answer_string(sheet2,span,i));
				facts.add(fact);
			}
			else {
			Map_my_map.remove(i);//Explications : on enlève tous les glisseurs de Map_my_map car ils ne constituent pas des questions
			//Normalement, un glisseur est appelé après qu'il soit utilisé, on peut donc supprimer la liste de ses enfants
			i--;
			fact_number--;
			}
		}
		
		String[][] fact_tab=new String[fact_number][2];
		for (int i=0;i<fact_number;i++){
			fact_tab[i][0]=facts.get(i).get(0);
			fact_tab[i][1]=facts.get(i).get(1);
		}
		return fact_tab;
		
		
		
	}

	private static String create_answer_string( ISheet sheet2,
			List<String> span, int i) {
		IWorkbook workbook=sheet2.getOwnedWorkbook();
		String answer="<ul class=\"tree\">";
		answer+="<div style=\"text-align: left;\">";
		
		
		//précalcul permettant de savoir si l'item n'aura pas de frère
		List<Integer>integers=new ArrayList<Integer>();
		for( int k=1;k<Map_my_map.get(i).size();k++){	
			int ind=map.find(Map_my_map.get(i).get(k));
			boolean inside=(Boolean) Opt.option_by_topic[ind][6];
			if(inside){ integers.add(k);}
			}

		//
		
		
		
		
		for( int k=1;k<Map_my_map.get(i).size();k++){
					
			int ind=map.find(Map_my_map.get(i).get(k));
			boolean inside=(Boolean) Opt.option_by_topic[ind][6];
			if(inside){
							
				if(k==integers.get(integers.size()-1)){answer+="<li class=\"last\">    ";}
				else {answer+="<li>    ";}
				//ajout des images début
				if ((HTImages.containsKey((Map_my_map.get(i).get(k))))&&((Boolean) Opt.option_by_topic[ind][10])){
					answer+="<img src=\""+HTImages.get(Map_my_map.get(i).get(k)).substring(12)+"\"><br>";
				}
				//ajout des images fin
				
				//ajout des markers début
				if (((Boolean) Opt.option_by_topic[ind][11])){
					File dest = new File(ankisave+"/"+sheet2.getTitleText()+".anki");
					 String path=dest.getPath();
					 String dirpath=path.substring(0,path.length()-5)+".media";
					
					Set<IMarkerRef> markerSet = workbook.findTopic(Map_my_map.get(i).get(k)).getMarkerRefs();
					if (!markerSet.isEmpty()){
						List<IMarkerRef> markerList=new ArrayList<IMarkerRef>();
						markerList.addAll(markerSet);
						
						for (int m=0;m<markerList.size();m++){
							IMarkerRef ref=markerList.get(m);
							String id=ref.getMarkerId();
							String FileName =id;//+".png";
							answer+="<img src=\""+FileName+"\">";
							 
						}
					}
				}
				
				//ajout des markers fin
				answer+="<span ";
				String title2=workbook.findTopic(Map_my_map.get(i).get(k)).getTitleText();
				String style="";
				if (((Boolean) Opt.option_by_topic[ind][1])){
				style=span.get(map.find(Map_my_map.get(i).get(k)));
				}
				
				
				
				answer+=style+">";
				title2=StringEscapeUtils.escapeHtml4(title2);
				
				answer+="&nbsp;&nbsp;&nbsp;"+title2+"</span>";
				
				String hp="";
				if((Boolean) Opt.option_by_topic[ind][0]){
				hp=workbook.findTopic(Map_my_map.get(i).get(k)).getHyperlink();
				if ((hp!=null)&&(!hp.equals(""))){
				answer+="<br><a href=\""+hp+" \" target=\"_blank\" >link</a>";}
				}
				
				answer+="<br>";
				if((Boolean) Opt.option_by_topic[ind][3]){
					List<String> labels=new ArrayList<String> (workbook.findTopic(Map_my_map.get(i).get(k)).getLabels());
					String printlab="";
					for (int p=0;p<labels.size();p++){
						printlab+="<span style=\"background-color: rgb(255, 255, 102);\">"+labels.get(p)+"</span>";
					}
					answer+=printlab;
				}
				if ((RunWorkbook.Notes.containsKey(Map_my_map.get(i).get(k)))&&((Boolean) Opt.option_by_topic[ind][2])){
					answer+="<img src=\"notes.png\"> : "+RunWorkbook.Notes.get(Map_my_map.get(i).get(k));
				}
				
				

				
			
			}
			//ind 	if((Boolean) Opt.option_by_topic[ind][9]){
			
			
			if((Boolean) Opt.option_by_topic[ind][9]){// si la réponse est un glisseur
				
				// trouver l'index du fact => parcourir map_my_map jusqu'à trouver l'ID
				
				String id=Map_my_map.get(i).get(k); //on prend l'identifiant du glisseur
				boolean bool=true;
				int p=0;	
				while ((bool)&&(p<Map_my_map.size())){ 
					if (Map_my_map.get(p).get(0).equals(id)){bool=false;//on le cherche parmi les questions
					
						
					} 
					p++;
				}
				
				if (!bool){answer+=create_answer_string(sheet2, span, p-1);//on génère la sous liste à puce du glisseur

				answer+="</ul>";
				
				
			}
				
			}
			
			//StringEscapeUtils.escapeHtml
		}
		
		answer+="</div>";
	
		
		
		return answer;
	}

	private static String create_question_string(ITopic iTopic, ISheet sheet2, List<String> span ) {
		String id=iTopic.getId();
		
		int index=map.find(id);
		
		
		
		String title=iTopic.getTitleText();
		String main=StringEscapeUtils.escapeHtml4(title);
		List<String> parents=list_my_parents(iTopic);
		IWorkbook workbook=sheet2.getOwnedWorkbook();
		
		String answer="";
		
		if ((HTImages.containsKey(id))&&((Boolean) Opt.option_by_topic[index][10])){
			answer+="<img src=\""+HTImages.get(id).substring(12)+"\"><br>";
		}
		//ajout des markers début
		if (((Boolean) Opt.option_by_topic[index][11])){
			File dest = new File(ankisave+"/"+sheet2.getTitleText()+".anki");
			 String path=dest.getPath();
			 String dirpath=path.substring(0,path.length()-5)+".media";
			
			Set<IMarkerRef> markerSet = iTopic.getMarkerRefs();
			if (!markerSet.isEmpty()){
				List<IMarkerRef> markerList=new ArrayList<IMarkerRef>();
				markerList.addAll(markerSet);
				
				for (int m=0;m<markerList.size();m++){
					IMarkerRef ref=markerList.get(m);
					String idm=ref.getMarkerId();
					String FileName=idm;//+".png";
					answer+="<img src=\""+FileName+"\">";
					 
				}
			}
		}
		
	
		answer+="<span title=\"";
		for (int i=0;i<parents.size();i++){
			String parent=StringEscapeUtils.escapeHtml4(workbook.findTopic(parents.get(i)).getTitleText());

			if (parent.length()>25){
				//parent=parent.substring(0,25)+"[...]";
			}
			answer+=parent+"<<";
			
		}
		String style="";
		if (((Boolean) Opt.option_by_topic[index][1])){
			style=span.get(index);
			}
		answer+="\" "+style+">";//6 18 23 25 26 43 44 VS 3 19 21
		answer+=main+"</span>";//+"<img src=\"attachments/6ttmdoilj7f517snpqmbpc8nvd.png\">";
		
		//********fin du span
		
		
		String hp="";
		if((Boolean) Opt.option_by_topic[index][0]){
		hp=iTopic.getHyperlink();
		if ((hp!=null)&&(!hp.equals(""))){
		answer+="<br><a href=\""+hp+" \" target=\"_blank\" >link</a>";}}
		
		if((Boolean) Opt.option_by_topic[index][3]){
			List<String> labels=new ArrayList<String> (workbook.findTopic(iTopic.getId()).getLabels());
			String printlab="";
			for (int p=0;p<labels.size();p++){
				if(p==0){answer+="<br>";}
				printlab+="<span style=\"background-color: rgb(255, 255, 102);\">"+labels.get(p)+"</span>";
			}
			answer+=printlab;
		}
		
		
		if ((RunWorkbook.Notes.containsKey(id))&&((Boolean) Opt.option_by_topic[index][2])){
			answer+="<br><img src=\"notes.png\"> : "+RunWorkbook.Notes.get(id);
		}
		
		return answer;
	}

	private static void recurcive_list_my_childrens(ITopic Topic, int rank) {
		rank++;
		
		
		
		// on stocke les images
		IImage im=Topic.getImage();
		
		if (im.getSource()!=null){
			List<String> image=new ArrayList<String>();
			String id=Topic.getId();
			String path=(im.getSource()).substring(4);
			image.add(id);
			image.add(path);
			Images.add(image);
			HTImages.put(id, path);
		}
		
		//on stocke les markers
		Set<IMarkerRef> markerSet = Topic.getMarkerRefs();
		if (!markerSet.isEmpty()){
			List<IMarkerRef> markerList=new ArrayList<IMarkerRef>();
			markerList.addAll(markerSet);
			for (int i=0;i<markerList.size();i++){
			IMarkerRef ref=markerList.get(i);
			String id=ref.getMarkerId();
				if (!Markers.contains(id)){
				//System.out.println(id);
				Markers.add(id);}
			}
		}
		
		

		
		
		//Map_temp;
		
		if(Options.rank_options==true){
		Rank rk=new Rank(Topic.getId(),rank,rank);
		Rank_list.add(rk);
		}
		
		List<String> Map_my_sons = new ArrayList<String>();
		Map_my_sons.add(Topic.getId()); //the first item is the ID of the father, the next ones will be the id of the childrens
		if((Topic.hasChildren(ITopic.ATTACHED))||(Topic.hasChildren(ITopic.DETACHED))){
			List<ITopic> allChildren = Topic.getAllChildren();
			for (int i=0; i<allChildren.size();i++) {
				Map_my_sons.add(allChildren.get(i).getId());
				TopicList.add(allChildren.get(i).getId());//les summaries vont ils poser pbm ?
				
			}
			Map_my_map.add(Map_my_sons);
			
			
			if ((allChildren.size()==1)&&(Options.glisseur_for_unique)){
				
				
				unique.add(Topic.getId());
			}
			
			
			for (int i=0; i<allChildren.size();i++) {
			
				recurcive_list_my_childrens(allChildren.get(i),rank);
			}		
		}
		else if(Options.rank_options==true){update_rankmax(Topic,rank);}
		
	}

	private static void update_rankmax(ITopic topic, int rank2) {
		while (!topic.isRoot()){
			topic=topic.getParent();
			String id=topic.getId();
			
			boolean bool=false;
			int i=-1;
			while (!bool){
				i++;
				bool=Rank_list.get(i).getId().equals(id);
			}
			if(Rank_list.get(i).getRankmax_nb()<rank2){
				Rank rk=Rank_list.get(i);
				rk.setRankmax_nb(rank2);
				Rank_list.remove(i);
				Rank_list.add(rk);
			}
			
		}
		
	}
	private static List<String> list_my_parents(ITopic topic) {
		List<String> parent_list=new ArrayList<String>();
		while (!topic.isRoot()){
			topic=topic.getParent();
			parent_list.add(topic.getId());}
		return parent_list;

	}

}

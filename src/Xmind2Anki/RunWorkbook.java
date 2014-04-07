package Xmind2Anki;

import java.io.File;
import java.io.IOException;
import java.util.*;

import boutons.Xmind2AnkiFenetre;


import progress_monitor.ProgressMonitorDemo;


import javax.swing.JLabel;
import org.xmind.core.Core;
import org.xmind.core.ISheet;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;
import org.xmind.core.io.IStorage;




public class RunWorkbook {

	/**
	 * @param args
	 * @throws CoreException 
	 * @throws IOException 
	 */
	public static HashMap<String, String> Notes = new HashMap<String, String>();
	static List<List<String>> Map_my_map = new ArrayList<List<String>>();
	
	
	public static void runW(String dir, String file) throws Exception{
		
		ProgressMonitorDemo.advancement=0;
		
		
		
		new Options();
		Methods.delete(Options.Xmind_temp_storage);
		//String Workbook = "C:/Users/bruno/sheets.xmind";
		String Workbook=dir+file;
		
		File w=new File(Workbook);
		File t=new File(Options.Xmind_temp_storage);
		MyZip.unzip(w, t);
		Notes=Test_JDOM.find_notes(new File(Options.Xmind_temp_storage+"/"+"content.xml"));
	
		
		IWorkbook workbook = org.xmind.core.Core.getWorkbookBuilder().loadFromPath(Workbook); 

		String da=Options.Xmind_temp_storage;
		workbook.setTempLocation(da);
		//workbook.saveTemp();
		IStorage stor=workbook.getTempStorage();
		
		IWorkbookBuilder builder = Core.getWorkbookBuilder();
		workbook.saveTemp();
		//MergeXml.merge();
		
		List<ISheet> SheetList=workbook.getSheets();
		ProgressMonitorDemo.nb_sheet=SheetList.size();
		
		for (int i=0;i<SheetList.size();i++){
			ProgressMonitorDemo.current_sheet=i;
			
			//ProgressMonitorDemo.advancement
			ProgressMonitorDemo.advancement=(int) (Math.floor( (i)/ (double)SheetList.size()*100+1));
			ProgressMonitorDemo.stateName=SheetList.get(i).getTitleText();
			
			RunSheet runsheet=new RunSheet(SheetList.get(i),file);
			workbook=runsheet.processXmind2Anki();
		}
		ProgressMonitorDemo.stateName="saving";
		
		

		workbook.saveTemp();
		
		IWorkbook workbook2=builder.loadFromStorage(stor);
		workbook2.save(Options.tempfile);
		
		//report du anki
		JLabel lab=Xmind2AnkiFenetre.getLabel();
        String fn=file;
        ProgressMonitorDemo.advancement=100;
       
        lab.setText(fn+"   (done)");
      //mise à jour d'un booléen indiquant la présence de temp 
        Xmind2AnkiFenetre.incrust=true;
        
        try {
        	File src2=new File(Options.tempfile);
        	String destination=Xmind2AnkiFenetre.outputdir+"/"+Xmind2AnkiFenetre.outputfilename;
        	File dest2=new File(destination);
			Methods.copyFile(src2,dest2); } catch (IOException e1) {e1.printStackTrace();}
      } 
       //mise à jour des champs - fin

		
		//fin report du anki
		
		
		
		
	}

   


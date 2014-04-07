package Xmind2Anki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class Ressource {

	public static void saveRessource(String id,Hashtable<String,String> resources, String dirpath) throws IOException{
		

		String file=resources.get(id);
		
		if(file!=null){
		
		String Marker ="markers/"+file;
		File Markfile=new File(Marker);
		System.out.println(file);
		
		FileInputStream in= new FileInputStream (Markfile);
		//int da=2;
		String FileName =dirpath+"/"+id;//+".png";
		FileOutputStream out = new FileOutputStream(FileName);
		
		java.nio.channels.FileChannel channelSrc   = in.getChannel();
	     java.nio.channels.FileChannel channelDest = out.getChannel();

	     channelSrc.transferTo(0, channelSrc.size() , channelDest);

	     in.close();
	     out.close();
		}
		
	}

	}

package Xmind2Anki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Methods {

	public static void copyFile(File src, File dest) throws IOException
	{   
		if(src.canRead()){
		FileInputStream fis = new FileInputStream(src);
	     FileOutputStream fos = new FileOutputStream(dest);

	     java.nio.channels.FileChannel channelSrc   = fis.getChannel();
	     java.nio.channels.FileChannel channelDest = fos.getChannel();

	     channelSrc.transferTo(0, channelSrc.size() , channelDest);

	     fis.close();
	     fos.close();
		}
	
}
	
	public static void delete(String string) {
		System.out.println(deleteDir(new File(string)));
		new File(string).mkdir();
	}
	
	
	public static  boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++){
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) 
					return false;	
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
}
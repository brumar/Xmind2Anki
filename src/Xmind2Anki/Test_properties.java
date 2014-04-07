package Xmind2Anki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Test_properties {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// create and load default properties
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream("C:/Users/bruno/defaultProperties");
		defaultProps.load(in);
		in.close();

		// create application properties with default
		Properties applicationProps = new Properties(defaultProps);

		// now load properties 
		// from last invocation
		/*in = new FileInputStream("C://Users//bruno//appProperties");
		applicationProps.load(in);
		in.close();*/
		FileOutputStream out = new FileOutputStream("C:/Users/bruno/appProperties");
		applicationProps.setProperty("key", "value");
		applicationProps.store(out, "---No Comment---");
		out.close();

	}

}

package controller.handler.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailConfig {
	
	public static Properties emailConfig() {
			Properties properties = new Properties();
			File f = new File("C:\\Projetos\\miauDote.cao\\admin\\emailConfig.ini");
			if(!f.exists()){
				 f = new File("C:\\Users\\Joao Gabriel\\Desktop\\miauDote.cao\\admin\\emailConfig.ini");
			}
	        try (FileInputStream fileInputStream = new FileInputStream(f)) {
	            properties.load(fileInputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return properties;
	    }
	}



package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {
	 private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getName());

	    private String getConfigValueByKey(String key) throws IOException {
	    	File file = new File("C:\\Projetos\\miauDote.cao\\admin\\configMySQL.ini");
	    	//File file = new File("C:\\Users\\lpereira\\Desktop\\miauDote.cao\\admin\\configMySQL.ini");
	    	if(!file.exists()) {
	    		file = new File("C:\\Users\\Joao Gabriel\\Desktop\\miauDote.cao\\admin\\configMySQL.ini");
	    	}
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                line = line.trim();
	                if (!line.isEmpty() && !line.startsWith("#")) {
	                    String[] parts = line.split("=", 2);
	                    if (parts.length == 2 && parts[0].equals(key)) {
	                        return parts[1].trim();
	                    }
	                }
	            }
	        }
	        return null;
	    }

	    public Connection connectDB() throws SQLException, IOException, ClassNotFoundException {
	        String url = getConfigValueByKey("url");
	        String user = getConfigValueByKey("user");
	        String password = getConfigValueByKey("password");
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        Connection connection = null;
	        try {
	            connection = DriverManager.getConnection(url, user, password);
	        } catch (SQLException e) {
	            LOGGER.log(Level.SEVERE, "Failed to connect to the database", e);
	            throw e;
	        }
	        return connection;
	    }
}

package ca.myseneca.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtilities {

    public static Connection connect() throws SQLException, ClassNotFoundException{
        // Load properties to access connection info
        Properties props = loadProps();
        
        // Load driver create and return connection
        Class.forName(props.getProperty("DRIVER_NAME"));
        return DriverManager.getConnection(props.getProperty("URL"), props.getProperty("USERNAME"), props.getProperty("PASSWORD"));

    }

    public static Properties loadProps(){

        // Create properties object
        Properties prop = new Properties();
  
        try{
            
            // Load the properties object with an input stream made with the properties file
            prop.load(DBUtilities.class.getResourceAsStream("/database.properties"));

            // Return the object
            return prop;

        }catch(IOException IOe){
            IOe.printStackTrace();
            return null;
        }

   }
}

package ca.carsXML;
//SQL Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//JDOM Imports
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
//IO Imports
import java.io.FileOutputStream;
//SQLInfo Import
import ca.sqlinfo.MySqlInfo;

public class carsXML {
    public static void main(String[] args){

        Connection con = null;
        ResultSet rset = null;
        Statement stmt = null;

        try{

            // Load Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            // Connect to DB
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            // Query the SPORTSCARS table for the entire list of cars
            stmt = con.createStatement();
            rset = stmt.executeQuery("SELECT * FROM SPORTSCARS;");

			// Create the root element
			Element carsElement = new Element("cars");
			Document doc = new Document(carsElement);

            while(rset.next()){
                // Get resultset info for current result
                int yr = rset.getInt(1);
                String mk = rset.getString(2);
                String mdl = rset.getString(3);
                Double prc = rset.getDouble(4);
                // Create an element for the individual car
                Element car = new Element("car");
                // Create and add an element for the year
                Element year = new Element("year");
                year.setText(String.valueOf(yr));
                car.addContent(year);
                // Create and add an element for the make
                Element make = new Element("make");
                make.setText(mk);
                car.addContent(make);
                // Create and add an element for the model
                Element model = new Element("model");
                model.setText(mdl);
                car.addContent(model);
                // Create and add an element for the price
                Element price = new Element("price");
                price.setText(String.valueOf(prc));
                car.addContent(price);
                // Add the completed car element to the root element "cars"
                doc.getRootElement().addContent(car);

            }

            // Output to xml file, formatted
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileOutputStream("cars.xml"));

        } catch(SQLException SQLe){
            SQLe.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            try {
				if (rset != null)
					rset.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }
}

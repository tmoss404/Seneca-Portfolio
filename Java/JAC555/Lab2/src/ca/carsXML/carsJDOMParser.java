package ca.carsXML;
// IO Imports
import java.io.File;
import java.io.IOException;
// Util Imports
import java.util.List;
// JDOM Imports
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class carsJDOMParser {

    public static void main(String[] args){
        try {
	    	  
            // Create a DocumentBuilder
            SAXBuilder saxBuilder = new SAXBuilder();

            // Create a Document from a file 
            File inputFile = new File("cars.xml");
            Document document = saxBuilder.build(inputFile);            

            // Extract the root element
            Element carsElement = document.getRootElement();
            System.out.println("Root element: " + carsElement.getName());
            
            // Examine sub-elements
            List<Element> carsList = carsElement.getChildren();
            System.out.println("================================");

            for (int i = 0; i < carsList.size(); i++) {    
                
               // Examine a single sub-element
               Element car = carsList.get(i);
               // Examine the sub-text nodes and print out
               System.out.println("Year : " + car.getChild("year").getText());
               System.out.println("Make : "+ car.getChild("make").getText());
               System.out.println("Model : "+ car.getChild("model").getText());
               System.out.println("Price : "+ car.getChild("price").getText());	           
               System.out.println("------------"); 		
            }
         } catch(JDOMException JDe){
            JDe.printStackTrace();
         } catch(IOException ioe){
            ioe.printStackTrace();
         } catch(Exception e){
            e.printStackTrace();
         }
    }
}
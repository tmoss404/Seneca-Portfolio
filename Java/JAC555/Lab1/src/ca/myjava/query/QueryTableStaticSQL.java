package ca.myjava.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;


public class QueryTableStaticSQL {
        
    public static void main(String[] args){
        Connection con = null;
        Statement stmt = null;
        ResultSet rset = null;

        try(Scanner in = new Scanner(System.in)) {

            //Start Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            System.out.println("Driver Loaded...");
            //Make connection to the server
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            System.out.println("Connected to DB...\n");

            //Accept user input for age range
			System.out.print("Please enter starting age: ");
			float a1 = in.nextFloat();
			System.out.print("Please enter end age: ");
			float a2 = in.nextFloat();

            //Build the query based on user input
            String query = "SELECT name, lifeExpectancy FROM Country "
            + "WHERE lifeExpectancy between "+ a1 +" and " + a2 + ";";

            //Create statement and execute the query
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            //Did this before I built the dynamic printing loop so I left it as is
            //Just print two columns
            System.out.println("\nCountry - LifeExpectancy");
			System.out.println("------------------------");
			while (rset.next()) {
				for (int i = 1; i <= 2; i++) {
					System.out.print(rset.getString(i) + (i == 2 ? "\n" : " - ") );
				}
			}
            System.out.println(); //Formatting print

        } catch(SQLException SQLe){
            SQLe.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
                SQLe.printStackTrace();
			}
		}
    }
}

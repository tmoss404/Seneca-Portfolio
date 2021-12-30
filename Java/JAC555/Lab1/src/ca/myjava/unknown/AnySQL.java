package ca.myjava.unknown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;

public class AnySQL {

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

            //Accept user input for SQL statement and store it
			System.out.print("Please enter SQL statement to execute: ");
			String query = in.nextLine();

            //Create statement and execute it to check whether it returned a ResultSet object or an update count
            stmt = con.createStatement();
            boolean check = stmt.execute(query);
            if (check) {

                //Store the Resultset
                ResultSet rs = stmt.getResultSet();

                //Get the metadata to see how many columns were selected and store the result
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                System.out.println(); //Formatting print

                //Use the cols value from the metadata to dynamically print the results
                for(int i = 1; i <= cols; i++){
                    System.out.print(rsmd.getColumnName(i) + (i == cols ? "\n" : " - ") );
                }
                System.out.println("---------------------");
                while(rs.next()){
                    for (int i = 1; i <= cols; i++) {
                        System.out.print(rs.getString(i) + (i == cols ? "\n" : " - ") );
                    }
                }
                System.out.println(); //Formatting print

            } else {

                System.out.println();//Formatting print

                //Print out the count of updated rows
                int rowCount = stmt.getUpdateCount();
                System.out.println("Number of rows affected: " + rowCount);
                
                System.out.println();//Formatting print

            } 
            
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
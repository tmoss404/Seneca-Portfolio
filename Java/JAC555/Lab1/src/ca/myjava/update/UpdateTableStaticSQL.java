package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;

public class UpdateTableStaticSQL {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try(Scanner in = new Scanner(System.in)) {

            //Start Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            System.out.println("Driver Loaded...");
            //Make connection to the server
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            System.out.println("Connected to DB...\n");

            //Create the statement 
            stmt = con.createStatement();
            //Select all to display all
            rs = stmt.executeQuery("SELECT * FROM Country");

            //Get MetaData to dynamically print columns (Not necesarry here but it's better to have it be flexible)
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            
            //Dynamically print columns to show which rows are available
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

             //Accept user input for id to select and new life expectancy value
			System.out.print("Please enter the id of row to update: ");
			int id = in.nextInt();
			System.out.print("Please enter updated life expectancy: ");
			float lifeExpectancy = in.nextFloat();
            
            //Build static statement with user input
            String query = "UPDATE Country SET lifeExpectancy = " + lifeExpectancy + " WHERE id = " + id + ";";
            
            //Execute statement
            stmt.executeUpdate(query);

            System.out.println(); //Formatting print
            System.out.println("Country " + id + " updated successfully.");
            System.out.println(); //Formatting print

        } catch(SQLException SQLe) {
            SQLe.printStackTrace();
        } catch(Exception e ) {
            e.printStackTrace();
        } finally {
			try {
                if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
		}
    }
}

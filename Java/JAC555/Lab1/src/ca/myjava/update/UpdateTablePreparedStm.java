package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;

public class UpdateTablePreparedStm {
    
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try(Scanner in = new Scanner(System.in)) {

            //Start Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            System.out.println("Driver Loaded...");
            //Make connection to the server
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            System.out.println("Connected to DB...\n");

            //Build the Insert statement formatted for a PreparedStatement and prepare it
            String query = "INSERT INTO Country VALUES(?, ?, ?);";
            pstmt = con.prepareStatement(query);

            //Accept user input for id, name, and life expectancy
            System.out.print("Please enter id: ");
		    int id = in.nextInt();
			System.out.print("Please country name: ");
			String name = in.next();
            System.out.print("Please country life expectancy: ");
			float lifeExpectancy = in.nextFloat();

            //Set the values in the prepared statement with the user input
            pstmt.setInt(1, id);
			pstmt.setString(2, name);
            pstmt.setFloat(3, lifeExpectancy);
			
            //Execute statement
            int rows = pstmt.executeUpdate();

            System.out.println();//Formatting print
            System.out.println("Number of rows affected: " + rows);
            System.out.println();//Formatting print

        } catch(SQLException SQLe) {
            SQLe.printStackTrace();
        } catch(Exception e ) {
            e.printStackTrace();
        } finally {
			try {
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

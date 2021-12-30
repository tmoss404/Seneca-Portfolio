package ca.myjava.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;

public class QueryTablePreparedStm2 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try(Scanner in = new Scanner(System.in)) {

            //Start Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            System.out.println("Driver Loaded...");
            //Make connection to the server
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            System.out.println("Connected to DB...\n");

			//Build the query formatted for a PreparedStatement and prepare the statement
            String query = "select name, lifeExpectancy from Country "
					+ "where lifeExpectancy between ? and ?;";
			pstmt = con.prepareStatement(query);

			//Accept user input for the age range
			System.out.print("Please enter starting age: ");
			float a1 = in.nextFloat();
			System.out.print("Please enter end age: ");
			float a2 = in.nextFloat();

			//Set the age range in the prepared statement with the user input
			pstmt.setFloat(1, a1);
			pstmt.setFloat(2, a2);

			//Execute query
			rset = pstmt.executeQuery();

			//Did this before I built the dynamic printing loop so I left it as is
            //Just print two columns
			System.out.println("\nCountry - LifeExpectancy");
			System.out.println("------------------------");
			while (rset.next()) {
				for (int i = 1; i <= 2; i++) {
					System.out.print(rset.getString(i) + (i == 2 ? "\n" : " - ") );
				}
			}
        } catch(SQLException SQLe) {
            SQLe.printStackTrace();
        } catch(Exception e ) {
            e.printStackTrace();
        } finally {
			try {
				if (rset != null)
					rset.close();
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

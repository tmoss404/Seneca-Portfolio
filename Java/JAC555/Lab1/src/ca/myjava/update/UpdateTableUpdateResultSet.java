package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ca.sqlinfo.MySqlInfo;

public class UpdateTableUpdateResultSet {
    
    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try(Scanner in = new Scanner(System.in)) {

            //Start Driver
            Class.forName(MySqlInfo.DRIVER_NAME);
            System.out.println("Driver Loaded...");
            //Make connection to the server
            con = DriverManager.getConnection(MySqlInfo.URL, MySqlInfo.USERNAME, MySqlInfo.PASSWORD);
            System.out.println("Connected to DB...\n");

            //Create the statement with options for updatable ResultSet 
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //Select all to display all and allow deleting of any row
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

            //Store the amount of rows available
            rs.last();
            int rows = rs.getRow();
            int selected = 0;
            
            while(true){//Loop until valid row is selected
                //Accept unput for the desired row to delete
                System.out.print("Enter the number for the row you wish to Delete: ");
                selected = in.nextInt();
                //Check against the number of rows available
                if(selected > rows || selected < 1){
                    //Alert the user that they tried to delete a nonexistent row
                    System.out.println("Selected row does not exist, try again.");
                    System.out.println(); //Formatting print
                } else {
                    //Seek to the selected row and delete
                    rs.absolute(selected);
                    rs.deleteRow();
                    System.out.println("Row " + selected + " deleted successfully.");
                    System.out.println(); //Formatting print
                    break;
                }
            }
            
        } catch(SQLException SQLe) {
            SQLe.printStackTrace();
        } catch(Exception e ) {
            e.printStackTrace();
        } finally {
			try {
                if (rs != null)
                    rs.close();
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

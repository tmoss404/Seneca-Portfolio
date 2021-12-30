package ca.myseneca.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;


public class DBAccessHelper {

    public static int getEmployeeID(String user, String password){
        // Initialize the id value as 0
        // if nothing comes back this will become 0 anyway
        int id = 0;

        Connection con =  null;
        CallableStatement stmt = null;

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Create callable statement for the F_SECURITY function in the P_SECURITY package on
            stmt = con.prepareCall("{? = call P_SECURITY.F_SECURITY(?,?)}");

            // Register the employee id out parameter as a number
            stmt.registerOutParameter(1, Types.NUMERIC);

            // Set the user credentials as parameters
            stmt.setString(2, user);
            stmt.setString(3, password);

            // Execute callable statement and store employee id to return
            stmt.execute();
            id = stmt.getInt(1);

            // Return the retrieved employee id
            return id;

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return 0;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    public static void addEmployee(Employee emp){
        Connection con =  null;
        Statement stmt = null;
        ResultSet rset = null;

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Prepare insert statement
            stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            rset = stmt.executeQuery("SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, " +
                                    "PHONE, HIRE_DATE, MANAGER_ID, JOB_TITLE FROM EMPLOYEES");

            // Navigate to the insert row in the resultset
            rset.moveToInsertRow();

            // Set all parameters with information from the provided employee object
            rset.updateInt(1, emp.getEMPLOYEE_ID());
            rset.updateString(2, emp.getFIRST_NAME());
            rset.updateString(3, emp.getLAST_NAME());
            rset.updateString(4, emp.getEMAIL());
            rset.updateString(5, emp.getPHONE());
            rset.updateDate(6, emp.getHIRE_DATE());
            rset.updateInt(7, emp.getMANAGER_ID());
            rset.updateString(8, emp.getJOB_TITLE());

            // Insert the row, creating a new employee entry
            rset.insertRow();

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
                if (rset != null)
					rset.close();
                
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    public static ArrayList<Employee> getAllEmployees(){
        Connection con =  null;
        Statement stmt = null;
        ResultSet rset = null;

        // Create an empty ArrayList ready to be populated with employee objects
        ArrayList<Employee> employees = new ArrayList<>();

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Create statement
            stmt = con.createStatement();

            // Query for all employees
            rset = stmt.executeQuery("SELECT * FROM EMPLOYEES");

            // Loop through each entry in the resultset
            while(rset.next()){
                // Leverage a helper function I made to quickly create an employee object out of a resultset entry
                // add the created employee to the arraylist
                employees.add(rsetEmployeeExtract(rset));
            }
            return employees;

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return null;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
                if (rset != null)
					rset.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
        
    }

    public static ArrayList<Employee> getEmployeesByManagerID(int manId){

        Connection con =  null;
        Statement stmt = null;
        ResultSet rset = null;

        // Create an empty ArrayList ready to be populated with employee objects
        ArrayList<Employee> employees = new ArrayList<>();

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Create statement
            stmt = con.createStatement();

            // Query for all employees that match the provided manager ID
            rset = stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE MANAGER_ID = " + manId);

            // Loop through each entry in the resultset
            while(rset.next()){
                // Leverage a helper function I made to quickly create an employee object out of a resultset entry
                // add the created employee to the arraylist
                employees.add(rsetEmployeeExtract(rset));
            }
            return employees;

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return null;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
                if (rset != null)
					rset.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    public static Employee getEmployeeByID(int empid){

        Connection con =  null;
        OracleCallableStatement ostmt = null;
        ResultSet rset = null;

        // Create default employee object to be set with data later
        Employee emp = new Employee();

        try{
            // Establish connection
            con = DBUtilities.connect();

            //Prepare call with prepared statement P_EMP_INFO
            ostmt = (OracleCallableStatement)con.prepareCall("{call P_SECURITY.P_EMP_INFO(?, ?)}");

            // Set the first parameter as employee id from the method arg
            ostmt.setInt(1, empid);

            // Register the output parameter as an oracle cursor type
            ostmt.registerOutParameter(2, OracleTypes.CURSOR);
            ostmt.execute();

            // Fill the result set with the data from the cursor
            rset = (ResultSet)ostmt.getObject(2);

            // We only want to retrieve and return one employee so I opt not to use a while(rset.next())
            // but instead just call rset.next() to navigate to the first and only entry in the rset
            rset.next();
                emp.setEMPLOYEE_ID( rset.getInt("EMPLOYEE_ID") );
                emp.setFIRST_NAME( rset.getString("FIRST_NAME") );
                emp.setLAST_NAME( rset.getString("LAST_NAME") );
                emp.setEMAIL( rset.getString("EMAIL") );
                emp.setPHONE( rset.getString("PHONE") );
                emp.setHIRE_DATE( rset.getDate("HIRE_DATE") );
                emp.setMANAGER_ID( rset.getInt("MANAGER_ID") );
                emp.setJOB_TITLE( rset.getString("JOB_TITLE") );

            return emp;
        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return null;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
				if (ostmt != null)
					ostmt.close();
				if (con != null)
					con.close();
                if (rset != null)
					rset.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
        
    }

    public static int updateEmployee(Employee emp){
        Connection con =  null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Prepare statement
            pstmt = con.prepareStatement("UPDATE EMPLOYEES SET " +
                                        "FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, " +
                                        "PHONE = ?, HIRE_DATE = ?, MANAGER_ID = ?, JOB_TITLE = ? " + 
                                        "WHERE EMPLOYEE_ID = ?");

            // Set the parameters to be updated on selected employee
            pstmt.setString(1, emp.getFIRST_NAME());
            pstmt.setString(2, emp.getLAST_NAME());
            pstmt.setString(3, emp.getEMAIL());
            pstmt.setString(4, emp.getPHONE());
            pstmt.setDate(5, emp.getHIRE_DATE());
            pstmt.setInt(6, emp.getMANAGER_ID());
            pstmt.setString(7, emp.getJOB_TITLE());

            // Set the employee id parameter to select the employee to update
            pstmt.setInt(8, emp.getEMPLOYEE_ID());
            return pstmt.executeUpdate();

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return 0;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
                if (rset != null)
					rset.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    public static int deleteEmployeeByID(int empid){
        Connection con =  null;
        PreparedStatement pstmt = null;

        try{
            // Create Connection
            con = DBUtilities.connect();

            // Prepare statement
            pstmt = con.prepareStatement("DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?");

            // Set the employee id parameter
            pstmt.setInt(1, empid);
            return pstmt.executeUpdate();

        }catch(SQLException SQLe){
            SQLExceptionErrPrint(SQLe);
            return 0;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    public static boolean batchUpdate(String[] SQLs) {
        Connection con =  null;
        Statement stmt = null;

        try{
            // Create Connection
            con = DBUtilities.connect();
            con.setAutoCommit(false);

            // Create Statement
            stmt = con.createStatement();

            // Iterate over the SQL statements and each one to the batch
            for(String sql : SQLs){
                stmt.addBatch(sql);
            }

            // Execute the batch
            stmt.executeBatch();
            // If all statements succeed commit updates and return true
            con.commit();
            return true;

        }catch(SQLException SQLe){ 
            // Use my SQL Exception printing method
            SQLExceptionErrPrint(SQLe);
                try {
                    // Rollback the changes since something has gone wrong
                    con.rollback();
                } catch (SQLException SQLe2) {
                    SQLExceptionErrPrint(SQLe2);
                } 
            return false;
        }catch(ClassNotFoundException CNFe){
            CNFe.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
                try {
                    // Rollback the changes since something has gone wrong
                    con.rollback();
                } catch (SQLException SQLe2) {
                    SQLExceptionErrPrint(SQLe2);
                }
            return false;
        }finally{
            try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			}
        }
    }

    // --------- Helper methods ---------
    // I made these to consolidate some functions that I felt made a bit 
    // of a mess where they were used or ones that were used more than once
    public static void SQLExceptionErrPrint(SQLException SQLe){
        System.err.println(SQLe.getSQLState());
        System.err.println(SQLe.getErrorCode());
        System.err.println(SQLe.getMessage());
    }

    public static Employee rsetEmployeeExtract(ResultSet rset) throws SQLException{
        return new Employee(rset.getInt("EMPLOYEE_ID"), rset.getString("FIRST_NAME"),
                            rset.getString("LAST_NAME"), rset.getString("EMAIL"),
                            rset.getString("PHONE"), rset.getDate("HIRE_DATE"),
                            rset.getInt("MANAGER_ID"), rset.getString("JOB_TITLE") );
    }
    // --------- --------- --------- ---------
}
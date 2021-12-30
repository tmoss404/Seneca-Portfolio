package ca.myseneca.test;

import java.util.ArrayList;
import java.util.Scanner;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.Employee;

public class HRManagement {
    
    
    public static void main(String[] args){

        Employee emp = null;
        try(Scanner in = new Scanner(System.in)) {

            //Accept user input for the username
            System.out.println(); // Formatting print
			System.out.print("Please enter username: ");
			String user = in.next();
			System.out.print("Please enter password: ");
			String pass = in.next();

            // --------- getEmployeeID example ---------
            int empid = DBAccessHelper.getEmployeeID(user, pass);

            if(empid == 0){
                System.out.println(); // Formatting print
                System.out.println("Invalid Employee Information.");
                System.exit(0);
            }else{
            // --------- getEmployeeByID example ---------
                emp = DBAccessHelper.getEmployeeByID(empid);
            }

            // Print out selected Employees info
            System.out.println(); // Formatting print
            System.out.println("--------- Selected Employee Information ---------");
            System.out.println(emp);


            // --------- getAllEmployees example ---------
            // I am not going to print out each of the employee entries here since it completely engulfs the output
            // Nothing goes wrong here so it is functioning properly, and it has been tested
            ArrayList<Employee> AllEmployees = DBAccessHelper.getAllEmployees(); 


            // --------- getEmployeesByManagerID example ---------
            // See the notes on this function and why it is different than the specs. Short form is that
            // the table that I had access to did not contain and deptId so I made this instead.
            // This will retrieve each employee with the same manager as your selected employee
            System.out.println(); // Formatting print
            System.out.println("--------- getEmployeesByManagerID example ---------");
            ArrayList<Employee> SameManagerEmployees = DBAccessHelper.getEmployeesByManagerID(emp.getMANAGER_ID());
            for(Employee e : SameManagerEmployees){
                System.out.println(e);
            }


            // --------- updateEmployee example ---------
            // Store selected employee name
            String temp_FirstName = emp.getFIRST_NAME();

            // Updates selected employee first name to "Changed", queries the DB to show it changed
            System.out.println(); // Formatting print
            System.out.println("--------- updateEmployee example ---------");
            emp.setFIRST_NAME("Changed");
            DBAccessHelper.updateEmployee(emp);
            System.out.println(DBAccessHelper.getEmployeeByID(emp.getEMPLOYEE_ID()));

            // Change it back and show it again, restored to normal
            emp.setFIRST_NAME(temp_FirstName);
            DBAccessHelper.updateEmployee(emp);
            System.out.println(DBAccessHelper.getEmployeeByID(emp.getEMPLOYEE_ID()));


            // These examples could not be done with the selected employee due to the fact that they are connected to the security table
            // I will query the table for a valid employee to demonstrate these methods
            // If your table does not contain the employee with this specific ID simply add one that is valid and the example should work
            Employee testEmp = DBAccessHelper.getEmployeeByID(105);
            // --------- deleteEmployeeByID example ---------
            System.out.println(); // Formatting print
            System.out.println("--------- deleteEmployeeByID example ---------");
            System.out.println("Number of employees deleted: " + DBAccessHelper.deleteEmployeeByID(testEmp.getEMPLOYEE_ID()));

            // --------- addEmployee example ---------
            System.out.println(); // Formatting print
            System.out.println("--------- addEmployee example ---------");
            DBAccessHelper.addEmployee(testEmp);
            System.out.println(DBAccessHelper.getEmployeeByID(testEmp.getEMPLOYEE_ID()));

            // --------- batchUpdate example ---------
            // Tried to make the most harmless batch of updates I could think of
            // settled on changing the name of the selected employee 5 times
            // if they all succeed the selected employee should end up named 'BatchChange5'
            // finally I change it back to normal, no harm done
            String[] SQLs = new String[]{"UPDATE EMPLOYEES SET FIRST_NAME ='BatchChange1' WHERE EMPLOYEE_ID = " + emp.getEMPLOYEE_ID(),
                                         "UPDATE EMPLOYEES SET FIRST_NAME ='BatchChange2' WHERE EMPLOYEE_ID = " + emp.getEMPLOYEE_ID(),
                                         "UPDATE EMPLOYEES SET FIRST_NAME ='BatchChange3' WHERE EMPLOYEE_ID = " + emp.getEMPLOYEE_ID(),
                                         "UPDATE EMPLOYEES SET FIRST_NAME ='BatchChange4' WHERE EMPLOYEE_ID = " + emp.getEMPLOYEE_ID(),
                                         "UPDATE EMPLOYEES SET FIRST_NAME ='BatchChange5' WHERE EMPLOYEE_ID = " + emp.getEMPLOYEE_ID()
                                        };
            
            System.out.println(); // Formatting print
            System.out.println("--------- batchUpdate example ---------");
            System.out.println("Did the batch update succeed: " + DBAccessHelper.batchUpdate(SQLs));
            System.out.println(DBAccessHelper.getEmployeeByID(emp.getEMPLOYEE_ID()));
            DBAccessHelper.updateEmployee(emp);
            System.out.println(); // Formatting print
            System.out.println("--------- Employee Restored ---------");
            System.out.println(DBAccessHelper.getEmployeeByID(emp.getEMPLOYEE_ID()));
        }
        
    }
}

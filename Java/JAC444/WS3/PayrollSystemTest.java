/**********************************************
Workshop #3
Course: JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.

Tanner Douglas Moss
Date:10/10/2020
**********************************************/
public class PayrollSystemTest {
    public static void main(String[] args){
        try{
            
            HourlyEmployee hourlyEmp = new HourlyEmployee("John", "Hourly", "111-11-1111", 10, 45);
            CommissionEmployee commissionEmp = new CommissionEmployee("Mary", "Commish", "222-22-2222", 3000, .1);
            SalariedEmployee salaryEmp = new SalariedEmployee("Mahboob", "Ali", "444-44-4444", 1200);
            BasePlusCommissionEmployee basePlusEmp = new BasePlusCommissionEmployee("Tanner", "Moss", "666-66-6666", 6000, .30, 800);

            //----- Part 1 -----//
            System.out.println("\n------ Part 1 ------\n");
            System.out.println( "Employees processed individually:\n" );
            System.out.println("--------------------\n");
            System.out.printf( "%s\n%s: $%,.2f\n\n", hourlyEmp, "Earned", hourlyEmp.getPaymentAmount() );
            System.out.println("--------------------\n");
            System.out.printf( "%s\n%s: $%,.2f\n\n", commissionEmp, "Earned", commissionEmp.getPaymentAmount() );
            System.out.println("--------------------\n");
            System.out.printf( "%s\n%s: $%,.2f\n\n", salaryEmp, "Earned", salaryEmp.getPaymentAmount() );
            System.out.println("--------------------\n");
            System.out.printf( "%s\n%s: $%,.2f\n\n", basePlusEmp, "Earned", basePlusEmp.getPaymentAmount() );

            //----- Part 2 -----//
            Employee[] empArray = { hourlyEmp, commissionEmp, salaryEmp, basePlusEmp };

            System.out.println("\n------ Part 2 ------\n");
            System.out.println( "Employees processed in a loop:\n" );
            for(Employee emp : empArray){
                if(emp instanceof BasePlusCommissionEmployee){
                    ((BasePlusCommissionEmployee)emp).setBaseSalary( 
                        ((BasePlusCommissionEmployee)emp).getBaseSalary() +           // salary + 
                        ( ((BasePlusCommissionEmployee)emp).getBaseSalary() * .10) ); // (10% bonus applied to salary)
                }

                /*
                Since I need to show off checking with instanceof and using a cast I decided to implement
                the salaried - commission 10% bonus here. I apply it based on checking if this particular employee 
                qualifies and then using both the "set" and "get" that are unique to the salaried - commission
                employees.
                */
                System.out.println("--------------------\n");
                System.out.println("Employee Satus: " + emp.getClass().getName()+ "\n");
                System.out.printf( "%s%s: $%,.2f\n\n", emp, "Earned", emp.getPaymentAmount() );
                
            }

        } catch(Exception e){
            System.out.println(e);
        }
        
    }
}

public class SalariedEmployee extends Employee {
    
    //----- Instance Variables -----//
    private double weeklySalary;


    //-----  Weekly Salary Methods-----/
    public double getWeeklySalary(){
        return this.weeklySalary;
    }

    public void setWeeklySalary(double salary){
        if(salary <= 0)
            throw new ArithmeticException("Weekly Salary must be greater than 0.");
        else
            this.weeklySalary = salary;
    }

    //----- Constructors -----//
    public SalariedEmployee(){

    }

    public SalariedEmployee(String first, String last, String ssn, double salary){
        super(first, last, ssn);
        this.setWeeklySalary(salary);
    }

    //----- Overloads, Overrides, and Abstract implementations -----//
    public String toString(){

        return  super.toString() +
                "Weekly Salary: " + this.getWeeklySalary() + "\n";
    }

    public double getPaymentAmount(){

        return this.getWeeklySalary(); //I'm assuming the pay period is weekly here if it was bi-weekly I'd probably just double this return
    }
}

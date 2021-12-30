public class BasePlusCommissionEmployee extends CommissionEmployee {
    
    //----- Instance Variables -----//
    private double baseSalary;


    //----- Base Salary Methods -----//
    public double getBaseSalary(){
        return this.baseSalary;
    }

    public void setBaseSalary(double salary){
        if(salary <= 0)
            throw new ArithmeticException("Base Salary must be greater than 0.");
        else
            this.baseSalary = salary;
    }


    //----- Constructors -----//
    public BasePlusCommissionEmployee(){

    }

    public BasePlusCommissionEmployee(String first, String last, String ssn, double sales, double rate, double salary){
        super(first, last, ssn, sales, rate);
        this.setBaseSalary(salary);
    }


    //----- Overloads, Overrides, and Abstract Implementations -----//
    public String toString(){

        return  super.toString() +
                "Base Salary: " + this.getBaseSalary() + "\n";
    }

    public double getPaymentAmount(){

        return this.getBaseSalary() + (this.getGrossSales() * this.getCommissionRate());
    }
}

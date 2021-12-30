public class CommissionEmployee extends Employee {

    //----- Instance Variables -----//
    private double grossSales;
    private double commissionRate;


    //----- GrossSales Methods -----//
    public double getGrossSales(){
        return this.grossSales;
    }

    public void setGrossSales(double sales){
        if(sales <= 0)
            throw new ArithmeticException("Gross Sales must be greater than 0.");
        else
            this.grossSales = sales;
    }

    //----- CommissionRate Methods -----//
    public double getCommissionRate(){
        return this.commissionRate;
    }

    public void setCommissionRate(double rate){
        if(rate <= 0 || rate >= 1) //This is assuming the specification of "between 0 and 1" is not inclusive so there can't be a 100% or 0% commission
            throw new ArithmeticException("Commission Rate must be between 0 and 1.");
        else
            this.commissionRate = rate;
    }


    //----- Constructors -----//
    public CommissionEmployee(){

    }

    public CommissionEmployee(String first, String last, String ssn, double sales, double rate){
        super(first, last, ssn);
        this.setGrossSales(sales);
        this.setCommissionRate(rate);
    }


    //----- Overloads, Overrides, and Abstract implementations -----//
    public String toString(){

        return  super.toString() +
                "Gross Sales: " + this.getGrossSales() + "\n" +
                "Commission Rate: " + this.getCommissionRate() + "\n";
    }

    public double getPaymentAmount() {

        return this.getGrossSales() * this.getCommissionRate();
    }

}

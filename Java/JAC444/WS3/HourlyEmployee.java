public class HourlyEmployee extends Employee {

    //----- Instance Variables -----//
    private double wage;
    private double hours;


    //----- Wage Methods -----//
    public double getWage(){
        return this.wage;
    }

    public void setWage(double hourlyWage){
        if(hourlyWage <= 0)
            throw new ArithmeticException("Wage must be greater than 0.");
        else
            this.wage = hourlyWage;
    }


    //----- Hours Methods -----//
    public double getHours(){
        return this.hours;
    }

    public void setHours(double hoursWorked){
        if(hoursWorked < 0 || hoursWorked > 168)
            throw new ArithmeticException("Hours must be between 0 and 168");
        else
            this.hours = hoursWorked;
    }


    //----- Constructors -----//
    public HourlyEmployee(){

    }

    public HourlyEmployee(String first, String last, String ssn, double hourlyWage, double hoursWorked){
        super(first, last, ssn);
        this.setWage(hourlyWage);
        this.setHours(hoursWorked);
    }


    //----- Overloads, Overrides, and Abstract implementations -----//
    public String toString(){

        return  super.toString() +
                "Hourly Wage: " + this.getWage() + "\n" +
                "Hours Worked: " + this.getHours() + "\n";
    }

    public double getPaymentAmount(){
        double result = 0;
        double tempHours = this.getHours();
        double otRate = this.getWage() + (this.getWage() * .5);
        
        if(this.getHours() > 40){    
            result = 40 * this.getWage();
            tempHours -= 40;
            result += tempHours * otRate;
            
        }else{
            result = tempHours * this.getWage();
        }
        return result;
    }
}

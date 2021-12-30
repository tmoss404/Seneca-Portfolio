public abstract class Employee implements Payable {
    
    //----- Instance Variables -----//
    private String firstName;
    private String lastName;
    private String ssn;


    //----- FirstName Methods -----//
    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String first){
        this.firstName = first;
    }


    //----- LastName Methods -----//
    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String last){
        this.lastName = last;
    }

    //----- SSN Methods -----//
    public String getSSN(){
        return this.ssn;
    }

    public void setSSN(String ssn){
        this.ssn = ssn;
    }


    //----- Constructors -----//
    public Employee(){

    }

    public Employee(String first, String last, String ssn){
        this.setFirstName(first);;
        this.setLastName(last);
        this.setSSN(ssn);
    }


    //----- Overloads, Overrides, and Abstract implementations -----//
    public String toString(){

        return  "First Name: " + this.getFirstName() + "\n" +
                "Last Name: " + this.getLastName() + "\n" +
                "SSN: " + this.getSSN() + "\n";
    }
}

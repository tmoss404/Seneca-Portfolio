package ca.myseneca.model;

import java.io.Serializable;
import java.sql.Date;

public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;
    private int EMPLOYEE_ID;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String EMAIL;
    private String PHONE;
    private Date HIRE_DATE;
    private int MANAGER_ID;
    private String JOB_TITLE;

    // --------- Constructors ---------

    // Default
    public Employee(){}

    // Constructor with parameters 
    public Employee(int EMPLOYEE_ID, String FIRST_NAME, String LAST_NAME, String EMAIL, 
                    String PHONE, Date HIRE_DATE, int MANAGER_ID, String JOB_TITLE) {

        this.EMPLOYEE_ID = EMPLOYEE_ID;
        this.FIRST_NAME = FIRST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.HIRE_DATE = HIRE_DATE;
        this.MANAGER_ID = MANAGER_ID;
        this.JOB_TITLE = JOB_TITLE;

    }
    // --------- --------- --------- ---------

    // --------- Getters & Setters ---------

    // EMPLOYEE_ID Operations
    public int getEMPLOYEE_ID() {
        return EMPLOYEE_ID;
    }
    public void setEMPLOYEE_ID(int EMPLOYEE_ID) {
        this.EMPLOYEE_ID = EMPLOYEE_ID;
    }

    // FIRST_NAME Operations
    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    // LAST_NAME Operations
    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    // EMAIL Operations
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    // PHONE Operations
    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    // HIRE_DATE Operations
    public Date getHIRE_DATE() {
        return HIRE_DATE;
    }

    public void setHIRE_DATE(Date HIRE_DATE) {
        this.HIRE_DATE = HIRE_DATE;
    }

    // MANAGER_ID Operations
    public int getMANAGER_ID() {
        return MANAGER_ID;
    }

    public void setMANAGER_ID(int MANAGER_ID) {
        this.MANAGER_ID = MANAGER_ID;
    }

    // JOB_TITLE Operations
    public String getJOB_TITLE() {
        return JOB_TITLE;
    }

    public void setJOB_TITLE(String JOB_TITLE) {
        this.JOB_TITLE = JOB_TITLE;
    }
    // --------- --------- --------- ---------

    // --------- toString Method ---------
    @Override
    public String toString() {
        return "Employee [EMPLOYEE_ID = " + EMPLOYEE_ID + ", FIRST_NAME = " + FIRST_NAME + ", LAST_NAME = " + LAST_NAME + ", EMAIL = " + EMAIL
                + ", HIRE_DATE = " + HIRE_DATE + ", JOB_TITLE = " + JOB_TITLE + ", MANAGER_ID = " + MANAGER_ID + ", PHONE = " + PHONE + "]";
    }
   // --------- --------- --------- ---------
}

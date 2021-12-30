/**********************************************
Workshop #
Course:JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date:10/03/2020
**********************************************/
import java.util.Scanner;

public class IncomeTax { 
    static Scanner in = new Scanner(System.in);

    final int SINGLE_FILER = 0;
    final int MARRIED_JOINTLY_OR_QUALIFYING_WIDOW_ER = 1;
    final int MARRIED_SEPARATELY = 2;
    final int HEAD_OF_HOUSEHOLD = 3;
    final int statusTotal = 4; // This value helps us format output into a table based on how many status options there are, change this if more status options are added
    final double[] rates_2001 = {.15, .275, .305, .355, .391};
    final int[][] intervals_2001 = {        // The intervals are based on the section of income that will be evaluated at a specific rate
        { 27050, 38500, 71200, 160600, 0 }, // ie. the first $27050 at 15% the next $38500 at 27.5% etc. 
        { 45200, 64050, 57250, 130850, 0 }, // once we make it to the final interval we only need to check if there is income left to calculate at the final rate
        { 22600, 32025, 28624, 65425, 0 },  // so we will only need to check if my temp taxable is still greater than 0 and apply the rate to the remaining total
        { 36250, 57400, 58000, 145700, 0 }
    };

    final double[] rates_2009 = {.10, .15, .25, .28, .33, .35};
    final int[][] intervals_2009= {
        { 8350, 25600, 48300, 89300, 201400, 0 },
        { 16700, 51200, 69150, 71800, 164100, 0 },
        { 8350, 25600, 34575, 35900, 82050, 0 },
        { 11950, 33550, 71950, 72750, 182750, 0 }

    };

    int filingStatus;
    double taxableIncome;


    // -------------------------- //
    public static void main(String[] args){
        IncomeTax taxes = new IncomeTax();
        int selection = taxes.optionSelect();
        if(selection == 1)
            taxes.option_1();
        else if(selection == 2)
            taxes.option_2();
        else if(selection == 0)
            System.exit(0);

    }
    // -------------------------- //

    static double TaxCalc(int[][] intervals, double[] rates, double taxableIncome, int filingStatus){
        
        double taxableTemp = taxableIncome; // Set a temp value in order to chunk out each interval
        double tax = 0;

        for(int i = 0; i < intervals[filingStatus].length; i++){

            if(taxableTemp > intervals[filingStatus][i] && i < intervals[filingStatus].length){ // If the temp tax value is greater than the current interval, and we haven't reached the last interval yet
                tax += (intervals[filingStatus][i] * rates[i]);
                taxableTemp -= intervals[filingStatus][i];
            }else{ // This will call if the temp is over the current interval and we are at the last one OR if the temp is simply within the current interval
                tax += (taxableTemp * rates[i]);
                break;
            }

        }

        return tax;
        
    }

    void option_1(){

        System.out.println();
        System.out.println("0 - single filer");
        System.out.println("1 - married jointly or qualifying widow(er)");
        System.out.println("2 - married separately");
        System.out.println("3 - head of household");
        System.out.print("Enter the filing status: ");

        while(true){
            try{
                int input = in.nextInt();

                if(input == 0){
                   this.filingStatus = SINGLE_FILER;
                   break;
                }
                else if(input == 1 ){
                    this.filingStatus = MARRIED_JOINTLY_OR_QUALIFYING_WIDOW_ER;
                    break;
                }
                else if(input == 2){
                    this.filingStatus = MARRIED_SEPARATELY;
                    break;
                }else if(input == 3){
                    this.filingStatus = HEAD_OF_HOUSEHOLD;
                    break;
                }else{
                    in.nextLine();
                    System.out.print("Please enter a valid selection (0, 1, 2, 3) : ");
                }

            } catch(Exception e){
                in.nextLine();
                System.out.print("Please enter a valid selection (0, 1, 2, 3) : ");
            }
        }
        
        System.out.print("Enter the taxable income: $");

        while(true){
            try{
                this.taxableIncome = in.nextDouble();
                break;
            } catch(Exception e){
                in.nextLine();
                System.out.print("Please enter a valid amount of taxable income: ");
            }
        }
       
        
        System.out.println("Tax is: $" + TaxCalc(this.intervals_2009, this.rates_2009, this.taxableIncome, this.filingStatus));

    }

    void option_2(){
        double taxFrom;
        double taxTo;

        System.out.print("Enter the amount From: ");
        while(true){
            try{
                taxFrom = in.nextDouble();
                break;
            } catch(Exception e){
                in.nextLine();
                System.out.print("Please enter a valid amount of taxable income (From): ");
            }
        }

        System.out.print("Enter the amount To: ");
        while(true){
            try{
                taxTo = in.nextDouble();
                break;
            } catch(Exception e){
                in.nextLine();
                System.out.print("Please enter a valid amount of taxable income (To): ");
            }
        }

        System.out.println();
        System.out.println("2009 tax tables for taxable income from $" + String.format("%.0f", taxFrom) +" to $" + String.format("%.0f", taxTo));
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Taxable   Single         Married Joint     Married        Head of");
        System.out.println("Income                   or Qualifying     Separate       a House");
        System.out.println("                         Widow(er)                              ");
        System.out.println("-------------------------------------------------------------------");
            for(double taxTemp = taxFrom; taxTemp <= taxTo; taxTemp += 1000){ // Start with the "From" value and increment our temp value to calculate each interval
                Object[] row = new Double[statusTotal + 1]; // Create a row object for formatting purposes [size = to number of status options + 1(for the income column)]
                row[0] = taxTemp; // Set the first row of them column to the current incremented temp value
                for(int i = 0; i < statusTotal; i++){ // Loop through and add a column for each filer status
                    row[i+1] = TaxCalc(this.intervals_2001, this.rates_2001, taxTemp, i);
                }
                System.out.println(String.format("%-10.0f%-15.2f%-18.2f%-15.2f%-15.2f", row)); // Formatting
            }

        System.out.println();
        System.out.println("2009 tax tables for taxable income from $" + String.format("%.0f", taxFrom) +" to $" + String.format("%.0f", taxTo));
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Taxable   Single         Married Joint     Married        Head of");
        System.out.println("Income                   or Qualifying     Separate       a House");
        System.out.println("                         Widow(er)                              ");
        System.out.println("-------------------------------------------------------------------");
            for(double taxTemp = taxFrom; taxTemp <= taxTo; taxTemp += 1000){
                Object[] row = new Double[statusTotal + 1];
                row[0] = taxTemp;
                for(int i = 0; i < statusTotal; i++){
                    row[i+1] = TaxCalc(this.intervals_2009, this.rates_2009, taxTemp, i);
                }
                System.out.println(String.format("%-10.0f%-15.2f%-18.2f%-15.2f%-15.2f", row));
            }
    }

    int optionSelect(){

        
        System.out.println("1 - Compute personal income tax");
        System.out.println("2 - Print the tax tables for taxable incomes (with range)");
        System.out.println("0 - Exit");
        System.out.print("Enter the corresponding number based on your desired selection: ");
        
        // --------------------------------------------------- //

        int input = 0;

        while(true){
            try{
                input = in.nextInt();

                if(input == 0)
                    return 0;
                else if(input == 1 ){
                    return input;
                }
                else if(input == 2){
                    return input;
                }else{
                    in.nextLine();
                    System.out.print("Please enter a valid selection (0, 1, 2): ");
                }
            } catch(Exception e){
                in.nextLine();
                System.out.print("Please enter a valid selection (0, 1, 2): ");
            }
        }
    }

    /*
    IncomeTax(int filingStatus, int[][] intervals, double[] rates, double taxableIncome){
         
        this.filingStatus = filingStatus;
        this.taxableIncome = taxableIncome;

        for(int i=0; i<this.intervals.length; i++)
            for(int j=0; j<this.intervals[i].length; j++)
                this.intervals[i][j]=intervals[i][j];

        for(int i=0; i<this.rates.length; i++)
            this.rates[i]=rates[i];
    }

    Since this is explicitly requested in the assignment I have implemented what it might look like based on the parameters.
    However, since in my mind there is no logical reason to use this since all the rates are fairly static and can easily be final
    variables and not instance variables I am implementing a different solution. I spent way too much time trying to find a 
    way to actually do it this way though.
    */ 
}
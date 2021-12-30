package ca.Bank;

import java.util.ArrayList;
import java.util.Scanner;

public class tester {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        
        ArrayList<Bank> banks = init();
        checkSafety(banks);
        
    }

    // -----------------------

    public static ArrayList<Bank> init(){
        ArrayList<Bank> banks = new ArrayList<>();
        int input = 0;
        double min = 0;

        while(true){
            System.out.print("\nNumber of banks: ");
            if(in.hasNextInt()) {
                input = in.nextInt();
                in.nextLine();
                break;
            }
            else {
                System.out.println("\nPlease enter a valid amount of banks.");
                in.nextLine();
            }
        }
        while(true){
            System.out.print("Minimum asset limit: ");
            if(in.hasNextDouble()) {
                min = in.nextDouble();
                in.nextLine();
                break;
            }
            else {
                System.out.println("\nPlease enter a valid minimum asset limit.\n");
                in.nextLine();
            }
        }

        //------ Construct arrayList of banks with ID's and minimum asset limits
        for(int i = 0; i < input; i++){
            banks.add(new Bank(min, i));
        }
        //-----------------------------

        //------ Set Balance and loan information for each bank
        for(Bank bank : banks){
            int loanedBanks;
            System.out.println("\nFor Bank #" + bank.getId());
            while(true){
                System.out.print("  Balance: ");
                if(in.hasNextDouble()) {
                    bank.setBalance(in.nextDouble());
                    in.nextLine();
                    break;
                }
                else {
                    System.out.println("\nPlease enter a valid balance. (Bank #" + bank.getId() + ")\n");
                    in.nextLine();
                }
            }

            while(true){
                System.out.print("  Number of banks Loaned: ");
                if(in.hasNextInt()) {
                    loanedBanks = in.nextInt();
                    in.nextLine();
                    break;
                }
                else {
                    System.out.println("\nPlease enter a valid number of banks. (Loans from Bank #" + bank.getId() + ")\n");
                    in.nextLine();
                }
            }

            for(int i = 0; i < loanedBanks; i++){
                int loaningTo;
                double amount;

                while(true){
                    System.out.print("      Bank ID who gets the loan: ");
                    if(in.hasNextInt()) {
                        loaningTo = in.nextInt();
                        in.nextLine();
                        if(loaningTo != bank.getId()){
                            if(loaningTo >= banks.size() || loaningTo < 0){
                                System.out.println("\nRequested bank does not exist.\n");
                            }else{
                                break;
                            }
                        }else{
                            System.out.println("\nA bank cannot loan to itself.\n");
                        }
                    }
                    else {
                        System.out.println("\nPlease enter a valid bank id.\n");
                        in.nextLine();
                    }
                }

                while(true){
                    System.out.print("      Loaned amount: ");
                    if(in.hasNextDouble()) {
                        amount = in.nextDouble();
                        in.nextLine();
                        break;
                    }
                    else {
                        System.out.println("\nPlease enter a valid amount to loan (From Bank #" + bank.getId() + " to Bank #" + loaningTo + ")\n");
                        in.nextLine();
                    }
                }
                bank.loanTo(banks.get(loaningTo), amount);
            }
        }
        //--------------------------

        return banks;
    }

    //-------------------

    public static void checkSafety(ArrayList<Bank> banks){
        ArrayList<Bank> unsafe = new ArrayList<>();
        ArrayList<String> loanBankrupts =  new ArrayList<>();

        for(Bank b : banks)
            if(!b.isSafe()) unsafe.add(b);

        // For each bank
        for(int i = 0; i < banks.size(); i++){
            Bank b = banks.get(i);
            double bankTotal = b.getTotal();
            // Loop through unsafe banks
            for(int j = 0; j < unsafe.size(); j++){
                Bank unsafeBank = unsafe.get(j);
                if(!b.equals(unsafeBank)){
                    // If the bank we're checking loaned to an unsafe bank
                    if(b.loans.containsKey(unsafeBank)){
                        // Remove the unsafe loan from the calculated total of the bank
                        bankTotal -= b.loans.get(unsafeBank);
                        // Check if that loan puts them in the red if it cannot be fulfilled
                        if(bankTotal < b.getMinimum()){
                            // If the bank is not already marked as unsafe, it has been made unsafe by this loan
                            if(!unsafe.contains(b)){
                                // Add it to unsafe list
                                unsafe.add(b);
                                // Record a string of the reason this bank was marked unsafe
                                loanBankrupts.add("Bank " + unsafeBank.getId() + " got bankrupted " 
                                                    + "and it got the loan from Bank " + b.getId() 
                                                    + ", because Bank " + unsafeBank.getId() + " is unsafe due to "
                                                    + "under the limit Bank " + b.getId() + " is also unsafe due to lower limit.");
                                // Reset the check since now a new bank has been marked unsafe it may cause others to become unsafe
                                i=0;
                            }
                        }
                    }
                }
            }
        }

        System.out.print("\nUnsafe banks are");
        for(int i = 0; i < unsafe.size(); i++){
            System.out.print(" Bank " + unsafe.get(i).getId());
            System.out.print( i != unsafe.size() - 1 ? " and" : "\n");
        }
        for(String msg: loanBankrupts)
            System.out.println(msg);
    }
}

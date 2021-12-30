package ca.Bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private int id;
    private double balance;
    private double minimum;
    private boolean safe;
    public Map<Bank, Double> loans = new HashMap<>();

    public void loanTo(Bank target, double amount){
        this.loans.put(target, amount);
        // Set the safety of the bank any time a new loan is added
        setSafe();
    }

    public Bank(double minimum, int id){
        this.minimum = minimum;
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        // Set the safety of the bank any time the balance changes
        setSafe();
    }

    public double getTotal(){
        double total = this.balance;
        for(Map.Entry<Bank, Double> loan: this.loans.entrySet())
            total += loan.getValue();

        return total;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe() {
        double total = getTotal();
        
        if(total < this.minimum )
            this.safe = false;
        else
            this.safe = true;
    }

    @Override
    public String toString() {
        return "Bank [balance=" + balance + ", id=" + id + ", loans=" + loans + ", minimum=" + minimum + ", safe="
                + safe + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }


}

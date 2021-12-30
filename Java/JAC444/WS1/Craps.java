/**********************************************
Workshop #1
Course: JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date: 9/26/2020
**********************************************/
public class Craps {
    
    public static void main(String[] args){
    
        int result = roll();

        if(result == 2 || result == 3 || result == 12) // If you roll any of the losing number
            System.out.println("Craps, better luck next time, you lose.");
        if(result == 7 || result == 11) // If you roll any of the winning numbers
            System.out.println("Congratulations, you win!");

        if(result == 4 || result == 5 || result == 6 || result == 8 || result == 9 || result == 10){ // If you roll anything else
            System.out.println("Point is (established) set to " + result);
            int newResult = 0; // New result to check against the established one each time we roll 
            boolean rolling = true;
            while(rolling){ // Rolling until we win or lose
                newResult = roll();
                if(newResult == result){ // If you win we have something to do
                    System.out.println("Congratulations, you win!");
                    rolling = false; // Not rolling anymore
                }
                if(newResult == 7){ // If you lose we have something to do
                    System.out.println("Craps, better luck next time, you lose.");
                    rolling = false;// Not rolling anymore
                }
            }

        }
            
        
    }
    
    public static int roll(){ // quick custom roll method since we do this more than once
    
        int d1=(int)(Math.random()*6+1);
        int d2=(int)(Math.random()*6+1);
        int result = d1 + d2;
        System.out.println("You rolled " + d1 + " + " + d2 + " = " + result);
        return result;
    }

}

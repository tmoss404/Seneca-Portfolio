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
import java.util.Scanner;

public class MaxLocation {
    int Row; // For storing the row index of the max
    int Column; // For storing the column index of the max
    double maxValue; // For storing the max value itself

    static int rows; // Number of rows to create the array with
    static int cols; // Number of columns to create the array witb
    static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args){
        
        MaxLocation obj = new MaxLocation();

        obj.getArraySize();
        double arr[][] = new double[rows][cols];

        obj.setArray(arr);
        obj.getMax(arr);

        System.out.println("The location of the largest element is " + obj.maxValue + " at (" + obj.Row + ", " + obj.Column + ") ");
        in.close();
    }
    
    public void getArraySize(){

        System.out.print("Enter the number of rows and columns in the array: ");
        while(true){ // Keep checking until both rows and cols are fill correctly
            if(in.hasNextInt()){ // If the user successfully entered a first int
                rows = in.nextInt();
                if(in.hasNextInt()){// If the user successfully entered a second int
                    cols = in.nextInt();
                    in.nextLine(); // Consume any remaining entry to move on safely
                    break; // Break since we successfully filled both rows and cols
                } else { // If the 2nd entry is not an int
                    System.out.println("Please enter two valid integers.");
                    in.nextLine();
                }
            } else { // If the first entry is not an int
                System.out.println("Please enter two valid integers.");
                in.nextLine();
            }
        }

    }
    public void setArray(double[][] arr){

        System.out.println("Enter the array: ");
        for(int n = 0; n < arr.length; n++){
            while(true){ // Keep moving through the current row until all entries are valid
                for(int i = 0; i < arr[n].length; i++){
                    if(in.hasNextDouble()){ // If the next entry is a valid double
                        arr[n][i] = in.nextDouble();
                    } else { // If user inputs invalid anything but a double
                        System.out.println("Please enter " + arr[n].length + " valid doubles to insert into row " + (n+1) + ".");
                        // Added the information of which row you're attempting to fill, if lots of mistakes are made you won't lose track
                        i = -1; // Restart filling the row
                        in.nextLine();
                    }
                }
                in.nextLine(); // Consume any remaining input to move on safely
                break; // Move on to the next row
                
            }
        }

    }

    public MaxLocation getMax(double[][] arr){

        for(int n = 0; n < arr.length; n++){
            for(int i = 0; i < arr[n].length; i++){
                if(arr[n][i] > maxValue){ // Check the current index against the highest value so far
                    this.maxValue = arr[n][i]; // If the value at the index is the new max replace set the info accordingly
                    this.Row = n+1;
                    this.Column = i+1;
                }
            }
        }
        
        return this; // The requsted return of the instance of the object
    }

}

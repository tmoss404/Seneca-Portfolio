/**********************************************
Workshop #6
Course:JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date: 11/10/2020
**********************************************/
import java.util.InputMismatchException;
import java.util.Scanner;

public class Processor {
    @FunctionalInterface
    public interface ArrayProcessor {
        double apply( double[] array );
    } 

    public static final ArrayProcessor max = (array) -> {
        //Initialize the temp value to a value from the array, 
        //accounting for cases where each value in the array could be negative
        //if I were to initialize it to something like 0 that wouldn't go over well
        double max = array[0];
        for(double num : array)
            if(num > max) max = num;
        
        return max;
    }; 

    public static final ArrayProcessor min = (array) -> {
        double min = array[0];
        for(double num : array)
            if(num < min) min = num;
        
        return min;
    };
    
    public static final ArrayProcessor sum = (array) -> {
        double sum = 0;
        for(double num : array)
            sum += num;

        return sum;
    };

    public static final ArrayProcessor average = (array) -> {

        return sum.apply(array)/(array.length);
    
    };

    public static ArrayProcessor counter(double value){
        return (array)->{
            double count = 0;
            for(double num : array)
                if(num == value) count++;

            return count;
        };
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int size;
        while(true){
            System.out.print("Please enter the desired size of your array: ");
            if(in.hasNextInt())
                try{
                    size = in.nextInt();
                    in.nextLine();
                    break;
                }catch(InputMismatchException e){
                    e.printStackTrace();
                }
            else{
                System.out.println("Please enter a valid size for the array. (int)");
                in.nextLine();
            }
        }

        double array[] = new double[size];
        for(int i = 0; i < array.length; i++){
            while(true){
                System.out.print("Enter a double to insert into index [" + i + "] : ");
                if(in.hasNextDouble()){
                    try{
                        array[i] = in.nextDouble();
                        in.nextLine();
                        break;
                    }catch(InputMismatchException e){
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Please enter valid value for array index. (double)");
                    in.nextLine();
                }
            }
        }
        System.out.println("--------------");
        System.out.printf("Max is: %.2f%n", max.apply(array));
        System.out.printf("Min is: %.2f%n", min.apply(array));
        System.out.printf("Sum is: %.2f%n", sum.apply(array));
        System.out.printf("Average is: %.2f%n", average.apply(array));
        System.out.println("--------------");

        double check;
        while(true){
            System.out.print("Enter the number you'd like to count within the array: ");
            if(in.hasNextDouble()){
                try{
                    check = in.nextDouble();
                    in.nextLine();
                    break;
                }catch(InputMismatchException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Please enter valid value to count within the array. (double)");
                in.nextLine();
            }
        }
        System.out.printf("The number of times %.2f appears in the array is: %.0f%n", check, counter(check).apply(array));
        in.close();
    }
}

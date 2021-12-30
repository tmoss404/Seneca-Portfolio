package ca.Time;

import java.util.Scanner;

public class tester {
    public static void main(String[] args){
        Time time1;
        Time time2;
        Time time3;

        try(Scanner in = new Scanner(System.in) ){
            // Process time1
            while(true){
                System.out.print("\nEnter time1 (hour minute second): ");
                String input = in.nextLine();
                if(input.matches("(?:(\s*[0-9.]+\s+){2}[0-9.]+\s*){1}")){ 
                    String[] s = input.trim().split(" ");
                    long hour = Long.parseLong(s[0]);
                    long minute = Long.parseLong(s[1]);
                    long second = Long.parseLong(s[2]);
                    time1 = new Time(hour, minute, second);
                    
                    processTime("time1", time1);
                    break;
                } else {
                    System.out.println("\nPlease enter valid hours/minutes/seconds for: time1");
                }
            }
            
            // Process time2
            while(true){
                System.out.print("\nEnter time2 (elapsed time in seconds): ");
                if(in.hasNextLong()){ 
                    long input = in.nextLong();
                    time2 = new Time(input);

                    processTime("time2", time2);
                    break;
                } else {
                    System.out.println("\nPlease enter a valid number for: time2 (elapsed time in seconds)");
                    in.nextLine();
                }
            }

            System.out.println("\ntime1.compareTo(time2)? " + time1.compareTo(time2) +"\n");

            time3 = time1.clone();
            System.out.println("time3 is created as a clone of time1");
            System.out.println("time1.compareTo(time3)? " + time1.compareTo(time3) +"\n");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void processTime(String name, Time time){
        System.out.println("\n" + time.toString());
        System.out.println("\nElapsed seconds in " + name + ": " + time.getSeconds() + "\n");
        
    }
}

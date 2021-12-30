import java.io.*;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.List;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;


public class CharacterCount {
    static final Scanner in = new Scanner(System.in);
    static final char[] lower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static final char[] upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static void main(String[] args) {

        while(true){
            try{
                File file = new File(fileInput());
                List<String> lines = Files.readAllLines(file.toPath()); // Creates a list of lines in the file
                // Iterate through each letter in the alphabet.
                // I'm not that upset about hardcoding 26 here since the number of letters in the alphabet will never change
                for(int i = 0; i < 26; i++){
                    long upperCount = 0;
                    long lowerCount = 0;
                    // Set the current letters of both cases because the predicate didn't like the array[i]
                    char currentLower = lower[i];
                    char currentUpper = upper[i];
                    //Iterate over the List of lines from the file
                    for(int n = 0; n < lines.size(); n++){
                        
                        lowerCount += lines.get(n).chars()
                                                    .filter(ch -> ch == currentLower)
                                                    .count();
                        upperCount += lines.get(n).chars()
                                                    .filter(ch -> ch == currentUpper)
                                                    .count();
                    }
                    System.out.println("Number of " + currentUpper +"'s: " + upperCount);
                    System.out.println("Number of " + currentLower +"'s: " + lowerCount);
                }
                break;
            }catch(NoSuchFileException e){
                System.out.println("Please enter the name of a file that exists.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }

    public static String fileInput(){
        while(true){
            try{
                System.out.print("Enter a filename: ");
                String input = in.next();
                in.nextLine();
                return input;
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid String for the filename.");
            }catch(Exception e){
                System.out.println("Error: " + e);
                System.exit(1);
            }
            
        }
    }
}

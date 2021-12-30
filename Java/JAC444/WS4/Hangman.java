import java.io.*;
import java.util.ArrayList;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Hangman {
    static final File file = new File("hangman.txt");
    static final int MAX_INCORRECT_GUESSES = 6;
    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        boolean replay = true;
        while(replay){
            newGame();
            String response = replayInput();
            if(response.matches("[n,N]"))
                replay = false;
        }

    }

    public static void newGame() throws IOException {
        String word = getRandomWord(file);
        ArrayList<String> guesses = new ArrayList<String>();
        int incorrectGuesses = 0;
        int correctGuesses = 0;

        boolean guessing = true;
        // Draw the blank hangman for the start of the game
        draw(incorrectGuesses);
        while(guessing){
            // Create a string to produce a hidden version of the word
            String hidden = "";
            for(int i = 0; i < word.length(); i++){ // Iterate through the characters of the selected word
                boolean match = false;
                Character currentChar = word.charAt(i);
                for(int n = 0; n < guesses.size(); n++){ // Iterate through each previous guess
                    if( guesses.get(n).equals(currentChar.toString()) )
                        match = true; // Found a match in the guesses for this letter of the secret word
                }
                if(match){
                    hidden += currentChar.toString(); // Reveal the letter in the version of the word we're printing
                }
                else if(!match){
                    hidden += "*"; // Keep this character secret
                }
            }

            // Set the newest guess with my method that validates user input for a proper guess
            String lastGuess = guessInput(hidden);
            // Check the new guess against the array of past guesses
            boolean guessed = false; 
            for(String guess: guesses){
                if(lastGuess.equals(guess))
                    guessed = true;
            }

            // Already guessed and correct
            if(guessed == true && word.contains(lastGuess))
                System.out.println(lastGuess + " is already in the word.");
            // Already guessed and wrong
            else if(guessed == true)
                System.out.println("Already guessed '" + lastGuess + "' try a new letter.");
            // If the new guess really is new we check if it's correct or not
            else if(guessed == false){
                guesses.add(lastGuess);
                // New guess and wrong
                if(!word.contains(lastGuess)){
                    incorrectGuesses++;
                    draw(incorrectGuesses);
                    System.out.println("'" + lastGuess + "'" + " is not in the word.");
                // New guess and correct
                }else if(word.contains(lastGuess)){
                    correctGuesses++;
                }
            }

            // If we've assembled the hangman It's time to stop guessing
            if(incorrectGuesses >= MAX_INCORRECT_GUESSES){
                System.out.println("Failure...\nThe word is " + word + ". You missed " + incorrectGuesses + " times.");
                guessing = false;
            // If the count of correct guesses is equal to the amount of unique characters in the word
            // you can assume we've guessed the entire word, also time to stop guessing
            } else if(correctGuesses == word.chars().distinct().count()){ // Big thanks to Java 8 here, this was almost going to be the most annoying thing about Hangman
                System.out.println("Success!\nThe word is " + word + ". You missed " + incorrectGuesses + " times.");
                addWord(file);
                guessing = false;
            }

        }
    }

    public static String getRandomWord(File file) throws IOException {
        String word = "";
        int lines = 0;
        
        try( BufferedReader reader = new BufferedReader(new FileReader(file)) ){
            //Count the lines in the file
            while (reader.readLine() != null) lines++;

            //Roll a random number within the bounds of the number of lines, then read that line
            int lineToRead = (int)(Math.random()*lines);
            word = Files.readAllLines(file.toPath()).get(lineToRead);
        
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
        return word;
    } 

    public static void addWord(File file) throws IOException {

        while(true){
            System.out.print("Enter a new Halloween-themed word to be added in the memory (enter 'n' to opt out): ");
            try( BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)) ){
                String input;
                input = in.next().toLowerCase(); // Store the first word and converts it to lowercase
                in.nextLine(); // Clear remaining input
                List<String> fileWords = Files.readAllLines(file.toPath()); // Creates a list of words in the file
                
                boolean copy = false;
                // Mostly for testing purposes, sometimes I wanted to continue testing but i was adding too many words
                if(input.matches("[n,N]"))
                    break;

                for(String word: fileWords){
                    if(word.equals(input))
                        copy = true;
                }

                // Checking for duplicates
                if(!copy){
                    writer.newLine(); // Add a new line to fill
                    writer.append(input); // Append the input to the file
                    break;
                }else if(copy){
                    System.out.println("The word '"+ input + "' is already in the file!");
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
                System.exit(1);
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }catch(InputMismatchException e){
                System.out.println("Please enter one Halloween-themed word!");
            }catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static String replayInput(){
        String input;

        while(true){
            System.out.print("Do you want to guess another word? Enter y or n: ");
            try{
                input = in.nextLine();
                // Same checking as the guess input, single character but this time only y/n
                // I don't bother converting these to lowercase because they are only used once
                // and it's easy enough to validate [n,N] on the exit check
                if(input.matches("[y,Y,n,N]{1}")){
                    return input;
                }else{
                    System.out.println("Please enter either y or n.");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter either y or n.");
            }catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static String guessInput(String hiddenWord){
        String input;
        
        while(true){
            System.out.print("(Guess) Enter a letter in this word < " + hiddenWord + " > : ");
            try{
                input = in.nextLine();
                // Checking to make sure the input is a single character in the alphabet, includes uppercase
                if(input.matches("[A-Za-z]{1}")){
                    // Convert to lowercase to keep things standard, all guesses are lowercase and checked against lowercase only words
                    return input.toLowerCase();
                }else{
                    System.out.println("Please enter a single character between A-Z.");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a single character between A-Z.");
            }catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    // Not required and not particularly elegant, but I wanted it anyway
    // I'm sure I could write a loop to do this dynamically but I don't have that kinda time
    public static void draw(int incorrectGuesses){
        switch(incorrectGuesses){
            case 0:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 1:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 2:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|           2    ");
                System.out.println("|           2    ");
                System.out.println("|           2    ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 3:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|          32    ");
                System.out.println("|         3 2    ");
                System.out.println("|        3  2    ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 4:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|          324   ");
                System.out.println("|         3 2 4  ");
                System.out.println("|        3  2  4 ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 5:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|          324   ");
                System.out.println("|         3 2 4  ");
                System.out.println("|        3  2  4 ");
                System.out.println("|          5     ");
                System.out.println("|         5      ");
                System.out.println("|        5       ");
                System.out.println("|       5        ");
                System.out.println("|                ");
                System.out.println();
                break;
            case 6:
                System.out.println();
                System.out.println(" ___________     "); 
                System.out.println("|           |    ");
                System.out.println("|           1    ");
                System.out.println("|          1 1   ");
                System.out.println("|           1    ");
                System.out.println("|          324   ");
                System.out.println("|         3 2 4  ");
                System.out.println("|        3  2  4 ");
                System.out.println("|          5 6   ");
                System.out.println("|         5   6  ");
                System.out.println("|        5     6 ");
                System.out.println("|       5       6");
                System.out.println("|                ");
                System.out.println();
                break;
        }
        
    }

}
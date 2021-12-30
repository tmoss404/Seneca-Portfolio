import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeSet;

public class Babynames {
    public static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
        System.out.println();
        while(true){
            try{
                System.out.print("Enter a file name for baby name ranking: ");
                File f = new File("Babynames files/" + in.next()); in.nextLine();
                
                ArrayList<String> data = openAndRead(f);
                if(data != null){
                    process(data);
                }else if(data == null){
                    System.out.println("Something went wrong and the file was empty!");
                }

                while(true){
                    System.out.print("Would you like to check another file? (y/n): ");
                    String more = in.next(); in.nextLine();
                    if(more.matches("[n,N]")){
                        System.exit(0);
                    }else if(more.matches("[y,Y]")){
                        System.out.println();
                        break;
                    }else if(!more.matches("[y,Y]") & !more.matches("[n,N]")){
                        System.out.println("Please enter either y or n.\n");
                    }
                }
                
            }catch(FileNotFoundException e){
                System.out.println("File does not exist, please try again.\n");
            }catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        
    }

    public static ArrayList<String> openAndRead(File file) throws FileNotFoundException, IOException {
        try( BufferedReader in = new BufferedReader(new FileReader(file)); ){
            ArrayList<String> fileData = new ArrayList<String>();

            String line;
            while((line = in.readLine()) != null){
                fileData.add(line);
            }

            return fileData;

        }catch(FileNotFoundException e){
            throw e;
        }catch(IOException e){
            throw e;
        }
            
    }

    public static void process(ArrayList<String> data){
        Collection<String> names = new TreeSet<String>();
        Collection<String> girlNames = new TreeSet<String>();
        Collection<String> boyNames = new TreeSet<String>();

        //------ Processing for part 1 ------
        for(String line: data){
            String[] s = line.split("\\s+");
            boyNames.add(s[1]); 
            girlNames.add(s[3]);
        }

        for(String name: boyNames) 
            if(girlNames.contains(name)) names.add(name);

        System.out.println();
        System.out.println(names.size() + " names used for both genders");
        
        int i = 0;
        System.out.println("They are:");
        for(String name: names){
            if(i%15 == 0  & i != 0)
                System.out.print("\n"); 
            System.out.print(name + " ");
            i++;
        }
        System.out.print("\n\n");
        
        //------ Processing for part 2 | Remove duplicates ------
        for(String name: names){
            boyNames.remove(name);
            girlNames.remove(name);
        }

        //------ Processing for part 2 | Boy names ------
        int x = 0;
        System.out.println("Boy Names:");
        for(String name: boyNames){
            if(x%22 == 0  & x != 0)
                System.out.print("\n"); 
            System.out.print(name + " ");
            x++;
        }
        System.out.print("\n\n");

        //------ Processing for part 2 | Girl names ------
        int g = 0;
        System.out.println("Girl names:");
        for(String name: girlNames){
            if(g%22 == 0 & g != 0)
                System.out.print("\n"); 
            System.out.print(name + " ");
            g++;
        }
        System.out.print("\n\n");
        
    }
}

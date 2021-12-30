import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Countries {
    public static Map<String, String> countryMap = new HashMap<>();
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println();
        buildMap();
        capital();
    }

    public static void buildMap(){
        countryMap.put("Canada", "Ottawa");
        countryMap.put("Armenia", "Yerevan");
        countryMap.put("Belgium", "Brussels");
        countryMap.put("Denmark", "Copenhagen");
        countryMap.put("Fiji", "Suva");
        countryMap.put("Germany", "Berlin");
        countryMap.put("Hungary", "Budapest");
        countryMap.put("Iceland", "Reykjavik");
        countryMap.put("Jamaica", "Kingston");
        countryMap.put("Kenya", "Nairobi");
        countryMap.put("Latvia", "Riga");
        countryMap.put("Madagascar", "Antananarivo");
        countryMap.put("Nicaragua", "Managua");
        countryMap.put("Oman", "Muscat");
        countryMap.put("Peru", "Lima");
        countryMap.put("Qatar", "Doha");
        countryMap.put("Spain", "Madrid");
        countryMap.put("Turkey", "Ankara");
        countryMap.put("Ukraine", "Kiev");
        countryMap.put("Yemen", "Sana'a");
        countryMap.put("Zambia", "Lusaka");
        countryMap.put("Australia", "Canberra");
        countryMap.put("Bolivia", "Sucre");
        countryMap.put("Chad", "N'djamena");
        countryMap.put("Dominica", "Roseau");
    }

    public static void capital(){
        
        while(true){
            System.out.print("Enter a country: ");
            String country = in.next(); in.nextLine();
            System.out.println(
                countryMap.get(caseCorrect(country)) == null 
                ? "Requested country is missing.\n" 
                : "Capital of " + caseCorrect(country) + " is: " + countryMap.get(caseCorrect(country)) + "\n"
            ); 
            
            while(true){

                System.out.print("Would you like to check another country? (y/n): ");
                String more = in.next(); in.nextLine();
                if(more.matches("[n,N]")){
                    System.exit(0);
                }else if(more.matches("[y,Y]")){
                    System.out.println();
                    break;
                }else{
                    System.out.println("Please enter either y or n.\n");
                }
            }
        }
    }

    public static String caseCorrect(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}



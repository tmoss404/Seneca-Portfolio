package ca.jac555;

import java.util.Stack;

public class MyString {

    private char[] chars;
    
    public MyString(char[] chars){
        this.chars = chars;
    }

    public void printMyString(){

        if(chars != null)
            System.out.println(this.chars);
        else
            System.out.println("null");

    }

    public int length(){
        return chars.length;
    }

    public char charAt(int index){

        // Check for invalid indexes
        if (index < 0 || index >= chars.length)
            throw new IndexOutOfBoundsException(index);
        else
            return chars[index];

    }

    public MyString substring(int beginIndex , int endIndex){

        // Check for index bounds errors
        if (beginIndex < 0 || beginIndex > endIndex || endIndex > chars.length)
            throw new IndexOutOfBoundsException("begin " + beginIndex + ", end " + endIndex + ", length " + chars.length);

        // If they want to substring the whole string, return the string
        if (beginIndex == 0 && endIndex == chars.length)
            return this;

        // Calculate the size of the new char array
        int size = endIndex - beginIndex;
        char[] sub = new char[size];

        int x = 0;
        for(int i = beginIndex; i < endIndex; i++){
            sub[x] = chars[i];
            x++;
        }
        
        return new MyString(sub);

    }

    public MyString toLowerCase(){

        char[] lower = new char[length()];

        for(int i = 0; i < length(); i++)
            lower[i] = Character.toLowerCase(chars[i]);
        
        return new MyString(lower);

    }

    
    public boolean equals(MyString s){   

        // Not equal if it's null
        if(s == null)
            return false;
        
        // Not equal if one is larger than the other
        if(s.length() != this.length())
            return false;
        
        // Compare each char, if any are different auto return false
        for(int i = 0; i < this.length(); i++){
            if(this.charAt(i) != s.charAt(i))
                return false;
        }
        
        return true;

    }


    public static MyString valueOf(int i){
        
        // Store the int because the modulus operator will ruin it
        int tmp = i;
        
        // int zero is the only int the modulus can't handle how I need it to
        if (i == 0)
            return new MyString(new char[] {'0'} );

        Stack<Integer> nums = new Stack<Integer>();

        // Pull the final digit out one by one and push it to the stack
        for (; tmp != 0; tmp /= 10)
            nums.push(tmp % 10);

        // Record how many digits were found
        int size = nums.size();

        // Check if the number was negative
        if(i < 0){
            // Create a char array that accommodates a leading '-'
            char[] chars = new char[size+1];
            chars[0] = '-';
            for(int x = 1; x < size+1; x++){
                // Pop each digit out of the stack, converting it into char, and adding to an array
                int num = Math.abs(nums.pop().intValue());
                chars[x] = (char)(num+'0');
            }

            return new MyString(chars);
        }else{
            char[] chars = new char[size];
            for(int x = 0; x < size; x++){
                // Pop each digit out of the stack, converting it into char, and adding to an array
                int num = Math.abs(nums.pop().intValue());
                chars[x] = (char)(num+'0');
            }

            return new MyString(chars);
        }

    }

}
package ca.jac555;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;


public class StringTest {

    @Test
    public void lengthTest(){
        char[] chars = {'1', '2', '3', '4', '5', '6'};
        MyString lengthString = new MyString(chars);
        assertEquals(6, lengthString.length());
    }

    @Test
    public void subStringTest(){
        char[] chars = {'O', 'O', 'X', 'X', 'X', 'O', 'O'};
        MyString string = new MyString(chars);
        MyString subString = string.substring(2, 5);

        subString.printMyString();
    }

    @Test
    public void lowerCaseTest(){
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        MyString upperString = new MyString(chars);
        MyString lowerString = upperString.toLowerCase();

        upperString.printMyString();
        lowerString.printMyString();

    }

    @Test
    public void equalsTest(){
        char[] chars = {'X', 'X', 'X', 'O', 'X', 'X', 'X'};
        MyString string = new MyString(chars);

        MyString equalString = string;
        MyString notEqualString = new MyString(new char[] {'X', 'X', 'X', 'X', 'X', 'X', 'X'});

        assertTrue(string.equals(equalString));
        assertFalse(string.equals(notEqualString));

    }

    @Test
    public void charAtTest(){
        char[] chars = {'X', 'X', 'X', 'O', 'X', 'X', 'X'};
        MyString charAtString = new MyString(chars);
        assertEquals('O', charAtString.charAt(3));
    }

    @Test
    public void valueOfTest(){

        MyString minIntString = MyString.valueOf(Integer.MIN_VALUE);
        MyString maxIntString = MyString.valueOf(Integer.MAX_VALUE);
        MyString zeroIntString = MyString.valueOf(0);

        minIntString.printMyString();
        maxIntString.printMyString();
        zeroIntString.printMyString();

    }
}
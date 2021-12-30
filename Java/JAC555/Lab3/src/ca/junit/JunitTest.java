package ca.junit;

import org.junit.Assert;
import org.junit.Test;
    
public class JunitTest {
    public String TestString = "Test";

    @Test
    public void lengthTest() {
        Assert.assertEquals("Length test failed.", 4, TestString.length());
    }

    @Test
    public void charAtTest() {
        Assert.assertEquals("CharAt test failed.", 'T', TestString.charAt(0));
    }

    @Test
    public void substringTest() {
        Assert.assertEquals("SubString test failed.", "es", TestString.substring(1, 3));
    }

    @Test
    public void indexOfTest() {
        Assert.assertEquals("IndexOf test failed.", 3, TestString.indexOf("t"));
    }
}
    
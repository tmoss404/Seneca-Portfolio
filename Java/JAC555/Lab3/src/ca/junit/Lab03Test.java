package ca.junit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;   
    
public class Lab03Test {

    @Test
	public void part1Test(){
		Assert.assertTrue("minInteger test failed.", Lab03.minInteger() == -2147483648);
		Assert.assertTrue("maximumDouble test failed.", Lab03.maximumDouble() ==  4.94065645841247E-324);
	}

    @Test
    public void part2Test(){
		int sigma = 3;
        double x = 2.5;

        Assert.assertTrue("Rayleigh test failed", Lab03.rayleigh(x, sigma) == 0.19629118829381006);
	}

    @Test
    public void part3Test(){
        List<Integer> test = new ArrayList<Integer>();

        test.add(1);
        test.add(2);
        test.add(3);
        
        Lab03.sort(test);

        Assert.assertTrue("Sort test failed.", (test.get(0) > test.get(1) && test.get(1) > test.get(2)));
    }

    @Test
    public void part4Test(){
		List<String> test = new ArrayList<String>();

        test.add("test");
        test.add("test");
        test.add("test");
        test.add("bad");
        test.add("test");

        Assert.assertTrue("Frequency test failed", Lab03.frequency(test, "test") == 4);
       
	}

}
    
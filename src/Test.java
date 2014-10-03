import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

import javax.measure.*;
import org.jscience.mathematics.number.*;

public class Test {
	
	 // Global varibales

	static double arg1 = 0.1;
	static double arg2 = 0.2;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		/*
		Integer64 integer64 = Integer64.valueOf(14324);
		System.out.println(integer64.opposite());
		System.out.println(integer64.plus(23));
		
		Real two = Real.valueOf(2); // 2.0000..00 
		Real three = Real.valueOf(3);
	    Real.setExactPrecision(100); // Assumes 100 exact digits for exact numbers.

	    System.out.println("2/3       = " + two.divide(three));
	    Real sqrt2 = two.sqrt();
	    System.out.println("sqrt(2)   = " + sqrt2);
	    System.out.println("Precision = " + sqrt2.getPrecision() + " digits.");
		
		
		System.out.println("binary a");
		String a = scanner.nextLine();
		System.out.println("binary b");
		String b = scanner.nextLine();
		int numA = Integer.parseInt(a, 2);
		int numB = Integer.parseInt(b, 2);
		int res = numA + numB;
		
		System.out.println(" " + a + "\n" + "+" + b + "\n" + Integer.toBinaryString(res));
		
		String s = "11011101 10101010 0010101 00010101".replace( " ", "" );

		byte[] bs = new BigInteger( s, 2 ).toByteArray(); // [158,261,69,69]

		for ( byte by : bs )
		  System.out.println( Integer.toBinaryString(by & 0xFF) );
		
	    int longer = Integer.MAX_VALUE+1;
	    System.out.println("MAX_VALUE + 1: " + longer + " Bin: " + Integer.toBinaryString(longer));
	    
		BigInteger c = new BigInteger("1123");
		BigInteger d = new BigInteger("232");
		BigInteger e = c.add(d);
		System.out.println(e + "\nBitcount: " + e.bitCount() + "\nBitlength: " + e.bitLength() + "\nBin: " + e.toString(2) );
		
		BigInteger big1 = new BigInteger("9999");
		BigInteger big2 = big1.add(new BigInteger("2147483647"));
		System.out.println(big2);
		
		
		int i = -1 - 3;
		int i1 = 0 - 1;
		System.out.println(i + " " + i1);
		
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
		
		twoComp(55);
		
		String [] str = {"+"}; 
		textMatch(str[0]);
		*/
		double f = Math.pow(2, 31) ;
		int x = 2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2;
		System.out.println("double: " + f + " int: " + (int) f);
		System.out.println(Integer.MAX_VALUE);
		System.out.println(x);
		
		System.out.println(integerPow(2,31)-1);
		
		System.out.println(2 / 3);
	}
	
	public static int integerPow(int b, int e) {
		int a = 1;
		if (e == 0) {
			a = 0;
		}
		else if (e == 1) {
			a = b;
		}
		
		for (int i = 0; i < e; i++) {
			a *= b;
		}
		
		return a;	
		
	}
	
	public static String twoComp(int i) {
		int k = 2147483647;
		String output;
		//long k = 2147483648l;
		if (i >= k) {
			i -= 2 * k;
		}
		output = String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
		System.out.println(output);
		return output;
	}
	
	public static void textMatch(String x) {
		if (x == "+") {
			System.out.println("ein plus zeichen");
		}
		else {
			System.out.println("etwas anderes");
		}
		
	}

}

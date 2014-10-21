

import java.math.BigDecimal;
import java.math.MathContext;
import org.nevec.rjm.*;


public class BigDecimalMathTest {

	
	public static void main(String[] args) {
		
		System.out.println(BigDecimalMath.sin(new BigDecimal("0.67678681")));
		System.out.println(BigDecimalMath.cos(new BigDecimal("0.4")));
		System.out.println(BigDecimalMath.pi(new MathContext(100)));
		System.out.println(BigDecimalMath.pow(new BigDecimal("2"), new BigDecimal("-2")));
		System.out.println(Math.PI);
		System.out.println(Math.E);
		double d = Math.E;
		float fPI = (float) d;
		System.out.println("double PI: " + d);
		System.out.println("float PI: " + fPI);
	}
}


import java.math.BigDecimal;
import java.math.MathContext;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.number.Real;
import org.nevec.rjm.*;


public class BigDecimalMathTest {

	
	public static void main(String[] args) {
		
		System.out.println(BigDecimalMath.sin(new BigDecimal("0.67678681")));
		System.out.println(BigDecimalMath.cos(new BigDecimal("0.4")));
		System.out.println(BigDecimalMath.pi(new MathContext(100)));
		System.out.println(Math.PI);
		System.out.println(Math.E);
		double d = Math.E;
		float fPI = (float) d;
		System.out.println("double PI: " + d);
		System.out.println("float PI: " + fPI);
		// e^(b*ln(a))
		BigDecimal bigDecPow = BigDecimalMath.pow(new BigDecimal("1.21"), new BigDecimal("2.5"));		
		System.out.println(bigDecPow);
		float base = 1.21f;
		float exp = 2.5f;
		float floatPow = (float) Math.pow(Math.E,exp*Math.log(base));
		System.out.println(floatPow);
		// e^(exp*ln(base)
		BigDecimal b = new BigDecimal("1.21");
		BigDecimal e = new BigDecimal("2.5");
		BigDecimal euler = new BigDecimal("2.71828182845904");
		BigDecimal tmp = BigDecimalMath.log(b);
		tmp = e.multiply(tmp);
		
		BigDecimal pow = BigDecimalMath.pow(euler, tmp);
		System.out.println("einzeln: " + pow);
		System.out.println("zusammen: " + BigDecimalMath.pow(b, e));
		System.out.println("-------------------");
		
		Float64 f = Float64.valueOf(1.21);
		System.out.println(f.pow(Float64.valueOf(2.5)));
		
		System.out.println(Math.pow(1.21, 2.5));
		

	}
}

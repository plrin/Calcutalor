import java.math.BigDecimal;


public class DezimalTest {

	public static void main(String[] args) {

		BigDecimal a = new BigDecimal("23.17238423");
		int b = a.scale();
		System.out.println(b);
		System.out.println(a.unscaledValue());
		System.out.println(new BigDecimal(1).divide(new BigDecimal(3), 100, BigDecimal.ROUND_DOWN));
		
		double d = 0.3 - 0.1;
		System.out.println("0.3 - 0.1 = " + d + "(double)");
		
		System.out.println("0.3 - 0.1 = " + new BigDecimal("0.3").subtract(new BigDecimal("0.1")) + "(BigDecimal)");
	}

}

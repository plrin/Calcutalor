package subsift;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class ArithmeticTools {

    private static BigDecimal TWO = new BigDecimal(2);
    private static BigDecimal TEN = new BigDecimal(10);
    private static BigDecimal LOGTEN = new BigDecimal(Math.log(10));
    private static BigDecimal LNTWO = new BigDecimal(Math.log(2));
    private static BigDecimal MAXSIMPLE = new BigDecimal(Double.MAX_VALUE);
    
    public static BigDecimal log(BigDecimal v, double base, MathContext mc) {
    	return log(v).divide(log(new BigDecimal(base)), mc);
    }
    
    /**
     * Method which calculates natural logarithm for BigDecimal. 
     * If number is smaller than Double.MAX_VALUE, then Math.log is used, 
     * otherwise BigDecimals with v.divide(TWO).add(LNTWO).
     * @param v
     * @return
     */
    public static BigDecimal log(BigDecimal v) {
        if(v.compareTo(MAXSIMPLE) > 0) {
            return v.divide(TWO).add(LNTWO);
        } else {
            return new BigDecimal(Math.log(v.doubleValue()));
        }
    }
    
    /**
     * Method which calculates natural logarithm for BigInteger.
     * BigDecimals are used.
     * @param v
     * @return
     */
    public static BigInteger log(BigInteger v) {
        return log(new BigDecimal(v)).toBigInteger();
    }
    
    /**
     * Method which calculates logarithm base2 for BigDecimal.
     * If number is smaller than Double.MAX_VALUE, then Math.log(x)/Math.log(2) is used, 
     * otherwise BigDecimal with log(v).divide(log(LNTWO)).
     */
    public static BigDecimal log2(BigDecimal v){
        if(v.compareTo(MAXSIMPLE) > 0) {
            return log(v).divide(log(LNTWO));
        } else {
            return new BigDecimal(Math.log(v.doubleValue()) / Math.log(2));
        }
    }
    
    /**
     * Method which calculates logarithm base2 for BigInteger using BigDecimal.
     */
    public static BigInteger log2(BigInteger v){
    	return log2(new BigDecimal(v)).toBigInteger();
    }
    
    /**
     * Method which calculates logarithm base10 for BigDecimal.
     * If number is smaller than Double.MAX_VALUE, then Math.log is used, 
     * otherwise BigDecimal with v.divide(TEN).add(LOGTEN).
     * @param v
     * @return
     */
    public static BigDecimal log10(BigDecimal v) {
        if(v.compareTo(MAXSIMPLE) > 0) {
            return v.divide(TEN).add(LOGTEN);
        } else {
            return new BigDecimal(Math.log10(v.doubleValue()));
        }
    }
    
    /**
     * Method which calculates logarithm base10 for BigInteger using BigDecimal.
     * @param v
     * @return
     */
    public static BigInteger log10(BigInteger v) {
        return new BigInteger(Integer.toString(v.toString().length() - 1));
    }

	public static BigDecimal sum(BigDecimal val1, BigDecimal val2){//, int scale, RoundingMode mode){
		BigDecimal sum = val1.add(val2);
		//return rescale(sum, scale, mode);
		return sum;
	}
	
	public static BigDecimal subtract(BigDecimal val1, BigDecimal val2){//, int scale, RoundingMode mode){
		BigDecimal diff = val1.subtract(val2);
		//return rescale(diff, scale, mode);
		return diff;
	}

	public static BigDecimal divide(BigDecimal val1, BigDecimal val2, int scale, RoundingMode mode){
		BigDecimal quota = val1.divide(val2, scale, mode);
		return rescale(quota, scale, mode);
	}

	public static BigDecimal multiply(BigDecimal val1, BigDecimal val2){//, int scale, RoundingMode mode){
		BigDecimal product = val1.multiply(val2);
		//return rescale(product, scale, mode);
		return product;
	}
	
	public static BigDecimal pow(BigDecimal base, BigDecimal exponent){//, int scale, RoundingMode mode){
		BigDecimal b = base;
		BigDecimal pow = b.pow(exponent.intValue());
		//return rescale(pow, scale, mode);
		return pow;
	}

	public static BigDecimal pow(double base, BigDecimal exponent, MathContext mc){//, int scale, RoundingMode mode){
		double dPow = Math.pow(base, exponent.doubleValue());
		BigDecimal pow = new BigDecimal(dPow, mc);
		//return rescale(pow, scale, mode);
		return pow;
	}

	public static BigDecimal exp(BigDecimal exponent){//, int scale, RoundingMode mode){
		return new BigDecimal(Math.pow(Math.E, exponent.doubleValue()));
	}

	public static BigDecimal rescale(BigDecimal n, int scale, RoundingMode mode){
		return (n.setScale(scale, mode.ordinal()));
	}
	
	public static BigDecimal logSum(BigDecimal p_log, BigDecimal q_log, double base){
		double p_l = Math.max(p_log.doubleValue(), q_log.doubleValue());
		double q_l = Math.min(p_log.doubleValue(), q_log.doubleValue());
		double inner = 1 + Math.pow(base, (q_l - p_l));
		double r_l = p_l + (Math.log(inner) / Math.log(base));
		return new BigDecimal(r_l);
	}
	
	
	
	// TESTING
	
	public static void main(String[] args){
		// log sum
		System.out.println("log sum: " + 1 + " + " + 10 + " = " + (0+10));
		System.out.println("log sum: " + "Math.log(0 + 10)                                          = " + Math.log(10));
		System.out.println("log sum: " + "logSum(log(BigDecimal.ONE), log(BigDecimal.TEN, Math.E))  = " + logSum(new BigDecimal(Double.NaN), log(BigDecimal.TEN), Math.E));
	}

	public static void main2(String[] args){
		
		// log 2
		System.out.println("log2: " + 10 + ": " + Math.log(10) / Math.log(2));
		System.out.println("log2: " + BigDecimal.TEN + ": " + log2(BigDecimal.TEN));
		System.out.println();
		System.out.println("log: " + 10 + ": " + Math.log(10));
		System.out.println("log: " + BigDecimal.TEN + ": " + log(BigDecimal.TEN));
		System.out.println();
		System.out.println("log: " + 10 + ": " + Math.log10(10));
		System.out.println("log: " + BigDecimal.TEN + ": " + log10(BigDecimal.TEN));
		System.out.println();
		System.out.println("log: " + 10 + ": " + Math.log10(10));
		System.out.println("log(v, base): " + BigDecimal.TEN + ": " + log(BigDecimal.TEN, 10.0, new MathContext(10)));
		System.out.println();
		
		// exponent
		System.out.println("exp: " + 10 + ": " + Math.exp(10));
		System.out.println("exp: " + BigDecimal.TEN + ": " + exp(BigDecimal.TEN));
		System.out.println();
		System.out.println("pow 10: " + 10 + ": " + Math.pow(10,10));
		System.out.println("pow 10: " + BigDecimal.TEN + ": " + pow(BigDecimal.TEN, BigDecimal.TEN));
		System.out.println();
		
		// log sum
		System.out.println("log sum: " + 1 + " + " + 10 + " = " + (1+10));
		System.out.println("log sum: " + "Math.log(1 + 10)                                          = " + Math.log(1+10));
		System.out.println("log sum: " + "logSum(log(BigDecimal.ONE), log(BigDecimal.TEN, Math.E))  = " + logSum(log(BigDecimal.ONE), log(BigDecimal.TEN), Math.E));
	}
	public static void main1(String[] args){
    	double no = Double.MAX_VALUE;
    	BigDecimal bd = new BigDecimal(no);
    	BigDecimal bi = new BigDecimal(no);
    	System.out.println(bd.doubleValue() + ": " + log10(bd));
    	System.out.println(bi.doubleValue() + ": " + log10(bi));
    	System.out.println(no + ": " + Math.log10(no));
    }


}

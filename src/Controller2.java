import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JOptionPane;

import org.nevec.rjm.BigDecimalMath;

public class Controller2 implements ActionListener {
	
	// instance variables
	private View2 view;
	private String comboBox;
	private String thermString = "";
	private String compString = "";
	private String realString = "";
	
	
	public Controller2() {
		this.view = new View2(this);
		
	}
	
	// button action on click
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			comboBox = getComboBox();

			String realOutput = "";
			String compOutput = "";
			thermString = "";
			compString = "";
			realString = "";
			
			if (comboBox.equals("Integer")) {
				try {
					realOutput = String.valueOf(bigIntegerRPN());
					compOutput = String.valueOf(integerRPN());
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Eingabe ueberschreitet Zahlenbereich\n"
							+ "[-2^31 = -2.147.483.648 bis 2^31-1 = 2.147.483.647].\n" + exc );
					realOutput = "error";
					compOutput = "error";
				}	
			}
			else if (comboBox.equals("Float")) {
				realOutput = String.valueOf(bigDecimalRPN());
				compOutput = String.valueOf(floatRPN());
				/*
				try {
					realOutput = String.valueOf(bigDecimalRPN());
					compOutput = String.valueOf(floatRPN());
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Eingabe ueberschreitet Zahlenbereich"
							+ "[x nachkommastellen].\n" + exc );
					
					realOutput = "error";
					compOutput = "error";
				}*/		
			}
			else {
				JOptionPane.showMessageDialog(null, "Combobox Fehler.");
			}
		
		view.realOutput.setText(realOutput);
		view.compOutput.setText(compOutput);
		view.thermTextArea.setText(printTherm());
		view.realTextPane.setText(printReal());
		view.compTextPane.setText(printComp());
	}
		


	/* start of methods */
	
	/* 
	 * calculation of Integer and Big Integer 
	 */
		
	/* computer arithmetic */
	public String integerRPN() {
		String string = getInput();
		String tk;
		Stack<Integer> st = new Stack<Integer>(); 
		// a and b are stack numbers to pop, x input/result number to push
		int a = 0, b = 0, x = 0, i; 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; i++) {
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = Integer.parseInt(tk);
			}
			else {
				if (tk.matches("^for{1}\\d+$")) {
					int loops = Integer.valueOf(tk.replace("for", ""));
					tk = splitArray[i+1]; // take the token behind "for" from the array
					a = st.pop(); // pop the numbers
					b = st.pop();
					x = evalInt(b,a,tk);
					saveCompStringInteger(a,b,x,tk);
					for (int j = 0; j < loops; j++) {
						int tmp = x;
						x = evalInt(x,a,tk);
						saveCompStringInteger(a,tmp,x,tk);
					}
					i++;
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalInt(b,a,tk);
					saveCompStringInteger(a,b,x,tk);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
			}
			st.push(x);
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	public static int evalInt(int b, int a, String tk) {
		int x = 0;
		if (tk.equals("+")) {
			x = b + a;	
		}
		else if (tk.equals("-")) {
			x = b - a;
		}
		else if (tk.equals("*")) {
			x = b * a;
		}
		else if (tk.equals("/")){
			x = b / a;
		}
		else if (tk.equals("^")) {
			x = (int) Math.pow(b, a);
		}
		else {
			JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
		}
		return x;
	}
	
	/* real/human arithmetic */
	public String bigIntegerRPN() {
		String string = getInput();
		String tk;
		Stack<BigInteger> st = new Stack<BigInteger>();
		int i; 
		boolean isNumber = true;
		// a and b are stack numbers to pop, x input/result number to push
		BigInteger a = new BigInteger("0"), b = new BigInteger("0") , x = new BigInteger("0"); 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; i++) {
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = new BigInteger(tk);
				isNumber = true;
			}
			else {
				if (tk.matches("^for{1}\\d+$")) {
					int loops = Integer.valueOf(tk.replace("for", ""));
					tk = splitArray[i+1]; // take the token next to "for" from the array
					a = st.pop(); // pop the numbers
					b = st.pop();
					x = evalBigInt(b,a,tk);
					saveRealStringInteger(a,b,x,tk);
					for (int j = 0; j < loops; j++) {
						BigInteger tmp = x;
						x = evalBigInt(x,a,tk);	
						saveRealStringInteger(a,tmp,x,tk);
					}
					i++;
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalBigInt(b,a,tk);
					saveRealStringInteger(a,b,x,tk);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
				isNumber = false;
			}
			st.push(x);
			// save operations into string
			String tmpTk = tk;
			tk = x.toString();
			String tmp = "";
			if(tk.length() < 8) {
				tmp = "\t";
			}
			
			if(isNumber == true) {
				thermString += tk + "\t\tpush(" + tk + ")\t" + tmp + st.toString() + "\n";  
			}
			else {
				thermString += tmpTk + "\t\t" + "pop(" + b + "," + a + ")\n\t\t" + b + tmpTk + 
						a + "\n\t\tpush(" + x + ")\t" + tmp + st.toString() + 
						"\t\t" + checkOverflow(x) + "\n";
			}
			// end of string save
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	public static BigInteger evalBigInt(BigInteger b, BigInteger a, String tk) {
		BigInteger x = new BigInteger("0");
		
		if (tk.equals("+")) {
			x = b.add(a);
		}
		else if (tk.equals("-")) {
			x = b.subtract(a);
		}
		else if (tk.equals("*")) {
				x = b.multiply(a);
		}
		else if (tk.equals("/")){
			x = b.divide(a);
		}
		else if (tk.equals("^")) {
			int an = a.intValue();
			x = b.pow(an);
		}
		else {
			JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
		}
		return x;
	}
		
	/* 
	 * end of Integer and Big Integer calculation 
	 */ 
	
	/*--------------------------------------------- */
	
	/* 
	 * start of float and big float calculation
	 */
	
	/* computer arithmetic */
	public String floatRPN() {
		String string = getInput();
		String tk;
		Stack<Float> st = new Stack<Float>(); 
		// a and b are stack numbers to pop, x input/result number to push
		float a = 0f, b = 0f, x = 0f;
		int i; 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; i++) {
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$") || tk.matches("pi|e")) {
				if (tk.equals("pi")) {
					x = 3.1415927f;
				}
				else if (tk.equals("e")) {
					x = 2.7182817f;
				}
				else {
					x = Float.parseFloat(tk);
				}	
			}
			// operate
			else {
				if (tk.matches("^for{1}\\d+$")) {
					int loops = Integer.valueOf(tk.replace("for", ""));
					tk = splitArray[i+1]; // take the token behind "for" from the array
					a = st.pop(); // pop the numbers
					b = st.pop();
					x = evalFloat(b,a,tk);
					saveCompStringFloat(a,b,x,tk);
					for (int j = 0; j < loops; j++) {
						float tmp = x;
						x = evalFloat(x,a,tk);
						saveCompStringFloat(a,tmp,x,tk);
					}
					i++;
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalFloat(b,a,tk);
					saveCompStringFloat(a,b,x,tk);
				}
				else if (tk.matches("sin|cos|tan")) {
					a = st.pop();
					b = 0;
					x = evalFloat(a,b,tk);
					saveCompStringFloat(a,b,x,tk);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
				
			}
			st.push(x);	
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	public static float evalFloat(float b, float a, String tk) {
		float x = 0;
		if (tk.equals("+")) {
			x = b + a;
		}
		else if (tk.equals("-")) {
			x = b - a;
		}
		else if (tk.equals("*")) {
				x = b * a;
		}
		else if (tk.equals("/")){
			x = b / a;
		}
		else if (tk.equals("^")) {
			x = floatPow(b,a);
		}
		else if (tk.equals("sin")) {
			x = (float) Math.sin(b);
		}
		else if (tk.equals("cos")) {
			x = (float) Math.cos(b);
		}
		else if (tk.equals("tan")) {
			x = (float) Math.tan(b);
		}		
		else {
			JOptionPane.showMessageDialog(null, "Rechenzeichen nicht gueltig.");
		}
		
		return x;
	}
	
	
	/* real/human arithmetic */
	public String bigDecimalRPN() {
		String string = getInput();
		String tk = "";
		Stack<BigDecimal> st = new Stack<BigDecimal>();
		int i; 
		boolean isNumber = true;
		// a and b are stack numbers to pop, x input/result number to push
		BigDecimal a = new BigDecimal("0"), b = new BigDecimal("0") , x = new BigDecimal("0"); 
		String [] splitArray = string.split("\\s");
	
		for (i = 0; i < splitArray.length; i++) {
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$") || tk.matches("pi|e")) {
				if (tk.equals("pi")) {
					x = BigDecimalMath.pi(new MathContext(14));
				}
				else if (tk.equals("e")) {
					x = new BigDecimal("2.71828182845904");
				}
				else {
					x = new BigDecimal(tk);
				}	
				isNumber = true;
			}	
			else {
				if (tk.matches("^for{1}\\d+$")) {
					int loops = Integer.valueOf(tk.replace("for", ""));
					tk = splitArray[i+1]; // take the token next to "for" from the array
					a = st.pop(); // pop the numbers
					b = st.pop();
					x = evalBigDec(b,a,tk);
					saveRealStringFloat(a,b,x,tk);
					for (int j = 0; j < loops; j++) {
						BigDecimal tmp = x;
						x = evalBigDec(x,a,tk);	
						saveRealStringFloat(a,tmp,x,tk);
					}
					i++;
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalBigDec(b,a,tk);
					saveRealStringFloat(a,b,x,tk);
				}
				else if (tk.matches("sin|cos|tan")) {
					a = st.pop();
					b = new BigDecimal("0");
					x = evalBigDec(a,b,tk);
					saveRealStringFloat(a,b,x,tk);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
				isNumber = false;
			}
			st.push(x);
			// save operations into string
			String tmpTk = tk;
			tk = x.toString();
			String tmp = "";
			if(tk.length() < 8) {
				tmp = "\t";
			}
			
			if(isNumber == true) {
				thermString += tk + "\t\tpush(" + tk + ")\t" + tmp + st.toString() + "\n";  
			}
			else {
				thermString += tmpTk + "\t\t" + "pop(" + b + "," + a + ")\n\t\t" + b + 
						tmpTk + a + "\n\t\tpush(" + x + ")\t" +
						tmp + st.toString() + "\t\t" + "" + "\n";
			}
			// end of string save
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	public static BigDecimal evalBigDec(BigDecimal b, BigDecimal a, String tk) {
		BigDecimal x = new BigDecimal("0");
		
		if (tk.equals("+")) {
			x = b.add(a);
		}
		else if (tk.equals("-")) {
			x = b.subtract(a);
		}
		else if (tk.equals("*")) {
				x = b.multiply(a);
		}
		else if (tk.equals("/")){
			x = b.divide(a);
		}
		else if (tk.equals("^")) {
			x = bigDecPow(b, a);
		}
		else if (tk.equals("sin")) {
			x = BigDecimalMath.sin(b);
		}
		else if (tk.equals("cos")) {
			x = BigDecimalMath.cos(b);
		}
		else if (tk.equals("tan")) {
			x = BigDecimalMath.tan(b);
		}
		else {
			JOptionPane.showMessageDialog(null, "Operator nicht gueltig");
		}
		
		return x;
	}
	
	/* end of float and big decimal calculation */
	
	/* --------------------------------------------- */
	
	/* functions used in/for the calculation of integer and float */
	
	
	public void focusInput () {
		view.frame.addWindowListener (new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				view.inputField.requestFocus();
			}
		});
	}
	
	
	/* get the number of loops which were entered before */
	public int getLoop() {
		String textField = view.loopField.getText();
		int numberOfLoops = 0;
		try {
			numberOfLoops = Integer.parseInt(textField);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Keine gueltige Nummer im Loop!\n" + e);
		}
		return numberOfLoops;
	}
	
	
	public String getInput() {
		return view.inputField.getText();
	}
	
	
	/* get the type of data type to calculate with */
	public String getComboBox() {
		comboBox = (String) view.numSystemComboBox.getSelectedItem();
		return comboBox;
	}
	
	
	/* exponential function for integer */
	public static int integerPow(int base, int exp) {
		int res = 1;
		if (exp == 0) {
			res = 0;
		}
		else if (exp == 1) {
			res = base;
		}
		// result would be decimal between 0 and 1
		else if (exp < 0) {
			res = 0;
		}
		for (int i = 0; i < exp; i++) {
			res *= base;
		}
		
		return res;
	}
	
	/* exponential function for float with non floating point exponent */
	public static float floatPow(float base, float exp) {
		float res = (float) Math.pow(Math.E, exp*Math.log(base));
		
		return res;
	}
	
	/* exponential function for big decimal */
	public static BigDecimal bigDecPow(BigDecimal base, BigDecimal exp) {
		/*BigDecimal res = new BigDecimal("1");
		boolean isNegativ = false;
		if (exp.compareTo(new BigDecimal("0")) == -1) {
			isNegativ = true;
		} 
		else {
			isNegativ = false;
		}
		
		exp = exp.abs();
		int i = exp.intValueExact();
		res = base.pow(i);
		if (isNegativ == true) {
			res = new BigDecimal("1").divide(res);
		}	*/
		BigDecimal res = BigDecimalMath.pow(base, exp);
		
		return res;	
	}
	
	/* convert an integer to a two's Complement with 32 Bits, each digit will be shown */
	public static String twosComplement(int i) {
		String output;
		output = String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
		return output;
	}
	
	/* convert a long number into binary twos complement string */
	public static String bigInteger2bin(BigInteger b) {
		String output;
		String str = "";

		byte[] bs = b.toByteArray();
		int k = 0;
		for (int i = 0; i < bs.length; i++) {
			System.out.print(String.format("%02X", 0xff & bs[i]));
			str += String.format("%02X", 0xff & bs[i]); 
			k++;
		}
		k = k*8;
		String tmp = String.format("%"+k+"s", new BigInteger(str, 16).toString(2));
		output = String.format("%"+k+"s", tmp.replace(" ", "0"));
		return output;
	}
	
	/* convert BigDecimal to Binary */
	public static String bigDec2Bin(BigDecimal i) {
		String s = i.toString();
		float f = Float.valueOf(s);
		s = float2bin(f);
		
		return s;
	}
	
	/* convert float to binary representation IEEE 754 */
	public static String float2bin(float f) {
		String str = new String("");
		//str = Integer.toString(Float.floatToIntBits(f), 2);
		System.out.println(str);
		str = String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(f))).replace(' ', '0');
		str = str.substring(0, 1) + " " + str.substring(1, 9) + " " + str.substring(9, str.length());
		
		return str;
	}
	
	
	/* prints a hint under "hinweis" if there is an overflow  */
	public static String checkOverflow(BigInteger i) {
		String output = "";
		if(i.bitLength() > 31) {
			output = "Overflow ";
		}
		else {
			output = "";
		}
		
		return output;
	}
	
	// functions for saving strings to print
	public String printTherm() {
		String start = "Reale Arithmetik\n\nInput\t\tOperation\t\tStack\t\tHinweis\n";

		return start + thermString;
	}
	
	public String printReal() {
		String realStart = "\n";
		return realStart + realString;
	}
	
	public String printComp() {
		String compStart = "\n"; 
		return compStart + compString;
	}	
	
	// convert to bin and save in string
	public void saveCompStringInteger(int a, int b, int x, String tk) {
		compString += " (" + b + ") " + twosComplement(b)  + "\n" + tk + 
				" (" + a + ") " + twosComplement(a) + 
				"\n   -------------------------------------\n   " + 
				" (" + x + ") " + twosComplement(x) + "\n\n";
		
	}

	// convert to bin and save in string 
	public void saveRealStringInteger(BigInteger a, BigInteger b, BigInteger x, String tk) {
			realString += " (" + b + ") " + bigInteger2bin(b) + "\n" + tk + 
				" (" + a + ") " + bigInteger2bin(a) + 
				"\n   -------------------------------------\n   " + 
				" (" + x + ") " + bigInteger2bin(x) + "\n\n";
	}
	
	// convert to bin and save in string
	public void saveCompStringFloat(float a, float b, float x, String tk) {
		compString += " (" + b + ") " +  float2bin(b) +  "\n" + tk + 
				" (" + a + ") " + float2bin(a) +  
			"\n   -------------------------------------\n   " + 
			" (" + x + ") " + float2bin(x) + "\n\n";
	}
	
	// convert bigdec to bin and save in string
	public void saveRealStringFloat(BigDecimal a, BigDecimal b, BigDecimal x, String tk) {
		realString += "   " + " (" + b + ") " + bigDec2Bin(b) + "\n" + tk + " " + 
			" (" + a + ") " + bigDec2Bin(a) + 
			"\n   -------------------------------------\n   " + 
			" (" + x + ") " + bigDec2Bin(x) +  "\n\n";
	}
	
}

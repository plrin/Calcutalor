import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Stack;
import javax.swing.JOptionPane;
import org.nevec.rjm.BigDecimalMath;
import subsift.ArithmeticTools;


public class Controller2 implements ActionListener {
	
	// instance variables
	private View2 view;
	private String comboBox;
	private String thermString = "";
	private static String compString = "";
	private static String realString = "";
	// register for latest results
	private int intLastX;
	private BigInteger bigIntLastX;
	private float floatLastX;
	private BigDecimal bigDecLastX;
	// storage
	private int[] intStore = new int [100];
	private BigInteger[] bigIntStore = new BigInteger [100];
	private float[] floatStore = new float [100];
	private BigDecimal[] bigDecStore = new BigDecimal [100];
		
	
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
				//realOutput = String.valueOf(bigIntegerRPN());
				//compOutput = String.valueOf(integerRPN());
			}
			else if (comboBox.equals("Float")) {
				try {
					realOutput = String.valueOf(bigDecimalRPN());
					compOutput = String.valueOf(floatRPN());
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc );
					realOutput = "error";
					compOutput = "error";
				}
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
	public int integerRPN() {
		String string = getInput();
		String tk;
		Stack<Integer> st = new Stack<Integer>(); 
		// a and b are stack numbers to pop, x input/result number to push
		int a, b, x, i; 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; ++i) {
			a = 0; b = 0; x = 0;
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\d+)$") || tk.matches("(?i)last")) {
				if (tk.equals("last")) {
					x = intLastX;
				}
				else {
					x = Integer.parseInt(tk);
				}	
				st.push(x);
			}
			// start of operations
			else {
				if (tk.matches("(?i)drop")) {
					st.pop();
				}
				else if (tk.matches("(?i)dup")) {
					x = st.peek();
					st.push(x);
				}
				else if (tk.matches("(?i)sto")) {
					a = st.pop(); // index
					b = st.pop(); // number to store
					intStore[a] = b; // save b in array index a
				}
				else if (tk.matches("(?i)rcl")) {
					a = st.pop(); // index
					x = intStore[a]; // get number from index a
					st.push(x); // push number into stack
				}
				else if (tk.matches("(?i)ifeq")) {
					a = st.pop(); // number of position
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x == b
					if (x == b) {
						i = a;
						i--;
					}
				}
				else if (tk.matches("(?i)ifgr")) {
					a = st.pop(); // number of the position to go
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x > b 
					if (x > b) {
						i = a;
						i--;
					}
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalInt(b,a,tk);
					st.push(x);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
			}
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return 00;
		}
		else {
			intLastX = st.peek();
			return st.pop();
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
		saveCompStringInteger(a,b,x,tk);

		return x;
	}
	
	/* real/human arithmetic */
	public BigInteger bigIntegerRPN() {
		String string = getInput();
		String tk;
		Stack<BigInteger> st = new Stack<BigInteger>();
		int i; 
		// a and b are stack numbers to pop, x input/result number to push
		BigInteger a, b, x; 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; ++i) {
			a = new BigInteger("0"); b = new BigInteger("0"); x = new BigInteger("0");
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\d+)$") || tk.matches("last")) {
				if (tk.equals("last")) {
					x = bigIntLastX;
				}
				else {
					x = new BigInteger(tk);
				}
				st.push(x);
				// save string operation window
				thermString += x + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
			}
			// operators
			else {
				if (tk.matches("(?i)drop")) {
					x = st.pop();
					thermString += tk + "\t\tpop(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
				}
				else if (tk.matches("(?i)dup")) {
					x = st.peek();
					st.push(x);
					thermString += tk + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
				}
				else if (tk.matches("(?i)sto")) {
					a = st.pop(); // index
					int index = a.intValue();
					b = st.pop(); // number to store
					bigIntStore[index] = b; // save b in array index a
					thermString += tk + "\t\tpop(" + a + "," + b + ")\t" + checkTab(tk.length()) + st.toString() +
							"\t\t" + "save " + b + " in [" + a + "]" + "\n";
				}
				else if (tk.matches("(?i)rcl")) {
					a = st.pop(); // index
					int index = a.intValue();
					x = bigIntStore[index]; // get number from index a
					st.push(x); // push number into stack
					thermString += tk + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) +st.toString() + "\n";

				}
				else if (tk.matches("(?i)ifeq")) {
					a = st.pop(); // number of position
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x == b 
					if (x.compareTo(b) == 0) {
						int tmp = a.intValue();
						i = tmp;
						thermString += tk + "\t\t" + x + "=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tJump to pos " + i + "\n";
						i--;
					}
					else {
						thermString += tk + "\t\t" + x + "=/=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tweiter" + "\n";
					}
				}
				else if (tk.matches("(?i)ifgr")) {
					a = st.pop(); // number of position
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x > b 
					if (x.compareTo(b) == 1) {
						int tmp = a.intValue();
						i = tmp;
						thermString += tk + "\t\t" + x + ">" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tJump to pos " + i + "\n";
						i--;
					}
					else {
						thermString += tk + "\t\t" + x + "<=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tweiter" + "\n";
					}
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalBigInt(b,a,tk);
					st.push(x);
					// save string operation window
					thermString += tk + "\t\t" + "pop(" + b + "," + a + ")\n\t\t" + b + tk + 
							a + "\n\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + 
							"\t\t" + checkOverflow(x) + "\n";
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
			}
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return new BigInteger("00");
		}
		else {
			bigIntLastX = st.peek();
			return st.pop();
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
		saveRealStringInteger(a,b,x,tk);

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
	public float floatRPN() {
		String string = getInput();
		String tk;
		Stack<Float> st = new Stack<Float>(); 
		// a and b are stack numbers to pop, x input/result number to push
		float a, b, x;
		int i; 
		String [] splitArray = string.split("\\s");
		
		for (i = 0; i < splitArray.length; i++) {
			a = 0f; b = 0f; x = 0f;
			tk = splitArray[i];
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$") || tk.matches("(?i)pi|eu|last") || 
					tk.matches("^[+-]?(\\d+.\\d+)[eE]{1}[+-]?\\d+")) {
				if (tk.equals("pi")) {
					x = 3.1415927f;
				}
				else if (tk.equals("eu")) {
					x = 2.7182817f;
				}
				else if(tk.equals("last")) {
					x = floatLastX;
				}
				else {
					x = Float.parseFloat(tk);
				}
				st.push(x);
			}
			// operate
			else {
				if (tk.matches("(?i)drop")) {
					st.pop();
				}
				else if (tk.matches("(?i)dup")) {
					x = st.peek();
					st.push(x);
				}
				else if (tk.matches("(?i)sto")) {
					a = st.pop(); // index
					int index = (int) a;
					b = st.pop(); // number to store
					floatStore[index] = b; // save b in array index a
				}
				else if (tk.matches("(?i)rcl")) {
					a = st.pop(); // index
					int index = (int) a;
					x = floatStore[index]; // get number from index a
					st.push(x); // push number into stack
				}
				else if (tk.matches("(?i)ifeq")) {
					a = st.pop(); // number of pos
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x == b 
					if (x == b) {
						int tmp = (int) a;
						i = tmp;
						i--;
					}
				}
				else if (tk.matches("(?i)ifgr")) {
					a = st.pop(); // number of jump backs
					b = st.pop(); // first number to check
					x = st.pop(); // second number to check, b > x 
					if (x > b) {
						int tmp = (int) a;
						i = tmp;
						i--;
					}
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalFloat(b,a,tk);
					st.push(x);
				}
				else if (tk.matches("(?i)sin|cos|tan")) {
					a = st.pop();
					b = 0;
					x = evalFloat(a,b,tk);
					st.push(x);
				}
				else if (tk.matches("(?i)ln")) {
					a = st.pop();
					b = 0;
					x = evalFloat(a,b,tk);
					st.push(x);
				}
				else if (tk.matches("(?i)log")) {
					a = st.pop();
					b = 0;
					x = evalFloat(a,b,tk);
					st.push(x);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}	
			}	
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return 00;
		}
		else {
			floatLastX = st.peek();
			return st.pop();
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
		else if(tk.equals("ln")) {
			x = (float) Math.log(b);
		}
		else if(tk.matches("log")) {
			x = (float) Math.log10(b);
		}
		else {
			JOptionPane.showMessageDialog(null, "Rechenzeichen nicht gueltig.");
		}
		saveCompStringFloat(a,b,x,tk);

		return x;
	}
	
	
	/* real/human arithmetic */
	public BigDecimal bigDecimalRPN() {
		String string = getInput();
		String tk = "";
		Stack<BigDecimal> st = new Stack<BigDecimal>();
		int i; 
		// a and b are stack numbers to pop, x input/result number to push
		BigDecimal a, b, x; 
		String [] splitArray = string.split("\\s");
	
		for (i = 0; i < splitArray.length; i++) {
			tk = splitArray[i];
			a = new BigDecimal("0"); b = new BigDecimal("0"); x = new BigDecimal("0");
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$") || tk.matches("(?i)pi|eu|last") ||
					tk.matches("^[+-]?(\\d+.\\d+)[eE]{1}[+-]?\\d+")) {
				if (tk.equals("pi")) {
					x = BigDecimalMath.pi(new MathContext(14));
				}
				else if (tk.equals("eu")) {
					x = new BigDecimal("2.71828182845904");
				}
				else if (tk.equals("last")) {
					x = bigDecLastX;
				}
				else {
					x = new BigDecimal(tk);
				}
				st.push(x);
				// save string operation window
				thermString += x + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
			}	
			else { // operations
				if (tk.matches("(?i)drop")) {
					x = st.pop();
					thermString += tk + "\t\tpop(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
				}
				else if (tk.matches("(?i)dup")) {
					x = st.peek();
					st.push(x);
					thermString += tk + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
				}
				else if (tk.matches("(?i)sto")) {
					a = st.pop(); // index
					int index = a.intValue();
					b = st.pop(); // number to store
					bigDecStore[index] = b; // save b in array index a
				}
				else if (tk.matches("(?i)rcl")) {
					a = st.pop(); // index
					int index = a.intValue();
					x = bigDecStore[index]; // get number from index a
					st.push(x); // push number into stack
					thermString += tk + "\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + "\n";
				}
				else if (tk.matches("(?i)ifeq")) {
					a = st.pop(); // number of jump backs
					b = st.pop(); // first number to check
					x = st.pop(); // second number to check, x == b 
					if (x.compareTo(b) == 0) {
						int tmp = a.intValue();
						i = tmp;
						thermString += tk + "\t\t" + x + "=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tJump to pos " + i + "\n";
						i--;
					}
					else {
						thermString += tk + "\t\t" + x + "=/=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tweiter" + "\n";
					
					}
				}
				else if (tk.matches("(?i)ifgr")) {
					a = st.pop(); // number of position
					b = st.pop(); // second number to check
					x = st.pop(); // first number to check, x >bx 
					if (x.compareTo(b) == 1) {
						int tmp = a.intValue();
						i = tmp;
						thermString += tk + "\t\t" + x + ">" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tJump to pos " + i + "\n";
						i--;
					}
					else {
						thermString += tk + "\t\t" + x + "<=" + b + "\t" + checkTab(tk.length()) + st.toString() + 
								"\t\tweiter\n";
					}
				}
				else if (tk.matches("(\\+|\\-|\\*|\\/|\\^)?")) {
					a = st.pop();
					b = st.pop();
					x = evalBigDec(b,a,tk);
					st.push(x);
					// save string operation window
					thermString += tk + "\t\t" + "pop(" + b + "," + a + ")\n\t\t" + b + tk + 
							a + "\n\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + 
							"\t\t" + "\n";
				}
				else if (tk.matches("(?i)sin|cos|tan")) {
					a = st.pop();
					b = new BigDecimal("0");
					x = evalBigDec(a,b,tk);
					st.push(x);
					// save string operation window
					thermString += tk + "\t\t" + "pop(" + a + ")\n\t\t" + tk + 
							a + "\n\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + 
							"\t\t" + "\n";
				}
				else if(tk.matches("(?i)ln")) {
					a = st.pop();
					b = new BigDecimal("0");
					x = evalBigDec(a,b,tk);
					st.push(x);
					// save string operation window
					thermString += tk + "\t\t" + "pop(" + a + ")\n\t\t" + tk + 
							a + "\n\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + 
							"\t\t" + "\n";
				}
				else if(tk.matches("(?i)log")) {
					a = st.pop();
					b = new BigDecimal("0");
					x = evalBigDec(a,b,tk);
					st.push(x);
					// save string operation window
					thermString += tk + "\t\t" + "pop(" + a + ")\n\t\t" + tk + 
							a + "\n\t\tpush(" + x + ")\t" + checkTab(tk.length()) + st.toString() + 
							"\t\t" + "\n";
				}
				else {
					JOptionPane.showMessageDialog(null, "Ungueltiger Operator.");
				}
			}
		}
		
		if (i < splitArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return new BigDecimal("00");
		}
		else {
			bigDecLastX = st.peek();
			return st.pop();
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
			x = b.divide(a, new MathContext(8));
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
		else if (tk.equals("ln")) {
			x = BigDecimalMath.log(b);
		}
		else if (tk.equals("log")) {
			x = ArithmeticTools.log10(b);
		}
		else {
			JOptionPane.showMessageDialog(null, "Operator nicht gueltig");
		}
		saveRealStringFloat(b,a,x,tk);
		
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
		
	public String getInput() {
		return view.inputArea.getText();
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
		BigDecimal res = BigDecimalMath.pow(base, exp);
		return res;	
	}
	
	/* convert an integer to a two's Complement with 32 Bits, each digit will be shown */
	public static String twosComplement(int i) {
		String output;
		output = String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
		
		output = output.substring(0, 4) + " " + output.substring(4,8) + " " + 
				output.substring(8, 12) + " " + output.substring(12, 16) + " " +
				output.substring(16, 20) + " " + output.substring(20, 24) + " " +
				output.substring(24, 28) + " " + output.substring(28, output.length());
		
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
		str = String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(f))).replace(' ', '0');
		str = str.substring(0, 1) + " " + str.substring(1, 9) + " " + str.substring(9, str.length());
		
		return str;
	}
	
	
	// credit to Tobi Lehman on stackoverflow
	private static double binaryStringToDouble(String s) {
	    return stringToDouble(s, 2);
	}

	private static double stringToDouble(String s, int base) {
	    String withoutPeriod = s.replace(".", "");
	    double value = new BigInteger(withoutPeriod, base).doubleValue();
	    String binaryDivisor = "1" + s.split("\\.")[1].replace("1", "0");
	    double divisor = new BigInteger(binaryDivisor, base).doubleValue();
	    return value / divisor;
	}
	
	// convert IEEE 754 BitString into normalize floating point number
		public static String bin2norm(float x) {
			String str = float2bin(x);
			
			String v = str.substring(0,1);
			String b = str.substring(2,10);
			String m = str.substring(11, str.length());
			// get sign
			if (v.equals("0")) {
				v = "+";
			}
			else if (v.equals("1")) {
				v = "-";
			}
			
			// get bias
			int bias = Integer.parseInt(b, 2);
			bias = -127 + bias;
			b = String.valueOf(bias);
			
			// get mantissa
			if(b.equals("-127")) {
				
				m = "0." + m;
			}
			else {
				m = "1." + m;
			}
			double mantissa = binaryStringToDouble(m);
			m = String.valueOf(mantissa);
			
			return v + m + " x 2^" + b;
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
	
	/* check tab for operator window in string, depends on tk.length */
	public static String checkTab(int length) {
		String tab = "";
		if(length < 8) {
			tab = "\t";
		}
		return tab;
	}
	
	
	// functions for saving strings to print
	public String printTherm() {
		String start = "Operationen\n\nInput\t\tOperation\t\tStack\t\tHinweis\n";

		return start + thermString;
	}
	
	public String printReal() {
		String realStart = "\n";
		return realStart + realString;
	}
	
	public String printComp() {
		String compStart = "(Dezimalzahl) [normaliserte Gleitkommazahl] Binaerdarstellung\n\n"; 
		return compStart + compString;
	}	
	
	// convert to bin and save in string
	public static void saveCompStringInteger(int a, int b, int x, String tk) {
		compString += " (" + b + ") " + twosComplement(b)  + "\n" + tk + 
				" (" + a + ") " + twosComplement(a) + 
				"\n   --------------------------------------------\n   " + 
				" (" + x + ") " + twosComplement(x) + "\n\n";
		
	}

	// convert to bin and save in string 
	public static void saveRealStringInteger(BigInteger a, BigInteger b, BigInteger x, String tk) {
			realString += " (" + b + ") " + "\n" + tk + 
				" (" + a + ") " + 
				"\n   --------------------------------------------\n   " + 
				" (" + x + ") " + "\n\n";
	}
	
	// convert to bin and save in string
	public static void saveCompStringFloat(float a, float b, float x, String tk) {
		compString += " (" + b + ") " + "[" + bin2norm(b) + "] " +  float2bin(b) +  "\n" + tk + 
				" (" + a + ") " + "[" + bin2norm(a) + "] " + float2bin(a) +  
			"\n   --------------------------------------------\n   " + 
			" (" + x + ") " + "[" + bin2norm(x) + "] "  + float2bin(x) + "\n\n";
	}
	
	
	// convert bigdec to bin and save in string
	public static void saveRealStringFloat(BigDecimal a, BigDecimal b, BigDecimal x, String tk) {
		realString += " (" + a + ") " + "\n" + tk + 
			" (" + b + ") " + 
			"\n   -------------------------------------------\n   " + 
			" (" + x + ") " +  "\n\n";
	}
	
}


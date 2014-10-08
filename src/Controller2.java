import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;

import javax.swing.JOptionPane;


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
			int loops = getLoop();
			comboBox = getComboBox();

			BigInteger realInt = new BigInteger("0");
			int compInt = 0;
			BigDecimal realDec = new BigDecimal("0");
			float compFloat = 0f;
			String realOutput = "";
			String compOutput = "";
			thermString = "";
			compString = "";
			realString = "";
			
			if (comboBox.equals("Integer")) {
				try {
					int x = Integer.parseInt(integerRPN());
					BigInteger y = new BigInteger(bigIntegerRPN());
					for (int i = 0; i <= loops; i++) {
						realInt = realInt.add(y);
						compInt += x;
					}
					realOutput = String.valueOf(realInt);
					compOutput = String.valueOf(compInt);
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Eingabe ueberschreitet Zahlenbereich\n"
							+ "[-2^31 = -2.147.483.648 bis 2^31-1 = 2.147.483.647].\n" + e );
					realOutput = "error";
					compOutput = "error";
				}	
			}
			else if (comboBox.equals("Float")) {
				try {
					float x = Float.parseFloat(floatRPN());
					BigDecimal y = new BigDecimal(bigDecimalRPN());
					for (int i = 0; i <= loops; i++) {
						realDec = realDec.add(y);
						compFloat += x;
					}
					realOutput = String.valueOf(realDec);
					compOutput = String.valueOf(compFloat);
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Eingabe ueberschreitet Zahlenbereich"
							+ "[x nachkommastellen].\n" + e );
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
		view.realTextArea.setText(printReal());
		view.compTextArea.setText(printComp());
	}
		


	/* start of methods */
	
	/* calculation of Integer and Big Integer */
	/* computer arithmetic */
	public String integerRPN() {
		String string = getInput();
		String tk;
		Stack<Integer> st = new Stack<Integer>(); 
		// a and b are stack numbers to pop, x input/result number to push
		int a = 0, b = 0, x = 0, i; 
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = Integer.parseInt(tk);
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\n"
							+ "oder\nZahl fehlt.");
					return "error";
				}
				else {
					a = st.pop();
					b = st.pop();
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
						x = integerPow(b, a);
					}
					else {
						JOptionPane.showMessageDialog(null, "Fehler.");
						 return "error";
					}
					// convert to bin and save in string
					compString += "   " + twosComplement(b) + " (" + b + ")" + "\n" + tk + " " + 
					twosComplement(a) + " (" + a + ")" + "\n   --------------------------------\n   " + 
					twosComplement(x) + " (" + x + ")" + "\n\n";
				}
			}
			st.push(x);
			
		}
		
		if (i < splitedArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
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
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = new BigInteger(tk);
				isNumber = true;
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\n"
							+ "oder\nZahl fehlt.");
					return "error";
				}
				else {
					a = st.pop();
					b = st.pop();
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
						JOptionPane.showMessageDialog(null, "Fehler.");
						 return "error";
					}
					// convert to bin and save in string 
					// ==> not working right atm
					realString += "   " + b.toString(2) + " (" + b + ")" + "\n" + tk + " " + 
					a.toString(2) + " (" + a + ")" + "\n   --------------------------------\n   " + 
					x.toString(2) + " (" + x + ")" + "\n\n";
				}
				isNumber = false;
			}
			st.push(x);
			// save operations into string
			//
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
				thermString += tmpTk + "\t\t" + b + tmpTk + a + "\n\t\tpush(" + x + ")\t" +
						tmp + st.toString() + "\t\t" + checkOverflow(x) + "\n";
			}
			// end of string save
		}
		
		if (i < splitedArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
		
	/* end of Integer and Big Integer calculation */ 
	
	/*--------------------------------------------- */
	
	/* start of float and big float calculation */
	/* computer arithmetic */
	public String floatRPN() {
		String string = getInput();
		String tk;
		Stack<Float> st = new Stack<Float>(); 
		// a and b are stack numbers to pop, x input/result number to push
		float a = 0, b = 0, x = 0;
		int i; 
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$")) {
				x = Float.parseFloat(tk);
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\n"
							+ "oder\nZahl fehlt.");
					return "error";
				}
				else {
					a = st.pop();
					b = st.pop();
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
						String strA = Float.toString(a);
						double dA = Double.parseDouble(strA);
						String strB = Float.toString(b);
						double dB = Double.parseDouble(strB);
						double dX = Math.pow(dB, dA);
						String s = Double.toString(dX);
						x = Float.valueOf(s);
						System.out.println("double: " + dX + " string: " + s + " float: " + x);
					}
					else {
						JOptionPane.showMessageDialog(null, "Fehler.");
						 return "error";
					}
					// convert to bin and save in string
					compString += "   " +  float2bin(b) + " (" + b + ")" + "\n" + tk + " " + 
						float2bin(a) + " (" + a + ")" + "\n   --------------------------------\n   " + 
						float2bin(x) + " (" + x + ")" + "\n\n";
				}
			}
			st.push(x);
			
		}
		
		if (i < splitedArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	
	/* real/human arithmetic */
	public String bigDecimalRPN() {
		String string = getInput();
		String tk;
		Stack<BigDecimal> st = new Stack<BigDecimal>();
		int i; 
		boolean isNumber = true;
		// a and b are stack numbers to pop, x input/result number to push
		BigDecimal a = new BigDecimal("0"), b = new BigDecimal("0") , x = new BigDecimal("0.2"); 
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\.\\d+|\\d+(\\.\\d+)?)$")) {
				x = new BigDecimal(tk);
				isNumber = true;
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\n"
							+ "oder\nZahl fehlt.");
					return "error";
				}
				else {
					a = st.pop();
					b = st.pop();
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
						JOptionPane.showMessageDialog(null, "Fehler.");
						 return "error";
					}
					// convert bigdec to bin and save in string
					realString += "   " + bigDec2Bin(b) + " (" + b + ")" + "\n" + tk + " " + 
						bigDec2Bin(a) + " (" + a + ")" + "\n   --------------------------------\n   " + 
						bigDec2Bin(x) + " (" + x + ")" + "\n\n";
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
				thermString += tmpTk + "\t\t" + b + tmpTk + a + "\n\t\tpush(" + x + ")\t" +
						tmp + st.toString() + "\t\t" + "" + "\n";
			}
			// end of string save
		}
		
		if (i < splitedArray.length || st.size() > 1) {
			JOptionPane.showMessageDialog(null, "Rechenzeichen Fehlt.");
			return "error";
		}
		else {
			return String.valueOf(st.pop());
		}
	}
	
	/* end of float and big float calculation */
	
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
	
	
	/* convert an integer to a two's Complement with 32 Bits, each digit will be shown */
	public static String twosComplement(int i) {
		String output;
		output = String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
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
		str = Integer.toString(Float.floatToIntBits(f), 2);
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
		String realStart = "Reale Arithmetik\n\n";
		return realStart + realString;
	}
	
	public String printComp() {
		String compStart = "Computer-Arithmetik\n\n"; 
		return compStart + compString;
	}	

}

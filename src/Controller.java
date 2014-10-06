import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.util.Stack;

import javax.swing.JOptionPane;


public class Controller implements ActionListener {
	
	// instance variables
	private View view;
	private String comboBox;
	private String compString = "";
	private String realString = "";

	
	public Controller() {
		this.view = new View(this);
		
	}
	
	// button action on click
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int loops = getLoop();
		comboBox = getComboBox();

		BigInteger realInt = new BigInteger("0");
		int compInt = 0;
		String realOutput = "";
		String compOutput = "";
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
				JOptionPane.showMessageDialog(null, "Eingabe ueberschreitet Zahlenbereich"
						+ "[-2^31 = -2.147.483.648 bis 2^31-1 = 2.147.483.647].\n" + e );
				realOutput = "error";
				compOutput = "error";
			}	
		}
		else if (comboBox.equals("Float")) {
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Combobox Fehler.");
		}
		
		view.realResultTextField.setText(realOutput);
		view.compResultTextField.setText(compOutput);
		view.textArea.setText(printText());
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
				}
				isNumber = false;
			}
			st.push(x);
			// save operations into string
			if(isNumber == true) {
				realString += tk + "\t\tpush(" + tk + ")\t\t" + st.toString() + "\n";  
			}
			else {
				realString += tk + "\t\t" + b + tk + a + "\n\t\tpush(" + x + ")\t\t" + 
						st.toString() + "\t\t" + checkOverflow(x) + "\n";
			}
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
		
		return null;
	}
	
	
	/* real/human arithmetic */
	public String bigFloatRPN() {
		
		return null;
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
	
	
	/* get the type of datatype to calculate */
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
	
	
	/* return the text for the textarea */
	public String printText() {
		String start = "Reale Arithmetik\n\nInput\t\tOperation\t\tStack\t\tHinweis\n";
		String compStart = "-----------------------------------------------------"
				+ "\nComputer-Arithmetik\n\n"; 
		return start + realString + compStart + compString;
	}
		

}

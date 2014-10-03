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
	}
		


	/* start of methods */
	
	public void focusInput () {
		view.frame.addWindowListener (new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				view.inputField.requestFocus();
			}
		});
	}
	
	public int getLoop() {
		String textField = view.loopField.getText();
		int numberOfLoops = 0;
		try {
			numberOfLoops = Integer.parseInt(textField);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Keine gueltige Nummer!\n" + e);
		}
		return numberOfLoops;
	}
	
	
	public String getInput() {
		return view.inputField.getText();
	}
	
	
	public String getComboBox() {
		comboBox = (String) view.numSystemComboBox.getSelectedItem();
		return comboBox;
	}
	
	
	public String integerRPN() {
		String string = getInput();
		String tk;
		Stack<Integer> st = new Stack<Integer>(); 
		int a, b, x = 0, i; // a and b are stack numbers to pop, x input/result number to push
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = Integer.parseInt(tk);
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\noder\nZahl fehlt.");
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
	
	
	public String bigIntegerRPN() {
		String string = getInput();
		String tk;
		Stack<BigInteger> st = new Stack<BigInteger>();
		int i; 
		// a and b are stack numbers to pop, x input/result number to push
		BigInteger a, b , x = new BigInteger("0"); 
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; i++) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = new BigInteger(tk);
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\noder\nZahl fehlt.");
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
		
}

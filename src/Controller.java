import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import javax.swing.JOptionPane;


public class Controller implements ActionListener {
	
	// instance variables
	//private Model model;
	private View view;

	
	public Controller() {
		//this.model = new Model();
		this.view = new View(this);
		
		// add observers
		//this.model.addObserver(this.view);
		
	}
	
	// button action on click
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*
		int loops = getLoop();
		while(loops >= 0) {
			integerRPN();
			loops--;
		}
		*/
		view.compResultTextField.setText(integerRPN());
		System.out.println(integerRPN());
		//System.out.println(Math.random());
	
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
		//String x = view.inputField.getText();
		//System.out.println(x.matches(loopPattern));
		return view.inputField.getText();
	}
	
	
	
	public String integerRPN() {
		String string = getInput();
		String tk;
		Stack<Integer> st = new Stack<Integer>(); 
		int a, b, x = 0, i; // a and b are stack numbers to pop, x input/result number to push
		String [] splitedArray = string.split("\\s");
		
		for (i = 0; i < splitedArray.length; ++i) {
			tk = splitedArray[i];
			if (tk.matches("^[+-]?(\\d+)$")) {
				x = Integer.parseInt(tk);
				//st.push(x);
			}
			else {
				if (tk.length() < 1 || st.size() < 2 ) {
					JOptionPane.showMessageDialog(null, "Ungueltiges Zeichen wurde gelesen\noder\nZahl fehlt.");
					 return "error";
				}
				else {
					//tk = String.valueOf(tk);
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
						double bn = (double) b;
						double an = (double) a;
						double xn = (double) x;
						xn = Math.pow(bn, an);
						x = (int) xn;
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

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class View {
	
	// instance variables
	protected JFrame frame;
	protected JComboBox numSystemComboBox;
	private String numSystemArray [] = {"Integer", "Float"}; 
	private JPanel loopPanel;
	private JLabel loopLabel;
	protected JTextField loopField;
	private JPanel inputPanel;
	protected JLabel inputLabel;
	protected JTextField inputField;
	protected JButton button;
	private JLabel realResultLabel;
	protected JTextField realResultTextField;
	private JLabel compResultLabel;
	protected JTextField compResultTextField;
	private JPanel scrollPanel;
	private JScrollPane scroll;
	protected JTextArea textArea;
	
	
	public View(Controller controller) {
		frame = new JFrame("Rechner");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		numSystemComboBox = new JComboBox(numSystemArray);
		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(numSystemComboBox, gbc);
		
		loopPanel = new JPanel(new FlowLayout());
		gbc.gridx = 1;
		gbc.gridy = 0;
		frame.add(loopPanel, gbc);
		
		loopLabel = new JLabel("Loop");
		loopPanel.add(loopLabel);
		loopField = new JTextField("0", 3);
		loopPanel.add(loopField);
		
		inputLabel = new JLabel("Therm");
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(inputLabel, gbc);
		
		inputPanel = new JPanel(new FlowLayout());
		gbc.gridx = 1;
		gbc.gridy = 1;
		frame.add(inputPanel, gbc);
		
		inputField = new JTextField(17);
		inputPanel.add(inputField);		
		button = new JButton("=");
		button.setPreferredSize(new Dimension(30,20));
		button.addActionListener(controller);
		inputPanel.add(button);
		
		realResultLabel = new JLabel("richitges Ergebnis");
		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(realResultLabel, gbc);
		
		realResultTextField = new JTextField(20);
		realResultTextField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 2;
		frame.add(realResultTextField, gbc);
		
		compResultLabel = new JLabel("Computer Ergebnis");
		gbc.gridx = 0;
		gbc.gridy = 3;
		frame.add(compResultLabel, gbc);
		
		compResultTextField = new JTextField(20);
		compResultTextField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		frame.add(compResultTextField, gbc);
		
		scrollPanel = new JPanel();
	    scrollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Erklaerung"));
	    textArea= new JTextArea(20, 55);
		textArea.setEditable(false);

		scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.add(scroll);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 4;
		frame.add(scrollPanel, gbc);
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();	
	
	}
}

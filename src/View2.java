import java.awt.*;

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


public class View2 {
	
	// instance variables
	protected JFrame frame;
	protected JComboBox numSystemComboBox;
	private String numSystemArray [] = {"Integer", "Float"}; 
	private JPanel loopPanel;
	private JLabel loopLabel;
	protected JTextField loopField;
	private JLabel inputLabel;
	protected JTextField inputField;
	protected JButton button;
	private JLabel realLabel;
	protected JTextField realOutput;
	private JLabel compLabel;
	protected JTextField compOutput;
	
	private JPanel scrollPanel1;
	private JPanel scrollPanel2;
	private JPanel scrollPanel3;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JScrollPane scroll3;
		
	protected JTextArea thermTextArea;
	protected JTextArea realTextArea;
	protected JTextArea compTextArea;
	
	private JPanel thermPanel;
	private JPanel realPanel;
	private JPanel compPanel;
	
	
	public View2(Controller2 controller) {
		frame = new JFrame("Rechner");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Row = 0
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
		
		button = new JButton("=");
		button.addActionListener(controller);
		gbc.gridx = 2;
		gbc.gridy = 0;
		frame.add(button, gbc);
		
		// Row = 1
		thermPanel = new JPanel(new GridLayout(2,0));
		inputLabel = new JLabel("Therm");
		inputField = new JTextField(20);
		thermPanel.add(inputLabel);
		thermPanel.add(inputField);
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(thermPanel, gbc);
		
		scrollPanel1 = new JPanel();
	    scrollPanel1.setBorder(new TitledBorder(new EtchedBorder(), "Berechnung"));
		
		thermTextArea = new JTextArea(10, 55);
		thermTextArea.setEditable(false);
		scroll1 = new JScrollPane(thermTextArea);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel1.add(scroll1);
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		frame.add(scrollPanel1, gbc);
		
		// Row = 2
		realPanel = new JPanel(new GridLayout(2,1));
		realLabel = new JLabel("richtiges Ergebnis");
		realOutput = new JTextField(20);
		realOutput.setEditable(false);
		realPanel.add(realLabel);
		realPanel.add(realOutput);
		//gbc.fill = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(realPanel, gbc);
		
		scrollPanel2 = new JPanel();
	    scrollPanel2.setBorder(new TitledBorder(new EtchedBorder(), "richtig Binaer"));
		
		realTextArea = new JTextArea(10, 55);
		realTextArea.setEditable(false);
		scroll2 = new JScrollPane(realTextArea);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel2.add(scroll2);
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 2;
		frame.add(scrollPanel2, gbc);
		
		// Row = 3
		compPanel = new JPanel(new GridLayout(2,1));
		
		compLabel = new JLabel("Computer Ergebnis");
		compOutput = new JTextField(20);
		compOutput.setEditable(false);
		compPanel.add(compLabel);
		compPanel.add(compOutput);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		frame.add(compPanel, gbc);
		
		scrollPanel3 = new JPanel();
	    scrollPanel3.setBorder(new TitledBorder(new EtchedBorder(), "Binaer Computer"));
		
		compTextArea = new JTextArea(10, 55);
		compTextArea.setEditable(false);
		scroll3 = new JScrollPane(compTextArea);
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel3.add(scroll3);
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 3;
		frame.add(scrollPanel3, gbc);
		
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();	
	
	}
}

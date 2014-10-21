import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


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
	
	protected JTextPane realTextPane;
	protected JTextPane compTextPane;
	
	private JPanel thermPanel;
	private JPanel realPanel;
	private JPanel compPanel;
	
	
	public View2(Controller2 controller) {
		
		// frame attributes 
		frame = new JFrame("Rechner");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// style attributes
		SimpleAttributeSet style = new SimpleAttributeSet();  
		StyleConstants.setAlignment(style , StyleConstants.ALIGN_RIGHT); 
		
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
		inputLabel = new JLabel("Eingabe");
		inputField = new JTextField(20);
		thermPanel.add(inputLabel);
		thermPanel.add(inputField);
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(thermPanel, gbc);
		
		scrollPanel1 = new JPanel();
	    scrollPanel1.setBorder(new TitledBorder(new EtchedBorder(), "Berechnung im Dezimalsystem"));
		
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
	    scrollPanel2.setBorder(new TitledBorder(new EtchedBorder(), "richtige Berechnung im Binaersystem"));
		
		
		realTextPane = new JTextPane();
		realTextPane.setMargin(new Insets(5,5,5,5));
		realTextPane.setBounds(0, 0, 10, 55);
		realTextPane.setEditable(false);
		realTextPane.setParagraphAttributes(style, true);
		scroll2 = new JScrollPane(realTextPane);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setPreferredSize(new Dimension(680,160));
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
	    scrollPanel3.setBorder(new TitledBorder(new EtchedBorder(), "Computer Berechnung im Binaersystem"));
		
	    compTextPane = new JTextPane();
		compTextPane.setBounds(0, 0, 10, 55);
		compTextPane.setMargin(new Insets(5,5,5,5));
		compTextPane.setEditable(false);
		compTextPane.setParagraphAttributes(style, true);
		scroll3 = new JScrollPane(compTextPane);
		scroll3.setPreferredSize(new Dimension(680,160));
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

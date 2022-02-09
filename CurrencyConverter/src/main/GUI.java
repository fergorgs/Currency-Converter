package main;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

public class GUI {

	private Currency from = Currency.REAL;
	private Currency to = Currency.DOLAR;
	private float amount = 1f;
	private Converter converter;
	
	private HashMap<String, Currency> currencyCodes; 
	
	JFrame frame;
	
	GUI(){
		
		converter = new Converter();
		
		currencyCodes = new HashMap<String, Currency>();
		
		currencyCodes.put("Real", Currency.REAL);
		currencyCodes.put("US Dollar", Currency.DOLAR);
		currencyCodes.put("Euro", Currency.EURO);
		currencyCodes.put("Czech Crown", Currency.COROA_TCHECA);
		currencyCodes.put("Australian Dollar", Currency.DOLAR_AUSTRALIANO);
		currencyCodes.put("Canadian Dollar", Currency.DOLAR_CANADENCE);
		currencyCodes.put("Swiss Franc", Currency.FRANCO_SUICO);
		currencyCodes.put("Yen", Currency.IENE);
		currencyCodes.put("British Pound", Currency.LIBRA_ESTERLINA);
		currencyCodes.put("Argentine Peso", Currency.PESO_ARGENTINO);
		currencyCodes.put("Yuan", Currency.YUAN);
		
		frame = new JFrame("Currency Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 150);
		
		// MENU BAR----------------------------------------
		JMenuBar menu = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenuItem m2 = new JMenuItem("About");
		JMenuItem m11 = new JMenuItem("Exit");
		
		m11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
                System.exit(0);
			}
		});
		m2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String messege = "Simple currency converter created to improve Java skills.\nMade by: @fergorgs\nGithub: github.com/fergorgs/";
				JOptionPane.showMessageDialog(null, messege,"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		m1.add(m11);
		menu.add(m1);
		menu.add(m2);
		
		// INPUT FIELDS------------------------------------
		JTextField fromValue = new JTextField(17);
		JTextField toValue = new JTextField(17);
		JLabel arrowLabel = new JLabel("  ==>  ");
		
		toValue.setEditable(false);
		
		fromValue.setText("1.00");
		fromValue.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if (((c < '0') || (c > '9')) && c != KeyEvent.VK_BACK_SPACE && c != '.' && c != ',')
	                  e.consume();
	         }
	      });
		
		JPanel amountsPanel = new JPanel();
		amountsPanel.add(fromValue);
		amountsPanel.add(arrowLabel);
		amountsPanel.add(toValue);
		
		// DROP DOWN MENUS----------------------------------
		String[] currencies = { "Real",
								"US Dollar",
								"Euro",
								"Czech Crown",
								"Australian Dollar",
								"Canadian Dollar",
								"Swiss Franc",
								"Yen",
								"British Pound",
								"Argentine Peso",
								"Yuan" };
		
		JComboBox<String> fromCurrency = new JComboBox<String>(currencies);
		JComboBox<String> toCurrency = new JComboBox<String>(currencies);
		JLabel fromLabel = new JLabel("from:");
		JLabel toLabel = new JLabel("to:");
		
		fromCurrency.setSelectedIndex(0);
		toCurrency.setSelectedIndex(1);
		
		fromCurrency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> target = (JComboBox<String>)e.getSource();
				String targetString = (String)target.getSelectedItem();
				from = currencyCodes.get(targetString);
			}
		});
		toCurrency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> target = (JComboBox<String>)e.getSource();
				String targetString = (String)target.getSelectedItem();
				to = currencyCodes.get(targetString);
			}
		});
		
		JPanel currenciesPanel = new JPanel();
		currenciesPanel.add(fromLabel);
		currenciesPanel.add(fromCurrency);
		currenciesPanel.add(toLabel);
		currenciesPanel.add(toCurrency);
		
		// CONVERT BUTTON----------------------------------
		JButton convertBtn = new JButton("Convert");
		
		convertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				String amountString = fromValue.getText();
				amountString = amountString.replace(",", ".");
				
				try {
					if(amountString.split(".").length > 2)
						throw new IllegalArgumentException("Invalid input format");
					
					amount = Float.parseFloat(amountString);
					float result = converter.convert(amount, from, to);
					toValue.setText(Float.toString(result));
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Please input a valid number","Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(convertBtn);
		
		// BRINGING ALL TOGETHER---------------------------
		JPanel mainPanel = new JPanel();
		mainPanel.add(amountsPanel);
		mainPanel.add(currenciesPanel);
		mainPanel.add(convertBtn);
		
		frame.add(menu, BorderLayout.NORTH);
		frame.add(mainPanel , BorderLayout.CENTER);
		
		toValue.setText(Float.toString(converter.convert(amount, from, to)));
	}
	
	public void display() {
		frame.setVisible(true);
	}
}

package gui;

import gui.AppointmentGui.PlaceTextFieldAction;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SokePaAndre extends JPanel{
	
	private JList addPersons;
	private JScrollPane addPersonsScroll;
	private JButton sok;
	private JLabel sokLabel;
	private JTextField sokeField;
	
	private GridBagConstraints c;
	
	public SokePaAndre(){
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		//label
		sokLabel = new JLabel("Søk");
		c.gridx=0;
		c.gridy=1;
		add(sokLabel,c);
		
		//sokefelt
		c.gridx = 1;
		c.gridy = 1;
		sokeField = new JTextField("Type or click button");
		sokeField.setColumns(15);
		c.insets = new Insets(10, 50, 0, 0);
		add(sokeField, c);
		
		//sokeknapp
		sok = new JButton("Søk");
		c.gridx=2;
		c.gridy=1;
		add(sok,c);
	}
}

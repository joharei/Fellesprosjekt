package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MeetingGui extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel invite;
	private JButton add, remove, newCreate, newCancel;
	private JList addPersons;
	private JScrollPane addPersonsScroll;
	
	protected GridBagConstraints gb;
	protected GridBagLayout gbLayout;
	
	public MeetingGui(){


		this.setModal(true);
		this.setPreferredSize(new Dimension(500,600));
		pack();
		
		gb = new GridBagConstraints();
		gbLayout = new GridBagLayout();
		setLayout(gbLayout);
		//invite
		gb.gridx = 0;
		gb.gridy = 5;
		invite = new JLabel("Invite: ");
		gb.insets = new Insets(10, 30, 50, 0);
		add(invite, gb);
		
		
		//add persons field
		gb.gridx = 1;
		gb.gridy = 5;
		addPersons = new JList();
		gb.insets = new Insets(48, 50, 0, 0);
		
		addPersonsScroll = new JScrollPane(addPersons);
		addPersonsScroll.setPreferredSize(new Dimension(170, 100));
		add(addPersonsScroll, gb);
		
		
		//add button
		gb.gridx = 2;
		gb.gridy = 5;
		add = new JButton("add");
		add.setPreferredSize(new Dimension(80, 30));
		gb.insets = new Insets(10, 0, 32, 0);
		add(add, gb);
		
		//remove button
		gb.gridx = 2;
		gb.gridy = 5;
		remove = new JButton("remove");
		remove.setPreferredSize(new Dimension(80, 30));
		gb.insets = new Insets(45, 0, 0, 0);
		add(remove, gb);
		
		
		//create and cancel button
		gb.gridx = 1;
		gb.gridy = 6;
		newCreate = new JButton("Create");
		gb.insets = new Insets(75, 0, 0, 50);
		add(newCreate, gb);
		
		gb.gridx = 1;
		gb.gridy = 6;
		newCancel =  new JButton("cancel");
		gb.insets = new Insets(75, 100, 0, 0);
		add(newCancel, gb);
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		newCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				setVisible(false);
			}
		});
		newCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				setVisible(false);
			}
		});
	}

	/**
	 * @param args
	 */

}

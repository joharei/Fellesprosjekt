package gui;

/*
 * GUIMain
 * 
 * Version 1.26
 * 
 * Author Magnus Solheim Thrap
 * 
 * GUI-class.
 * Purpose: main gui-class: show buttons for all tabs (chef/driver/order etc)
 * 
 */


import java.awt.Color;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUIMain extends JFrame {
	
	
	public static void main(String[] args) {
		GUIMain gmain = new GUIMain();
		gmain.setVisible(true);
	}

	public GUIMain() {
		add(new ScrollPane());
	}
}

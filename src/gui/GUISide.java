package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUISide extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rader=25;
	private int kolonner=7;
	private JButton [][] button = new JButton [rader][kolonner];
	protected GridBagConstraints c;
	GridBagConstraints gbc = new GridBagConstraints();
	JPanel panel = new JPanel();
	
	
	public static void main(String[] args) {
		GUISide gmain = new GUISide();
		gmain.pack();
		gmain.setVisible(true);
	}
	
	public GUISide(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		createCal();
	}
	
	public void createCal(){
		for ( int h = 0; h < rader; h++) {
			for (int j = 0; j < kolonner; j++) {
				button[h][j] = new JButton(new AbstractAction("") {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					// This method is called when the button is pressed
					public void actionPerformed(ActionEvent evt) {
						System.out.println("Test");
					}
				}
			);
				c.gridx=j;
				c.gridy=h;
				
				button[h][j].setSize(5, 5);
				button[h][j].setActionCommand(" ");
				
				add(button[h][j]);
			}
		}
	}
}

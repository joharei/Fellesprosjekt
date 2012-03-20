package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUICalender extends JPanel{
	private int rader =7;
	private int kolonner=24;
	private JButton [][] button = new JButton [kolonner][rader];
	GridBagConstraints c = new GridBagConstraints();
	private JPanel panel2 = new JPanel();
	
	public GUICalender(){
		
		setLayout(new GridBagLayout());
		String [] teller={"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
		String [] col ={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		for ( int h = 0; h < kolonner; h++) {
			
			button[h][0] = new JButton(teller[h]);
			c.gridx=9;
			c.gridy=4+h;
			//button[h][j].setSize(5, 5);
			//button[h][0].setActionCommand();
			add(button[h][0],c);
	}


	for ( int h = 0; h < kolonner; h++) {
		for (int j = 0; j < rader; j++) {
			button[h][j] = new JButton(new AbstractAction(" - ") {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				// This method is called when the button is pressed
				public void actionPerformed(ActionEvent evt) {
					System.out.println("Tester");
				}
			}
					);
			//button[h][j].setText(""+h+"."+j);
			c.gridx=11+j;
			c.gridy=4+h;
			//button[h][j].setSize(5, 5);
			button[h][j].setActionCommand(" ");
			//add(button[h][j],c);
			add(button[h][j],c);
		}
	}
	}
}
package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUILoggInInfo extends JPanel{
	private JLabel nameLabel;
	private JLabel ansattNrLabel;
	private JComboBox notificationBox;
	private String [] notifications;
	private GridBagConstraints c = new GridBagConstraints();
	
	public GUILoggInInfo(){
		
		setLayout(new GridBagLayout());
		
		nameLabel = new JLabel();
		nameLabel.setText(getName());
		c.gridx=1;
		c.gridy=1;
		add(nameLabel,c);
		
		ansattNrLabel = new JLabel();
		ansattNrLabel.setText(getAnsattNr());
		c.gridx=1;
		c.gridy=2;
		add(ansattNrLabel,c);
		
		notifications = new String[10];
		notifications[0]=("Notifications");
		for (int i = 1; i < notifications.length-1; i++) {
			
			try {
				notifications[i]=(sendNotification(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		
		notificationBox = new JComboBox(notifications);
		c.gridx=1;
		c.gridy=4;
		add(notificationBox,c);
		notificationBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        getSelectedNotification();
		    }
		});
		getSelectedNotification();
		
	}
	public String sendNotification(int a){
		String message []={"Notifications","Meeting deleted","Invitation","Meeting declined","Meeting accepted"};
		
		return message[a];
	}
	public void getSelectedNotification(){
		Object value = notificationBox.getSelectedItem();
		if(value.equals("Meeting deleted")){
			JOptionPane.showMessageDialog(null,"Meeting was deleted");
		}
		//Her skal det skje noe mer n�r man velger en notification
	}
	private String getAnsattNr() {
		// TODO Auto-generated method stub
		String nr=" 600134";
		String text="Employee Nr: ";
		
		return text+nr;
	}
	
	public String getName(){
		String info="Magnus Kongshem";//midlertidig for testing av GUI
		//hent opp info fra Logg inn siden
		
		return info;
	}
}

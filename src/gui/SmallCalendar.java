package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SmallCalendar extends JPanel{
	private JButton [] buttons;
	private GridBagConstraints c = new GridBagConstraints();
	private JComboBox mndText;
	private String [] dagerOdde = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private String [] dagerPar = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
	private String [] februar = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
	private String [] maned = {"-","Januar", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	public SmallCalendar(){
		
		setLayout(new GridBagLayout());
		
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		
		mndText = new JComboBox(maned);
		mndText.setSelectedIndex(month);
		rebuildButtons(mndText.getSelectedIndex());
		mndText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getActionCommand());
				System.out.println(mndText.getSelectedIndex());
				buildButtons(mndText.getSelectedIndex());
				//rebuildButtons(mndText.getSelectedIndex());
			}
			
		});
		c.gridx=5;
		c.gridy=8;
		c.gridwidth=2;
		add(mndText,c);
		c.gridwidth=1;

		
	}
	public void buildButtons(int maned){
		if(maned==1 || maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
			if(buttons[30]==null){
				buttons[30]=new JButton("31");
			}
			else{
				return;
			}
		}
		if(maned==4||maned==6||maned==9||maned==11){
			if(buttons[30]==null){
				
			}
		}
		
			
	}
	public void rebuildButtons(int maned){
		//System.out.println("gennomføriong");
		buttons = new JButton[32];
		c.gridy=8;
			if(maned==1 || maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
				for (int i = 0; i < 31; i++) {
					buttons[i]= new JButton(dagerOdde[i]);
					buttons[i].setActionCommand(dagerOdde[i]);
					buttons[i].addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
						  String choice = e.getActionCommand();
						  System.out.println(choice);
						  }
						});
				
					if(i==0 || i==7 || i==14|| i==21|| i==28){
						c.gridy++;
						c.gridx=1;
					}
				c.gridx++;
				add(buttons[i],c);
				
				
				
				}		
			}
			if(maned==4||maned==6||maned==9||maned==11){
				for (int i = 0; i < 30; i++) {
				buttons[i]= new JButton(dagerPar[i]);
				buttons[i].setActionCommand(dagerPar[i]);
				buttons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					  String choice = e.getActionCommand();
					  System.out.println(choice);
					  }
					});
				
				if(i==0 || i==7 || i==14|| i==21|| i==28){
					c.gridy++;
					c.gridx=1;
				}
				c.gridx++;
				
				add(buttons[i],c);
				}
			}
			if(maned==2){
				for (int i = 0; i < 28; i++) {
				buttons[i]= new JButton(februar[i]);
				buttons[i].setActionCommand(februar[i]);
				buttons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					  String choice = e.getActionCommand();
					  System.out.println(choice);
					  }
					});
				
				if(i==0 || i==7 || i==14|| i==21|| i==28){
					c.gridy++;
					c.gridx=1;
				}
				c.gridx++;

				add(buttons[i],c);
			}
			
			
		}
	}
	public String findMnd(int a){
		if(a==0){
			return maned[0];
		}
		if(a==1){
			return maned[1];
		}
		if(a==2){
			return maned[2];
		}
		if(a==3){
			return maned[3];
		}
		if(a==4){
			return maned[4];
		}
		if(a==5){
			return maned[5];
		}
		if(a==6){
			return maned[6];
		}
		if(a==7){
			return maned[7];
		}
		if(a==8){
			return maned[8];
		}
		if(a==9){
			return maned[9];
		}
		if(a==10){
			return maned[10];
		}
		if(a==11){
			return maned[11];
		}
		return "Mars";
	}
}

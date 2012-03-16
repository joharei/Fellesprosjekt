package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//protected static final String IMAGE_PATH = "gui/";
	protected static final Color COLOR_BACKGROUND = Color.WHITE;
	protected static final Color COLOR_APOINTMENT = new Color(247, 199, 173);
	protected static final Color COLOR_NO_APOINTMENT = new Color(198, 243, 214);
	//protected static final Color COLOR_IN_DELIVERY = new Color(206, 219, 239);
	protected static final Font FONT_HEADER = new Font("", Font.PLAIN, 20);
	protected static final Font FONT_FIELD = new Font("", Font.PLAIN, 18);

	protected GridBagConstraints c;
	GridBagConstraints gbc = new GridBagConstraints();
	JPanel panel = new JPanel();
	
	private JTextArea navnet;
	private JComboBox mndText;
	private int rader =25;
	private int kolonner=8;
	private JButton knapper;
	private int mndTeller;
	private JButton notificationButton;
	private JButton logOutButton;
	private JButton createButton;
	private JButton [] buttons;
	private JButton [][] button = new JButton [rader][kolonner];
	private String [] maned = {"-","Januar", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private String [] dagerOdde = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private String [] dagerPar = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
	private String [] februar = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
	private String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
	
	public static void main(String[] args) {
		GUI gmain = new GUI();
		gmain.pack();
		gmain.setVisible(true);
	}
	
	
	public GUI() {
		getContentPane().setBackground(COLOR_BACKGROUND);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
	
		String [] teller={"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","012:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00",};
		String [] col ={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		
		for ( int h = 0; h < rader; h++) {
			for (int j = 0; j < kolonner; j++) {
				button[h][j] = new JButton(new AbstractAction("") {
					
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
	
		
		
		/*
		navnet = new JTextArea();
		c.gridx=1;
		c.gridy=1;
		add(navnet,c);
		*/
		notificationButton = new JButton("Notification");
		c.gridx=1;
		c.gridy=2;
		add(notificationButton,c);
		
		logOutButton = new JButton("Log Out");
		c.gridx=9;
		c.gridy=0;
		add(logOutButton,c);
		
		mndText = new JComboBox(maned);
		c.gridx=3;
		c.gridy=3;
		add(mndText,c);
		
		int maneden=3;
		if(mndText.getSelectedItem()=="January" ||mndText.getSelectedItem()=="Mars" ||mndText.getSelectedItem()=="May" ||mndText.getSelectedItem()=="July" ||mndText.getSelectedItem()=="August" ||mndText.getSelectedItem()=="October" ||mndText.getSelectedItem()=="December"){
			maneden=2;
		}
		if(mndText.getSelectedItem()=="February"){
			maneden=5;
		}
		buttons = new JButton[32];
		for (int i = 0; i < 33-maneden; i++) {
			
			if(maneden==2){
				buttons[i]= new JButton(dagerOdde[i]);
				buttons[i].setActionCommand(dagerOdde[i]);
				buttons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					  String choice = e.getActionCommand();
					  }
					});
				
				if(i==0 || i==7 || i==14|| i==21|| i==28){
					c.gridy++;
					c.gridx=1;
				}
				c.gridx++;
				add(buttons[i],c);
					
			}
			if(maneden==3){
				buttons[i]= new JButton(dagerPar[i]);
				buttons[i].setActionCommand(dagerPar[i]);
				buttons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					  String choice = e.getActionCommand();
					  }
					});
				
				if(i==0 || i==7 || i==14|| i==21|| i==28){
					c.gridy++;
					c.gridx=1;
				}
				c.gridx++;
				add(buttons[i],c);
			}
			if(maneden==5){
				buttons[i]= new JButton(februar[i]);
				buttons[i].setActionCommand(februar[i]);
				buttons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
					  String choice = e.getActionCommand();
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
		
		createButton = new JButton("Create");
		c.gridx=1;
		c.gridy++;
		add(createButton,c);
		
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
	public void createCal(){
		
	}
	class CalendarModel extends AbstractTableModel {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		

		  int[] numDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		  String[][] calendar = new String[7][7];

		  public CalendarModel() {
		    for (int i = 0; i < days.length; ++i)
		      calendar[0][i] = days[i];
		    for (int i = 1; i < 7; ++i)
		      for (int j = 0; j < 7; ++j)
		        calendar[i][j] = " ";
		  }

		  public int getRowCount() {
		    return 7;
		  }

		  public int getColumnCount() {
		    return 7;
		  }

		  public Object getValueAt(int row, int column) {
		    return calendar[row][column];
		  }

		  public void setValueAt(Object value, int row, int column) {
		    calendar[row][column] = (String) value;
		  }

		  public void setMonth(int year, int month) {
		    for (int i = 1; i < 7; ++i)
		      for (int j = 0; j < 7; ++j)
		        calendar[i][j] = " ";
		    java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
		    cal.set(year, month, 1);
		    int offset = cal.get(java.util.GregorianCalendar.DAY_OF_WEEK) - 1;
		    offset += 7;
		    int num = daysInMonth(year, month);
		    for (int i = 0; i < num; ++i) {
		      calendar[offset / 7][offset % 7] = Integer.toString(i + 1);
		      ++offset;
		    }
		  }

		  public boolean isLeapYear(int year) {
		    if (year % 4 == 0)
		      return true;
		    return false;
		  }

		  public int daysInMonth(int year, int month) {
		    int days = numDays[month];
		    if (month == 1 && isLeapYear(year))
		      ++days;
		    return days;
		  }
		}
}

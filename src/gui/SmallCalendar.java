package gui;

import gui.ListOfPersons2.SearchAction;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import synclogic.ClientSynchronizationUnit;

import model.SaveableClass;
import model.User;

import com.toedter.calendar.JCalendar;

public class SmallCalendar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c = new GridBagConstraints();
	protected JComboBox mndText;
	private JCalendar jDate;
	private int ukenr;
	private Object[] colNames =  {"Name", "e-mail"};
	private int year;
	private Calendar calender=Calendar.getInstance();
	private Calendar cal = Calendar.getInstance();
	public final static String NAME_PROPERTY_WEEK_NUMBER="ukenr";
	private PropertyChangeSupport support;
	public final static String NAME_PROPERTY_START_DATE_OF_WEEK="startDateOfWeek";
	public final static String NAME_PROPERTY_START_MONTH_OF_WEEK="startMonthOfWeek";
	public final static String NAME_PROPERY_DATES_IN_WEEK="datoIUken";
	public final static String NAME_PROPERTY_MAX_DAYS_IN_WEEK="maxDays";
	public final static String NAME_PROPERTY_MONTH="maaned";
	public final static String NAME_PROPERTY_DAYS_IN_WEEK="dagIUken";
	public final static String NAME_PROPERTY_DATE_IN_MONTH="datoIMnd";
	public static String navnSokes;
	private JList addPersons;
	private JScrollPane addPersonsScroll;
	private JLabel sokLabel;
	private JTextField sokeField;
	private JButton getUserButton;
	private JButton removeUserButton;
	//ArrayList<User> allUsers;
	public int getValue (){
		return mndText.getSelectedIndex();
	}
	
	public SmallCalendar(){
		support = new PropertyChangeSupport(this);
		
		setLayout(new GridBagLayout());
		jDate = new JCalendar();
		c.gridx=2;
		c.gridy=1;
		add(jDate,c);
		jDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				calender.setTime(jDate.getDate());
				calender.setMinimalDaysInFirstWeek(4);  
				calender.setFirstDayOfWeek(Calendar.MONDAY);
				year=calender.get(Calendar.YEAR);
				setUkeNr(calender.get(Calendar.WEEK_OF_YEAR));
				//setDateInWeek(calender.get(Calendar.));
				calender.set(Calendar.WEEK_OF_YEAR, ukenr);
//				setDateInMonth(calender.get(Calendar.DAY_OF_MONTH));
//				setDagIUken(calender.get(Calendar.DAY_OF_WEEK));
//				setMonth(calender.get(Calendar.MONTH)+1);
//				setMaxDays(calender.getActualMaximum(Calendar.DAY_OF_MONTH));
				//System.out.println("Dato p� f�rste dag i uken:"+calender.getTime());
				cal.set(cal.WEEK_OF_YEAR,ukenr);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				cal.get(Calendar.DAY_OF_MONTH);
				cal.get(Calendar.MONTH);
				//System.out.println("Takk piss: "+cal.getTime());
				//System.out.println("Takk igjen: "+cal.get(Calendar.DAY_OF_MONTH)+" - "+ cal.get(Calendar.MONTH));
				
			//	System.out.println("Ukenr: "+calender.get(Calendar.WEEK_OF_YEAR));
			//	System.out.println("Dag i uken: "+dagIUken+" M�ned nr: "+maaned + " Dato i mnd: "+datoIMnd);
			}

		});
		
		//label
		sokLabel = new JLabel("S�k");
		c.gridx=1;
		c.gridy=3;
		add(sokLabel,c);
		
		//sokefelt
		c.gridx = 2;
		c.gridy = 3;
		sokeField = new JTextField();
		sokeField.setColumns(15);
		sokeField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getKeyChar());
				navnSokes+=e.getKeyChar();
				//System.out.println(navnSokes);
				//System.out.println("Her skal det komme: "+XCal.getCSU().getAllUsers());
				ArrayList<User> userlist = (ArrayList<User>) XCal.getCSU().getAllUsers();
				//String temp =XCal.getCSU().getAllUsers().toString();
				//System.out.println(temp);
				User [] tempe=null;
				
				int count = userlist.size();
				
				for(int i = 0; i<count; i++){
					if(userlist.get(i) != null){
//							String userString = selectedNames[i] + " ; " + selectedEmails[i];
						DefaultListModel model = (DefaultListModel) addPersons.getModel();
						if(!model.contains(userlist.get(i))){
							model.addElement(userlist.get(i));
						}
					}
					continue;
				}
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		c.insets = new Insets(10, 50, 0, 0);
		add(sokeField, c);
		
		//liste
		c.gridx=2;
		c.gridy=4;
		addPersons = new JList();
		addPersons.setModel(new DefaultListModel());
		addPersonsScroll = new JScrollPane(addPersons);
		addPersonsScroll.setPreferredSize(new Dimension(170, 100));
		add(addPersonsScroll, c);
		
		c.gridx=3;
		c.gridy=3;
		getUserButton = new JButton("Hent");
		getUserButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(addPersons.getSelectedValues());
				addPersons.getSelectedValues();
				int temp=0;
				ArrayList <String> users= new ArrayList<String>();
				temp=addPersons.getSelectedValues().length;
				User user =(User)XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText());
				for (int i = 0; i < temp; i++) {
					//user.addSubscription();
					users.add(((User)addPersons.getModel().getElementAt(i)).getUsername());
				}
				user.setSubscriptionsToAdd(users);
				XCal.getCSU().addToSendQueue(user);
			}
		});
		add(getUserButton,c);
		
		c.gridx=3;
		c.gridy=4;
		removeUserButton = new JButton("Remove");
	
		getUserButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addPersons.getSelectedValue();
				int temp=0;
				temp=addPersons.getSelectedValues().length;
				String usernameTemp="";
				User user =(User)XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText());
				for (int i = 0; i < temp; i++) {
					//user.addSubscription((User)addPersons.getModel().getElementAt(i));
					usernameTemp=((((User) addPersons.getModel().getElementAt(i)).getUsername()));
					
				}
				user.removeSubscriptionToAdd(usernameTemp);
				XCal.getCSU().addToSendQueue(user);
			}
		});
		add(removeUserButton,c);
	}
	public void setUkeNr(int ukenr){
		int temp=this.ukenr;
		this.ukenr=ukenr;
		
		support.firePropertyChange(NAME_PROPERTY_WEEK_NUMBER, temp, ukenr);
	}
	
	
	public int getUkeNr(){
		return ukenr;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public void addListener(PropertyChangeListener listener){
		support.addPropertyChangeListener(listener);
	}
	public void removeListener(PropertyChangeListener listener){
		support.removePropertyChangeListener(listener);
	}
	
	
}

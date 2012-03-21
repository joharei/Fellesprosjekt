package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

public class SmallCalendar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton [] buttons;
	private CalendarPanecolumnHeaderPanel langtNavnPanel;
	private GridBagConstraints c = new GridBagConstraints();
	protected JComboBox mndText;
	private String [] dagerOdde = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private String [] dagerPar = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
	private String [] februar = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
	private String [] maned = {"-","Januar", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private JCalendar jDate;
	private int ukenr;
	private int maaned;
	private int dagIUken;
	private int datoIMnd;
	private Calendar calender=Calendar.getInstance();
	public final static String NAME_PROPERTY_WEEK_NUMBER="ukenr";
	private PropertyChangeSupport support;
	public final static String NAME_PROPERTY_MONTH="maaned";
	public final static String NAME_PROPERTY_DAYS_IN_WEEK="dagIUken";
	public final static String NAME_PROPERTY_DATE_IN_MONTH="datoIMnd";
	
	public int getValue (){
		return mndText.getSelectedIndex();
	}
	
	public SmallCalendar(){
		support = new PropertyChangeSupport(this);
		
		setLayout(new GridBagLayout());
		jDate = new JCalendar();
		c.gridx=1;
		c.gridy=1;
		add(jDate,c);
		jDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				calender.setTime(jDate.getDate());
				calender.setMinimalDaysInFirstWeek(4);  
				calender.setFirstDayOfWeek(Calendar.MONDAY);
				setUkeNr(calender.get(Calendar.WEEK_OF_YEAR));
				calender.set(Calendar.WEEK_OF_YEAR, ukenr);
				setDateInMonth(calender.get(Calendar.DAY_OF_MONTH));
				setDagerIUken(calender.get(Calendar.DAY_OF_WEEK));
				setMonth(calender.get(Calendar.MONTH)+1);
			//	System.out.println("Ukenr: "+calender.get(Calendar.WEEK_OF_YEAR));
			//	System.out.println("Dag i uken: "+dagIUken+" Måned nr: "+maaned + " Dato i mnd: "+datoIMnd);
			}
		});
	
	}
	public void setUkeNr(int ukenr){
		int temp=this.ukenr;
		this.ukenr=ukenr;
		
		support.firePropertyChange(NAME_PROPERTY_WEEK_NUMBER, temp, ukenr);
	}
	public void setMonth(int month){
		int temp=this.maaned;
		this.maaned=month;
		
		support.firePropertyChange(NAME_PROPERTY_MONTH, temp, month);
	}
	public void setDagerIUken(int dagerIUken){
		if(dagerIUken==1){
			dagerIUken=7;
		}
		else{
			dagerIUken--;
		}
		int temp = this.dagIUken;
		this.dagIUken=dagerIUken;
		
		support.firePropertyChange(NAME_PROPERTY_DAYS_IN_WEEK, temp, dagerIUken);
	}
	public void setDateInMonth(int datoIMnd){
		int temp = this.datoIMnd;
		this.datoIMnd=datoIMnd;
		
		support.firePropertyChange(NAME_PROPERTY_DATE_IN_MONTH, temp, datoIMnd);
	}
	public void addListener(PropertyChangeListener listener){
		support.addPropertyChangeListener(listener);
	}
	public void removeListener(PropertyChangeListener listener){
		support.removePropertyChangeListener(listener);
	}
	
}

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
	private int startDateOfWeek;
	private int startMonthOfWeek;
	private int year;
	private int maaned;
	private int dagIUken;
	private int datoIMnd;
	private int maxDays;
	private int [] datesInWeek;
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
				year=calender.get(calender.YEAR);
				setUkeNr(calender.get(Calendar.WEEK_OF_YEAR));
				//setDateInWeek(calender.get(Calendar.));
				calender.set(Calendar.WEEK_OF_YEAR, ukenr);
				setDateInMonth(calender.get(Calendar.DAY_OF_MONTH));
				setDagIUken(calender.get(Calendar.DAY_OF_WEEK));
				setMonth(calender.get(Calendar.MONTH)+1);
				setMaxDays(calender.getActualMaximum(Calendar.DAY_OF_MONTH));
				//System.out.println("Dato på første dag i uken:"+calender.getTime());
				cal.set(cal.WEEK_OF_YEAR,ukenr);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				cal.get(Calendar.DAY_OF_MONTH);
				cal.get(Calendar.MONTH);
				//System.out.println("Takk piss: "+cal.getTime());
				//System.out.println("Takk igjen: "+cal.get(Calendar.DAY_OF_MONTH)+" - "+ cal.get(Calendar.MONTH));
				
			//	System.out.println("Ukenr: "+calender.get(Calendar.WEEK_OF_YEAR));
			//	System.out.println("Dag i uken: "+dagIUken+" Måned nr: "+maaned + " Dato i mnd: "+datoIMnd);
			}

		});
	
	}
	private void setStartDateOfWeek(int startDateOfWeek){
		int temp=this.startDateOfWeek;
		this.startDateOfWeek=startDateOfWeek;
		support.firePropertyChange(NAME_PROPERTY_START_DATE_OF_WEEK, temp, startDateOfWeek);
	}
	private void setStartMonthOfWeek(int startMonthOfWeek){
		int temp=this.startMonthOfWeek;
		this.startMonthOfWeek=startMonthOfWeek;
		support.firePropertyChange(NAME_PROPERTY_START_MONTH_OF_WEEK, temp, startMonthOfWeek);
	}
	private void setMaxDays(int actualMaximum) {
		// TODO Auto-generated method stub
		int temp=this.maxDays;
		this.maxDays=actualMaximum;
		support.firePropertyChange(NAME_PROPERTY_MAX_DAYS_IN_WEEK, temp, actualMaximum);
		
	}
	
	public void setUkeNr(int ukenr){
		int temp=this.ukenr;
		this.ukenr=ukenr;
		
		support.firePropertyChange(NAME_PROPERTY_WEEK_NUMBER, temp, ukenr);
	}
	
	public int getUkeNr(){
		return ukenr;
	}
	public void setMonth(int month){
		int temp=this.maaned;
		this.maaned=month;
		
		support.firePropertyChange(NAME_PROPERTY_MONTH, temp, month);
	}
	public int getMonth(){
		return maaned;
	}
	public void setDagIUken(int dagerIUken){
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
	
	public void setDateInWeek(int [] datesInWeek){
		int [] temp = null;
		for (int i = 0; i <7; i++) {
			temp [i]= this.datesInWeek[i];
			this.datesInWeek[i]=datesInWeek[i];
			support.firePropertyChange(NAME_PROPERY_DATES_IN_WEEK, temp, datesInWeek[i]);
		}
		
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
